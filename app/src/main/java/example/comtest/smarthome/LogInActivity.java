package example.comtest.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Sweetpink on 2016-11-27.
 */

public class LogInActivity  extends AppCompatActivity {

    requestToApi API;
    TextView errorMessageTV = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        API = new requestToApi(getApplicationContext());

        final Button login = (Button) findViewById(R.id.loginbutton);
        final EditText usernameET = (EditText) findViewById(R.id.username);
        final EditText passwordET = (EditText) findViewById(R.id.password);
        errorMessageTV = (TextView) findViewById(R.id.ErrorMessage);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                API.checkUser(username, password, LogInActivity.this);
                System.out.println("Calling API");
            }
        });



    }

    public void checkCredantial(String response){
        System.out.println("Response in method: " + response);
        if (response.equalsIgnoreCase("Wrong credentials")) {
            errorMessageTV.setText("Wrong username or password!");
        } else{
            DataStorage.getInstance().setUserId(response);
            System.out.println("THE USER ID IS: " + response);
            Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(myIntent);
        }
    }
}
