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
import java.time.LocalDateTime;
import java.time.ZoneId;
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

public class TopSalesReportLayout {

    private final List<List<Invoice>> fGroupList;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public TopSalesReportLayout(List<List<Invoice>> list, LocalDateTime from, LocalDateTime to) {
        this.fGroupList = list;
        fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
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
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
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
        if (!fGroupList.isEmpty()) {
            fGroupList.stream().forEach((p) -> {
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
