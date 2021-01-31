package com.salesliant.report;

import com.salesliant.entity.Customer;
import com.salesliant.entity.Deposit;
import static com.salesliant.report.Templates.createCompanyComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.decimalType;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.leftColumnTitleStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getString;
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

public class DepositListReportLayout {

    private final List<Deposit> fList;

    public DepositListReportLayout(List<Deposit> list) {
        this.fList = list;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> customerColumn = col.column("Customer", "customer", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(230);
        TextColumnBuilder<String> orderColumn = col.column("Order", "order", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(100);
        TextColumnBuilder<String> datePurchasedColumn = col.column("Date", "datePurchased", type.stringType()).setTitleStyle(leftColumnTitleStyle).setStyle(leftColumnStyle).setFixedWidth(100);
        TextColumnBuilder<BigDecimal> amountColumn = col.column("Amount", "amount", decimalType).setTitleStyle(rightColumnTitleStyle).setStyle(rightColumnStyle).setFixedWidth(100);

        AggregationSubtotalBuilder<BigDecimal> amountSum = sbt.sum(amountColumn).setStyle(subtotalBoldStyle).setLabel("Total");

        report.setTemplate(Templates.invoiceReportTemplate)
                .columns(customerColumn, orderColumn, datePurchasedColumn, amountColumn)
                .title(createTitleComponent("Deposit List"),
                        cmp.horizontalList().setStyle(stl.style(5)).add(cmp.hListCell(createCompanyComponent())),
                        cmp.verticalGap(10))
                .sortBy(asc(datePurchasedColumn))
                .subtotalsAtSummary(amountSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

        return report;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("customer", "order", "datePurchased", "amount");
        if (!fList.isEmpty()) {
            fList.stream().forEach(e -> {
                Customer customer = e.getCustomer();
                String name;
                if (customer.getCompany() != null && !customer.getCompany().isEmpty()) {
                    name = customer.getCompany();
                } else {
                    name = !isEmpty(customer.getFirstName()) ? customer.getFirstName() : ""
                            + (!isEmpty(customer.getFirstName()) ? " " : "")
                            + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "");
                }
                String order = "";
                if (e.getSalesOrder() != null && e.getSalesOrder().getSalesOrderNumber() != null) {
                    order = e.getSalesOrder().getSalesOrderNumber().toString();
                }
                String date = getString(e.getDateCreated());
                dataSource.add(name, order, date, e.getAmount());
            });
        }
        return dataSource;
    }
}
