package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;
import static com.salesliant.report.Templates.bold18CenteredStyle;
import static com.salesliant.report.Templates.bold22CenteredStyle;
import static com.salesliant.report.Templates.borderedStyle;
import static com.salesliant.report.Templates.columnInvoiceReportTitleStyle;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.leftTopColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rightTopColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getDateString;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class PurchaseOrderLayout {

    private final PurchaseOrder fPurchaseOrder;
    private final Store fStore;

    public PurchaseOrderLayout(PurchaseOrder purchaseOrder) {
        this.fPurchaseOrder = purchaseOrder;
        fStore = Config.getStore();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> vendorSKUColumn = col.column("VENDOR SKU", "vendorSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> yourSKUColumn = col.column("YOUR SKU", "yourSKU", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(219);
        TextColumnBuilder<BigDecimal> quantityOrderedColumn = col.column("Ordered", "qtyOrdered", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("PRICE", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityOrderedColumn).setTitle("Total").setDataType(decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(55);

        report.setTemplate(Templates.invoiceReportTemplate)
                //                .setColumnStyle(columnStyle)
                .setSubtotalStyle(subtotalStyle)
                .columns(vendorSKUColumn, yourSKUColumn, descriptionColumn, quantityOrderedColumn, unitPriceColumn, priceColumn)
                .title(createTitleComponent(fStore.getStoreName(), "Purchase Order"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent())),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(10).add(
                                cmp.hListCell(createVendorComponent("Vendor")).heightFixedOnTop(),
                                cmp.hListCell(createShipToComponent("Ship To")).heightFixedOnTop()),
                        createInfoRowHorizontalList(),
                        cmp.verticalGap(5))
                .summary(
                        cmp.horizontalList(cmp.verticalGap(8), cmp.text(fPurchaseOrder.getNote()).setStyle(leftColumnStyle)),
                        cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("Subtotal:", zeroIfNull(fPurchaseOrder.getTotal())),
                        createSumComponent("Freight Invoiced:", zeroIfNull(fPurchaseOrder.getFreightInvoicedAmount())),
                        createSumComponent("Freight PrePaid:", zeroIfNull(fPurchaseOrder.getFreightPrePaidAmount())),
                        createSumComponent("Tax On Order:", zeroIfNull(fPurchaseOrder.getTaxOnOrderAmount())),
                        createSumComponent("Tax On Freight:", zeroIfNull(fPurchaseOrder.getTaxOnFreightAmount())),
                        createSumComponent("Total:", zeroIfNull(fPurchaseOrder.getTotal()).add(zeroIfNull(fPurchaseOrder.getFreightInvoicedAmount()).add(zeroIfNull(fPurchaseOrder.getFreightPrePaidAmount())))
                                .add(zeroIfNull(fPurchaseOrder.getTaxOnOrderAmount())).add(zeroIfNull(fPurchaseOrder.getTaxOnFreightAmount()))))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(30).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createTitleComponent(String company, String label) {
        HorizontalListBuilder list = cmp.horizontalList(cmp.verticalList(cmp.text(company).setStyle(bold22CenteredStyle.italic()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))).setFixedWidth(300);
        return cmp.horizontalList().add(list, cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).newRow().add(cmp.line());
    }

    private ComponentBuilder<?, ?> createCompanyComponent() {
        String c = "";
        if (fStore.getAddress1() != null && !fStore.getAddress1().isEmpty()) {
            c = fStore.getAddress1();
        }
        if (fStore.getAddress2() != null && !fStore.getAddress2().isEmpty()) {
            c = c + "," + fStore.getAddress2();
        }
        c = c + "\n" + getString(fStore.getCity()) + ", " + getString(fStore.getState()) + " " + getString(fStore.getPostCode());
        if (fStore.getPhoneNumber() != null && !fStore.getPhoneNumber().isEmpty() && fStore.getFaxNumber() == null) {
            c = c + "\n" + "Phone:" + fStore.getPhoneNumber();
        } else if (fStore.getPhoneNumber() != null && !fStore.getPhoneNumber().isEmpty() && fStore.getFaxNumber() != null && !fStore.getFaxNumber().isEmpty()) {
            c = c + "\n" + "Phone:" + fStore.getPhoneNumber() + ", " + "Fax:" + fStore.getFaxNumber();
        }
        return cmp.text(c).setStyle(stl.style().setFontSize(7).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    private ComponentBuilder<?, ?> createVendorComponent(String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        String c = "";
        if (fPurchaseOrder.getVendorName() != null && !fPurchaseOrder.getVendorName().isEmpty()) {
            c = c + getString(fPurchaseOrder.getVendorName());
        }
        if (fPurchaseOrder.getVendor().getAddress1() != null && !fPurchaseOrder.getVendor().getAddress1().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrder.getVendor().getAddress1());
        }
        if (fPurchaseOrder.getVendor().getAddress2() != null && !fPurchaseOrder.getVendor().getAddress2().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrder.getVendor().getAddress2());
        }
        c = c + "\n" + getString(fPurchaseOrder.getVendor().getCity()) + ", " + getString(fPurchaseOrder.getVendor().getState()) + " " + getString(fPurchaseOrder.getVendor().getPostCode());
        if (fPurchaseOrder.getVendor().getCountry() != null) {
            c = c + "\n" + getString(fPurchaseOrder.getVendor().getCountry().getIsoCode3());
        }
        if (fPurchaseOrder.getVendor().getPhoneNumber() != null && !fPurchaseOrder.getVendor().getPhoneNumber().isEmpty()) {
            c = c + "\n" + getString(fPurchaseOrder.getVendor().getPhoneNumber());
        }
        addAttributes(aList, c);
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    private ComponentBuilder<?, ?> createShipToComponent(String label) {
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addAttributes(list, getString(fPurchaseOrder.getShipToAddress()));
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

        horizontalList.add(cmp.text(getString(fPurchaseOrder.getPurchaseOrderNumber())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrder.getVendor().getVendorCode())).setStyle(borderedStyle));
        if (fPurchaseOrder.getVendorShippingService() != null && fPurchaseOrder.getVendorShippingService().getCode() != null) {
            horizontalList.add(cmp.text(getString(fPurchaseOrder.getVendorShippingService().getCode())).setStyle(borderedStyle));
        } else {
            horizontalList.add(cmp.text(getString("")).setStyle(borderedStyle));
        }
        if (fPurchaseOrder.getVendorTerm() != null && fPurchaseOrder.getVendorTerm().getCode() != null) {
            horizontalList.add(cmp.text(getString(fPurchaseOrder.getVendorTerm().getCode())).setStyle(borderedStyle));
        } else {
            horizontalList.add(cmp.text(getString("")).setStyle(borderedStyle));
        }
        horizontalList.add(cmp.text(getDateString(fPurchaseOrder.getDatePurchased())).setStyle(borderedStyle));
        horizontalList.add(cmp.text(getString(fPurchaseOrder.getEmployeePurchasedName())).setStyle(borderedStyle));
        return horizontalList;
    }

    private void addAttributes(HorizontalListBuilder list, String value) {
        if (value != null) {
            list.add(cmp.text(value).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))).newRow();
        }
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("vendorSKU", "yourSKU", "description", "qtyOrdered", "cost");
        if (fPurchaseOrder.getPurchaseOrderEntries() != null && !fPurchaseOrder.getPurchaseOrderEntries().isEmpty()) {
            fPurchaseOrder.getPurchaseOrderEntries().stream()
                    .sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                    .forEachOrdered(e -> {
                        if (e.getVendorItemLookUpCode() != null && e.getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                            dataSource.add(null, e.getItemLookUpCode(), null, null, null);
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
                            dataSource.add(e.getVendorItemLookUpCode(), e.getItemLookUpCode(), description, e.getQuantityOrdered(), e.getCost());
                        }
                    });
        }
        return dataSource;
    }
}
