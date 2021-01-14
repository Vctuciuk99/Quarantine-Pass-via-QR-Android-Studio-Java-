package com.example.q_passaccount;

import java.util.Queue;

public class UserData {
    String Email;
    String First_name;
    String Middle_name;
    String Last_name;
    String Contact_Info;
    String Province;
    String Municipality;
    String Barangay;
    String Street;
    String Password;
    String QRCode_Url;


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getMiddle_name() {
        return Middle_name;
    }

    public void setMiddle_name(String middle_name) {
        Middle_name = middle_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getContact_Info() {
        return Contact_Info;
    }

    public void setContact_Info(String contact_Info) {
        Contact_Info = contact_Info;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getMunicipality() {
        return Municipality;
    }

    public void setMunicipality(String municipality) {
        Municipality = municipality;
    }

    public String getBarangay() {
        return Barangay;
    }

    public void setBarangay(String barangay) {
        Barangay = barangay;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getQRCode_Url() {
        return QRCode_Url;
    }

    public void setQRCode_Url(String QRCode_Url) {
        this.QRCode_Url = QRCode_Url;
    }

    public UserData (String Email, String First_name, String Middle_name,
                     String Last_name, String Contact_Info, String Province,
                     String Municipality, String Barangay, String Street,
                     String Password , String QRCode_Url){
        this.Email = Email;
        this.First_name = First_name;
        this.Middle_name = Middle_name;
        this.Last_name = Last_name;
        this.Contact_Info = Contact_Info;
        this.Province = Province;
        this.Municipality = Municipality;
        this.Barangay = Barangay;
        this.Street = Street;
        this.Password = Password;
        this.QRCode_Url = QRCode_Url;




    }
}
