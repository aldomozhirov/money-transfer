package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction add(Transaction transaction);

    Transaction get(Long id);

    List<Transaction> getAll();

}
