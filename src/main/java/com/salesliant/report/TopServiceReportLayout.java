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
import java.math.BigDecimal;
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

public class TopServiceReportLayout {

    private final List<List<Invoice>> fGroupList;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public TopServiceReportLayout(List<List<Invoice>> list, LocalDateTime from, LocalDateTime to) {
        this.fGroupList = list;
        fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(225);
        TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(110);
        TextColumnBuilder<BigDecimal> salesColumn = col.column("Sales", "sales", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(210);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(salesColumn).setStyle(subtotalStyle).setLabel("Total");
        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(nameColumn, qtyColumn, salesColumn)
                .title(createTitleComponent("Service By Employee"),
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
        DRDataSource dataSource = new DRDataSource("name", "qty", "sales");
        if (!fGroupList.isEmpty()) {
            fGroupList.stream().forEach((p) -> {
                String qty = String.valueOf(p.size());
                BigDecimal sales = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(p.get(0).getSalesName(), qty, sales);
            });
        }
        return dataSource;
    }
}
