package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Customer;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createCustomerComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
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

public class AccountReceivableCreditStatementLayout {

    private final Customer fCustomer;
    private final List<AccountReceivable> fList;
    private final StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

    public AccountReceivableCreditStatementLayout(List<AccountReceivable> list) {
        this.fList = list;
        this.fCustomer = fList.get(0).getCustomer();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> invoiceNumberColumn = col.column("Invoice #", "invoiceNumber", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(295);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(120);
        TextColumnBuilder<BigDecimal> creditAmountColumn = col.column("Credit", "creditAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(120);

        AggregationSubtotalBuilder<BigDecimal> creditAmountColumnSum = sbt.sum(creditAmountColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(invoiceNumberColumn, dateInvoicedColumn, creditAmountColumn)
                .title(createTitleComponent("Store Credit Statement"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createAccountComponent())),
                        cmp.verticalGap(5),
                        cmp.horizontalList().setStyle(stl.style(10)).setGap(20).add(cmp.hListCell(createCustomerComponent(fCustomer, "Customer")).heightFixedOnTop()),
                        cmp.verticalGap(5))
                .sortBy(asc(dateInvoicedColumn))
                .subtotalsAtSummary(creditAmountColumnSum)
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
        DRDataSource dataSource = new DRDataSource("invoiceNumber", "dateInvoiced", "creditAmount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String dateInvoiced = "";
                if (p.getDateProcessed() != null) {
                    dateInvoiced = getString(p.getDateProcessed());
                }
                dataSource.add(p.getInvoiceNumber().toString(), dateInvoiced, p.getBalanceAmount());
            });
        }
        return dataSource;
    }
}
