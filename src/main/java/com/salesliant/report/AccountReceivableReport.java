package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createLocalDateRangeComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class AccountReceivableReport {

    private final List<AccountReceivable> fList;
    private final LocalDateTime fFrom;
    private final LocalDateTime fTo;

    public AccountReceivableReport(List<AccountReceivable> list, LocalDateTime from, LocalDateTime to) {
        this.fList = list;
        this.fFrom = from;
        this.fTo = to;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(115);
        TextColumnBuilder<String> invoiceColumn = col.column("Invoice", "invoice", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(50);
        TextColumnBuilder<String> dateInvoicedColumn = col.column("Date", "dateInvoiced", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<String> dateDueColumn = col.column("Due", "dateDue", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<String> typeColumn = col.column("Type", "type", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(30);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "originalAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> paidAmountColumn = col.column("Paid", "paidAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> discountAmountColumn = col.column("Discount", "discountAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<BigDecimal> balanceAmountColumn = col.column("Balance", "balance", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(50);
        TextColumnBuilder<String> statusColumn = col.column("Status", "status", type.stringType()).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(40);

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(nameColumn, invoiceColumn, dateInvoicedColumn, dateDueColumn, typeColumn, originalAmountColumn, paidAmountColumn, discountAmountColumn, balanceAmountColumn, statusColumn)
                .title(createTitleComponent("AR Transaction Report"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent()),
                                cmp.hListCell(createLocalDateRangeComponent(fFrom, fTo))),
                        cmp.verticalGap(10))
                .sortBy(desc(dateInvoicedColumn))
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("name", "invoice", "dateInvoiced", "dateDue", "type", "originalAmount", "paidAmount", "discountAmount", "balance", "status");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                String name = "";
                if (p.getCustomer().getCompany() != null) {
                    name = p.getCustomer().getCompany();
                } else if (p.getCustomer().getFirstName() == null && p.getCustomer().getLastName() == null) {
                    name = p.getCustomer().getAddress1();
                } else {
                    name = name + (!isEmpty(p.getCustomer().getFirstName()) ? p.getCustomer().getFirstName() : "")
                            + (!isEmpty(p.getCustomer().getFirstName()) ? " " : "")
                            + (!isEmpty(p.getCustomer().getLastName()) ? p.getCustomer().getLastName() : "");
                }
                String invoice = "";
                if (p.getInvoiceNumber() != null) {
                    invoice = p.getInvoiceNumber().toString();
                }
                String dateInvoiced = "";
                if (p.getDateProcessed() != null) {
                    dateInvoiced = getString(p.getDateProcessed());
                }
                String dateDue = "";
                if (p.getDateDue() != null) {
                    dateDue = getString(p.getDateDue());
                }
                String status;
                if (p.getStatus() != null && p.getStatus().equals(DBConstants.STATUS_OPEN)) {
                    status = "Open";
                } else if (p.getStatus() != null && p.getStatus().equals(DBConstants.STATUS_CLOSE)) {
                    status = "Close";
                } else if (p.getStatus() != null && p.getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
                    status = "Processed";
                } else {
                    status = "";
                }
                BigDecimal balanceAmount = BigDecimal.ZERO;
                BigDecimal totalAmount = BigDecimal.ZERO;
                BigDecimal paidAmount = BigDecimal.ZERO;
                if (p.getAccountReceivableType() != null) {
                    if (p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT) || p.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                        balanceAmount = p.getBalanceAmount().negate();
                        totalAmount = p.getTotalAmount().negate();
                        paidAmount = p.getPaidAmount().negate();
                    } else {
                        balanceAmount = p.getBalanceAmount();
                        totalAmount = p.getTotalAmount();
                        paidAmount = p.getPaidAmount();
                    }
                }
                dataSource.add(name, invoice, dateInvoiced, dateDue, p.getAccountReceivableType(), totalAmount, paidAmount, p.getDiscountAmount(), balanceAmount, status);
            });
        }
        return dataSource;
    }
}
