package util;

import android.provider.Telephony;
import android.renderscript.Float3;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 10/10/2017.
 */

public class Utility {
    public static final String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String iconUrl = "http://openweathermap.org/img/w/";
    public static final String apikey = "ca699fb7555c6aab1ef0f0ea8354a4f9";


    public static JSONObject getObject(String key, JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(key);
    }


    public static String getString(String key, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(key);

    }

    public static float getFloat(String key, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(key);

    }


    public static double getDouble(String key, JSONObject jsonObject) throws JSONException {
        return jsonObject.getDouble(key);

    }

    public static int getInteger(String key, JSONObject jsonObject) throws JSONException {

        return jsonObject.getInt(key);
    }

}
