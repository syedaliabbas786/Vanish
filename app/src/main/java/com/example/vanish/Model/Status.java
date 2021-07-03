package com.example.vanish.Model;

public class Status {
    int image;
    String user,time;

    public Status() {
    }

    public Status(int image, String user, String time) {
        this.image = image;
        this.user = user;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
