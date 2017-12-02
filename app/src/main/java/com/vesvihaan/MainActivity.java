package com.vesvihaan;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {
    EditText f_name,l_name,c_name,email,phone,class_nm;
    Button b;
    Spinner s;
    JSONObject jObject;
    AlertDialog.Builder noconn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        f_name=(EditText)findViewById(R.id.et1_first);
        l_name=(EditText)findViewById(R.id.et1_second);
        c_name=(EditText)findViewById(R.id.et4_col);
        phone=(EditText)findViewById(R.id.et3_phone);
        class_nm=(EditText)findViewById(R.id.et5_class);
        email=(EditText)findViewById(R.id.et2_email);
        b=(Button)findViewById(R.id.bt1);
        s=(Spinner)findViewById(R.id.events);
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
                    jObject = new JSONObject();
                    try{
                        jObject.put("fname",first_name);
                        jObject.put("lname",last_name);
                        jObject.put("email",email_id);
                        jObject.put("cell",phone_no);
                        jObject.put("clgname",college_name);
                        jObject.put("class",class_name);
                        jObject.put("event",val);

                        new SendData().execute("http://vesvihaan.com/Registration/submit_data", jObject.toString());
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),jObject.toString(),Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,MainWindow.class);
                    startActivity(i);
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

    private class SendData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();
                httpURLConnection.setRequestMethod("GET");
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
            if(result == null) {
                result = "THERE WAS AN ERROR";
            }
            //parseJsonData(result);

        }

    }
    /*private void parseJsonData(String jsonResponse){
        try
        {
            JSONArray jsonArray = new JSONArray(jsonResponse);

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String f = jsonObject1.optString("fname");
                String s = jsonObject1.optString("lname");
                String Id = jsonObject1.optString("email");
                String c = jsonObject1.optString("cell");
                Toast.makeText(getApplicationContext(),f+" "+s,Toast.LENGTH_LONG).show();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }*/

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


