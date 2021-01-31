package com.salesliant.report;

import com.salesliant.entity.Customer;
import com.salesliant.entity.GiftCertificate;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.math.BigDecimal;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import static com.salesliant.report.Templates.subtotalBoldStyle;

public class GiftCertificateListReportLayout {

    private final List<GiftCertificate> fList;

    public GiftCertificateListReportLayout(List<GiftCertificate> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> giftCertificateColumn = col.column("Gift Certificate", "giftCertificate", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(90);
        TextColumnBuilder<String> customerColumn = col.column("Customer", "customer", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(110);
        TextColumnBuilder<String> invoiceColumn = col.column("Invoice", "invoice", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(80);
        TextColumnBuilder<String> datePurchasedColumn = col.column("Date", "datePurchased", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(90);
        TextColumnBuilder<BigDecimal> originalAmountColumn = col.column("Amount", "originalAmount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);
        TextColumnBuilder<BigDecimal> balanceColumn = col.column("Balance", "balance", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(80);

        AggregationSubtotalBuilder<BigDecimal> totalBalanceSum = sbt.sum(balanceColumn).setStyle(subtotalBoldStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(giftCertificateColumn, customerColumn, invoiceColumn, datePurchasedColumn, originalAmountColumn, balanceColumn)
                .title(createTitleComponent("Gift Certificate Outstanding List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(datePurchasedColumn))
                .subtotalsAtSummary(totalBalanceSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("giftCertificate", "customer", "invoice", "datePurchased", "originalAmount", "balance");
        if (!fList.isEmpty()) {
            fList.stream().forEach((p) -> {
                Customer customer = p.getCustomer();
                String name;
                if (customer.getCompany() != null && !customer.getCompany().isEmpty()) {
                    name = customer.getCompany();
                } else {
                    name = !isEmpty(customer.getFirstName()) ? customer.getFirstName() : ""
                            + (!isEmpty(customer.getFirstName()) ? " " : "")
                            + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "");
                }
                dataSource.add(p.getCode(), name, p.getInvoice().getInvoiceNumber().toString(), getDateDateFormat().format(p.getDatePurchased()), p.getOriginalAmount(), p.getBalance());
            });
        }
        return dataSource;
    }
}
