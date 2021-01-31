/*
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2018 Ricardo Mariaca and the Dynamic Reports Contributors
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.salesliant.test;

import static net.sf.dynamicreports.report.builder.DynamicReports.bcode;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.margin;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;

/**
 * <p>
 * CardReport class.</p>
 *
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 * @version $Id: $Id
 */
public class CardReport {

    /**
     * <p>
     * Constructor for CardReport.</p>
     */
    int topMargin, bottomMargin, rightMargin, leftMargin;
    int horizontalSpace, verticalSpace, labelWidth, labelHeight;
    int barcodeWidth, barcodeHeight;
    int firstColumn = 1, firstRow = 1, nLength;
    int maximumColumns, maximumRows;
    private final StyleBuilder pricelStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(10);
    private final StyleBuilder descriptionStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(9);
    private final StyleBuilder barcodeStyle = stl.style().setImageAlignment(HorizontalImageAlignment.CENTER, VerticalImageAlignment.BOTTOM).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
    private final StyleBuilder codeStyle = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFontSize(10);

    public CardReport() {
        init();
        build();
    }

    /**
     * <p>
     * main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        new CardReport();
    }

    private void init() {
        topMargin = 36;
        bottomMargin = 36;
        rightMargin = 15;
        leftMargin = 15;
        maximumColumns = 3;
        maximumRows = 10;
        horizontalSpace = 11;
        verticalSpace = 5;
        labelWidth = 185;
        labelHeight = 71;
        nLength = 30;
        barcodeWidth = 175;
        barcodeHeight = 26;
    }

    private void build() {
        ComponentBuilder<?, ?> cardComponent = createCardComponent();
        HorizontalListBuilder cards = cmp.horizontalFlowList();
        for (int i = 0; i < 30; i++) {
            cards.add(cardComponent);
        }

        try {
            report()
                    //                    .setTemplate(Templates.reportTemplate)
                    //                    .setTextStyle(stl.style())
                    .setPageFormat(PageType.LETTER)
                    .setPageMargin(margin().setTop(topMargin).setBottom(bottomMargin).setLeft(leftMargin).setRight(rightMargin))
                    .title(cards).show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private ComponentBuilder<?, ?> createCardComponent() {
        HorizontalListBuilder cardComponent = cmp.horizontalList();
//        StyleBuilder cardStyle = stl.style(stl.pen1Point()).setPadding(10);
//        cardComponent.setStyle(cardStyle);

        StyleBuilder boldStyle = stl.style().bold();
        VerticalListBuilder content
                = cmp.verticalList(
                        cmp.verticalGap(verticalSpace),
                        bcode.barbecue_code128B("02888").setFixedDimension(barcodeWidth, barcodeHeight).setStyle(barcodeStyle),
                        cmp.text("02888").setStyle(codeStyle),
                        cmp.text("CPU Intel CORE i3-9100F 4.2GHZ").setStyle(descriptionStyle),
                        cmp.text("$89").setStyle(pricelStyle),
                        cmp.verticalGap(verticalSpace));
        content.setFixedHeight(labelHeight);
//        content.setFixedDimension(labelWidth, labelHeight);
        cardComponent.add(content);
        return cardComponent;
    }
}
