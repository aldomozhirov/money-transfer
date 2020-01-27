package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepositoryImpl implements AccountRepository {

    private Map<Long, Account> accountMap;
    private Long increment;

    public AccountRepositoryImpl() {
        accountMap = new HashMap<>();
    }

    @Override
    public Account add(Account account) {
        account.setId(increment++);
        return accountMap.put(account.getId(), account);
    }

    @Override
    public boolean remove(Long id) {
        return accountMap.remove(id) != null;
    }

    @Override
    public Account update(Long id, Account account) {
        if (!accountMap.containsKey(id)) {
            return null;
        }
        accountMap.put(id, account);
        return account;
    }

    @Override
    public Account get(Long id) {
        return accountMap.get(id);
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

}
