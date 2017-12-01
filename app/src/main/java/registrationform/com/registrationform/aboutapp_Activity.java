package registrationform.com.registrationform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pramod_sj on 25/11/17.
 */

public class aboutapp_Activity extends AppCompatActivity {
    TextView t11;
    String versionName = BuildConfig.VERSION_NAME;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.aboutapp);
        t11=(TextView)findViewById(R.id.vernumber);
        t11.setText("Version "+versionName);

    }
}
