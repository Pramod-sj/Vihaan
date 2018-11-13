package com.vesvihaan;

import java.io.Serializable;

public class Registration implements Serializable{
    User user;
    boolean paid;
    String date;

    public Registration(){}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
