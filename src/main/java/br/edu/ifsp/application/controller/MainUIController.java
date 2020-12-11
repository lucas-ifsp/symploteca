package br.edu.ifsp.application.controller;

import br.edu.ifsp.application.view.WindowLoader;
import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.entities.transaction.Transaction;
import br.edu.ifsp.domain.entities.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.*;

public class MainUIController {

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> cName;
    @FXML
    private TableColumn<Book, String>  cAuthors;
    @FXML
    private TableColumn<Book, String> cPublisher;
    @FXML
    private TableColumn<Book, String> cGenre;
    @FXML
    private TableColumn<Book, String> cStatus;
    @FXML
    private TextField txtId;
    @FXML
    private Label lbName;
    @FXML
    private Button btnBorrowOrReturn;

    private ObservableList<Book> tableData;
    private User user;
    private Book selectedBook;

    private final String BORROW_BUTTON_TEXT = "Realizar Empréstimo";
    private final String RETURN_BUTTON_TEXT = "Realizar Devolução";

    @FXML
    private void initialize(){
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindColumnsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        cPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        cGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void loadDataAndShow() {
        List<Book> books = findBookUseCase.findAll();
        tableData.clear();
        tableData.addAll(books);
    }


    public void manageBooks(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("BookManagementUI");
    }

    public void manageUsers(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("UserManagementUI");
    }

    public void findUser(ActionEvent actionEvent) {
        Optional<User> result = findUserUseCase.findOne(txtId.getText());
        if(result.isPresent()){
            user = result.get();
            lbName.setText(user.getName() + " [" + user.getUserType() + "]");
        }else{
            user = null;
            lbName.setText("");
            showAlert("Erro!", "Usuário não encontrado", Alert.AlertType.ERROR);
        }
    }

    public void borrowOrReturn(ActionEvent actionEvent) {
        if(selectedBook != null){
            if(selectedBook.getStatus() == BookStatus.AVAILABLE) {
                if (user == null)
                    showAlert("Erro!", "O leitor deve ser informado para a realizacão do empréstimo", Alert.AlertType.ERROR);
                else
                    borrowBook();
            }else{
                returnBook();
            }
            loadDataAndShow();
        }
    }

    private void borrowBook() {
        try {
            Transaction transaction = checkoutTransactionUseCase.checkoutABook(user.getInstitutionalId(), selectedBook.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = formatter.format(transaction.getDueDate());
            String title = "Livro emprestado com sucesso.";
            String message = "[ID " + transaction.getId() + "] A devolucao deve ser realizada até: " + formattedString + ".";
            txtId.setText("");
            showAlert(title, message, Alert.AlertType.INFORMATION);

        }catch (Exception e){
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void returnBook() {
        try{
            returnTransactionUseCase.returnBook(selectedBook.getId());
            String title = "Livro devolvido com sucesso.";
            String message = "O livro  \"" + selectedBook.getName() + "\" foi devolvido.";
            showAlert(title, message, Alert.AlertType.INFORMATION);
        }catch (Exception e){
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void getSelectedAndSetButton(MouseEvent mouseEvent) {
        selectedBook  = tableView.getSelectionModel().getSelectedItem();
        if(selectedBook != null && selectedBook.getStatus() == BookStatus.CHECKED_OUT)
            btnBorrowOrReturn.setText(RETURN_BUTTON_TEXT);
        else{
            btnBorrowOrReturn.setText(BORROW_BUTTON_TEXT);
        }
    }

}
