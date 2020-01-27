package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public interface AccountTransactionsRepository {

    /**
     * Add new accountId to transactionId mapping
     * @param accountId
     * @param transactionId
     * @throws RepositoryException
     */
    void add(Long accountId, Long transactionId) throws RepositoryException;

    /**
     * Remove accountId to transactionId mapping
     * @param accountId
     * @param transactionId
     * @return true if removal is successful or false if there is no such mapping
     * @throws RepositoryException
     * @throws NoSuchIdException
     */
    boolean remove(Long accountId, Long transactionId) throws RepositoryException;

    /**
     * Get all transactionId mappings for specified accountId
     * @param accountId
     * @return List of transactionId mappings or empty List if there are no any
     * @throws RepositoryException
     * @throws NoSuchIdException
     */
    List<Long> getAll(Long accountId) throws RepositoryException;

}
