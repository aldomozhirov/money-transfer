package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository {

    private Map<Long, Transaction> transactionMap;
    private Long increment;

    public TransactionRepositoryImpl() {
        transactionMap = new HashMap<>();
        increment = 0L;
    }

    @Override
    public Transaction add(Transaction transaction) {
        transaction.setId(increment++);
        return transactionMap.put(transaction.getId(), transaction);
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
        //TODO
        return null;
    }

    @Override
    public List<Transaction> getByTargetAccountId(Long targetAccountId) {
        //TODO
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return new ArrayList<>(transactionMap.values());
    }

}
