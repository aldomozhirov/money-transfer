package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public interface UserAccountsRepository {

    /**
     * Add new userId to accountId mapping
     * @param userId
     * @param accountId
     * @throws RepositoryException
     */
    void add(Long userId, Long accountId) throws RepositoryException;

    /**
     * Remove userId to accountId mapping
     * @param userId
     * @param accountId
     * @return true if removal is successful or false if there is no such mapping
     * @throws RepositoryException
     * @throws NoSuchIdException
     */
    boolean remove(Long userId, Long accountId) throws RepositoryException;

    /**
     * Get all accountId mappings for specified userId
     * @param userId
     * @return List of accountId mappings or empty List if there are no any
     * @throws RepositoryException
     * @throws NoSuchIdException
     */
    List<Long> getAll(Long userId) throws RepositoryException;

}
