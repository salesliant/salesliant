package com.salesliant.report;

import com.salesliant.entity.DropPayout;
import static com.salesliant.report.Templates.centerColumnStyle;
import static com.salesliant.report.Templates.centerColumnTitleStyle;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.subtotalBoldStyle;

public class DropPayoutListReportLayout {

    private final List<DropPayout> fList;

    public DropPayoutListReportLayout(List<DropPayout> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> typeColumn = col.column("In/Out", "type", type.stringType()).setTitleStyle(centerColumnTitleStyle).setStyle(centerColumnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> isCheckColumn = col.column("Type", "isCheck", type.stringType()).setTitleStyle(centerColumnTitleStyle).setStyle(centerColumnStyle).setFixedWidth(50);
        TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setTitleStyle(centerColumnTitleStyle).setStyle(centerColumnStyle).setFixedWidth(90);
        TextColumnBuilder<String> employeeColumn = col.column("Employee", "employee", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(100);
        TextColumnBuilder<String> noteColumn = col.column("Note", "note", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(170);

        AggregationSubtotalBuilder<BigDecimal> amountSum = sbt.sum(amountColumn).setStyle(subtotalBoldStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(typeColumn, amountColumn, isCheckColumn, dateColumn, employeeColumn, noteColumn)
                .title(createTitleComponent("DropPayout List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(dateColumn))
                .subtotalsAtSummary(amountSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("type", "amount", "isCheck", "date", "employee", "note");
        if (!fList.isEmpty()) {
            fList.stream().forEach(e -> {
                String dropType;
                if (e.getAmount() != null && e.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
                    dropType = "Out";
                } else {
                    dropType = "In";
                }
                String emp = "";
                emp = emp + (!isEmpty(e.getEmployee().getFirstName()) ? e.getEmployee().getFirstName() : "")
                        + (!isEmpty(e.getEmployee().getFirstName()) ? " " : "")
                        + (!isEmpty(e.getEmployee().getLastName()) ? e.getEmployee().getLastName() : "");
                String date = getString(e.getDateCreated());
                String moneyType;
                if (e.getCheckTag() != null && e.getCheckTag()) {
                    moneyType = "Check";
                } else {
                    moneyType = "Cash";
                }
                dataSource.add(dropType, e.getAmount(), moneyType, date, emp, e.getNote());
            });
        }
        return dataSource;
    }
}
