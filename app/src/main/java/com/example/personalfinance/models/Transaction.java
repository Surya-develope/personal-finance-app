package com.example.personalfinance.models;

public class Transaction {
    public String id;
    public String type;
    public String note;
    public int amount;
    public long timestamp;

    public Transaction() { }

    public Transaction(String id, String type, int amount, String note, long timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }
}
