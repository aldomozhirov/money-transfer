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
     * Remove Transaction entry with specified id
     * @param id
     * @return true if removal is successful or false if there is no Transaction with specified id
     * @throws RepositoryException
     */
    boolean remove(Long id) throws RepositoryException;

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
    Transaction getById(Long id) throws RepositoryException;

    /**
     * Get Transaction entries by sourceAccountId
     * @param sourceAccountId
     * @return
     * @throws RepositoryException
     */
    List<Transaction> getBySourceAccountId(Long sourceAccountId) throws RepositoryException;

    /**
     * Get Transaction entries by targetAccountId
     * @param targetAccountId
     * @return
     * @throws RepositoryException
     */
    List<Transaction> getByTargetAccountId(Long targetAccountId) throws RepositoryException;

    /**
     * Get Transaction entries by accountId
     * @param accountId
     * @return
     * @throws RepositoryException
     */
    List<Transaction> getByAccountId(Long accountId) throws RepositoryException;

    /**
     * Get all Transaction entries stored
     * @return List of Transaction objects
     * @throws RepositoryException
     */
    List<Transaction> getAll() throws RepositoryException;

}
