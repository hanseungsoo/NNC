package com.example.han.coaching;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 2015-11-25.
 */
public class SaveData {
    Context context;
    SharedPreferences SharedData;
    public SaveData(Context c){
        context = c;
    }
    public void save(String name){
        SharedData = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedData.edit();
        if(name.equals("SharedNews"))
        {
            if(MainActivity.NewsNews.size()>0)
            {
                List<NewsItem> news = MainActivity.NewsNews;
                Log.i("aaaa","1111111111111111111111111111"+news.get(0).getTitle());
                Gson gson = new Gson();
                String jsonNews = gson.toJson(news);
                editor.putString("NewsData",jsonNews);
                editor.commit();
            }

        }else if(name.equals("SharedFood")) {
            if(MainActivity.ThemaItem.size()>0)
            {
                List<Item> food = MainActivity.ThemaItem;
                Gson gson = new Gson();
                String jsonFood = gson.toJson(food);
                editor.putString("FoodData", jsonFood);
                editor.commit();
            }

        }
    }
    public ArrayList<NewsItem> getNews(String name){
        SharedData = context.getSharedPreferences(name, context.MODE_PRIVATE);
        String jsonNews = SharedData.getString("NewsData", null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<NewsItem>>(){}.getType();
        List<NewsItem> newsList = gson.fromJson(jsonNews, type);
        Log.i("aaaa","0000000000000000000000000000"+newsList.get(0).getTitle());
        return (ArrayList<NewsItem>)newsList;
    }

    public ArrayList<Item> getFood(String name){
        SharedData = context.getSharedPreferences(name, context.MODE_PRIVATE);
        String jsonfood = SharedData.getString("FoodData", null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>(){}.getType();
        List<Item> foodList = gson.fromJson(jsonfood, type);
        return (ArrayList<Item>)foodList;
    }
    public boolean isNews(){
        SharedData = context.getSharedPreferences("SharedNews", context.MODE_PRIVATE);
        if(SharedData.contains("NewsData")){
            return true;
        }else{
            return false;
        }
    }
    public boolean isFood(){
        SharedData = context.getSharedPreferences("SharedFood", context.MODE_PRIVATE);
        if(SharedData.contains("FoodData")){
            return true;
        }else{
            return false;
        }
    }

}
