package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;

import java.util.List;

public class AccountService {

    private static AccountService instance;

    private RepositoryFactory repositoryFactory;

    private AccountService() {
        repositoryFactory = RepositoryFactory.getInstance();
    };

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public Account createAccount(Account account) throws RepositoryException, NoSuchIdException {
        if (!repositoryFactory.getUserRepository().isExists(account.getUserId())) {
            throw new NoSuchIdException(String.format(
                    "Unable to create account for user with id=%d cause such user does not exists",
                    account.getUserId())
            );
        }
        return repositoryFactory.getAccountRepository().add(account);
    }

    public void deleteAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getAccountRepository().remove(accountId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to delete account with id=%d cause such account does not exists",
                    accountId)
            );
        }
    }

    public Account getAccountById(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().getById(accountId);
        if (account == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find account with id=%d",
                    accountId)
            );
        }
        return account;
    }

    public List<Account> getAccountsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get accounts of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        return repositoryFactory.getAccountRepository().getByUserId(userId);
    }

    public Double getAccountBalance(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().getById(accountId);
        if (account == null) {
            throw new NoSuchIdException(String.format(
                    "Unable to get current balance on account with id=%d cause such account does not exists",
                    accountId)
            );
        }
        return account.getBalance();
    }

    public List<Account> getAllAccounts() throws RepositoryException {
        return repositoryFactory.getAccountRepository().getAll();
    }

}
