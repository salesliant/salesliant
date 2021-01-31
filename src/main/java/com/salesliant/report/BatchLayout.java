package com.salesliant.report;

import com.salesliant.entity.Batch;
import static com.salesliant.report.Templates.createCompanyNameComponent;
import static com.salesliant.report.Templates.createTitleComponent;
import static com.salesliant.report.Templates.leftColumnStyle;
import static com.salesliant.report.Templates.rightColumnStyle;
import static com.salesliant.report.Templates.rightColumnTitleStyle;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import java.math.BigDecimal;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class BatchLayout {

    private final Batch fBatch;
    private BigDecimal subTotalAmount = BigDecimal.ZERO;
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal overShort = BigDecimal.ZERO;
    private BigDecimal collectedTotal = BigDecimal.ZERO;
    private BigDecimal deposit = BigDecimal.ZERO;
    private BigDecimal cashDeposit = BigDecimal.ZERO;
    private String note = "";
    private final String title = "Register Balance Report";

    public BatchLayout(Batch batch) {
        this.fBatch = batch;
        cashDeposit = deposit.add(fBatch.getPaymentBatch().getCashCounted()).subtract(fBatch.getPaymentBatch().getCashLeft());
        deposit = deposit.add(fBatch.getPaymentBatch().getCashCounted()).subtract(fBatch.getPaymentBatch().getCashLeft())
                .add(fBatch.getPaymentBatch().getCheckCounted());
        overShort = overShort
                .add(fBatch.getPaymentBatch().getCashCounted().subtract(fBatch.getPaymentBatch().getCashTendered()).subtract(fBatch.getOpeningTotal()))
                .add(fBatch.getPaymentBatch().getCheckCounted().subtract(fBatch.getPaymentBatch().getCheckTendered()))
                .add(fBatch.getPaymentBatch().getCreditCardCounted().subtract(fBatch.getPaymentBatch().getCreditCardTendered()))
                .add(fBatch.getPaymentBatch().getGiftCardCounted().subtract(fBatch.getPaymentBatch().getGiftCardTendered()))
                .add(fBatch.getPaymentBatch().getGiftCertificateCounted().subtract(fBatch.getPaymentBatch().getGiftCertificateTendered()))
                .add(fBatch.getPaymentBatch().getCouponCounted().subtract(fBatch.getPaymentBatch().getCouponTendered()))
                .add(fBatch.getPaymentBatch().getOnAccountCounted().subtract(fBatch.getPaymentBatch().getOnAccountTendered()));
        if (fBatch.getInvoices() != null && !fBatch.getInvoices().isEmpty()) {
            fBatch.getInvoices().forEach(e -> {
                subTotalAmount = subTotalAmount.add(e.getSubTotal());
                taxAmount = taxAmount.add(e.getTaxAmount());
            });
        }
        collectedTotal = subTotalAmount
                .add(taxAmount)
                .add(fBatch.getPaidToAccount())
                .subtract(fBatch.getCreditRedeemed())
                .add(overShort)
                .add(fBatch.getDepositMade()).subtract(fBatch.getDepositRedeemed())
                .add(fBatch.getOpeningTotal()).subtract(fBatch.getClosingTotal())
                .subtract(fBatch.getPaymentBatch().getOnAccountCounted());
        if (fBatch.getPaymentBatch() != null && fBatch.getPaymentBatch().getNote() != null && !fBatch.getPaymentBatch().getNote().isEmpty()) {
            note = fBatch.getPaymentBatch().getNote();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        TextColumnBuilder<String> textColumn = col.column("", "disc", type.stringType()).setFixedWidth(120);
        textColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> cashColumn = col.column("CASH", "cash", type.bigDecimalType()).setFixedWidth(72);
        cashColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> checkColumn = col.column("CHECKS", "check", type.bigDecimalType()).setFixedWidth(72);
        checkColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> chargeColumn = col.column("CHARGES", "charge", type.bigDecimalType()).setFixedWidth(72);
        chargeColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> couponColumn = col.column("COUPON", "coupon", type.bigDecimalType()).setFixedWidth(70);
        couponColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> giftColumn = col.column("GIFT", "gift", type.bigDecimalType()).setFixedWidth(62);
        giftColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<BigDecimal> giftCardColumn = col.column("GIFT Card", "giftCard", type.bigDecimalType()).setFixedWidth(62);
        giftCardColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        report.setTemplate(Templates.batchReportTemplate)
                .setColumnStyle(rightColumnStyle)
                .setSubtotalStyle(rightColumnTitleStyle)
                .columns(textColumn, cashColumn, checkColumn, chargeColumn, couponColumn, giftColumn, giftCardColumn)
                .title(createTitleComponent(title),
                        cmp.horizontalList().setStyle(stl.style(5)).add(
                                cmp.hListCell(createRegisterComponent()),
                                cmp.hListCell(createCompanyNameComponent())),
                        cmp.verticalGap(10))
                .summary(createSummaryComponent())
                .pageFooter(cmp.pageXofY())
                .setDataSource(createDataSource());
        report.setPageMargin(DynamicReports.margin().setLeft(30).setRight(30).setTop(20).setBottom(80));

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
        return cmp.text(register).setStyle(stl.style().setFontSize(8).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
    }

    private ComponentBuilder<?, ?> createSumGroup() {
        HorizontalListBuilder horizontalList = cmp.horizontalList();
        horizontalList.add(cmp.filler(), cmp.text("Total Sales without Tax:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(subTotalAmount)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Tax Collected:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(taxAmount)).setStyle(stl.style(1).setBottomBorder(stl.pen1Point())).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Total Sales:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(subTotalAmount.add(taxAmount))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("-Sales On Account:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(zeroIfNull(fBatch.getPaymentBatch().getOnAccountTendered()))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("-Ending Balance:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(zeroIfNull(fBatch.getClosingTotal()))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("+Starting Balance:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(zeroIfNull(fBatch.getOpeningTotal()))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        if (fBatch.getDepositMade() != null && fBatch.getDepositMade().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("+Deposit Made:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fBatch.getDepositMade())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        if (fBatch.getDepositRedeemed() != null && fBatch.getDepositRedeemed().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("-Deposit Redeemed:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fBatch.getDepositRedeemed().negate())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        if (fBatch.getCreditMade() != null && fBatch.getCreditMade().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("+Credit Made:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fBatch.getCreditMade())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        if (fBatch.getCreditRedeemed() != null && fBatch.getCreditRedeemed().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("-Credit Redeemed:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fBatch.getCreditRedeemed().negate())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        if (fBatch.getPaidToAccount() != null && fBatch.getPaidToAccount().compareTo(BigDecimal.ZERO) != 0) {
            horizontalList.add(cmp.filler(), cmp.text("+AR Payment:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                    cmp.text(getString(fBatch.getPaidToAccount())).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
            horizontalList.newRow();
        }
        horizontalList.add(cmp.filler(), cmp.text("+Over/Short:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(overShort)).setStyle(stl.style(1).setBottomBorder(stl.pen1Point())).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("TOTAL:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(collectedTotal)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.verticalGap(10));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Cash:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(cashDeposit)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Check:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(zeroIfNull(fBatch.getPaymentBatch().getCheckCounted()))).setStyle(stl.style(1).setBottomBorder(stl.pen1Point())).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Subtotal:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(cashDeposit.add(zeroIfNull(fBatch.getPaymentBatch().getCheckCounted())))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Charge:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(zeroIfNull(fBatch.getPaymentBatch().getCreditCardCounted()))).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        horizontalList.add(cmp.filler(), cmp.text("Your Deposit:").setStyle(stl.style(1)).setFixedWidth(150).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT),
                cmp.text(getString(deposit)).setStyle(stl.style(1)).setFixedWidth(65).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));
        horizontalList.newRow();
        return horizontalList;
    }

    private ComponentBuilder<?, ?> createSummaryComponent() {
        VerticalListBuilder horizontalList = cmp.verticalList();
        horizontalList.add(cmp.line());
        horizontalList.add(cmp.verticalGap(5));
        horizontalList.add(cmp.horizontalList().setStyle(stl.style(1)).add(
                cmp.hListCell(cmp.text(note).setWidth(250).setStyle(leftColumnStyle)).heightFixedOnTop(),
                cmp.hListCell(createSumGroup()).heightFixedOnTop()));
        return horizontalList;
    }

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("disc", "cash", "check", "charge", "coupon", "gift", "giftCard");
        if (fBatch != null) {
            BigDecimal cashShortAmount = BigDecimal.ZERO;
            BigDecimal cashOverAmount = BigDecimal.ZERO;
            BigDecimal cashDiff = fBatch.getPaymentBatch().getCashCounted().subtract(fBatch.getPaymentBatch().getCashTendered()).subtract(fBatch.getOpeningTotal());
            if (cashDiff.compareTo(BigDecimal.ZERO) > 0) {
                cashOverAmount = cashDiff;
            } else if (cashDiff.compareTo(BigDecimal.ZERO) < 0) {
                cashShortAmount = cashDiff.negate();
            }
            BigDecimal checkShortAmount = BigDecimal.ZERO;
            BigDecimal checkOverAmount = BigDecimal.ZERO;
            BigDecimal checkDiff = fBatch.getPaymentBatch().getCheckCounted().subtract(fBatch.getPaymentBatch().getCheckTendered());
            if (checkDiff.compareTo(BigDecimal.ZERO) > 0) {
                checkOverAmount = checkDiff;
            } else if (checkDiff.compareTo(BigDecimal.ZERO) < 0) {
                checkShortAmount = checkDiff.negate();
            }
            BigDecimal creditCardShortAmount = BigDecimal.ZERO;
            BigDecimal creditCardOverAmount = BigDecimal.ZERO;
            BigDecimal creditCardDiff = fBatch.getPaymentBatch().getCreditCardCounted().subtract(fBatch.getPaymentBatch().getCreditCardTendered());
            if (creditCardDiff.compareTo(BigDecimal.ZERO) > 0) {
                creditCardOverAmount = creditCardDiff;
            } else if (creditCardDiff.compareTo(BigDecimal.ZERO) < 0) {
                creditCardShortAmount = creditCardDiff.negate();
            }
            BigDecimal couponShortAmount = BigDecimal.ZERO;
            BigDecimal couponOverAmount = BigDecimal.ZERO;
            BigDecimal couponDiff = fBatch.getPaymentBatch().getCouponCounted().subtract(fBatch.getPaymentBatch().getCouponTendered());
            if (couponDiff.compareTo(BigDecimal.ZERO) > 0) {
                couponOverAmount = couponDiff;
            } else if (couponDiff.compareTo(BigDecimal.ZERO) < 0) {
                couponShortAmount = couponDiff.negate();
            }
            BigDecimal giftCertificateShortAmount = BigDecimal.ZERO;
            BigDecimal giftCertificateOverAmount = BigDecimal.ZERO;
            BigDecimal giftCertificateDiff = fBatch.getPaymentBatch().getGiftCertificateCounted().subtract(fBatch.getPaymentBatch().getGiftCertificateTendered());
            if (giftCertificateDiff.compareTo(BigDecimal.ZERO) > 0) {
                giftCertificateOverAmount = giftCertificateDiff;
            } else if (giftCertificateDiff.compareTo(BigDecimal.ZERO) < 0) {
                giftCertificateShortAmount = giftCertificateDiff.negate();
            }
            BigDecimal giftCardShortAmount = BigDecimal.ZERO;
            BigDecimal giftCardOverAmount = BigDecimal.ZERO;
            BigDecimal giftCardDiff = fBatch.getPaymentBatch().getGiftCardCounted().subtract(fBatch.getPaymentBatch().getGiftCardTendered());
            if (giftCardDiff.compareTo(BigDecimal.ZERO) > 0) {
                giftCardOverAmount = giftCardDiff;
            } else if (giftCardDiff.compareTo(BigDecimal.ZERO) < 0) {
                giftCardShortAmount = giftCardDiff.negate();
            }
            dataSource.add("Your Count:", fBatch.getPaymentBatch().getCashCounted(), fBatch.getPaymentBatch().getCheckCounted(),
                    fBatch.getPaymentBatch().getCreditCardCounted(), fBatch.getPaymentBatch().getCouponCounted(), fBatch.getPaymentBatch().getGiftCertificateCounted());
            dataSource.add("Less Starting Balance:", zeroIfNull(fBatch.getOpeningTotal()), null, null, null, null);
            dataSource.add("Actual Cash Received:", fBatch.getPaymentBatch().getCashCounted().subtract(zeroIfNull(fBatch.getOpeningTotal())), null, null, null, null);
            dataSource.add("Computer Reported:", fBatch.getPaymentBatch().getCashTendered(), fBatch.getPaymentBatch().getCheckTendered(), fBatch.getPaymentBatch().getCreditCardTendered(),
                    fBatch.getPaymentBatch().getCouponTendered(), fBatch.getPaymentBatch().getGiftCertificateTendered(), fBatch.getPaymentBatch().getGiftCardTendered());
            dataSource.add("Over:", cashOverAmount, checkOverAmount, creditCardOverAmount, couponOverAmount, giftCertificateOverAmount, giftCardOverAmount);
            dataSource.add("Short:", cashShortAmount, checkShortAmount, creditCardShortAmount, couponShortAmount, giftCertificateShortAmount, giftCardShortAmount);
            dataSource.add("Change Left In Register:", zeroIfNull(fBatch.getClosingTotal()), null, null, null, null);
        }
        return dataSource;
    }
}
