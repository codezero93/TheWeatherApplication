package com.vortex.sam.theweatherapplication;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.CityPreferences;
import data.WeatherHttpClient;
import data.WeatherJSONParser;
import model.Weather;
import util.Utility;

public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView wind;
    private TextView pressure;
    private TextView sunrise;
    private TextView sunset;

    private TextView updated;
    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityName = (TextView) findViewById(R.id.cityText);
        temp = (TextView) findViewById(R.id.tempText);
        iconView = (ImageView) findViewById(R.id.thumbIcon);
        humidity = (TextView) findViewById(R.id.humidityText);
        pressure = (TextView) findViewById(R.id.pressureText);
        sunrise = (TextView) findViewById(R.id.sunriseText);
        sunset = (TextView) findViewById(R.id.sunsetText);
        description = (TextView) findViewById(R.id.cloudText);
        wind = (TextView) findViewById(R.id.windText);
        updated = (TextView) findViewById(R.id.updateText);

        CityPreferences cityPreferences = new CityPreferences(MainActivity.this);


        showWeatherData(cityPreferences.getCity());
        new DownloadImageTask().execute(weather.iconData);
    }

    public void showWeatherData(String city) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(city);
    }


    private class WeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            weather = WeatherJSONParser.getWeather(data);
            weather.iconData = weather.currentCondition.getIcon();


            return weather;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            DateFormat dateFormat = DateFormat.getTimeInstance();
            String sunriseDate = dateFormat.format(new Date(weather.place.getSunrise()));
            String sunsetDate = dateFormat.format(new Date(weather.place.getSunset()));
            String lastupdateDate = dateFormat.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");


            String weatherFormat = decimalFormat.format(((weather.currentCondition.getTemprature()) - 273.15));

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + weatherFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText(("Pressure: " + weather.currentCondition.getPressure() + "hPa"));
            wind.setText("Wind: " + weather.wind.getSpeed() + " mps");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Last Updated: " + lastupdateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "("
                    + weather.currentCondition.getDescription() + ")");
        }


    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                //return BitmapFactory.decodeStream(new URL(Utility.iconUrl+params[0]+".png").openConnection().getInputStream());
                return BitmapFactory.decodeStream(new URL("http://footage.framepool.com/shotimg/772048114-spessart-natural-park-sunrise-nature-reserve-haze.jpg").openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);
        }


    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText editText = new EditText(MainActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint("Karachi,PK");
        builder.setView(editText);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreferences cityPreferences = new CityPreferences(MainActivity.this);
                cityPreferences.setCity(editText.getText().toString());
                String ncity = cityPreferences.getCity();
                showWeatherData(ncity);
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeCity) {
            showInputDialog();

        }

        return super.onOptionsItemSelected(item);
    }
}
