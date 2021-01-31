package com.salesliant.report;

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Payment;
import static com.salesliant.report.Templates.createCompanyNameComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.subtotalBatchDetailStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BatchDetailLayout {

    private final Batch fBatch;
    private final String title = "Register Balance Detail Report";

    public BatchDetailLayout(Batch batch) {
        this.fBatch = batch;
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> typeColumn = col.column("", "type", type.stringType()).setFixedWidth(45).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setFixedWidth(45).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> numberColumn = col.column("Number", "number", type.stringType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> cashInColumn = col.column("Cash In", "cashIn", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> cashOutColumn = col.column("Cash Out", "cashOut", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> checkColumn = col.column("Checks", "check", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> chargeColumn = col.column("Charges", "charge", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> couponColumn = col.column("Coupon", "coupon", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> giftColumn = col.column("Gift", "gift", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> creditColumn = col.column("Credit", "credit", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> onAccountColumn = col.column("On Account", "onAccount", type.bigDecimalType()).setFixedWidth(51).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> clerkColumn = col.column("Clerk", "clerk", type.stringType()).setFixedWidth(40).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        ColumnGroupBuilder typeGroup = grp.group(typeColumn);

        AggregationSubtotalBuilder<BigDecimal> cashInSum = sbt.sum(cashInColumn).setStyle(subtotalBatchDetailStyle).setLabel("Total:");
        AggregationSubtotalBuilder<BigDecimal> cashOutSum = sbt.sum(cashOutColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> checkSum = sbt.sum(checkColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> chargeSum = sbt.sum(chargeColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> couponSum = sbt.sum(couponColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> giftSum = sbt.sum(giftColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> creditSum = sbt.sum(creditColumn).setStyle(subtotalBatchDetailStyle).setLabel("");
        AggregationSubtotalBuilder<BigDecimal> onAccountSum = sbt.sum(onAccountColumn).setStyle(subtotalBatchDetailStyle).setLabel("");

        report.setTemplate(Templates.batchDetailReportTemplate)
                .title(createTitleComponent(title),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createRegisterComponent()),
                                cmp.hListCell(createCompanyNameComponent())),
                        cmp.verticalGap(10))
                .columns(typeColumn, dateColumn, numberColumn, cashInColumn, cashOutColumn, checkColumn, chargeColumn, couponColumn, giftColumn, creditColumn, onAccountColumn, clerkColumn)
                //                .sortBy(asc(dateColumn))
                .groupBy(typeGroup)
                .subtotalsAtFirstGroupFooter(
                        sbt.sum(cashInColumn), sbt.sum(cashOutColumn), sbt.sum(checkColumn), sbt.sum(chargeColumn), sbt.sum(couponColumn),
                        sbt.sum(giftColumn), sbt.sum(creditColumn), sbt.sum(onAccountColumn)).setSubtotalStyle(subtotalBatchDetailStyle)
                .subtotalsAtSummary(
                        cashInSum, cashOutSum, checkSum, chargeSum, couponSum, giftSum, creditSum, onAccountSum)
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(20).setRight(20).setTop(20).setBottom(80));

        return report;
    }

    private ComponentBuilder<?, ?> createRegisterComponent() {
        String register = "";
        if (fBatch.getStation() != null && fBatch.getStation().getDescription() != null) {
            register = register + "Register: " + fBatch.getStation().getDescription() + "\n";
        } else {
            register = register + "Register: " + "\n";
        }
        if (fBatch.getEmployee() != null && fBatch.getEmployee().getNameOnSalesOrder() != null) {
            register = register + "By: " + fBatch.getEmployee().getNameOnSalesOrder() + "\n";
        } else {
            register = register + "By: " + "\n";
        }
        if (fBatch.getDateClosed() != null) {
            register = register + "Date: " + getString(fBatch.getDateClosed());
        } else {
            register = register + "Date: ";
        }
        return cmp.text(register).setStyle(stl.style().setFontSize(7).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    private JRDataSource createDataSource() {
        List<ReportData> dataSource = new ArrayList<>();
        if (fBatch.getAccountReceivables() != null && !fBatch.getAccountReceivables().isEmpty()) {
            fBatch.getAccountReceivables().forEach(ar -> {
                String date = "";
                BigDecimal cashIn = BigDecimal.ZERO;
                BigDecimal cashOut = BigDecimal.ZERO;
                BigDecimal check = BigDecimal.ZERO;
                BigDecimal creditCard = BigDecimal.ZERO;
                BigDecimal coupon = BigDecimal.ZERO;
                BigDecimal gift = BigDecimal.ZERO;
                BigDecimal onAccount = BigDecimal.ZERO;
                BigDecimal credit = BigDecimal.ZERO;
                if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT)) {
                    ReportData data = new ReportData();
                    String type = "AR Payment";
                    if (ar.getPayments() != null && !ar.getPayments().isEmpty()) {
                        for (Payment pe : ar.getPayments()) {
                            if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !pe.getPaymentType().getIsNetTerm()) {
                                cashIn = cashIn.add(pe.getTenderedAmount());
                                cashOut = cashOut.add(pe.getChangeAmount());
                            }
                            if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                                check = check.add(pe.getTenderedAmount());
                            }
                            if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                                creditCard = creditCard.add(pe.getTenderedAmount());
                            }
                            if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                                gift = gift.add(pe.getTenderedAmount());
                            }
                            if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                                gift = gift.add(pe.getTenderedAmount());
                            }
                        }
                    }
                    if (ar.getDateProcessed() != null) {
                        date = getString(ar.getDateProcessed());
                    }
                    data.setType(type);
                    data.setDate(date);
                    data.setNumber(getString(ar.getInvoiceNumber()));
                    data.setCashIn(cashIn);
                    data.setCashOut(cashOut);
                    data.setCheck(check);
                    data.setCharge(creditCard);
                    data.setCoupon(coupon);
                    data.setGift(gift);
                    data.setCredit(credit);
                    data.setOnAccount(onAccount);
                    data.setClerk(getString(ar.getEmployeeName()));
                    dataSource.add(data);
                } else if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                    ReportData data = new ReportData();
                    String type = "AR Credit Made";
                    if (ar.getDateProcessed() != null) {
                        date = getString(ar.getDateProcessed());
                    }
                    data.setType(type);
                    data.setDate(date);
                    data.setNumber(getString(ar.getInvoiceNumber()));
                    data.setCashIn(cashIn);
                    data.setCashOut(cashOut);
                    data.setCheck(check);
                    data.setCharge(creditCard);
                    data.setCoupon(coupon);
                    data.setGift(gift);
                    data.setCredit(ar.getPaidAmount());
                    data.setOnAccount(onAccount);
                    data.setClerk(getString(ar.getEmployeeName()));
                    dataSource.add(data);
                } else if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                    ReportData data = new ReportData();
                    String type = "AR Credit Redeemed";
                    if (ar.getDateProcessed() != null) {
                        date = getString(ar.getDateProcessed());
                    }
                    data.setType(type);
                    data.setDate(date);
                    data.setNumber(getString(ar.getInvoiceNumber()));
                    data.setCashIn(cashIn);
                    data.setCashOut(cashOut);
                    data.setCheck(check);
                    data.setCharge(creditCard);
                    data.setCoupon(coupon);
                    data.setGift(gift);
                    data.setCredit(ar.getTotalAmount().negate());
                    data.setOnAccount(onAccount);
                    data.setClerk(getString(ar.getEmployeeName()));
                    dataSource.add(data);
                }
            });
        }
        if (fBatch.getDepositOpenBatchs() != null && !fBatch.getDepositOpenBatchs().isEmpty()) {
            fBatch.getDepositOpenBatchs().forEach(e -> {
                ReportData data = new ReportData();
                String type = "Deposit";
                String date = "";
                BigDecimal cashIn = BigDecimal.ZERO;
                BigDecimal cashOut = BigDecimal.ZERO;
                BigDecimal check = BigDecimal.ZERO;
                BigDecimal creditCard = BigDecimal.ZERO;
                BigDecimal coupon = BigDecimal.ZERO;
                BigDecimal gift = BigDecimal.ZERO;
                BigDecimal onAccount = BigDecimal.ZERO;
                BigDecimal credit = BigDecimal.ZERO;
                String orderNumber;
                String saleName = "";
                if (!e.getPayments().isEmpty()) {
                    for (Payment pe : e.getPayments()) {
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !pe.getPaymentType().getIsNetTerm()) {
                            cashIn = cashIn.add(pe.getTenderedAmount());
                            cashOut = cashOut.add(pe.getChangeAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                            check = check.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                            creditCard = creditCard.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                            gift = gift.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                            gift = gift.add(pe.getTenderedAmount());
                        }
                    }
                }
                if (e.getDateCreated() != null) {
                    date = getString(e.getDateCreated());
                }
                if (e.getSalesOrder() != null) {
                    if (e.getSalesOrder().getSales() != null) {
                        saleName = getString(e.getSalesOrder().getSales().getNameOnSalesOrder());
                    }
                } else if (e.getInvoice() != null) {
                    saleName = getString(e.getInvoice().getSalesName());
                }
                if (e.getInvoice() != null && e.getInvoice().getInvoiceNumber() != null) {
                    orderNumber = getString(e.getInvoice().getInvoiceNumber());
                } else if (e.getOrderNumber() != null && !e.getOrderNumber().isEmpty()) {
                    orderNumber = e.getOrderNumber();
                } else {
                    orderNumber = "";
                }
//                orderNumber = getString(e.getOrderNumber());
                data.setType(type);
                data.setDate(date);
                data.setNumber(orderNumber);
                data.setCashIn(cashIn);
                data.setCashOut(cashOut);
                data.setCheck(check);
                data.setCharge(creditCard);
                data.setCoupon(coupon);
                data.setGift(gift);
                data.setCredit(credit);
                data.setOnAccount(onAccount);
                data.setClerk(saleName);
                dataSource.add(data);
            });
        }
        if (fBatch.getDepositCloseBatchs() != null && !fBatch.getDepositCloseBatchs().isEmpty()) {
            fBatch.getDepositCloseBatchs().forEach(e -> {
                ReportData data = new ReportData();
                String type = "Deposit";
                String date = "";
                BigDecimal cashIn = BigDecimal.ZERO;
                BigDecimal cashOut = BigDecimal.ZERO;
                BigDecimal check = BigDecimal.ZERO;
                BigDecimal creditCard = BigDecimal.ZERO;
                BigDecimal coupon = BigDecimal.ZERO;
                BigDecimal gift = BigDecimal.ZERO;
                BigDecimal onAccount = BigDecimal.ZERO;
                BigDecimal credit = BigDecimal.ZERO;
                String orderNumber;
                String saleName = "";
                if (e.getAmount() != null) {
                    credit = e.getAmount().negate();
                }
                if (e.getDateCreated() != null) {
                    date = getString(e.getDateCreated());
                }
                if (e.getSalesOrder() != null) {
                    if (e.getSalesOrder().getSales() != null) {
                        saleName = getString(e.getSalesOrder().getSales().getNameOnSalesOrder());
                    }
                } else if (e.getInvoice() != null) {
                    saleName = getString(e.getInvoice().getSalesName());
                }
                if (e.getInvoice() != null && e.getInvoice().getInvoiceNumber() != null) {
                    orderNumber = getString(e.getInvoice().getInvoiceNumber());
                } else if (e.getOrderNumber() != null && !e.getOrderNumber().isEmpty()) {
                    orderNumber = e.getOrderNumber();
                } else {
                    orderNumber = "";
                }
//                orderNumber = getString(e.getOrderNumber());
                data.setType(type);
                data.setDate(date);
                data.setNumber(orderNumber);
                data.setCashIn(cashIn);
                data.setCashOut(cashOut);
                data.setCheck(check);
                data.setCharge(creditCard);
                data.setCoupon(coupon);
                data.setGift(gift);
                data.setCredit(credit);
                data.setOnAccount(onAccount);
                data.setClerk(saleName);
                dataSource.add(data);
            });
        }
        if (fBatch.getInvoices() != null && !fBatch.getInvoices().isEmpty()) {
            fBatch.getInvoices().forEach((invoice) -> {
                ReportData data = new ReportData();
                String type = "Invoice";
                String date = "";
                BigDecimal cashIn = BigDecimal.ZERO;
                BigDecimal cashOut = BigDecimal.ZERO;
                BigDecimal check = BigDecimal.ZERO;
                BigDecimal creditCard = BigDecimal.ZERO;
                BigDecimal coupon = BigDecimal.ZERO;
                BigDecimal gift = BigDecimal.ZERO;
                BigDecimal onAccount = BigDecimal.ZERO;
                BigDecimal credit = BigDecimal.ZERO;

                if (invoice.getPayments() != null && !invoice.getPayments().isEmpty()) {
                    for (Payment pe : invoice.getPayments()) {
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !pe.getPaymentType().getIsNetTerm()) {
                            cashIn = cashIn.add(pe.getTenderedAmount());
                            cashOut = cashOut.add(pe.getChangeAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                            check = check.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                            creditCard = creditCard.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                            coupon = coupon.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                            gift = gift.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                            gift = gift.add(pe.getTenderedAmount());
                        }
                        if (pe.getTenderedAmount() != null && pe.getPaymentType().getIsNetTerm() != null && pe.getPaymentType().getIsNetTerm()) {
                            onAccount = onAccount.add(pe.getTenderedAmount());
                        }
                    }
                }
                if (!invoice.getAccountReceivables().isEmpty()) {
                    for (AccountReceivable ar : invoice.getAccountReceivables()) {
                        if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                            credit = credit.add(zeroIfNull(ar.getTotalAmount()));
                        }
                    }
                }
                if (!invoice.getDeposits().isEmpty()) {
                    for (Deposit deposit : invoice.getDeposits()) {
                        if (deposit.getAmount() != null) {
                            credit = credit.add(deposit.getAmount());
                        }
                    }
                }
                if (invoice.getDateInvoiced() != null) {
                    date = getString(invoice.getDateInvoiced());
                }
                data.setType(type);
                data.setDate(date);
                data.setNumber(getString(invoice.getInvoiceNumber()));
                data.setCashIn(cashIn);
                data.setCashOut(cashOut);
                data.setCheck(check);
                data.setCharge(creditCard);
                data.setCoupon(coupon);
                data.setGift(gift);
                data.setCredit(credit);
                data.setOnAccount(onAccount);
                data.setClerk(getString(invoice.getSalesName()));
                dataSource.add(data);
            });
        }
//        Collections.sort(dataSource, (d1, d2) -> d1.getDate().compareTo(d2.getDate()));
        return new JRBeanCollectionDataSource(dataSource);
    }

    public class ReportData {

        private String type;
        private String date;
        private String number;
        private BigDecimal cashIn;
        private BigDecimal cashOut;
        private BigDecimal check;
        private BigDecimal charge;
        private BigDecimal coupon;
        private BigDecimal gift;
        private BigDecimal credit;
        private BigDecimal onAccount;
        private String clerk;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public BigDecimal getCashIn() {
            return cashIn;
        }

        public void setCashIn(BigDecimal cashin) {
            this.cashIn = cashin;
        }

        public BigDecimal getCashOut() {
            return cashOut;
        }

        public void setCashOut(BigDecimal cashout) {
            this.cashOut = cashout;
        }

        public BigDecimal getCheck() {
            return check;
        }

        public void setCheck(BigDecimal check) {
            this.check = check;
        }

        public BigDecimal getCoupon() {
            return coupon;
        }

        public void setCoupon(BigDecimal coupon) {
            this.coupon = coupon;
        }

        public BigDecimal getGift() {
            return gift;
        }

        public void setGift(BigDecimal gift) {
            this.gift = gift;
        }

        public String getClerk() {
            return clerk;
        }

        public void setClerk(String clerk) {
            this.clerk = clerk;
        }

        public BigDecimal getCredit() {
            return credit;
        }

        public void setCredit(BigDecimal credit) {
            this.credit = credit;
        }

        public BigDecimal getOnAccount() {
            return onAccount;
        }

        public void setOnAccount(BigDecimal onaccount) {
            this.onAccount = onaccount;
        }

        public BigDecimal getCharge() {
            return charge;
        }

        public void setCharge(BigDecimal charge) {
            this.charge = charge;
        }
    }
}
