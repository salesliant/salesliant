package com.salesliant.report;

import com.salesliant.entity.PurchaseOrderHistory;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getMonthDateFormat;
import java.math.BigDecimal;
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

public class PurchaseOrderHistoryListByMonthReportLayout {

    private final List<List<PurchaseOrderHistory>> fList;

    public PurchaseOrderHistoryListByMonthReportLayout(List<List<PurchaseOrderHistory>> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> monthColumn = col.column("Month", "month", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(100);
        TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(150);
        TextColumnBuilder<BigDecimal> yearAmountColumn = col.column("Year to Date", "yearAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(150);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(monthColumn, qtyColumn, amountColumn, yearAmountColumn)
                .title(createTitleComponent("Purchased By Month"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("month", "qty", "amount", "yearAmount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String qty = String.valueOf(p.size());
                BigDecimal amount = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                LocalDate date = p.get(0).getDatePurchased().toLocalDateTime().toLocalDate();
                LocalDate firstDayofYear = date.withDayOfYear(1).minusDays(1);
                LocalDateTime thisYearFrom = firstDayofYear.atTime(LocalTime.MAX);
                LocalDateTime thisYearTo = date.atTime(LocalTime.MAX);
                Timestamp from = Timestamp.valueOf(thisYearFrom);
                Timestamp to = Timestamp.valueOf(thisYearTo);
                BigDecimal total = fList
                        .stream()
                        .filter(e -> e.get(0).getDatePurchased().after(from) && e.get(0).getDatePurchased().before(to))
                        .map(e -> {
                            BigDecimal subTotal = e
                                    .stream()
                                    .map(g -> g.getTotal())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            return subTotal;
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(getMonthDateFormat().format(p.get(0).getDatePurchased()), qty, amount, total);
            });
        }
        return dataSource;
    }
}
