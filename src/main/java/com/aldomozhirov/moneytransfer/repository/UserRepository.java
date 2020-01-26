package com.aldomozhirov.moneytransfer.repository;

import com.aldomozhirov.moneytransfer.dto.User;

import java.util.List;

public interface UserRepository {

    Long add(User user);

    void remove(Long id);

    User get(Long id);

    List<User> getAll();

}