package com.salesliant.util;

import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Country;
import com.salesliant.entity.Customer;
import com.salesliant.entity.VendorShipTo;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Vendor;
import com.salesliant.client.Config;

public class AddressFactory {

    private java.lang.String fFirstName;
    private java.lang.String fLastName;
    private java.lang.String fCompany;
    private java.lang.String fDepartment;
    private java.lang.String fAddress1;
    private java.lang.String fAddress2;
    private java.lang.String fCity;
    private java.lang.String fState;
    private java.lang.String fZip;
    private Country fCountry;
    private java.lang.String fPhoneNumber;
    private java.lang.String fFaxNumber;
    private java.lang.String fCellNumber;
    private java.lang.String fWebAddress;
    private java.lang.String fEmailAddress;
    private int fStyle;
    public static final int STYLE_SHORT = 0;
    public static final int STYLE_LONG = 1;
    private static final AddressFactory INSTANCE = new AddressFactory();

    private AddressFactory() {
    }

    public static AddressFactory getInstance() {
        return INSTANCE;
    }

    public static String getCompanyAddress() {
        return AddressFactory.INSTANCE.setCompanyAddress();
    }

    private String setCompanyAddress() {
        fStyle = STYLE_SHORT;
        setFirstName(null);
        setLastName(null);
        setCompany(Config.getStore().getStoreName());
        setDepartment(null);
        setAddress1(Config.getStore().getAddress1());
        setAddress2(Config.getStore().getAddress2());
        setCity(Config.getStore().getCity());
        setState(Config.getStore().getState());
        setZip(Config.getStore().getPostCode());
        setCountry(Config.getStore().getCountry());
        setPhoneNumber(Config.getStore().getPhoneNumber());
        setFaxNumber(Config.getStore().getFaxNumber());
        setCellNumber(null);
        setEmailAddress(null);
        setWebAddress(Config.getStore().getWebAddress());
        return toString();
    }

    public static String getVendorAddress(Vendor vendor, int style) {
        return AddressFactory.INSTANCE.setVendorAddress(vendor, style);
    }

    private String setVendorAddress(Vendor vendor, int style) {
        fStyle = style;
        setFirstName(null);
        setLastName(null);
        setCompany(vendor.getCompany());
        setAddress1(vendor.getAddress1());
        setAddress2(vendor.getAddress2());
        setCity(vendor.getCity());
        setState(vendor.getState());
        setZip(vendor.getPostCode());
        setCountry(vendor.getCountry());
        setPhoneNumber(vendor.getPhoneNumber());
        setFaxNumber(vendor.getFaxNumber());
        setCellNumber(null);
        setEmailAddress(null);
        setWebAddress(vendor.getWebAddress());
        return toString();
    }

    public static String getEmployeeAddress(Employee employee, int style) {
        return AddressFactory.INSTANCE.setEmployeeAddress(employee, style);
    }

    private String setEmployeeAddress(Employee employee, int style) {
        fStyle = style;
        setFirstName(employee.getFirstName());
        setLastName(employee.getLastName());
        setCompany(null);
        setDepartment(null);
        setAddress1(employee.getAddress1());
        setAddress2(employee.getAddress2());
        setCity(employee.getCity());
        setState(employee.getState());
        setZip(employee.getPostCode());
        setCountry(employee.getCountry());
        setPhoneNumber(employee.getPhoneNumber());
        setFaxNumber(null);
        setCellNumber(employee.getCellPhoneNumber());
        setEmailAddress(employee.getEmailAddress());
        setWebAddress(null);
        return toString();
    }

    public static String getVendorShipToAddress(VendorShipTo vendorShipTo, int style) {
        return AddressFactory.INSTANCE.setVendorShipToAddress(vendorShipTo, style);
    }

    private String setVendorShipToAddress(VendorShipTo vendorShipTo, int style) {
        fStyle = style;
        setFirstName(null);
        setLastName(null);
        setCompany(vendorShipTo.getCompany());
        setDepartment(null);
        setAddress1(vendorShipTo.getAddress1());
        setAddress2(vendorShipTo.getAddress2());
        setCity(vendorShipTo.getCity());
        setState(vendorShipTo.getState());
        setZip(vendorShipTo.getPostCode());
        setCountry(vendorShipTo.getCountry());
        setPhoneNumber(vendorShipTo.getPhoneNumber());
        setFaxNumber(vendorShipTo.getFaxNumber());
        setCellNumber(null);
        setEmailAddress(null);
        setWebAddress(null);
        return toString();
    }

    public static String getVendorBillToAddress(Vendor vendor, int style) {
        return AddressFactory.INSTANCE.setVendorBillToAddress(vendor, style);
    }

