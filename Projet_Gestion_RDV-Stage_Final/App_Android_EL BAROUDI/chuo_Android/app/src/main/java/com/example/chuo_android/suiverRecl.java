package com.example.chuo_android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class suiverRecl extends AppCompatActivity {

    private boolean table_flg = false;
    private Recl[] recl = SharedPrefManager.getInstance(this).getAllRecl();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiver_recl);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suivre mes réclamations");


        TableRow row=new TableRow(this.getApplicationContext());
        TableLayout tlayout= findViewById(R.id.tablerec);

        tlayout.setGravity(Gravity.CENTER);

        row=new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#FF9800"));
        row.setPadding(5,5,5,5);
        TextView text1=new TextView(this.getApplicationContext());
        TextView text2=new TextView(this.getApplicationContext());
        TextView text3=new TextView(this.getApplicationContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                150,
                TableRow.LayoutParams.WRAP_CONTENT,1
        );
        text1.setText("Date du Recl");
        text2.setText("Objet");
        text3.setText("Statut");

       text1.setTypeface(Typeface.DEFAULT_BOLD);
        text1.setLayoutParams(params);
        text1.setGravity(Gravity.CENTER);
        text1.setTextSize(20);

        text2.setTypeface(Typeface.DEFAULT_BOLD);
        text2.setLayoutParams(params);
        text2.setGravity(Gravity.CENTER);
        text2.setTextSize(20);

        text3.setTypeface(Typeface.DEFAULT_BOLD);
        text3.setLayoutParams(params);
        text3.setGravity(Gravity.CENTER);
        text3.setTextSize(20);

        row.addView(text1);
        row.addView(text2);

        row.addView(text3);

        tlayout.addView(row);
        for (int i=0;i<recl[0].getTaille();i++)
        {
            row=new TableRow(this.getApplicationContext());
            row.setBackgroundColor(Color.parseColor("#DAE8FC"));
            row.setPadding(5,5,5,5);

            text1=new TextView(this.getApplicationContext());
            text2=new TextView(this.getApplicationContext());
            text3=new TextView(this.getApplicationContext());
            params.setMargins(0,0,1,1);
           // text1.setBackgroundColor(Color.parseColor("#ffffff"));
          //  text2.setBackgroundColor(Color.parseColor("#ffffff"));
          //  text3.setBackgroundColor(Color.parseColor("#ffffff"));
            text1.setLayoutParams(params);
            text1.setGravity(Gravity.CENTER);
           // text1.setTextSize(15);
            text2.setLayoutParams(params);
            text2.setGravity(Gravity.CENTER);
         //   text2.setTextSize(15);

            text3.setLayoutParams(params);
            text3.setGravity(Gravity.CENTER);
         //   text3.setTextSize(15);

            if(recl[i].getStatut().equals("Encoure")){
                text3.setTextColor(Color.parseColor("#FF5722"));
            }else{
                if(recl[i].getStatut().equals("Corriger")){
                    text3.setTextColor(Color.parseColor("#38AF3C"));
                }else{
                    if(recl[i].getStatut().equals("Rejeter")){
                        text3.setTextColor(Color.parseColor("#C11515"));
                    }
                }
            }


           /* text1.setText("24-03-2000");
            text2.setText("Je veux bien corriger mon CIN");
            text3.setText("Encoure");*/

            text1.setText(recl[i].getDateRecl().toString());
            text2.setText(recl[i].getContenu().toString());
            text3.setText(recl[i].getStatut().toString());

            row.addView(text1);
            row.addView(text2);
            row.addView(text3);
            tlayout.addView(row);
        }
    }

    public void collapseTable(View view) {
        TableLayout table = findViewById(R.id.tablerec);
        Button switchBtn = findViewById(R.id.switchBtnrec);

        // setColumnCollapsed(int columnIndex, boolean isCollapsed)
        table.setColumnCollapsed(1, table_flg);

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
