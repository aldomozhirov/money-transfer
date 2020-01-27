package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public interface TransactionRepository {

    /**
     * Add new Transaction entry
     * @param transaction
     * @return
     * @throws RepositoryException
     */
    Transaction add(Transaction transaction) throws RepositoryException;

    /**
     * Check if Transaction entry with specified id exists
     * @return
     */
    boolean isExists(Long id);

    /**
     * Get Transaction entry
     * @param id
     * @return Transaction object or null if there is no Transaction with specified id
     * @throws RepositoryException
     */
    Transaction get(Long id) throws RepositoryException;

    /**
     * Get all Transaction entries stored
     * @return List of Transaction objects
     * @throws RepositoryException
     */
    List<Transaction> getAll() throws RepositoryException;

}
