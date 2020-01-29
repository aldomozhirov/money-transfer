package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RelationException;
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

    @PUT
    @Path("/{id}")
    public User updateUser(@PathParam("id") long id, User user) throws RepositoryException, NoSuchIdException, IncorrectInputDataException {
        return UserService.getInstance().updateUser(id, user);
    }

    @DELETE
    @Path("/{id}")
    public User deleteUser(@PathParam("id") long id) throws RepositoryException, NoSuchIdException, RelationException {
        return UserService.getInstance().deleteUser(id);
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
