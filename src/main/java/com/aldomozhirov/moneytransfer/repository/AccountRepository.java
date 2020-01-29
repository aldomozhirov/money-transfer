package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public interface AccountRepository {

    /**
     * Add new Account entry
     * @param account
     * @return
     * @throws RepositoryException
     */
    Account add(Account account) throws RepositoryException;

    /**
     * Remove Account entry with specified id
     * @param id
     * @return true if removal is successful or false if there is no Account with specified id
     * @throws RepositoryException
     */
    Account remove(Long id) throws RepositoryException;

    /**
     * Check if Account entry with specified id exists
     * @return
     */
    boolean isExists(Long id);

    /**
     * Update Account entry
     * @param account
     * @return updated Account object or null if there is no Account with specified id
     * @throws RepositoryException
     */
    Account update(Long id, Account account) throws RepositoryException;

    /**
     * Get Account entry by accountId
     * @param id
     * @return Account object or null if there is no Account with specified id
     * @throws RepositoryException
     */
    Account getById(Long id) throws RepositoryException;


    /**
     * Get Account entries by userId
     * @param userId
     * @return
     * @throws RepositoryException
     */
    List<Account> getByUserId(Long userId) throws RepositoryException;

    /**
     * Get all Account entries stored
     * @return List of Account objects
     * @throws RepositoryException
     */
    List<Account> getAll() throws RepositoryException;

}
