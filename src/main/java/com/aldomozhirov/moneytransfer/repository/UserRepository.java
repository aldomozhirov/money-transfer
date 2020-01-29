package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public interface UserRepository {

    /**
     * Add new User entry
     * @param user
     * @return
     * @throws RepositoryException
     */
    User add(User user) throws RepositoryException;

    /**
     * Remove User entry with specified id
     * @param id
     * @return true if removal is successful or false if there is no User with specified id
     * @throws RepositoryException
     */
    User remove(Long id) throws RepositoryException;

    /**
     * Update User entry
     * @param user
     * @return updated User object or null if there is no User with specified id
     * @throws RepositoryException
     */
    User update(Long id, User user) throws RepositoryException;

    /**
     * Check if User entry with specified id exists
     * @return
     */
    boolean isExists(Long id);

    /**
     * Get User entry
     * @param id
     * @return User object or null if there is no User with specified id
     * @throws RepositoryException
     */
    User getById(Long id) throws RepositoryException;

    /**
     * Get all User entries stored
     * @return List of User objects
     * @throws RepositoryException
     */
    List<User> getAll() throws RepositoryException;

}
