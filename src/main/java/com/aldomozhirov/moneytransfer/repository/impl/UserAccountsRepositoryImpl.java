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
    public void remove(Long userId, Long accountId) {
        userAccountsMap.get(userId).remove(accountId);
    }

    @Override
    public List<Long> getAll(Long userId) {
        return new ArrayList<>(userAccountsMap.get(userId));
    }

}
