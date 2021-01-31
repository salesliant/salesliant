/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Customer_;
import com.salesliant.entity.SalesOrder;
import static com.salesliant.ui.SalesOrderUI.CUSTOMER_SELECTED;
import com.salesliant.util.BaseDao;
import com.salesliant.util.DBConstants;
import java.util.logging.Logger;

/**
 *
 * @author Lewis
 */
public final class QuickInvoiceUI extends SalesOrderUI {

    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);

    public QuickInvoiceUI() {
        fOrderType = DBConstants.TYPE_SALESORDER_INVOICE;
        fCustomer = daoCustomer.find(Customer_.accountNumber, "pos", Customer_.store, Config.getStore());
        fSalesOrder = new SalesOrder();
        fSalesOrder.setCustomer(fCustomer);
        if (fCustomer.getTaxZone() != null) {
            fSalesOrder.setTaxZone(fCustomer.getTaxZone());
        } else {
            fSalesOrder.setTaxZone(Config.getTaxZone());
        }
        if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
            fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
        } else {
            fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
        }
        fSalesOrder.setStation(Config.getStation());
        fSalesOrder.setStore(Config.getStore());
        fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
        fSalesOrder.setType(fOrderType);
        if (Config.checkTransactionRequireLogin()) {
            LoginUI.login();
        }
        fSalesOrder.setSales(Config.getEmployee());
        handleAction(CUSTOMER_SELECTED);
    }
}
