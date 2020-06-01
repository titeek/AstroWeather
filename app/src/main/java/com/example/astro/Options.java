package com.example.astro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Options extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] minutes={"15","30","45","60","120"};
    String[] width={"N", "S"};
    String[] height={"W", "E"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Spinner refreshingSpinner = (Spinner) findViewById(R.id.refreshingSpinner);
        refreshingSpinner.setOnItemSelectedListener(this);
        Spinner widthSpinner = (Spinner) findViewById(R.id.widthSpinner);
        widthSpinner.setOnItemSelectedListener(this);
        Spinner heightSpinner = (Spinner) findViewById(R.id.heightSpinner);
        heightSpinner.setOnItemSelectedListener(this);

        ArrayAdapter refreshingSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,minutes);
        refreshingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refreshingSpinner.setAdapter(refreshingSpinnerAdapter);

        ArrayAdapter widthSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,width);
        widthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        widthSpinner.setAdapter(widthSpinnerAdapter);

        ArrayAdapter heightSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,height);
        heightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightSpinnerAdapter);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText latitudeText = (EditText) findViewById(R.id.widthEditText);
                String latitude = latitudeText.getText().toString();
                EditText longitudeText = (EditText) findViewById(R.id.heightEditText);
                String longitude = longitudeText.getText().toString();
                TextView minTextView = (TextView) findViewById(R.id.minTextEdit);
                String sleepTime = minTextView.getText().toString();

                Intent startIntent = new Intent(getApplicationContext(), AstroActivity.class);
                startIntent.putExtra("latitude", latitude);
                startIntent.putExtra("longitude", longitude);
                startIntent.putExtra("sleepTime", sleepTime);
                startActivity(startIntent);
            }
        });
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (arg0.getId()) {
            case R.id.refreshingSpinner:
                TextView minTextView = (TextView) findViewById(R.id.minTextEdit);
                minTextView.setText(minutes[position]);
                break;
            case R.id.widthSpinner:
                TextView widthTextView = (TextView) findViewById(R.id.widthText);
                widthTextView.setText(width[position]);
                break;
            case R.id.heightSpinner:
                TextView heightTextView = (TextView) findViewById(R.id.heightText);
                heightTextView.setText(height[position]);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
}
