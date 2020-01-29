package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RelationException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import org.junit.Test;

import java.util.List;

public class UserServiceTest extends AbstractServiceTest {

    UserService userService = UserService.getInstance();

    @Test
    public void testCreateUser() throws RepositoryException {
        User user = new User();
        userService.createUser(user);
    }

    @Test
    public void testDeleteUser() throws RepositoryException, NoSuchIdException, RelationException {
        User deletedUser = userService.deleteUser(5L);
    }

    @Test
    public void testUpdateUser() throws RepositoryException, NoSuchIdException, IncorrectInputDataException {
        User user = new User();
        userService.updateUser(4L, user);
    }

    @Test
    public void testGetUser() throws NoSuchIdException, RepositoryException {
        User user = userService.getUser(1L);
    }

    @Test
    public void testGetAllUsers() throws RepositoryException {
        List<User> user = userService.getAllUsers();
    }

}
