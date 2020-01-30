package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.exception.UnsupportedCurrencyException;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AccountServiceTest extends AbstractServiceTest {

    private AccountService accountService = AccountService.getInstance();
    private AccountRepository accountRepository = RepositoryFactory.getInstance().getAccountRepository();

    @Test
    public void testCreateAccount() throws RepositoryException, NoSuchIdException, UnsupportedCurrencyException {
        Account accountToCreate = new Account(2L, "USD", 0.0);
        Account createdAccount = accountService.createAccount(accountToCreate);
        Assert.assertEquals(accountToCreate.getUserId(), createdAccount.getUserId());
        Assert.assertEquals(accountToCreate.getCurrencyCode(), createdAccount.getCurrencyCode());
        Assert.assertEquals(accountToCreate.getBalance(), createdAccount.getBalance());
        Account accountInRepository = accountRepository.getById(createdAccount.getId());
        assertAccountEquals(createdAccount, accountInRepository);
    }

    @Test
    public void testDeleteAccount() throws NoSuchIdException, RepositoryException {
        Long accountToDeleteId = 5L;
        Account deletedAccount = accountService.deleteAccount(accountToDeleteId);
        assertAccountEquals(SAMPLE_ACCOUNTS[4], deletedAccount);
        Assert.assertFalse(accountRepository.isExists(accountToDeleteId));
    }

    @Test
    public void testGetAllAccounts() throws RepositoryException {
        List<Account> receivedAccountsList = accountService.getAllAccounts();
        List<Account> accountListInRepository = accountRepository.getAll();
        assertAccountsEquals(accountListInRepository, receivedAccountsList);
    }

}
