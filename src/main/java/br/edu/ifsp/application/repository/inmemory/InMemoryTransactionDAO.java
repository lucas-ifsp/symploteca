package br.edu.ifsp.application.repository.inmemory;

import br.edu.ifsp.domain.entities.transaction.Transaction;
import br.edu.ifsp.domain.usecases.transaction.TransactionDAO;

import java.util.*;

public class InMemoryTransactionDAO implements TransactionDAO {

    private static final Map<Integer, Transaction> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Transaction transaction) {
        idCounter++;
        transaction.setId(idCounter);
        db.put(idCounter, transaction);
        return idCounter;
    }

    @Override
    public Optional<Transaction> findOne(Integer key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> findTransactionByBookId(Integer id) {
        return db.values().stream()
                .filter(transaction -> transaction.getBook().getId() == id)
                .findAny();
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Transaction transaction) {
        Integer id = transaction.getId();
        if (db.containsKey(id)) {
            db.replace(id, transaction);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)) {
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Transaction transaction) {
        return deleteByKey(transaction.getId());
    }
}
