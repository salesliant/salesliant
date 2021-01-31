package com.salesliant.client;

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Batch_;
import com.salesliant.entity.Country;
import com.salesliant.entity.Currency;
import com.salesliant.entity.CustomerTerm;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Payment;
import com.salesliant.entity.Seq;
import com.salesliant.entity.Seq_;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.entity.TaxClass;
import com.salesliant.entity.TaxZone;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.DBConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {

    private static final Config INSTANCE = new Config();
    private static final BaseDao<Seq> DAO_SEQ = new BaseDao<>(Seq.class);
    private static final BaseDao<Batch> DAO_BATCH = new BaseDao<>(Batch.class);
    private static final BaseDao<Store> DAO_STORE = new BaseDao<>(Store.class);
    private static final BaseDao<Deposit> DAO_DEPOSIT = new BaseDao<>(Deposit.class);
    private static final BaseDao<Invoice> DAO_INVOICE = new BaseDao<>(Invoice.class);
    private static final BaseDao<ItemPriceLevel> DAO_PRICE_LEVEL = new BaseDao<>(ItemPriceLevel.class);
    private static final BaseDao<DropPayout> DAO_DROP_PAYOUT = new BaseDao<>(DropPayout.class);
    private static final BaseDao<Payment> DAO_PAYMENT = new BaseDao<>(Payment.class);
    private static final BaseDao<AccountReceivable> DAO_ACCOUNT_RECEIVABLE = new BaseDao<>(AccountReceivable.class);
    private static EntityManagerFactory emf;
    private static final String DB_PU = "salesliant";
    private static final boolean DEBUG = false;
    private static Store fStore;
    private static Employee fEmployee;
    private static Station fStation;
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    private Config() {
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        if (emf == null) {
            String databaseConfig = "./database.properties";
            File databaseFile = new File(databaseConfig);
            Map<String, String> persistenceMap = new HashMap<>();
            if (databaseFile.exists()) {
                try {
                    PropertiesConfiguration config = new PropertiesConfiguration(databaseConfig);
                    String url = config.getString("javax.persistence.jdbc.url");
                    String driver = "org.mariadb.jdbc.Driver";
                    String storecode = config.getString("store.code");
                    persistenceMap.put("javax.persistence.jdbc.url", url);
                    persistenceMap.put("javax.persistence.jdbc.driver", driver);
                    emf = Persistence.createEntityManagerFactory(DB_PU, persistenceMap);
                    EntityManager em = emf.createEntityManager();
                    CriteriaBuilder builder = em.getCriteriaBuilder();
                    CriteriaQuery<Store> cq = builder.createQuery(Store.class);
                    Root<Store> root = cq.from(Store.class);
                    cq.select(root);
                    cq.where(root.get(Store_.storeCode).in(storecode));
                    TypedQuery<Store> q = em.createQuery(cq);
                    List<Store> list = q.getResultList();
                    if (list != null && !list.isEmpty() && list.get(0) != null) {
                        Config.setStore(list.get(0));
                    }
                } catch (ConfigurationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
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
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public static Store getStore() {
        return fStore;
    }

    public static void setStore(Store store) {
        fStore = store;
    }

    public static List<Store> getStoreList() {
        List<Store> list = DAO_STORE.read();
        return list;
    }

    public static void loadStation() {
        String path = System.getenv("APPDATA") + File.separator + "Salesliant" + File.separator + Config.getStore().getStoreCode();
        String file = path + File.separator + "user.properties";
        File directory = new File(path);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                System.out.printf("Unable to create the folder %s, check your privileges.", path);
            }
        }
        File userData = new File(file);
        if (userData.exists()) {
            try (InputStream input = new FileInputStream(file)) {
                Properties prop = new Properties();
                prop.load(input);
                input.close();
                String stationString = prop.getProperty("station.number");
                if (stationString != null && !stationString.isEmpty()) {
                    Station station = null;
                    List<Station> stationList = Config.getStore().getStations()
                            .stream()
                            .sorted((e1, e2) -> e1.getNumber().compareTo(e2.getNumber()))
                            .collect(Collectors.toList());
                    for (int i = 0; i < stationList.size(); i++) {
                        if (stationList.get(i).getNumber() != null && (stationList.get(i).getNumber().toString()).equalsIgnoreCase(stationString)) {
                            station = stationList.get(i);
                            break;
                        }
                    }
                    if (station == null) {
                        Station firstStation = stationList.get(0);
                        Config.setStation(firstStation);
                    } else {
                        Config.setStation(station);
                    }
                }
            } catch (IOException e) {
                System.out.printf("Error while loading the settings: %s", e.getMessage());
            }
        } else {
            try (OutputStream output = new FileOutputStream(file)) {
                List<Station> list = Config.getStore().getStations();
                Station firstStation = list.get(0);
                String stationString = firstStation.getNumber().toString();
                Properties prop = new Properties();
                prop.setProperty("station.number", stationString);
                prop.store(output, null);
                output.close();
                Config.setStation(firstStation);
            } catch (IOException e) {
                System.out.printf("Error while creating the settings: %s", e.getMessage());
            }
        }
    }

    public static Station getStation() {
        return fStation;
    }

    public static void setStation(Station station) {
        fStation = station;
        String title = "";
        if (Config.getStore() != null) {
            title = title + getString(Config.getStore().getStoreName()) + "     ";
        }
        if (Config.getStation() != null) {
            title = title + "Stataion:" + getString(Config.getStation().getNumber()) + "    ";
        }
        if (Config.getEmployee() != null) {
            title = title + "  User:" + getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName());
        }
        ClientApp.primaryStage.setTitle(title);
    }

    public static Employee getEmployee() {
        return fEmployee;
    }

    public static void setEmployee(Employee employee) {
        fEmployee = employee;
    }

    public static String getDefaultCountryName() {
        Locale locale = Locale.getDefault();
        String name = locale.getDisplayCountry();
        return name;
    }

    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        return locale;
    }

    public static Country getDefaultCountry() {
        return fStore.getCountry();
    }

    public static TaxZone getTaxZone() {
        return fStore.getDefaultTaxZone();
    }

    public static Batch getBatch() {
        Station tenderStation = Config.getStation().getTenderedStation();
        Comparator<Batch> comparator = Comparator.comparing(Batch::getBatchNumber);
        List<Batch> openBatchList = DAO_BATCH.read(Batch_.station, tenderStation.getId(), Batch_.status, DBConstants.STATUS_OPEN, Batch_.store, Config.getStore());
        if (openBatchList != null && !openBatchList.isEmpty()) {
            Optional<Batch> findLastOpenBatch = openBatchList.stream().max(comparator);
            if (findLastOpenBatch.isPresent()) {
                Batch lastOpenBatch = findLastOpenBatch.get();
                return lastOpenBatch;
            }
        }
        Batch batch = new Batch();
        batch.setStore(Config.getStore());
        batch.setStation(tenderStation);
        batch.setEmployee(Config.getEmployee());
        batch.setBatchNumber(Config.getNumber(DBConstants.SEQ_BATCH_NUMBER));
        batch.setStatus(DBConstants.STATUS_OPEN);
        batch.setDateOpened(new Timestamp(new Date().getTime()));
        List<Batch> batchList = DAO_BATCH.read(Batch_.store, fStore, Batch_.status, DBConstants.STATUS_CLOSE, Batch_.station, tenderStation);
        if (batchList != null && !batchList.isEmpty()) {
            Optional<Batch> findLastBatch = batchList.stream().max(comparator);
            if (findLastBatch.isPresent()) {
                Batch lastBatch = findLastBatch.get();
                if (lastBatch.getClosingTotal() != null) {
                    batch.setOpeningTotal(lastBatch.getClosingTotal());
                } else {
                    batch.setOpeningTotal(BigDecimal.ZERO);
                }
            }
        }
        batch = DAO_BATCH.insert(batch);
        return batch;
    }

    public static void closeBatch(Batch batch) {
        batch.getInvoices().forEach(e -> {
            e.setBatch(batch);
            DAO_INVOICE.update(e);
        });
        batch.getDepositOpenBatchs().forEach(e -> {
            e.setOpenBatch(batch);
            DAO_DEPOSIT.update(e);
        });
        batch.getDepositCloseBatchs().forEach(e -> {
            e.setCloseBatch(batch);
            DAO_DEPOSIT.update(e);
        });
        batch.getDropPayouts().forEach(e -> {
            e.setBatch(batch);
            DAO_DROP_PAYOUT.update(e);
        });
        batch.getPayments().forEach(e -> {
            e.setBatch(batch);
            DAO_PAYMENT.update(e);
        });
        batch.getAccountReceivables().forEach(e -> {
            e.setBatch(batch);
            e.setPostedTag(Boolean.TRUE);
            DAO_ACCOUNT_RECEIVABLE.update(e);
        });
        DAO_BATCH.update(batch);
    }

    public static Integer getNumber(String seq_name) {
        int number;
        Seq seq = DAO_SEQ.find(Seq_.seqName, seq_name, Seq_.store, fStore);
        number = seq.getSeqValue() + 1;
        seq.setSeqValue(number);
        DAO_SEQ.update(seq);
        return number;
    }

    public static List<ItemPriceLevel> getItemPriceLevel() {
        List<ItemPriceLevel> list = DAO_PRICE_LEVEL.read().stream()
                .sorted((e1, e2) -> Integer.compare(e1.getId(), e2.getId()))
                .filter(e -> !getString(e.getDescription()).trim().isEmpty())
                .collect(Collectors.toList());
        return list;
    }

    public TaxClass getDefaultTaxClass() {
        return getStore().getDefaultTaxClass();
    }

    public static CustomerTerm getDefaultCustomerTerm() {
        return getStore().getDefaultCustomerTerm();
    }

    public static Currency getDefaultCurrency() {
        return getStore().getDefaultCurrency();
    }

    public static int getDefaultDefaultItemCostType() {
        return getStore().getDefaultItemCostMethod();
    }

    public static int getDefaultOrderDueDays() {
        return getStore().getOrderDueDays();
    }

    public static int getDefaultServiceOrderDueDays() {
        return getStore().getServiceOrderDueDays();
    }

    public static int getDefaultInternetOrderDueDays() {
        return getStore().getInternetOrderDueDays();
    }

    public static int getDefaultQuoteExpirationDays() {
        return getStore().getQuoteExpirationDays();
    }

    public static int getDefaultLayawayExpirationDays() {
        return getStore().getLayawayExpirationDays();
    }

    public static boolean getDefaultEdcTimeOut() {
        return getStore().getEdcTimeOut();
    }

    public static boolean getDefaultGlobalCustomer() {
        return getStore().getDefaultGlobalCustomer();
    }

    public static BigDecimal getDefaultLayawayDeposit() {
        return getStore().getLayawayDeposit();
    }

    public static BigDecimal getDefaultLayawayFee() {
        return getStore().getLayawayFee();
    }

    public static BigDecimal getDefaultOrderDeposit() {
        return getStore().getOrderDeposit();
    }

    public static int getDefaultInvoiceCount() {
        return getStore().getInvoiceCount();
    }

    public static boolean isAutoCustomerNumberGeneration() {
        return getStore().getAutoCustomerNumberGeneration();
    }

    public static boolean isAutoSkuGeneration() {
        return getStore().getAutoSkuGeneration();
    }

    public static boolean isDefaultGlobalCustomer() {
        return getStore().getDefaultGlobalCustomer();
    }

    public static boolean isDisplayOutOfStock() {
        return getStore().getDisplayOutOfStock();
    }

    public static boolean isEdctimeOut() {
        return getStore().getEdcTimeOut();
    }

    public static boolean isEnableBackOrders() {
        return getStore().getEnableBackOrders();
    }

    public static boolean isEnforceOpenCloseAmount() {
        return getStore().getEnforceOpenCloseAmount();
    }

    public static boolean getAllowZeroQtySale() {
        return getStore().getAllowZeroQtySale();
    }

    public static boolean getRequireSerialNumber() {
        return getStore().getRequireSerialNumber();
    }

    public static boolean getShowAddressAtPos() {
        return getStore().getShowAddressAtPos();
    }

    public static boolean getShowFunctionKeysAtPos() {
        return getStore().getShowFunctionKeysAtPos();
    }

    public static boolean checkTransactionRequireLogin() {
        if (Config.getStation() == null) {
            return false;
        } else {
            return Config.getStation().getTransactionRequireLogin() != null && Config.getStation().getTransactionRequireLogin();
        }
    }

    public static Boolean checkLogin(String username, String password) {
        BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
        List<Employee> employeeList = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, true);
        if (employeeList != null && !employeeList.isEmpty() && employeeList.get(0) != null) {
            for (Employee e : employeeList) {
                if (e.getLogin().equalsIgnoreCase(username) && e.getPassword().equalsIgnoreCase(password) && e.getStore().getId().equals(Config.getStore().getId()) && e.getActiveTag()) {
                    Config.setEmployee(e);
                    String title = "";
                    if (Config.getStore() != null) {
                        title = title + getString(Config.getStore().getStoreName()) + "     ";
                    }
                    if (Config.getStation() != null) {
                        title = title + "Stataion:" + getString(Config.getStation().getNumber()) + "    ";
                    }
                    if (Config.getEmployee() != null) {
                        title = title + "  User:" + getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName());
                    }
                    ClientApp.primaryStage.setTitle(title);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}
