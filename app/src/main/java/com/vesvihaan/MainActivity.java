package com.vesvihaan;
//all server connection and data sending part is not mine .....
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {
    EditText f_name,l_name,c_name,email,phone,class_nm;
    mehdi.sakout.fancybuttons.FancyButton b;
    Spinner s;
    HashMap<String, String> postDataParams;
    HTTPURLConnection service;
    private ProgressDialog pDialog;
    private int success=0;
    AlertDialog.Builder noconn;
    String first_name="";
    String last_name="";
    String college_name="";
    String class_name="";
    String phone_no="";
    String email_id="";
    String val="";
    private String path = "https://www.vesvihaan.com/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        service=new HTTPURLConnection();
        f_name=(EditText)findViewById(R.id.et1_first);
        l_name=(EditText)findViewById(R.id.et1_second);
        c_name=(EditText)findViewById(R.id.et4_col);
        phone=(EditText)findViewById(R.id.et3_phone);
        class_nm=(EditText)findViewById(R.id.et5_class);
        email=(EditText)findViewById(R.id.et2_email);
        b=(mehdi.sakout.fancybuttons.FancyButton)findViewById(R.id.bt1);
        s=(Spinner)findViewById(R.id.events);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        noconn=new AlertDialog.Builder(this);
        noconn.setCancelable(false);
        noconn.setMessage("Need internet for checking update");
        noconn.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        noconn.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        final AlertDialog alert=noconn.create();
        alert.setTitle("No Internet");
        if(isConnected_custom()==false){
            alert.show();
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isConnected_custom()==false){
                    alert.show();
                }
                String first_name = f_name.getText().toString();
                String last_name = l_name.getText().toString();
                String college_name = c_name.getText().toString();
                String class_name = class_nm.getText().toString();
                String phone_no = phone.getText().toString();
                String email_id = email.getText().toString();
                String val = s.getSelectedItem().toString();
                if ((TextUtils.isEmpty(email_id) == true) || (TextUtils.isEmpty(college_name) == true) || (TextUtils.isEmpty(first_name) == true) || (TextUtils.isEmpty(phone_no) == true) || (TextUtils.isEmpty(last_name) == true) || (TextUtils.isEmpty(class_name) == true)) {
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if ((TextUtils.isEmpty(email_id) == false) && (TextUtils.isEmpty(college_name) == false) && (TextUtils.isEmpty(first_name) == false) && (TextUtils.isEmpty(phone_no) == false) && (TextUtils.isEmpty(last_name) == false) && (TextUtils.isEmpty(class_name) == false)){
                    if (!validateName(first_name) && !validateName(last_name)) {
                        Toast.makeText(getApplicationContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
                    }
                    else if (!validateName(college_name)) {
                        Toast.makeText(getApplicationContext(), "Invalid college Name", Toast.LENGTH_SHORT).show();
                    }
                    else if (!isEmailValid(email_id)) {
                        Toast.makeText(getApplicationContext(), "Invalid Email id", Toast.LENGTH_SHORT).show();
                    }
                    else if (!validatePhone(phone_no)) {
                        Toast.makeText(getApplicationContext(), "Phone number is incorrect", Toast.LENGTH_SHORT).show();
                    }
                    else if (!validateName(class_name)) {
                        Toast.makeText(getApplicationContext(), "Should be of character", Toast.LENGTH_SHORT).show();
                    }
                }

                if ((isEmailValid(email_id)==true) && (validateName(college_name)==true) && (validateName(first_name)==true)&& (validatePhone(phone_no)==true)&& (validateName(last_name)==true)&& (validateName(class_name)==true)) {
                    postDataParams=new HashMap<String, String>();
                    postDataParams.put("fname",first_name.toString());
                    postDataParams.put("lname",last_name.toString());
                    postDataParams.put("email",email_id.toString());
                    postDataParams.put("phone",phone_no);
                    postDataParams.put("col_name",college_name.toString());
                    postDataParams.put("col_class",class_name.toString());
                    postDataParams.put("event",val);

                    //Call WebService
                    new PostDataTOServer().execute();

                }
             }
        });
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    public static boolean validateName( String name )
    {
        return name.matches( "^[a-zA-Z\\s]*$" );
    } // end method validateFirstName
    public boolean validatePhone(String phone){
        return phone.matches("^[987]{1,1}[0-9]{9,9}$");
    }

    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {

        String response = "";
        //Create hashmap Object to send parameters to web service

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            response= service.ServerData(path,postDataParams);
            try {
                JSONObject json = new JSONObject(response);
                //Get Values from JSONobject
                System.out.println("success=" + json.get("success"));
                success = json.getInt("success");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(MainActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(MainActivity.this,MainWindow.class);
            startActivity(i);

        }
    }

    public boolean isConnected_custom(){
        boolean isInternetAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(startpage_activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && (networkInfo.isConnected())){
                isInternetAvailable  = true;
            }
        }
        catch(Exception exception) {}
        return isInternetAvailable;
    }
}


