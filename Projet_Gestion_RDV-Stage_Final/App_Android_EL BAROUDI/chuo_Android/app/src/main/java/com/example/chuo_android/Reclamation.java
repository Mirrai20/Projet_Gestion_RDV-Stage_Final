package com.example.chuo_android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Reclamation extends AppCompatActivity {
    private User user = SharedPrefManager.getInstance(this).getUser();

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;

    TextView file_name;
    String file_path=null;
    Button upload,upload_file;
    ProgressBar progressBar;
    EditText yourmsg;
    ImageView imageView;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Envoyer mes réclamations");

        upload_file=findViewById(R.id.upload_file);
        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    filePicker();
                }
                else{
                    requestPermission();
                }
            }
        });

        progressBar=findViewById(R.id.progress);
        upload=findViewById(R.id.upload);
        file_name=findViewById(R.id.filename);
        yourmsg = (EditText) findViewById(R.id.yourMsg);
        imageView = (ImageView) findViewById(R.id.imageView);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file_path!=null){
                    UploadFile();
                }
                else{
                    Toast.makeText(Reclamation.this, "Please Select File First", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void UploadFile() {
        final String msg = yourmsg.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(msg)) {
            yourmsg.setError("Please enter your message");
            yourmsg.requestFocus();
            return;
        }
        UploadFile uploadfile=new UploadFile();
        uploadfile.execute();
    }


    private void filePicker(){

        //.Now Permission Working
        Toast.makeText(Reclamation.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    //now checking if download complete

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Reclamation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(Reclamation.this, "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(Reclamation.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(Reclamation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Reclamation.this, "Permission Successfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Reclamation.this, "Permission Failed", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);
                upload_file.setText("Edit File");
                file_path=getPath(data.getData());
               // file_name.setText(file_path);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    class UploadFile extends AsyncTask<Bitmap, Void, String> {

        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progress);
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
                  //  JSONArray oldrdvJson = obj.getJSONArray("UplFILE");
                 /*   RDV[] oldrdv = new RDV[50];
                    int t=oldrdvJson.length();

                    for (int i=0;i<t;i++){
                        oldrdv[i] = new RDV(oldrdvJson.getJSONObject(i).getString("daterdv"),oldrdvJson.getJSONObject(i).getString("specialite"),oldrdvJson.getJSONObject(i).getString("medecin"),t);
                    }*/

                    //  oldrdv[0] = new RDV(oldrdvJson.getJSONObject(0).getString("daterdv"),"test","test",t);

                    //storing the oldRDV in shared preferences
                 //   SharedPrefManager.getInstance(getApplicationContext()).OLDRDV(oldrdv,t);


                    //starting the profile activity
                    // finish();
                    startActivity(new Intent(getApplicationContext(), Accueil.class));
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

            @Override
            protected String doInBackground(Bitmap... params) {
                RequestHandler requestHandler = new RequestHandler();

               // Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put("CIN",user.getCin().toString());
                data.put("Contenu", yourmsg.getText().toString());
                data.put("image", uploadImage);
                return requestHandler.sendPostRequest(URLs.URL_UplFILE,data);

            }
        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

    }

}