package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createEmptyTitleComponent;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rootStyle;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
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

public class AccountReceivableCreditReportLayout {

    private final List<List<AccountReceivable>> fList;

    public AccountReceivableCreditReportLayout(List<List<AccountReceivable>> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        StyleBuilder columnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = stl.style(columnStyle).setFontSize(8).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        StyleBuilder subtotalStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8)).setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(250);
        TextColumnBuilder<String> accountColumn = col.column("Account", "account", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(220);
        TextColumnBuilder<BigDecimal> totalColumn = col.column("Total", "total", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(63);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(totalColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .setColumnStyle(columnStyle)
                .setSubtotalStyle(subtotalStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .columns(nameColumn, accountColumn, totalColumn)
                .noData(createEmptyTitleComponent("NoData"), cmp.text("There is no data"))
                .title(createTitleComponent("Store Credit Summary Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(desc(nameColumn))
                .subtotalsAtSummary(totalSum)
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
        DRDataSource dataSource = new DRDataSource("name", "account", "total");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String name = "";
                String account = "";
                if (p.get(0).getCustomer().getCompany() != null) {
                    name = p.get(0).getCustomer().getCompany();
                } else if (p.get(0).getCustomer().getFirstName() == null && p.get(0).getCustomer().getLastName() == null) {
                    name = p.get(0).getCustomer().getAddress1();
                } else {
                    name = name + (!isEmpty(p.get(0).getCustomer().getFirstName()) ? p.get(0).getCustomer().getFirstName() : "")
                            + (!isEmpty(p.get(0).getCustomer().getFirstName()) ? " " : "")
                            + (!isEmpty(p.get(0).getCustomer().getLastName()) ? p.get(0).getCustomer().getLastName() : "");
                }
                if (p.get(0).getCustomer().getAccountNumber() != null) {
                    account = p.get(0).getCustomer().getAccountNumber();
                }
                BigDecimal total = p.stream().map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(name, account, total);
            });
        }
        return dataSource;
    }
}
