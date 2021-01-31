package com.salesliant.validator;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.util.BaseDao;
import java.util.List;

public class EmployeeValidator {

    private final BaseDao<Employee> daoEmployee = new BaseDao(Employee.class);
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 25;

    public void validate(Employee employee) throws InvalidException {
        List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore());
        String loginID = employee.getLogin();
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String password = employee.getPassword();
        if (loginID != null && !loginID.isEmpty()) {
            if ((loginID.length() < MIN_LENGTH) || (loginID.length() > MAX_LENGTH)) {
                throw new InvalidException("Login ID must be 3 to 25 characters long!");
            }
        } else {
            throw new InvalidException("Login ID cann't be empty!");
        }
        if (password != null && !password.isEmpty()) {
            if ((password.length() < MIN_LENGTH) || (password.length() > MAX_LENGTH)) {
                throw new InvalidException("Login ID must be 3 to 25 characters long!");
            }
            if ((password.length() < MIN_LENGTH) || (password.length() > MAX_LENGTH)) {
                throw new InvalidException("Password must be 3 to 25 characters long!");
            }
        } else {
            throw new InvalidException("Password cann't be empty!");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new InvalidException("First name cann't be empty!");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new InvalidException("Last name cann't be empty!");
        }
        if (employee.getNameOnSalesOrder() == null || employee.getNameOnSalesOrder().isEmpty()) {
            throw new InvalidException("Name on Sales Order cann't be empty!");
        }
        if (!nameHasValidCharacters(loginID)) {
            throw new InvalidException("Login ID can only have (a-z A-Z 0-9 space and _) characters!");
        }
        for (Employee e : list) {
            if (employee.getId() == null) {
                if (e.getLogin().equalsIgnoreCase(employee.getLogin())) {
                    throw new InvalidException("Login ID alread exists in the database!");
                }
                if (e.getNameOnSalesOrder().equalsIgnoreCase(employee.getNameOnSalesOrder())) {
                    throw new InvalidException("Name on Sales Order alread exists in the database!");
                }
            } else {
                if (!employee.getId().equals(e.getId())) {
                    if (e.getLogin().equalsIgnoreCase(employee.getLogin())) {
                        throw new InvalidException("Login ID alread exists in the database!");
                    }
                    if (e.getNameOnSalesOrder().equalsIgnoreCase(employee.getNameOnSalesOrder())) {
                        throw new InvalidException("Name on Sales Order alread exists in the database!");
                    }
                }
            }
        }
    }

    private boolean nameHasValidCharacters(String input) {
        char[] cc = input.toCharArray();
        for (char c : cc) {
            if ((Character.isLetterOrDigit(c)) || (Character.isSpaceChar(c)) || (c == '_')) {
            } else {
                return false;
            }
        }
        return true;
    }
}
