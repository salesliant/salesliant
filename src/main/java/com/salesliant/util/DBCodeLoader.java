package com.salesliant.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>
 * Title: DBCodeLoader
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Salesliant, LLC
 * </p>
 *
 * @author Lewis Liu
 * @version 1.0
 */
public class DBCodeLoader extends DefaultHandler {

    private static final Logger LOGGER = Logger.getLogger(DBCodeLoader.class.getName());
    private List<String> fTypeList;
    private HashMap<String, List<String>> fTypeTable;
    private static DBCodeLoader fInstance;

    private DBCodeLoader() {
        fTypeList = new ArrayList<>();
        fTypeTable = new HashMap<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("META-INF/DBCode.xml");
            saxParser.parse(is, this);
        } catch (SAXException ex) {
            LOGGER.log(Level.SEVERE, "Parse error: ", ex.getMessage());
        } catch (ParserConfigurationException | IOException ex) {
            LOGGER.log(Level.SEVERE, "TypeLoader, loading error: ", ex.getMessage());
        }
    }

    /**
     * Handler the beginning of an element.
     *
     * @param name type name
     * @param atts type attribute
     */
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
        if (qName.equalsIgnoreCase("Type")) {
            setType(atts);
        } else if (qName.equalsIgnoreCase("TypeCode")) {
            setTypeCode(atts);
        }
    }

    /**
     * Handle the end of an element context. This varies by element type.
     *
     * @param name type name
     */
    @Override
    public void endElement(String uri, String name, String qName) {
        if (qName.equalsIgnoreCase("Type")) {
            fTypeList = null;
        }
    }

    /**
     * Define a menu bar and register it in the hash table
     *
     * @param atts type attribute
     */
    protected void setType(Attributes atts) {
        String name = atts.getValue("NAME");
        fTypeList = new ArrayList<>();
        fTypeTable.put(name, fTypeList);
    }

    /**
     * Define a menu, add it the the current menu, and then make the new menu
     * the current menu.
     *
     * @param atts type attribute
     */
    protected void setTypeCode(Attributes atts) {
        String name = atts.getValue("DESCRIPTION");
        fTypeList.add(name);
    }

    /**
     * Return reference to current menu
     *
     * @return object
     */
    protected String getCurrentType() {
        int iStacksize = fTypeList.size();
        if (iStacksize == 0) {
            return null;
        }
        String aType = (String) fTypeList.get(iStacksize - 1);
        return aType;
    }

    /**
     * Return int by name.
     *
     * @param name type name
     * @return type object
     */
    public int getType(String[] name) {
        List<String> aType = fTypeTable.get(name[0]);
        return aType.indexOf(name[1]);
    }

    /**
     * Return Type List by name.
     *
     * @param name type name
     * @return object
     */
    public List<String> getTypes(String name) {
        List<String> aType = fTypeTable.get(name);
        if (aType != null) {
            return aType;
        }
        return null;
    }

    /**
     * Return ChoiceBox by name.
     *
     * @param name choice box name
     * @return object
     */
    public ChoiceBox<String> getChoiceBoxTypes(String name) {
        List<String> aType = fTypeTable.get(name);
        if (aType != null) {
            ChoiceBox<String> aChoiceBox = new ChoiceBox<>();
            aChoiceBox.setItems(FXCollections.observableArrayList(aType));
            return aChoiceBox;
        }
        return null;
    }

    /**
     * Return ComboBox by name.
     *
     * @param name combo box name
     * @return object
     */
    public ComboBox<String> getComboBoxTypes(String name) {
        List<String> aType = fTypeTable.get(name);
        if (aType != null) {
            ComboBox<String> aComboBox = new ComboBox<>();
            // aComboBox.getStyleClass().add("comboBox");
            aComboBox.setItems(FXCollections.observableArrayList(aType));
            return aComboBox;
        }
        return null;
    }

    public static DBCodeLoader getInstance() {
        if (fInstance == null) {
            fInstance = new DBCodeLoader();
        }
        return fInstance;
    }
}
