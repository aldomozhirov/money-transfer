package com.aldomozhirov.moneytransfer.repository;

import java.util.List;

public interface UserAccountsRepository {

    void add(Long userId, Long accountId);

    boolean remove(Long userId, Long accountId);

    List<Long> getAll(Long userId);

}
