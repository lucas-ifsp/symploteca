package br.edu.ifsp.domain.usecases.transaction;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.entities.transaction.Transaction;
import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.book.FindBookUseCase;
import br.edu.ifsp.domain.usecases.book.UpdateBookUseCase;
import br.edu.ifsp.domain.usecases.user.FindUserUseCase;
import br.edu.ifsp.domain.usecases.user.UpdateUserUseCase;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.time.LocalDate;

public class ReturnTransactionUseCase {


    private TransactionDAO transactionDAO;
    private FindUserUseCase findUserUseCase;
    private FindBookUseCase findBookUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private UpdateBookUseCase updateBookUseCase;

    public ReturnTransactionUseCase(
            TransactionDAO transactionDAO,
            FindUserUseCase findUserUseCase,
            FindBookUseCase findBookUseCase,
            UpdateUserUseCase updateUserUseCase,
            UpdateBookUseCase updateBookUseCase) {

        this.transactionDAO = transactionDAO;
        this.findUserUseCase = findUserUseCase;
        this.findBookUseCase = findBookUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.updateBookUseCase = updateBookUseCase;
    }

    public void returnBook(Integer bookId){
        if (bookId == null)
            throw new IllegalArgumentException("Book ID is null");

        Transaction transaction = transactionDAO.findTransactionByBookId(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an open transaction for book ID " + bookId));

        transaction.setReturnDate(LocalDate.now());
        transactionDAO.update(transaction);

        Book book = findBookUseCase.findOne(bookId).get();
        book.setStatus(BookStatus.AVAILABLE);
        updateBookUseCase.update(book);

        String userId = transaction.getUser().getInstitutionalId();
        User user = findUserUseCase.findOne(userId).get();
        user.decreaseNumberOfBooksCheckedOut();
        updateUserUseCase.update(user);
    }
}
