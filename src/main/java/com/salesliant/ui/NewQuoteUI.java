/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.util.DBConstants;

/**
 *
 * @author Lewis
 */
public final class NewQuoteUI extends SalesOrderUI {

    public NewQuoteUI() {
        fOrderType = DBConstants.TYPE_SALESORDER_QUOTE;
        if (Config.checkTransactionRequireLogin()) {
            LoginUI.login();
        }
        handleAction(SELECT_CUSTOMER);
    }
}
