package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rootStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;

public class AccountReceivableActivityReportLayout {

    private final List<AccountReceivable> fList;
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;

    public AccountReceivableActivityReportLayout(List<AccountReceivable> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        StyleBuilder columnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = stl.style(columnStyle).setFontSize(8).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        StyleBuilder subtotalStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8)).setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        TextColumnBuilder<String> nameColumn = col.column("Customer", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(185);
        TextColumnBuilder<String> accountColumn = col.column("Account", "account", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(85);
        TextColumnBuilder<String> invoiceColumn = col.column("Invoice", "invoice", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(60);
        TextColumnBuilder<String> typeColumn = col.column("Type", "type", type.stringType()).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "originalAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);

        AggregationSubtotalBuilder<BigDecimal> amountSum = sbt.sum(originalAmountColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .columns(dateInvoicedColumn, nameColumn, accountColumn, invoiceColumn, typeColumn, originalAmountColumn)
                .title(createTitleComponent("AR Activity Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .sortBy(desc(dateInvoicedColumn))
                .subtotalsAtSummary(amountSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("dateInvoiced", "name", "account", "invoice", "type", "originalAmount");
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
                String invoice = "";
                if (p.getInvoiceNumber() != null) {
                    invoice = p.getInvoiceNumber().toString();
                }
                String dateInvoiced = "";
                if (p.getDateProcessed() != null) {
                    dateInvoiced = getString(p.getDateProcessed());
                }

                dataSource.add(dateInvoiced, name, account, invoice, p.getAccountReceivableType(), p.getTotalAmount());
            });
        }
        return dataSource;
    }
}
