package com.example.chuo_android;

public class Recl {
    private String dateRecl;
    private String contenu;
    private String statut;
    private int taille;

    public Recl() {
        this.dateRecl = null;
        this.contenu = null;
        this.statut = null;
        this.taille =0;
    }

    public Recl(String dateRecl,String contenu,String statut) {
        this.dateRecl = dateRecl;
        this.contenu = contenu;
        this.statut =statut;
    }
    public Recl(String dateRecl,String contenu,String statut,int taille) {
        this.dateRecl = dateRecl;
        this.contenu = contenu;
        this.statut =statut;
        this.taille =taille;

    }
    //setters
    public void setDateRecl(String dateRDV){this.dateRecl=dateRDV;}
    public void setContenu(String contenu){this.contenu=contenu;}
    public void setStatut(String statut){this.statut=statut;}
    public void setTaille(int taille){this.taille=taille;}

    //getters
    public String getDateRecl() { return dateRecl; }
    public String getContenu() {
        return contenu;
    }
    public String getStatut() {
        return statut;
    }
    public int getTaille() {
        return taille;
    }

}
