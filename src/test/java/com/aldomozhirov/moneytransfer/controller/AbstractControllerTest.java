package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.AbstractTest;
import com.aldomozhirov.moneytransfer.MoneyTransferApp;
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

public abstract class AbstractControllerTest extends AbstractTest {

    private static final int PORT = 8080;
    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    private static HttpClient client;
    private URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost").setPort(PORT);
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() throws Exception {
        MoneyTransferApp app = new MoneyTransferApp();
        app.start();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
        addSampleData(app.getRepositoryFactory());
    }

    @AfterClass
    public static void closeClient() {
        HttpClientUtils.closeQuietly(client);
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

    HttpResponse putRequest(String path, String jsonString) throws URISyntaxException, IOException {
        URI uri = builder.setPath(path).build();
        StringEntity entity = new StringEntity(jsonString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
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
