package com.example.chuo_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "chuappandroidsharedpref";
    private static final String KEY_CIN = "keyCIN";
    private static final String KEY_NOM = "keynom";
    private static final String KEY_prenom = "keyprenom";
    private static final String KEY_dateN = "keydateN";
    private static final String KEY_tele = "keytelephone";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_password = "keypassword";
    private static final String KEY_description = "keydescription";
    private static final String KEY_gender = "keygender";
    //class Specialite
    private static final String KEY_specialite = "keyspecialite";
    private static final String KEY_numedecin= "keynumedecin";
    private static final String KEY_length= "keylength";
    //class RDV
    private static final String KEY_DateoldRDV= "keyoldrdv";
    private static final String KEY_NameMedecin= "keynameMedecin";
    private static final String KEY_Etat= "keyEtat";

    //class Recl
    private static final String KEY_DateRecl= "keyrecl";
    private static final String KEY_Contenu= "keyContenu";
    private static final String KEY_Statut= "keyStatut";




    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CIN, user.getCin());
        editor.putString(KEY_NOM, user.getNom());
        editor.putString(KEY_prenom, user.getPrenom());
        editor.putString(KEY_dateN, user.getDateN());
        editor.putString(KEY_tele, user.getTelephone());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_password, user.getPassword());
        editor.putString(KEY_description, user.getDescription());

        editor.apply();
    }
    //this method will store the user data in shared preferences
    public void AllSP(ClSpecialite sp[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putString(KEY_specialite+i, sp[i].getNamSP());
            editor.putString(KEY_numedecin+i, sp[i].getMedecin());
            editor.putInt(KEY_length+i, sp[i].getTaille());
        }


        editor.apply();
    }
    //this method will store the olds Rdv data in shared preferences
    public void OLDRDV(RDV oldrdv[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putString(KEY_DateoldRDV+i, oldrdv[i].getDateRDV());
            editor.putString(KEY_specialite+i, oldrdv[i].getSpecialite());
            editor.putString(KEY_NameMedecin+i, oldrdv[i].getNameMedecin());
            editor.putInt(KEY_length+i, oldrdv[i].getTaille());
        }

        editor.apply();
    }
    //this method will store the Rdv of all patient data in shared preferences
    public void RDVAllPatient(RDV RdvPatient[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putInt(KEY_length+i, RdvPatient[i].getTaille());
            editor.putString(KEY_DateoldRDV+i, RdvPatient[i].getDateRDV());
            editor.putString(KEY_NOM+i, RdvPatient[i].getNom());
            editor.putString(KEY_prenom+i, RdvPatient[i].getPrenom());
            editor.putString(KEY_gender+i, RdvPatient[i].getSex());

        }

        editor.apply();
    }
    //this method will store the olds Rdv data in shared preferences
    public void ALLRECL(Recl recl[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putString(KEY_DateRecl+i, recl[i].getDateRecl());
            editor.putString(KEY_Contenu+i, recl[i].getContenu());
            editor.putString(KEY_Statut+i, recl[i].getStatut());
            editor.putInt(KEY_length+i, recl[i].getTaille());
        }

        editor.apply();
    }
    public void AllMedecin(Medecin md[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putString(KEY_NOM+i, md[i].getNom());
            editor.putString(KEY_prenom+i, md[i].getPrenom());
            editor.putString(KEY_numedecin+i, md[i].getNumedecin());
            editor.putInt(KEY_length+i, md[i].getTaille());
        }

        editor.apply();
    }
    //this method will store the new Rdv data in shared preferences
    public void ConsultationRDV(RDV Consurdv[],int t) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0;i<t;i++){
            editor.putString(KEY_DateoldRDV+i, Consurdv[i].getDateRDV());
            editor.putString(KEY_specialite+i, Consurdv[i].getSpecialite());
            editor.putString(KEY_NameMedecin+i, Consurdv[i].getNameMedecin());
            editor.putString(KEY_Etat+i, Consurdv[i].getEtat());
            editor.putInt(KEY_length+i, Consurdv[i].getTaille());
        }

        editor.apply();
    }
    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_CIN, null),
                sharedPreferences.getString(KEY_NOM, null),
                sharedPreferences.getString(KEY_prenom, null),
                sharedPreferences.getString(KEY_dateN, null),
                sharedPreferences.getString(KEY_tele, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_password, null),
                sharedPreferences.getString(KEY_description, null)
        );
    }
    //this method will give you all specialites
    public ClSpecialite[] getALLsp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ClSpecialite[] sp = new ClSpecialite[10];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            sp[i] = new ClSpecialite(sharedPreferences.getString(KEY_specialite+i, null),sharedPreferences.getString(KEY_numedecin+i, null),sharedPreferences.getInt(KEY_length+i, 0));
        }
        return sp;
    }
    //this method will give you OLD RDV
    public RDV[] getOLDRDV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        RDV[] oldRdv = new RDV[50];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            oldRdv[i] = new RDV(sharedPreferences.getString(KEY_DateoldRDV+i, null),sharedPreferences.getString(KEY_specialite+i, null),sharedPreferences.getString(KEY_NameMedecin+i, null),sharedPreferences.getInt(KEY_length+i, 0));
        }
        return oldRdv;
    }
    //this method will give you ALL RECL
    public Recl[] getAllRecl() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Recl[] recl = new Recl[50];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            recl[i] = new Recl(sharedPreferences.getString(KEY_DateRecl+i, null),sharedPreferences.getString(KEY_Contenu+i, null),sharedPreferences.getString(KEY_Statut+i, null),sharedPreferences.getInt(KEY_length+i, 0));
        }
        return recl;
    }
    //this method will give you ALL medecin
    public Medecin[] getAllMedecin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Medecin[] md = new Medecin[50];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            md[i] = new Medecin(sharedPreferences.getString(KEY_NOM+i, null),sharedPreferences.getString(KEY_prenom+i, null),sharedPreferences.getString(KEY_numedecin+i, null),sharedPreferences.getInt(KEY_length+i, 0));
        }
        return md;
    }
    //this method will give you ALL new RDV
    public RDV[] getConsuRDV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        RDV[] Consurdv = new RDV[50];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            Consurdv[i] = new RDV(sharedPreferences.getString(KEY_DateoldRDV+i, null),sharedPreferences.getString(KEY_specialite+i, null),sharedPreferences.getString(KEY_NameMedecin+i, null),sharedPreferences.getString(KEY_Etat+i, null),sharedPreferences.getInt(KEY_length+i, 0));
        }
        return Consurdv;
    }
    //this method will give you ALL RDV
    public RDV[] getRDVAllPatient() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        RDV[] RdvPatient = new RDV[50];
        for (int i=0;i<sharedPreferences.getInt(KEY_length+0, 0);i++){
            RdvPatient[i] = new RDV(sharedPreferences.getInt(KEY_length+i, 0),sharedPreferences.getString(KEY_DateoldRDV+i, null),sharedPreferences.getString(KEY_NOM+i, null),sharedPreferences.getString(KEY_prenom+i, null),sharedPreferences.getString(KEY_gender+i, null));
        }
        return RdvPatient;
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
