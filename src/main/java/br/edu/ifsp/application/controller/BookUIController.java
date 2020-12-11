package br.edu.ifsp.application.controller;

import br.edu.ifsp.application.view.WindowLoader;
import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGenre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.edu.ifsp.application.main.Main.createBookUseCase;
import static br.edu.ifsp.application.main.Main.updateBookUseCase;

public class BookUIController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAuthors;
    @FXML
    private TextField txtPublisher;
    @FXML
    private TextField txtEdition;
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtPages;
    @FXML
    private ComboBox<BookGenre> cbGenres;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Book book;

    @FXML
    private void initialize(){
        cbGenres.getItems().setAll(BookGenre.values());
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        if(book.getId() == null)
            createBookUseCase.insert(book);
        else
            updateBookUseCase.update(book);
        WindowLoader.setRoot("BookManagementUI");
    }

    private void getEntityToView(){
        if (book == null) {
            book = new Book();
        }
        book.setName(txtName.getText());
        book.setAuthors(txtAuthors.getText());
        book.setPublisher(txtPublisher.getText());
        book.setEdition(Integer.valueOf(txtEdition.getText()));
        book.setIsbn(txtIsbn.getText());
        book.setNumberOfPages(Integer.valueOf(txtPages.getText()));
        book.setName(txtName.getText());
        book.setGenre(cbGenres.getValue());
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("BookManagementUI");
    }

    public void setBook(Book book, UIMode mode) {
        if(book == null)
            throw new IllegalArgumentException("Book can not be null.");

        this.book = book;
        setEntityIntoView();

        if(mode == UIMode.VIEW)
            configureViewMode();
    }

    private void setEntityIntoView(){
        txtName.setText(book.getName());
        txtIsbn.setText(book.getIsbn());
        txtPages.setText(String.valueOf(book.getNumberOfPages()));
        txtEdition.setText(String.valueOf(book.getEdition()));
        txtPublisher.setText(book.getPublisher());
        txtAuthors.setText(book.getAuthors());
        cbGenres.setValue(book.getGenre());
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtName.setDisable(true);
        txtIsbn.setDisable(true);
        txtPages.setDisable(true);
        txtEdition.setDisable(true);
        txtPublisher.setDisable(true);
        txtAuthors.setDisable(true);
        cbGenres.setDisable(true);    }
}
