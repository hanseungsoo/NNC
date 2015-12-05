package com.example.han.coaching;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by han on 2015-11-24.
 */
public class foodDbJson extends AsyncTask<String,Void,Void> {
    @Override
    protected void onPostExecute(Void aVoid) {

        DBHandler dh = DBHandler.open(MainActivity.mContext);
        dh.selectfood(staticMerge.dong);

        try{
            LocationManager locationManager = (LocationManager) MainActivity.mContext.getSystemService(Context.LOCATION_SERVICE);
            int radius = 1000;
            int page = 1;
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            if(!(staticMerge.foodAnni.get(0).equals("empty"))) {
                Searcher searcher1_1,searcher1_2,searcher1_3;
                switch (staticMerge.foodAnni.size()){
                    case 1:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;
                    case 2:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        break;
                    default:
                        searcher1_1 = new Searcher();
                        searcher1_1.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(0), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(0)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘은";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(0, item);
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                } else {
                                    MainActivity.ThemaItem.set(0, itemList.get(0));
                                    MainActivity.ThemaItem.set(1, item2);
                                    MainActivity.ThemaItem.set(2, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_2 = new Searcher();
                        searcher1_2.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(1), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(1)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";
                                Item item2 = new Item();
                                item2.title = "오늘에 맞는 ";
                                item2.category = "";
                                item2.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item2.address = "추천 주소가 없습니다.";
                                item2.phone = "기념일이 없습니다.";
                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(1, item);
                                    MainActivity.ThemaItem.set(2, item2);
                                } else {
                                    MainActivity.ThemaItem.set(1, itemList.get(0));
                                    MainActivity.ThemaItem.set(2, item2);
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                        searcher1_3 = new Searcher();
                        searcher1_3.searchKeyword(MainActivity.mContext, staticMerge.foodAnni.get(2), latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                            @Override
                            public void onSuccess(List<Item> itemList) {
                                Item item = new Item();
                                item.title = "오늘은 "+staticMerge.foodAnni.get(2)+"을 먹는 날입니다만";
                                item.category = "";
                                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                                item.address = "추천 주소가 없습니다.";
                                item.phone = "주변에 맞는 음식점이 없습니다.";

                                if (itemList.size() == 0) {
                                    MainActivity.ThemaItem.set(2, item);
                                } else {
                                    MainActivity.ThemaItem.set(2, itemList.get(0));
                                }
                            }

                            @Override
                            public void onFail() {
                            }
                        });

                }
            }else {
                Item item = new Item();
                item.title = "오늘에 해당하는 ";
                item.category = " ";
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                item.address = "추천 주소가 없습니다.";
                item.phone = "기념일이 없습니다.";
                MainActivity.ThemaItem.set(0, item);
                MainActivity.ThemaItem.set(1, item);
                MainActivity.ThemaItem.set(2, item);
            }
            Log.i("aaaa", "aaaaaaaaaaaaaaaaa2" + staticMerge.finish_food[0]);

            if(staticMerge.finish_food[0].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(3, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.finish_food[0], latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){
                            Item item = new Item();
                            item.title = "맞춤";
                            item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                            item.category = "음식점이 없습니다.";
                            item.address = "추천 주소가 없습니다.";
                            item.phone = "추천할만한";
                            MainActivity.ThemaItem.set(3, item);
                        }else{

                            MainActivity.ThemaItem.set(3, itemList.get(0));

                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }

            if(staticMerge.finish_food[1].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(4, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.finish_food[1], latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){
                            Item item = new Item();
                            item.title = "맞춤";
                            item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                            item.category = "음식점이 없습니다.";
                            item.address = "추천 주소가 없습니다.";
                            item.phone = "추천할만한";
                            MainActivity.ThemaItem.set(4, item);
                        }else{

                            MainActivity.ThemaItem.set(4, itemList.get(0));

                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }
            if(staticMerge.finish_food[2].equals("empty")){
                Item item = new Item();
                item.title = "맞춤";
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                item.category = "음식점이 없습니다.";
                item.address = "추천 주소가 없습니다.";
                item.phone = "추천할만한";
                MainActivity.ThemaItem.set(5, item);
            }else{
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(MainActivity.mContext, staticMerge.finish_food[2], latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        if(itemList.size() == 0){
                            Item item = new Item();
                            item.title = "맞춤";
                            item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                            item.category = "음식점이 없습니다.";
                            item.address = "추천 주소가 없습니다.";
                            item.phone = "추천할만한";
                            MainActivity.ThemaItem.set(5, item);
                        }else{

                            MainActivity.ThemaItem.set(5, itemList.get(0));

                        }
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }

            Searcher searcher3 = new Searcher();
            searcher3.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, page, 2, "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                @Override
                public void onSuccess(List<Item> itemList) {
                    Item item = new Item();
                    item.title = "가까운 거리에";
                    item.category = "음식점이 없습니다.";
                    item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                    item.address = "추천 주소가 없습니다.";
                    item.phone = "추천할만한";
                    if(itemList.size() == 0){

                        MainActivity.ThemaItem.set(6, item);
                        MainActivity.ThemaItem.set(7, item);
                        MainActivity.ThemaItem.set(8, item);
                    }else{
                        switch (itemList.size()){
                            case 2:
                                MainActivity.ThemaItem.set(6, itemList.get(0));
                                MainActivity.ThemaItem.set(7, itemList.get(1));
                                MainActivity.ThemaItem.set(8, item);
                                break;
                            case 1:
                                MainActivity.ThemaItem.set(6, itemList.get(0));
                                MainActivity.ThemaItem.set(7, item);
                                MainActivity.ThemaItem.set(8, item);
                                break;
                            default:
                                MainActivity.ThemaItem.set(6, itemList.get(0));
                                MainActivity.ThemaItem.set(7, itemList.get(1));
                                MainActivity.ThemaItem.set(8, itemList.get(2));
                                break;
                        }
                    }
                }

                @Override
                public void onFail() {
                }
            });
            Searcher searcher4 = new Searcher();
            searcher4.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "9db6272582177f1d7b0643e35e1993e9", new OnFinishSearchListener() {
                @Override
                public void onSuccess(List<Item> itemList) {
                    Item item = new Item();
                    item.title = "무작위로";
                    item.category = "음식점이 없습니다.";
                    item.address = "추천 주소가 없습니다.";
                    item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
                    item.phone = "추천할만한";
                    if (itemList.size() == 0) {

                        MainActivity.ThemaItem.set(9, item);
                        MainActivity.ThemaItem.set(10, item);
                        MainActivity.ThemaItem.set(11, item);
                        SaveData svData = new SaveData(MainActivity.mContext);
                        svData.save("SharedFood");
                        MainActivity.ViewInt = 1;
                        staticMerge.timer = true;
                        /*MainActivity.mHandler.sendEmptyMessage(1);*/
                        registerAlarm rA = new registerAlarm(MainActivity.mContext);
                        rA.registerNews(60 * 60);


                    } else {

                        switch (itemList.size()) {
                            case 2:
                                MainActivity.ThemaItem.set(9, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(10, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(11, item);
                                break;
                            case 1:
                                MainActivity.ThemaItem.set(9, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(10, item);
                                MainActivity.ThemaItem.set(11, item);
                                break;
                            default:
                                MainActivity.ThemaItem.set(9, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(10, itemList.get((int) (Math.random() * 15)));
                                MainActivity.ThemaItem.set(11, itemList.get((int) (Math.random() * 15)));
                                break;
                        }
                        SaveData svData = new SaveData(MainActivity.mContext);
                        svData.save("SharedFood");

                        MainActivity.ViewInt = 1;
                        noonWidget.contentValue = "content1";
                        MainActivity.mmmm();
                        staticMerge.timer = true;
                        /*MainActivity.mHandler.sendEmptyMessage(1);*/
                        registerAlarm rA = new registerAlarm(MainActivity.mContext);
                        rA.registerNews(60 * 60);

                    }

                }

                @Override
                public void onFail() {
                }
            });
        }catch (Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }


        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... params) {
        ArrayList<String> foodDB = new ArrayList<String>();

        try {
            URL url = new URL("http://222.116.135.76:8080/Noon/createJson.jsp?" + params[0]);
            Log.i("param0", "" + params[0]);
            URLConnection conn = (HttpURLConnection) url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setChunkedStreamingMode(0);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
            //InputStream in = http.getInputStream();

            StringBuffer sb = new StringBuffer();
            String myResult = "";
            try {
                int chr;
                while ((chr = in.read()) != -1) {
                    sb.append((char) chr);
                }
                myResult = sb.toString();
                Log.i("param0", myResult);
            } finally {
                in.close();
            }
            try {
                JSONObject root = new JSONObject(myResult);
                JSONArray jarr = root.getJSONArray("Food");
                String food_name = "";
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject json = new JSONObject();
                    json = jarr.getJSONObject(i);
                    food_name = json.getString("food_name");
                    foodDB.add(food_name);
                    Log.i("param0", food_name);
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("error", exceptionAsStrting);
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        int month,day;
        month = Integer.parseInt(CurMonthFormat.format(date));
        day = Integer.parseInt(CurDayFormat.format(date));
        DBHandler dh = DBHandler.open(MainActivity.mContext);
        Log.i("cccc",""+month+" "+day);
        ArrayList<Anni> Annis = dh.selectAnniWithWhere(month-1, day);
        String food_nameAnniv = "";

        if(Annis == null){
            try {
                URL url = new URL("http://222.116.135.76:8080/Noon/createJson.jsp?" + params[1]);
                Log.i("param1", "" + params[1]);
                URLConnection conn = (HttpURLConnection) url.openConnection();
                HttpURLConnection http = (HttpURLConnection) conn;

                http.setChunkedStreamingMode(0);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                //InputStream in = http.getInputStream();
                StringBuffer sb = new StringBuffer();
                String myResult = "";
                try {
                    int chr;
                    while ((chr = in.read()) != -1) {
                        sb.append((char) chr);
                    }
                    myResult = sb.toString();
                    Log.i("param1", myResult);
                } finally {
                    in.close();
                }
                try {
                    JSONObject root = new JSONObject(myResult);
                    JSONArray jarr = root.getJSONArray("Food");
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = new JSONObject();
                        json = jarr.getJSONObject(i);
                        food_nameAnniv = json.getString("food_name");
                        Log.i("param1", food_nameAnniv);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }

            if(food_nameAnniv.equals("empty")){
                try {
                    URL url = new URL("http://222.116.135.76:8080/Noon/createJson.jsp?" + params[2]);
                    Log.i("param2", "" + params[2]);
                    URLConnection conn = (HttpURLConnection) url.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    http.setChunkedStreamingMode(0);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");

                    BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                    //InputStream in = http.getInputStream();

                    StringBuffer sb = new StringBuffer();
                    String myResult = "";
                    try {
                        int chr;
                        while ((chr = in.read()) != -1) {
                            sb.append((char) chr);
                        }
                        myResult = sb.toString();
                        Log.i("param2", myResult);
                    } finally {
                        in.close();
                    }
                    try {
                        JSONObject root = new JSONObject(myResult);
                        JSONArray jarr = root.getJSONArray("Food");
                        food_nameAnniv = "";
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject json = new JSONObject();
                            json = jarr.getJSONObject(i);
                            food_nameAnniv = json.getString("food_name");
                            Log.i("param2", food_nameAnniv);
                        }

                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsStrting = sw.toString();
                        Log.e("error", exceptionAsStrting);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }
                if(food_nameAnniv.equals("empty")){

                    staticMerge.finish_food[3]="empty";
                    staticMerge.finish_food[4] ="empty";
                }else{
                    staticMerge.finish_food[3]=food_nameAnniv;
                }
            }else{
                staticMerge.finish_food[3]=food_nameAnniv;
            }
        }else{
            staticMerge.finish_food[3] = "empty";
            staticMerge.finish_food[4] = Annis.get(0).getCate();
        }
        staticMerge.foodAnni.clear();
        if(!staticMerge.finish_food[3].equals("empty")){
            staticMerge.foodAnni.add(staticMerge.finish_food[3]);
        }else if(!staticMerge.finish_food[4].equals("empty")){
            try {

                String query = URLEncoder.encode(staticMerge.finish_food[4], "utf-8");
                URL url2 = new URL("http://222.116.135.76:8080/Noon/createJson.jsp?" + "tbName=noon_food&col=food_name&food_wea="+staticMerge.temp+"&food_ann="+query);
                Log.i("param3","http://222.116.135.76:8080/Noon/createJson.jsp?" + "tbName=noon_food&col=food_name&food_wea="+staticMerge.temp+"&food_ann="+query);
                URLConnection conn2 = (HttpURLConnection) url2.openConnection();
                HttpURLConnection http2 = (HttpURLConnection) conn2;
                ArrayList<String> foodAnni = new ArrayList<>();
                http2.setChunkedStreamingMode(0);
                http2.setDoInput(true);
                http2.setDoOutput(true);
                http2.setRequestMethod("POST");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(http2.getInputStream(), "UTF-8"));
                //InputStream in = http.getInputStream();
                foodAnni.clear();

                StringBuffer sb2 = new StringBuffer();
                String myResult = "";

                try {
                    int chr;
                    while ((chr = in2.read()) != -1) {
                        sb2.append((char) chr);
                    }
                    myResult = sb2.toString();
                    Log.i("param3", myResult);
                } finally {
                    in2.close();
                }
                try {
                    JSONObject root = new JSONObject(myResult);
                    JSONArray jarr = root.getJSONArray("Food");
                    String food_name = "";
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = new JSONObject();
                        json = jarr.getJSONObject(i);
                        food_name = json.getString("food_name");
                        foodAnni.add(food_name);
                        Log.i("param3", food_name);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("error", exceptionAsStrting);
                }
                staticMerge.foodAnni = foodAnni;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsStrting = sw.toString();
                Log.e("error", exceptionAsStrting);
            }
        }else{
            staticMerge.foodAnni.add("empty");
        }



        staticMerge.food = foodDB;
        Log.i("cccc",staticMerge.foodAnni.get(0));
        return null;
    }

}
