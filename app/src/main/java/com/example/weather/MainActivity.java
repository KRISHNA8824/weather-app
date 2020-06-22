package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView result_weather;
    String rslt;
    public void button(View view){
        String a = "";
        String cityname = city.getText().toString();

        DownloadTask task = new DownloadTask();
        try {

            String xyz = URLEncoder.encode(cityname,"UTF-8");
            //a = "https://api.openweathermap.org/data/2.5/weather?q=" + xyz + "&appid=0aed008483f0af3da757a98098c9460f";
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + xyz + "&appid=0aed008483f0af3da757a98098c9460f");
            Log.i("URL","https://api.openweathermap.org/data/2.5/weather?q=" + xyz + "&appid=0aed008483f0af3da757a98098c9460f");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //result_weather.setText(a);
        //Log.i("cityname",a);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = (EditText) findViewById(R.id.city);
        result_weather = (TextView) findViewById(R.id.result);
        //DownloadTask task = new DownloadTask();
        //task.execute("https://api.openweathermap.org/data/2.5/weather?q=lucknow&appid=0aed008483f0af3da757a98098c9460f");
        //result_weather.setText();


    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while(data != -1){
                    result += (char) data;
                    data = reader.read();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            String message = "";
            String a = "";
            String b = "";
            String c = "";
            String d = "";
            String A = "";
            //Log.i("cheeck", result);
            super.onPostExecute(result);
            try {
                JSONObject jsonObject;
                    jsonObject = new JSONObject(result);
                
                    JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = jsonObject.getJSONObject("main");
                
                    a = main.getString("temp_min");
                    b = main.getString("temp_max");
                    c = "Humidity: " + main.getString("humidity");
                    d = "Pressure: " + main.getString("pressure") + " Pa";

                    double a1 = Double.parseDouble(a);
                    double b1 = Double.parseDouble(b);
                
                    a1 = a1 - 273.15;
                    b1 = b1 - 273.15;
                
                    String a2 = String.valueOf(a1);
                    String a3 = "min. temp: " + a2 + "°C";
                    String b2 = String.valueOf(b1);
                    String b3 = "max temp: " + b2 + "°C";
                    if (a3!="") {
                        message = "\r\r\r" + A + "\n" + a3 + "\n" + b3 + "\n" + c + "\n" + d;
                    }
                    if (message != "") {
                        result_weather.setText(message);
                    }

                /*Log.i("check point 1", "ok");
                JSONObject jsonObject = new JSONObject(result);
                Log.i("check point 2", "ok");
                String weatherInfo = jsonObject.getString("main");
                Log.i("check point 3", "ok");
                //String temp  = jsonObject.getString("main");
                JSONArray arr = new JSONArray(weatherInfo);
                Log.i("check point 4", "ok");
                for(int i = 0; i < arr.length(); i++){
                    String main = "";
                    String description = "";
                    JSONObject jsonPart = arr.getJSONObject(i);
                    Log.i("main",jsonPart.getString("temp"));
                    main = jsonPart.getString("temp");
                    Log.i("description",jsonPart.getString("humidity"));
                    description = jsonPart.getString("humidity");
                    if(main != "" && description != ""){
                        message += main + " : " + description /*+ "\r\n";
                    }
                }
                Log.i("check point 5", "ok");
                if(message != ""){
                    result_weather.setText(message);
                }
                Log.i("weather",message);
                Log.i("dfs","dfs");*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
