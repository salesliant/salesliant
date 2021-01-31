package com.salesliant.report;

import com.salesliant.entity.Employee;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;

public class TimeCardReportLayout {

    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;
    private final List<Employee> fList;

    public TimeCardReportLayout(Set<Employee> list, LocalDateTime from, LocalDateTime to) {
        this.fFrom = from;
        this.fTo = to;
        fList = list
                .stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "")
                            + (!isEmpty(e1.getLastName()) ? " " : "");
                    String name2 = (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "")
                            + (!isEmpty(e2.getLastName()) ? " " : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> employeeNameColumn = col.column("Employee", "employeeName", type.stringType()).setStyle(leftColumnStyle).setTitleStyle(leftColumnTitleStyle).setFixedWidth(305).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<String> nameOnOrderColumn = col.column("Name On Order", "nameOnOrder", type.stringType()).setStyle(leftColumnStyle).setTitleStyle(leftColumnTitleStyle).setFixedWidth(100).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<BigDecimal> hoursColumn = col.column("Hours", "hours", decimalType).setStyle(rightColumnStyle).setTitleStyle(rightColumnTitleStyle).setFixedWidth(120);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(employeeNameColumn, nameOnOrderColumn, hoursColumn)
                .title(createTitleComponent("Time Card Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("employeeName", "nameOnOrder", "hours");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String name = (!isEmpty(p.getFirstName()) ? p.getFirstName() : "")
                        + (!isEmpty(p.getLastName()) ? " " : "");
                BigDecimal hours = p.getTimeCards()
                        .stream()
                        .filter(e -> e.getTimeIn() != null)
                        .filter(e -> e.getTimeOut() != null)
                        .filter(e -> e.getTimeIn().after(Timestamp.valueOf(fFrom)))
                        .filter(e -> e.getTimeIn().before(Timestamp.valueOf(fTo)))
                        .map(e -> (e.getHours()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(name, p.getNameOnSalesOrder(), hours);
            });
        }
        return dataSource;
    }
}
