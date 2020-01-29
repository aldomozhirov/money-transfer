package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.exception.UnsupportedCurrencyException;
import org.junit.Test;

import java.util.List;

public class AccountServiceTest extends AbstractServiceTest {

    AccountService accountService = AccountService.getInstance();

    @Test
    public void testCreateAccount() throws RepositoryException, NoSuchIdException, UnsupportedCurrencyException {
        Account account = new Account();
        accountService.createAccount(account);
    }

    @Test
    public void testDeleteAccount() throws NoSuchIdException, RepositoryException {
        Account deletedAccount = accountService.deleteAccount(5L);
    }

    @Test
    public void testGetAccountById() throws NoSuchIdException, RepositoryException {
        Account account = accountService.getAccountById(1L);
    }

    @Test
    public void testGetAccountsByUser() throws NoSuchIdException, RepositoryException {
        List<Account> accounts = accountService.getAccountsByUser(1L);
    }

    @Test
    public void testGetAllAccounts() throws RepositoryException {
        List<Account> accounts = accountService.getAllAccounts();
    }

    @Test
    public void testGetAccountBalance() throws NoSuchIdException, RepositoryException {
        Double balance = accountService.getAccountBalance(1L);
    }

}
