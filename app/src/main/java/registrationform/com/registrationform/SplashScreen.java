package registrationform.com.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import registrationform.com.registrationform.MainActivity;
public class SplashScreen extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ProgressBar spinner;
        spinner=(ProgressBar)findViewById(R.id.probar);
        spinner.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, startpage_activity.class);
                startActivity(intent);
                spinner.setVisibility(View.GONE);
                finish();

            }
        }, 2000);
    }
}