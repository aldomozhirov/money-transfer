package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class TransactionControllerTest extends AbstractControllerTest {

    @Test
    public void testPerformTransaction() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testPerformTransactionWithNegativeAmount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testPerformTransactionWithIncorrectSourceAccountId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testPerformTransactionWithIncorrectTargetAccountId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testPerformTransactionWithInsufficientFundsOnSourceAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionById() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionByIncorrectId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionsByAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionsByUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetOutcomeTransactionsByAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetOutcomeTransactionsByUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetIncomeTransactionsByAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetIncomeTransactionsByUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAllTransactions() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] transactions = mapper.readValue(jsonString, Transaction[].class);
        assertTrue(transactions.length > 0);
    }

}
