package com.example.assignment2copycopy;

public class Temperature {
    String temp, date, time;
    Account user;

    public Temperature() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Temperature(String temp, String date, String time, Account user) {
        this.temp = temp;
        this.date = date;
        this.user = user;
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
