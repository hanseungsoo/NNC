package com.example.han.coaching;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {



    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private String[] tabs = {"기념일추천", "맞춤추천", "가까운거리추천", "무작위추천"};
    public static Context mContext;
    ActionBar actionbar;
    MapView mapView;
    TextView nameTv = null;
    TextView telTv  = null;
    TextView cateTv = null;
    TextView addrTv = null;
    ImageView foodImg = null;
    Spinner SP2 =null;
    ViewGroup mapViewContainer = null;
    static Handler mHandler;
    static int ViewInt = 1;

    //Data
    public static ArrayList<Item> ThemaItem = new ArrayList<Item>();
    public static ArrayList<NewsItem> NewsNews = new ArrayList<NewsItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_news);
        if(ThemaItem.isEmpty()) {
            make_dummy(); }
        Intent a = new Intent(this, GpsService.class);
        startService(a);
        System.setProperty("http.keepAlive","false");
        //珥덇린�솕&�븣�엺
        SharedInit SI = new SharedInit(getApplicationContext());
        registerAlarm rA = new registerAlarm(getApplicationContext());
        if(!SI.getSharedTrue("isCreate")){
            SI.Init();
            rA.registerInit();
            rA.registerWT("Weather.a");
            rA.registerDong("Detailaddr");
            rA.registerNews(10);
            rA.registerOneWeek();
            rA.registerpattern();
            rA.registerplace();
        }
       // rA.testAM("ACTION.GET.ONE",21,45);

        //諛붿씤�뵫
        actionbar = getActionBar();

        //Drawer
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        // tab
        actionbar.setHomeButtonEnabled(false);

        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        setContentView(R.layout.activity_news);
                        setDrawer(ActionBar.NAVIGATION_MODE_STANDARD);
                        TextView newstitle = (TextView)findViewById(R.id.newsTitle);
                        TextView newsdesc = (TextView)findViewById(R.id.newsDesc);
                        ImageView newsimage = (ImageView)findViewById(R.id.newsImage);
                        Button newsbutton = (Button)findViewById(R.id.newsButton);

                        if(MainActivity.NewsNews.size()>0){
                            newstitle.setText(NewsNews.get(0).getTitle());
                            newsdesc.setText(NewsNews.get(0).getDesc());
                            new DownloadImageTask(newsimage).execute(NewsNews.get(0).getImageUrl());
                            newsbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(NewsNews.get(0).getLink());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    startActivity(intent);
                                }
                            });
                        }
                        if(noonWidget.CLICK_FLAG == true) {
                            noonWidget.CLICK_FLAG = false;
                        }
                        break;
                    case 1:
                        setContentView(R.layout.activity_main);
                        nameTv = (TextView)findViewById(R.id.nameView);
                        telTv = (TextView)findViewById(R.id.telView);
                        cateTv = (TextView)findViewById(R.id.cateView);
                        addrTv = (TextView)findViewById(R.id.addrView);
                        foodImg = (ImageView)findViewById(R.id.cookImage);
                        SP2 = (Spinner)findViewById(R.id.spinner2);
                        final ArrayList<String> arraylist2 = new ArrayList<String>();
                        arraylist2.add("추천1");
                        arraylist2.add("추천2");
                        arraylist2.add("추천3");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.mContext,
                                android.R.layout.simple_spinner_dropdown_item, arraylist2);
                        SP2.setAdapter(adapter);

                        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
                        mapViewContainer.removeAllViews();
                        mapView = null;
                        mapView = new MapView(MainActivity.this);
                        mapView.setDaumMapApiKey("9db6272582177f1d7b0643e35e1993e9");
                        mapViewContainer.addView(mapView);

                        actionbar.removeAllTabs();
                        setDrawer(ActionBar.NAVIGATION_MODE_TABS);
                        for (String tab_name : tabs) {
                            actionbar.addTab(actionbar.newTab().setText(tab_name)
                                    .setTabListener(new TabListen()));
                        }
                        if(noonWidget.CLICK_FLAG == true) {
                            noonWidget.CLICK_FLAG = false;
                            if(noonWidget.contentValue.equals("content1")) {
                                Log.i("widget", "widget->main, " + noonWidget.contentValue);
                                int tab_position = noonWidget.themaValue;
                                actionbar.setSelectedNavigationItem(tab_position);
                                SP2.setSelection(noonWidget.swapValue,true);
                            }
                        }
                        /*if(staticMerge.timer){
                            Log.i("aaaa","타이머가 울렸네~");
                            registerAlarm rA = new registerAlarm(MainActivity.mContext);
                            rA.registerNews(30);
                            staticMerge.timer = false;
                        }*/
                        break;
                }

            }
        };
        mHandler.sendEmptyMessage(ViewInt);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(MainActivity.this, optionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */



    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                setContentView(R.layout.activity_main);
                nameTv = (TextView)findViewById(R.id.nameView);
                telTv = (TextView)findViewById(R.id.telView);
                cateTv = (TextView)findViewById(R.id.cateView);
                addrTv = (TextView)findViewById(R.id.addrView);
                foodImg = (ImageView)findViewById(R.id.cookImage);
                SP2 = (Spinner)findViewById(R.id.spinner2);
                final ArrayList<String> arraylist2 = new ArrayList<String>();
                arraylist2.add("추천1");
                arraylist2.add("추천2");
                arraylist2.add("추천3");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.mContext,
                        android.R.layout.simple_spinner_dropdown_item, arraylist2);
                SP2.setAdapter(adapter);

                mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
                mapViewContainer.removeAllViews();
                mapView = null;
                mapView = new MapView(MainActivity.this);
                mapView.setDaumMapApiKey("9db6272582177f1d7b0643e35e1993e9");

                mapViewContainer.addView(mapView);


                actionbar.removeAllTabs();
                setDrawer(ActionBar.NAVIGATION_MODE_TABS);
                for (String tab_name : tabs) {
                    actionbar.addTab(actionbar.newTab().setText(tab_name)
                            .setTabListener(new TabListen()));
                }
            }else if(position ==3) {
                setContentView(R.layout.activity_news);
                setDrawer(ActionBar.NAVIGATION_MODE_STANDARD);
                TextView newstitle = (TextView)findViewById(R.id.newsTitle);
                TextView newsdesc = (TextView)findViewById(R.id.newsDesc);
                ImageView newsimage = (ImageView)findViewById(R.id.newsImage);
                Button newsbutton = (Button)findViewById(R.id.newsButton);

                if(MainActivity.NewsNews.size()>0){
                    newstitle.setText(NewsNews.get(0).getTitle());
                    newsdesc.setText(NewsNews.get(0).getDesc());
                    if(NewsNews.get(0).getImageUrl().equals("")){
                        NewsNews.get(0).setImageUrl("http://222.116.135.76:8080/Noon/images/noon.png");
                        new DownloadImageTask(newsimage).execute(NewsNews.get(0).getImageUrl());
                    }else{
                        new DownloadImageTask(newsimage).execute(NewsNews.get(0).getImageUrl());
                    }
                    newsbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(NewsNews.get(0).getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            startActivity(intent);
                        }
                    });
                }

            }
        }
    }
    private void selectItem(int position) {

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    public void make_dummy() {

        for (int i=1; i<13;i++) {
            Item item = new Item();
            item.title = "(X)title"+i;
            item.category = "(X)category"+i;
            item.address = "(X) add ress"+i;
            item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
            item.phone = "010-2043-5392";
            ThemaItem.add(i-1,item);
        }

        NewsItem item = new NewsItem();
        item.setTitle("눈치코칭 뉴스");
        item.setDesc("눈치코칭입니다.");
        item.setImageUrl("11");
        item.setLink("http://222.116.135.76:8080/Noon/noon.jsp");
        NewsNews.add(0, item);
    }

    public static void mmmm() {
        //Context context = mContext.getApplicationContext();
        Intent update = new Intent();
        update.setAction("chae.widget.update");
        mContext.sendBroadcast(update);
    }

    public void setDrawer(int a){
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(a);
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    public class TabListen implements ActionBar.TabListener{
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            int position = tab.getPosition();
            int size = MainActivity.ThemaItem.size();
            switch (position) {
                case 0:
                    if(size>=0) {
                        mapView.removeAllPOIItems();
                        Item in1 = MainActivity.ThemaItem.get(0);
                        MapPOIItem marker = new MapPOIItem();
                        nameTv.setText("" + in1.title);
                        telTv.setText("" + in1.phone);
                        cateTv.setText("" + in1.category);
                        addrTv.setText("" + in1.address);
                        if(in1.imageUrl.equals("")){
                            in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }else{
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }
                        marker.setItemName("Default Marker");
                        marker.setTag(0);
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                        mapView.addPOIItem(marker);
                        Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    MapPOIItem marker;
                                    Item in1;
                                    switch (position){
                                        case 0:
                                            in1 = MainActivity.ThemaItem.get(0);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 1:
                                            in1 = MainActivity.ThemaItem.get(1);

                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 2:
                                            in1 = MainActivity.ThemaItem.get(2);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Item in1 = MainActivity.ThemaItem.get(0);
                                    MapPOIItem marker = new MapPOIItem();
                                    nameTv.setText("" + in1.title);
                                    telTv.setText("" + in1.phone);
                                    cateTv.setText("" + in1.category);
                                    addrTv.setText("" + in1.address);
                                    if(in1.imageUrl.equals("")){
                                        in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }else{
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }
                                    marker.setItemName("Default Marker");
                                    marker.setTag(0);
                                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                    mapView.addPOIItem(marker);
                                }
                            });
                        }
                    }


                    SP2.setSelection(0, true);
                    break;
                case 1:
                    if(size>=3) {
                        mapView.removeAllPOIItems();
                        Item in1 = MainActivity.ThemaItem.get(3);
                        MapPOIItem marker = new MapPOIItem();
                        nameTv.setText("" + in1.title);
                        telTv.setText("" + in1.phone);
                        cateTv.setText("" + in1.category);
                        addrTv.setText("" + in1.address);
                        if(in1.imageUrl.equals("")){
                            in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }else{
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }
                        marker.setItemName("Default Marker");
                        marker.setTag(0);
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                        mapView.addPOIItem(marker);
                        Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    MapPOIItem marker;
                                    Item in1;
                                    switch (position){
                                        case 0:
                                            in1 = MainActivity.ThemaItem.get(3);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 1:
                                            in1 = MainActivity.ThemaItem.get(4);

                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 2:
                                            in1 = MainActivity.ThemaItem.get(5);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Item in1 = MainActivity.ThemaItem.get(3);
                                    MapPOIItem marker = new MapPOIItem();
                                    nameTv.setText("" + in1.title);
                                    telTv.setText("" + in1.phone);
                                    cateTv.setText("" + in1.category);
                                    addrTv.setText("" + in1.address);
                                    if(in1.imageUrl.equals("")){
                                        in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }else{
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }
                                    marker.setItemName("Default Marker");
                                    marker.setTag(0);
                                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                    mapView.addPOIItem(marker);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
                case 2:
                    if(size>=6) {
                        mapView.removeAllPOIItems();
                        Item in1 = MainActivity.ThemaItem.get(6);
                        MapPOIItem marker = new MapPOIItem();
                        nameTv.setText("" + in1.title);
                        telTv.setText("" + in1.phone);
                        cateTv.setText("" + in1.category);
                        addrTv.setText("" + in1.address);
                        if(in1.imageUrl.equals("")){
                            in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }else{
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }
                        marker.setItemName("Default Marker");
                        marker.setTag(0);
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                        mapView.addPOIItem(marker);
                        Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    MapPOIItem marker;
                                    Item in1;
                                    switch (position){
                                        case 0:
                                            in1 = MainActivity.ThemaItem.get(6);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 1:
                                            in1 = MainActivity.ThemaItem.get(7);

                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 2:
                                            in1 = MainActivity.ThemaItem.get(8);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Item in1 = MainActivity.ThemaItem.get(6);
                                    MapPOIItem marker = new MapPOIItem();
                                    nameTv.setText("" + in1.title);
                                    telTv.setText("" + in1.phone);
                                    cateTv.setText("" + in1.category);
                                    addrTv.setText("" + in1.address);
                                    if(in1.imageUrl.equals("")){
                                        in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }else{
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }
                                    marker.setItemName("Default Marker");
                                    marker.setTag(0);
                                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                    mapView.addPOIItem(marker);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
                case 3:
                    if(size>=9) {
                        mapView.removeAllPOIItems();
                        Item in1 = MainActivity.ThemaItem.get(9);
                        MapPOIItem marker = new MapPOIItem();
                        nameTv.setText("" + in1.title);
                        telTv.setText("" + in1.phone);
                        cateTv.setText("" + in1.category);
                        addrTv.setText("" + in1.address);
                        if(in1.imageUrl.equals("")){
                            in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }else{
                            new DownloadImageTask(foodImg).execute(in1.imageUrl);
                        }
                        marker.setItemName("Default Marker");
                        marker.setTag(0);
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                        mapView.addPOIItem(marker);
                        Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    MapPOIItem marker;
                                    Item in1;
                                    switch (position){
                                        case 0:
                                            in1 = MainActivity.ThemaItem.get(9);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 1:
                                            in1 = MainActivity.ThemaItem.get(10);

                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;
                                        case 2:
                                            in1 = MainActivity.ThemaItem.get(11);
                                            marker = new MapPOIItem();
                                            nameTv.setText("" + in1.title);
                                            telTv.setText("" + in1.phone);
                                            cateTv.setText("" + in1.category);
                                            addrTv.setText("" + in1.address);
                                            if(in1.imageUrl.equals("")){
                                                in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }else{
                                                new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                            }
                                            marker.setItemName("Default Marker");
                                            marker.setTag(0);
                                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                            mapView.addPOIItem(marker);

                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Item in1 = MainActivity.ThemaItem.get(9);
                                    MapPOIItem marker = new MapPOIItem();
                                    nameTv.setText("" + in1.title);
                                    telTv.setText("" + in1.phone);
                                    cateTv.setText("" + in1.category);
                                    addrTv.setText("" + in1.address);
                                    if(in1.imageUrl.equals("")){
                                        in1.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }else{
                                        new DownloadImageTask(foodImg).execute(in1.imageUrl);
                                    }
                                    marker.setItemName("Default Marker");
                                    marker.setTag(0);
                                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                                    mapView.addPOIItem(marker);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        public ImageView miv;

        public DownloadImageTask(ImageView iv){
            miv = iv;
        }

        protected Bitmap doInBackground(String... str) {
            String urldisplay = str[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            miv.setImageBitmap(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SaveData svData = new SaveData(mContext);
        if(svData.isFood()){
            ThemaItem = svData.getFood("SharedFood");
            Log.i("aaaa", "222222222222222222222222222" + ThemaItem.get(0).title);
        }
        if(svData.isNews()){
            NewsNews = svData.getNews("SharedNews");
            Log.i("aaaa", "33333333333333333333333333333333" + NewsNews.get(0).getTitle());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaveData svData = new SaveData(mContext);
        svData.save("SharedNews");
        svData.save("SharedFood");
    }
}
