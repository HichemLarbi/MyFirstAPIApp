package com.example.larbih.myapplication;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larbih.myapplication.data.Channel;
import com.example.larbih.myapplication.data.Condition;
import com.example.larbih.myapplication.data.Item;
import com.example.larbih.myapplication.data.Units;
import com.example.larbih.myapplication.listener.WeatherServiceListener;
import com.example.larbih.myapplication.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceListener {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService weatherService;

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);



        weatherService = new YahooWeatherService(this);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Loading...");
        loadingDialog.show();


        weatherService.refreshWeather("Austin, TX");


    }

    @Override
    public void serviceSuccess(Channel channel) {
                   loadingDialog.hide();

                   Item item = channel.getItem();
                   Condition condition = channel.getItem().getCondition();
                   Units units = channel.getUnits();

            int weatherIconImageResource = getResources().getIdentifier("@drawable/icon_" + channel.getItem().getCondition().getCode(), null, getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(weatherIconImageResource);

            weatherIconImageView.setImageResource(weatherIconImageResource);
            temperatureTextView.setText(item.getCondition().getTemperature()+ "\u00B0" + channel.getUnits().getTemperature());
            conditionTextView.setText(condition.getDescription());
            locationTextView.setText(channel.getLocation());

        }

        @Override
        public void serviceFailure(Exception exception) {
            loadingDialog.hide();
            Toast.makeText(this,exception.getMessage(), Toast.LENGTH_LONG).show();
        }
}
