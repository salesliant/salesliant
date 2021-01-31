package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Batch;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.DropPayout_;
import com.salesliant.entity.Payment;
import com.salesliant.entity.PaymentType;
import com.salesliant.entity.PaymentType_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lewis
 */
public class DropPayoutUI extends BaseListUI<DropPayout> {

    private final BaseDao<DropPayout> daoDropPayout = new BaseDao<>(DropPayout.class);
    private final BaseDao<PaymentType> daoPaymentType = new BaseDao<>(PaymentType.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final DataUI dataUI = new DataUI(DropPayout.class);
    private final RadioButton cashOutBtn = new RadioButton("Cash Out  ");
    private final RadioButton cashInBtn = new RadioButton("Cash In  ");
    private final RadioButton cashCheckBtn = new RadioButton("Cash A Check  ");
    private final PaymentType cashPaymentType;
    private final PaymentType checkPaymentType;
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(DropPayoutUI.class.getName());

    public DropPayoutUI() {
        fEditPane = createDropPayoutPane();
        cashPaymentType = daoPaymentType.find(PaymentType_.verificationType, DBConstants.TYPE_VERIFICATION_CASH);
        checkPaymentType = daoPaymentType.find(PaymentType_.verificationType, DBConstants.TYPE_VERIFICATION_CHECK);
        init();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_OPEN_CLOSE_REGISTER:
                Batch batch = Config.getBatch();
                fEntity = new DropPayout();
                fEntity.setAmount(BigDecimal.ZERO);
                fEntity.setEmployee(Config.getEmployee());
                fEntity.setStore(Config.getStore());
                fEntity.setCheckTag(Boolean.FALSE);
                fEntity.setBatch(batch);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Open Cash Drawer");
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setDateCreated(new Date());
                        BigDecimal amount = fEntity.getAmount();
                        if (amount.compareTo(BigDecimal.ZERO) != 0) {
                            Payment cashPaymentEntry = new Payment();
                            cashPaymentEntry.setPaymentType(cashPaymentType);
                            cashPaymentEntry.setStore(Config.getStore());
                            cashPaymentEntry.setBatch(batch);
                            if (cashCheckBtn.isSelected()) {
                                fEntity.setCheckTag(Boolean.TRUE);
                                fEntity.setAmount(amount.abs());
                                Payment checkPaymentEntry = new Payment();
                                checkPaymentEntry.setTenderedAmount(amount.abs());
                                checkPaymentEntry.setPaymentType(checkPaymentType);
                                checkPaymentEntry.setStore(Config.getStore());
                                checkPaymentEntry.setDropPayout(fEntity);
                                checkPaymentEntry.setBatch(batch);
                                fEntity.getPayments().add(checkPaymentEntry);
                            } else if (cashOutBtn.isSelected()) {
                                fEntity.setCheckTag(Boolean.FALSE);
                                fEntity.setAmount(amount.abs());
                            } else if (cashInBtn.isSelected()) {
                                fEntity.setCheckTag(Boolean.FALSE);
                                fEntity.setAmount(amount.abs().negate());
                            }
                            cashPaymentEntry.setTenderedAmount(fEntity.getAmount().negate());
                            cashPaymentEntry.setDropPayout(fEntity);
                            fEntity.getPayments().add(cashPaymentEntry);
                            daoDropPayout.insert(fEntity);
                            batch.getDropPayouts().add(fEntity);
                            BigDecimal dropPayoutAmount = fEntity.getPayments().stream().map(Payment::getTenderedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                            BigDecimal totalDropPayoutAmount = batch.getDropPayoutAmount();
                            BigDecimal totalTenderAmount = batch.getTotalTendered();
                            totalDropPayoutAmount = totalDropPayoutAmount.add(dropPayoutAmount);
                            totalTenderAmount = totalTenderAmount.add(dropPayoutAmount);
                            batch.setDropPayoutAmount(totalDropPayoutAmount);
                            batch.setTotalTendered(totalTenderAmount);
                            daoBatch.update(batch);
                        }
                        handleAction(AppConstants.ACTION_CLOSE);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(DropPayout_.amount).requestFocus());
                fInputDialog.showDialog();
                break;
        }
    }

    private void init() {
        handleAction(AppConstants.ACTION_OPEN_CLOSE_REGISTER);
    }

    private GridPane createDropPayoutPane() {
        GridPane dropPayoutPane = new GridPane();
        dropPayoutPane.getStyleClass().add("editView");
        final ToggleGroup group = new ToggleGroup();
        cashOutBtn.setSelected(true);
        cashInBtn.setSelected(false);
        cashCheckBtn.setSelected(false);
        cashOutBtn.setToggleGroup(group);
        cashInBtn.setToggleGroup(group);
        cashCheckBtn.setToggleGroup(group);
        HBox hb = new HBox();
        hb.getChildren().addAll(cashOutBtn, cashInBtn, cashCheckBtn);
        dropPayoutPane.add(hb, 1, 0);
        add(dropPayoutPane, "Amount: ", dataUI.createTextField(DropPayout_.amount), fListener, 1);
        add(dropPayoutPane, "Recipient: ", dataUI.createTextField(DropPayout_.recipient), fListener, 2);
        add(dropPayoutPane, "Note: ", dataUI.createTextArea(DropPayout_.note), 150, 300, fListener, 3);
        dataUI.getTextField(DropPayout_.amount).setMaxWidth(120);
        dataUI.getTextField(DropPayout_.recipient).setMaxWidth(200);

        return dropPayoutPane;
    }
}
