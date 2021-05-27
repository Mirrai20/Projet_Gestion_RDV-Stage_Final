package com.example.chuo_android;

public class RDV {
    private String dateRDV;
    private String specialite;
    private String nameMedecin;
    private int taille;
    private String Etat;
    private String nom;
    private String prenom;
    private String sex;



    public RDV() {
        this.dateRDV = null;
        this.specialite = null;
        this.nameMedecin = null;
        this.Etat=null;
        this.taille =0;
    }

    public RDV(String dateRDV,String specialite,String nameMedecin) {
        this.dateRDV = dateRDV;
        this.specialite = specialite;
        this.nameMedecin =nameMedecin;
    }
    public RDV(String dateRDV,String specialite,String nameMedecin,int taille) {
        this.dateRDV = dateRDV;
        this.specialite = specialite;
        this.nameMedecin =nameMedecin;
        this.taille =taille;

    }
    public RDV(String dateRDV,String specialite,String nameMedecin,String Etat,int taille) {
        this.dateRDV = dateRDV;
        this.specialite = specialite;
        this.nameMedecin =nameMedecin;
        this.taille =taille;
        this.Etat=Etat;
    }
    public RDV(int taille,String dateRDV,String nom,String prenom,String sex) {
        this.dateRDV = dateRDV;
        this.nom = nom;
        this.prenom =prenom;
        this.sex =sex;
        this.taille =taille;
    }
    //setters
    public void setDateRDV(String dateRDV){this.dateRDV=dateRDV;}
    public void setSpecialite(String specialite){this.specialite=specialite;}
    public void setNameMedecin(String nameMedecin){this.nameMedecin=nameMedecin;}
    public void setTaille(int taille){this.taille=taille;}
    public void setEtat(String Etat){this.Etat=Etat;}
    public void setNom(String nom){this.nom=nom;}
    public void setPrenom(String prenom){this.prenom=prenom;}
    public void setSex(String sex){this.sex=sex;}




    //getters
    public String getDateRDV() { return dateRDV; }
    public String getSpecialite() {
        return specialite;
    }
    public String getNameMedecin() {
        return nameMedecin;
    }
    public int getTaille() {
        return taille;
    }
    public String getEtat() {
        return Etat;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getSex() {
        return sex;
    }




}
