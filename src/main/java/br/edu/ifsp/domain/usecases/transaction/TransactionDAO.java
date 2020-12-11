package br.edu.ifsp.domain.usecases.transaction;

import br.edu.ifsp.domain.entities.transaction.Transaction;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface TransactionDAO extends DAO<Transaction, Integer> {
    Optional<Transaction> findTransactionByBookId(Integer id);
}
