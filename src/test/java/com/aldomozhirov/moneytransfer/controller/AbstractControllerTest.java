package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.MoneyTransferApp;
import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;
import com.aldomozhirov.moneytransfer.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class AbstractControllerTest {

    static final User[] SAMPLE_USERS = {
        new User(1L, "Aleksei", "Domozhirov"),
        new User(2L, "John", "Smith"),
        new User(3L, "Peter", "Simpson"),
        new User(4L, "Jenifer", "Roberts")
    };

    static final Account[] SAMPLE_ACCOUNTS = {
        new Account(1L, 1L, 10.0),
        new Account(2L, 1L, 30.0),
        new Account(3L, 2L, 60.0),
        new Account(4L, 3L, 140.56),
        new Account(5L, 3L, 280.10)
    };

    static final Transaction[] SAMPLE_TRANSACTIONS = {
        new Transaction(1L, 1L, 2L, 10.0),
        new Transaction(2L, 2L, 1L, 20.0),
        new Transaction(3L, 2L, 3L, 20.0),
        new Transaction(4L, 3L, 2L, 30.0),
        new Transaction(5L, 3L, 4L, 21.28),
        new Transaction(6L, 4L, 5L, 10.0),
        new Transaction(7L, 4L, 5L, 300.0)
    };

    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    private static HttpClient client;
    private URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8080");
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void setup() throws Exception {
        MoneyTransferApp.start();
        addSampleData(MoneyTransferApp.getRepositoryFactory());
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
    }

    @AfterClass
    public static void closeClient() {
        HttpClientUtils.closeQuietly(client);
    }

    private static void addSampleData(RepositoryFactory repositoryFactory) throws RepositoryException {
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

    HttpResponse getRequest(String path) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        HttpGet request = new HttpGet(uri);
        return client.execute(request);
    }

    HttpResponse deleteRequest(String path) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        HttpDelete request = new HttpDelete(uri);
        return client.execute(request);
    }

    HttpResponse putRequest(String path) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        HttpPut request = new HttpPut(uri);
        return client.execute(request);
    }

    HttpResponse postRequest(String path, String jsonString) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        StringEntity entity = new StringEntity(jsonString);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        return client.execute(request);
    }

    ObjectMapper getMapper() {
        return mapper;
    }

}
