package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Payment;
import com.salesliant.entity.SerialNumber;
import static com.salesliant.report.Templates.addAttributes;
import static com.salesliant.report.Templates.borderedStyle;
import static com.salesliant.report.Templates.columnInvoiceReportTitleStyle;
import static com.salesliant.report.Templates.createCompanyAddressComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.leftTopColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rightTopColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseListUI.getBillToAddress;
import static com.salesliant.util.BaseUtil.getString;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class InvoiceLayout {

    private final Invoice fInvoice;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal taxTotal = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal deposit = BigDecimal.ZERO;
    private BigDecimal paidTotal = BigDecimal.ZERO;
//    private BigDecimal freightTotal = BigDecimal.ZERO;
    private BigDecimal change = BigDecimal.ZERO;
    private String note = "";
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public InvoiceLayout(Invoice invoice) {
        this.fInvoice = invoice;
        if (fInvoice.getSubTotal() != null) {
            subTotal = fInvoice.getSubTotal();
        }
        if (fInvoice.getPayments() != null && !fInvoice.getPayments().isEmpty()) {
            change = fInvoice.getPayments()
                    .stream()
                    .filter(f -> f.getChangeAmount() != null && f.getChangeAmount().compareTo(BigDecimal.ZERO) > 0)
                    .map(e -> e.getChangeAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            paidTotal = fInvoice.getPayments()
                    .stream()
                    .filter(f -> f.getPaymentType() != null && !f.getPaymentType().getIsNetTerm()
                    && f.getTenderedAmount() != null && f.getTenderedAmount().compareTo(BigDecimal.ZERO) > 0)
                    .map(e -> e.getTenderedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        if (fInvoice.getTaxAmount() != null) {
            taxTotal = fInvoice.getTaxAmount();
        }
        if (fInvoice.getTotal() != null) {
            total = fInvoice.getTotal();
        }
        if (fInvoice.getDepositAmount() != null && fInvoice.getDepositAmount() != null) {
            deposit = fInvoice.getDepositAmount();
        }
        if (fInvoice.getNote() != null) {
            note = "Note: " + fInvoice.getNote();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> yourSKUColumn = col.column("SKU", "yourSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(110);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(245);
        TextColumnBuilder<BigDecimal> quantityColumn = col.column("QTY", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("PRICE", "price", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("TOTAL").setDataType(decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(65);

        report.setTemplate(Templates.invoiceReportTemplate)
                //                .setColumnStyle(columnStyle)
                .setSubtotalStyle(subtotalStyle)
                .columns(yourSKUColumn, descriptionColumn, quantityColumn, unitPriceColumn, priceColumn)
                .title(createTitleComponent(Config.getStore().getStoreName(), "Invoice"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyAddressComponent()),
                                cmp.hListCell(createInvoiceComponent())),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(20).add(
                                cmp.hListCell(createCustomerComponent("Bill To")).heightFixedOnTop(),
                                cmp.hListCell(createShipToComponent("Ship To")).heightFixedOnTop()),
                        createInfoRowHorizontalList(),
                        createServiceHorizontalList(),
                        cmp.verticalGap(5))
                .summary(createSummaryComponent())
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createCustomerComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addAttributes(aList, getBillToAddress(fInvoice));
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createInvoiceComponent() {
        String i = "Invoice #:  " + getString(fInvoice.getInvoiceNumber()) + "\n"
                + "Customer Account #:  " + getString(fInvoice.getCustomerAccountNumber());
        if (fInvoice.getDateInvoiced() != null) {
            i = i + "\n" + "DATE:  " + getString(fInvoice.getDateInvoiced());
        }

        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    private ComponentBuilder<?, ?> createShipToComponent(String label) {
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));

        addAttributes(list, getString(fInvoice.getShipToAddress()));

        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), list);
    }

    private ComponentBuilder<?, ?> createInfoRowHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.text("REQUISITIONER").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("SHIP VIA").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("TERM").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Customer PO#").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.newRow();

        String salesName = getString(fInvoice.getSalesName());
        String term = "";
        if (fInvoice.getCustomerTermCode() != null) {
            term = fInvoice.getCustomerTermCode();
        }
        String shipVIA = getString(fInvoice.getShipVia());
        String customerPONumber = "";
        if (fInvoice.getCustomerPoNumber() != null) {
            customerPONumber = fInvoice.getCustomerPoNumber();
        }
        horizontalList.add(cmp.text(salesName).setStyle(borderedStyle));
        horizontalList.add(cmp.text(shipVIA).setStyle(borderedStyle));
        horizontalList.add(cmp.text(term).setStyle(borderedStyle));
        horizontalList.add(cmp.text(customerPONumber).setStyle(borderedStyle));
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createPaymentComponent() {
        String c = "";
        if (fInvoice.getPayments() != null && !fInvoice.getPayments().isEmpty()) {
            for (Payment p : fInvoice.getPayments()) {
                if (p.getPaymentType() != null && p.getTenderedAmount() != null) {
                    c = c + getString(p.getPaymentType().getCode()) + "\t" + getString(p.getTenderedAmount()) + "\n";
                }
            }
        }
        return cmp.text(c).setStyle(stl.style().setFontSize(8).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    private ComponentBuilder<?, ?> createSumGroup() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.filler(), cmp.text("Subtotal:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(subTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Deposit:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(deposit)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        if (fInvoice.getCreditAmount() != null && fInvoice.getCreditAmount().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("Credit Applied:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fInvoice.getCreditAmount())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        horizontalList.add(cmp.filler(), cmp.text("Tax:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(taxTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
//        horizontalList.add(cmp.filler(), cmp.text("Freight:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
//                cmp.text(getString(freightTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
//        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Total:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(total)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Total Paid:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(paidTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Change Due:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(change)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createServiceHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        if (fInvoice.getService() != null && !fInvoice.getService().getServiceEntries().isEmpty()) {
            horizontalList.add(cmp.text("Tech").setFixedWidth(60));
            horizontalList.add(cmp.text("Service Code").setFixedWidth(80));
            horizontalList.add(cmp.text("Time").setFixedWidth(105));
            horizontalList.add(cmp.text("Note"));
            horizontalList.newRow();
            fInvoice.getService().getServiceEntries().forEach(se -> {
                String tech = "";
                if (se.getEmployee() != null) {
                    tech = se.getEmployee().getNameOnSalesOrder();
                }
                String dateTime = "";
                if (se.getDateEntered() != null) {
                    dateTime = getString(se.getDateEntered());
                }
                String serviceCode = "";
                if (se.getServiceCode() != null) {
                    serviceCode = se.getServiceCode().getCode();
                }
                String serviceNote = "";
                if (se.getNote() != null) {
                    serviceNote = se.getNote();
                }
                horizontalList.add(cmp.text(tech).setFixedWidth(60));
                horizontalList.add(cmp.text(serviceCode).setFixedWidth(80));
                horizontalList.add(cmp.text(dateTime).setFixedWidth(105));
                horizontalList.add(cmp.text(serviceNote));
                horizontalList.newRow();
            });
        }
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createSummaryComponent() {
        VerticalListBuilder horizontalList = cmp.verticalList();
        horizontalList.add(cmp.line());
        horizontalList.add(cmp.verticalGap(5));
        horizontalList.add(cmp.horizontalList().setStyle(stl.style(1)).add(
                cmp.hListCell(createPaymentComponent()).heightFixedOnTop(),
                cmp.hListCell(createSumGroup()).heightFixedOnTop()));
        horizontalList.add(cmp.horizontalList(cmp.verticalGap(8), cmp.text(note).setStyle(leftColumnStyle)));
        horizontalList.add(cmp.horizontalList(cmp.verticalGap(8), cmp.text(Config.getStore().getInvoiceMessage()).setStyle(Templates.leftColumnStyle)));
        return horizontalList;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("yourSKU", "description", "qty", "price");
        if (fInvoice.getInvoiceEntries() != null || !fInvoice.getInvoiceEntries().isEmpty()) {
            List<InvoiceEntry> list = fInvoice.getInvoiceEntries().stream()
                    .sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                    .collect(Collectors.toList());
            int from = 1000000;
            int end = 1000000;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getItemLookUpCode().equalsIgnoreCase("Package")) {
                    from = i;
                }
                if (list.get(i).getItemLookUpCode().equalsIgnoreCase("Subtotal")) {
                    end = i;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                String description;
                BigDecimal qty;
                BigDecimal price;
                if ((list.get(i).getItemLookUpCode() != null && list.get(i).getItemLookUpCode().equalsIgnoreCase("NOTE:"))
                        && (list.get(i).getItemLookUpCode() != null && list.get(i).getItemDescription().equalsIgnoreCase("SYSSN"))) {
                    description = getString(list.get(i).getNote());
                } else if (list.get(i).getLineNote() != null && !list.get(i).getLineNote().isEmpty()) {
                    description = getString(list.get(i).getItemDescription()) + "\n" + list.get(i).getLineNote();
                } else {
                    description = getString(list.get(i).getItemDescription());
                }
                if (list.get(i).getSerialNumbers() != null & !list.get(i).getSerialNumbers().isEmpty()) {
                    description = description + "\n";
                    for (SerialNumber sn : list.get(i).getSerialNumbers()) {
                        description = description + getString(sn.getSerialNumber()) + "; ";
                    }
                }
                description = description.trim();
                if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                    description = description.substring(0, description.length() - 1);
                }
                if ((list.get(i).getItemLookUpCode() != null && list.get(i).getItemLookUpCode().equalsIgnoreCase("NOTE:"))
                        || (list.get(i).getItemLookUpCode() != null && list.get(i).getItemDescription() != null
                        && list.get(i).getItemDescription().equalsIgnoreCase("SUBTOTAL"))) {
                    qty = null;
                } else {
                    qty = list.get(i).getQuantity();
                }
                if ((list.get(i).getComponentFlag() != null && list.get(i).getComponentFlag())
                        || (list.get(i).getItemLookUpCode() != null && list.get(i).getItemLookUpCode().equalsIgnoreCase("NOTE:"))
                        || (list.get(i).getItemLookUpCode() != null && list.get(i).getItemLookUpCode().equalsIgnoreCase("SYSSN"))
                        || (list.get(i).getItemLookUpCode() != null && list.get(i).getItemLookUpCode().equalsIgnoreCase("SUBTOTAL"))) {
                    price = null;
                } else {
                    price = list.get(i).getPrice();
                }
                if (from < 10000 && end < 10000 && i > from && i < end) {
                    price = null;
                }
                dataSource.add(list.get(i).getItemLookUpCode(), description, qty, price);
            }
        }

        return dataSource;
    }
}
