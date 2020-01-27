package com.aldomozhirov.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonIgnore
    private Long id;

    @JsonProperty(required = true)
    private Long userId;

    @JsonProperty()
    private Double balance;

    public Account() {}

    public Account(Long userId) {
        this.userId = userId;
    }

    public Account(Long userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

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
