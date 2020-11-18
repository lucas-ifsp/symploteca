package br.edu.ifsp.domain.usecases.book;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface BookDAO extends DAO<Book, Integer> {
    Optional<Book> findByIsnb(String isbn);
}