    private String setVendorBillToAddress(Vendor vendor, int style) {
        fStyle = style;
        setFirstName(null);
        setLastName(null);
        setCompany(vendor.getCompany());
        setAddress1(vendor.getAddress1());
        setAddress2(vendor.getAddress2());
        setCity(vendor.getCity());
        setState(vendor.getState());
        setZip(vendor.getPostCode());
        setCountry(vendor.getCountry());
        setPhoneNumber(vendor.getPhoneNumber());
        setFaxNumber(vendor.getFaxNumber());
        setCellNumber(null);
        setEmailAddress(null);
        setWebAddress(null);
        return toString();
    }

    public static String getCustomerAddress(Customer customer, int style) {
        return AddressFactory.INSTANCE.setCustomerAddress(customer, style);
    }

    private String setCustomerAddress(Customer customer, int style) {
        if (customer == null) {
            return null;
        } else {
            fStyle = style;

            setFirstName(customer.getFirstName());
            setLastName(customer.getLastName());
            setPhoneNumber(customer.getPhoneNumber());
            setFaxNumber(customer.getFaxNumber());
            setCellNumber(null);
            setEmailAddress(customer.getEmailAddress());
            setWebAddress(null);
            setAddress1(customer.getAddress1());
            setAddress2(customer.getAddress2());
            setCity(customer.getCity());
            setState(customer.getState());
            setCountry(customer.getCountry());
            return toString();
        }
    }

    public static String getCustomerBillToAddress(Customer customer, int style) {
        return AddressFactory.INSTANCE.setCustomerBillToAddress(customer, style);
    }

    private String setCustomerBillToAddress(Customer customer, int style) {
        fStyle = style;
        setFirstName(customer.getFirstName());
        setLastName(customer.getLastName());
        setCompany(customer.getCompany());
        setPhoneNumber(customer.getPhoneNumber());
        setFaxNumber(customer.getFaxNumber());
        setCellNumber(customer.getCellPhoneNumber());
        setEmailAddress(customer.getEmailAddress());
        setWebAddress(null);
        return toString();
    }

    public static String getCustomerShipToAddress(CustomerShipTo customerShipTo, int style) {
        return AddressFactory.INSTANCE.setCustomerShipToAddress(customerShipTo, style);
    }

    private String setCustomerShipToAddress(CustomerShipTo customerShipTo, int style) {
        fStyle = style;
        setContactName(customerShipTo.getContactName());
        setCompany(customerShipTo.getCompany());
        setDepartment(customerShipTo.getDepartment());
        setAddress1(customerShipTo.getAddress1());
        setAddress2(customerShipTo.getAddress2());
        setCity(customerShipTo.getCity());
        setState(customerShipTo.getState());
        setZip(customerShipTo.getPostCode());
        setCountry(customerShipTo.getCountry());
        setPhoneNumber(customerShipTo.getPhoneNumber());
        setEmailAddress(customerShipTo.getEmailAddress());
        setWebAddress(null);
        return toString();
    }

    /**
     * Checks if a string is empty Note: ( "" == "" ) is false, must use
     * equals()
     */
    private static boolean isEmpty(String string) {
        return (string == null || "".equals(string.trim()));
    }

    @Override
    public String toString() {
        String aText = "";
        if (!isEmpty(fFirstName)) {
            if (!isEmpty(fLastName)) {
                aText += fFirstName + " " + fLastName + "\n";
            } else {
                aText += fFirstName + "\n";
            }
        } else {
            if (!isEmpty(fLastName)) {
                aText += fLastName + "\n";
            }
        }
        if (!isEmpty(fCompany)) {
            aText += fCompany + "\n";
        }
        if (!isEmpty(fDepartment)) {
            aText += fDepartment + "\n";
        }
        if (!isEmpty(fAddress1)) {
            aText += fAddress1 + "\n";
        }
        if (!isEmpty(fAddress2)) { // != null && fAddress2.trim() != "") {
            aText += fAddress2 + "\n";
        }
        if (!isEmpty(fCity)) {
            if (!isEmpty(fState)) {
                if (!isEmpty(fZip)) {
                    switch (fStyle) {
                        case STYLE_LONG:
                            aText += fCity + ", " + fState + " " + fZip + "\n";
                            break;
                        case STYLE_SHORT:
                            aText += fCity + ", " + fState + " " + fZip + " ";
                            break;
                        default:
                            aText += fCity + ", " + fState + " " + fZip + "\n";
                            break;
                    }
                } else {
                    switch (fStyle) {
                        case STYLE_LONG:
                            aText += fCity + ", " + fState + "\n";
                            break;
                        case STYLE_SHORT:
                            aText += fCity + ", " + fState + " ";
                            break;
                        default:
                            aText += fCity + ", " + fState + "\n";
                            break;
                    }
                }
            } else {
                switch (fStyle) {
                    case STYLE_LONG:
                        aText += fCity + "\n";
                        break;
                    case STYLE_SHORT:
                        aText += fCity + " ";
                        break;
                    default:
                        aText += fCity + "\n";
                        break;
                }
            }
        } else {
            if (!isEmpty(fState)) {
                if (!isEmpty(fZip)) {
                    switch (fStyle) {
                        case STYLE_LONG:
                            aText += fState + " " + fZip + "\n";
                            break;
                        case STYLE_SHORT:
                            aText += fState + " " + fZip + " ";
                            break;
                        default:
                            aText += fState + " " + fZip + "\n";
                            break;
                    }
                } else {
                    switch (fStyle) {
                        case STYLE_LONG:
                            aText += fState + "\n";
                            break;
                        case STYLE_SHORT:
                            aText += fState + " ";
                            break;
                        default:
                            aText += fState + "\n";
                            break;
                    }
                }
            }
        }
        if (fCountry != null) {
            aText += fCountry.getIsoCode3() + "\n";
        }
        if (!aText.endsWith("\n")) {
            aText += "\n";
        }
        if (!isEmpty(getPhoneFaxCellString().trim())) {
            aText += getPhoneFaxCellString() + "\n";
        }
        if (fStyle == STYLE_LONG && !isEmpty(getWebEmailString().trim())) {
            aText += getWebEmailString();
        }
        return aText.trim();
    }

