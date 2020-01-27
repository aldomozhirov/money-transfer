package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.*;
import com.aldomozhirov.moneytransfer.repository.impl.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

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
        User user = repositoryFactory.getUserRepository().get(userId);
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

    public List<Account> getUserAccounts(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get accounts of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        return repositoryFactory.getUserAccountsRepository()
                .getAll(userId).stream()
                .map(id -> {
                    try {
                        return accountRepository.get(id);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> getUserTransactions(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get transactions of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        AccountTransactionsRepository accountTransactionsRepository = repositoryFactory.getAccountTransactionsRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getUserAccountsRepository()
                .getAll(userId).stream()
                .flatMap((accountId) -> {
                    try {
                        return accountTransactionsRepository
                                .getAll(accountId).stream()
                                .map(transactionId -> {
                                    try {
                                        return transactionRepository.get(transactionId);
                                    } catch (RepositoryException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
