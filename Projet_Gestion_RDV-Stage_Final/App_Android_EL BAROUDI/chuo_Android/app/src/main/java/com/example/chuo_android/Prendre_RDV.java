package com.example.chuo_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Prendre_RDV extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private User user = SharedPrefManager.getInstance(this).getUser();

    EditText Spselected,dateRDV;
    private Spinner customSpinner;
    String ValideDate;


    Medecin[] md = SharedPrefManager.getInstance(this).getAllMedecin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre__r_d_v);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Prendre un Rendez-vous");

        Spselected = (EditText) findViewById(R.id.SPselected);

        // get the text from Specialite-activity
        Intent intent = getIntent();
        String text = intent.getStringExtra(intent.EXTRA_TEXT);

        Spselected.setText(text);
        Spselected.setTextColor(Color.parseColor("#000000"));
        Spselected.setBackgroundColor(Color.parseColor("#DAE8FC"));
        Spselected.setGravity(Gravity.CENTER);
        Spselected.setEnabled(false);

        customSpinner = findViewById(R.id.customSpinner);




        // create spinneritemlist for spinner

        ArrayList<Medecin> customList = new ArrayList<>();
        for (int i=0;i<md[0].getTaille();i++){
            customList.add(new Medecin(md[i].getNom().toString(),md[i].getPrenom().toString(),md[i].getNumedecin().toString(),md[i].getTaille()));
        }
        //customList.add(new Medecin(md[0].getNom(),"test",0));




        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        if (customSpinner != null) {
            customSpinner.setAdapter(customAdapter);
            customSpinner.setOnItemSelectedListener(this);
        }


        //Date RDV

        dateRDV = findViewById(R.id.dateRDV);
        dateRDV.setTextColor(Color.parseColor("#000000"));
        dateRDV.setBackgroundColor(Color.parseColor("#F87474"));
        dateRDV.setInputType(InputType.TYPE_NULL);

        dateRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateRDV);
            }
        });

        dateRDV.setCursorVisible(false);
        findViewById(R.id.validerRDV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValiderRDV();

            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Medecin items = (Medecin) adapterView.getSelectedItem();
        Toast.makeText(this, items.getNom(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm  dd MMMM yyyy");


                            if(calendar.get(Calendar.MINUTE)>0 && calendar.get(Calendar.MINUTE)<=10){
                                calendar.set(Calendar.MINUTE,0);
                            }else {
                                if(calendar.get(Calendar.MINUTE)>10 && calendar.get(Calendar.MINUTE)<=30){
                                    calendar.set(Calendar.MINUTE,20);
                                }else {
                                    if(calendar.get(Calendar.MINUTE)>30 && calendar.get(Calendar.MINUTE)<=50){
                                        calendar.set(Calendar.MINUTE,40);
                                    }else {
                                        if(calendar.get(Calendar.MINUTE)>50){
                                            calendar.set(Calendar.MINUTE,0);
                                            calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+1);

                                        }
                                    }
                                }
                            }
                        if(calendar.get(Calendar.HOUR_OF_DAY)>16 || calendar.get(Calendar.HOUR_OF_DAY)<9){
                            date_time_in.setText("SVP selectionner une heur entre 9h->17h");
                            date_time_in.setError("SVP selectionner une heur entre 9h->17h");
                            date_time_in.requestFocus();
                            date_time_in.setTextColor(Color.parseColor("#000000"));
                            date_time_in.setBackgroundColor(Color.parseColor("#F87474"));
                            return;
                        }

                            date_time_in.setError(null);
                            date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                            date_time_in.setTextColor(Color.parseColor("#000000"));
                            date_time_in.setBackgroundColor(Color.parseColor("#FF9800"));

                            SimpleDateFormat FormatV=new SimpleDateFormat("yyyy-MM-dd hh:mm");
                             ValideDate=FormatV.format(calendar.getTime());

                    }
                };
                TimePickerDialog  dialogT =new TimePickerDialog (Prendre_RDV.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);

                dialogT.show();

            }
        };

        DatePickerDialog dialog = new DatePickerDialog(Prendre_RDV.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();



    }
    // Valider RDV
    private void ValiderRDV() {

        //if it passes all the validations

        class validerrdv extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("CIN",user.getCin().toString());
                params.put("Numedecin", md[customSpinner.getSelectedItemPosition()].getNumedecin().toString());
                params.put("dateRDV", ValideDate);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_PrRDV, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBarRDV);
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

                        //starting Accueil activity
                        startActivity(new Intent(getApplicationContext(), Accueil.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //Re-starting the RDV activity
                        startActivity(new Intent(getApplicationContext(), Prendre_RDV.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        validerrdv vr = new validerrdv();
        vr.execute();
    }

}
