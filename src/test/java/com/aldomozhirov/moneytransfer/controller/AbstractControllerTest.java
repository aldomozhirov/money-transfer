package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.MoneyTransferApp;
import com.aldomozhirov.moneytransfer.RepositoryFactory;
import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

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
        repositoryFactory.getAccountRepository().add(new Account(4L, 3L, 280.10));

        // Add sample transactions

        repositoryFactory.getTransactionRepository().add(new Transaction(1L, 2L, 1L, 10.0));
        repositoryFactory.getTransactionRepository().add(new Transaction(2L, 1L, 3L, 21.28));

    }

    protected HttpResponse getRequest(String path) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        HttpGet request = new HttpGet(uri);
        return client.execute(request);
    }

    protected HttpResponse deleteRequest(String path) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        HttpDelete request = new HttpDelete(uri);
        return client.execute(request);
    }

    protected HttpResponse postRequest(String path, String jsonString) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        StringEntity entity = new StringEntity(jsonString);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        return client.execute(request);
    }

    protected HttpResponse putRequest(String path, String jsonString) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        StringEntity entity = new StringEntity(jsonString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        return client.execute(request);
    }

}
