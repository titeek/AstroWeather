package com.example.astro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

public class AstroActivity extends FragmentActivity {

    int year, month, day, hour, minute, second;
    double latitude, longitude;
    double sleepTime;

    private volatile Thread clockThread;
    private volatile Thread refreshDataThread;
    AstroCalculator astroCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_activity);
        setData();

        String latitudeString = getIntent().getStringExtra("latitude");
        latitude = Double.parseDouble(latitudeString);

        String longitudeString = getIntent().getStringExtra("longitude");
        longitude = Double.parseDouble(longitudeString);

        String sleepTimeString = getIntent().getStringExtra("sleepTime");
        sleepTime = Double.parseDouble(sleepTimeString);
        sleepTime = sleepTime * 60 * 1000;

        AstroCalculator.Location location = new AstroCalculator.Location(latitude, longitude);
        AstroDateTime astroDateTime = new AstroDateTime(year, month, day, hour, minute, second, 2, false);

        astroCalculator = new AstroCalculator(astroDateTime, location);

    }

    protected void onStart() {
        super.onStart();
        startClockThread();
        startRefreshDataThread(astroCalculator);
    }

    protected void onStop() {
        super.onStop();
        stopClockThread();
        stopRefreshDataThread();
    }

    private void startClockThread() {
        clockThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView clock = (TextView) findViewById(R.id.timeText);
                                long clockTime = System.currentTimeMillis();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                String dateString = simpleDateFormat.format(clockTime);
                                clock.setText(dateString);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        clockThread.start();
    }

    private void stopClockThread() {
        clockThread = null;
    }

    private void startRefreshDataThread(final AstroCalculator astroCalculator) {
        refreshDataThread = new Thread() { //odswiezanie co 15 min danych
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showParameters(astroCalculator);
                            }
                        });
                        Thread.sleep((long)sleepTime);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        refreshDataThread.start();
    }

    private void stopRefreshDataThread() {
        refreshDataThread = null;
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
        month = 5;
        day = 31;
        hour = 22;
        minute = 0;
        second = 0;
    }


    public void showParameters(AstroCalculator astroCalculator) {
        showSunrise(astroCalculator);
        showSunset(astroCalculator);
        showTwilightMorning(astroCalculator);
        showTwilightEvening(astroCalculator);
        showAzimuthRise(astroCalculator);
        showAzimuthSet(astroCalculator);

        showMoonrise(astroCalculator);
        showMoonset(astroCalculator);
        showIllumination(astroCalculator);
        showAge(astroCalculator);
        showFullMoon(astroCalculator);
        showNewMoon(astroCalculator);
        showCoordinates(astroCalculator);
        //showDate();
    }

    /*public void showDate(){
        String date = astroDateTime.toString();

        TextView x = (TextView) findViewById(R.id.sunriseTimeText);
        x.setText(date);
    }*/

    public void showCoordinates(AstroCalculator astroCalculator){
        double latitudeD = astroCalculator.getLocation().getLatitude();
        double longitudeD = astroCalculator.getLocation().getLongitude();

        String latitudeString = Double.toString(latitudeD);
        String longitudeString = Double.toString(longitudeD);

        TextView coordText = (TextView) findViewById(R.id.gpsText);
        coordText.setText(latitudeString + "° " +  longitudeString + "° ");

    }

    public void showSunrise(AstroCalculator astroCalculator){
        AstroDateTime sunrise = astroCalculator.getSunInfo().getSunrise();
        String sunriseString = sunrise.toString();
        String sunriseEnd = sunriseString.substring(11,19);

        TextView sunriseTextView = (TextView) findViewById(R.id.sunriseTimeText);
        sunriseTextView.setText(sunriseEnd);

    }

    public void showSunset(AstroCalculator astroCalculator){
        AstroDateTime sunset = astroCalculator.getSunInfo().getSunset();
        String sunsetString = sunset.toString();
        String sunsetEnd = sunsetString.substring(11,19);

        TextView sunsetTimeTextView = (TextView) findViewById(R.id.sunsetTimeText);
        sunsetTimeTextView.setText(sunsetEnd);

    }

    public void showAzimuthRise(AstroCalculator astroCalculator){
        double azimuthRise = astroCalculator.getSunInfo().getAzimuthRise();
        String azimuthRiseString = Double.toString(azimuthRise);

        TextView azimuthRiseTextView = (TextView) findViewById(R.id.sunriseAzymutText);
        azimuthRiseTextView.setText(azimuthRiseString);

    }

    public void showAzimuthSet(AstroCalculator astroCalculator){
        double azimuthSet = astroCalculator.getSunInfo().getAzimuthSet();
        String azimuthSetString = Double.toString(azimuthSet);

        TextView azimuthSetTextView = (TextView) findViewById(R.id.sunsetAzymutText);
        azimuthSetTextView.setText(azimuthSetString);

    }

    public void showTwilightMorning(AstroCalculator astroCalculator){
        AstroDateTime twilightMorning = astroCalculator.getSunInfo().getTwilightMorning();
        String twilightMorningString = twilightMorning.toString();
        String twilightMorningEnd = twilightMorningString.substring(11,19);

        TextView twilightMorningTextView = (TextView) findViewById(R.id.dawnText);
        twilightMorningTextView.setText(twilightMorningEnd);

    }

    public void showTwilightEvening(AstroCalculator astroCalculator){
        AstroDateTime twilightEvening = astroCalculator.getSunInfo().getTwilightEvening();
        String twilightEveningString = twilightEvening.toString();
        String twilightEveningEnd = twilightEveningString.substring(11,19);

        TextView twilightEveningTextView = (TextView) findViewById(R.id.duskText);
        twilightEveningTextView.setText(twilightEveningEnd);

    }

    public void showMoonrise(AstroCalculator astroCalculator){
        AstroDateTime moonrise = astroCalculator.getMoonInfo().getMoonrise();
        String moonriseString = moonrise.toString();
        String moonriseEnd = moonriseString.substring(11,19);

        TextView moonriseTextView = (TextView) findViewById(R.id.moonriseText);
        moonriseTextView.setText(moonriseEnd);

    }

    public void showMoonset(AstroCalculator astroCalculator){
        AstroDateTime moonset = astroCalculator.getMoonInfo().getMoonset();
        String moonsetString = moonset.toString();
        String moonsetEnd = moonsetString.substring(11,19);

        TextView moonsetTimeTextView = (TextView) findViewById(R.id.moonsetText);
        moonsetTimeTextView.setText(moonsetEnd);

    }

    public void showIllumination(AstroCalculator astroCalculator){
        double illumination = astroCalculator.getMoonInfo().getIllumination();
        illumination = illumination * 100;
        String illuminationString = Double.toString(illumination);

        TextView azimuthSetTextView = (TextView) findViewById(R.id.phaseText);
        azimuthSetTextView.setText(illuminationString);
    }

    public void showAge(AstroCalculator astroCalculator){
        double age = astroCalculator.getMoonInfo().getAge();
        String ageString = Double.toString(age);

        TextView ageTextView = (TextView) findViewById(R.id.dayText);
        ageTextView.setText(ageString);
    }

    public void showFullMoon(AstroCalculator astroCalculator){
        AstroDateTime fullMoon = astroCalculator.getMoonInfo().getNextFullMoon();
        String fullMoonString = fullMoon.toString();
        String fullMoonEnd = fullMoonString.substring(0,5);

        TextView fullMoonTextView = (TextView) findViewById(R.id.fullMoonText);
        fullMoonTextView.setText(fullMoonEnd);
    }

    public void showNewMoon(AstroCalculator astroCalculator){
        AstroDateTime newMoon = astroCalculator.getMoonInfo().getNextNewMoon();
        String newMoonString = newMoon.toString();
        String newMoonSEnd = newMoonString.substring(0,5);

        TextView newMoonTextView = (TextView) findViewById(R.id.nearestText);
        newMoonTextView.setText(newMoonSEnd);
    }

}
