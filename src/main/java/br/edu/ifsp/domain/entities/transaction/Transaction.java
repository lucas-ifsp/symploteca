package br.edu.ifsp.domain.entities.transaction;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.user.User;

import java.time.LocalDate;

public class Transaction {
    private Integer id;
    private LocalDate issuedDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Book book;
    private User user;


    public Transaction(Book book, User user, int numberOfCheckoutDays) {
        this.book = book;
        this.user = user;
        this.issuedDate = LocalDate.now();
        this.dueDate = issuedDate.plusDays(numberOfCheckoutDays);
    }

    public Transaction(Integer id, LocalDate issuedDate, LocalDate dueDate, LocalDate returnDate, Book book, User user) {
        this.id = id;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", issueDate=" + issuedDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
