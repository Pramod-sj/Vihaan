package registrationform.com.registrationform;

import android.app.ActionBar;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity  {
    EditText name,c_name,email;
    Button b;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.et1);
        c_name=(EditText)findViewById(R.id.et2);
        email=(EditText)findViewById(R.id.et3);
        b=(Button)findViewById(R.id.bt1);
        s=(Spinner)findViewById(R.id.events);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = name.getText().toString();
                String college_name = c_name.getText().toString();
                String email_id = email.getText().toString();
                String val = s.getSelectedItem().toString();
                if (TextUtils.isEmpty(full_name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } else if (!validateName(full_name)) {
                    Toast.makeText(getApplicationContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(college_name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your college name", Toast.LENGTH_SHORT).show();
                } else if (!validateName(college_name)) {
                    Toast.makeText(getApplicationContext(), "Invalid college Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(getApplicationContext(), "Empty email field", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email_id)) {
                    Toast.makeText(getApplicationContext(), "Invalid Email id", Toast.LENGTH_SHORT).show();
                }
                if ((isEmailValid(email_id)==true) && (validateName(college_name)==true) && (validateName(full_name)==true)){
                    Toast.makeText(getApplicationContext(), full_name + "\n" + college_name + "\n" + email_id + "\n" + val, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    public static boolean validateName( String name )
    {
        return name.matches( "^[a-zA-Z\\s]*$" );
    } // end method validateFirstName

}
