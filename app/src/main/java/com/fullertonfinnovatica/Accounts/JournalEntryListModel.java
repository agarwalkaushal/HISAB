package com.fullertonfinnovatica.Accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class
JournalEntryListModel {

    @SerializedName("journalData")
    @Expose
    private List<JournalEntryModel> contacts = null;

    public List<JournalEntryModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<JournalEntryModel> contacts) {
        this.contacts = contacts;
    }
}
