package example.comtest.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.buttonTest);



        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                System.out.println("HEEEEEHHOO");
                requestToApi rta = new requestToApi(getApplicationContext());
                String response;
                response = rta.postToServer("on", "1", "1");
                System.out.println(response);

            }
        });
    }
}
