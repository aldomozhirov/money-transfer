package com.aldomozhirov.moneytransfer;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;
import com.aldomozhirov.moneytransfer.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractTest {

    protected static final User[] SAMPLE_USERS = {
            new User(1L, "Aleksei", "Domozhirov"),
            new User(2L, "John", "Smith"),
            new User(3L, "Peter", "Simpson"),
            new User(4L, "Jenifer", "Roberts")
    };

    protected static final Account[] SAMPLE_ACCOUNTS = {
            new Account(1L,1L, "USD", 10.0),
            new Account(2L, 1L, "USD", 30.0),
            new Account(3L, 2L, "USD", 60.0),
            new Account(4L, 3L, "USD", 140.56),
            new Account(5L, 3L, "USD", 280.10),
            new Account(6L, 3L, "EUR", 30.10)
    };

    protected static final Transaction[] SAMPLE_TRANSACTIONS = {
            new Transaction(1L, 1L, 2L, 10.0),
            new Transaction(2L, 2L, 1L, 20.0),
            new Transaction(3L, 2L, 3L, 20.0),
            new Transaction(4L, 3L, 2L, 30.0),
            new Transaction(5L, 3L, 4L, 21.28),
            new Transaction(6L, 4L, 5L, 10.0),
            new Transaction(7L, 4L, 5L, 300.0),
            new Transaction(8L, 10L, 5L, 10.0),
            new Transaction(9L, 4L, 10L, 300.0)
    };

    protected static void addSampleData(RepositoryFactory repositoryFactory) throws RepositoryException {
        UserRepository userRepository = repositoryFactory.getUserRepository();
        for(User u : SAMPLE_USERS) {
            userRepository.add(u);
        }
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        for(Account a : SAMPLE_ACCOUNTS) {
            accountRepository.add(a);
        }
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        for(Transaction t : SAMPLE_TRANSACTIONS) {
            transactionRepository.add(t);
        }
    }

    protected void assertUserEquals(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    protected void assertUsersEquals(User[] expected, User[] actual) {
        TestCase.assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertUserEquals(expected[i], actual[i]);
        }
    }

    protected void assertUsersEquals(List<User> expected, List<User> actual) {
        assertUsersEquals(expected.toArray(new User[0]), actual.toArray(new User[0]));
    }

    protected void assertAccountEquals(Account expected, Account actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }

    protected void assertAccountsEquals(Account[] expected, Account[] actual) {
        TestCase.assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertAccountEquals(expected[i], actual[i]);
        }
    }

    protected void assertAccountsEquals(List<Account> expected, List<Account> actual) {
        assertAccountsEquals(expected.toArray(new Account[0]), actual.toArray(new Account[0]));
    }

    protected void assertTransactionEquals(Transaction expected, Transaction actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getSourceAccountId(), actual.getSourceAccountId());
        Assert.assertEquals(expected.getTargetAccountId(), actual.getTargetAccountId());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
    }

    protected void assertTransactionsEquals(Transaction[] expected, Transaction[] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertTransactionEquals(expected[i], actual[i]);
        }
    }
    protected void assertTransactionsEquals(List<Transaction> expected, List<Transaction> actual) {
        assertTransactionsEquals(expected.toArray(new Transaction[0]), actual.toArray(new Transaction[0]));
    }


}
