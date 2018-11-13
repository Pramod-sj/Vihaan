package com.vesvihaan;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ChecksumChecker extends AsyncTask<String,Void,String> {

    String url;
    String data;
    PaytmPGService paytmPGService;
    Context context;
    EventRegistrationHelper.OnPaymentListener onPaymentListener;
    public ChecksumChecker(Context context, String url, String data, PaytmPGService paytmPGService, EventRegistrationHelper.OnPaymentListener onPaymentListener) {
        this.context=context;
        this.url = url;
        this.data = data;
        this.paytmPGService = paytmPGService;
        this.onPaymentListener=onPaymentListener;

    }

    @Override
    protected String doInBackground(String... strings) {
        String data1="";
        HttpURLConnection httpURLConnection=null;
        try{
            //executing url
            URL url_=new URL(url);
            httpURLConnection= (HttpURLConnection) url_.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.getDoOutput();
            DataOutputStream dataOutputStream=new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
            dataOutputStream.close();
            //end
            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data1 += current;
            }
            Log.d("DATA",data1);
        }
        catch(Exception e){
            Log.d("ERROR",e.getLocalizedMessage());
        }
        return data1;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        HashMap<String,String> paramMap=new HashMap<>();
        JSONArray jsonArray;
        JSONObject mJsonObject;
        try {
            jsonArray=new JSONArray(result);
            mJsonObject=new JSONObject(jsonArray.get(0).toString());
            paramMap.put("MID", mJsonObject.getString("MID"));
            paramMap.put("ORDER_ID",mJsonObject.getString("ORDER_ID"));
            paramMap.put("CUST_ID", mJsonObject.getString("CUST_ID"));
            paramMap.put("INDUSTRY_TYPE_ID",mJsonObject.getString("INDUSTRY_TYPE_ID"));
            paramMap.put("CHANNEL_ID",mJsonObject.getString("CHANNEL_ID"));
            paramMap.put("TXN_AMOUNT",mJsonObject.getString("TXN_AMOUNT"));
            paramMap.put("WEBSITE",mJsonObject.getString("WEBSITE"));
            paramMap.put("EMAIL",mJsonObject.getString("EMAIL"));
            paramMap.put("CALLBACK_URL",mJsonObject.getString("CALLBACK_URL"));
            paramMap.put("CHECKSUMHASH",mJsonObject.getString("CHECKSUMHASH"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        PaytmOrder Order = new PaytmOrder(paramMap);

        paytmPGService.initialize(Order, null);

        paytmPGService.startPaymentTransaction(context, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        onPaymentListener.onFailed(inErrorMessage);
                        Log.i("uiError",inErrorMessage);
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction :" + inResponse);

                        String response=inResponse.getString("RESPMSG");

                        Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                        if (response.toLowerCase().equals("Txn Success".toLowerCase()))
                        {
                            onPaymentListener.onSuccessFullyPaid();
                        }else
                        {
                            onPaymentListener.onFailed(response);
                        }

                    }

                    @Override
                    public void networkNotAvailable() {
                        onPaymentListener.onFailed("Network is not available or might be slow!");
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        onPaymentListener.onFailed(inErrorMessage);
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                        onPaymentListener.onFailed(inErrorMessage);
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        onPaymentListener.onFailed("Transaction is cancelled");
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage,
                                                    Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed "
                                + inErrorMessage);
                        onPaymentListener.onFailed("Payment Transaction Failed ");
                    }

                });
    }
}
