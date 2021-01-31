package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.Store;
import com.salesliant.entity.TransferOrderHistory;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.groupStyle;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.leftTopColumnStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.rightTopColumnStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import java.util.Locale;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class TransferOrderHistoryLayout {

    private final TransferOrderHistory fTransferOrderHistory;
    private final Store fStore;

    public TransferOrderHistoryLayout(TransferOrderHistory transfer) {
        this.fTransferOrderHistory = transfer;
        fStore = Config.getStore();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        ReportTemplateBuilder reportTemplate = template().setLocale(Locale.ENGLISH)
                .setColumnStyle(rightColumnStyle).setColumnTitleStyle(leftColumnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalStyle);

        TextColumnBuilder<String> skuColumn = col.column("SKU", "sku", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> descriptionColumn = col.column("DESCRIPTION", "description", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftTopColumnStyle).setFixedWidth(295);
        TextColumnBuilder<BigDecimal> qtyColumn = col.column("Transfer", "qty", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> costColumn = col.column("Cost", "cost", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> totalColumn = costColumn.multiply(qtyColumn).setTitle("Total").setDataType(decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightTopColumnStyle).setFixedWidth(60);
        report.setTemplate(reportTemplate)
                .setColumnStyle(rightColumnStyle)
                .setSubtotalStyle(subtotalStyle)
                .columns(skuColumn, descriptionColumn, costColumn, qtyColumn, totalColumn)
                .title(createTitleComponent(fStore.getStoreName(), "Transfer Inventory History"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createTransferComponent()),
                                cmp.hListCell(createTransferInfoComponent())),
                        cmp.verticalGap(5))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource())
                .summary(
                        cmp.horizontalList(cmp.verticalGap(8), cmp.text(fTransferOrderHistory.getNote()).setStyle(leftColumnStyle)),
                        cmp.line(),
                        cmp.verticalGap(5),
                        createSumComponent("Subtotal:", fTransferOrderHistory.getTotal()),
                        createSumComponent("Freight:", zeroIfNull(fTransferOrderHistory.getFreightAmount())),
                        createSumComponent("Total:", fTransferOrderHistory.getTotal().add(zeroIfNull(fTransferOrderHistory.getFreightAmount()))));
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(30).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createTransferComponent() {
        String t = "From:  " + getString(fTransferOrderHistory.getSendStore().getStoreName()) + "\n"
                + "To: " + getString(fTransferOrderHistory.getReceiveStore().getStoreName());
        return cmp.text(t).setStyle(stl.style().setFontSize(7).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    private ComponentBuilder<?, ?> createTransferInfoComponent() {
        String i = "Transfer #:  " + getString(fTransferOrderHistory.getTransferOrderNumber()) + "\n"
                + "Transfer Date: " + getString(fTransferOrderHistory.getDateCreated());
        return cmp.text(i).setStyle(stl.style().setFontSize(7).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("sku", "description", "cost", "qty");
        if (fTransferOrderHistory.getTransferOrderHistoryEntries() != null && !fTransferOrderHistory.getTransferOrderHistoryEntries().isEmpty()) {
            fTransferOrderHistory.getTransferOrderHistoryEntries().forEach(e -> {
                dataSource.add(e.getItemLookUpCode(), e.getItemDescription(), e.getCost(), e.getQuantity());
            });
        }
        return dataSource;
    }
}
