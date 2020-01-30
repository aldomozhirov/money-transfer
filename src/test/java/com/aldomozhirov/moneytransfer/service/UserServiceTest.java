package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RelationException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.repository.UserRepository;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserServiceTest extends AbstractServiceTest {

    private UserService userService = UserService.getInstance();
    private UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();

    @Test
    public void testCreateUser() throws RepositoryException {
        User userToCreate = new User("Robert", "Lawson");
        User createdUser = userService.createUser(userToCreate);
        assertEquals(userToCreate.getFirstName(), createdUser.getFirstName());
        assertEquals(userToCreate.getLastName(), createdUser.getLastName());
        User userInRepository = userRepository.getById(createdUser.getId());
        assertUserEquals(createdUser, userInRepository);
    }

    @Test
    public void testDeleteUser() throws RepositoryException, NoSuchIdException, RelationException {
        User deletedUser = userService.deleteUser(4L);
        assertUserEquals(SAMPLE_USERS[3], deletedUser);
        assertFalse(userRepository.isExists(deletedUser.getId()));
    }

    @Test
    public void testUpdateUser() throws RepositoryException, NoSuchIdException, IncorrectInputDataException {
        Long userIdToUpdate = 3L;
        User userToUpdate = new User("Peter", "Thomson");
        User updatedUser = userService.updateUser(userIdToUpdate, userToUpdate);
        assertEquals(userToUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userToUpdate.getLastName(), updatedUser.getLastName());
        User userInRepository = userRepository.getById(userIdToUpdate);
        assertUserEquals(updatedUser, userInRepository);
    }

    @Test
    public void testGetAllUsers() throws RepositoryException, NoSuchIdException, IncorrectInputDataException {
        List<User> receivedUsersList = userService.getAllUsers();
        List<User> usersListInRepository = userRepository.getAll();
        assertUsersEquals(usersListInRepository, receivedUsersList);
    }

}
