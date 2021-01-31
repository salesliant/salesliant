package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Customer_;
import com.salesliant.report.EmailListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CustomerSearchField;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class EmailListUI extends BaseListUI<Customer> {

    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private final CustomerSearchField searchField = new CustomerSearchField();
    private static final Logger LOGGER = Logger.getLogger(EmailListUI.class.getName());

    public EmailListUI() {
        mainView = createMainView();
        loadData();
        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Customer> list = daoCustomer.read(Customer_.store, Config.getStore()).stream()
                    .filter(e -> !e.getAccountNumber().equalsIgnoreCase("pos"))
                    .filter(e -> e.getAddToEmailListTag() != null && e.getAddToEmailListTag() && e.getEmailAddress() != null && !e.getEmailAddress().isEmpty())
                    .sorted((e1, e2) -> {
                        String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                                + (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                                + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                        String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                                + (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                                + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                        return name1.compareToIgnoreCase(name2);
                    })
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to remove this email from email list?", (ActionEvent e) -> {
                        fEntity.setAddToEmailListTag(Boolean.FALSE);
                        daoCustomer.update(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_PRINT:
                if (!fEntityList.isEmpty()) {
                    EmailListReportLayout layout = new EmailListReportLayout(fEntityList);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Customer, String> accountNumberCol = new TableColumn<>("Account");
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.accountNumber.getName()));
        accountNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountNumberCol.setPrefWidth(100);

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.firstName.getName()));
        firstNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        firstNameCol.setPrefWidth(80);

        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.lastName.getName()));
        lastNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lastNameCol.setPrefWidth(80);

        TableColumn<Customer, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((CellDataFeatures<Customer, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getCompany());
            } else {
                return null;
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(200);

        TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER));
        phoneNumberCol.setPrefWidth(75);

        TableColumn<Customer, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.city.getName()));
        cityCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cityCol.setPrefWidth(130);

        TableColumn<Customer, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.state.getName()));
        stateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        stateCol.setPrefWidth(80);

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.emailAddress.getName()));
        emailCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        emailCol.setPrefWidth(180);

        fTableView.getColumns().add(accountNumberCol);
        fTableView.getColumns().add(firstNameCol);
        fTableView.getColumns().add(lastNameCol);
        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(phoneNumberCol);
        fTableView.getColumns().add(cityCol);
        fTableView.getColumns().add(stateCol);
        fTableView.getColumns().add(emailCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(createPrintCloseButtonPane(), 1, 3);
        return mainPane;
    }

    private HBox createPrintCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Export List", fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, "Remove From List", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(deleteButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}
