package com.example.chuo_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class rdvAllPatient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private boolean table_flg = false;
    private RDV[] RdvPatient = SharedPrefManager.getInstance(this).getRDVAllPatient();
   EditText dateRDV;
    String ValideDate,dateChoisi;
    String currentDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
    private User user = SharedPrefManager.getInstance(this).getUser();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_all_patient);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Consulter votre liste de Rendez-vous");

        dateRDV = findViewById(R.id.dateRDVAllP);
      /*  dateRDV.setTextColor(Color.parseColor("#000000"));
        dateRDV.setBackgroundColor(Color.parseColor("#F87474"));*/
        Intent intent = getIntent();
        String text = intent.getStringExtra(intent.EXTRA_TEXT);
        dateRDV.setText(text);
        dateRDV.setTextColor(Color.parseColor("#000000"));
        dateRDV.setBackgroundColor(Color.parseColor("#FF9800"));


        dateRDV.setInputType(InputType.TYPE_NULL);
        dateRDV.setGravity(Gravity.CENTER);
        dateRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateRDV);
            }
        });
        findViewById(R.id.calendrier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateRDV);
            }
        });


        TableRow row=new TableRow(this.getApplicationContext());
        TableLayout tlayout= findViewById(R.id.tableALLrdv);

        tlayout.setGravity(Gravity.CENTER);

        row=new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#FF9800"));
        row.setPadding(5,5,5,5);

        TextView text1=new TextView(this.getApplicationContext());
        TextView text2=new TextView(this.getApplicationContext());
        TextView text3=new TextView(this.getApplicationContext());
        TextView text4=new TextView(this.getApplicationContext());

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,1
        );
        text1.setText("Date du RDV");
        text2.setText("Nom");
        text3.setText("Prenom");
        text4.setText("Sex");

        text1.setTypeface(Typeface.DEFAULT_BOLD);
        text1.setLayoutParams(params);
        text1.setGravity(Gravity.CENTER);
        text1.setTextSize(15);

        text2.setTypeface(Typeface.DEFAULT_BOLD);
        text2.setLayoutParams(params);
        text2.setGravity(Gravity.CENTER);
        text2.setTextSize(15);

        text3.setTypeface(Typeface.DEFAULT_BOLD);
        text3.setLayoutParams(params);
        text3.setGravity(Gravity.CENTER);
        text3.setTextSize(15);

        text4.setTypeface(Typeface.DEFAULT_BOLD);
        text4.setLayoutParams(params);
        text4.setGravity(Gravity.CENTER);
        text4.setTextSize(15);

        row.addView(text1);
        row.addView(text2);

        row.addView(text3);
        row.addView(text4);

        tlayout.addView(row);

       // Arrays.sort(RdvPatient);
       // Arrays.sort(RdvPatient, Comparator.comparing(RDV::getDateRDV));
        /*
        for (  int  i  =  0 ;  i < RdvPatient[0].getTaille() ;  i ++ ) {
                if(min.compareTo(RdvPatient[i].getDateRDV()) < 0){
                    min=RdvPatient[i].getDateRDV();
                }
        }*/
        for (int i=0;i<RdvPatient[0].getTaille();i++)
        {
            row=new TableRow(this.getApplicationContext());
            row.setBackgroundColor(Color.parseColor("#DAE8FC"));
            row.setPadding(5,5,5,5);

            text1=new TextView(this.getApplicationContext());
            text2=new TextView(this.getApplicationContext());
            text3=new TextView(this.getApplicationContext());
            text4=new TextView(this.getApplicationContext());

            params.setMargins(0,0,1,1);
            // text1.setBackgroundColor(Color.parseColor("#ffffff"));
            //  text2.setBackgroundColor(Color.parseColor("#ffffff"));
            //  text3.setBackgroundColor(Color.parseColor("#ffffff"));
            text1.setLayoutParams(params);
            text1.setGravity(Gravity.CENTER);
            text1.setTextSize(12);
            text2.setLayoutParams(params);
            text2.setGravity(Gravity.CENTER);
            text2.setTextSize(12);

            text3.setLayoutParams(params);
            text3.setGravity(Gravity.CENTER);
            text3.setTextSize(12);
            text4.setLayoutParams(params);
            text4.setGravity(Gravity.CENTER);
            text4.setTextSize(12);

            /*text1.setText("TEST1");
            text2.setText("TEST2");
            text3.setText("TEST3");
            text4.setText("TEST4");*/

            text1.setText(RdvPatient[i].getDateRDV().toString());
            text2.setText(RdvPatient[i].getNom().toString());
            text3.setText(RdvPatient[i].getPrenom().toString());
            text4.setText(RdvPatient[i].getSex().toString());
/*
            if(Consurdv[i].getEtat().toString().equals("Valider <Modifier>")){
                text1.setBackgroundColor(Color.parseColor("#4CAF50"));
                text2.setBackgroundColor(Color.parseColor("#4CAF50"));
                text3.setBackgroundColor(Color.parseColor("#4CAF50"));
                text4.setBackgroundColor(Color.parseColor("#4CAF50"));

            }else{
                if(Consurdv[i].getEtat().toString().equals("Annuler")){
                    text1.setBackgroundColor(Color.parseColor("#C11515"));
                    text2.setBackgroundColor(Color.parseColor("#C11515"));
                    text3.setBackgroundColor(Color.parseColor("#C11515"));
                    text4.setBackgroundColor(Color.parseColor("#C11515"));
                }
            }*/


            row.addView(text1);
            row.addView(text2);
            row.addView(text3);
            row.addView(text4);

            tlayout.addView(row);
        }
    }

    public void collapseTable(View view) {
        TableLayout table = findViewById(R.id.tableALLrdv);
        Button switchBtn = findViewById(R.id.switchBtnALLrdv);

        // setColumnCollapsed(int columnIndex, boolean isCollapsed)
        table.setColumnCollapsed(2, table_flg);
        table.setColumnCollapsed(3, table_flg);


        if (table_flg) {
            // Close
            table_flg = false;
            switchBtn.setText("Show Detail");
        } else {
            // Open
            table_flg = true;
            switchBtn.setText("Hide Detail");
        }

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
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy");

                dateRDV.setText(simpleDateFormat.format(calendar.getTime()));
                dateRDV.setTextColor(Color.parseColor("#000000"));
                dateRDV.setBackgroundColor(Color.parseColor("#FF9800"));
                currentDate=simpleDateFormat.format(calendar.getTime());

                SimpleDateFormat FormatV=new SimpleDateFormat("yyyy-MM-dd");
                ValideDate=FormatV.format(calendar.getTime());
                dateValide();

            }
        };

        DatePickerDialog dialog = new DatePickerDialog(rdvAllPatient.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();



    }
    private void dateValide(){
        //old rdv
        class dateRdvValide extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarRDVallPatient);
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
                        //RdvPatient[0] = new RDV(0,RdvPatientJson.getJSONObject(0).getString("daterdv"),RdvPatientJson.getJSONObject(0).getString("nom"),RdvPatientJson.getJSONObject(0).getString("prenom"),RdvPatientJson.getJSONObject(0).getString("sex"));



                        //storing the oldRDV in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).RDVAllPatient(RdvPatient,t);


                        //starting the profile activity
                        // finish();
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
                params.put("dateValide",ValideDate);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_dateValide,params);
            }
        }

        dateRdvValide rp = new dateRdvValide();
        rp.execute();
    }

}
