package com.aldomozhirov.moneytransfer.constant;

public class ExceptionConstants {

    public static final String CANNOT_FIND_ACCOUNT_BY_ID = "Cannot find account by id=%d";
    public static final String UNABLE_TO_CREATE_ACCOUNT_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to create account for user with id=%d cause such user does not exists";
    public static final String UNABLE_TO_DELETE_ACCOUNT_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS = "Unable to delete account with id=%d cause such account does not exists";
    public static final String UNABLE_TO_GET_ACCOUNTS_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to get accounts of user with id=%d cause such user does not exists";
    public static final String UNABLE_TO_GET_BALANCE_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS = "Unable to get current balance on account with id=%d cause such account does not exists";

    public static final String CANNOT_FIND_USER_BY_ID = "Cannot find account by id=%d";
    public static final String UNABLE_TO_DELETE_USER_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to delete user with id=%d cause such user does not exists";
    public static final String UNABLE_TO_DELETE_USER_CAUSE_IT_HAVE_RELATED_ACCOUNTS = "Unable to delete user with id=%d cause there are related accounts with this user";

    public static final String CANNOT_FIND_TRANSACTION_BY_ID = "Cannot find transaction with id=%d";
    public static final String CANNOT_FIND_SOURCE_ACCOUNT = "Cannot find source account with id=%d specified in transaction";
    public static final String CANNOT_FIND_TARGET_ACCOUNT = "Cannot find target account with id=%d specified in transaction";
    public static final String TRANSACTION_AMOUNT_SHOULD_BE_POSITIVE = "Transaction amount should be grater than 0";
    public static final String NOT_ENOUGH_MONEY = "Not enough money to perform the transaction on the source account with id=%d";
    public static final String UNABLE_TO_GET_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS = "Unable to get transactions of account with id=%d cause such account does not exists";
    public static final String UNABLE_TO_GET_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to get transactions of user with id=%d cause such user does not exists";
    public static final String UNABLE_TO_GET_OUTCOME_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS = "Unable to get outcome transactions of account with id=%d cause such account does not exists";
    public static final String UNABLE_TO_GET_OUTCOME_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to get outcome transactions of user with id=%d cause such user does not exists";
    public static final String UNABLE_TO_GET_INCOME_TRANSACTIONS_CAUSE_SUCH_ACCOUNT_DOES_NOT_EXISTS = "Unable to get income transactions of account with id=%d cause such account does not exists";
    public static final String UNABLE_TO_GET_INCOME_TRANSACTIONS_CAUSE_SUCH_USER_DOES_NOT_EXISTS = "Unable to get income transactions of user with id=%d cause such user does not exists";

    public static final String USER_ALREADY_EXISTS = "User with id=%d already exists";
    public static final String ACCOUNT_ALREADY_EXISTS = "Account with id=%d already exists";
    public static final String TRANSACTION_ALREADY_EXISTS = "Transaction with id=%d already exists";

}