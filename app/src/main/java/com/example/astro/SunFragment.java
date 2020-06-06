package com.example.astro;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;

public class SunFragment extends Fragment {
    int year, month, day, hour, minute, second;
    double latitude, longitude;
    double sleepTime;

    public AstroCalculator astroCalculator;
    private Handler handlerUpdateData = new Handler();
    private Handler handlerTime = new Handler();
    //private volatile Thread clockThread;
    //private volatile Thread refreshDataThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sun, container, false);
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
        }
    };

    private final Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            TextView clock = (TextView) getView().findViewById(R.id.timeText);
            long clockTime = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String dateString = simpleDateFormat.format(clockTime);
            clock.setText(dateString);
            handlerTime.postDelayed(this, 1000);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        handlerUpdateData.removeCallbacks(updateDataThread);
        handlerTime.removeCallbacks(updateTime);
    }

    @Override
    public void onStart() {
        super.onStart();
        showParameters(astroCalculator);
        handlerUpdateData.postDelayed(updateDataThread, (long)sleepTime);
        handlerTime.postDelayed(updateTime, 1);
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
        showSunrise(astroCalculator);
        showSunset(astroCalculator);
        showTwilightMorning(astroCalculator);
        showTwilightEvening(astroCalculator);
        showAzimuthRise(astroCalculator);
        showAzimuthSet(astroCalculator);
    }

    public void showSunrise(AstroCalculator astroCalculator){
        AstroDateTime sunrise = astroCalculator.getSunInfo().getSunrise();
        String sunriseString = sunrise.toString();
        String sunriseEnd = sunriseString.substring(11,19);

        TextView sunriseTextView = (TextView) getView().findViewById(R.id.sunriseTimeText);
        sunriseTextView.setText(sunriseEnd);

    }

    public void showSunset(AstroCalculator astroCalculator){
        AstroDateTime sunset = astroCalculator.getSunInfo().getSunset();
        String sunsetString = sunset.toString();
        String sunsetEnd = sunsetString.substring(11,19);

        TextView sunsetTimeTextView = (TextView) getView().findViewById(R.id.sunsetTimeText);
        sunsetTimeTextView.setText(sunsetEnd);

    }

    public void showAzimuthRise(AstroCalculator astroCalculator){
        double azimuthRise = astroCalculator.getSunInfo().getAzimuthRise();
        azimuthRise = Math.round(azimuthRise * 100);
        String azimuthRiseString = Double.toString(azimuthRise/100);

        TextView azimuthRiseTextView = (TextView) getView().findViewById(R.id.sunriseAzymutText);
        azimuthRiseTextView.setText(azimuthRiseString);

    }

    public void showAzimuthSet(AstroCalculator astroCalculator){
        double azimuthSet = astroCalculator.getSunInfo().getAzimuthSet();
        azimuthSet = Math.round(azimuthSet * 100);
        String azimuthSetString = Double.toString(azimuthSet/100);

        TextView azimuthSetTextView = (TextView) getView().findViewById(R.id.sunsetAzymutText);
        azimuthSetTextView.setText(azimuthSetString);

    }

    public void showTwilightMorning(AstroCalculator astroCalculator){
        AstroDateTime twilightMorning = astroCalculator.getSunInfo().getTwilightMorning();
        String twilightMorningString = twilightMorning.toString();
        String twilightMorningEnd = twilightMorningString.substring(11,19);

        TextView twilightMorningTextView = (TextView) getView().findViewById(R.id.dawnText);
        twilightMorningTextView.setText(twilightMorningEnd);

    }

    public void showTwilightEvening(AstroCalculator astroCalculator){
        AstroDateTime twilightEvening = astroCalculator.getSunInfo().getTwilightEvening();
        String twilightEveningString = twilightEvening.toString();
        String twilightEveningEnd = twilightEveningString.substring(11,19);

        TextView twilightEveningTextView = (TextView) getView().findViewById(R.id.duskText);
        twilightEveningTextView.setText(twilightEveningEnd);

    }
}

