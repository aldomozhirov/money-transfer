package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
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

    public Account createAccount(Account account) {
        Long accountId = repositoryFactory.getAccountRepository().add(account);
        RepositoryFactory.getInstance().getUserAccountsRepository().add(account.getUserId(), accountId);
        account.setId(accountId);
        return account;
    }

    public void deleteAccount(Long accountId) {
        repositoryFactory.getAccountRepository().remove(accountId);
    }

    public List<Account> getAllAccounts() {
        return repositoryFactory.getAccountRepository().getAll();
    }

    public Account getAccountById(Long accountId) {
        return repositoryFactory.getAccountRepository().get(accountId);
    }

    public Double getAccountBalance(Long accountId) {
        return repositoryFactory.getAccountRepository().get(accountId).getBalance();
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getAccountTransactionsRepository()
                .getAll(accountId).stream()
                .map(transactionRepository::get)
                .collect(Collectors.toList());
    }

}
