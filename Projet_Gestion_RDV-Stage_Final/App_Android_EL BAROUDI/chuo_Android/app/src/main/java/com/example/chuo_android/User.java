package com.example.chuo_android;

public class User {

    private String cin, nom, prenom, dateN, telephone, email, password, description;

    public User(String cin, String nom, String prenom, String dateN, String telephone, String email, String password, String description) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    //setters
    public void setCin(String cin){this.cin=cin;}
    public void setNom(String nom){this.nom=nom;}
    public void setPrenom(String prenom){this.prenom=prenom;}
    public void setDateN(String dateN){this.dateN=dateN;}
    public void setTelephone(String telephone){this.telephone=telephone;}
    public void setEmail(String email){this.email=email;}
    public void setPassword(String password){this.password=password;}
    public void setDescription(String description){this.description=description;}

    //getters
    public String getCin() {
        return cin;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getDateN() {
        return dateN;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {  return password; }
    public String getDescription() {
        return description;
    }
}