package com.salesliant.report;

import com.salesliant.entity.Invoice;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class InvoiceListByDateReportLayout {

    private final List<List<Invoice>> fList;

    public InvoiceListByDateReportLayout(List<List<Invoice>> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(65);
        TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(35);
        TextColumnBuilder<BigDecimal> salesColumn = col.column("Sales", "sales", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(55);
        TextColumnBuilder<BigDecimal> costColumn = col.column("Cost", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(55);
        TextColumnBuilder<BigDecimal> profitColumn = col.column("Profit", "profit", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(55);
        TextColumnBuilder<BigDecimal> marginColumn = col.column("Margin", "margin", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(55);
        TextColumnBuilder<BigDecimal> returnsColumn = col.column("Return", "returns", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(55);
        TextColumnBuilder<BigDecimal> netAmountColumn = col.column("Net-Amount", "netAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> monthAmountColumn = col.column("Month to Date", "monthAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(dateColumn, qtyColumn, salesColumn, costColumn, profitColumn, marginColumn, returnsColumn, netAmountColumn, monthAmountColumn)
                .title(createTitleComponent("Sales By Date"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("date", "qty", "sales", "cost", "profit", "margin", "returns", "netAmount", "monthAmount");
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
                LocalDate date = p.get(0).getDateInvoiced().toLocalDateTime().toLocalDate();
                LocalDate firstDayofMonth = date.withDayOfMonth(1);
                LocalDateTime thisMonthFrom = firstDayofMonth.atTime(0, 0, 0, 0);
                LocalDateTime thisMonthTo = date.atTime(LocalTime.MAX);
                Timestamp from = Timestamp.valueOf(thisMonthFrom);
                Timestamp to = Timestamp.valueOf(thisMonthTo);
                BigDecimal total = fList
                        .stream()
                        .filter(e -> e.get(0).getDateInvoiced().after(from) && e.get(0).getDateInvoiced().before(to))
                        .map(e -> {
                            BigDecimal amount = e
                                    .stream()
                                    .map(g -> g.getTotal())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            return amount;
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(getDateDateFormat().format(p.get(0).getDateInvoiced()), qty, sales, cost, profit, margin, returns, netAmount, total);
            });
        }
        return dataSource;
    }
}
