package com.salesliant.report;

import com.salesliant.entity.AccountPayableBatch;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class AccountPayableBatchLayout {

    private final AccountPayableBatch fAccountPayableBatch;

    public AccountPayableBatchLayout(AccountPayableBatch accountPayableBatch) {
        this.fAccountPayableBatch = accountPayableBatch;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Vendor", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(200);
        TextColumnBuilder<String> typeColumn = col.column("Type", "type", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);
        TextColumnBuilder<String> invoiceNumberColumn = col.column("Invoice #", "invoiceNumber", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(64);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);
        TextColumnBuilder<String> dateDueColumn = col.column("Due", "dateDue", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> discountColumn = col.column("Discount", "discount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> paidColumn = col.column("Paid", "paid", decimalType).setTitleStyle(rightColumnTitleStyle)
                .setStyle(rightColumnStyle.setVerticalTextAlignment(VerticalTextAlignment.TOP)).setFixedWidth(70);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(paidColumn).setStyle(subtotalStyle).setLabel("Total");
        ColumnGroupBuilder nameGroup = grp.group(nameColumn);

        report.setTemplate(Templates.batchDetailReportTemplate)
                .title(createTitleComponent("AR Batch Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createPurchaseOrderComponent())),
                        cmp.verticalGap(10))
                .columns(nameColumn, typeColumn, invoiceNumberColumn, dateInvoicedColumn, dateDueColumn, amountColumn, discountColumn, paidColumn)
                .sortBy(asc(nameColumn))
                .groupBy(nameGroup).subtotalsAtFirstGroupFooter(sbt.sum(paidColumn))
                .subtotalsAtSummary(totalSum).setSubtotalStyle(subtotalStyle.setTopBorder(stl.pen1Point()))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createPurchaseOrderComponent() {
        String date = "Batch#:  " + getString(fAccountPayableBatch.getBatchNumber()) + "\n"
                + "Date:  " + getString(fAccountPayableBatch.getDatePaidOn()) + "\n";
        return cmp.text(date).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("name", "type", "invoiceNumber", "dateInvoiced", "dateDue", "amount", "discount", "paid");
        if (fAccountPayableBatch != null && !fAccountPayableBatch.getAccountPayableHistories().isEmpty()) {
            fAccountPayableBatch.getAccountPayableHistories().stream().forEach((p) -> {
                String type;
                if (p.getAccountPayableType() != null && p.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                    type = "Invoice";
                } else if (p.getAccountPayableType() != null && p.getAccountPayableType().equals(DBConstants.TYPE_APAR_CRE)) {
                    type = "Credit";
                } else {
                    type = "";
                }
                dataSource.add(p.getVendorName(), type, p.getVendorInvoiceNumber(), getString(p.getDateInvoiced()), getString(p.getDateDue()), p.getTotalAmount(), p.getDiscountAmount(), p.getPaidAmount());
            });
        }
        return dataSource;
    }
}
