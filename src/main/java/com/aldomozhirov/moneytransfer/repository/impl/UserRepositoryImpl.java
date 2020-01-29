package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.constant.ExceptionConstants;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> userMap;
    private long increment;

    public UserRepositoryImpl() {
        userMap = new HashMap<>();
        increment = 1L;
    }

    @Override
    public synchronized User add(User user) throws RepositoryException {
        if (user.getId() != null) {
            if (userMap.containsKey(user.getId())) {
                throw new RepositoryException(String.format(
                        ExceptionConstants.USER_ALREADY_EXISTS,
                        user.getId())
                );
            }
        } else {
            user.setId(increment());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public synchronized User remove(Long id) {
        return userMap.remove(id);
    }

    @Override
    public synchronized User update(Long id, User user) {
        if (!userMap.containsKey(id)) {
            return null;
        }
        userMap.put(id, user);
        return user;
    }

    @Override
    public boolean isExists(Long id) {
        return userMap.containsKey(id);
    }

    @Override
    public User getById(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

    private long increment() {
        while (userMap.containsKey(increment)) {
            increment++;
        }
        return increment;
    }

}
