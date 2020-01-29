package com.aldomozhirov.moneytransfer.factory;

import com.aldomozhirov.moneytransfer.repository.*;
import com.aldomozhirov.moneytransfer.repository.impl.*;

public class RepositoryFactory {

    private static UserRepository userRepository;
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;

    private static RepositoryFactory instance;

    private RepositoryFactory() {
        userRepository = new UserRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
        transactionRepository = new TransactionRepositoryImpl();
    }

    public static RepositoryFactory create() {
        if (instance != null) {
            throw new RuntimeException("RepositoryFactory already created");
        }
        instance = new RepositoryFactory();
        return instance;
    }

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            throw new RuntimeException("RepositoryFactory have not been created");
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

}
