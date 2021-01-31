package com.salesliant.report;

import com.salesliant.entity.Invoice;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getMonthDateFormat;
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

public class InvoiceListByMonthReportLayout {

    private final List<List<Invoice>> fList;

    public InvoiceListByMonthReportLayout(List<List<Invoice>> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> monthColumn = col.column("Month", "month", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(115);
        TextColumnBuilder<String> orderColumn = col.column("Number of Order", "orders", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(140);
        TextColumnBuilder<String> returnColumn = col.column("Number of Return", "returns", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(140);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(140);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(amountColumn).setStyle(subtotalStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(monthColumn, orderColumn, returnColumn, amountColumn)
                .title(createTitleComponent("Sales By Month"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(desc(monthColumn))
                .subtotalsAtSummary(totalSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("month", "orders", "returns", "amount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String month = getMonthDateFormat().format(p.get(0).getDateInvoiced());
                String orders = String.valueOf(p.size());
                long size = p.stream().filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) < 0).count();
                String returns = String.valueOf(size);
                Double total = p.stream().mapToDouble(e -> e.getTotal().doubleValue()).sum();
                BigDecimal amount = BigDecimal.valueOf(total);
                dataSource.add(month, orders, returns, amount);
            });
        }
        return dataSource;
    }
}
