package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.transaction.Transaction;
import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.transaction.TransactionDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findBookUseCase;
import static br.edu.ifsp.application.main.Main.findUserUseCase;


public class SqliteTransactionDAO implements TransactionDAO {

    @Override
    public Integer create(Transaction transaction) {
        String sql = "INSERT INTO BookTransaction(user, book, issue_date, due_date) VALUES(?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, transaction.getUser().getInstitutionalId());
            stmt.setInt(2, transaction.getBook().getId());
            stmt.setString(3, transaction.getIssuedDate().toString());
            stmt.setString(4, transaction.getDueDate().toString());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();
            int generatedId = resultSet.getInt(1);

            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Transaction> findOne(Integer key) {
        String sql = "SELECT * FROM BookTransaction WHERE id = ?";
        Transaction transaction = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, key);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                transaction = resultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(transaction);
    }

    private Transaction resultSetToEntity(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("book");
        Book book = findBookUseCase.findOne(bookId).get();

        String userId = resultSet.getString("user");
        User user = findUserUseCase.findOne(userId).get();


        String returnDate = resultSet.getString("return_date");
        LocalDate convertedReturnDate = null;

        if(returnDate != null)
            convertedReturnDate = LocalDate.parse(returnDate);

        return new Transaction(
                resultSet.getInt("id"),
                LocalDate.parse(resultSet.getString("issue_date")),
                LocalDate.parse(resultSet.getString("due_date")),
                convertedReturnDate,
                book,
                user
        );

    }


    @Override
    public Optional<Transaction> findTransactionByBookId(Integer id) {
        String sql = "SELECT * FROM BookTransaction WHERE book = ?";
        Transaction transaction = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                transaction = resultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM BookTransaction";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = resultSetToEntity(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public boolean update(Transaction transaction) {
        String sql = "UPDATE BookTransaction SET user = ?, book = ?, issue_date = ?, due_date = ?, return_date = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, transaction.getUser().getInstitutionalId());
            stmt.setInt(2, transaction.getBook().getId());
            stmt.setString(3, transaction.getIssuedDate().toString());
            stmt.setString(4, transaction.getDueDate().toString());
            if(transaction.getReturnDate() != null) {
                stmt.setString(5, transaction.getReturnDate().toString());
            }
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM BookTransaction WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Transaction transaction) {
        if (transaction == null || transaction.getId() == null)
            throw new IllegalArgumentException("Transaction and transaction ID must not be null.");
        return deleteByKey(transaction.getId());
    }

}
