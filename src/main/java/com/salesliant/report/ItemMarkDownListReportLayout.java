package com.salesliant.report;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
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

public class ItemMarkDownListReportLayout {

    private final List<ItemLog> fList;
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public ItemMarkDownListReportLayout(List<ItemLog> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(60);
        TextColumnBuilder<String> typeColumn = col.column("Description", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(165);
        TextColumnBuilder<String> numberColumn = col.column("Invoice", "invoice", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(60);
        TextColumnBuilder<String> customerColumn = col.column("Customer", "customer", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> itemPriceColumn = col.column("Item Price", "itemPrice", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(60);
        TextColumnBuilder<BigDecimal> priceColumn = col.column("Sales Price", "price", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(60);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(skuColumn, typeColumn, numberColumn, customerColumn, dateColumn, itemPriceColumn, priceColumn)
                .title(createTitleComponent("Item Mark Down Sales List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createDateRangeComponent(fromDate, toDate))),
                        cmp.verticalGap(10))
                .sortBy(asc(skuColumn))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "description", "invoice", "customer", "date", "itemPrice", "price");
        if (!fList.isEmpty()) {
            fList.stream().forEachOrdered((p) -> {
                String sku = "";
                String description = "";
                String customer = "";
                Item item = p.getItem();
                if (item != null) {
                    sku = item.getItemLookUpCode();
                    description = item.getDescription();
                }
                if (p.getInvoice() != null && p.getInvoice().getCustomerName() != null) {
                    customer = p.getInvoice().getCustomerName();
                }
                dataSource.add(sku, description, p.getTransactionNumber(), customer, getDateDateFormat().format(p.getDateCreated()), p.getItemPrice(), p.getPrice());
            });
        }
        return dataSource;
    }
}
