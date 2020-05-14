package com.example.astro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Options extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] minutes={"15","30","45","60","120"};
    String[] width={"N", "S"};
    String[] height={"W", "E"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

//Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner refreshingSpinner = (Spinner) findViewById(R.id.refreshingSpinner);
        refreshingSpinner.setOnItemSelectedListener(this);
        Spinner widthSpinner = (Spinner) findViewById(R.id.widthSpinner);
        widthSpinner.setOnItemSelectedListener(this);
        Spinner heightSpinner = (Spinner) findViewById(R.id.heightSpinner);
        heightSpinner.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter refreshingSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,minutes);
        refreshingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refreshingSpinner.setAdapter(refreshingSpinnerAdapter);

        ArrayAdapter widthSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,width);
        widthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        widthSpinner.setAdapter(widthSpinnerAdapter);

        ArrayAdapter heightSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,height);
        heightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightSpinnerAdapter);
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
