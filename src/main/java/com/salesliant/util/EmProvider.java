/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lewis
 */
public class EmProvider {

    private static final String DB_PU = "salesliant";
    public static final boolean DEBUG = false;
    private static final EmProvider SINGLETON = new EmProvider();
    private EntityManagerFactory emf;

    private static final Logger LOGGER = Logger.getLogger(EmProvider.class.getName());

    private EmProvider() {
    }

    public static EmProvider getInstance() {
        return SINGLETON;
    }

    private EntityManagerFactory getFactory() {
        if (emf == null) {
            String databaseConfig = "./database.properties";
            File databaseFile = new File(databaseConfig);
            Map<String, String> persistenceMap = new HashMap<>();
            if (databaseFile.exists()) {
                try (InputStream in = new FileInputStream(databaseConfig)) {
                    Properties prop = new Properties();
                    prop.load(in);
                    String url = prop.getProperty("javax.persistence.jdbc.url");
//                    String user = prop.getProperty("javax.persistence.jdbc.user");
//                    String password = prop.getProperty("javax.persistence.jdbc.password");
                    String driver = prop.getProperty("javax.persistence.jdbc.driver");
                    String storecode = prop.getProperty("store.code");

                    persistenceMap.put("javax.persistence.jdbc.url", url);
//                    persistenceMap.put("javax.persistence.jdbc.user", user);
//                    persistenceMap.put("javax.persistence.jdbc.password", password);
                    persistenceMap.put("javax.persistence.jdbc.driver", driver);
//                    Config.setStoreCode(storecode);

                    emf = Persistence.createEntityManagerFactory(DB_PU, persistenceMap);
//                    System.out.println("####Properties.getProperty usage####");
//                    System.out.println(url);
//                    System.out.println();
//                    System.out.println("####Properties.stringPropertyNames usage####");
//                    for (String property : prop.stringPropertyNames()) {
//                        String value = prop.getProperty(property);
//                        System.out.println(property + "=" + value);
//                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, null, e);
                }
            } else {
                persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mariadb://localhost:3306/salesliantdb");
                persistenceMap.put("javax.persistence.jdbc.driver", "org.mariadb.jdbc.Driver");
                emf = Persistence.createEntityManagerFactory(DB_PU, persistenceMap);
            }
        }
        if (DEBUG) {
            System.out.println("factory created on: " + new Date());
        }
        return emf;
    }

    public static EntityManager createEntityManager() {
        return getInstance().getFactory().createEntityManager();
    }

    public void closeEmf() {
        if (emf.isOpen() || emf != null) {
            emf.close();
        }
        emf = null;
        if (DEBUG) {
            System.out.println("EMF closed at: " + new Date());
        }
    }

}
