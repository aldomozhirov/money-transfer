package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Account;

import java.util.List;

public interface AccountRepository {

    long add(Account account);

    boolean remove(Long id);

    boolean update(Account account);

    Account get(Long id);

    List<Account> getAll();

}
