package com.salesliant.report;

import com.salesliant.entity.ItemLabel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.MultiPageListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class Avery5160Layout {

    int topMargin, bottomMargin, rightMargin, leftMargin;
    int horizontalSpace, verticalSpace, labelWidth, labelHeight;
    int barcodeWidth, barcodeHeight;
    int firstColumn = 1, firstRow = 1, nLength;
    int maximumColumns, maximumRows;
    private final StyleBuilder pricelStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(10);
    private final StyleBuilder descriptionStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(9);
    private final StyleBuilder barcodeStyle = stl.style().setImageAlignment(HorizontalImageAlignment.CENTER, VerticalImageAlignment.BOTTOM).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
    private final StyleBuilder codeStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(10);

    private final List<ItemLabel> labelList;

    public Avery5160Layout(List<ItemLabel> list, int row, int col) {
        this.labelList = list;
        this.firstRow = row;
        this.firstColumn = col;
        init();
    }

    private void init() {
        topMargin = 35;
        bottomMargin = 35;
        rightMargin = 15;
        leftMargin = 15;
        maximumColumns = 3;
        maximumRows = 10;
        horizontalSpace = 11;
        verticalSpace = 5;
        labelWidth = 185;
        labelHeight = 71;
        nLength = 33;
        barcodeWidth = 175;
        barcodeHeight = 26;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();
        report.setPageFormat(PageType.LETTER)
                .setPageMargin(margin().setTop(topMargin).setBottom(bottomMargin).setLeft(leftMargin).setRight(rightMargin))
                .title(getLabels());
        return report;
    }

    private ComponentBuilder<?, ?> getLabels() {
        MultiPageListBuilder multiPageList = cmp.multiPageList();
        List<ItemLabel> labels = new ArrayList<>();
        if (firstRow > 1) {
            for (int i = 1; i <= firstRow - 1; i++) {
                for (int j = 0; j < maximumColumns; j++) {
                    labels.add(null);
                }
            }
        }
        if (firstColumn > 1) {
            for (int i = 1; i <= firstColumn - 1; i++) {
                labels.add(null);
            }
        }
        labelList.forEach(il -> {
            ItemLabel itemLabel = new ItemLabel();
            itemLabel.setItem(il.getItem());
            itemLabel.setQuantity(1);
            int qty = il.getQuantity();
            for (int i = 0; i <= qty - 1; i++) {
                labels.add(itemLabel);
            }
        });
        if (labels.size() % maximumColumns != 0) {
            int tail = maximumColumns - labels.size() % maximumColumns;
            for (int i = 0; i <= tail - 1; i++) {
                labels.add(null);
            }
        }
        int count = 0;
        for (int i = 0; i < labels.size(); i += maximumColumns) {
            HorizontalListBuilder flowList = cmp.horizontalFlowList().setGap(horizontalSpace);
            for (int j = 0; j < maximumColumns; j++) {
                flowList.add(addLabel(labels.get(i + j)));
            }
            multiPageList.add(flowList);
            count = count + maximumColumns;
            if ((count % (maximumColumns * maximumRows)) == 0) {
                multiPageList.add(cmp.pageBreak());
            }
        }
        return multiPageList;
    }

    private ComponentBuilder<?, ?> addLabel(ItemLabel il) {
        HorizontalListBuilder labelComponent = cmp.horizontalList();
        VerticalListBuilder content;
        if (il != null && il.getItem() != null && il.getItem().getItemLookUpCode() != null) {
            content = cmp.verticalList(
                    cmp.verticalGap(verticalSpace),
                    bcode.barbecue_code128B(il.getItem().getItemLookUpCode()).setFixedDimension(barcodeWidth, barcodeHeight).setStyle(barcodeStyle),
                    cmp.text(il.getItem().getItemLookUpCode()).setStyle(codeStyle),
                    cmp.text(getDescription(il)).setStyle(descriptionStyle),
                    cmp.text(getPrice(il)).setStyle(pricelStyle),
                    cmp.verticalGap(verticalSpace));
        } else {
            content = cmp.verticalList(
                    cmp.verticalGap(verticalSpace),
                    cmp.text(" ").setFixedDimension(barcodeWidth, barcodeHeight).setStyle(barcodeStyle),
                    cmp.text(" ").setStyle(barcodeStyle),
                    cmp.text(" ").setStyle(descriptionStyle),
                    cmp.text(" ").setStyle(pricelStyle),
                    cmp.verticalGap(verticalSpace));
        }
        content.setFixedDimension(labelWidth, labelHeight);
        labelComponent.add(content);
        return labelComponent;
    }

    protected String getDescription(ItemLabel il) {
        if (il == null) {
            return "";
        } else {
            if (il.getItem() == null) {
                return "";
            } else {
                if (il.getItem().getDescription() == null) {
                    return "";
                } else {
                    String upTo = il.getItem().getDescription().substring(0, Math.min(il.getItem().getDescription().length(), nLength));
                    return upTo;
                }
            }
        }
    }

    protected String getPrice(ItemLabel il) {
        if (il == null) {
            return "";
        } else {
            if (il.getItem() == null) {
                return "";
            } else {
                if (il.getItem().getPrice1() == null) {
                    return "";
                } else {
                    DecimalFormat moneyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
                    moneyFormat.setGroupingUsed(false);
                    moneyFormat.setMaximumIntegerDigits(10);
                    moneyFormat.setMaximumFractionDigits(2);
                    String price = moneyFormat.format(il.getItem().getPrice1());
                    return price;
                }
            }
        }
    }
}
