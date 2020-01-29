package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.constant.ExceptionConstants;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository {

    private Map<Long, Transaction> transactionMap;
    private long increment;

    public TransactionRepositoryImpl() {
        transactionMap = new HashMap<>();
        increment = 1L;
    }

    @Override
    public synchronized Transaction add(Transaction transaction) throws RepositoryException {
        if (transaction.getId() != null) {
            if (transactionMap.containsKey(transaction.getId())) {
                throw new RepositoryException(String.format(
                        ExceptionConstants.TRANSACTION_ALREADY_EXISTS,
                        transaction.getId())
                );
            }
        } else {
            transaction.setId(increment());
        }
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Transaction remove(Long id) {
        return transactionMap.remove(id);
    }

    @Override
    public boolean isExists(Long id) {
        return transactionMap.containsKey(id);
    }

    @Override
    public Transaction getById(Long id) {
        return transactionMap.get(id);
    }

    @Override
    public List<Transaction> getBySourceAccountId(Long sourceAccountId) {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : transactionMap.values()) {
            if (transaction.getSourceAccountId().equals(sourceAccountId)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getByTargetAccountId(Long targetAccountId) {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : transactionMap.values()) {
            if (transaction.getTargetAccountId().equals(targetAccountId)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getByAccountId(Long accountId) {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : transactionMap.values()) {
            if (transaction.getSourceAccountId().equals(accountId) || transaction.getTargetAccountId().equals(accountId)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAll() {
        return new ArrayList<>(transactionMap.values());
    }

    private long increment() {
        while (transactionMap.containsKey(increment)) {
            increment++;
        }
        return increment;
    }

}
