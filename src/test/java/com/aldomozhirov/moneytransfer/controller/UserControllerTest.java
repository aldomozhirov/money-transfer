package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateUser() throws IOException, URISyntaxException {
        User user = new User("Robert", "Lawson");
        String jsonInString = getMapper().writeValueAsString(user);
        HttpResponse response = postRequest("/user/create", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User uAfterCreation = getMapper().readValue(jsonString, User.class);
        assertEquals("Robert", uAfterCreation.getFirstName());
        assertEquals("Lawson", uAfterCreation.getLastName());
    }

    @Test
    public void testDeleteUser() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/user/4");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User uDeleted = getMapper().readValue(jsonString, User.class);
        assertUserEquals(SAMPLE_USERS[3], uDeleted);
    }

    @Test
    public void testDeleteNotExistingUser() throws IOException, URISyntaxException {
        HttpResponse response = deleteRequest("/user/10");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testUpdateUser() throws IOException, URISyntaxException {
        User user = new User("Peter", "Thomson");
        String jsonInString = getMapper().writeValueAsString(user);
        HttpResponse response = putRequest("/user/3", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User uAfterUpdate = getMapper().readValue(jsonString, User.class);
        assertEquals("Peter", uAfterUpdate.getFirstName());
        assertEquals("Thomson", uAfterUpdate.getLastName());
    }

    @Test
    public void testUpdateNotExistingUser() throws IOException, URISyntaxException {
        User user = new User("Peter", "Thomson");
        String jsonInString = getMapper().writeValueAsString(user);
        HttpResponse response = putRequest("/user/10", jsonInString);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetUserById() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/1");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User actualUser = getMapper().readValue(jsonString, User.class);
        User expectedUser = SAMPLE_USERS[0];
        assertUserEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUserByIncorrectId() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/10");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        HttpResponse response = getRequest("/user/all");
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = getMapper().readValue(jsonString, User[].class);
        assertTrue(users.length > 0);
    }

}
