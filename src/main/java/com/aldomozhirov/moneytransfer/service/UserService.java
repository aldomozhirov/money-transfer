package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    @POST
    @Path("/create")
    public User createUser(User user) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(User account) {
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
