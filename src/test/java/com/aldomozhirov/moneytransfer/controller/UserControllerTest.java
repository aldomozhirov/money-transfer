package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateUser() throws IOException, URISyntaxException {
        User user = new User("Robert", "Lawson");
        String jsonInString = mapper.writeValueAsString(user);
        HttpResponse response = postRequest("/user/create", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User uAfterCreation = mapper.readValue(jsonString, User.class);
        assertEquals("Robert", uAfterCreation.getFirstName());
        assertEquals("Lawson", uAfterCreation.getLastName());
    }

    @Test
    public void testDeleteUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/4");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testDeleteNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/5");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(400, statusCode);
    }

    @Test
    public void testGetUserById() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/1");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User user = mapper.readValue(jsonString, User.class);
        //TODO
    }

    @Test
    public void testGetUserByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/5");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(400, statusCode);
    }

    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = mapper.readValue(jsonString, User[].class);
        assertTrue(users.length > 0);
    }

}
