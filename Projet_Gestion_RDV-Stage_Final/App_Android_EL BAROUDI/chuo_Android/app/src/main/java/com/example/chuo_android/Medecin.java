package com.example.chuo_android;

public class Medecin extends User {
    private String specialite,numedecin;
    private static int taille=0;

    public Medecin(String cin, String nom, String prenom, String dateN, String telephone, String email, String password, String description, String specialite, String numedecin) {
        super(cin, nom, prenom, dateN, telephone, email, password,"Medecin");
        this.specialite = specialite;
        this.numedecin = numedecin;
        this.taille=0;
    }
    public Medecin(String nom,String prenom,String numedecin,int taille) {
        super(null, nom, prenom, null, null, null, null,"Medecin");
        this.specialite = null;
        this.numedecin = numedecin;
        this.taille=taille;

    }
    /*public Medecin(String nom,String prenom) {
        super(null, nom, prenom, null, null, null, null,"Medecin");
        this.specialite = null;
        this.numedecin = null;
        this.taille=0;

    }*/
    //setters
    public void setSpecialite(String specialite){this.specialite=specialite;}
    public void setNumedecin(String numedecin){this.numedecin=numedecin;}
    public void setTaille(int taille){this.taille=taille;}

    //getters
    public String getSpecialite() {
        return specialite;
    }
    public String getNumedecin() {
        return numedecin;
    }
    public int getTaille() {
        return taille;
    }
}
