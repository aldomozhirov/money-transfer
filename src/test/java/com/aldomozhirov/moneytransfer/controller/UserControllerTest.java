package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testDeleteUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testDeleteNotExistingUser() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetUserById() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetUserByIncorrectId() throws IOException, URISyntaxException {
        //TODO
    }

    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = mapper.readValue(jsonString, User[].class);
        assertNotEquals(0, users.length);
    }

}
