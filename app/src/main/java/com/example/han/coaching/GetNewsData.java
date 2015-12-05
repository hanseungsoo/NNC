package com.example.han.coaching;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by han on 2015-11-24.
 */
public class GetNewsData extends AsyncTask<Void,Void,Void> {


    NewsItem item;
    String uri = "http://rss.hankyung.com/new/news_main.xml";
    URL url;
    String tagname = "", title="", desc="", link ="", image = "";
    Boolean flag = null;
    @Override
    protected void onPreExecute() {
        item = new NewsItem();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Boolean flag = false;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            MainActivity.NewsNews.clear();
            url = new URL(uri);
            InputStream in = url.openStream();
            xpp.setInput(in, "utf-8");

            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT ) {
                if(eventType == XmlPullParser.START_TAG) {
                    if(xpp.getName().equals("item")){
                        flag = true;
                    }
                    tagname = xpp.getName();
                } else if(eventType == XmlPullParser.TEXT) {
                    if(flag){
                        if(tagname.equals("title")) title += xpp.getText();
                        else if (tagname.equals("link")) link += xpp.getText();
                        else if (tagname.equals("description")) desc += xpp.getText();
                        else if (tagname.equals("image")) image += xpp.getText();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    tagname = xpp.getName();
                    if(tagname.equals("item")) {

                        item.setTitle(title);
                        item.setDesc(desc);
                        item.setImageUrl(image);
                        item.setLink(link);

                        MainActivity.NewsNews.add(item);

                        title="";
                        desc="";
                        link="";
                        image="";
                    }
                }

                eventType = xpp.next();
            }

            flag = true;

        } catch(Exception e) {
            e.printStackTrace();
        }
        MainActivity.ViewInt = 0;
        SaveData svData = new SaveData(MainActivity.mContext);
        svData.save("SharedNews");

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i("aaaa","news!!!!!!");
        noonWidget.contentValue = "content2";
        MainActivity.mmmm();
    }

}

