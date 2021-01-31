package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;
import static com.salesliant.report.Templates.addAttributes;
import static com.salesliant.report.Templates.borderedStyle;
import static com.salesliant.report.Templates.columnInvoiceReportTitleStyle;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.createCompanyAddressComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.leftTopColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rightTopColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getDateString;
import static com.salesliant.util.BaseUtil.getString;

public class PurchaseOrderHistoryLayout {

    private final PurchaseOrderHistory fPurchaseOrderHistory;
    private final Store fStore;

    public PurchaseOrderHistoryLayout(PurchaseOrderHistory purchaseOrder) {
        this.fPurchaseOrderHistory = purchaseOrder;
        fStore = Config.getStore();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> vendorSKUColumn = col.column("VENDOR SKU", "vendorSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> yourSKUColumn = col.column("YOUR SKU", "yourSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(60);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(188);
        TextColumnBuilder<BigDecimal> quantityOrderedColumn = col.column("Ordered", "qtyOrdered", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> quantityReceivedColumn = col.column("Received", "qtyReceived", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("PRICE", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityOrderedColumn).setTitle("Total").setDataType(decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(55);

        report.setTemplate(Templates.invoiceReportTemplate)
                .setSubtotalStyle(subtotalStyle)
                .columns(vendorSKUColumn, yourSKUColumn, descriptionColumn, quantityOrderedColumn, quantityReceivedColumn, unitPriceColumn, priceColumn)
                .title(createTitleComponent(fStore.getStoreName(), "Purchase Order History"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyAddressComponent()),
                                cmp.hListCell(createPurchaseOrderComponent())),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(10).add(
                                cmp.hListCell(createVendorComponent("Vendor")).heightFixedOnTop(),
                                cmp.hListCell(createShipToComponent("Ship To")).heightFixedOnTop()),
                        createInfoRowHorizontalList(),
                        cmp.verticalGap(5))
                .summary(
                        cmp.horizontalList(cmp.verticalGap(8), cmp.text(fPurchaseOrderHistory.getNote()).setStyle(leftColumnStyle)),
                        cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("Subtotal:", fPurchaseOrderHistory.getTotal()),
                        createSumComponent("Freight Invoiced:", fPurchaseOrderHistory.getFreightInvoicedAmount()),
                        createSumComponent("Freight PrePaid:", fPurchaseOrderHistory.getFreightPrePaidAmount()),
                        createSumComponent("Tax On Order:", fPurchaseOrderHistory.getTaxOnOrderAmount()),
                        createSumComponent("Tax On Freight:", fPurchaseOrderHistory.getTaxOnFreightAmount()),
                        createSumComponent("Total:", fPurchaseOrderHistory.getTotal().add(fPurchaseOrderHistory.getFreightInvoicedAmount().add(fPurchaseOrderHistory.getFreightPrePaidAmount()))
                                .add(fPurchaseOrderHistory.getTaxOnOrderAmount()).add(fPurchaseOrderHistory.getTaxOnFreightAmount())))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(30).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createPurchaseOrderComponent() {
        String i = "Received Date:  " + getString(fPurchaseOrderHistory.getDateReceived()) + "\n"
                + "Invoice Date:  " + getString(fPurchaseOrderHistory.getDateInvoiced()) + "\n"
                + "Invoice Number:  " + getString(fPurchaseOrderHistory.getVendorInvoiceNumber());
        return cmp.text(i).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    private ComponentBuilder<?, ?> createVendorComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        String c = "";
        if (fPurchaseOrderHistory.getVendorName() != null && !fPurchaseOrderHistory.getVendorName().isEmpty()) {
            c = c + getString(fPurchaseOrderHistory.getVendorName());
        }
        if (fPurchaseOrderHistory.getVendorAddress1() != null && !fPurchaseOrderHistory.getVendorAddress1().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrderHistory.getVendorAddress1());
        }
        if (fPurchaseOrderHistory.getVendorAddress2() != null && !fPurchaseOrderHistory.getVendorAddress2().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrderHistory.getVendorAddress2());
        }
        c = c + "\n" + getString(fPurchaseOrderHistory.getVendorCity()) + ", " + getString(fPurchaseOrderHistory.getVendorState()) + " " + getString(fPurchaseOrderHistory.getVendorPostCode());
        if (fPurchaseOrderHistory.getVendorCountry() != null && !fPurchaseOrderHistory.getVendorCountry().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrderHistory.getVendorCountry());
        }
        if (fPurchaseOrderHistory.getVendorPhoneNumber() != null && !fPurchaseOrderHistory.getVendorPhoneNumber().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrderHistory.getVendorPhoneNumber());
        }
        addAttributes(aList, c, 9);
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createShipToComponent(String label) {
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addAttributes(list, getString(fPurchaseOrderHistory.getShipToAddress()), 9);
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), list);
    }

    private ComponentBuilder<?, ?> createInfoRowHorizontalList() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.text("PO Number").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Account#").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Ship Via").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Term").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Date Ordered").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.add(cmp.text("Purchaser").setStyle(columnInvoiceReportTitleStyle));
        horizontalList.newRow();

        horizontalList.add(cmp.text(getString(fPurchaseOrderHistory.getPurchaseOrderNumber())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrderHistory.getVendorCode())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrderHistory.getVendorShippingServiceCode())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrderHistory.getVendorTermCode())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(getDateString(fPurchaseOrderHistory.getDatePurchased()))).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrderHistory.getEmployeePurchasedName())).setStyle(borderedStyle));
        return horizontalList;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("vendorSKU", "yourSKU", "description", "qtyOrdered", "qtyReceived", "cost");
        if (fPurchaseOrderHistory.getPurchaseOrderHistoryEntries() != null && !fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().isEmpty()) {
            fPurchaseOrderHistory.getPurchaseOrderHistoryEntries()
                    .stream()
                    .sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                    .forEachOrdered(e -> {
                        if (e.getVendorItemLookUpCode() != null && e.getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                            dataSource.add(null, e.getItemLookUpCode(), null, null, null, null);
                        } else {
                            String description;
                            if (e.getLineNote() == null) {
                                description = getString(e.getItemDescription());
                            } else {
                                description = getString(e.getItemDescription()) + "\n" + e.getLineNote();
                            }
                            if (e.getSerialNumbers() != null & !e.getSerialNumbers().isEmpty()) {
                                description = description + "\n";
                                for (SerialNumber sn : e.getSerialNumbers()) {
                                    description = description + getString(sn.getSerialNumber()) + "; ";
                                }
                            }
                            description = description.trim();
                            if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                                description = description.substring(0, description.length() - 1);
                            }
                            dataSource.add(e.getVendorItemLookUpCode(), e.getItemLookUpCode(), description, e.getQuantityOrdered(), e.getQuantityReceived(), e.getCost());
                        }
                    });
        }
        return dataSource;
    }
}
