package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.Store;
import static com.salesliant.report.Templates.addAttributes;
import static com.salesliant.report.Templates.borderedStyle;
import static com.salesliant.report.Templates.columnTitleStyle;
import static com.salesliant.report.Templates.createCompanyAddressComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.createVoidBackgroundComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class VoidedSalesOrderLayout {

    private final SalesOrder fSalesOrder;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal taxTotal = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal deposit = BigDecimal.ZERO;
    private String note = "";
    private final Store fStore;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public VoidedSalesOrderLayout(SalesOrder salesOrder) {
        this.fSalesOrder = salesOrder;
        fStore = Config.getStore();
        fSalesOrder.getSalesOrderEntries().stream().forEach((p) -> {
            subTotal = subTotal.add(p.getPrice().multiply(p.getQuantity()));
        });
        if (fSalesOrder.getTaxAmount() != null) {
            taxTotal = fSalesOrder.getTaxAmount();
        }
        if (fSalesOrder.getTotal() != null) {
            total = fSalesOrder.getTotal();
        }
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

        StyleBuilder columnStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
        StyleBuilder subtotalStyle = stl.style(columnStyle).bold();

        TextColumnBuilder<String> yourSKUColumn = col.column("SKU", "yourSKU", type.stringType()).setFixedWidth(70);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setFixedWidth(250);
        TextColumnBuilder<BigDecimal> quantityColumn = col.column("QTY", "qty", decimalType).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("PRICE", "price", decimalType);
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("TOTAL").setDataType(decimalType);

        AggregationSubtotalBuilder<BigDecimal> priceSum = sbt.sum(priceColumn).setLabel("SUBTOTAL:").setLabelStyle(Templates.boldStyle);
        priceSum.setStyle(columnStyle);

        report.setTemplate(Templates.reportTemplate)
                .setColumnStyle(columnStyle)
                .setSubtotalStyle(subtotalStyle)
                .setBackgroundBackgroundComponent(createVoidBackgroundComponent())
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
                .summary(
                        cmp.line(),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(1)).add(
                                cmp.hListCell(createSumGroup()).heightFixedOnTop()),
                        cmp.horizontalList(cmp.verticalGap(8), cmp.text(note).setStyle(Templates.bold12CenteredStyle)))
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(30).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createInvoiceComponent() {
        String accountNumber = "";
        if (fSalesOrder.getCustomer() != null && fSalesOrder.getCustomer().getAccountNumber() != null) {
            accountNumber = fSalesOrder.getCustomer().getAccountNumber();
        }
        String i = "Reference #:  " + getString(fSalesOrder.getSalesOrderNumber()) + "\n"
                + "Customer Account #:  " + accountNumber;
        if (fSalesOrder.getDateOrdered() != null) {
            i = i + "\n" + "DATE:  " + getString(fSalesOrder.getDateOrdered());
        }

        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    private ComponentBuilder<?, ?> createCustomerComponent(String label) {

        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        String c = "";
        c = c + (!isEmpty(fSalesOrder.getCustomer().getFirstName()) ? fSalesOrder.getCustomer().getFirstName() : "")
                + (!isEmpty(fSalesOrder.getCustomer().getFirstName()) ? " " : "")
                + (!isEmpty(fSalesOrder.getCustomer().getLastName()) ? fSalesOrder.getCustomer().getLastName() : "");
        c = c + (!isEmpty(fSalesOrder.getCustomer().getCompany()) ? "\n" + fSalesOrder.getCustomer().getCompany() : "");
        c = c + (!isEmpty(fSalesOrder.getCustomer().getAddress1()) ? "\n" + fSalesOrder.getCustomer().getAddress1() : "");
        c = c + (!isEmpty(fSalesOrder.getCustomer().getAddress2()) ? "\n" + fSalesOrder.getCustomer().getAddress2() : "");
        String csz = (!isEmpty(fSalesOrder.getCustomer().getCity()) ? fSalesOrder.getCustomer().getCity() : "")
                + (!isEmpty(fSalesOrder.getCustomer().getCity()) ? ", " : "")
                + (!isEmpty(fSalesOrder.getCustomer().getState()) ? fSalesOrder.getCustomer().getState() : "")
                + (!isEmpty(fSalesOrder.getCustomer().getState()) ? " " : "")
                + (!isEmpty(fSalesOrder.getCustomer().getPostCode()) ? fSalesOrder.getCustomer().getPostCode() : "");
        c = c + (!isEmpty(csz) ? csz : "");
        if (fSalesOrder.getCustomer().getCountry() != null) {
            c = c + (!isEmpty(fSalesOrder.getCustomer().getCountry().getIsoCode3()) ? "\n" + fSalesOrder.getCustomer().getCountry().getIsoCode3() : "");
        }
        c = c + (!isEmpty(fSalesOrder.getCustomer().getPhoneNumber()) ? "\n" + fSalesOrder.getCustomer().getPhoneNumber() : "");
        addAttributes(aList, c);
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createShipToComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        String c = "";
        if (fSalesOrder.getShipTo() == null) {
            c = c + (!isEmpty(fSalesOrder.getCustomer().getFirstName()) ? fSalesOrder.getCustomer().getFirstName() : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getFirstName()) ? " " : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getLastName()) ? fSalesOrder.getCustomer().getLastName() : "");
            c = c + (!isEmpty(fSalesOrder.getCustomer().getCompany()) ? "\n" + fSalesOrder.getCustomer().getCompany() : "");
            c = c + (!isEmpty(fSalesOrder.getCustomer().getAddress1()) ? "\n" + fSalesOrder.getCustomer().getAddress1() : "");
            c = c + (!isEmpty(fSalesOrder.getCustomer().getAddress2()) ? "\n" + fSalesOrder.getCustomer().getAddress2() : "");
            String csz = (!isEmpty(fSalesOrder.getCustomer().getCity()) ? fSalesOrder.getCustomer().getCity() : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getCity()) ? ", " : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getState()) ? fSalesOrder.getCustomer().getState() : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getState()) ? " " : "")
                    + (!isEmpty(fSalesOrder.getCustomer().getPostCode()) ? fSalesOrder.getCustomer().getPostCode() : "");
            c = c + (!isEmpty(csz) ? csz : "");
            if (fSalesOrder.getCustomer().getCountry() != null) {
                c = c + (!isEmpty(fSalesOrder.getCustomer().getCountry().getIsoCode3()) ? "\n" + fSalesOrder.getCustomer().getCountry().getIsoCode3() : "");
            }
            c = c + (!isEmpty(fSalesOrder.getCustomer().getPhoneNumber()) ? "\n" + fSalesOrder.getCustomer().getPhoneNumber() : "");
        } else {
            c = c + (!isEmpty(fSalesOrder.getShipTo().getContactName()) ? fSalesOrder.getShipTo().getContactName() : "");
            c = c + (!isEmpty(fSalesOrder.getShipTo().getCompany()) ? "\n" + fSalesOrder.getShipTo().getCompany() : "");
            c = c + (!isEmpty(fSalesOrder.getShipTo().getAddress1()) ? "\n" + fSalesOrder.getShipTo().getAddress1() : "");
            c = c + (!isEmpty(fSalesOrder.getShipTo().getAddress2()) ? "\n" + fSalesOrder.getShipTo().getAddress2() : "");
            String csz = (!isEmpty(fSalesOrder.getShipTo().getCity()) ? fSalesOrder.getShipTo().getCity() : "")
                    + (!isEmpty(fSalesOrder.getShipTo().getCity()) ? ", " : "")
                    + (!isEmpty(fSalesOrder.getShipTo().getState()) ? fSalesOrder.getShipTo().getState() : "")
                    + (!isEmpty(fSalesOrder.getShipTo().getState()) ? " " : "")
                    + (!isEmpty(fSalesOrder.getShipTo().getPostCode()) ? fSalesOrder.getShipTo().getPostCode() : "");
            c = c + (!isEmpty(csz) ? csz : "");
            if (fSalesOrder.getShipTo().getCountry() != null) {
                c = c + (!isEmpty(fSalesOrder.getShipTo().getCountry().getIsoCode3()) ? "\n" + fSalesOrder.getShipTo().getCountry().getIsoCode3() : "");
            }
            c = c + (!isEmpty(fSalesOrder.getShipTo().getPhoneNumber()) ? "\n" + fSalesOrder.getShipTo().getPhoneNumber() : "");
        }
        addAttributes(aList, c);
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
        horizontalList.add(cmp.filler(), cmp.text("Total:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(total)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Balance:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(balance)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createInfoRowHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.text("REQUISITIONER").setStyle(columnTitleStyle));
        horizontalList.add(cmp.text("TERM").setStyle(columnTitleStyle));
        horizontalList.add(cmp.text("Customer PO#").setStyle(columnTitleStyle));
        horizontalList.newRow();
        String purchaser = "";
        if (fSalesOrder.getSales() != null) {
            purchaser = fSalesOrder.getSales().getFirstName() + " " + fSalesOrder.getSales().getLastName();
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

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("yourSKU", "description", "qty", "price");
        fSalesOrder.getSalesOrderEntries().stream().forEach((p) -> {
            String description;
            if (p.getLineNote() == null) {
                description = getString(p.getItem().getDescription());
            } else {
                description = getString(p.getItem().getDescription()) + "\n" + p.getLineNote();
            }
            description = description.trim();
            dataSource.add(p.getItem().getItemLookUpCode(), description, p.getQuantity(), p.getPrice());
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
