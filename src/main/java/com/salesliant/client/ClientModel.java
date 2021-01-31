/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.client;

import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.mvc.AbstractModel;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.toList;
import java.util.List;

/**
 *
 * @author Lewis
 */
public class ClientModel extends AbstractModel {

    @Override
    public void updateModel(String propertyName, Object oldValue) {
        if (propertyName.equals(ClientController.USERNAME_PASSWORD) && oldValue instanceof List) {
            List<String> pair = toList(oldValue);
            String username = pair.get(0);
            String password = pair.get(1);
            if (checkLogin(username, password)) {
                firePropertyChange(ClientController.USERNAME_PASSWORD, oldValue, true);
            } else {
                firePropertyChange(ClientController.USERNAME_PASSWORD, oldValue, false);
            }
        }
        if (propertyName.equals(ClientController.SET_UP)) {
            firePropertyChange(ClientController.SET_UP, null, true);
        }
    }

    private Boolean checkLogin(String username, String password) {
        BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
        List<Employee> employeeList = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, true);
        if (employeeList != null && !employeeList.isEmpty() && employeeList.get(0) != null) {
            for (Employee e : employeeList) {
                if (e.getLogin().equalsIgnoreCase(username) && e.getPassword().equalsIgnoreCase(password) && e.getStore().getId().equals(Config.getStore().getId()) && e.getActiveTag()) {
                    Config.setEmployee(e);
                    Config.loadStation();
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
