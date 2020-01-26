package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.repository.AccountTransactionsRepository;

import java.util.*;

public class AccountTransactionsRepositoryImpl implements AccountTransactionsRepository {

    private Map<Long, Set<Long>> accountTransactionsMap;

    public AccountTransactionsRepositoryImpl() {
        accountTransactionsMap = new HashMap<>();
    }

    @Override
    public void add(Long accountId, Long transactionId) {
        if (!accountTransactionsMap.containsKey(accountId)) {
            accountTransactionsMap.put(accountId, new HashSet<>());
        }
        accountTransactionsMap.get(accountId).add(transactionId);
    }

    @Override
    public void remove(Long accountId, Long transactionId) {
        accountTransactionsMap.get(accountId).remove(transactionId);
    }

    @Override
    public List<Long> getAll(Long accountId) {
        return new ArrayList<>(accountTransactionsMap.get(accountId));
    }

}
