package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.constant.ExceptionConstants;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RelationException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.repository.UserRepository;

import java.util.List;

public class UserService {

    private static UserService instance;

    private RepositoryFactory repositoryFactory;

    private UserService() {
        repositoryFactory = RepositoryFactory.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User createUser(User user) throws RepositoryException {
        return repositoryFactory.getUserRepository().add(user);
    }

    public User deleteUser(Long userId) throws NoSuchIdException, RepositoryException, RelationException {
        UserRepository userRepository = repositoryFactory.getUserRepository();
        if (!userRepository.isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_DELETE_USER_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
                    userId)
            );
        }
        if(!repositoryFactory.getAccountRepository().getByUserId(userId).isEmpty()) {
            throw new RelationException(String.format(
                    ExceptionConstants.UNABLE_TO_DELETE_USER_CAUSE_IT_HAVE_RELATED_ACCOUNTS,
                    userId
            ));
        }
        return userRepository.remove(userId);
    }

    public User updateUser(Long userId, User user) throws RepositoryException, NoSuchIdException, IncorrectInputDataException {
        UserRepository userRepository = repositoryFactory.getUserRepository();
        if (!userRepository.isExists(userId)) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.UNABLE_TO_UPDATE_USER_CAUSE_SUCH_USER_DOES_NOT_EXISTS,
                    userId)
            );
        }
        if(user.getId() != null && !userId.equals(user.getId())) {
            throw new IncorrectInputDataException(String.format(
                   ExceptionConstants.INCONSISTENT_DATA_INPUT,
                   userId)
            );
        }
        return repositoryFactory.getUserRepository().update(userId, user);
    }

    public User getUser(Long userId) throws NoSuchIdException, RepositoryException {
        User user = repositoryFactory.getUserRepository().getById(userId);
        if (user == null) {
            throw new NoSuchIdException(String.format(
                    ExceptionConstants.CANNOT_FIND_USER_BY_ID,
                    userId)
            );
        }
        return user;
    }

    public List<User> getAllUsers() throws RepositoryException {
        return repositoryFactory.getUserRepository().getAll();
    }

}
