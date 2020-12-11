package br.edu.ifsp.application.controller;

import br.edu.ifsp.application.view.WindowLoader;
import br.edu.ifsp.domain.entities.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import static br.edu.ifsp.application.main.Main.findUserUseCase;
import static br.edu.ifsp.application.main.Main.removeUserUseCase;

public class UserManagementUIController {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> cId;
    @FXML
    private TableColumn<User, String> cName;
    @FXML
    private TableColumn<User, String> cEmail;
    @FXML
    private TableColumn<User, String> cPhone;
    @FXML
    private TableColumn<User, String> cType;

    private ObservableList<User> tableData;

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cId.setCellValueFactory(new PropertyValueFactory<>("institutionalId"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cType.setCellValueFactory(new PropertyValueFactory<>("userType"));
    }

    private void loadDataAndShow() {
        List<User> User = findUserUseCase.findAll();
        tableData.clear();
        tableData.addAll(User);
    }

    public void deleteUser(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            removeUserUseCase.remove(selectedUser);
            loadDataAndShow();
        }
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        showUserInMode(UIMode.UPDATE);
    }

    public void detailUser(ActionEvent actionEvent) throws IOException {
        showUserInMode(UIMode.VIEW);
    }

    private void showUserInMode(UIMode mode) throws IOException {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            WindowLoader.setRoot("UserUI");
            UserUIController controller = (UserUIController) WindowLoader.getController();
            controller.setUser(selectedUser, mode);
        }
    }

    public void createUser(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("UserUI");
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
