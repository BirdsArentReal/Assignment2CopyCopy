package com.example.assignment2copycopy;

public class Travel {
    String fromDate, toDate, destination;
    Account user;

    public Travel(String fromDate, String toDate, String destination, Account user) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.destination = destination;
        this.user = user;
    }

    public Travel() {
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
