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

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = mapper.readValue(jsonString, User[].class);
        assertEquals(0, users.length);
    }

}
