package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction tAfterPerforming = getMapper().readValue(jsonString, Transaction.class);
        Assert.assertEquals(Long.valueOf(4L), tAfterPerforming.getSourceAccountId());
        Assert.assertEquals(Long.valueOf(5L), tAfterPerforming.getTargetAccountId());
        Assert.assertEquals(Double.valueOf(10.0), tAfterPerforming.getAmount());
    }

    @Test
    public void testPerformTransactionWithNegativeAmount() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 5L, -10.0);
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testPerformTransactionWithIncorrectSourceAccountId() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(100L, 5L, 10.0);
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testPerformTransactionWithIncorrectTargetAccountId() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 100L, 10.0);
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testPerformTransactionWithInsufficientFundsOnSourceAccount() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(4L, 5L, 200.0);
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testPerformTransactionWithIncompatibleCurrencyCodes() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(5L, 6L, 10.0);
        String jsonInString = getMapper().writeValueAsString(transaction);
        HttpResponse response = postRequest("/transaction", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testRevertTransaction() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/transaction/6");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction tAfterRevert = getMapper().readValue(jsonString, Transaction.class);
        assertTransactionEquals(SAMPLE_TRANSACTIONS[5], tAfterRevert);
    }

    @Test
    public void testRevertTransactionWithInsufficientFundsOnTargetAccount() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/transaction/7");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testRevertTransactionWithNotExistingSourceId() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/transaction/8");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testRevertTransactionWithNotExistingTargetId() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/transaction/9");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testRevertTransactionByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/transaction/100");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetTransactionById() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/1");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction actualTransaction = getMapper().readValue(jsonString, Transaction.class);
        Transaction expectedTransaction = SAMPLE_TRANSACTIONS[0];
        assertTransactionEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void testGetTransactionByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/100");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAllTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetAllTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/100");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetOutcomeTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/100/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetIncomeTransactionsByAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/2/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingAccount() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/account/100/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAllTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetAllTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/100");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetOutcomeTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[2]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetOutcomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/100/outcome");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }


    @Test
    public void testGetIncomeTransactionsByUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/1/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] actualTransactions = getMapper().readValue(jsonString, Transaction[].class);
        Transaction[] expectedTransactions = {SAMPLE_TRANSACTIONS[0], SAMPLE_TRANSACTIONS[1], SAMPLE_TRANSACTIONS[3]};
        assertTransactionsEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetIncomeTransactionsByNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/user/100/income");
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAllTransactions() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/transaction/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] transactions = getMapper().readValue(jsonString, Transaction[].class);
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
