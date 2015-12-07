package com.example.han.coaching;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by han on 2015-11-24.
 */
public class noonWidget extends AppWidgetProvider {
    private static DisplayImageOptions displayOptions;
    private static RemoteViews updateViews;
    private static Item item;

    public static String contentValue;
    public static int themaValue;
    public static int swapValue;
    public static boolean CLICK_FLAG;
    public static String TAG = "noonWidget";

    static {
        // displayOptions = DisplayImageOptions.createSimple();
        displayOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.noon_widget_icon)
                .showImageForEmptyUri(R.drawable.noon_widget_icon)
                .showImageOnFail(R.drawable.noon_widget_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        contentValue = "content2";
        themaValue = 0;
        CLICK_FLAG = false;
        swapValue = 0;
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UILApplication.initImageLoader(context);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, widgetId);
            saveNoon(context);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("chae.widget.update")) {
            loadNoon(context);
            widgetUpdate(context);
        }
        if (intent.getAction().equals("chae.widget.left")) {
            loadNoon(context);
            swapValue = 0;
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    themaValue = 3;
                    break;
                case 1:
                    themaValue = 0;
                    break;
                case 2:
                    themaValue = 1;
                    break;
                case 3:
                    themaValue = 2;
                    break;
                default:
            }
            widgetUpdate(context);
        }

        if (intent.getAction().equals("chae.widget.right")) {
            loadNoon(context);
            swapValue = 0;
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    themaValue = 1;
                    break;
                case 1:
                    themaValue = 2;
                    break;
                case 2:
                    themaValue = 3;
                    break;
                case 3:
                    themaValue = 0;
                    break;
                default:
            }
            widgetUpdate(context);
        }

        if (intent.getAction().equals("chae.widget.click1")) {
            CLICK_FLAG = true;
            MainActivity.ViewInt = 1;
            Log.i("widget", "음식집 Clicked");
            enterMain(context);
        }

        if (intent.getAction().equals("chae.widget.click2")) {
            CLICK_FLAG = true;
            Log.i("widget", "뉴스 Clicked");
            MainActivity.ViewInt = 0;
            enterMain(context);
        }
        if (intent.getAction().equals("chae.widget.swap")) {
            loadNoon(context);
            if (swapValue == 0) {
                swapValue = 1;
            } else if (swapValue == 1) {
                swapValue = 2;
            } else {
                swapValue = 0;
            }
            widgetUpdate(context);
        }
        if(intent.getAction().equals("chae.widget.reload")) {
            loadNoon(context);
            Toast.makeText(context,"RELOAD:"+contentValue+","+themaValue+","+swapValue, Toast.LENGTH_SHORT).show();
            widgetUpdate(context);
        }
        if(intent.getAction().equals("chae.widget.thema.change")) {
            loadNoon(context);
            if (contentValue.equals("content2")) {
                contentValue = "content1";
            } else {
                contentValue = "content2";
            }
            widgetUpdate(context);
        }
        if(intent.getAction().equals("chae.widget.select.item")) {
            Toast.makeText(context,"선택 되었습니다.", Toast.LENGTH_SHORT).show();
            loadNoon(context);
            if (contentValue.equals("content1")) {
                noonFoodDb();
                MainActivity.ViewInt=1;
            } else  {
                noonNewsDb();
                MainActivity.ViewInt=0;
            }
            CLICK_FLAG = true;
            enterMain(context);
        }
    }

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        String content = contentValue;
        String phone = "010-2043-5392";
        int layoutId;

        if ("content1".equals(content)) {
            layoutId = R.layout.widget_layout;
            updateViews = new RemoteViews(context.getPackageName(), layoutId);
            item = contentValue(MainActivity.ThemaItem);
            phone = item.phone;
            String[] phn = phone.split("[-]");
            phone = "";
            for (int i = 0; i < phn.length; i++) {
                phone += phn[i];
            }
            Log.i("widget", "before configure : " + phone);
            configureLayout(item);
            Intent left_intent = new Intent();
            Intent right_intent = new Intent();
            Intent click_intent = new Intent();
            Intent call_intent = new Intent();
            Intent swap_intent = new Intent();
            Intent option_intent = new Intent(context,optionActivity.class);
            Intent reload_intent = new Intent();
            Intent thema_change_intent = new Intent();
            Intent select_item_intent = new Intent();
            left_intent.putExtra("T_value", themaValue);
            right_intent.putExtra("T_value", themaValue);
            left_intent.setAction("chae.widget.left");
            right_intent.setAction("chae.widget.right");
            click_intent.setAction("chae.widget.click1");
            call_intent.setAction(Intent.ACTION_DIAL);
            call_intent.setData(Uri.parse("tel:" + phone));
            swap_intent.setAction("chae.widget.swap");
            reload_intent.setAction("chae.widget.reload");
            thema_change_intent.setAction("chae.widget.thema.change");
            select_item_intent.setAction("chae.widget.select.item");
            PendingIntent pendingIntent_L = PendingIntent.getBroadcast(context, 0, left_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_R = PendingIntent.getBroadcast(context, 0, right_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_C = PendingIntent.getBroadcast(context, 0, click_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_D = PendingIntent.getActivity(context, 0, call_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_S = PendingIntent.getBroadcast(context, 0, swap_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_O = PendingIntent.getActivity(context, 0, option_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_I = PendingIntent.getBroadcast(MainActivity.mContext, 0, select_item_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_F = PendingIntent.getBroadcast(context, 0, reload_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent_T = PendingIntent.getBroadcast(context, 0, thema_change_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.call_button, pendingIntent_D);
            updateViews.setOnClickPendingIntent(R.id.left_button, pendingIntent_L);
            updateViews.setOnClickPendingIntent(R.id.right_button, pendingIntent_R);
            updateViews.setOnClickPendingIntent(R.id.widget_image, pendingIntent_C);
            updateViews.setOnClickPendingIntent(R.id.widget_textLay, pendingIntent_C);
            updateViews.setOnClickPendingIntent(R.id.widget_swap, pendingIntent_S);
            updateViews.setOnClickPendingIntent(R.id.widget_setting, pendingIntent_O);
            updateViews.setOnClickPendingIntent(R.id.thema_change_button, pendingIntent_T);
            updateViews.setOnClickPendingIntent(R.id.select_item_button,pendingIntent_I);
            updateViews.setOnClickPendingIntent(R.id.widget_reload,pendingIntent_F);

            ImageSize minImazeSize = new ImageSize(120, 400);
            if(item.imageUrl.equals("")) {
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
            }
            ImageLoader.getInstance().loadImage(item.imageUrl, minImazeSize, displayOptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    updateViews.setImageViewBitmap(R.id.widget_image, loadedImage);
                    appWidgetManager.updateAppWidget(appWidgetId, updateViews);
                }
            });
        } else if ("content2".equals(content)) {
            Log.i("widget", "content2");
            layoutId = R.layout.widget_layout2;

            updateViews = new RemoteViews(context.getPackageName(), layoutId);

            updateViews.setTextViewText(R.id.widget_tv, "뉴스 추천");
            updateViews.setTextViewText(R.id.widget_title, MainActivity.NewsNews.get(0).getTitle());
            if (MainActivity.NewsNews.get(0).getDesc().length() >= 30) {
                updateViews.setTextViewText(R.id.widget_sub, MainActivity.NewsNews.get(0).getDesc().substring(0, 30) + "...");
            } else {
                updateViews.setTextViewText(R.id.widget_sub, MainActivity.NewsNews.get(0).getDesc() + "...");
            }


            Intent click_intent = new Intent();
            click_intent.setAction("chae.widget.click2");
            PendingIntent pendingIntent_C = PendingIntent.getBroadcast(context, 0, click_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.layout2, pendingIntent_C);
            String url;

            url = MainActivity.NewsNews.get(0).getImageUrl();

            ImageSize minImazeSize = new ImageSize(120, 400);
            if(item.imageUrl.equals("")) {
                item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
            }
            ImageLoader.getInstance().loadImage(item.imageUrl, minImazeSize, displayOptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    updateViews.setImageViewBitmap(R.id.widget_image, loadedImage);
                    appWidgetManager.updateAppWidget(appWidgetId, updateViews);
                }
            });
            appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        } else {
            appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        }
    }

    private static void configureLayout(Item item1) {
        updateViews.setTextViewText(R.id.widget_content, "음식점 추천");

        if(themaValue==0) {
            updateViews.setTextViewText(R.id.widget_thema, "기념일추천");
            if(item1.address.substring(0, 3).equals("(X)")) {
                updateViews.setTextViewText(R.id.widget_title, "오늘은 기념일이 아니어서");
                updateViews.setTextViewText(R.id.widget_cg, "추천할 만한 음식점이 없습니다.");
                updateViews.setTextViewText(R.id.widget_address, "");
            }else {
                updateViews.setTextViewText(R.id.widget_title, item1.title);
                updateViews.setTextViewText(R.id.widget_cg, item1.category);
                updateViews.setTextViewText(R.id.widget_address, item1.address);
            }
        } else if(themaValue==1) {
            updateViews.setTextViewText(R.id.widget_thema, "맞춤음식 추천");
            if(item1.address.substring(0, 3).equals("(X)")) {
                updateViews.setTextViewText(R.id.widget_title, "");
                updateViews.setTextViewText(R.id.widget_cg, "맞춤 추천되는 음식점이 없습니다.");
                updateViews.setTextViewText(R.id.widget_address, "");
            }else {
                updateViews.setTextViewText(R.id.widget_title, item1.title);
                updateViews.setTextViewText(R.id.widget_cg, item1.category);
                updateViews.setTextViewText(R.id.widget_address, item1.address);
            }
        } else if(themaValue==2) {
            updateViews.setTextViewText(R.id.widget_thema, "가까운거리 추천");
            if(item1.address.substring(0, 3).equals("(X)")) {
                updateViews.setTextViewText(R.id.widget_title, "");
                updateViews.setTextViewText(R.id.widget_cg, "가까운 거리에 있는 음식점이 없습니다.");
                updateViews.setTextViewText(R.id.widget_address, "");
            }else {
                updateViews.setTextViewText(R.id.widget_title, item1.title);
                updateViews.setTextViewText(R.id.widget_cg, item1.category);
                updateViews.setTextViewText(R.id.widget_address, item1.address);
            }
        } else {
            updateViews.setTextViewText(R.id.widget_thema, "무작위 추천");
            if(item1.address.substring(0, 3).equals("(X)")) {
                updateViews.setTextViewText(R.id.widget_title, "");
                updateViews.setTextViewText(R.id.widget_cg, "무작위 추천되는 음식점이 없습니다.");
                updateViews.setTextViewText(R.id.widget_address, "");
            }else {
                updateViews.setTextViewText(R.id.widget_title, item1.title);
                updateViews.setTextViewText(R.id.widget_cg, item1.category);
                updateViews.setTextViewText(R.id.widget_address, item1.address);
            }
        }
    }

    private static Item contentValue(ArrayList<Item> items) {
        Item item1 = new Item();
        switch (themaValue) {
            case 0:
                if(swapValue==0){
                    item1 = items.get(0);
                }else if(swapValue==1) {
                    item1 = items.get(1);
                }else {
                    item1 = items.get(2);
                }
                break;
            case 1:
                if(swapValue==0){
                    item1 = items.get(3);
                }else if(swapValue==1) {
                    item1 = items.get(4);
                }else {
                    item1 = items.get(5);
                }
                break;
            case 2:
                if(swapValue==0){
                    item1 = items.get(6);
                }else if(swapValue==1) {
                    item1 = items.get(7);
                }else {
                    item1 = items.get(8);
                }
                break;
            case 3:
                if(swapValue==0){
                    item1 = items.get(9);
                }else if(swapValue==1) {
                    item1 = items.get(10);
                }else {
                    item1 = items.get(11);
                }
                break;
            default:
                themaValue=0;
        }
        return item1;
    }

    private void noonFoodDb() {
        DBHandler dbHandler = DBHandler.open(MainActivity.mContext, item);
        dbHandler.click_time();
        dbHandler.food_favorite_insert();
        dbHandler.close();
    }
    private void noonNewsDb() {
        DBHandler dbHandler = DBHandler.open(MainActivity.mContext, item);
        dbHandler.close();
    }


    private void widgetUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thiswidget = new ComponentName(context, noonWidget.class);
        int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
        onUpdate(context, appWidgetManager, ids);
    }

    private void enterMain(Context context) {
        Intent i = new Intent();
        i.setClassName("com.example.han.coaching", "com.example.han.coaching.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(i);
    }

    public static  void saveNoon(Context context) {
        SharedPreferences pref = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("contentValue").commit();
        editor.putString("contentValue", contentValue);
        editor.remove("themaValue").commit();
        editor.putInt("themaValue", themaValue);
        editor.remove("swapValue").commit();
        editor.putInt("swapValue", swapValue);

        editor.commit();
    }

    private void loadNoon(Context context) {
        SharedPreferences pref = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
        contentValue = pref.getString("contentValue","content2");
        themaValue = pref.getInt("themaValue",0);
        swapValue = pref.getInt("swapValue",0);
    }
}
