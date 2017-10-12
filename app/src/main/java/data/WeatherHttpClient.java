package data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import util.Utility;

/**
 * Created by Sam on 10/11/2017.
 */

public class WeatherHttpClient {
    public String getWeatherData(String place) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {

            //Make http request
            conn = (HttpURLConnection) (new URL(Utility.baseUrl + place+"&APPID="+Utility.apikey)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);//its default value is already true,later check this by commenting the line
            conn.setDoOutput(true);//its default value is already true,later check this by commenting the line
            conn.connect();

            //Read the http response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");

            }
            //closing the streams and disconnecting from net
            inputStream.close();
            bufferedReader.close();
            conn.disconnect();
            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

}
