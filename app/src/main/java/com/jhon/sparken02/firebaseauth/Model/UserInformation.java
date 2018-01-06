package com.jhon.sparken02.firebaseauth.Model;

/**
 * Created by sparken02 on 21/8/17.
 */

public class UserInformation {
    public String name;
    public String email;
    public String address;
    public String phone;
    public String gender;

    public UserInformation() {
    }

    public UserInformation(String name, String email, String address, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
    }
}
