package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;
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
import static com.salesliant.util.BaseListUI.getShipToAddress;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.DBConstants;
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

public class SalesOrderLayout {

    private final SalesOrder fSalesOrder;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal taxTotal = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal deposit = BigDecimal.ZERO;
    private BigDecimal freightTotal = BigDecimal.ZERO;
    private String note = "";
    private final Store fStore;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public SalesOrderLayout(SalesOrder salesOrder) {
        this.fSalesOrder = salesOrder;
        fStore = Config.getStore();
        if (fSalesOrder.getTaxAmount() != null) {
            taxTotal = fSalesOrder.getTaxAmount();
        }
        if (fSalesOrder.getShippingCharge() != null) {
            freightTotal = fSalesOrder.getShippingCharge();
        }
        if (fSalesOrder.getTotal() != null) {
            total = fSalesOrder.getTotal();
        }
        subTotal = total.subtract(taxTotal).subtract(freightTotal);
        if (!fSalesOrder.getDeposits().isEmpty()) {
            deposit = fSalesOrder.getDeposits()
                    .stream()
                    .map(e -> e.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        balance = total.subtract(deposit);
        if (fSalesOrder.getNote() != null) {
            note = "Note: " + fSalesOrder.getNote();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> yourSKUColumn = col.column("SKU", "yourSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(110);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(230);
        TextColumnBuilder<BigDecimal> quantityColumn = col.column("QTY", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("PRICE", "price", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("TOTAL").setDataType(decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(55);

        report.setTemplate(Templates.invoiceReportTemplate)
                //                .setColumnStyle(columnStyle)
                .setSubtotalStyle(subtotalStyle)
                .columns(yourSKUColumn, descriptionColumn, quantityColumn, unitPriceColumn, priceColumn)
                .title(createTitleComponent(fStore.getStoreName(), getOrderType()),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyAddressComponent()),
                                cmp.hListCell(createInvoiceComponent())),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(120).add(
                                cmp.hListCell(createCustomerComponent("Bill To")).heightFixedOnTop(),
                                cmp.hListCell(createShipToComponent("Ship To")).heightFixedOnTop()),
                        cmp.verticalGap(10),
                        createInfoRowHorizontalList(),
                        createServiceHorizontalList(),
                        cmp.verticalGap(10))
                .summary(createSummaryComponent())
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(30).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createCustomerComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        if (fSalesOrder.getBuyer() != null) {
            addAttributes(aList, getBillToAddress(fSalesOrder.getBuyer()));
        } else {
            addAttributes(aList, getBillToAddress(fSalesOrder.getCustomer()));
        }
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createInvoiceComponent() {
        String accountNumber = "";
        if (fSalesOrder.getCustomer() != null && fSalesOrder.getCustomer().getAccountNumber() != null) {
            accountNumber = fSalesOrder.getCustomer().getAccountNumber();
        }
        String i = "Reference #:  " + getString(fSalesOrder.getSalesOrderNumber()) + "\n"
                + "Customer Account #:  " + accountNumber;
        if (fSalesOrder.getDateOrdered() != null) {
            i = i + "\n" + "Date:  " + getString(fSalesOrder.getDateOrdered());
        }
        if (fSalesOrder.getDateDue() != null) {
            if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
                i = i + "\n" + "Expiration Date:  " + getString(fSalesOrder.getDateDue());
            } else {
                i = i + "\n" + "Due Date:  " + getString(fSalesOrder.getDateDue());
            }
        }

        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    private ComponentBuilder<?, ?> createShipToComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        String shipTo;
        if (fSalesOrder.getShipTo() == null) {
            shipTo = getBillToAddress(fSalesOrder.getCustomer());
        } else {
            shipTo = getShipToAddress(fSalesOrder.getShipTo());
        }
        addAttributes(aList, shipTo);
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createSumGroup() {

        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.filler(), cmp.text("Subtotal:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(subTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Deposit:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(deposit)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Tax:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(taxTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Freight:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(freightTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Total:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(total)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Balance:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(balance)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createInfoRowHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.text("REQUISITIONER").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("TERM").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Customer PO#").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.newRow();
        String purchaser = "";
        if (fSalesOrder.getSales() != null) {
            purchaser = (isEmpty(fSalesOrder.getSales().getFirstName()) ? "" : fSalesOrder.getSales().getFirstName())
                    + (isEmpty(fSalesOrder.getSales().getLastName()) ? "" : (" " + fSalesOrder.getSales().getLastName()));
        }
        String term = "";
        if (fSalesOrder.getCustomer().getCustomerTerm() != null) {
            term = fSalesOrder.getCustomer().getCustomerTerm().getCode();
        }
        String customerPONumber = "";
        if (fSalesOrder.getCustomerPoNumber() != null) {
            customerPONumber = fSalesOrder.getCustomerPoNumber();
        }
        horizontalList.add(cmp.text(purchaser).setStyle(borderedStyle));
        horizontalList.add(cmp.text(term).setStyle(borderedStyle));
        horizontalList.add(cmp.text(customerPONumber).setStyle(borderedStyle));
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createServiceHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        if (fSalesOrder.getService() != null && !fSalesOrder.getService().getServiceEntries().isEmpty()) {
            horizontalList.add(cmp.text("Tech").setFixedWidth(60));
            horizontalList.add(cmp.text("Service Code").setFixedWidth(80));
            horizontalList.add(cmp.text("Time").setFixedWidth(105));
            horizontalList.add(cmp.text("Note"));
            horizontalList.newRow();
            fSalesOrder.getService().getServiceEntries().forEach(se -> {
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
        horizontalList.add(cmp.horizontalList().setStyle(stl.style(1)).add(cmp.hListCell(createSumGroup()).heightFixedOnTop()));
        horizontalList.add(cmp.horizontalList(cmp.verticalGap(8), cmp.text(note).setStyle(leftColumnStyle)));
        if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
            horizontalList.add(cmp.horizontalList(cmp.verticalGap(8), cmp.text(Config.getStore().getSalesOrderMessage()).setStyle(Templates.leftColumnStyle)));
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_SERVICE) {
            horizontalList.add(cmp.horizontalList(cmp.verticalGap(8), cmp.text(Config.getStore().getServiceOrderMessage()).setStyle(Templates.leftColumnStyle)));
        }
        return horizontalList;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("yourSKU", "description", "qty", "price");
        List<SalesOrderEntry> list = fSalesOrder.getSalesOrderEntries().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                .collect(Collectors.toList());
        list.forEach((p) -> {
            String description;
            BigDecimal price;
            if (p.getComponentFlag() != null && p.getComponentFlag()) {
                price = null;
            } else {
                price = p.getPrice();
            }
            if (p.getLineNote() == null) {
                description = getString(p.getItem().getDescription());
            } else {
                description = getString(p.getItem().getDescription()) + "\n" + p.getLineNote();
            }
            if (p.getSerialNumbers() != null & !p.getSerialNumbers().isEmpty()) {
                description = description + "\n";
                for (SerialNumber sn : p.getSerialNumbers()) {
                    description = description + getString(sn.getSerialNumber()) + "; ";
                }
            }
            description = description.trim();
            if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                description = description.substring(0, description.length() - 1);
            }
            dataSource.add(p.getItem().getItemLookUpCode(), description, p.getQuantity(), price);
        });
        return dataSource;
    }

    private String getOrderType() {
        String type = "";
        if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_QUOTE)) {
            type = "Quote";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_ORDER)) {
            type = "Order";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_SERVICE)) {
            type = "Service";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
            type = "Invoice";
        }
        return type;
    }
}
