package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @POST
    @Path("/create")
    public User createUser(User user) throws RepositoryException {
        return UserService.getInstance().createUser(user);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        UserService.getInstance().deleteUser(id);
        return null;
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        return UserService.getInstance().getUser(id);
    }

    @GET
    @Path("/all")
    public List<User> getAllUsers() throws RepositoryException {
        return UserService.getInstance().getAllUsers();
    }

}
