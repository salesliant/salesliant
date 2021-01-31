package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Batch_;
import com.salesliant.report.BatchDetailLayout;
import com.salesliant.report.BatchLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class BatchListUI extends BaseListUI<Batch> {

    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final TextArea fPayment = new TextArea(), fSales = new TextArea(), fCash = new TextArea(), fOther = new TextArea(), fNote = new TextArea();
    private final static String PRINT_BATCH_REPORT = "Print Batch Report";
    private final static String PRINT_BATCH_DETAIL_REPORT = "Print Batch Detail Report";
    private static final Logger LOGGER = Logger.getLogger(BatchListUI.class.getName());

    public BatchListUI() {
        mainView = createMainView();
        List<Batch> list = daoBatch.readOrderBy(Batch_.store, Config.getStore(), Batch_.status, DBConstants.STATUS_CLOSE, Batch_.dateClosed, AppConstants.ORDER_BY_DESC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Batch> observable, Batch newValue, Batch oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                updateInfoPane(fEntity);
            } else {
                fPayment.setText("");
                fSales.setText("");
                fCash.setText("");
                fOther.setText("");
                fNote.setText("");
            }
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case PRINT_BATCH_REPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Platform.runLater(() -> {
                        BatchLayout layout = new BatchLayout(fEntity);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case PRINT_BATCH_DETAIL_REPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Platform.runLater(() -> {
                        BatchDetailLayout layout = new BatchDetailLayout(fEntity);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        Label tableLbl = new Label("List of Batch:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Batch, String> registerCol = new TableColumn<>("Register");
        registerCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getStation() != null && p.getValue().getStation().getDescription() != null) {
                return new SimpleStringProperty(p.getValue().getStation().getDescription());
            } else {
                return null;
            }
        });
        registerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        registerCol.setPrefWidth(90);

        TableColumn<Batch, String> dateCloseCol = new TableColumn<>("Date");
        dateCloseCol.setCellValueFactory(new PropertyValueFactory<>(Batch_.dateClosed.getName()));
        dateCloseCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCloseCol.setPrefWidth(125);

        TableColumn<Batch, String> openningTotalCol = new TableColumn<>("Opening");
        openningTotalCol.setCellValueFactory(new PropertyValueFactory<>(Batch_.openingTotal.getName()));
        openningTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        openningTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> closingTotalCol = new TableColumn<>("Closing");
        closingTotalCol.setCellValueFactory(new PropertyValueFactory<>(Batch_.closingTotal.getName()));
        closingTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        closingTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> tenderedCol = new TableColumn<>("Tendered");
        tenderedCol.setCellValueFactory(new PropertyValueFactory<>(Batch_.totalTendered.getName()));
        tenderedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        tenderedCol.setPrefWidth(85);

        TableColumn<Batch, String> changeTotalCol = new TableColumn<>("Change");
        changeTotalCol.setCellValueFactory(new PropertyValueFactory<>(Batch_.totalChange.getName()));
        changeTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        changeTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> checkTotalCol = new TableColumn<>("Checks");
        checkTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getPaymentBatch() != null && p.getValue().getPaymentBatch().getCheckTendered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPaymentBatch().getCheckTendered()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        checkTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        checkTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> chargeTotalCol = new TableColumn<>("Charges");
        chargeTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getPaymentBatch() != null && p.getValue().getPaymentBatch().getCreditCardTendered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPaymentBatch().getCreditCardTendered()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        chargeTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        chargeTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> onAccountTotalCol = new TableColumn<>("On Accoount");
        onAccountTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getPaymentBatch() != null && p.getValue().getPaymentBatch().getOnAccountTendered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPaymentBatch().getOnAccountTendered()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        onAccountTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        onAccountTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> couponTotalCol = new TableColumn<>("Coupons");
        couponTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getPaymentBatch() != null && p.getValue().getPaymentBatch().getCouponTendered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPaymentBatch().getCouponTendered()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        couponTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        couponTotalCol.setPrefWidth(85);

        TableColumn<Batch, String> giftTotalCol = new TableColumn<>("Gifts");
        giftTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<Batch, String> p) -> {
            if (p.getValue() != null && p.getValue().getPaymentBatch() != null && p.getValue().getPaymentBatch().getGiftCertificateTendered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPaymentBatch().getGiftCertificateTendered()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        giftTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        giftTotalCol.setPrefWidth(85);

        fTableView.getColumns().add(registerCol);
        fTableView.getColumns().add(dateCloseCol);
        fTableView.getColumns().add(openningTotalCol);
        fTableView.getColumns().add(closingTotalCol);
        fTableView.getColumns().add(tenderedCol);
        fTableView.getColumns().add(changeTotalCol);
        fTableView.getColumns().add(checkTotalCol);
        fTableView.getColumns().add(chargeTotalCol);
        fTableView.getColumns().add(onAccountTotalCol);
        fTableView.getColumns().add(couponTotalCol);
        fTableView.getColumns().add(giftTotalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setPrefWidth(995);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createDetailPane(), 0, 3);
        mainPane.add(createButtonPane(), 0, 4);
        return mainPane;
    }

    private Node createDetailPane() {

        VBox salesBox = new VBox();
        Label salesLabel = new Label("Sales");
        fSales.setEditable(false);
        fSales.setPrefSize(190, 170);
        salesBox.getChildren().addAll(salesLabel, fSales);

        VBox cashBox = new VBox();
        Label cashLabel = new Label("Cash");
        fCash.setEditable(false);
        fCash.setPrefSize(190, 170);
        cashBox.getChildren().addAll(cashLabel, fCash);

        VBox paymentBox = new VBox();
        Label paymentLabel = new Label("Payment");
        fPayment.setEditable(false);
        fPayment.setPrefSize(190, 170);
        paymentBox.getChildren().addAll(paymentLabel, fPayment);

        VBox otherBox = new VBox();
        Label otherLabel = new Label("Other");
        fOther.setEditable(false);
        fOther.setPrefSize(190, 170);
        otherBox.getChildren().addAll(otherLabel, fOther);

        VBox noteBox = new VBox();
        Label noteLabel = new Label("Note");
        fNote.setEditable(false);
        fNote.setPrefSize(190, 170);
        fNote.setWrapText(true);
        noteBox.getChildren().addAll(noteLabel, fNote);

        HBox result = new HBox();
        result.setSpacing(4);
        result.setPadding(new Insets(5, 0, 7, 0));
        result.getChildren().addAll(salesBox, cashBox, paymentBox, otherBox, noteBox);
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("border");

        return result;
    }

    protected HBox createButtonPane() {
        Button printBatchReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_BATCH_REPORT, "Print Report", fHandler);
        printBatchReportButton.setPrefWidth(120);
        Button printBatchDetailReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_BATCH_DETAIL_REPORT, "Print Detail Report", fHandler);
        printBatchDetailReportButton.setPrefWidth(150);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printBatchReportButton, printBatchDetailReportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private void updateInfoPane(Batch batch) {
        String payment = "";
        payment = payment + "Tendered Amount: " + getString(zeroIfNull(batch.getTotalTendered())) + "\n";
        payment = payment + "Change Amount: " + getString(zeroIfNull(batch.getTotalChange())) + "\n";
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCheckTendered() != null) {
            payment = payment + "Check Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getCheckTendered())) + "\n";
        } else {
            payment = payment + "Check Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCreditCardTendered() != null) {
            payment = payment + "Charge Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getCreditCardTendered())) + "\n";
        } else {
            payment = payment + "Charge Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getGiftCardTendered() != null) {
            payment = payment + "Gift Card Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getGiftCardTendered())) + "\n";
        } else {
            payment = payment + "Gift Card Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getGiftCertificateTendered() != null) {
            payment = payment + "Gift Certificate Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getGiftCertificateTendered())) + "\n";
        } else {
            payment = payment + "Gift Certificate Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCouponTendered() != null) {
            payment = payment + "Coupon Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getCouponTendered())) + "\n";
        } else {
            payment = payment + "Coupon Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getOnAccountTendered() != null) {
            payment = payment + "On Account Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getOnAccountTendered())) + "\n";
        } else {
            payment = payment + "On Account Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        fPayment.setText(payment.trim());

        String sales = "";
        sales = sales + "Sales: " + getString(zeroIfNull(batch.getSalesAmount().subtract(batch.getReturnAmount()))) + "\n";
        sales = sales + "Tax: " + getString(zeroIfNull(batch.getTax())) + "\n";
        sales = sales + "Shipping: " + getString(zeroIfNull(batch.getShipping())) + "\n";
        sales = sales + "Discount: " + getString(zeroIfNull(batch.getDiscountAmount())) + "\n";
        sales = sales + "Returns: " + getString(zeroIfNull(batch.getReturnAmount())) + "\n";
        sales = sales + "Cost of Goods: " + getString(zeroIfNull(batch.getCostOfGoods())) + "\n";
        sales = sales + "Commission: " + getString(zeroIfNull(batch.getCommissionTotal())) + "\n";
        sales = sales + "Customer Count: " + getString(zeroIfNull(batch.getCustomerCount()));
        fSales.setText(sales.trim());

        BigDecimal shortAmount = BigDecimal.ZERO;
        BigDecimal overAmount = BigDecimal.ZERO;
        BigDecimal cashCounted = BigDecimal.ZERO;
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCashCounted() != null) {
            cashCounted = batch.getPaymentBatch().getCashCounted();
        }
        BigDecimal diff = cashCounted.subtract(zeroIfNull(batch.getOpeningTotal()))
                .subtract(zeroIfNull(batch.getTotalTendered()).subtract(zeroIfNull(batch.getTotalChange())));
        if (diff.compareTo(BigDecimal.ZERO) > 0) {
            overAmount = diff;
        } else if (diff.compareTo(BigDecimal.ZERO) < 0) {
            shortAmount = diff.negate();
        }
        String count = "";
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCashCounted() != null) {
            count = count + "Counted Amount: " + getString(zeroIfNull(batch.getPaymentBatch().getCashCounted())) + "\n";
        } else {
            count = count + "Counted Amount: " + getString(BigDecimal.ZERO) + "\n";
        }
        count = count + "Openning Balance: " + getString(zeroIfNull(batch.getOpeningTotal())) + "\n";
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getCashCounted() != null) {
            count = count + "Cash Received: " + getString(batch.getPaymentBatch().getCashCounted().subtract(zeroIfNull(batch.getOpeningTotal()))) + "\n";
        } else {
            count = count + "Cash Received: " + getString((BigDecimal.ZERO).subtract(zeroIfNull(batch.getOpeningTotal()))) + "\n";
        }
        count = count + "Computer Reported: " + getString(zeroIfNull(batch.getTotalTendered()).subtract(zeroIfNull(batch.getTotalChange()))) + "\n";
        count = count + "Over: " + getString(overAmount) + "\n";
        count = count + "Short " + getString(shortAmount) + "\n";
        count = count + "Closing Balance: " + getString(zeroIfNull(batch.getClosingTotal()));
        fCash.setText(count.trim());

        String other = "";
        other = other + "Paid On Account: " + getString(zeroIfNull(batch.getPaidOnAccount())) + "\n";
        other = other + "Paid To Account: " + getString(zeroIfNull(batch.getPaidToAccount())) + "\n";
        other = other + "Deposit: " + getString(zeroIfNull(batch.getDepositMade())) + "\n";
        other = other + "Deposit Redeemed: " + getString(zeroIfNull(batch.getDepositRedeemed())) + "\n";
        other = other + "Credit: " + getString(zeroIfNull(batch.getCreditMade())) + "\n";
        other = other + "Credit Redeemed: " + getString(zeroIfNull(batch.getCreditRedeemed())) + "\n";
        other = other + "Register: " + getString(batch.getStation().getDescription()) + "\n";
        other = other + "Openning: " + getString(batch.getDateOpened()) + "\n";
        other = other + "Closing : " + getString(batch.getDateClosed());
        fOther.setText(other.trim());
        if (batch.getPaymentBatch() != null && batch.getPaymentBatch().getNote() != null && !batch.getPaymentBatch().getNote().isEmpty()) {
            fNote.setText(batch.getPaymentBatch().getNote());
        }
    }
}
