package com.example.han.coaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by han on 2015-11-24.
 */
public class Weather extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("Weather.a"))
        {
            WWW w = new WWW();
            w.execute(intent.getDoubleExtra("lati",0),intent.getDoubleExtra("lon",0));
        }else if(intent.getAction().equals("Detailaddr")){
            detailAddr d = new detailAddr();
            d.execute(intent.getDoubleExtra("lati",0),intent.getDoubleExtra("lon",0));
        }



    }
    private class WWW extends AsyncTask<Double,Void,String> {
        @Override
        protected String doInBackground(Double... params) {
            double lati , lon;
            lati = params[0];
            lon = params[1];
            try {
                String UUU = "http://api.openweathermap.org/data/2.5/weather?lat="+lati+"&lon="+lon+"&appid=228771918de505e0d9cf551a5a322259";
                URL url = new URL(UUU);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(4000 /* milliseconds */);
                conn.setConnectTimeout(7000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                Scanner s = new Scanner(is);
                String str = "";
                while (s.hasNext())
                    str += s.nextLine();
                is.close();
                return str;
            }
            catch(Exception t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();

                Log.e("aaaa", exceptionAsStrting);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject searchresult = new JSONObject(s);
                JSONArray results = searchresult.getJSONArray("weather");
                JSONObject json = new JSONObject();
                json = results.getJSONObject(0);
                int id = json.getInt("id");
                staticMerge.idTotemp(id);
            }catch (Exception e){
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("aaaa", exceptionAsStrting);
            }
            Log.i("aaaa",staticMerge.temp + "- now weather");
            super.onPostExecute(s);
        }
    }

    private class detailAddr extends AsyncTask<Double,Void,String>{
        @Override
        protected String doInBackground(Double... params) {
            Log.i("aaaa", "----------------------------- 마임1" );
            double lati , lon;
            lati = params[0];
            lon = params[1];
            try {
                String UUU = "https://apis.daum.net/local/geo/coord2detailaddr?apikey=704c8f7bbfab6a07aa2fd61bf544d9f7&x="+lon+"&y="+lati+"&inputCoordSystem=WGS84&output=json";
                URL url = new URL(UUU);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(4000 /* milliseconds */);
                conn.setConnectTimeout(7000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                Scanner s = new Scanner(is);
                String str = "";
                while (s.hasNext())
                    str += s.nextLine();
                is.close();
                Log.i("json:",str);
                return str;
            }
            catch(Exception t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();

                Log.e("aaaa", exceptionAsStrting);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String temp[];
            String temp1;
            String dong="";
            String bunji="";
            try{
                JSONObject searchresult = new JSONObject(s);

                JSONObject json = new JSONObject();
                temp1 = searchresult.getString("region");
                json = searchresult.getJSONObject("old");
                bunji = json.getString("bunji");
                if(!json.getString("ho").equals("")) {
                    bunji += "-"+json.getString("ho");
                }
                temp = temp1.split(" ");
                dong = temp[2];


            }catch (Exception e){
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("aaaa", exceptionAsStrting);
            }
            Log.i("aaaa",dong+","+bunji);
            staticMerge.dong = dong;
            staticMerge.bunji = bunji;
            //동 이름 result
            super.onPostExecute(s);
        }
    }

}