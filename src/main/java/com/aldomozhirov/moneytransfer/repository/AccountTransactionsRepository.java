package com.aldomozhirov.moneytransfer.repository;

import java.util.List;

public interface AccountTransactionsRepository {

    void add(Long accountId, Long transactionId);

    void remove(Long accountId, Long transactionId);

    List<Long> getAll(Long accountId);

}
