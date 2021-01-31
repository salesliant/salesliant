package com.salesliant.report;

import com.salesliant.entity.Category;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Item;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.rootStyle;
import static com.salesliant.util.BaseListUI.getCategory;
import static com.salesliant.util.BaseListUI.getItem;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.getString;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class InvoiceEntryListByCategoryReportLayout {

    private final List<InvoiceEntry> fList;
    private final String title = "Sales By Category Report";
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;
    private String fCategoryName = "";

    public InvoiceEntryListByCategoryReportLayout(List<InvoiceEntry> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
        InvoiceEntry ie = fList.get(0);
        if (ie.getCategoryName() != null && !ie.getCategoryName().isEmpty()) {
            fCategoryName = ie.getCategoryName();
        } else {
            Category category = getCategory(ie.getCategoryName());
            if (category == null) {
                Item item = getItem(ie.getItemLookUpCode());
                if (item != null && item.getCategory() != null && item.getCategory().getName() != null) {
                    fCategoryName = item.getCategory().getName();
                }
            }
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();
        StyleBuilder columnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder subtotalStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        TextColumnBuilder<String> itemSKUColumn = col.column("", "itemSKU", type.stringType()).setFixedWidth(255).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setStyle(columnStyle).setFixedWidth(45);
        TextColumnBuilder<String> invoiceNumberColumn = col.column("Invoice", "invoiceNumber", type.stringType()).setStyle(columnStyle).setFixedWidth(45);
        TextColumnBuilder<String> accountNumberColumn = col.column("Account", "accountNumber", type.stringType()).setStyle(columnStyle).setFixedWidth(45);
        TextColumnBuilder<String> nameColumn = col.column("Name", "customerName", type.stringType()).setStyle(columnStyle).setFixedWidth(70);
        TextColumnBuilder<String> salesColumn = col.column("Sales", "salesName", type.stringType()).setStyle(columnStyle).setFixedWidth(60);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("Quantity", "qty", decimalType).setStyle(columnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> priceColumn = col.column("Price", "price", decimalType).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> costColumn = col.column("Cost", "cost", decimalType).setStyle(columnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> profitColumn = col.column("Profit", "profit", decimalType).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> marginColumn = col.column("Margin", "margin", decimalType).setStyle(columnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> totalColumn = col.column("Total", "total", decimalType).setStyle(columnStyle).setFixedWidth(50);

        ColumnGroupBuilder typeGroup = grp.group(itemSKUColumn);

        AggregationSubtotalBuilder<BigDecimal> qtySum = sbt.sum(qtyColumn).setStyle(subtotalStyle).setLabel("Total:");
        AggregationSubtotalBuilder<BigDecimal> profitSum = sbt.sum(profitColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(totalColumn).setStyle(subtotalStyle).setLabel("");

        report
                .setTemplate(Templates.batchDetailReportTemplate)
                .title(createTitleComponent(title),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeCategoryComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .columns(itemSKUColumn, dateInvoicedColumn, invoiceNumberColumn, accountNumberColumn, nameColumn, salesColumn, qtyColumn, priceColumn, costColumn, profitColumn, marginColumn, totalColumn)
                .sortBy(asc(itemSKUColumn))
                .groupBy(typeGroup)
                .subtotalsAtFirstGroupFooter(sbt.sum(qtyColumn).setStyle(subtotalStyle), sbt.sum(priceColumn).setStyle(subtotalStyle),
                        sbt.sum(costColumn).setStyle(subtotalStyle), sbt.sum(profitColumn).setStyle(subtotalStyle),
                        sbt.sum(totalColumn).setStyle(subtotalStyle))
                .subtotalsAtSummary(qtySum, profitSum, totalSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(20).setRight(20).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createLocalDateRangeCategoryComponent(LocalDateTime from, LocalDateTime to) {
        String range = "From: " + getString(Date.from(from.atZone(ZoneId.systemDefault()).toInstant())) + "  To: " + getString(Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        range = range + "\n" + "Category: " + fCategoryName;
        return cmp.text(range).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("itemSKU", "dateInvoiced", "invoiceNumber", "accountNumber", "customerName", "salesName", "qty", "price", "cost", "profit", "margin", "total");
        if (fList != null && !fList.isEmpty()) {
            fList.forEach(e -> {
                String sku = getString(e.getItemLookUpCode()) + "    " + getString(e.getItemDescription());
                String dateInvoiced = "";
                if (e.getInvoice() != null && e.getInvoice().getDateInvoiced() != null) {
                    dateInvoiced = getDateDateFormat().format(e.getInvoice().getDateInvoiced());
                }
                String invoiceNumber = "";
                if (e.getInvoice() != null && e.getInvoice().getInvoiceNumber() != null) {
                    invoiceNumber = e.getInvoice().getInvoiceNumber().toString();
                }
                String accountNumber = "";
                if (e.getInvoice() != null && e.getInvoice().getCustomerAccountNumber() != null) {
                    accountNumber = e.getInvoice().getCustomerAccountNumber();
                }
                String customerName = "";
                if (e.getInvoice() != null && e.getInvoice().getCustomerName() != null) {
                    customerName = e.getInvoice().getCustomerName();
                }
                String salesName = "";
                if (e.getInvoice() != null && e.getInvoice().getSalesName() != null) {
                    salesName = e.getInvoice().getSalesName();
                }
                BigDecimal profit = BigDecimal.ZERO;
                BigDecimal margin = BigDecimal.ZERO;
                BigDecimal total = BigDecimal.ZERO;
                BigDecimal one_hundred = new BigDecimal(100);
                if (e.getCost() != null && e.getCost() != null && e.getQuantity() != null) {
                    profit = (e.getPrice().subtract(e.getCost())).multiply(e.getQuantity());
                    if (e.getPrice().compareTo(BigDecimal.ZERO) != 0) {
                        margin = (profit.divide(e.getPrice(), 6, RoundingMode.HALF_UP)).multiply(one_hundred);
                    }
                }
                if (e.getPrice() != null && e.getQuantity() != null) {
                    total = e.getPrice().multiply(e.getQuantity());
                }
                dataSource.add(sku, dateInvoiced, invoiceNumber, accountNumber, customerName, salesName, e.getQuantity(), e.getPrice(), e.getCost(), profit, margin, total);

            });
        }
        return dataSource;
    }
}
