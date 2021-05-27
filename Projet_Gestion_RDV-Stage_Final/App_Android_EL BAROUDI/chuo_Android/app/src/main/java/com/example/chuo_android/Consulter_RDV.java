package com.example.chuo_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Consulter_RDV extends AppCompatActivity{
    private boolean table_flg = false;
    private RDV[] Consurdv = SharedPrefManager.getInstance(this).getConsuRDV();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter__r_d_v);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Consulter vos Rendez-vous");


        TableRow row=new TableRow(this.getApplicationContext());
        TableLayout tlayout= findViewById(R.id.tableConsultation);

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
        text2.setText("Specialité");
        text3.setText("Medecin");
        text4.setText("Etat");

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
        for (int i=0;i<Consurdv[0].getTaille();i++)
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


            text1.setText(Consurdv[i].getDateRDV().toString());
            text2.setText(Consurdv[i].getSpecialite().toString());
            text3.setText(Consurdv[i].getNameMedecin().toString());
            text4.setText(Consurdv[i].getEtat().toString());

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
            }


            row.addView(text1);
            row.addView(text2);
            row.addView(text3);
            row.addView(text4);

            tlayout.addView(row);
        }
    }

    public void collapseTable(View view) {
        TableLayout table = findViewById(R.id.tableConsultation);
        Button switchBtn = findViewById(R.id.switchBtnConsu);

        // setColumnCollapsed(int columnIndex, boolean isCollapsed)
        table.setColumnCollapsed(1, table_flg);
        table.setColumnCollapsed(2, table_flg);

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


}
