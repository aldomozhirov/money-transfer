package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.repository.UserAccountsRepository;

import java.util.*;
import java.util.stream.Collectors;

public class UserAccountsRepositoryImpl implements UserAccountsRepository {

    private Map<Long, Set<Long>> userAccountsMap;

    public UserAccountsRepositoryImpl() {
        userAccountsMap = new HashMap<>();
    }

    @Override
    public void add(Long userId, Long accountId) {
        if(!userAccountsMap.containsKey(userId)) {
            userAccountsMap.put(userId, new HashSet<>());
        }
        userAccountsMap.get(userId).add(accountId);
    }

    @Override
    public boolean remove(Long userId, Long accountId) {
        Set<Long> accountSet;
        if ((accountSet = userAccountsMap.get(userId)) == null) {
            return false;
        }
        return accountSet.remove(accountId);
    }

    @Override
    public List<Long> getAll(Long userId) {
        if (!userAccountsMap.containsKey(userId)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(userAccountsMap.get(userId));
    }

}
