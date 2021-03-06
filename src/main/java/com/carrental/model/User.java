package com.carrental.model;

import java.sql.Date;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private long passportNumber;
    private Date dateOfIssue;


    public User(String firstName, String lastName, String middleName, Date dateOfBirth, long passportNumber, Date dateOfIssue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.dateOfIssue = dateOfIssue;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public long getPassportNumber() {
        return this.passportNumber;
    }

    public Date getDateOfIssue() {
        return this.dateOfIssue;
    }

    @Override
    public String toString() {
        return "User ID: " + this.getUserId() + "\t\tFirst name: " + this.getFirstName() + "\tLast Name: " + this.getLastName() + "\tMiddle name: " + this.getMiddleName() + "\tDate of birth: " + this.getDateOfBirth() + "\tPassport Number: " + this.getPassportNumber() + "\tDate of issue: " + this.getDateOfIssue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) obj;
        return ( firstName != null && firstName.equals(user.firstName) && lastName != null && lastName.equals(user.lastName) && middleName != null && middleName.equals(user.middleName) && dateOfBirth != null && dateOfBirth.equals(user.dateOfBirth) && passportNumber == user.passportNumber && dateOfIssue != null && dateOfIssue.equals(user.dateOfIssue));
    }

    @Override
    public int hashCode(){
        int result = (firstName == null) ? 0 : firstName.hashCode();
        result+= (lastName == null) ? 0 : lastName.hashCode();
        result+= (middleName == null) ? 0 : middleName.hashCode();
        result+= (dateOfBirth == null) ? 0 : dateOfBirth.hashCode();
        result+= passportNumber;
        result+= (dateOfIssue == null) ? 0 : dateOfIssue.hashCode();
        return (result%1000);
    }

}
