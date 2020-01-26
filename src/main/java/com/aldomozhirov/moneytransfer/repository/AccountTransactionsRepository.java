package com.aldomozhirov.moneytransfer.repository;

import java.util.List;

public interface AccountTransactionsRepository {

    void add(Long accountId, Long transactionId);

    boolean remove(Long accountId, Long transactionId);

    List<Long> getAll(Long accountId);

}
