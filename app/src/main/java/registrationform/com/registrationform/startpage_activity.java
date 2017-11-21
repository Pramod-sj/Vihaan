package registrationform.com.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);
        txt1=(TextView)findViewById(R.id.txt1);
        regbutton=(Button)findViewById(R.id.regbutton);
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
}
