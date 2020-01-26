package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Transaction;

import java.util.List;

public interface TransactionRepository {

    long add(Transaction user);

    Transaction get(long id);

    List<Transaction> getAll();

}
