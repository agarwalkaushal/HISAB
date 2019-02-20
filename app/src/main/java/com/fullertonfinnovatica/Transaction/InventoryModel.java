package com.fullertonfinnovatica.Transaction;

public class InventoryModel {

    String fromname, toname, date, transmode, creditamount, debitamount;

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransmode() {
        return transmode;
    }

    public void setTransmode(String transmode) {
        this.transmode = transmode;
    }

    public String getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(String creditamount) {
        this.creditamount = creditamount;
    }

    public String getDebitamount() {
        return debitamount;
    }

    public void setDebitamount(String debitamount) {
        this.debitamount = debitamount;
    }
}
