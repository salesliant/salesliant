package com.salesliant.report;

import com.salesliant.entity.PurchaseOrderHistoryEntry;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ItemPurchaseListByDateReportLayout {

    private final List<List<PurchaseOrderHistoryEntry>> fList;
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;

    public ItemPurchaseListByDateReportLayout(List<List<PurchaseOrderHistoryEntry>> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(85);
        TextColumnBuilder<String> descriptionColumn = col.column("Description", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(300);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("QTY", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(75);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(skuColumn, descriptionColumn, qtyColumn, amountColumn)
                .title(createTitleComponent("Items Sales Qty By Date"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "description", "qty", "amount");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                BigDecimal qty = p
                        .stream()
                        .filter(e -> e.getQuantityOrdered().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getQuantityOrdered())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal amount = p
                        .stream()
                        .filter(e -> e.getCost().compareTo(BigDecimal.ZERO) >= 0 && e.getQuantityOrdered().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getCost().multiply(e.getQuantityOrdered()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                dataSource.add(p.get(0).getItemLookUpCode(), p.get(0).getItemDescription(), qty, amount);
            });
        }
        return dataSource;
    }
}
