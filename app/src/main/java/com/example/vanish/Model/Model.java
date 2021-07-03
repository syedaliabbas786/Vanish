package com.example.vanish.Model;

public class Model {
    String Id,Username,Password,Email,PhoneNo,FullName;

    public Model() {
    }

    public Model(String Id, String username, String password, String email, String phoneNo, String fullName) {
        this.Id = Id;
        Username = username;
        Password = password;
        Email = email;
        PhoneNo = phoneNo;
        FullName = fullName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
