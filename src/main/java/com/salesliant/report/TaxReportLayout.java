package com.salesliant.report;

import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Tax;
import com.salesliant.entity.TaxRate;
import com.salesliant.entity.TaxZone;
import com.salesliant.entity.Tax_;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;
import static com.salesliant.report.Templates.createSumComponent;
import static com.salesliant.report.Templates.createSumLine;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.rootStyle;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseListUI.getTaxZone;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang.ArrayUtils;

public class TaxReportLayout {

    private final List<Invoice> fList;
    private final BaseDao daoTaxRate = new BaseDao(TaxRate.class);
    private final BaseDao daoTax = new BaseDao(Tax.class);
    private final String title = "Sales Tax Report";
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;
    private final List<TaxRate> taxRateList;

    public TaxReportLayout(List<Invoice> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
        taxRateList = daoTaxRate.read();
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();
        StyleBuilder columnStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder subtotalStyle = stl.style(rootStyle).setFont(stl.font().setFontSize(8).setFontName("Arial Narrow"))
                .setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        TextColumnBuilder<String> taxRateTypeColumn = col.column("", "taxRateType", type.stringType()).setFixedWidth(5).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setStyle(columnStyle).setFixedWidth(75);
        TextColumnBuilder<String> invoiceNumberColumn = col.column("Number", "invoiceNumber", type.stringType()).setStyle(columnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> taxedColumn = col.column("Taxed", "taxed", decimalType).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> notTaxedColumn = col.column("Not Taxed", "notTaxed", decimalType).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> salesColumn = col.column("Sales", "salesAmount", decimalType).setStyle(columnStyle).setFixedWidth(51);
        TextColumnBuilder<BigDecimal> returnColumn = col.column("Return", "returnAmount", decimalType).setStyle(columnStyle).setFixedWidth(40);
        TextColumnBuilder<BigDecimal> totalAmountColumn = col.column("Total", "totalAmount", decimalType).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<List> taxCategoryColumn = col.column("Category", "taxCategory", type.listType()).setStyle(columnStyle).setFixedWidth(51);
        TextColumnBuilder<List> appliedToColumn = col.column("Applied", "appliedTo", type.listType()).setStyle(columnStyle).setFixedWidth(50);
        TextColumnBuilder<List> taxColumn = col.column("Tax", "tax", type.listType()).setStyle(columnStyle).setFixedWidth(41);
        TextColumnBuilder<BigDecimal> totalTaxColumn = col.column("Total Tax", "totalTax", decimalType).setStyle(columnStyle).setFixedWidth(41);

        ColumnGroupBuilder typeGroup = grp.group(taxRateTypeColumn);

        AggregationSubtotalBuilder<BigDecimal> taxedSum = sbt.sum(taxedColumn).setStyle(subtotalStyle).setLabel("Total:");
        AggregationSubtotalBuilder<BigDecimal> notTaxedSum = sbt.sum(notTaxedColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> totalAmountSum = sbt.sum(totalAmountColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> salesSum = sbt.sum(salesColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> returnSum = sbt.sum(returnColumn).setStyle(subtotalStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> totalTaxSum = sbt.sum(totalTaxColumn).setStyle(subtotalStyle).setLabel("");

        report
                .setTemplate(Templates.batchDetailReportTemplate)
                .title(createTitleComponent(title),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .columns(taxRateTypeColumn, dateInvoicedColumn, invoiceNumberColumn, taxedColumn, notTaxedColumn, salesColumn, returnColumn, totalAmountColumn, taxCategoryColumn, appliedToColumn, taxColumn, totalTaxColumn)
                .sortBy(asc(taxRateTypeColumn))
                .groupBy(typeGroup)
                .subtotalsAtFirstGroupFooter(sbt.sum(taxedColumn).setStyle(subtotalStyle), sbt.sum(notTaxedColumn).setStyle(subtotalStyle),
                        sbt.sum(salesColumn).setStyle(subtotalStyle), sbt.sum(returnColumn).setStyle(subtotalStyle), sbt.sum(totalAmountColumn).setStyle(subtotalStyle),
                        sbt.sum(totalTaxColumn).setStyle(subtotalStyle))
                .subtotalsAtSummary(taxedSum, notTaxedSum, salesSum, returnSum, totalAmountSum, totalTaxSum)
                .summary(cmp.line(),
                        cmp.verticalGap(10),
                        createSumComponent("Sales:", salesSum),
                        createSumComponent("Returns:", returnSum),
                        createSumComponent("Total (w/o Tax):", totalAmountSum),
                        createSumComponent("Total Tax:", totalTaxSum),
                        createSumLine(),
                        createSumComponent("Total Collected:", totalAmountSum, totalTaxSum)
                )
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(20).setRight(20).setTop(20).setBottom(80));

        return report;
    }

    private JRDataSource createDataSource() {
        List<ReportData> dataSource = new ArrayList<>();
        if (fList != null && !fList.isEmpty()) {
            fList.forEach(invoice -> {
                ReportData data = new ReportData();
                BigDecimal taxed = BigDecimal.ZERO;
                BigDecimal notTaxed = BigDecimal.ZERO;
                BigDecimal totalAmount = BigDecimal.ZERO;
                BigDecimal salesTotal = BigDecimal.ZERO;
                BigDecimal returnTotal = BigDecimal.ZERO;
                List<String> taxCategoryList = new ArrayList<>();
                List<BigDecimal> appliedToList = new ArrayList<>();
                List<BigDecimal> taxList = new ArrayList<>();

                TaxZone tz = getTaxZone(invoice);
                data.setTaxRateType(invoice.getTaxZoneName());
                List<Tax> tList = daoTax.read(Tax_.taxZone, tz);
                BigDecimal taxRate = BigDecimal.ZERO;
                for (Tax t : tList) {
                    if (t.getTaxRate().getRate().compareTo(BigDecimal.ZERO) != 0) {
                        taxRate = t.getTaxRate().getRate();
                    }
                }
                if (taxRate.compareTo(BigDecimal.ZERO) != 0 && invoice.getTaxExemptFlag() != null && invoice.getTaxExemptFlag()) {
                    for (TaxRate e : taxRateList) {
                        if (e.getRate().compareTo(BigDecimal.ZERO) == 0) {
                            data.setTaxRateType(e.getName());
                        }
                    }
                }
                data.setInvoiceNumber(getString(invoice.getInvoiceNumber()));
                if (invoice.getDateInvoiced() != null) {
                    data.setDateInvoiced(getString(invoice.getDateInvoiced()));
                }
                if (invoice.getInvoiceEntries() != null && !invoice.getInvoiceEntries().isEmpty()) {
                    for (InvoiceEntry ie : invoice.getInvoiceEntries()) {
                        if (ie.getItemLookUpCode() != null
                                && !(ie.getComponentFlag() != null && ie.getComponentFlag())
                                && !ie.getItemLookUpCode().equalsIgnoreCase("NOTE:")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("SYSSN")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("CPUSN")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("RDSN")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("MBSN")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("MBCPU")
                                && !ie.getItemLookUpCode().equalsIgnoreCase("PACKAGE")) {
                            if (ie.getQuantity() != null && ie.getQuantity().compareTo(BigDecimal.ZERO) >= 0) {
                                salesTotal = salesTotal.add(zeroIfNull(ie.getPrice()).multiply(zeroIfNull(ie.getQuantity())));
                            } else {
                                returnTotal = returnTotal.add(zeroIfNull(ie.getPrice()).multiply(zeroIfNull(ie.getQuantity())));
                            }
                            String taxClassName = ie.getTaxClassName();
                            if (taxClassName != null && !taxClassName.isEmpty()) {
                                BigDecimal applied = BigDecimal.ZERO;
                                BigDecimal taxAmount = BigDecimal.ZERO;
                                BigDecimal entryAmount = zeroIfNull(ie.getPrice()).multiply(zeroIfNull(ie.getQuantity()));
                                if (taxCategoryList.contains(taxClassName)) {
                                    int index = ArrayUtils.indexOf(taxCategoryList.toArray(), taxClassName);
                                    if (appliedToList.get(index) != null) {
                                        applied = applied.add(appliedToList.get(index)).add(entryAmount);
                                        appliedToList.set(index, applied);
                                        taxAmount = taxAmount.add(taxList.get(index)).add(zeroIfNull(ie.getTaxAmount()));
                                        taxList.set(index, taxAmount);
                                    }
                                } else {
                                    taxCategoryList.add(taxClassName);
                                    appliedToList.add(entryAmount);
                                    taxList.add(zeroIfNull(ie.getTaxAmount()));
                                }
                                if (invoice.getTaxExemptFlag() != null && invoice.getTaxExemptFlag()) {
                                    notTaxed = notTaxed.add(entryAmount);
                                } else {
                                    if (zeroIfNull(ie.getTaxAmount()).compareTo(BigDecimal.ZERO) != 0) {
                                        taxed = taxed.add(entryAmount);
                                    } else {
                                        notTaxed = notTaxed.add(entryAmount);
                                    }
                                }
                            }
                        }
                    }
                }
                salesTotal = salesTotal.setScale(2, RoundingMode.HALF_UP);
                returnTotal = returnTotal.setScale(2, RoundingMode.HALF_UP);
                notTaxed = notTaxed.setScale(2, RoundingMode.HALF_UP);
                taxed = taxed.setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(taxed).add(notTaxed);
                List<String> appliedToStringList = new ArrayList<>();
                appliedToList.forEach(e -> {
                    e = e.setScale(2, RoundingMode.HALF_UP);
                    appliedToStringList.add(getString(e));
                });
                List<String> taxStringList = new ArrayList<>();
                taxList.forEach(e -> {
                    e = e.setScale(2, RoundingMode.HALF_UP);
                    taxStringList.add(getString(e));
                });
                BigDecimal totalTax = taxList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                totalTax = totalTax.setScale(2, RoundingMode.HALF_UP);
                data.setAppliedTo(appliedToStringList);
                data.setTaxCategory(taxCategoryList);
                data.setTax(taxStringList);
                data.setSaleslAmount(salesTotal);
                data.setReturnAmount(returnTotal);
                data.setTotalTax(totalTax);
                data.setTaxed(taxed);
                data.setNotTaxed(notTaxed);
                data.setTotalAmount(totalAmount);
                dataSource.add(data);
            });
        }

        return new JRBeanCollectionDataSource(dataSource);
    }

    public class ReportData {

        private String taxRateType;
        private String dateInvoiced;
        private String invoiceNumber;
        private BigDecimal taxed;
        private BigDecimal notTaxed;
        private BigDecimal totalAmount;
        private BigDecimal totalTax;
        private BigDecimal salesAmount;
        private BigDecimal returnAmount;
        private List<String> appliedTo;
        private List<String> tax;
        private List<String> taxCategory;

        public String getTaxRateType() {
            return taxRateType;
        }

        public void setTaxRateType(String taxRateType) {
            this.taxRateType = taxRateType;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getDateInvoiced() {
            return dateInvoiced;
        }

        public void setDateInvoiced(String dateInvoiced) {
            this.dateInvoiced = dateInvoiced;
        }

        public BigDecimal getTaxed() {
            return taxed;
        }

        public void setTaxed(BigDecimal taxed) {
            this.taxed = taxed;
        }

        public BigDecimal getNotTaxed() {
            return notTaxed;
        }

        public void setNotTaxed(BigDecimal notTaxed) {
            this.notTaxed = notTaxed;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getSalesAmount() {
            return salesAmount;
        }

        public void setSaleslAmount(BigDecimal salesAmount) {
            this.salesAmount = salesAmount;
        }

        public BigDecimal getReturnAmount() {
            return returnAmount;
        }

        public void setReturnAmount(BigDecimal returnAmount) {
            this.returnAmount = returnAmount;
        }

        public List<String> getTaxCategory() {
            return taxCategory;
        }

        public void setTaxCategory(List<String> taxCategory) {
            this.taxCategory = taxCategory;
        }

        public List<String> getAppliedTo() {
            return appliedTo;
        }

        public void setAppliedTo(List<String> appliedTo) {
            this.appliedTo = appliedTo;
        }

        public List<String> getTax() {
            return tax;
        }

        public void setTax(List<String> tax) {
            this.tax = tax;
        }

        public BigDecimal getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(BigDecimal totalTax) {
            this.totalTax = totalTax;
        }
    }
}
