package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.constant.ExceptionConstants;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.*;
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

    public Transaction performTransaction(Transaction transaction) throws IncorrectInputDataException, NoSuchIdException, NotEnoughMoneyException, RepositoryException, IncompatibleCurrenciesException {
        if(transaction.getAmount() < 0) {
            throw new IncorrectInputDataException(ExceptionConstants.TRANSACTION_AMOUNT_SHOULD_BE_POSITIVE);
        }
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        Account source = accountRepository.getById(transaction.getSourceAccountId());
        Account target = accountRepository.getById(transaction.getTargetAccountId());
        if (source == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_SOURCE_ACCOUNT,
                    transaction.getSourceAccountId()));
        }
        if (target == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_TARGET_ACCOUNT,
                    transaction.getTargetAccountId()));
        }
        if (!source.getCurrencyCode().equals(target.getCurrencyCode())) {
            throw new IncompatibleCurrenciesException(
                    ExceptionConstants.ACCOUNTS_HAVE_INCOMPATIBLE_CURRENCY_CODES
            );
        }
        moveMoney(source, target, transaction.getAmount(), accountRepository);
        return transactionRepository.add(transaction);
    }

    public Transaction revertTransaction(Long transactionId) throws RepositoryException, NoSuchIdException, NotEnoughMoneyException {
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        Transaction transaction = transactionRepository.getById(transactionId);
        if (transaction == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_TRANSACTION_BY_ID,
                    transactionId));
        }
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        Account source = accountRepository.getById(transaction.getSourceAccountId());
        if (source == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_REVERT_TRANSACTION_CAUSE_SOURCE_ACCOUNT_REMOVED,
                    transaction.getTargetAccountId()));
        }
        Account target = accountRepository.getById(transaction.getTargetAccountId());
        if (target == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_REVERT_TRANSACTION_CAUSE_TARGET_ACCOUNT_REMOVED,
                    transaction.getTargetAccountId()));
        }
        moveMoney(target, source, transaction.getAmount(), accountRepository);
        return transactionRepository.remove(transactionId);
    }

    public Transaction getTransactionById(long transactionId) throws NoSuchIdException, RepositoryException {
        Transaction transaction = repositoryFactory.getTransactionRepository().getById(transactionId);
        if (transaction == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_TRANSACTION_BY_ID,
                    transactionId));
        }
        return transaction;
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) throws NoSuchIdException, RepositoryException {
        if(!repositoryFactory.getAccountRepository().isExists(accountId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS,
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getByAccountId(accountId);
    }

    public List<Transaction> getTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
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
                    ExceptionConstants.UNABLE_TO_GET_OUTCOME_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS,
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getBySourceAccountId(accountId);
    }

    public List<Transaction> getOutcomeTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_OUTCOME_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
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
                    ExceptionConstants.UNABLE_TO_GET_INCOME_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS,
                    accountId)
            );
        }
        return repositoryFactory.getTransactionRepository().getByTargetAccountId(accountId);
    }

    public List<Transaction> getIncomeTransactionsByUser(Long userId) throws NoSuchIdException, RepositoryException {
        if (!repositoryFactory.getUserRepository().isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_GET_INCOME_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
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

    private void moveMoney(Account source, Account target, Double amount, AccountRepository accountRepository) throws NotEnoughMoneyException, RepositoryException {
        if (source.getBalance() - amount < 0) {
            throw new NotEnoughMoneyException(String.format(
                    ExceptionConstants.NOT_ENOUGH_MONEY,
                    source.getId())
            );
        }
        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        accountRepository.update(source.getId(), source);
        accountRepository.update(target.getId(), target);
    }

}
