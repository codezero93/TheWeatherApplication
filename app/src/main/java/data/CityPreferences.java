package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Sam on 10/12/2017.
 */

public class CityPreferences {
    SharedPreferences prefs;

    public CityPreferences(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity(){
        return prefs.getString("city","Karachi,PK");

    }

    public void setCity(String city){
        prefs.edit().putString("city",city).commit();

    }
}
