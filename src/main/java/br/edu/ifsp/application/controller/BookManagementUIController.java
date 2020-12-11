package br.edu.ifsp.application.controller;

import br.edu.ifsp.application.view.WindowLoader;
import br.edu.ifsp.domain.entities.book.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class BookManagementUIController {

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> cName;
    @FXML
    private TableColumn<Book, String> cAuthors;
    @FXML
    private TableColumn<Book, String> cPublisher;
    @FXML
    private TableColumn<Book, String> cGenre;
    @FXML
    private TableColumn<Book, String> cStatus;

    private ObservableList<Book> tableData;

    @FXML
    private void initialize(){
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        cPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        cGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Book> books = findBookUseCase.findAll();
        tableData.clear();
        tableData.addAll(books);
    }

    public void deleteBook(ActionEvent actionEvent) {
        Book selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            removeBookUseCase.remove(selectedItem);
            loadDataAndShow();
        }
    }

    public void editBook(ActionEvent actionEvent) throws IOException {
        showBookInMode(UIMode.UPDATE);
    }

    public void detailBook(ActionEvent actionEvent) throws IOException {
        showBookInMode(UIMode.VIEW);
    }

    private void showBookInMode(UIMode mode) throws IOException {
        Book selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            WindowLoader.setRoot("BookUI");
            BookUIController controller = (BookUIController) WindowLoader.getController();
            controller.setBook(selectedItem, mode);
        }
    }

    public void createBook(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("BookUI");
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
