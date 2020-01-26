package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.AccountTransactionsRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private AccountTransactionsRepository accountTransactionsRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          AccountTransactionsRepository accountTransactionsRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.accountTransactionsRepository = accountTransactionsRepository;
    }

    public Account createAccount(Account account) {
        long id = accountRepository.add(account);
        account.setId(id);
        return account;
    }

    public boolean deleteAccount(Long accountId) {
        return accountRepository.remove(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.get(accountId);
    }

    public Double getAccountBalance(Long accountId) {
        return accountRepository.get(accountId).getBalance();
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return accountTransactionsRepository
                .getAll(accountId).stream()
                .map((transactionId) -> transactionRepository.get(transactionId))
                .collect(Collectors.toList());
    }

    public void withdrawAmountFromAccount(Long accountId, Long amount) {
        //TODO
    }

    public void depositAmountToAccount(Long accountId, Long amount) {
        //TODO
    }

}
