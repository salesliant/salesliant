package com.salesliant.report;

import com.salesliant.entity.GiftCertificate;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.getString;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.subtotalBoldStyle;

public class GiftCertificateTransactionReportLayout {

    private final GiftCertificate fGiftCertificate;
    private BigDecimal originalAmount = BigDecimal.ZERO;
    private BigDecimal balanceAmount = BigDecimal.ZERO;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public GiftCertificateTransactionReportLayout(GiftCertificate giftCertificate) {
        this.fGiftCertificate = giftCertificate;
        originalAmount = fGiftCertificate.getOriginalAmount();
        balanceAmount = fGiftCertificate.getBalance();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> typeColumn = col.column("Transaction Type", "transType", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(140);
        TextColumnBuilder<String> numberColumn = col.column("Number", "number", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(130);
        TextColumnBuilder<String> datePurchasedColumn = col.column("Date", "datePurchased", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(110);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(150);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(amountColumn).setStyle(subtotalBoldStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(typeColumn, numberColumn, datePurchasedColumn, amountColumn)
                .title(createTitleComponent("Gift Certificate Transaction"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createAccountComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(datePurchasedColumn))
                .subtotalsAtSummary(totalSum)
                .summary(cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("Original Amount:", originalAmount),
                        createSumComponent("Balance:", balanceAmount),
                        cmp.horizontalList(cmp.verticalGap(8)))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createAccountComponent() {
        String i = "Gift Certificate #:  " + getString(fGiftCertificate.getCode());
        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("transType", "number", "datePurchased", "originalAmount", "amount");
        if (fGiftCertificate != null && fGiftCertificate.getGiftCertificateTransactions() != null && !fGiftCertificate.getGiftCertificateTransactions().isEmpty()) {
            fGiftCertificate.getGiftCertificateTransactions().stream().forEach((p) -> {
                String trans;
                String number;
                if (p.getInvoice() != null) {
                    trans = "Invoice";
                    number = p.getInvoice().getInvoiceNumber().toString();
                } else if (p.getSalesOrder() != null) {
                    trans = "Order";
                    number = p.getSalesOrder().getSalesOrderNumber().toString();
                } else {
                    trans = "";
                    number = "";
                }
                dataSource.add(trans, number, getDateDateFormat().format(p.getDateUpdated()), p.getAmount());
            });
        }
        return dataSource;
    }
}
