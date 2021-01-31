package com.salesliant.report;

import com.salesliant.entity.AccountPayable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.report.Templates.subtotalStyle;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AccountPayableReportLayout {

    private final List<List<AccountPayable>> fList;

    public AccountPayableReportLayout(List<List<AccountPayable>> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(200);
        TextColumnBuilder<List> typeColumn = col.column("Type", "type", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(60);
        TextColumnBuilder<List> invoiceNumberColumn = col.column("Invoice #", "invoiceNumber", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);
        TextColumnBuilder<List> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);
        TextColumnBuilder<List> dateDueColumn = col.column("Due", "dateDue", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);
        TextColumnBuilder<List> amountColumn = col.column("Amount", "amount", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(70);
        TextColumnBuilder<List> postedColumn = col.column("Posted", "posted", type.listType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> totalColumn = col.column("Total", "total", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle.setVerticalTextAlignment(VerticalTextAlignment.TOP)).setFixedWidth(70);

        AggregationSubtotalBuilder<BigDecimal> totalSum = sbt.sum(totalColumn).setStyle(subtotalStyle).setLabel("Total");
        ColumnGroupBuilder nameGroup = grp.group(nameColumn);

        report.setTemplate(Templates.invoiceReportTemplate)
                .setSubtotalStyle(subtotalStyle)
                .columns(nameColumn, typeColumn, invoiceNumberColumn, dateInvoicedColumn, dateDueColumn, amountColumn, postedColumn, totalColumn)
                .title(createTitleComponent("AP Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(nameColumn))
                .groupBy(nameGroup)
                .subtotalsAtSummary(totalSum)
                .summary(cmp.line(),
                        cmp.verticalGap(5),
                        cmp.horizontalList(cmp.verticalGap(8)))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        List<ReportData> dataSource = new ArrayList<>();
        if (fList != null && !fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                ReportData data = new ReportData();
                String name;
                if (p.get(0).getVendor().getCompany() != null) {
                    name = p.get(0).getVendor().getCompany();
                } else {
                    name = p.get(0).getVendor().getVendorContactName();
                }
                data.setName(name);
                List<String> typeList = new ArrayList<>();
                List<String> invoiceList = new ArrayList<>();
                List<String> dateInvoicedList = new ArrayList<>();
                List<String> dateDueList = new ArrayList<>();
                List<String> amountList = new ArrayList<>();
                List<String> postedList = new ArrayList<>();
                BigDecimal total = p.stream().map(AccountPayable::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                p.stream().forEach((e) -> {
                    if (e.getAccountPayableType() != null && e.getAccountPayableType() == DBConstants.TYPE_APAR_INV) {
                        typeList.add("Inv");
                    } else if (e.getAccountPayableType() != null && e.getAccountPayableType() == DBConstants.TYPE_APAR_CRE) {
                        typeList.add("Cre");
                    }
                    if (e.getVendorInvoiceNumber() != null) {
                        invoiceList.add(e.getVendorInvoiceNumber());
                    }
                    if (e.getDateInvoiced() != null) {
                        dateInvoicedList.add(getString(e.getDateInvoiced()));
                    }
                    if (e.getDateDue() != null) {
                        dateDueList.add(getString(e.getDateDue()));
                    }
                    if (e.getTotalAmount() != null) {
                        amountList.add(getString(e.getTotalAmount()));
                    }
                    if (e.getPostedTag() != null) {
                        if (e.getPostedTag()) {
                            postedList.add("Y");
                        } else {
                            postedList.add("N");
                        }
                    }
                });
                data.setInvoiceNumber(invoiceList);
                data.setType(typeList);
                data.setAmount(amountList);
                data.setDateInvoiced(dateInvoicedList);
                data.setDateDue(dateDueList);
                data.setPosted(postedList);
                data.setTotal(total);
                dataSource.add(data);
            });
        }
        return new JRBeanCollectionDataSource(dataSource);
    }

    public class ReportData {

        private String name;
        private List<String> type;
        private List<String> invoiceNumber;
        private List<String> dateInvoiced;
        private List<String> dateDue;
        private List<String> amount;
        private List<String> posted;
        private BigDecimal total;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public List<String> getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(List<String> invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public List<String> getDateInvoiced() {
            return dateInvoiced;
        }

        public void setDateInvoiced(List<String> dateInvoiced) {
            this.dateInvoiced = dateInvoiced;
        }

        public List<String> getDateDue() {
            return dateDue;
        }

        public void setDateDue(List<String> dateDue) {
            this.dateDue = dateDue;
        }

        public List<String> getAmount() {
            return amount;
        }

        public void setAmount(List<String> amount) {
            this.amount = amount;
        }

        public List<String> getPosted() {
            return posted;
        }

        public void setPosted(List<String> posted) {
            this.posted = posted;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }
    }
}