    private String getPhoneFaxCellString() {
        String aText = "";
        if (!isEmpty(fPhoneNumber)) {
            aText += "Phone: " + fPhoneNumber + "  ";
        }
        if (!isEmpty(fFaxNumber)) {
            aText += "Fax: " + fFaxNumber;
        }
        if (!isEmpty(fCellNumber)) {
            aText += "  Cell: " + fCellNumber;
        }
        return aText.trim();
    }

    private String getWebEmailString() {
        String aText = "";
        if (!isEmpty(fWebAddress)) {
            aText += fWebAddress + "  ";
        }
        if (!isEmpty(fEmailAddress)) {
            aText += fEmailAddress;
        }
        return aText.trim();
    }

    public void setContactName(java.lang.String contactName) {
    }

    public void setFirstName(java.lang.String firstName) {
        this.fFirstName = firstName;
    }

    public java.lang.String getFirstName() {
        return fFirstName;
    }

    public void setLastName(java.lang.String lastName) {
        this.fLastName = lastName;
    }

    public java.lang.String getLastName() {
        return fLastName;
    }

    public void setCompany(java.lang.String company) {
        this.fCompany = company;
    }

    public java.lang.String getCompany() {
        return fCompany;
    }

    public void setDepartment(java.lang.String department) {
        this.fDepartment = department;
    }

    public java.lang.String getDepartment() {
        return fDepartment;
    }

    public void setAddress1(java.lang.String address1) {
        this.fAddress1 = address1;
    }

    public java.lang.String getAddress1() {
        return fAddress1;
    }

    public void setAddress2(java.lang.String address2) {
        this.fAddress2 = address2;
    }

    public java.lang.String getAddress2() {
        return fAddress2;
    }

    public void setCity(java.lang.String city) {
        this.fCity = city;
    }

    public java.lang.String getCity() {
        return fCity;
    }

    public void setState(java.lang.String state) {
        this.fState = state;
    }

    public java.lang.String getState() {
        return fState;
    }

    public void setZip(java.lang.String zip) {
        this.fZip = zip;
    }

    public java.lang.String getZip() {
        return fZip;
    }

    public void setCountry(Country country) {
        this.fCountry = country;
    }

    public Country getCountry() {
        return fCountry;
    }

    public void setPhoneNumber(java.lang.String phoneNumber) {
        this.fPhoneNumber = phoneNumber;
    }

    public java.lang.String getPhoneNumber() {
        return fPhoneNumber;
    }

    public void setFaxNumber(java.lang.String faxNumber) {
        this.fFaxNumber = faxNumber;
    }

    public java.lang.String getFaxNumber() {
        return fFaxNumber;
    }

    public void setCellNumber(java.lang.String cellNumber) {
        this.fCellNumber = cellNumber;
    }

    public java.lang.String getCellNumber() {
        return fCellNumber;
    }

    public void setEmailAddress(java.lang.String emailAddress) {
        this.fEmailAddress = emailAddress;
    }

    public java.lang.String getEmailAddress() {
        return fEmailAddress;
    }

    public void setWebAddress(java.lang.String webAddress) {
        this.fWebAddress = webAddress;
    }

    public java.lang.String getWebAddress() {
        return fWebAddress;
    }
}
