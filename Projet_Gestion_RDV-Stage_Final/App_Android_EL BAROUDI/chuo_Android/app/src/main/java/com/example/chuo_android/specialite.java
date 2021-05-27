package com.example.chuo_android;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class specialite extends AppCompatActivity {

    MaterialSearchView searchView;
    ListView lstView;
    ClSpecialite[] sp = SharedPrefManager.getInstance(this).getALLsp();

    ArrayList<String> lstSource =new ArrayList<String>();
    String SPECIALITE=null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int i=0;i<sp[0].getTaille();i++){
            lstSource.add(sp[i].getNamSP().toString().toUpperCase());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialite);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recherche spécialité");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lstView = (ListView)findViewById(R.id.lstView);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource);
        lstView.setAdapter(adapter);


        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default
                lstView = (ListView)findViewById(R.id.lstView);
                ArrayAdapter adapter = new ArrayAdapter(specialite.this,android.R.layout.simple_list_item_1,lstSource);
                lstView.setAdapter(adapter);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:lstSource){
                        if(item.contains(newText.toUpperCase()))
                            lstFound.add(item);

                    }

                    ArrayAdapter adapter = new ArrayAdapter(specialite.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(specialite.this,android.R.layout.simple_list_item_1,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });
      /*  lstView.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                Toast.makeText(getApplicationContext(), "My account Selected",
                        Toast.LENGTH_SHORT).show();
            }

        });*/
       /* lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(View view) {

                String main = lstView.getSelectedItem().toString();
               // startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                Toast.makeText(getApplicationContext(), main+" Selected",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                SPECIALITE=lstSource.get(position);
                ALLmedecin();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void ALLmedecin() {
        //first getting the values
        final String specialite = SPECIALITE;

        //if everything is fine

        class ALLMEDECIN extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBarVR);
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

                        //getting the medecin from the response
                        JSONArray mdJson = obj.getJSONArray("ALLmedc");
                        //String command;
                        Medecin[] md = new Medecin[10];
                        //creating a new user object
                        int t=mdJson.length();
                        if(t>0){
                            for (int i=0;i<t;i++){
                                md[i] = new Medecin(mdJson.getJSONObject(i).getString("Nom"),mdJson.getJSONObject(i).getString("Prenom"),mdJson.getJSONObject(i).getString("NuMedecin"),t);

                            }
                        }else{
                            md[0] = new Medecin("NULL","NULL","NULL",0);
                        }

                        //storing the medecin in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).AllMedecin(md,t);


                        //starting the profile activity
                        // finish();
                        Intent intent = new Intent(getApplicationContext(), Prendre_RDV.class);
                        intent.putExtra(intent.EXTRA_TEXT, specialite.toString());
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

                params.put("specialite", SPECIALITE);
                /* params.put("password", password);*/

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ALLmedc,params);
            }
        }

        ALLMEDECIN md = new ALLMEDECIN();
        md.execute();
    }


}
