package br.edu.ifsp.domain.usecases.transaction;

import br.edu.ifsp.domain.entities.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public class FindTransactionUseCase {
    private TransactionDAO transactionDAO;

    public FindTransactionUseCase(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public Optional<Transaction> findOne(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return transactionDAO.findOne(id);
    }

    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }
}
