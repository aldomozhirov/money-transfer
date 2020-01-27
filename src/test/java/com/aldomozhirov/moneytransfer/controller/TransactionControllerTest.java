package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class TransactionControllerTest extends AbstractControllerTest {

    @Test
    public void testGetAllTransactions() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transaction/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction[] transactions = mapper.readValue(jsonString, Transaction[].class);
        assertEquals(0, transactions.length);
    }

}
