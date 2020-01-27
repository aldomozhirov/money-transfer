package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.RepositoryFactory;

import java.util.List;

public class UserService {

    private static UserService instance;

    private RepositoryFactory repositoryFactory;

    private UserService() {
        repositoryFactory = RepositoryFactory.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User createUser(User user) throws RepositoryException {
        return repositoryFactory.getUserRepository().add(user);
    }

    public void deleteUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().remove(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to delete user with id=%d cause such user does not exists",
                    userId)
            );
        }
    }

    public User getUser(Long userId) throws NoSuchIdException, RepositoryException {
        User user = repositoryFactory.getUserRepository().getById(userId);
        if (user == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find user with id=%d",
                    userId)
            );
        }
        return user;
    }

    public List<User> getAllUsers() throws RepositoryException {
        return repositoryFactory.getUserRepository().getAll();
    }

}
