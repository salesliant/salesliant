package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Customer;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createCustomerComponent;
import static com.salesliant.report.Templates.createSubstractComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.Date;
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

public class AccountReceivableStatementReport {

    private final Customer fCustomer;
    private final List<AccountReceivable> fList;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public AccountReceivableStatementReport(List<AccountReceivable> list) {
        this.fList = list;
        this.fCustomer = fList.get(0).getCustomer();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> invoiceNumberColumn = col.column("Invoice #", "invoiceNumber", type.stringType()).setStyle(leftColumnStyle).setFixedWidth(55);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setStyle(rightColumnStyle).setFixedWidth(60);
        TextColumnBuilder<String> dateDueColumn = col.column("Due", "dateDue", type.stringType()).setStyle(rightColumnStyle).setFixedWidth(60);
        TextColumnBuilder<Integer> agingColumn = col.column("Aging", "aging", type.integerType()).setStyle(rightColumnStyle).setFixedWidth(40);
        TextColumnBuilder<String> poColumn = col.column("PO #", "po", type.stringType()).setStyle(leftColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "originalAmount", decimalType).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> paidAmountColumn = col.column("Paid", "paidAmount", decimalType).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> discountAmountColumn = col.column("Discount", "discountAmount", decimalType).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> balanceAmountColumn = col.column("Balance", "balance", decimalType).setStyle(rightColumnStyle).setFixedWidth(63);

        AggregationSubtotalBuilder<BigDecimal> paidAmountSum = sbt.sum(paidAmountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> balanceAmountSum = sbt.sum(balanceAmountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> discountAmountSum = sbt.sum(discountAmountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> originalAmountSum = sbt.sum(originalAmountColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(invoiceNumberColumn, poColumn, dateInvoicedColumn, dateDueColumn, agingColumn, originalAmountColumn, paidAmountColumn, discountAmountColumn, balanceAmountColumn)
                .title(createTitleComponent("Statement"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createAccountComponent())),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(20).add(cmp.hListCell(createCustomerComponent(fCustomer, "Bill To")).heightFixedOnTop()),
                        cmp.verticalGap(5))
                .sortBy(asc(agingColumn))
                .subtotalsAtSummary(originalAmountSum, paidAmountSum, discountAmountSum, balanceAmountSum)
                .summary(cmp.line(),
                        cmp.verticalGap(5),
                        createSubstractComponent("Balance:", balanceAmountSum, paidAmountSum),
                        cmp.horizontalList(cmp.verticalGap(8)))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createAccountComponent() {
        String i = "Account #:  " + getString(fCustomer.getAccountNumber());
        return cmp.text(i).setStyle(shippingStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("invoiceNumber", "po", "dateInvoiced", "dateDue", "aging", "originalAmount", "paidAmount", "discountAmount", "balance");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String dateInvoiced = "";
                if (p.getDateProcessed() != null) {
                    dateInvoiced = getString(p.getDateProcessed());
                }
                String dateDue = "";
                int diffInDays = 0;
                if (p.getDateDue() != null) {
                    dateDue = getString(p.getDateDue());
                    diffInDays = (int) (((new Date()).getTime() - p.getDateDue().getTime()) / (1000 * 60 * 60 * 24));
                }
                BigDecimal total;
                if (p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    total = p.getTotalAmount();
                } else {
                    total = p.getTotalAmount().negate();
                }
                BigDecimal balance;
                if (p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    balance = p.getBalanceAmount();
                } else {
                    balance = p.getBalanceAmount().negate();
                }
                String po = "";
                if (p.getInvoice() != null && p.getInvoice().getCustomerPoNumber() != null && !p.getInvoice().getCustomerPoNumber().isEmpty()) {
                    po = p.getInvoice().getCustomerPoNumber();
                }
                dataSource.add(p.getInvoiceNumber().toString(), po, dateInvoiced, dateDue, diffInDays, total, p.getPaidAmount(), p.getDiscountAmount(), balance);
            });
        }
        return dataSource;
    }
}
