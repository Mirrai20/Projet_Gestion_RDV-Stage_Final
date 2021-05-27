package com.example.chuo_android;

public class Patient extends User {
    private String gender;
    public Patient(String cin, String nom, String prenom, String dateN, String telephone, String email, String password, String description, String gender) {
        super(cin, nom, prenom, dateN, telephone, email, password, "Patient");
        this.gender = gender;
    }


    //setters
    public void setGender(String gender){this.gender=gender;}
    //getters
    public String getGender() {
        return gender;
    }
}
