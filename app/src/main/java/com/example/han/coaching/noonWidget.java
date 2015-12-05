package com.example.han.coaching;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by han on 2015-11-24.
 */
public class noonWidget extends AppWidgetProvider {
    private static RemoteViews updateViews;
    public static int themaValue=0;
    public static String t_Value="thema1";
    private static DisplayImageOptions displayOptions;
    public static String contentValue="content2";
    public static String ph="000-0000";
    public static int swapValue=0;
    public static boolean CLICK_FLAG = false;

    private static ArrayList<Item> items;
    private static Item item;

    static {
        displayOptions = DisplayImageOptions.createSimple();
        /*
        displayOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.noon)
                .showImageForEmptyUri(R.drawable.noon)
                .showImageOnFail(R.drawable.noon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
                */
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        themaValue = 0;
        t_Value = "thema1";
        Log.i("widget","onEnabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        themaValue = 0;
        t_Value = "thema1";
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        themaValue = 0;
        t_Value = "thema1";
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UILApplication.initImageLoader(context);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, widgetId);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("chae.widget.update")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }
        if (intent.getAction().equals("chae.widget.left")) {
            swapValue=0;
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    t_Value = "thema4";
                    themaValue = 3;
                    break;
                case 1:
                    t_Value = "thema1";
                    themaValue = 0;
                    break;
                case 2:
                    t_Value="thema2";
                    themaValue = 1;
                    break;
                case 3:
                    t_Value = "thema3";
                    themaValue = 2;
                    break;
                default:
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if (intent.getAction().equals("chae.widget.right")) {
            swapValue=0;
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    t_Value = "thema2";
                    themaValue = 1;
                    break;
                case 1:
                    t_Value = "thema3";
                    themaValue = 2;
                    break;
                case 2:
                    t_Value = "thema4";
                    themaValue = 3;
                    break;
                case 3:
                    t_Value = "thema1";
                    themaValue = 0;
                    break;
                default:
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if (intent.getAction().equals("chae.widget.click1")) {
            CLICK_FLAG = true;
            MainActivity.ViewInt = 1;
            noonDb();
            Log.i("widget", "음식집 Clicked");
            Intent i = new Intent();
            i.setClassName("com.example.han.coaching", "com.example.han.coaching.MainActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(i);
        }

        if(intent.getAction().equals("chae.widget.click2")) {
            CLICK_FLAG = true;
            Log.i("widget", "뉴스 Clicked");
            MainActivity.ViewInt = 0;
            Intent i = new Intent();
            i.setClassName("com.example.han.coaching", "com.example.han.coaching.MainActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(i);
        }

        if (intent.getAction().equals("chae.widget.swap")) {
            if(swapValue==0) {
                swapValue=1;
            } else if(swapValue==1) {
                swapValue =2;
            } else {
                swapValue =0;
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }
    }

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,final int appWidgetId) {

        String content = contentValue;
        int layoutId;
        if ("content1".equals(content)) {
            layoutId = R.layout.widget_layout;
            String thema = t_Value;
            updateViews = new RemoteViews(context.getPackageName(), layoutId);
            updateViews.setTextViewText(R.id.widget_tv, "음식집 추천");
            item = contentValue(MainActivity.ThemaItem, thema);
            ph = item.phone;
            String[] phn = ph.split("[-]");
            ph = "";
            for(int i=0; i<phn.length; i++) {
                ph += phn[i];
            }
            Log.i("widget", "before configure : " + ph);
            configureLayout(content, item);
            Intent left_intent = new Intent();
            Intent right_intent = new Intent();
            Intent click_intent = new Intent();
            Intent call_intent = new Intent();
            Intent swap_intent = new Intent();
            left_intent.putExtra("T_value", themaValue);
            right_intent.putExtra("T_value", themaValue);
            left_intent.setAction("chae.widget.left");
            right_intent.setAction("chae.widget.right");
            click_intent.setAction("chae.widget.click1");
            call_intent.setAction(Intent.ACTION_DIAL);
            call_intent.setData(Uri.parse("tel:" + ph));
            swap_intent.setAction("chae.widget.swap");
            PendingIntent pendingIntent_L = PendingIntent.getBroadcast(context, 0, left_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            PendingIntent pendingIntent_R = PendingIntent.getBroadcast(context, 0, right_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            PendingIntent pendingIntent_C = PendingIntent.getBroadcast(context, 0, click_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            PendingIntent pendingIntent_D = PendingIntent.getActivity(context, 0, call_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            PendingIntent pendingIntent_S = PendingIntent.getBroadcast(context, 0, swap_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.call_button, pendingIntent_D);
            updateViews.setOnClickPendingIntent(R.id.left_button, pendingIntent_L);
            updateViews.setOnClickPendingIntent(R.id.right_button, pendingIntent_R);
            updateViews.setOnClickPendingIntent(R.id.widget_image, pendingIntent_C);
            updateViews.setOnClickPendingIntent(R.id.widget_textLay, pendingIntent_C);
            updateViews.setOnClickPendingIntent(R.id.widget_swap, pendingIntent_S);

            ImageSize minImazeSize = new ImageSize(120,400);
            ImageLoader.getInstance().loadImage(item.imageUrl, minImazeSize,displayOptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    updateViews.setImageViewBitmap(R.id.widget_image,loadedImage);
                    appWidgetManager.updateAppWidget(appWidgetId, updateViews);
                }
            });
        } else if ("content2".equals(content)) {
            Log.i("widget","content2");
            layoutId = R.layout.widget_layout2;

            updateViews = new RemoteViews(context.getPackageName(), layoutId);

            updateViews.setTextViewText(R.id.widget_tv, "뉴스 추천");
            updateViews.setTextViewText(R.id.widget_title, MainActivity.NewsNews.get(0).getTitle());
            if(MainActivity.NewsNews.get(0).getDesc().length() >=30) {
                updateViews.setTextViewText(R.id.widget_sub, MainActivity.NewsNews.get(0).getDesc().substring(0,30)+"...");
            } else {
                updateViews.setTextViewText(R.id.widget_sub, MainActivity.NewsNews.get(0).getDesc()+"...");
            }


            Intent click_intent = new Intent();
            click_intent.setAction("chae.widget.click2");
            PendingIntent pendingIntent_C = PendingIntent.getBroadcast(context, 0, click_intent, PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.layout2, pendingIntent_C);
            String url;
            ImageSize minImazeSize = new ImageSize(120, 400);
            /*
            if(MainActivity.NewsNews.get(0).getImageUrl().length() >= 10) {
                url = "http://news.hankyung.com/nas_photo/201511/AA.10926114.3.jpg";
                Log.i("widget:image", url);
                Log.i("widget:image","http://news.hankyung.com/nas_photo/201511/AA.10926114.3.jpg");
            } else {
                url = "http://222.116.135.76:8080/Noon/images/noon.png";

            }*/
            url = "http://222.116.135.76:8080/Noon/images/noon.png";

            ImageLoader.getInstance().loadImage(url, minImazeSize, displayOptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    updateViews.setImageViewBitmap(R.id.widget_image,loadedImage);
                    appWidgetManager.updateAppWidget(appWidgetId, updateViews);
                }
            });

        } else if ("content3".equals(content)) {
            //layoutId = R.layout.widget_layout2;
        } else {
            layoutId = R.layout.widget_layout_default;
            appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        }


    }

    private static void configureLayout(String content, Item item1) {
        updateViews.setTextViewText(R.id.widget_title, item1.title);
        updateViews.setTextViewText(R.id.widget_cg, item1.category);
        if(item1.address.substring(0,3).equals("(X)")) {
            updateViews.setTextViewText(R.id.widget_title, "오늘은 기념일이 아니거나,");
            updateViews.setTextViewText(R.id.widget_cg, "주변에 추천되는 음식집이");
            updateViews.setTextViewText(R.id.widget_address,"없습니다." );
        }else {
            updateViews.setTextViewText(R.id.widget_address, item1.address);
        }

        if(t_Value.equals("thema1")) {
            updateViews.setTextViewText(R.id.themaText, "기념일추천");
        } else if(t_Value.equals("thema2")) {
            updateViews.setTextViewText(R.id.themaText, "선호음식 추천");
        } else if(t_Value.equals("thema3")) {
            updateViews.setTextViewText(R.id.themaText, "가까운거리 추천");
        } else {
            updateViews.setTextViewText(R.id.themaText, "랜덤추천");
        }
        Log.i("widget", "configure:" + item1.title + item1.imageUrl);

    }

    private static Item contentValue(ArrayList<Item> items, String string) {
        Item item1 = new Item();
        switch (string) {
            case "thema1":
                if(swapValue==0){
                    item1 = items.get(0);
                }else if(swapValue==1) {
                    item1 = items.get(1);
                }else {
                    item1 = items.get(2);
                }
                break;
            case "thema2":
                if(swapValue==0){
                    item1 = items.get(3);
                }else if(swapValue==1) {
                    item1 = items.get(4);
                }else {
                    item1 = items.get(5);
                }
                break;
            case "thema3":
                if(swapValue==0){
                    item1 = items.get(6);
                }else if(swapValue==1) {
                    item1 = items.get(7);
                }else {
                    item1 = items.get(8);
                }
                break;
            case "thema4":
                if(swapValue==0){
                    item1 = items.get(9);
                }else if(swapValue==1) {
                    item1 = items.get(10);
                }else {
                    item1 = items.get(11);
                }
                break;
            default:
        }
        return item1;
    }

    public void noonDb() {
        DBHandler dbHandler = DBHandler.open(MainActivity.mContext, item);
        dbHandler.click_time();
        dbHandler.food_favorite_insert();
        dbHandler.close();
    }


}
