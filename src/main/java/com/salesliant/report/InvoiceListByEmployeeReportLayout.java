package com.salesliant.report;

import com.salesliant.entity.Invoice;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class InvoiceListByEmployeeReportLayout {

    private final List<List<Invoice>> fList;
    private List<Invoice> fInvoiceList = new ArrayList<>();
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public InvoiceListByEmployeeReportLayout(List<Invoice> list) {
        this.fInvoiceList = list;
        Timestamp maxDate = fInvoiceList.stream()
                .map(e -> e.getDateInvoiced())
                .max(Timestamp::compareTo).get();
        Timestamp minDate = fInvoiceList.stream()
                .map(e -> e.getDateInvoiced())
                .min(Timestamp::compareTo).get();
        fromDate = new Date(minDate.getTime());
        toDate = new Date(maxDate.getTime());
        Map<String, List<Invoice>> groupByEmployee = fInvoiceList
                .stream()
                .filter(e -> e.getDateInvoiced() != null && e.getSalesName() != null && !e.getSalesName().isEmpty())
                .collect(Collectors.groupingBy(e -> e.getSalesName()));
        fList = new ArrayList<>(groupByEmployee.values())
                .stream()
                .sorted((e1, e2) -> e2.get(0).getDateInvoiced().compareTo(e1.get(0).getDateInvoiced())).collect(Collectors.toList());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(90);
        TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(45);
        TextColumnBuilder<BigDecimal> salesColumn = col.column("Sales", "sales", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> costColumn = col.column("Cost", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> profitColumn = col.column("Profit", "profit", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> marginColumn = col.column("Margin", "margin", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> returnsColumn = col.column("Return", "returns", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(65);
        TextColumnBuilder<BigDecimal> netAmountColumn = col.column("Net-Amount", "netAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(netAmountColumn).setStyle(subtotalStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(nameColumn, qtyColumn, salesColumn, costColumn, profitColumn, marginColumn, returnsColumn, netAmountColumn)
                .title(createTitleComponent("Sales By Employee"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createDateRangeComponent(fromDate, toDate))),
                        cmp.verticalGap(10))
                .subtotalsAtSummary(totalSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("name", "qty", "sales", "cost", "profit", "margin", "returns", "netAmount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String qty = String.valueOf(p.size());
                BigDecimal sales = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal cost = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getCost())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal profit = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> zeroIfNull(e.getTotal()).subtract(zeroIfNull(e.getCost())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal margin = BigDecimal.ZERO;
                if (cost.compareTo(BigDecimal.ZERO) != 0) {
                    margin = profit.divide(cost, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }
                BigDecimal returns = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) < 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal netAmount = p.stream()
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(p.get(0).getSalesName(), qty, sales, cost, profit, margin, returns, netAmount);
            });
        }
        return dataSource;
    }
}
