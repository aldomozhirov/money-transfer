package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.UserAccountsRepository;
import com.aldomozhirov.moneytransfer.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private UserAccountsRepository userAccountsRepository;

    public UserService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            UserAccountsRepository userAccountsRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.userAccountsRepository = userAccountsRepository;
    }

    public User createUser(User user) {
        long id = userRepository.add(user);
        user.setId(id);
        return user;
    }

    public boolean deleteUser(Long userId) {
        return userRepository.remove(userId);
    }

    public User getUser(Long userId) {
        return userRepository.get(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public List<Account> getUserAccounts(Long userId) {
        return userAccountsRepository
                .getAll(userId).stream()
                .map((accountId) -> accountRepository.get(accountId))
                .collect(Collectors.toList());
    }

    public void assignAccountToUser(Long userId, Long accountId) {
        userAccountsRepository.add(userId, accountId);
    }

}
