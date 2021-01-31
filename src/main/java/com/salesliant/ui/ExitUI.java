package com.salesliant.ui;

import com.salesliant.client.ClientApp;
import com.salesliant.entity.Employee;
import com.salesliant.util.BaseListUI;

/**
 *
 * @author Lewis
 */
public class ExitUI extends BaseListUI<Employee> {

    public ExitUI() {
        ClientApp.primaryStage.close();
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
