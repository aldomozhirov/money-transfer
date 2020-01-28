package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Account;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class AccountControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateAccount() throws IOException, URISyntaxException {
        Account account = new Account(1L, 0.0);
        String jsonInString = mapper.writeValueAsString(account);
        HttpResponse response = postRequest("/account/create", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account aAfterCreation = mapper.readValue(jsonString, Account.class);
        Assert.assertEquals(Long.valueOf(1L), aAfterCreation.getUserId());
        Assert.assertEquals(Double.valueOf(0.0), aAfterCreation.getBalance());
    }

    @Test
    public void testCreateAccountForNotExistingUser() throws IOException, URISyntaxException {
        Account account = new Account(5L, 0.0);
        String jsonInString = mapper.writeValueAsString(account);
        HttpResponse response = postRequest("/account/create", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testDeleteAccount() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/account/5");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_NO_CONTENT, statusCode);
    }

    @Test
    public void testDeleteNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/account/10");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAccountById() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/1");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account actualAccount = mapper.readValue(jsonString, Account.class);
        Account expectedAccount = SAMPLE_ACCOUNTS[0];
        assertAccountEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testGetAccountByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/10");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAccountsByUserId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/user/1");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account[] actualAccounts = mapper.readValue(jsonString, Account[].class);
        Account[] expectedAccounts = {SAMPLE_ACCOUNTS[0], SAMPLE_ACCOUNTS[1]};
        assertAccountsEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void testGetAccountsByIncorrectUserId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/user/10");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAccountBalance() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/1/balance");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        Double actualBalance = Double.valueOf(EntityUtils.toString(response.getEntity()));
        assertEquals(SAMPLE_ACCOUNTS[0].getBalance(), actualBalance);
    }

    @Test
    public void testGetNotExistingAccountBalance() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/10/balance");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAllAccounts() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/account/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account[] accounts = mapper.readValue(jsonString, Account[].class);
        Assert.assertTrue(accounts.length > 0);
    }

    private void assertAccountEquals(Account expected, Account actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }

    private void assertAccountsEquals(Account[] expected, Account[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertAccountEquals(expected[i], actual[i]);
        }
    }

}
