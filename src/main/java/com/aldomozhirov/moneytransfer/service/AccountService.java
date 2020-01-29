package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.constant.ExceptionConstants;
import com.aldomozhirov.moneytransfer.constant.SupportedCurrencyCodes;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.exception.UnsupportedCurrencyException;

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

    public Account createAccount(Account account) throws RepositoryException, NoSuchIdException, UnsupportedCurrencyException {
        if (!repositoryFactory.getUserRepository().isExists(account.getUserId())) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_CREATE_ACCOUNT_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
                    account.getUserId())
            );
        }
        try {
            SupportedCurrencyCodes.valueOf(account.getCurrencyCode());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCurrencyException(String.format(
                    ExceptionConstants.UNSUPPORTED_CURRENCY,
                    account.getCurrencyCode()
            ));
        }
        return repositoryFactory.getAccountRepository().add(account);
    }

    public void deleteAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getAccountRepository().remove(accountId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_DELETE_ACCOUNT_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS,
                    accountId)
            );
        }
    }

    public Account getAccountById(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().getById(accountId);
        if (account == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_ACCOUNT_BY_ID,
                    accountId)
            );
        }
        return account;
    }

    public List<Account> getAccountsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_ACCOUNTS_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
                    userId)
            );
        }
        return repositoryFactory.getAccountRepository().getByUserId(userId);
    }

    public Double getAccountBalance(Long accountId) throws NoSuchIdException, RepositoryException {
        Account account = repositoryFactory.getAccountRepository().getById(accountId);
        if (account == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_BALANCE_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS,
                    accountId)
            );
        }
        return account.getBalance();
    }

    public List<Account> getAllAccounts() throws RepositoryException {
        return repositoryFactory.getAccountRepository().getAll();
    }

}
