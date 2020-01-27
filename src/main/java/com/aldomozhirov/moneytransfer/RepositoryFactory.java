package com.aldomozhirov.moneytransfer;

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


}
