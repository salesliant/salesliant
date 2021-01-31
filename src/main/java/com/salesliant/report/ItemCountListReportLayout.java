package com.salesliant.report;

import com.salesliant.entity.Item;
import static com.salesliant.report.Templates.centerColumnStyle;
import static com.salesliant.report.Templates.centerColumnStyleWithBottomBorder;
import static com.salesliant.report.Templates.centerColumnTitleStyle;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
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

public class ItemCountListReportLayout {

    private final List<Item> fList;

    public ItemCountListReportLayout(List<Item> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(90);
        TextColumnBuilder<String> descriptionColumn = col.column("Description", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(285);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("Quantity", "qty", decimalType).setTitleStyle(centerColumnTitleStyle).setStyle(centerColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> countColumn = col.column("Your Count", "count", type.stringType()).setTitleStyle(centerColumnTitleStyle).setStyle(centerColumnStyleWithBottomBorder).setFixedWidth(80);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(skuColumn, descriptionColumn, qtyColumn, countColumn)
                .title(createTitleComponent("Physcial Inventory"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(skuColumn))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "description", "qty", "count");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                dataSource.add(p.getItemLookUpCode(), p.getDescription(), zeroIfNull(getQuantity(p)), "     ");
            });
        }
        return dataSource;
    }
}
