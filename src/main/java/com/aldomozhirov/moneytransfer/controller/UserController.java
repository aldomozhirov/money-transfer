package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @POST
    @Path("/create")
    public User createUser(User user) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/all")
    public List<User> getAllUsers() {
        return null;
    }

}
