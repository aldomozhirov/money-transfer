package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Account;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class AccountControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testCreateAccountForNotExistingUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testDeleteAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testDeleteNotExistingAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAccountById() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAccountByIncorrectId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAccountsByUserId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAccountsByIncorrectUserId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAccountBalance() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetNotExistingAccountBalance() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAllAccounts() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account[] accounts = mapper.readValue(jsonString, Account[].class);
        assertEquals(0, accounts.length);
    }

}
