package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.*;
import com.aldomozhirov.moneytransfer.repository.impl.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

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
        Account createdAccount = repositoryFactory.getAccountRepository().add(account);
        if (!repositoryFactory.getUserRepository().isExists(account.getUserId())) {
            throw new NoSuchIdException();
        }
        repositoryFactory.getUserAccountsRepository().add(account.getUserId(), createdAccount.getId());
        return createdAccount;
    }

    public void deleteAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getAccountRepository().remove(accountId)) {
            throw new NoSuchIdException();
        }
    }

    public List<Account> getAllAccounts() throws RepositoryException {
        return repositoryFactory.getAccountRepository().getAll();
    }

    public Account getAccountById(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().get(accountId);
        if (account == null) {
            throw new NoSuchIdException();
        }
        return account;
    }

    public Double getAccountBalance(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().get(accountId);
        if (account == null) {
            throw new NoSuchIdException();
        }
        return account.getBalance();
    }

    public List<Transaction> getAccountTransactions(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().get(accountId);
        if (account == null) {
            throw new NoSuchIdException();
        }
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getAccountTransactionsRepository()
                .getAll(accountId).stream()
                .map(id -> {
                    try {
                        return transactionRepository.get(id);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
