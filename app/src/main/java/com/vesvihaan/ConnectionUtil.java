package pramod.com.yourcook.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectionUtil {
    Context context;
    public ConnectionUtil(Context context){
        this.context=context;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null && activeNetwork.isFailover();
        return isConnected;
    }
}
