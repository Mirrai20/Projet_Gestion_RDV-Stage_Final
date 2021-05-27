package com.example.chuo_android;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Accueil extends AppCompatActivity {
    private User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Accueil");
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        findViewById(R.id.boxMyaccount).setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                Toast.makeText(getApplicationContext(), "My account Selected",
                        Toast.LENGTH_SHORT).show();
            }

        });
        findViewById(R.id.boxRecl).setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Reclamation.class));
                Toast.makeText(getApplicationContext(), "Reclamation Selected",
                        Toast.LENGTH_SHORT).show();
            }

        });
        findViewById(R.id.boxSrec).setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View view) {
                ViewALLRecl();
            }

        });

        if(user.getDescription().equals("Medecin")){
            findViewById(R.id.boxSP).setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) findViewById(R.id.boxSrec).getLayoutParams();
            layoutParams.setMargins(80, 0, 0, 32);
            findViewById(R.id.boxSrec).requestLayout();

            ViewGroup.MarginLayoutParams layoutParams2 =
                    (ViewGroup.MarginLayoutParams) findViewById(R.id.boxRecl).getLayoutParams();
            layoutParams2.setMargins(0, 0, 80, 32);
            findViewById(R.id.boxRecl).requestLayout();

            //LayoutParams layoutParams = (LayoutParams) yourView.findViewById(R.id.THE_ID).getLayoutParams();

            MenuItem itemsp = navigationView.getMenu().findItem(R.id.nav_search);
            itemsp.setVisible(false);
            findViewById(R.id.boxConulRdv).setOnClickListener(new CardView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    ALLRdvPatient();

                }

            });
            findViewById(R.id.boxOldRdv).setOnClickListener(new CardView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    AllOldRdvPatient();

                }

            });
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.nav_account) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        Toast.makeText(getApplicationContext(), menuItem.getTitle() + " Selected",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        if(id == R.id.nav_logout){
                            finish();
                            SharedPrefManager.getInstance(getApplicationContext()).logout();
                        }else{

                                if(id == R.id.nav_history){
                                    AllOldRdvPatient();
                                }else{

                                    if(id == R.id.nav_consultation){
                                        ALLRdvPatient();

                                    }else{

                                        if(id == R.id.nav_Rec){
                                            startActivity(new Intent(getApplicationContext(), Reclamation.class));
                                            Toast.makeText(getApplicationContext(), "Reclamation Selected",
                                                    Toast.LENGTH_SHORT).show();

                                        }else{

                                            if(id == R.id.nav_Srec){
                                                ViewALLRecl();

                                            }

                                        }

                                    }

                                }

                        }
                    }

                    drawer.closeDrawers();
                    return true;
                }
            });

        }else{
            if(user.getDescription().equals("Patient")){
                findViewById(R.id.boxOldRdv).setOnClickListener(new CardView.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewOldRdv();

                    }

                });
                findViewById(R.id.boxSP).setOnClickListener(new CardView.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewSpecialite();
                    }

                });
                findViewById(R.id.boxConulRdv).setOnClickListener(new CardView.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ConsulterRDV();

                    }

                });
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_account) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            Toast.makeText(getApplicationContext(), menuItem.getTitle() + " Selected",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            if(id == R.id.nav_logout){
                                finish();
                                SharedPrefManager.getInstance(getApplicationContext()).logout();
                            }else{
                                if (id == R.id.nav_search){
                                    ViewSpecialite();
                                }else{
                                    if(id == R.id.nav_history){
                                        ViewOldRdv();
                                    }else{
                                        if(id == R.id.nav_consultation){
                                            ConsulterRDV();
                                        }else{
                                            if(id == R.id.nav_Rec){
                                                startActivity(new Intent(getApplicationContext(), Reclamation.class));
                                                Toast.makeText(getApplicationContext(), "Reclamation Selected",
                                                        Toast.LENGTH_SHORT).show();

                                            }else{

                                                if(id == R.id.nav_Srec){
                                                    ViewALLRecl();

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }

                        drawer.closeDrawers();
                        return true;
                    }
                });


            }
        }



    }

    private void ViewSpecialite() {
        //first getting the values
       /* final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }*/

        //if everything is fine

        class AllSpecialite extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
             //   startActivity(new Intent(getApplicationContext(), specialite.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response JSONObject userJson = obj.getJSONObject("user");
                        JSONArray spJson = obj.getJSONArray("specialite");
                        //String command;
                        ClSpecialite[] sp = new ClSpecialite[10];
                        //creating a new user object
                        int t=spJson.length();
                    /*    List<String> strings = new ArrayList<>();
                        for (int i = 0; i <= t; i++) {
                            strings.add(spJson.getString(i));
                        }*/
                        for (int i=0;i<t;i++){
                          //  command = spJson.getString(i);
                            sp[i] = new ClSpecialite(spJson.getJSONObject(i).getString("specialite"),"test",t);
                          //  sp[1] = new ClSpecialite(spJson.getJSONObject(1).getString("specialite"),"test",t);

                                  /*  sp[i].setNamSP(spJson.getJSONObject(i).getString("specialite"));
                                    sp[i].setMedecin(spJson.getJSONObject(i).getString("Medecin"));*/
                        }
                        //storing the specialites in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).AllSP(sp,t);


                        //starting the profile activity
                       // finish();
                        startActivity(new Intent(getApplicationContext(), specialite.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", "test");
               /* params.put("password", password);*/

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_SP,params);
            }
        }

        AllSpecialite sp = new AllSpecialite();
        sp.execute();
    }


    private void ViewOldRdv(){
        //old rdv
        class OLDRDV extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                //   startActivity(new Intent(getApplicationContext(), historique.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the old rdv from the response JSONObject
                        JSONArray oldrdvJson = obj.getJSONArray("OLDRDV");
                        RDV[] oldrdv = new RDV[50];
                         int t=oldrdvJson.length();

                    for (int i=0;i<t;i++){
                        oldrdv[i] = new RDV(oldrdvJson.getJSONObject(i).getString("daterdv"),oldrdvJson.getJSONObject(i).getString("specialite"),oldrdvJson.getJSONObject(i).getString("medecin"),t);
                        }

                      //  oldrdv[0] = new RDV(oldrdvJson.getJSONObject(0).getString("daterdv"),"test","test",t);

                        //storing the oldRDV in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).OLDRDV(oldrdv,t);


                    //starting the profile activity
                    // finish();
                    startActivity(new Intent(getApplicationContext(), historique.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_OLDRDV,params);
            }
        }

        OLDRDV or = new OLDRDV();
        or.execute();
    }

    private void ViewALLRecl(){
        //old rdv
        class ALLRecl extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                //   startActivity(new Intent(getApplicationContext(), historique.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the old rdv from the response JSONObject
                        JSONArray ReclJson = obj.getJSONArray("ALLrecl");
                        Recl[] AllRecl = new Recl[50];
                        int t=ReclJson.length();

                        for (int i=0;i<t;i++){
                             AllRecl[i] = new Recl(ReclJson.getJSONObject(i).getString("dateRecl"),ReclJson.getJSONObject(i).getString("contenu"),ReclJson.getJSONObject(i).getString("statut"),t);
                        }

                       // AllRecl[0] = new Recl(ReclJson.getJSONObject(0).getString("dateRecl"),"test","test",t);

                        //storing the oldRDV in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).ALLRECL(AllRecl,t);


                        //starting the profile activity
                        // finish();
                        startActivity(new Intent(getApplicationContext(), suiverRecl.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ALLrecl,params);
            }
        }

        ALLRecl Ar = new ALLRecl();
        Ar.execute();
    }
    private void ConsulterRDV(){
        //Consultation de rdv
        class consulterRDV extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                //   startActivity(new Intent(getApplicationContext(), historique.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the old rdv from the response JSONObject
                        JSONArray ConsurdvJson = obj.getJSONArray("ConsuRDV");
                        RDV[] Consurdv = new RDV[50];
                        int t=ConsurdvJson.length();

                        for (int i=0;i<t;i++){
                            Consurdv[i] = new RDV(ConsurdvJson.getJSONObject(i).getString("daterdv"),ConsurdvJson.getJSONObject(i).getString("specialite"),ConsurdvJson.getJSONObject(i).getString("medecin"),ConsurdvJson.getJSONObject(i).getString("Etat"),t);
                        }


                        //storing the oldRDV in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).ConsultationRDV(Consurdv,t);


                        //starting the profile activity
                        // finish();

                        startActivity(new Intent(getApplicationContext(), Consulter_RDV.class));

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ConsuRDV,params);
            }
        }

        consulterRDV cr = new consulterRDV();
        cr.execute();
    }
    private void ALLRdvPatient(){
        //old rdv
        class RdvPatient extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                //   startActivity(new Intent(getApplicationContext(), historique.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the old rdv from the response JSONObject
                        JSONArray RdvPatientJson = obj.getJSONArray("RdvPatient");
                        RDV[] RdvPatient = new RDV[50];
                        int t=RdvPatientJson.length();

                        for (int i=0;i<t;i++){
                            RdvPatient[i] = new RDV(t,RdvPatientJson.getJSONObject(i).getString("daterdv"),RdvPatientJson.getJSONObject(i).getString("nom"),RdvPatientJson.getJSONObject(i).getString("prenom"),RdvPatientJson.getJSONObject(i).getString("sex"));
                        }

                        //  oldrdv[0] = new RDV(oldrdvJson.getJSONObject(0).getString("daterdv"),"test","test",t);

                        //storing the oldRDV in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).RDVAllPatient(RdvPatient,t);


                        //starting the profile activity
                        // finish();
                        String currentDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());

                        Intent intent = new Intent(getApplicationContext(), rdvAllPatient.class);
                        intent.putExtra(intent.EXTRA_TEXT, currentDate);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_RdvPatient,params);
            }
        }

        RdvPatient rp = new RdvPatient();
        rp.execute();
    }
    private void AllOldRdvPatient(){
        //old rdv
        class allOldRdvPatient extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarV3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                //   startActivity(new Intent(getApplicationContext(), historique.class));


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the old rdv from the response JSONObject
                        JSONArray RdvPatientJson = obj.getJSONArray("RdvPatient");
                        RDV[] RdvPatient = new RDV[50];
                        int t=RdvPatientJson.length();

                       for (int i=0;i<t;i++){
                            RdvPatient[i] = new RDV(t,RdvPatientJson.getJSONObject(i).getString("daterdv"),RdvPatientJson.getJSONObject(i).getString("nom"),RdvPatientJson.getJSONObject(i).getString("prenom"),RdvPatientJson.getJSONObject(i).getString("sex"));
                        }
                       // RdvPatient[0] = new RDV(1,"test1","test2","test3","test4");

                        //  oldrdv[0] = new RDV(oldrdvJson.getJSONObject(0).getString("daterdv"),"test","test",1);

                        //storing the oldRDV in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).RDVAllPatient(RdvPatient,t);


                        //starting the profile activity
                        // finish();
                       /* DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -1);*/
                       // String currentDate = dateFormat.format(cal.getTime());;

                        Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                        String currentDate = dateFormat.format(mydate);

                        Intent intent = new Intent(getApplicationContext(), Historique_AllRdvPatient.class);
                        intent.putExtra(intent.EXTRA_TEXT, currentDate);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_OLDRdvPatient,params);
            }
        }

        allOldRdvPatient rp = new allOldRdvPatient();
        rp.execute();
    }

}
