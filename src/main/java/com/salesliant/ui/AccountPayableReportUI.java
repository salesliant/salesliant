package com.salesliant.ui;

public class AccountPayableReportUI extends AccountPayablePostedBaseListUI {

    public AccountPayableReportUI() {
        loadData();
        mainView = createReportView();
        fSummaryTable.setPrefHeight(500);
        paymentTotalCol.setVisible(false);
        companyCol.setPrefWidth(350);
    }
}
