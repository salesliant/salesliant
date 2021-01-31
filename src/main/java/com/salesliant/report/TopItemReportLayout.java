package com.salesliant.report;

import com.salesliant.entity.InvoiceEntry;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class TopItemReportLayout {

    private final List<List<InvoiceEntry>> fGroupList;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public TopItemReportLayout(List<List<InvoiceEntry>> list, LocalDateTime from, LocalDateTime to) {
        this.fGroupList = list;
        fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> descriptionColumn = col.column("Item Description", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(230);
        TextColumnBuilder<String> skuColumn = col.column("Item SKU", "sku", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(115);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("Qty", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(90);
        TextColumnBuilder<BigDecimal> salesColumn = col.column("Sales", "sales", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(descriptionColumn, skuColumn, qtyColumn, salesColumn)
                .title(createTitleComponent("Sales By Item"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createDateRangeComponent(fromDate, toDate))),
                        cmp.verticalGap(10))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("description", "sku", "qty", "sales");
        if (!fGroupList.isEmpty()) {
            fGroupList.stream().forEach((p) -> {
                BigDecimal qty = p.stream()
                        .map(e -> e.getQuantity())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal sales = p.stream()
                        .filter(e -> e.getPrice() != null)
                        .filter(e -> e.getQuantity() != null)
                        .map(e -> e.getPrice().multiply(e.getPrice()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(p.get(0).getItemDescription(), p.get(0).getItemLookUpCode(), qty, sales);
            });
        }
        return dataSource;
    }
}
