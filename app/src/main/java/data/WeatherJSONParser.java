package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import util.Utility;

/**
 * Created by Sam on 10/11/2017.
 */

public class WeatherJSONParser {
    public static Weather getWeather(String data){
      Weather weather = new Weather();
        try {
            JSONObject rootObject = new JSONObject(data);
            Place place = new Place();
            JSONObject coordObject = Utility.getObject("coord",rootObject);
            place.setLatitude(Utility.getFloat("lat",coordObject));
            place.setLongitude(Utility.getFloat("lon",coordObject));


            JSONObject sysObject = Utility.getObject("sys",rootObject);
            place.setCountry(Utility.getString("country",sysObject));
            place.setCity(Utility.getString("name",rootObject));
            place.setSunrise(Utility.getInteger("sunrise",sysObject));
            place.setSunset(Utility.getInteger("sunset",sysObject));
            place.setLastupdate(Utility.getInteger("dt",rootObject));
            weather.place = place;


            JSONArray jsonArray = rootObject.getJSONArray("weather"); //Getting the Weather jsonArray from rootObject
            JSONObject weatherObj = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utility.getInteger("id",weatherObj));
            weather.currentCondition.setDescription(Utility.getString("description",weatherObj));
            weather.currentCondition.setIcon(Utility.getString("icon",weatherObj));
            weather.currentCondition.setCondition(Utility.getString("main",weatherObj));

            JSONObject mainObj = Utility.getObject("main",rootObject);
            weather.currentCondition.setHumidity(Utility.getInteger("humidity",mainObj));
            weather.currentCondition.setPressure(Utility.getInteger("pressure",mainObj));
            weather.currentCondition.setMinTemp(Utility.getInteger("temp_min",mainObj));
            weather.currentCondition.setMaxTemp(Utility.getInteger("temp_max",mainObj));
            weather.currentCondition.setTemprature(Utility.getInteger("temp",mainObj));

            JSONObject windObj = rootObject.getJSONObject("wind");
            weather.wind.setSpeed(Utility.getFloat("speed",windObj));
            weather.wind.setDeg(Utility.getFloat("deg",windObj));


            JSONObject cloudsObj = rootObject.getJSONObject("clouds");
            weather.clouds.setPercipitation(Utility.getInteger("all",cloudsObj));


            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
