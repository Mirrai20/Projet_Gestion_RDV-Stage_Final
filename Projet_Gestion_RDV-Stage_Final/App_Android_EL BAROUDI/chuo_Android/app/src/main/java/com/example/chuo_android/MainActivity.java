package com.example.chuo_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText CIN, Nom, Prenom, Email, Password, Telephone, NuMedecin;
    RadioGroup radioGroupGender, radioDescription;
    Spinner selectSP;
    TextView dateN;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Date de Naissance";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CHUO Authentification");

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Accueil.class));
            return;
        }
        CIN = (EditText) findViewById(R.id.signupCIN);
        Nom = (EditText) findViewById(R.id.signupNom);
        Prenom = (EditText) findViewById(R.id.signupPrenom);
        Email = (EditText) findViewById(R.id.signupEmail);
        Password = (EditText) findViewById(R.id.signupPassword);
        Telephone = (EditText) findViewById(R.id.signuptelephone);
        NuMedecin = (EditText) findViewById(R.id.signupNuMedecin);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);
        radioDescription = (RadioGroup) findViewById(R.id.radioDescription);
        selectSP = (Spinner) findViewById(R.id.signupselectSP);

        dateN = (TextView) findViewById(R.id.signupDateN);
        dateN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cal.add(Calendar.YEAR, -18);
                dialog.getDatePicker().setMaxDate(new Date(cal.getTimeInMillis()).getTime());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateN.setText(date);
            }
        };


        findViewById(R.id.radioButtonMedecin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.radioGender).setVisibility(View.GONE);
                findViewById(R.id.signupselectSP).setVisibility(View.VISIBLE);
                findViewById(R.id.signupNuMedecin).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.radioButtonPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.signupselectSP).setVisibility(View.GONE);
                findViewById(R.id.signupNuMedecin).setVisibility(View.GONE);
                findViewById(R.id.radioGender).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user pressed on login
                //we will open the login screen
                Intent i= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void registerUser() {
        final String cin = CIN.getText().toString().trim();
        final String nom = Nom.getText().toString().trim();
        final String prenom = Prenom.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String telephone = Telephone.getText().toString().trim();
        final String numedecin = NuMedecin.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String description = ((RadioButton) findViewById(radioDescription.getCheckedRadioButtonId())).getText().toString();
        final String selectsp =  selectSP.getSelectedItem().toString();

        //first we will do the validations
        if (TextUtils.isEmpty(cin)) {
            CIN.setError("Veuillez entrer ton CIN");
            CIN.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nom)) {
            Nom.setError("Veuillez entrer ton nom");
            Nom.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(prenom)) {
            Prenom.setError("Veuillez entrer ton prenom");
            Prenom.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Email.setError("Veuillez entrer ton email");
            Email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Veuillez entrer ton password");
            Password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(telephone)) {
            Telephone.setError("Veuillez entrer ton Téléphone");
            Telephone.requestFocus();
            return;
        }
        if(description.equals("Medecin")){
            if (TextUtils.isEmpty(numedecin)) {
                NuMedecin.setError("Veuillez entrer ton Numéro Medecin");
                NuMedecin.requestFocus();
                return;
            }
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",cin);
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("email", email);
                params.put("password", password);
                params.put("telephone", telephone);
                params.put("description", description);
                if(description.equals("Patient")){
                    params.put("sex", gender);
                }else{
                    if(description.equals("Medecin")){
                        params.put("specialite", selectsp);
                        params.put("Numedecin", numedecin);
                    }
                }

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //starting the Login activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}