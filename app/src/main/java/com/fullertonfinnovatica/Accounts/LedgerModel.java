package com.fullertonfinnovatica.Accounts;

public class LedgerModel {

    String account_name, balance_type, balance_amt;
    String[] debit_name, debit_amt, credit_name, credit_amt;
    int debitSize, creditSize;

    public int getDebitSize() {
        return debitSize;
    }

    public void setDebitSize(int debitSize) {
        this.debitSize = debitSize;
    }

    public int getCreditSize() {
        return creditSize;
    }

    public void setCreditSize(int creditSize) {
        this.creditSize = creditSize;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBalance_type() {
        return balance_type;
    }

    public void setBalance_type(String balance_type) {
        this.balance_type = balance_type;
    }

    public String getBalance_amt() {
        return balance_amt;
    }

    public void setBalance_amt(String balance_amt) {
        this.balance_amt = balance_amt;
    }

    public String[] getDebit_name() {
        return debit_name;
    }

    public void setDebit_name(String[] debit_name) {
        this.debit_name = debit_name;
    }

    public String[] getDebit_amt() {
        return debit_amt;
    }

    public void setDebit_amt(String[] debit_amt) {
        this.debit_amt = debit_amt;
    }

    public String[] getCredit_name() {
        return credit_name;
    }

    public void setCredit_name(String[] credit_name) {
        this.credit_name = credit_name;
    }

    public String[] getCredit_amt() {
        return credit_amt;
    }

    public void setCredit_amt(String[] credit_amt) {
        this.credit_amt = credit_amt;
    }
}
