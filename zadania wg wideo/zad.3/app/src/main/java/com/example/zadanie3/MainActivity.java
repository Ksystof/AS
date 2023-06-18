package com.example.zadanie3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Zad.1:
        // Log.d("test","output");

        //Zad.4:
        //Log.d("fail","it's not working");

        //Zad.11:
        //findViewById(R.id.button5);

    }

    //Zad.2:
    public void disable(View v){
        /*v.setEnabled(false);

        //Zad.3:
        //Log.d("Success","Button Disabled");

        //Zad.5:
        //Button button = (Button) v;
        .setText("Disabled");
         */

        /* //Zad.6:
        View myView = findViewById(R.id.button0);
        myView.setEnabled(false);
        Button button = (Button) myView;
        button.setText("New Disabled");
         */

        //Zad.7:
        //findViewById(R.id.button0).setEnabled(false);
        //((Button)findViewById(R.id.button0)).setText("new new Disabled");
    }

    public void handleText(View v){
        /*
        //Zad.8:
        TextView t = findViewById(R.id.source);
        String input = t.getText().toString();

        Log.d("info",input); */

        /*
        //Zad.9:
        TextView t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.output)).setText(input);
        Log.d("info",input); */

        /*
        //Zad.10:
        EditText t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.output)).setText(input);
        //Toast.makeText(this,"Alert",Toast.LENGTH_LONG).show();
        //Log.d("info",input);
        Toast.makeText(this,input,Toast.LENGTH_LONG).show(); */

        /*
        //Zad.12:
        v.setEnabled(false);
        Log.d("muLog","message");
        Toast.makeText(this, "clicked",
                Toast.LENGTH_LONG).show();
         */
    }

    //Zad.13
    public void launchSettings(View v){
        //launch a new activity
        Intent i = new Intent(this,launchSettings.class);
        startActivity(i);
    }
}