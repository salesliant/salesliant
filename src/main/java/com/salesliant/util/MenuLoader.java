package com.salesliant.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MenuLoader extends DefaultHandler {

    private static final Logger LOGGER = Logger.getLogger(MenuLoader.class.getName());
    private final List<Menu> fMenuList;
    private final HashMap<String, MenuBar> fMenuBarMap;
    private final HashMap<String, ToolBar> fToolBarMap;
    private MenuBar fMenuBar;
    private ToolBar fToolBar;
    private final EventHandler fListener;

    // private boolean isMenuBar;
    /**
     * Constructor, load a xml file. name:file name
     *
     * @param listener event listener
     */
    public MenuLoader(EventHandler listener) {
        fMenuList = new ArrayList<>();
        fMenuBarMap = new HashMap<>();
        fMenuBar = null;
        fToolBarMap = new HashMap<>();
        fToolBar = null;
        fListener = listener;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("META-INF/Menu.xml");
            saxParser.parse(is, this);
        } catch (SAXException ex) {
            LOGGER.log(Level.SEVERE, "Parse error: {0}", ex.getMessage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "MenuLoader, loading error: {0}{1}", new Object[]{ex.getClass().getName(), ex.getMessage()});
        } catch (ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    boolean tagMenuBar = false;

    /**
     * Handler the beginning of an element.
     *
     * @param name menu name
     * @param atts menu attribute
     */
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
        if (qName.equalsIgnoreCase("MenuBar")) {
            tagMenuBar = true;
            setMenuBar(atts);
        } else if (qName.equalsIgnoreCase("Menu")) {
            setMenu(atts);
        } else if (qName.equalsIgnoreCase("MenuItem")) {
            setMenuItem(atts);
        } else if (qName.equalsIgnoreCase("Separator")) {
            setSeparator();
        } else if (qName.equalsIgnoreCase("ToolBar")) {
            tagMenuBar = false;
            setToolBar(atts);
        } else if (qName.equalsIgnoreCase("Button")) {
            setButton(atts);
        }
    }

    /**
     * Handle the end of an element context. This varies by element type.
     *
     * @param name menu name
     */
    @Override
    public void endElement(String uri, String name, String qName) {
        if (qName.equalsIgnoreCase("Menu")) {
            popMenu();
        }
        if (qName.equalsIgnoreCase("MenuBar")) {
            fMenuBar = null;
        }
    }

    /**
     * Define a menu bar and register it in the hash Map
     *
     * @param atts attribute
     */
    protected void setMenuBar(Attributes atts) {
        String name = atts.getValue("NAME");
        fMenuBar = new MenuBar();
        // fMenuBar.getStyleClass().add("menuBar");
        fMenuBarMap.put(name, fMenuBar);
    }

    /**
     * Define a menu, add it the the current menu, and then make the new menu
     * the current menu.
     *
     * @param atts menu attribute
     */
    protected void setMenu(Attributes atts) {
        String name = atts.getValue("NAME");
        Menu aMenu = new Menu(name);
        // aMenu.getStyleClass().add("menu");
        add(aMenu);
        pushMenu(aMenu);
    }

    /**
     * Define a new MenuItem and add it to the current menu
     *
     * @param atts menu attribute
     */
    @SuppressWarnings("unchecked")
    protected void setMenuItem(Attributes atts) {
        String name = atts.getValue("NAME");
        String cmd = atts.getValue("COMMAND");
        MenuItem aMenuItem = new MenuItem(name);
        if (cmd != null) {
            aMenuItem.setId(cmd);
        }
        add(aMenuItem);
        aMenuItem.setOnAction(fListener);
    }

    /**
     * Add a new Separator to the current menu
     */
    protected void setSeparator() {
        if (tagMenuBar) {
            Menu aMenu = getCurrentMenu();
            if (aMenu != null) {
                aMenu.getItems().add(new SeparatorMenuItem());
            } else if (fMenuBar != null) {
                LOGGER.severe("Can't add bare separator to menu bars");
            }
        } else {
            fToolBar.getItems().add(new Separator());
        }
    }

    /**
     * Define a tool bar and register it in the hash Map
     *
     * @param atts menu attribute
     */
    protected void setToolBar(Attributes atts) {
        String name = atts.getValue("NAME");
        fToolBar = new ToolBar();
        // fToolBar.getStyleClass().add("toolBar");
        fToolBarMap.put(name, fToolBar);
    }

    /**
     * Define a new Button and add it to the ToolBar
     *
     * @param atts menu attribute
     */
    @SuppressWarnings("unchecked")
    protected void setButton(Attributes atts) {
        String name = atts.getValue("NAME");
        String label = atts.getValue("LABEL");
        String cmd = atts.getValue("COMMAND");
        String icon = atts.getValue("ICON");
        Button aButton = new Button();
        // aButton.getStyleClass().add("button");
        if (label != null) {
            aButton.setText(label);
        }
        if (name != null) {
            aButton.setTooltip(new Tooltip(name));
        }
        if (cmd != null && cmd.trim().length() > 0) {
            aButton.setId(cmd);
        }
        if (icon != null) {
            String iconPath = RES.ICON_RESOURCE_PATH_TOOLBAR + icon;
            try {
                Image aIcon = new Image(getClass().getResourceAsStream(iconPath));
                ImageView aImageView = new ImageView(aIcon);
                aImageView.setFitHeight(18);
                aImageView.setFitWidth(18);
                aButton.setGraphic(aImageView);
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Icon not loaded:{0}", iconPath);
            }
        }
        add(aButton);
        aButton.setOnAction(fListener);
    }

    /**
     * Add a menu to the current menu
     *
     * @param menu menu
     */
    protected void add(Menu menu) {
        Menu aMenu = getCurrentMenu();
        if (aMenu != null) {
            aMenu.getItems().add(menu);
        } else if (fMenuBar != null) {
            fMenuBar.getMenus().add(menu);
        }
    }

    /**
     * Add a MenuItem to the current Menu
     *
     * @param menuItem Menu Item
     */
    protected void add(MenuItem menuItem) {
        Menu aMenu = getCurrentMenu();
        if (aMenu != null) {
            aMenu.getItems().add(menuItem);
        } else if (fMenuBar != null) {
            LOGGER.log(Level.SEVERE, "{0}: Can''t add bare menu items to menu bars", menuItem.getText());
        }
    }

    /**
     * Add a button to the tool bar
     *
     * @param button menu button
     */
    protected void add(Button button) {
        fToolBar.getItems().add(button);
    }

    /**
     * Return MenuBar List or Module list.
     *
     * @return object
     */
    public List<String> getModules() {
        List<String> aList = new ArrayList<>();
        aList.addAll(fMenuBarMap.keySet());
        return aList;
    }

    public ChoiceBox<String> findChoiceBox() {
        ChoiceBox<String> aChoiceBox = new ChoiceBox<>();
        aChoiceBox.setItems(FXCollections.observableArrayList(getModules()));
        return aChoiceBox;
    }

    /**
     * Return MenuBar by name.
     *
     * @param name menu bar name
     * @return object
     */
    public MenuBar findMenuBar(String name) {
        MenuBar aMenuBar = fMenuBarMap.get(name);
        return aMenuBar;
    }

    /**
     * Return ToolBar by name.
     *
     * @param name tool bar name
     * @return object
     */
    public ToolBar findToolBar(String name) {
        ToolBar aToolBar = fToolBarMap.get(name);
        return aToolBar;
    }

    /**
     * Return reference to current menu
     *
     * @return object
     */
    protected Menu getCurrentMenu() {
        int iStacksize = fMenuList.size();
        if (iStacksize == 0) {
            return null;
        }
        Menu aMenu = (Menu) fMenuList.get(iStacksize - 1);
        return aMenu;
    }

    /**
     * Pop menu from current menu stack.
     *
     * @return object
     */
    protected Menu popMenu() {
        Menu aMenu = getCurrentMenu();
        if (aMenu != null) {
            fMenuList.remove(fMenuList.size() - 1);
        }
        return aMenu;
    }

    /**
     * Push menu onto menu stack
     *
     * @param menu menu
     */
    protected void pushMenu(Menu menu) {
        fMenuList.add(menu);
    }
}
