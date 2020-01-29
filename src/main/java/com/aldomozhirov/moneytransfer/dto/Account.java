package com.aldomozhirov.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonProperty
    private Long id;

    @JsonProperty(required = true)
    private Long userId;

    @JsonProperty(required = true)
    private String currencyCode;

    @JsonProperty()
    private Double balance;

    public Account() {}

    public Account(Long userId, String currencyCode) {
        this.userId = userId;
        this.currencyCode = currencyCode;
    }

    public Account(Long userId, String currencyCode, Double balance) {
        this.userId = userId;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    public Account(Long id, Long userId, String currencyCode, Double balance) {
        this.id = id;
        this.userId = userId;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

}
