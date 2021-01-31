package com.salesliant.client;

import com.salesliant.mvc.Controller;
import java.util.List;

/**
 *
 * @author Lewis
 */
public class ClientController extends Controller {

    public static final String USERNAME_PASSWORD = "LoginPair";
    public static final String SET_UP = "SetUp";
    public static final String CURRENCY_SYMBOL_CREATE = "CurrencySymbolCreate";
    public static final String CURRENCY_SYMBOL_READ = "CurrencySymbolRead";
    public static final String CURRENCY_SYMBOL_UPDATE = "CurrencySymbolUpadte";
    public static final String CURRENCY_SYMBOL_DELETE = "CurrencySymbolDelete";
    public static final String COUNTRY_READ = "CountryRead";
    private final ClientView view;
    private final ClientModel model;
    private static final ClientController CLIENT_CONTROLLER = new ClientController();

    public static ClientController getInstance() {
        return CLIENT_CONTROLLER;
    }

    public ClientController() {
        view = new ClientView(this);
        model = new ClientModel();
        init();
    }

    private void init() {
        addView(view);
        addModel(model);
    }

    @Override
    public ClientView getView() {
        return view;
    }

    public void sendLoginInfo(List loginPair) {
        setModelProperty(USERNAME_PASSWORD, loginPair);
    }

    public void sendCheckSetUp() {
        setModelProperty(SET_UP, null);
    }

    public void readCurrencySymbol() {
        setModelProperty(CURRENCY_SYMBOL_READ, null);
    }

    public void readCountry() {
        setModelProperty(COUNTRY_READ, null);
    }
}
