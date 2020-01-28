package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepositoryImpl implements AccountRepository {

    private Map<Long, Account> accountMap;
    private long increment;

    public AccountRepositoryImpl() {
        accountMap = new HashMap<>();
        increment = 1L;
    }

    @Override
    public synchronized Account add(Account account) throws RepositoryException {
        if (account.getId() != null) {
            if (accountMap.containsKey(account.getId())) {
                throw new RepositoryException(String.format(
                        "Account with id=%d already exists",
                        account.getId())
                );
            }
        } else {
            account.setId(increment());
        }
        accountMap.put(account.getId(), account);
        return account;
    }

    @Override
    public synchronized boolean remove(Long id) {
        return accountMap.remove(id) != null;
    }

    @Override
    public synchronized Account update(Long id, Account account) {
        if (!accountMap.containsKey(id)) {
            return null;
        }
        accountMap.put(id, account);
        return account;
    }

    @Override
    public boolean isExists(Long id) {
        return accountMap.containsKey(id);
    }

    @Override
    public Account getById(Long id) {
        return accountMap.get(id);
    }

    @Override
    public List<Account> getByUserId(Long userId) {
        List<Account> accounts = new ArrayList<>();
        for(Account account : accountMap.values()) {
            if (account.getUserId().equals(userId)) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

    private long increment() {
        while (accountMap.containsKey(increment)) {
            increment++;
        }
        return increment;
    }

}
