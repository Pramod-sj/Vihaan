package registrationform.com.registrationform;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by pramo on 10/18/2017.
 */

public class startpage_activity extends AppCompatActivity {
    Button regbutton;
    TextView txt1;
    AlertDialog.Builder noconn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);
        txt1=(TextView)findViewById(R.id.txt1);
        regbutton=(Button)findViewById(R.id.regbutton);
        noconn=new AlertDialog.Builder(this);
        noconn.setCancelable(false);
        noconn.setMessage("This app requires internet please enable your phone's wifi or data");
        noconn.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
        noconn.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        final AlertDialog alert = noconn.create();
        //Setting the title manually
        alert.setTitle("No Internet");
        if(isConnected_custom()==false){
            alert.show();
        }
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(startpage_activity.this,MainActivity.class);
                startActivity(i);
            }
        });
        txt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(startpage_activity.this,MainWindow.class);
                startActivity(i2);
            }
        });
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
