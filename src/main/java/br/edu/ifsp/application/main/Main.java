package br.edu.ifsp.application.main;

import br.edu.ifsp.application.repository.InMemoryBookDAO;
import br.edu.ifsp.application.repository.InMemoryTransactionDAO;
import br.edu.ifsp.application.repository.InMemoryUserDAO;
import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGender;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.entities.user.Faculty;
import br.edu.ifsp.domain.entities.user.Student;
import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.book.*;
import br.edu.ifsp.domain.usecases.transaction.CheckoutTransactionUseCase;
import br.edu.ifsp.domain.usecases.transaction.FindTransactionUseCase;
import br.edu.ifsp.domain.usecases.transaction.ReturnTransactionUseCase;
import br.edu.ifsp.domain.usecases.transaction.TransactionDAO;
import br.edu.ifsp.domain.usecases.user.*;

public class Main {

    private static CreateBookUseCase createBookUseCase;
    private static FindBookUseCase findBookUseCase;
    private static UpdateBookUseCase updateBookUseCase;
    private static RemoveBookUseCase removeBookUseCase;

    private static CreateUserUseCase createUserUseCase;
    private static FindUserUseCase findUserUseCase;
    private static UpdateUserUseCase updateUserUseCase;
    private static RemoveUserUseCase removeUserUseCase;

    private static CheckoutTransactionUseCase checkoutTransactionUseCase;
    private static ReturnTransactionUseCase returnTransactionUseCase;
    private static FindTransactionUseCase findTransactionUseCase;


    public static void main(String[] args) {
        configureInjection();

        User user1 = new Student("SC0001", "João da Silva", "joao@email.com", "16 99999 8888", "ADS" );
        User user2 = new Faculty("SC0002", "Maria da Silva", "maria@email.com", "16 98888 8888", "Computer Systems" );
        createUserUseCase.insert(user1);
        createUserUseCase.insert(user2);

        Book book1 = new Book(1, 300, "Java In Action", "Paulo & Paula", "Livro São Carlos", "11111", BookGender.TECHNICAL, BookStatus.AVAILABLE);
        Book book2 = new Book(4, 203, "A história da POO", "Carlos C.", "Baixa Books", "22222", BookGender.HISTORY, BookStatus.AVAILABLE);
        Book book3 = new Book(2, 132, "Invertendo as Dependências", "Interface S.", "Solid Books", "33333", BookGender.ACTION, BookStatus.AVAILABLE);
        Book book4 = new Book(3, 342, "O Código Mal Modularizado", "Student et al.", "DP inc.", "44444", BookGender.HORROR, BookStatus.AVAILABLE);
        Book book5 = new Book(4, 233, "Entendendo as Gambiarras", "Sérgio C.", "G. Reporter", "55555", BookGender.SCIENCE, BookStatus.AVAILABLE);
        Book book6 = new Book(5, 99, "O Aluno Mudo", "Lucas R.", "ADS EADs", "66666", BookGender.DRAMA, BookStatus.AVAILABLE);

        Integer b1 = createBookUseCase.insert(book1);
        Integer b2 = createBookUseCase.insert(book2);
        Integer b3 = createBookUseCase.insert(book3);
        Integer b4 = createBookUseCase.insert(book4);
        Integer b5 = createBookUseCase.insert(book5);
        Integer b6 = createBookUseCase.insert(book6);

        checkoutBook(user2.getInstitutionalId(), b1);
        checkoutBook(user2.getInstitutionalId(), b2);
        checkoutBook(user2.getInstitutionalId(), b3);
        checkoutBook(user2.getInstitutionalId(), b4);
        checkoutBook(user2.getInstitutionalId(), b5);
        checkoutBook(user2.getInstitutionalId(), b6);

        returnBook(b1);
        returnBook(b2);
        returnBook(b3);
        returnBook(b4);
        returnBook(b5);
        returnBook(b6);

        findTransactionUseCase.findAll().stream().forEach(transaction -> System.out.println(transaction));
    }

    public static void checkoutBook(String userId, Integer bookId){
        try{
            checkoutTransactionUseCase.checkoutABook(userId, bookId);
            System.out.println("Book has been checked out");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void returnBook(Integer bookId){
        try{
            returnTransactionUseCase.returnBook(bookId);
            System.out.println("Book has been returned");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void configureInjection() {
        BookDAO bookDAO = new InMemoryBookDAO();
        createBookUseCase = new CreateBookUseCase(bookDAO);
        updateBookUseCase = new UpdateBookUseCase(bookDAO);
        findBookUseCase = new FindBookUseCase(bookDAO);
        removeBookUseCase = new RemoveBookUseCase(bookDAO);

        UserDAO userDAO = new InMemoryUserDAO();
        createUserUseCase = new CreateUserUseCase(userDAO);
        updateUserUseCase = new UpdateUserUseCase(userDAO);
        findUserUseCase = new FindUserUseCase(userDAO);
        removeUserUseCase = new RemoveUserUseCase(userDAO);

        TransactionDAO transactionDAO = new InMemoryTransactionDAO();
        checkoutTransactionUseCase = new CheckoutTransactionUseCase(
                transactionDAO, findUserUseCase, findBookUseCase, updateUserUseCase, updateBookUseCase);
        returnTransactionUseCase = new ReturnTransactionUseCase(
                transactionDAO, findUserUseCase, findBookUseCase, updateUserUseCase, updateBookUseCase);
        findTransactionUseCase = new FindTransactionUseCase(transactionDAO);
    }
}
