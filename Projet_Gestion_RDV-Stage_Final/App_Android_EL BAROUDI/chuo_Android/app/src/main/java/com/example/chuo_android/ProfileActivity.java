package com.example.chuo_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {

    TextView cinN,Nom, Prenom, DateN, Tele, Email, Password,NewPassword, Description;
    String CIN;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mon Profil");

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        cinN = (TextView) findViewById(R.id.CINnoEdit);
        Nom = (TextView) findViewById(R.id.NomNoEdit);
        Prenom = (TextView) findViewById(R.id.PrenomNoEdit);
        DateN = (TextView) findViewById(R.id.DdNnoEdit);
        Tele = (TextView) findViewById(R.id.textTELE);
        Email = (TextView) findViewById(R.id.textEMAIL);
        Password = (TextView) findViewById(R.id.oldPassword);
        NewPassword = (TextView) findViewById(R.id.newPassword);
        //  Description = (TextView) findViewById(R.id.textDESCRIPTION);



        //getting the current user
        user = SharedPrefManager.getInstance(this).getUser();
        CIN=user.getCin().toString();
        //setting the values to the textviews
        cinN.setText(user.getCin().toUpperCase());
        Nom.setText(user.getNom().toUpperCase());
        Prenom.setText(user.getPrenom().toUpperCase());
        DateN.setText(user.getDateN().toUpperCase());
        Tele.setText(user.getTelephone());
        Email.setText(user.getEmail());

        cinN.setEnabled(false);
        Nom.setEnabled(false);
        Prenom.setEnabled(false);
        DateN.setEnabled(false);

        cinN.setGravity(Gravity.CENTER);
        Nom.setGravity(Gravity.CENTER);
        Prenom.setGravity(Gravity.CENTER);
        DateN.setGravity(Gravity.CENTER);

        cinN.setBackgroundColor(Color.parseColor("#FF9800"));
        Nom.setBackgroundColor(Color.parseColor("#FF9800"));
        Prenom.setBackgroundColor(Color.parseColor("#FF9800"));
        DateN.setBackgroundColor(Color.parseColor("#FF9800"));


        cinN.setTextColor(Color.parseColor("#000000"));
        Nom.setTextColor(Color.parseColor("#000000"));
        Prenom.setTextColor(Color.parseColor("#000000"));
        DateN.setTextColor(Color.parseColor("#000000"));

        //  Password.setText(user.getPassword());
      //  Description.setText(user.getDescription());

        //when the user presses logout button
        //calling the logout method
      /*  findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });*/
        //calling the method login
        findViewById(R.id.modifierPr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userUpdate();
            }
        });
    }
    private void userUpdate() {
        //first getting the values
        final String email = Email.getText().toString();
        final String tele = Tele.getText().toString();
        final String password = Password.getText().toString();
        final String newPassword = NewPassword.getText().toString();
        final String cin = CIN;
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            Email.setError("Please enter your email");
            Email.requestFocus();
            return;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Email.setError("S'il vous plait entrer une adresse EMAIL valide");
                Email.requestFocus();
                return;
            }
        }

        if (TextUtils.isEmpty(tele)) {
            Tele.setError("Please enter your number phone");
            Tele.requestFocus();
        }else{
            if(tele.length()<10){
                Tele.setError("Votre numéro de téléphone est trop court");
                Tele.requestFocus();
                return;

            }else{
                if(!Pattern.matches("[0]{1}[6-7]{1}[0-9]{8}", tele)){
                    Tele.setError("S'il vous plait entrer un numéro valide");
                    Tele.requestFocus();
                    return;
                }
            }
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Please enter your password");
            Password.requestFocus();
            return;
        }

        //if everything is fine

        class UserUpdate extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar2);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //getting the user from the response

                        JSONObject userJson = obj.getJSONObject("user");

                        user.setEmail(userJson.getString("email"));
                        user.setTelephone(userJson.getString("telephone"));
                        user.setPassword(userJson.getString("password"));
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        //Re-starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
                params.put("CIN", cin);
                params.put("email", email);
                params.put("password", password);
                params.put("newPassword", newPassword);
                params.put("telephone", tele);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_UPDPR, params);
            }
        }

        UserUpdate ul = new UserUpdate();
        ul.execute();
    }

}