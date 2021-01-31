package com.salesliant.report;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getString;
import java.math.BigDecimal;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ItemLogListReportLayout {

    private final List<ItemLog> fList;

    public ItemLogListReportLayout(List<ItemLog> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(70);
        TextColumnBuilder<String> typeColumn = col.column("Type", "type", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> numberColumn = col.column("NumberType", "number", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> customerColumn = col.column("Customer", "customer", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(110);
        TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> itemQtyColumn = col.column("Before Qty", "beforeQty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("After Qty", "afterQty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(skuColumn, typeColumn, numberColumn, customerColumn, dateColumn, itemQtyColumn, qtyColumn)
                .title(createTitleComponent("Item Log List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(skuColumn))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "type", "number", "customer", "date", "beforeQty", "afterQty");
        if (!fList.isEmpty()) {
            fList.stream().forEachOrdered((p) -> {
                String sku = "";
                String customer = "";
                Item item = p.getItem();
                if (item != null) {
                    sku = item.getItemLookUpCode();
                }
                if (p.getInvoice() != null && p.getInvoice().getCustomerName() != null) {
                    customer = p.getInvoice().getCustomerName();
                }
                String dateCreated = "";
                if (p.getDateCreated() != null) {
                    dateCreated = getString(p.getDateCreated());
                }
                dataSource.add(sku, p.getDescription(), p.getTransactionNumber(), customer, dateCreated, p.getBeforeQuantity(), p.getAfterQuantity());
            });
        }
        return dataSource;
    }
}
