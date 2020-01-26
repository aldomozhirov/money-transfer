package com.aldomozhirov.moneytransfer.dto;

public class Account {

    private Long id;
    private Long userId;
    private Double balance;

    public Account(Long id, Long userId, Double balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getBalance() {
        return balance;
    }

}
