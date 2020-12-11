package br.edu.ifsp.domain.usecases.book;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateBookUseCase {
    private BookDAO bookDAO;

    public UpdateBookUseCase(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public boolean update(Book book){
        Validator<Book> validator = new BookInputRequestValidator();
        Notification notification = validator.validate(book);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = book.getId();
        if(bookDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Book not found.");

        return bookDAO.update(book);
    }
}
