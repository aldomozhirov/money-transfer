package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Account;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertTrue;
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
        HttpResponse response = getRequest("/account/4");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void testDeleteNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/5");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
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
        HttpResponse response = getRequest("/account/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account[] accounts = mapper.readValue(jsonString, Account[].class);
        assertTrue(accounts.length > 0);
    }

}
