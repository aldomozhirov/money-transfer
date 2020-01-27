package com.aldomozhirov.moneytransfer.repository.impl;

import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> userMap;
    private Long increment;

    public UserRepositoryImpl() {
        userMap = new HashMap<>();
    }

    @Override
    public User add(User user) {
        user.setId(increment++);
        return userMap.put(user.getId(), user);
    }

    @Override
    public boolean remove(Long id) {
        return userMap.remove(id) != null;
    }

    @Override
    public User update(Long id, User user) {
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
    public User get(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

}
