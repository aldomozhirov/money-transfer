package com.aldomozhirov.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

    @JsonProperty
    private Long id;

    @JsonProperty(required = true)
    private Long sourceAccountId;

    @JsonProperty(required = true)
    private Long targetAccountId;

    @JsonProperty(required = true)
    private Double amount;

    public Transaction(Long id, Long sourceAccountId, Long targetAccountId, Double amount) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public Transaction(Long sourceAccountId, Long targetAccountId, Double amount) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public void setAmount(Double amount) {
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
