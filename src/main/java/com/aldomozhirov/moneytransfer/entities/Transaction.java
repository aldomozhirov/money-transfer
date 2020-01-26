package com.aldomozhirov.moneytransfer.entities;

public class Transaction {

    private Long id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;

    public Transaction(Long id, Long sourceAccountId, Long targetAccountId, Double amount) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public Double getAmount() {
        return amount;
    }

}
