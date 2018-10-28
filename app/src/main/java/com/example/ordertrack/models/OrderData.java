package com.example.ordertrack.models;

public class OrderData {
    int status;
    String label,date;

    public OrderData(int status, String label, String date) {
        this.status = status;
        this.label = label;
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {

        return status;
    }

    public String getLabel() {
        return label;
    }

    public String getDate() {
        return date;
    }
}
