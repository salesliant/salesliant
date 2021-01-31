package com.salesliant.report;

import com.salesliant.entity.Item;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseListUI.getQuantity;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ItemValueListReportLayout {

    private final List<Item> fList;

    public ItemValueListReportLayout(List<Item> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(90);
        TextColumnBuilder<String> descriptionColumn = col.column("Description", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(190);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("Quantity", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(85);
        TextColumnBuilder<BigDecimal> costColumn = col.column("Cost", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(85);
        TextColumnBuilder<BigDecimal> valueColumn = col.column("Value", "value", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(85);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(skuColumn, descriptionColumn, qtyColumn, costColumn, valueColumn)
                .subtotalsAtSummary(sbt.text("Total", skuColumn), sbt.sum(qtyColumn), sbt.sum(valueColumn))
                .title(createTitleComponent("Item Value List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(skuColumn))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "description", "qty", "cost", "value");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                dataSource.add(p.getItemLookUpCode(), p.getDescription(), getQuantity(p), zeroIfNull(p.getCost()), zeroIfNull(p.getCost()).multiply(getQuantity(p)));
            });
        }
        return dataSource;
    }
}
