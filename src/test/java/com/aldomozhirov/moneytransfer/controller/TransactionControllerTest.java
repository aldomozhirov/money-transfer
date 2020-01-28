package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class TransactionControllerTest extends AbstractControllerTest {

    @Test
    public void testPerformTransaction() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 5L, 10.0);
        String jsonInString = mapper.writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction tAfterPerforming = mapper.readValue(jsonString, Transaction.class);
        Assert.assertEquals(Long.valueOf(4L), tAfterPerforming.getSourceAccountId());
        Assert.assertEquals(Long.valueOf(5L), tAfterPerforming.getTargetAccountId());
        Assert.assertEquals(Double.valueOf(10.0), tAfterPerforming.getAmount());
    }

    @Test
    public void testPerformTransactionWithNegativeAmount() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 5L, -10.0);
        String jsonInString = mapper.writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testPerformTransactionWithIncorrectSourceAccountId() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(10L, 5L, 10.0);
        String jsonInString = mapper.writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testPerformTransactionWithIncorrectTargetAccountId() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 10L, 10.0);
        String jsonInString = mapper.writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testPerformTransactionWithInsufficientFundsOnSourceAccount() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 5L, 200.0);
        String jsonInString = mapper.writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetTransactionById() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/1");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction actualTransaction = mapper.readValue(jsonString, Transaction.class);
        Transaction expectedTransaction = SAMPLE_TRANSACTIONS[0];
        assertTransactionEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void testGetTransactionByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/10");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetAllTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetAllTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/10");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetOutcomeTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/10/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetIncomeTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/10/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetAllTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetAllTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/10");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetOutcomeTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/10/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }


    @Test
    public void testGetIncomeTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = mapper.readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/10/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testGetAllTransactions() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] transactions = mapper.readValue(jsonString, Transaction[].class);
        Assert.assertTrue(transactions.length > 0);
    }

    private void assertTransactionEquals(Transaction expected, Transaction actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getSourceAccountId(), actual.getSourceAccountId());
        Assert.assertEquals(expected.getTargetAccountId(), actual.getTargetAccountId());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
    }

    private void assertTransactionsEquals(Transaction[] expected, Transaction[] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertTransactionEquals(expected[i], actual[i]);
        }
    }

}
