package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class AccountReceivablePaymentReportLayout {

    private final List<AccountReceivable> fList;
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;

    public AccountReceivablePaymentReportLayout(List<AccountReceivable> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Customer", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(190);
        TextColumnBuilder<String> accountColumn = col.column("Account", "account", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> invoiceColumn = col.column("Transaction", "invoice", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(100);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateProcessed", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);

        AggregationSubtotalBuilder<BigDecimal> paymentSum = sbt.sum(originalAmountColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(dateInvoicedColumn, nameColumn, accountColumn, invoiceColumn, originalAmountColumn)
                .title(createTitleComponent("AR Payment Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .sortBy(desc(dateInvoicedColumn))
                .subtotalsAtSummary(paymentSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("dateProcessed", "name", "account", "invoice", "amount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String account = "";
                if (p.getCustomer() != null && p.getCustomer().getAccountNumber() != null) {
                    account = p.getCustomer().getAccountNumber();
                }
                String name = "";
                if (p.getCustomer().getCompany() != null) {
                    name = p.getCustomer().getCompany();
                } else if (p.getCustomer().getFirstName() == null && p.getCustomer().getLastName() == null) {
                    name = p.getCustomer().getAddress1();
                } else {
                    name = name + (!isEmpty(p.getCustomer().getFirstName()) ? p.getCustomer().getFirstName() : "")
                            + (!isEmpty(p.getCustomer().getFirstName()) ? " " : "")
                            + (!isEmpty(p.getCustomer().getLastName()) ? p.getCustomer().getLastName() : "");
                }
                BigDecimal totalAmount = BigDecimal.ZERO;
                if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT)) {
                    totalAmount = p.getTotalAmount();
                } else if (p.getAccountReceivableType() != null && p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                    totalAmount = p.getTotalAmount().negate();
                }

                dataSource.add(getString(p.getDateProcessed()), name, account, getString(p.getAccountReceivableNumber()), totalAmount);
            });
        }
        return dataSource;
    }
}
