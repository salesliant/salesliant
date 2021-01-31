package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.TimeCard_;
import com.salesliant.report.TimeCardReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class TimeCardReportUI extends BaseListUI<TimeCard> {

    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
    private final TableView<Employee> fEmployeeTable = new TableView<>();
    private ObservableList<Employee> fEmployeeList;
    private final ObservableSet<Employee> selectedItems = FXCollections.observableSet();
    private final Label rangeLabel = new Label();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(TimeCardReportUI.class.getName());

    public TimeCardReportUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
        fEmployeeTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Employee> observable, Employee newValue, Employee oldValue) -> {
            if (observable != null && observable.getValue() != null && observable.getValue().getTimeCards() != null && !observable.getValue().getTimeCards().isEmpty()) {
                List<TimeCard> tcList = observable.getValue().getTimeCards()
                        .stream()
                        .filter(e -> e.getTimeIn() != null)
                        .filter(e -> e.getTimeOut() != null)
                        .filter(e -> e.getTimeIn().after(Timestamp.valueOf(fFrom)))
                        .filter(e -> e.getTimeIn().before(Timestamp.valueOf(fTo)))
                        .collect(Collectors.toList());
                fEntityList = FXCollections.observableList(tcList);
            } else {
                fEntityList = FXCollections.observableArrayList();
            }
            fTableView.setItems(fEntityList);
            fTableView.refresh();
        });
    }

    private void loadData() {
        List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, true)
                .stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "")
                            + (!isEmpty(e1.getLastName()) ? " " : "");
                    String name2 = (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "")
                            + (!isEmpty(e2.getLastName()) ? " " : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
        fEmployeeList = FXCollections.observableList(list);
        fEmployeeTable.setItems(fEmployeeList);
        updateRange();
    }

    private void setDefaultDate() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DAY_OF_WEEK, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();

        LocalDate firstLocalDateOfPreviousMonth = firstDateOfPreviousMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastLocalDateOfPreviousMonth = lastDateOfPreviousMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fFrom = firstLocalDateOfPreviousMonth.atTime(0, 0, 0, 0);
        fTo = lastLocalDateOfPreviousMonth.atTime(LocalTime.MAX);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!selectedItems.isEmpty()) {
                    TimeCardReportLayout layout = new TimeCardReportLayout(selectedItems, fFrom, fTo);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                break;
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.refresh();
                    fEmployeeTable.refresh();
                    updateRange();
                });
                fInputDialog.showDialog();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(10));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(createEmployeeTable(), 0, 2);
        mainPane.add(createTimeCardTable(), 0, 3);
        mainPane.add(createSelectPrintCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private Node createEmployeeTable() {
        TableColumn<Employee, Employee> selectedCol = new TableColumn<>("");
        selectedCol.setCellValueFactory((CellDataFeatures<Employee, Employee> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Employee, Employee> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.firstName.getName()));
        firstNameCol.setCellFactory(stringCell(Pos.CENTER));
        firstNameCol.setPrefWidth(150);

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.lastName.getName()));
        lastNameCol.setCellFactory(stringCell(Pos.CENTER));
        lastNameCol.setPrefWidth(150);

        TableColumn<Employee, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER));
        phoneNumberCol.setPrefWidth(150);

        TableColumn<Employee, String> hoursCol = new TableColumn<>("Hours");
        hoursCol.setCellValueFactory((CellDataFeatures<Employee, String> p) -> {
            if (p.getValue() != null && p.getValue().getTimeCards() != null && !p.getValue().getTimeCards().isEmpty()) {
                BigDecimal hours = p.getValue().getTimeCards()
                        .stream()
                        .filter(e -> e.getTimeIn() != null)
                        .filter(e -> e.getTimeOut() != null)
                        .filter(e -> e.getTimeIn().after(Timestamp.valueOf(fFrom)))
                        .filter(e -> e.getTimeIn().before(Timestamp.valueOf(fTo)))
                        .map(e -> (e.getHours()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(hours));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        hoursCol.setCellFactory(stringCell(Pos.CENTER));
        hoursCol.setPrefWidth(150);

        fEmployeeTable.getColumns().add(selectedCol);
        fEmployeeTable.getColumns().add(firstNameCol);
        fEmployeeTable.getColumns().add(lastNameCol);
        fEmployeeTable.getColumns().add(phoneNumberCol);
        fEmployeeTable.getColumns().add(hoursCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefWidth(630);
        fTableView.setPrefHeight(250);

        return fEmployeeTable;
    }

    private Node createTimeCardTable() {
        TableColumn<TimeCard, String> clockInTimeCol = new TableColumn<>("Clock In Time");
        clockInTimeCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.timeIn.getName()));
        clockInTimeCol.setCellFactory(stringCell(Pos.CENTER));
        clockInTimeCol.setPrefWidth(200);

        TableColumn<TimeCard, String> clockOutTimeCol = new TableColumn<>("Clock Out Time");
        clockOutTimeCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.timeOut.getName()));
        clockOutTimeCol.setCellFactory(stringCell(Pos.CENTER));
        clockOutTimeCol.setPrefWidth(200);

        TableColumn<TimeCard, String> hoursCol = new TableColumn<>("Hours");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.hours.getName()));
        hoursCol.setCellFactory(stringCell(Pos.CENTER));
        hoursCol.setPrefWidth(200);

        fTableView.getColumns().add(clockInTimeCol);
        fTableView.getColumns().add(clockOutTimeCol);
        fTableView.getColumns().add(hoursCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setEditable(false);

        return fTableView;
    }

    private HBox createSelectPrintCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Selected", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}
