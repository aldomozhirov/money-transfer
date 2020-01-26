package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.repository.*;

public class RepositoryFactory {

    private static UserRepository userRepository;
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;
    private static UserAccountsRepository userAccountsRepository;
    private static AccountTransactionsRepository accountTransactionsRepository;

    private static RepositoryFactory instance;

    private RepositoryFactory() {
        userRepository = new UserRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
        transactionRepository = new TransactionRepositoryImpl();
        userAccountsRepository = new UserAccountsRepositoryImpl();
        accountTransactionsRepository = new AccountTransactionsRepositoryImpl();
    }

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }
        return instance;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public UserAccountsRepository getUserAccountsRepository() {
        return userAccountsRepository;
    }

    public AccountTransactionsRepository getAccountTransactionsRepository() {
        return accountTransactionsRepository;
    }

}
