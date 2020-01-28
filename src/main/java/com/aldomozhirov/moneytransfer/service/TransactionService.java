package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.NotEnoughMoneyException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionService {

    private static TransactionService instance;

    private RepositoryFactory repositoryFactory;

    private TransactionService() {
        repositoryFactory = RepositoryFactory.getInstance();
    };

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public Transaction performTransaction(Transaction transaction) throws IncorrectInputDataException, NoSuchIdException, NotEnoughMoneyException, RepositoryException {
        if(transaction.getAmount() < 0) {
            throw new IncorrectInputDataException("Transaction amount have to be grater than 0");
        }
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        Account source = accountRepository.getById(transaction.getSourceAccountId());
        Account target = accountRepository.getById(transaction.getTargetAccountId());
        if (source == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find source account with id=%d specified in transaction",
                    transaction.getSourceAccountId()));
        }
        if (target == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find target account with id=%d specified in transaction",
                    transaction.getTargetAccountId()));
        }
        if (source.getBalance() - transaction.getAmount() < 0) {
            throw new NotEnoughMoneyException(String.format(
                    "Not enough money to perform the transaction on the source account with id=%d",
                    transaction.getSourceAccountId())
            );
        }
        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() - transaction.getAmount());
        return transactionRepository.add(transaction);
    }

    public Transaction getTransactionById(long transactionId) throws NoSuchIdException, RepositoryException {
        Transaction transaction = repositoryFactory.getTransactionRepository().getById(transactionId);
        if (transaction == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find transaction with id=%d",
                    transactionId));
        }
        return transaction;
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if(!repositoryFactory.getAccountRepository().isExists(accountId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get transactions of account with id=%d cause such account does not exists",
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getByAccountId(accountId);
    }

    public List<Transaction> getTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get transactions of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getAccountRepository()
                .getByUserId(userId).stream()
                .flatMap((account) -> {
                    try {
                        return transactionRepository.getByAccountId(account.getId()).stream();
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(distinctByKey(Transaction::getId))
                .sorted(Comparator.comparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getOutcomeTransactionsByAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if(!repositoryFactory.getAccountRepository().isExists(accountId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get outcome transactions of account with id=%d cause such account does not exists",
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getBySourceAccountId(accountId);
    }

    public List<Transaction> getOutcomeTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get outcome transactions of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getAccountRepository()
                .getByUserId(userId).stream()
                .flatMap((account) -> {
                    try {
                        return transactionRepository.getBySourceAccountId(account.getId()).stream();
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getIncomeTransactionsByAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if(!repositoryFactory.getAccountRepository().isExists(accountId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get income transactions of account with id=%d cause such account does not exists",
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getByTargetAccountId(accountId);
    }

    public List<Transaction> getIncomeTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    "Unable to get income transactions of user with id=%d cause such user does not exists",
                    userId)
            );
        }
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getAccountRepository()
                .getByUserId(userId).stream()
                .flatMap((account) -> {
                    try {
                        return transactionRepository.getByTargetAccountId(account.getId()).stream();
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() throws RepositoryException {
        return repositoryFactory.getTransactionRepository().getAll();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
