package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGenre;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.book.BookDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteBookDAO implements BookDAO {

    @Override
    public Integer create(Book book) {
        String sql = "INSERT INTO Book(name, authors, publisher, isbn, edition, " +
                "num_of_pages, genre, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthors());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getEdition());
            stmt.setInt(6, book.getNumberOfPages());
            stmt.setString(7, book.getGenre().toString());
            stmt.setString(8, book.getStatus().toString());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();
            int generatedKey = resultSet.getInt(1);
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Book> findOne(Integer key) {
        String sql = "SELECT * FROM Book WHERE id = ?";
        Book book = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                book = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    private Book resultSetToEntity(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getInt("edition"),
                rs.getInt("num_of_pages"),
                rs.getString("name"),
                rs.getString("authors"),
                rs.getString("publisher"),
                rs.getString("isbn"),
                BookGenre.toEnum(rs.getString("genre")),
                BookStatus.toEnum(rs.getString("status"))
        );
    }

    @Override
    public Optional<Book> findByIsnb(String isbn) {
        String sql = "SELECT * FROM Book WHERE isbn = ?";
        Book book = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                book = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM Book";
        List<Book> books = new ArrayList<>();

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = resultSetToEntity(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE Book SET name = ?, authors = ?, publisher = ?, isbn = ?, edition = ?, " +
                "num_of_pages = ?, genre = ?, status = ? WHERE id = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthors());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getEdition());
            stmt.setInt(6, book.getNumberOfPages());
            stmt.setString(7, book.getGenre().toString());
            stmt.setString(8, book.getStatus().toString());
            stmt.setInt(9, book.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Book WHERE id = ?";
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Book book) {
        if (book == null || book.getId() == null)
            throw new IllegalArgumentException("Book and book ID must not be null.");
        return deleteByKey(book.getId());
    }
}
