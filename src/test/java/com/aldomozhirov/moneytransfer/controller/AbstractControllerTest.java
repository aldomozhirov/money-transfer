package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.MoneyTransferApp;
import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractControllerTest {

    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    static HttpClient client;
    ObjectMapper mapper = new ObjectMapper();
    URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8080");


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

        // Add sample users

        repositoryFactory.getUserRepository().add(new User(1L, "Aleksei", "Domozhirov"));
        repositoryFactory.getUserRepository().add(new User(2L, "John", "Smith"));
        repositoryFactory.getUserRepository().add(new User(3L, "Peter", "Simpson"));
        repositoryFactory.getUserRepository().add(new User(4L, "Jenifer", "Roberts"));

        // Add sample accounts

        repositoryFactory.getAccountRepository().add(new Account(1L, 1L, 10.0));
        repositoryFactory.getAccountRepository().add(new Account(2L, 2L, 30.0));
        repositoryFactory.getAccountRepository().add(new Account(3L, 3L, 140.56));

        // Add sample transactions

        repositoryFactory.getTransactionRepository().add(new Transaction(1L, 2L, 1L, 10.0));
        repositoryFactory.getTransactionRepository().add(new Transaction(2L, 1L, 3L, 21.28));

    }


}
