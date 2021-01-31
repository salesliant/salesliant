package com.salesliant.report;

import com.salesliant.entity.Employee;
import com.salesliant.entity.InvoiceEntry;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createEmptyTitleComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseListUI.getEmployee;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class CommissionPaymentLayout {

    private final Employee fEmployee;
    private final Timestamp fTime;
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;
    private final List<List<InvoiceEntry>> fList;

    public CommissionPaymentLayout(List<InvoiceEntry> list, Employee employee, Timestamp ts, LocalDateTime from, LocalDateTime to) {
        this.fFrom = from;
        this.fTo = to;
        this.fEmployee = employee;
        this.fTime = ts;
        Map<String, List<InvoiceEntry>> groupByEmployee = list
                .stream()
                .filter(e -> e != null && getEmployee(e.getSalesName()) != null)
                .collect(Collectors.groupingBy(e -> e.getSalesName()));
        fList = new ArrayList<>(groupByEmployee.values())
                .stream()
                .sorted((e1, e2) -> {
                    Employee employee1 = getEmployee(e1.get(0).getSalesName());
                    Employee employee2 = getEmployee(e2.get(0).getSalesName());
                    String name1 = (!isEmpty(employee1.getFirstName()) ? employee1.getFirstName() : "")
                            + (!isEmpty(employee1.getLastName()) ? " " + employee1.getLastName() : "");
                    String name2 = (!isEmpty(employee2.getFirstName()) ? employee2.getFirstName() : "")
                            + (!isEmpty(employee2.getLastName()) ? " " + employee2.getLastName() : "");
                    return name1.compareToIgnoreCase(name2);
                }).collect(Collectors.toList());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> employeeNameColumn = col.column("Sales", "employeeName", type.stringType()).setStyle(leftColumnStyle).setTitleStyle(leftColumnTitleStyle).setFixedWidth(405).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<BigDecimal> commissionAmountColumn = col.column("Commission Amount", "commissionAmount", decimalType).setStyle(rightColumnStyle).setTitleStyle(rightColumnTitleStyle).setFixedWidth(120);

        AggregationSubtotalBuilder<BigDecimal> commissionAmountSum = sbt.sum(commissionAmountColumn).setStyle(subtotalStyle).setLabel("");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(employeeNameColumn, commissionAmountColumn)
                .noData(createEmptyTitleComponent("NoData"), cmp.text("There is no data"))
                .title(createTitleComponent("Commission Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createRegisterComponent())),
                        cmp.verticalGap(10))
                .subtotalsAtSummary(commissionAmountSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createRegisterComponent() {
        String range = "Commission Paid Range From: " + getString(Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant())) + "  To: " + getString(Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant()))
                + "\n" + "Processed By: " + fEmployee.getFirstName() + " " + fEmployee.getLastName()
                + "\n" + new SimpleDateFormat("MM/dd/yyyy").format(fTime);
        return cmp.text(range).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("employeeName", "commissionAmount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String name;
                Employee employee = getEmployee(p.get(0).getSalesName());
                if (employee != null) {
                    name = (!isEmpty(employee.getFirstName()) ? employee.getFirstName() : "")
                            + (!isEmpty(employee.getLastName()) ? " " + employee.getLastName() : "");
                } else {
                    name = p.get(0).getSalesName();
                }
                BigDecimal commissionAmount = p.stream()
                        .filter(e -> e.getCommissionAmount() != null)
                        .map(InvoiceEntry::getCommissionAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(name, commissionAmount);
            });
        }
        return dataSource;
    }
}
