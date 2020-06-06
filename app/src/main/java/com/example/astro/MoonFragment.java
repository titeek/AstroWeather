package com.example.astro;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;


public class MoonFragment extends Fragment {

    int year, month, day, hour, minute, second;
    double latitude, longitude;
    double sleepTime;

    public AstroCalculator astroCalculator;
    private Handler handlerUpdateData = new Handler();

    //private volatile Thread clockThread;
    //private volatile Thread refreshDataThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_moon, container, false);
        setData();

        String latitudeString = getActivity().getIntent().getStringExtra("latitude");
        latitude = Double.parseDouble(latitudeString);

        String longitudeString = getActivity().getIntent().getStringExtra("longitude");
        longitude = Double.parseDouble(longitudeString);

        String sleepTimeString = getActivity().getIntent().getStringExtra("sleepTime");
        sleepTime = Double.parseDouble(sleepTimeString);
        sleepTime = sleepTime * 60 * 1000;

        sleepTime = 1000;
        AstroCalculator.Location location = new AstroCalculator.Location(latitude, longitude);
        AstroDateTime astroDateTime = new AstroDateTime(year, month, day, hour, minute, second, 2, false);

        astroCalculator = new AstroCalculator(astroDateTime, location);

        return rootView;
    }

    private final Runnable updateDataThread = new Runnable() {
        @Override
        public void run() {
            showParameters(astroCalculator);
            handlerUpdateData.postDelayed(this, (long)sleepTime);
            //Toast.makeText(getActivity(), "TOAST!",  Toast.LENGTH_LONG).show();
            //longitude++; //sprawdzenie czy sie odswieza
        }
    };



    @Override
    public void onStop() {
        super.onStop();
        handlerUpdateData.removeCallbacks(updateDataThread);
    }

    @Override
    public void onStart() {
        super.onStart();
        showParameters(astroCalculator);
        handlerUpdateData.postDelayed(updateDataThread, (long)sleepTime);
    }

    private void setData() {
        long date = System.currentTimeMillis();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String dateString = simpleDateFormat.format(date);
        year = Integer.parseInt(dateString);

        simpleDateFormat = new SimpleDateFormat("M");
        dateString = simpleDateFormat.format(date);
        month = Integer.parseInt(dateString);

        simpleDateFormat = new SimpleDateFormat("dd");
        dateString = simpleDateFormat.format(date);
        day = Integer.parseInt(dateString);

        simpleDateFormat = new SimpleDateFormat("HH");
        dateString = simpleDateFormat.format(date);
        hour = Integer.parseInt(dateString);

        simpleDateFormat = new SimpleDateFormat("mm");
        dateString = simpleDateFormat.format(date);
        minute = Integer.parseInt(dateString);

        simpleDateFormat = new SimpleDateFormat("ss");
        dateString = simpleDateFormat.format(date);
        second = Integer.parseInt(dateString);

        year = 2020;
        month = 6;
        day = 10;
        hour = 22;
        minute = 0;
        second = 0;
    }

    public void showParameters(AstroCalculator astroCalculator) {
        showMoonrise(astroCalculator);
        showMoonset(astroCalculator);
        showIllumination(astroCalculator);
        showAge(astroCalculator);
        showFullMoon(astroCalculator);
        showNewMoon(astroCalculator);
        showCoordinates(astroCalculator);
    }

    public void showCoordinates(AstroCalculator astroCalculator){
        /*double latitudeD = astroCalculator.getLocation().getLatitude();
        double longitudeD = astroCalculator.getLocation().getLongitude();

        String latitudeString = Double.toString(latitudeD);
        String longitudeString = Double.toString(longitudeD);*/

        String latitudeString = Double.toString(latitude);
        String longitudeString = Double.toString(longitude);

        TextView coordText = (TextView) getView().findViewById(R.id.gpsText);
        coordText.setText(latitudeString + "° " +  longitudeString + "° ");

    }

    public void showMoonrise(AstroCalculator astroCalculator){
        AstroDateTime moonrise = astroCalculator.getMoonInfo().getMoonrise();
        String moonriseString = moonrise.toString();
        String moonriseEnd = moonriseString.substring(11,19);

        TextView moonriseTextView = (TextView) getView().findViewById(R.id.moonriseText);
        moonriseTextView.setText(moonriseEnd);

    }

    public void showMoonset(AstroCalculator astroCalculator){
        AstroDateTime moonset = astroCalculator.getMoonInfo().getMoonset();
        String moonsetString = moonset.toString();
        String moonsetEnd = moonsetString.substring(11,19);

        TextView moonsetTimeTextView = (TextView) getView().findViewById(R.id.moonsetText);
        moonsetTimeTextView.setText(moonsetEnd);

    }

    public void showIllumination(AstroCalculator astroCalculator){
        double illumination = astroCalculator.getMoonInfo().getIllumination();
        illumination = illumination * 100;
        illumination = Math.round(illumination * 100);
        String illuminationString = Double.toString(illumination/100);

        TextView azimuthSetTextView = (TextView) getView().findViewById(R.id.phaseText);
        azimuthSetTextView.setText(illuminationString);
    }

    public void showAge(AstroCalculator astroCalculator){
        double age = astroCalculator.getMoonInfo().getAge();
        age = Math.round(age * 100);
        String ageString = Double.toString(age/100);

        TextView ageTextView = (TextView) getView().findViewById(R.id.dayText);
        ageTextView.setText(ageString);
    }

    public void showFullMoon(AstroCalculator astroCalculator){
        AstroDateTime fullMoon = astroCalculator.getMoonInfo().getNextFullMoon();
        String fullMoonString = fullMoon.toString();
        String fullMoonEnd = fullMoonString.substring(0,5);

        TextView fullMoonTextView = (TextView) getView().findViewById(R.id.fullMoonText);
        fullMoonTextView.setText(fullMoonEnd);
    }

    public void showNewMoon(AstroCalculator astroCalculator){
        AstroDateTime newMoon = astroCalculator.getMoonInfo().getNextNewMoon();
        String newMoonString = newMoon.toString();
        String newMoonSEnd = newMoonString.substring(0,5);

        TextView newMoonTextView = (TextView) getView().findViewById(R.id.nearestText);
        newMoonTextView.setText(newMoonSEnd);
    }


}