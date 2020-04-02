package com.example.assignment2copycopy;


public class Account {
    String name, mentor_group, password, firebaseID, studentID;

    public Account() {
    }

    public Account(String name, String mentor_group, String password, String firebaseID, String studentID) {
        this.name = name;
        this.mentor_group = mentor_group;
        this.password = password;
        this.firebaseID = firebaseID;
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMentor_group() {
        return mentor_group;
    }

    public void setMentor_group(String mentor_group) {
        this.mentor_group = mentor_group;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
