package com.salesliant.report;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Store;
import static com.salesliant.util.BaseListUI.getBillToAddress;
import static com.salesliant.util.BaseUtil.getString;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.datatype.ListType;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

public class Templates {

    public static final StyleBuilder rootStyle;
    public static final StyleBuilder borderedStyle;
    public static final StyleBuilder boldStyle;
    public static final StyleBuilder italicStyle;
    public static final StyleBuilder boldCenteredStyle;
    public static final StyleBuilder boldLeftStyle;
    public static final StyleBuilder bold12LeftStyle;
    public static final StyleBuilder bold12CenteredStyle;
    public static final StyleBuilder bold18CenteredStyle;
    public static final StyleBuilder bold22CenteredStyle;
    public static final StyleBuilder columnStyle;
    public static final StyleBuilder topColumnStyle;
    public static final StyleBuilder columnBatchReportStyle;
    public static final StyleBuilder columnBatchDetailReportStyle;
    public static final StyleBuilder columnTitleStyle;
    public static final StyleBuilder columnBatchReportTitleStyle;
    public static final StyleBuilder columnInvoiceReportTitleStyle;
    public static final StyleBuilder columnBatchDetailReportTitleStyle;
    public static final StyleBuilder groupStyle;
    public static final StyleBuilder subtotalBoldStyle;
    public static final StyleBuilder subtotalStyle;
    public static final StyleBuilder subtotalBatchDetailStyle;
    public static final StyleBuilder leftColumnTitleStyle;
    public static final StyleBuilder rightColumnTitleStyle;
    public static final StyleBuilder centerColumnTitleStyle;
    public static final StyleBuilder leftColumnStyle;
    public static final StyleBuilder rightColumnStyle;
    public static final StyleBuilder centerColumnStyle;
    public static final StyleBuilder leftTopColumnStyle;
    public static final StyleBuilder rightTopColumnStyle;
    public static final StyleBuilder centerTopColumnStyle;
    public static final StyleBuilder centerColumnStyleWithBottomBorder;
    public static final ReportTemplateBuilder reportTemplate;
    public static final ReportTemplateBuilder barcodeTemplate;
    public static final ReportTemplateBuilder batchReportTemplate;
    public static final ReportTemplateBuilder invoiceReportTemplate;
    public static final ReportTemplateBuilder batchDetailReportTemplate;
    public static final CurrencyType currencyType;
    public static final DecimalType decimalType;
    public static final DecimalListType decimalListType;
    public static final ComponentBuilder<?, ?> dynamicReportsComponent;
    public static final ComponentBuilder<?, ?> footerComponent;
    public static final String FONT_NAME = "Arial Narrow";

    static {
        rootStyle = stl.style().setPadding(2);
        borderedStyle = stl.style(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        boldStyle = stl.style(rootStyle).bold();
        italicStyle = stl.style(rootStyle).italic();
        boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        boldLeftStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        bold12LeftStyle = stl.style(boldLeftStyle).setFontSize(12);
        bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
        bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);
        bold22CenteredStyle = stl.style(boldCenteredStyle).setFontSize(22);
        columnStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        topColumnStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.TOP);
        columnBatchReportStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        columnBatchDetailReportStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName(FONT_NAME)).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        columnBatchReportTitleStyle = stl.style(columnStyle).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT).bold();
        columnBatchDetailReportTitleStyle = stl.style(columnStyle).setFontSize(8).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
