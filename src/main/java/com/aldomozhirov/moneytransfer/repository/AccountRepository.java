package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Account;

import java.util.List;

public interface AccountRepository {

    long add(Account account);

    void remove(Long id);

    void update(Account account);

    Account get(Long id);

    List<Account> getAll();

}
