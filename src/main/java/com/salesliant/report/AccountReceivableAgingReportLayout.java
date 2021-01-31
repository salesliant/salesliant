package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class AccountReceivableAgingReportLayout {

    private final List<List<AccountReceivable>> fList;
    private final Date f0, f30, f60, f90;

    public AccountReceivableAgingReportLayout(List<List<AccountReceivable>> list) {
        this.fList = list;
        f0 = new Date();
        Calendar c30 = Calendar.getInstance();
        c30.setTime(new Date());
        c30.add(Calendar.DATE, -30);
        f30 = c30.getTime();
        Calendar c60 = Calendar.getInstance();
        c60.setTime(new Date());
        c60.add(Calendar.DATE, -60);
        f60 = c60.getTime();
        Calendar c90 = Calendar.getInstance();
        c90.setTime(new Date());
        c90.add(Calendar.DATE, -90);
        f90 = c90.getTime();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(160);
        TextColumnBuilder<String> accountColumn = col.column("Account", "account", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(60);
        TextColumnBuilder<BigDecimal> a30Column = col.column("1-30", "a30", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> a60Column = col.column("31-60", "a60", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> a90Column = col.column("61-90", "a90", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> o90Column = col.column("Over 90", "o90", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);
        TextColumnBuilder<BigDecimal> totalColumn = col.column("Total", "total", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);

        AggregationSubtotalBuilder<BigDecimal> a60Sum = sbt.sum(a60Column).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(totalColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> o90Sum = sbt.sum(o90Column).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> a90Sum = sbt.sum(a90Column).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> a30Sum = sbt.sum(a30Column).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .setSubtotalStyle(subtotalStyle)
                .columns(nameColumn, accountColumn, a30Column, a60Column, a90Column, o90Column, totalColumn)
                .title(createTitleComponent("AR Summary Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .subtotalsAtSummary(a30Sum, a60Sum, a90Sum, o90Sum, totalSum)
                .summary(cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("Total:", totalSum),
                        cmp.horizontalList(cmp.verticalGap(8)))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("name", "account", "a30", "a60", "a90", "o90", "total");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String name = "";
                String account = "";
                if (p.get(0).getCustomer() != null && p.get(0).getCustomer().getCompany() != null) {
                    name = p.get(0).getCustomer().getCompany();
                } else if (p.get(0).getCustomer() != null && p.get(0).getCustomer().getFirstName() == null && p.get(0).getCustomer().getLastName() == null) {
                    name = p.get(0).getCustomer().getAddress1();
                } else {
                    name = name + (!isEmpty(p.get(0).getCustomer().getFirstName()) ? p.get(0).getCustomer().getFirstName() : "")
                            + (!isEmpty(p.get(0).getCustomer().getFirstName()) ? " " : "")
                            + (!isEmpty(p.get(0).getCustomer().getLastName()) ? p.get(0).getCustomer().getLastName() : "");
                }
                if (p.get(0).getCustomer().getAccountNumber() != null) {
                    account = p.get(0).getCustomer().getAccountNumber();
                }
                BigDecimal a30 = p.stream().filter(e -> e.getDateDue().after(f30))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        }).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal a60 = p.stream().filter(e -> e.getDateDue().after(f60) && e.getDateDue().before(f30))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        }).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal a90 = p.stream().filter(e -> e.getDateDue().after(f90) && e.getDateDue().before(f60))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        }).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal o90 = p.stream().filter(e -> e.getDateDue().before(f90))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        }).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal total = a30.add(a60).add(a90).add(o90);

                dataSource.add(name, account, a30, a60, a90, o90, total);
            });
        }
        return dataSource;
    }
}