//        columnBatchDetailReportTitleStyle = stl.style(columnStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow")).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        columnTitleStyle = stl.style(columnStyle).setBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBackgroundColor(Color.LIGHT_GRAY).bold();
        columnInvoiceReportTitleStyle = stl.style(columnStyle).setBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        groupStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        subtotalBoldStyle = stl.style(boldStyle).setTopBorder(stl.pen1Point());
        subtotalStyle = stl.style(rootStyle).setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        subtotalBatchDetailStyle = stl.style(rootStyle).setFontSize(8).setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        leftColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        leftTopColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setVerticalTextAlignment(VerticalTextAlignment.TOP);
        rightColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        rightTopColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT).setVerticalTextAlignment(VerticalTextAlignment.TOP);
        centerColumnTitleStyle = stl.style(columnStyle).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        leftColumnTitleStyle = stl.style(columnStyle).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        rightColumnTitleStyle = stl.style(columnStyle).setBottomBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        centerColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        centerTopColumnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setVerticalTextAlignment(VerticalTextAlignment.TOP);
        centerColumnStyleWithBottomBorder = stl.style(rootStyle).setBottomBorder(stl.pen1Point()).setFont(stl.font().setFontSize(9)).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder crosstabGroupStyle = stl.style(columnTitleStyle);
        StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(170, 170, 170));
        StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(140, 140, 140));
        StyleBuilder crosstabCellStyle = stl.style(columnStyle).setBorder(stl.pen1Point());

        TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer().setHeadingStyle(0, stl.style(rootStyle).bold());

        reportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(columnStyle).setColumnTitleStyle(columnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalBoldStyle).highlightDetailEvenRows().crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle).setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle).setTableOfContentsCustomizer(tableOfContentsCustomizer);
        invoiceReportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(topColumnStyle).setColumnTitleStyle(columnInvoiceReportTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalBoldStyle).crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle).setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle).setTableOfContentsCustomizer(tableOfContentsCustomizer);
        batchReportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(columnStyle).setColumnTitleStyle(columnBatchReportTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalBoldStyle)
                .setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle).setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle).setTableOfContentsCustomizer(tableOfContentsCustomizer);
        batchDetailReportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(columnBatchDetailReportStyle).setColumnTitleStyle(columnBatchDetailReportTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalBoldStyle)
                .setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle).setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle).setTableOfContentsCustomizer(tableOfContentsCustomizer);
        barcodeTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(centerColumnStyle).setColumnTitleStyle(columnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle).setSubtotalStyle(subtotalBoldStyle)
                .setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle).setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle).setTableOfContentsCustomizer(tableOfContentsCustomizer);

        currencyType = new CurrencyType();
        decimalType = new DecimalType();
        decimalListType = new DecimalListType();

        // HyperLinkBuilder link = hyperLink("http://www.dynamicreports.org");
        dynamicReportsComponent = cmp.horizontalList(
                // cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(60,
                // 60),
                cmp.verticalList(cmp.text("Digiliant").setStyle(bold22CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                // cmp.text("http://www.dynamicreports.org").setStyle(italicStyle).setHyperLink(link)
                )).setFixedWidth(300);

        footerComponent = cmp.pageXofY().setStyle(stl.style(boldCenteredStyle).setTopBorder(stl.pen1Point()));
    }

    public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
        return new CurrencyValueFormatter(label);
    }

    public static StringValueFormatter createStringValueFormatter(String label) {
        return new StringValueFormatter(label);
    }

    public static IntegerValueFormatter createIntegerValueFormatter(String label) {
        return new IntegerValueFormatter(label);
    }

    public static DateValueFormatter createDateValueFormatter(String label) {
        return new DateValueFormatter(label);
    }

    public static class CurrencyType extends BigDecimalType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return "$ #,###.00";
        }
    }

    public static class DecimalType extends BigDecimalType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return " #,##0.00";
        }
    }

    public static class DecimalListType extends ListType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return " #,##0.00";
        }
    }

    private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {

        private static final long serialVersionUID = 1L;

        private String label;

        public CurrencyValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(Number value, ReportParameters reportParameters) {
            return label + currencyType.valueToString(value, reportParameters.getLocale());
        }
    }

    private static class IntegerValueFormatter extends AbstractValueFormatter<String, Integer> {

        private static final long serialVersionUID = 1L;

        private String label;

        public IntegerValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(Integer value, ReportParameters reportParameters) {
            return label + Integer.toString(value);
        }
    }

    private static class StringValueFormatter extends AbstractValueFormatter<String, String> {

        private static final long serialVersionUID = 1L;

        private String label;

        public StringValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(String value, ReportParameters reportParameters) {
            return label + value;
        }
    }

    private static class DateValueFormatter extends AbstractValueFormatter<String, Date> {

        private static final long serialVersionUID = 1L;

        private String label;

        public DateValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(Date value, ReportParameters reportParameters) {
            return label + new SimpleDateFormat("MM/dd/yyyy").format(value);
        }
    }

    public static class GetSubtotalValue extends AbstractSimpleExpression<String> {

        private static final long serialVersionUID = 1L;
        private final AggregationSubtotalBuilder<BigDecimal> subtotal1;
        private final AggregationSubtotalBuilder<BigDecimal> subtotal2;

        public GetSubtotalValue(AggregationSubtotalBuilder<BigDecimal> subtotal1) {
            this.subtotal1 = subtotal1;
            this.subtotal2 = null;
        }

        public GetSubtotalValue(AggregationSubtotalBuilder<BigDecimal> subtotal1, AggregationSubtotalBuilder<BigDecimal> subtotal2) {
            this.subtotal1 = subtotal1;
            this.subtotal2 = subtotal2;
        }

        @Override
        public String evaluate(ReportParameters reportParameters) {
            BigDecimal data = reportParameters.getValue(subtotal1);
            if (subtotal2 != null) {
                data = data.add(reportParameters.getValue(subtotal2));
            }
            return getString(data);
        }
    }

    public static class GetSubstractValue extends AbstractSimpleExpression<String> {

        private static final long serialVersionUID = 1L;
        private final AggregationSubtotalBuilder<BigDecimal> subtotal1;
        private final AggregationSubtotalBuilder<BigDecimal> subtotal2;

        public GetSubstractValue(AggregationSubtotalBuilder<BigDecimal> subtotal1, AggregationSubtotalBuilder<BigDecimal> subtotal2) {
            this.subtotal1 = subtotal1;
            this.subtotal2 = subtotal2;
        }

        @Override
        public String evaluate(ReportParameters reportParameters) {
            BigDecimal data = reportParameters.getValue(subtotal1);
            if (subtotal2 != null) {
                data = data.subtract(reportParameters.getValue(subtotal2));
            }
            return getString(data);
        }
    }

    public static ComponentBuilder<?, ?> createSumComponent(String label, BigDecimal data) {
        return cmp.horizontalList(cmp.filler(), cmp.text(label).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(data)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createSumComponent(String label, AggregationSubtotalBuilder<BigDecimal> data) {
        return cmp.horizontalList(cmp.filler(), cmp.text(label).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(new GetSubtotalValue(data)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createSumComponent(String label, AggregationSubtotalBuilder<BigDecimal> data1, AggregationSubtotalBuilder<BigDecimal> data2) {
        return cmp.horizontalList(cmp.filler(), cmp.text(label).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(new GetSubtotalValue(data1, data2)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createSubstractComponent(String label, AggregationSubtotalBuilder<BigDecimal> data1, AggregationSubtotalBuilder<BigDecimal> data2) {
        return cmp.horizontalList(cmp.filler(), cmp.text(label).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(new GetSubstractValue(data1, data2)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createSumLine() {
        return cmp.horizontalList(cmp.filler(), cmp.line().setFixedWidth(65));
    }

    public static ComponentBuilder<?, ?> createEmptyTitleComponent(String label) {
        return cmp.horizontalList().add(dynamicReportsComponent, cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).newRow().add(cmp.line()).newRow().add(cmp.verticalGap(10));
    }

    public static ComponentBuilder<?, ?> createTitleComponent(String title) {
        HorizontalListBuilder list = cmp.horizontalList(cmp.verticalList(cmp.text(title).setStyle(bold22CenteredStyle.italic()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))).setFixedWidth(400);
        return cmp.horizontalList().add(list).newRow().add(cmp.line());
    }

    public static ComponentBuilder<?, ?> createTitleComponent(String company, String label) {
        HorizontalListBuilder list = cmp.horizontalList(cmp.verticalList(cmp.text(company).setStyle(bold22CenteredStyle.italic()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))).setFixedWidth(300);
        return cmp.horizontalList().add(list, cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).newRow().add(cmp.line());
    }

    public static ComponentBuilder<?, ?> createCompanyComponent() {
        Store store = Config.getStore();
        String c = "";
        if (store.getStoreName() != null) {
            c = store.getStoreName();
        }
        if (store.getAddress1() != null && !store.getAddress1().isEmpty()) {
            c = c + "\n" + store.getAddress1();
        }
        if (store.getAddress2() != null && !store.getAddress2().isEmpty()) {
            c = c + "," + store.getAddress2();
        }
        c = c + "\n" + getString(store.getCity()) + ", " + getString(store.getState()) + " " + getString(store.getPostCode());
        if (store.getPhoneNumber() != null && !store.getPhoneNumber().isEmpty() && store.getFaxNumber() == null) {
            c = c + "\n" + "Phone:" + store.getPhoneNumber();
        } else if (store.getPhoneNumber() != null && !store.getPhoneNumber().isEmpty() && store.getFaxNumber() != null && !store.getFaxNumber().isEmpty()) {
            c = c + "\n" + "Phone:" + store.getPhoneNumber() + ", " + "Fax:" + store.getFaxNumber();
        }
        return cmp.text(c).setStyle(stl.style().setFontSize(8).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    public static ComponentBuilder<?, ?> createCompanyAddressComponent() {
        Store store = Config.getStore();
        String c = "";
        if (store.getAddress1() != null && !store.getAddress1().isEmpty()) {
            c = c + store.getAddress1();
        }
        if (store.getAddress2() != null && !store.getAddress2().isEmpty()) {
            c = c + "," + store.getAddress2();
        }
        c = c + "\n" + getString(store.getCity()) + ", " + getString(store.getState()) + " " + getString(store.getPostCode());
        if (store.getPhoneNumber() != null && !store.getPhoneNumber().isEmpty() && store.getFaxNumber() == null) {
            c = c + "\n" + "Phone:" + store.getPhoneNumber();
        } else if (store.getPhoneNumber() != null && !store.getPhoneNumber().isEmpty() && store.getFaxNumber() != null && !store.getFaxNumber().isEmpty()) {
            c = c + "\n" + "Phone:" + store.getPhoneNumber() + ", " + "Fax:" + store.getFaxNumber();
        }
        return cmp.text(c).setStyle(stl.style().setFontSize(8).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    public static ComponentBuilder<?, ?> createCompanyNameComponent() {
        return cmp.text(Config.getStore().getStoreName()).setStyle(stl.style().setFontSize(8).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createCustomerComponent(Customer customer, String label) {
        HorizontalListBuilder aList = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addAttributes(aList, getBillToAddress(customer));
        return cmp.verticalList(cmp.text(label).setStyle(Templates.boldStyle), aList);
    }

    public static ComponentBuilder<?, ?> createLocalDateRangeComponent(LocalDateTime from, LocalDateTime to) {
        String range = "From: " + getString(Date.from(from.atZone(ZoneId.systemDefault()).toInstant())) + "  To: " + getString(Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        return cmp.text(range).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static ComponentBuilder<?, ?> createDateRangeComponent(Date from, Date to) {
        String range = "From: " + getString(from) + "  To: " + getString(to);
        return cmp.text(range).setStyle(stl.style().setFontSize(9).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
    }

    public static void addAttributes(HorizontalListBuilder list, String value) {
        if (value != null) {
            list.add(cmp.text(value)).newRow();
        }
    }

    public static void addAttributes(HorizontalListBuilder list, String value, int size) {
        if (value != null) {
            list.add(cmp.text(value).setStyle(stl.style().setFontSize(size).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))).newRow();
        }
    }

    public static ComponentBuilder<?, ?> createVoidBackgroundComponent() {
        return cmp.text("Void").setStyle(stl.style().setFontSize(100).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
    }
}
