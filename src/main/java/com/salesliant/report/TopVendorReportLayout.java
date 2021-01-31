package com.salesliant.report;

import com.salesliant.entity.PurchaseOrderHistory;
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

public class TopVendorReportLayout {

    private final List<List<PurchaseOrderHistory>> fGroupList;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public TopVendorReportLayout(List<List<PurchaseOrderHistory>> list, LocalDateTime from, LocalDateTime to) {
        this.fGroupList = list;
        fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Vendor Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(245);
        TextColumnBuilder<String> accountColumn = col.column("Account", "account", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(110);
        TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(amountColumn).setStyle(subtotalStyle).setLabel("Total");
        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(nameColumn, accountColumn, qtyColumn, amountColumn)
                .title(createTitleComponent("Purchase By Vendor"),
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
        DRDataSource dataSource = new DRDataSource("name", "account", "qty", "amount");
        if (!fGroupList.isEmpty()) {
            fGroupList.stream().forEach((p) -> {
                String qty = String.valueOf(p.size());
                BigDecimal amount = p.stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(p.get(0).getVendorName(), p.get(0).getVendorCode(), qty, amount);
            });
        }
        return dataSource;
    }
}
