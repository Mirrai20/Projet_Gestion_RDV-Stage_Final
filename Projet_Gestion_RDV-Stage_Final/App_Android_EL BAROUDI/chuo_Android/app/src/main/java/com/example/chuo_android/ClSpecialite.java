package com.example.chuo_android;

public class ClSpecialite {
    private String namSP;
    private String Medecin;
    private int taille;
    public ClSpecialite() {
        this.namSP = null;
        this.Medecin = null;
        this.taille =0;
    }
    public ClSpecialite(String namSP,String Medecin) {
        this.namSP = namSP;
        this.Medecin = Medecin;
        this.taille =0;
    }
    public ClSpecialite(String namSP,String Medecin, int taille) {
        this.namSP = namSP;
        this.Medecin = Medecin;
        this.taille =taille;
    }
    //setters
    public void setNamSP(String namSP){this.namSP=namSP;}
    public void setMedecin(String Medecin){this.Medecin=Medecin;}
    public void setTaille(int taille){this.taille=taille;}

    //getters
    public String getNamSP() { return namSP; }
    public String getMedecin() {
        return Medecin;
    }
    public int getTaille() {
        return taille;
    }

}
