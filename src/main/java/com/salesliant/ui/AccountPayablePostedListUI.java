package com.salesliant.ui;

import com.salesliant.entity.AccountPayable;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;

public class AccountPayablePostedListUI extends AccountPayablePostedBaseListUI {

    public AccountPayablePostedListUI() {
        mainView = createMainView();
        loadData();
        selectedItems.addListener((SetChangeListener<AccountPayable>) change -> {
            if (change.wasAdded()) {
                AccountPayable ap = change.getElementAdded();
                if (ap.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV) && ap.getPaidAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    ap.setPaidAmount(ap.getTotalAmount().subtract(ap.getDiscountAmount()));
                }
                if (ap.getAccountPayableType().equals(DBConstants.TYPE_APAR_CRE)) {
                    ap.setPaidAmount(ap.getTotalAmount().negate());
                }
            }
            if (change.wasRemoved()) {
                AccountPayable ap = change.getElementRemoved();
                ap.setPaidAmount(BigDecimal.ZERO);
            }
            totalProperty.set(selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            fTableView.refresh();
            fSummaryTable.refresh();
        });
        fSummaryTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends List<AccountPayable>> observable, List<AccountPayable> newValue, List<AccountPayable> oldValue) -> {
            accounPayableList = new ArrayList<>();
            if (observable != null && observable.getValue() != null) {
                accounPayableList = observable.getValue().stream().collect(Collectors.toList());
            }
            fTableView.setItems(FXCollections.observableList(accounPayableList));
        });
        fDiscountPane = createDiscountPane();
        fToPayPane = createToPayPane();
    }
}
