package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Customer;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createCustomerComponent;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.List;
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

public class AccountReceivablePaymentLayout {

    private final Customer fCustomer;
    private final List<AccountReceivable> fList;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public AccountReceivablePaymentLayout(List<AccountReceivable> list) {
        this.fList = list;
        this.fCustomer = fList.get(0).getCustomer();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> invoiceNumberColumn = col.column("Invoice #", "invoiceNumber", type.stringType()).setStyle(leftColumnStyle).setFixedWidth(55);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<String> dateDueColumn = col.column("Due", "dateDue", type.stringType()).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<String> typeColumn = col.column("Type", "type", type.stringType()).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "originalAmount", decimalType).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> discountColumn = col.column("Discount", "discountAmount", decimalType).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("This Payment", "amount", decimalType).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> balanceColumn = col.column("Balance", "balance", decimalType).setStyle(rightColumnStyle).setFixedWidth(75);

        AggregationSubtotalBuilder<BigDecimal> originalAmountSum = sbt.sum(originalAmountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> amountSum = sbt.sum(amountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> balanceSum = sbt.sum(balanceColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(invoiceNumberColumn, dateInvoicedColumn, dateDueColumn, typeColumn, originalAmountColumn, discountColumn, amountColumn, balanceColumn)
                .title(createTitleComponent("AR Payment Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createAccountComponent())),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(20).add(cmp.hListCell(createCustomerComponent(fCustomer, "Bill To")).heightFixedOnTop()),
                        cmp.verticalGap(5))
                .subtotalsAtSummary(originalAmountSum, amountSum, balanceSum)
                .summary(cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("This Payment:", amountSum),
                        createSumComponent("Total Paid:", amountSum),
                        createSumComponent("Balance:", balanceSum),
                        cmp.horizontalList(cmp.verticalGap(8)))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createAccountComponent() {
        String i = "Account #:  " + getString(fCustomer.getAccountNumber()) + "\n"
                + "Register: " + getString(Config.getStation().getId()) + "\n"
                + "By: " + getString(Config.getEmployee().getNameOnSalesOrder());
        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("invoiceNumber", "dateInvoiced", "dateDue", "type", "originalAmount", "discountAmount", "amount", "balance");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String dateInvoiced = "";
                if (p.getDateProcessed() != null) {
                    dateInvoiced = getString(p.getDateProcessed());
                }
                String dateDue = "";
                if (p.getDateDue() != null) {
                    dateDue = getString(p.getDateDue());
                }
                BigDecimal paymentAmount = BigDecimal.ZERO;
                if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    paymentAmount = p.getPaidAmount();
                } else if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                    paymentAmount = p.getPaidAmount().negate();
                }
                BigDecimal balanceAmount = BigDecimal.ZERO;
                if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    balanceAmount = p.getBalanceAmount();
                } else if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                    balanceAmount = p.getBalanceAmount().negate();
                }
                BigDecimal totalAmount = BigDecimal.ZERO;
                if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    totalAmount = p.getTotalAmount();
                } else if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                    totalAmount = p.getTotalAmount().negate();
                }
                dataSource.add(p.getInvoiceNumber().toString(), dateInvoiced, dateDue, p.getAccountReceivableType(), totalAmount, p.getDiscountAmount(), paymentAmount, balanceAmount);
            });
        }
        return dataSource;
    }
}
