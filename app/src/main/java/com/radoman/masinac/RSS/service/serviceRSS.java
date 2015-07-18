package com.radoman.masinac.RSS.service;
/**
    This file is part of Mašinac.

    Mašinac is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
**/

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.radoman.masinac.R;
import com.radoman.masinac.RSS.RSSModel;
import com.radoman.masinac.activity.MainActivity;
import com.radoman.masinac.model.Predmeti;
import com.radoman.masinac.network.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SPACEMAN on 6/28/2015.
 */



public class serviceRSS extends IntentService {
    // Must create a default constructor
    public static final String ACTION = "com.radoman.masinac.RSS.service.serviceRSS";
   // private String date = "Sun, 27 Jun 2015 11:34:34 +0000";
    public static HashMap<Integer,Boolean> mapa;

    Predmeti p;
    public serviceRSS() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

            mapa = new HashMap();
            p = new Predmeti();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            for (int i = 0; i < p.getPredmeti_id().length; i++)
                mapa.put(p.getPredmeti_id()[i], prefs.getBoolean(String.valueOf(p.getPredmeti_id()[i]),false));

        Log.v("TERMO",""+mapa.get(159));

       // mapa.put(73,true);


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int not = intent.getIntExtra("not", 0);

        Bundle b = new Bundle();
        b = intent.getExtras();
       // mapa = (HashMap<Integer,Boolean>) b.getSerializable("hashMap");


        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


        ServiceHandler sh = new ServiceHandler();
       //String url = "https://pipes.yahoo.com/pipes/pipe.run?_id=f6d7d67abb58ae98b667cbb377bd2864&_render=json";
        String url = "http://ferometal.rs/jsont_test.json";
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);


        Gson gson = new Gson();
        RSSModel rssModel = gson.fromJson(jsonStr, RSSModel.class);
        int i_count = rssModel.getValue().getItems().get(0).getEntries().size();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Calendar c = Calendar.getInstance();
        int i;

        String rssDate = rssModel.getValue().getItems().get(0).getUpdated();

        if(prefs.getString("date",null)==null){
            editor.putString("date",dateFormat2.format(c.getTime()));
            editor.commit();
        }
        String date = prefs.getString("date",null);

        Log.e("rssDate",rssDate+"     "+date);

        try {
            if(dateFormat2.parse(rssDate).after(dateFormat2.parse(date)))
            {
                for(i=0;i<i_count;i++)
                {
                    Date postDate = dateFormat2.parse(rssModel.getValue().getItems().get(0).getEntries().get(i).getUpdated());
                    String predmetNaslov = rssModel.getValue().getItems().get(0).getEntries().get(i).getTitle().getContent();
                    int predmetId = p.getIdByPredmet(predmetNaslov.substring(0,predmetNaslov.indexOf(')')+1));

                    URI href = null;
                    try {
                        href = new URI(rssModel.getValue().getItems().get(0).getEntries().get(i).getLink().getHref());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    String update = prefs.getString("date",rssDate);

                    Log.e("UPDATE", update+"      "+postDate);
                    List<NameValuePair> params = URLEncodedUtils.parse(href, "UTF-8");
                    String threadID = params.get(0).getValue();



                    if(postDate.after((dateFormat2.parse(update))))
                    {

                        if(mapa.get(predmetId))
                        {


                            showNotification(predmetNaslov.substring(0,predmetNaslov.indexOf(')')+1),threadID,String.valueOf(predmetId));


                        }

                        else
                        {
                        //    Log.e("PARAMS","NIJE NOTIFIKACIJA");
                        }



                    }
                    else
                    {
                      //  Log.e("PARAMS","Prosli datum");

                        break;
                    }
                }
                editor.putString("date",rssDate);
                editor.apply();
                Log.e("BREAK POINT","LELE");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
    @Override
    public void onDestroy(){
        Log.v("SERVICE", "STOPED");
        super.onDestroy();
    }

    public void showNotification(String predmet, String threadId, String forumId){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("threadId", threadId);
        notificationIntent.putExtra("forumID", forumId);
        notificationIntent.putExtra("notification", true);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        Integer.parseInt(forumId),
                        notificationIntent,
                        0
                );

        Log.v("NOTIFIKACIJA",predmet+"   "+forumId+"   "+threadId);
        NotificationManager nm = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(predmet)
                        .setContentText("Ново обавештење: " + predmet)
                        .setLights(Color.BLUE,1000,300)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setContentIntent(resultPendingIntent);

        Notification n = mBuilder.build();
        nm.notify(Integer.parseInt(forumId), n);
    }


}