package com.radoman.masinac.activity;
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


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.radoman.masinac.R;
import com.radoman.masinac.RSS.service.serviceRSS;
import com.radoman.masinac.RSS.service.serviceReciever;
import com.radoman.masinac.adapter.PredmetiAdapter;
import com.radoman.masinac.model.CanaroTextView;
import com.radoman.masinac.model.Predmeti;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements GuillotineListener {



    public static Toolbar mToolbar;
    private FrameLayout container;
    public static GuillotineAnimation gb;
    public static CanaroTextView gTitle,gPocetna,gSS,faSettings,faEmpty;
    int flag =0;
    public static CheckBox cbEmpty;
    SharedPreferences sharedPref;
    public static boolean isWebViewVisible = false;


    private serviceReciever RSSreciever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout)findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);
        gTitle = (CanaroTextView)findViewById(R.id.gTitle);
        gTitle.setText(R.string.pocetna);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        gPocetna = (CanaroTextView)findViewById(R.id.gPocetna);
        gSS = (CanaroTextView)findViewById(R.id.gStudentskiServisi);
        faSettings = (CanaroTextView)findViewById(R.id.faSettings);
        faEmpty = (CanaroTextView)findViewById(R.id.faEmpty);
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), "fontawesome-webfont.ttf");
        faSettings.setTypeface(type);
        faEmpty.setTypeface(type);

        cbEmpty = (CheckBox)findViewById(R.id.emptyBox);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        cbEmpty.setChecked(sharedPref.getBoolean("boxEmpty",false));

        cbEmpty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("boxEmpty", isChecked);
                editor.commit();

                Log.e("BOOLEAN",""+sharedPref.getBoolean("boxEmpty",false));

                if(isChecked && (PredmetiFragment.recList!=null)) {
                    ((PredmetiAdapter) PredmetiFragment.recList.getAdapter()).setEmptyFilter();
                }else if(PredmetiFragment.recList!=null) {
                    ((PredmetiAdapter) PredmetiFragment.recList.getAdapter()).flushFilter();
                }
            }

        });

         container = (FrameLayout)findViewById(R.id.container_body);
         gb = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setActionBarViewForAnimation(mToolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(this)
                 .setDuration(300)
                .build();

        View.OnClickListener listener = new View.OnClickListener() {
            Fragment fragment = null;
            Typeface type = Typeface.createFromAsset(getResources().getAssets(), "fonts/canaro_extra_bold.otf");
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.gPocetna:

                        gPocetna.setTextAppearance(getApplicationContext(),R.style.TextView_GuillotineItem_Selected);
                        gSS.setTextAppearance(getApplicationContext(),R.style.TextView_GuillotineItem);
                        gPocetna.setTypeface(type);
                        gSS.setTypeface(type);
                        gb.close();

                        final Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                new setFragment(new HomeFragment(),"HomeFragment").execute();
                            }
                        }, 350);
                        flag = 1;

                        break;

                    case R.id.gStudentskiServisi:

                        gb.close();
                        flag = 0;
                        gPocetna.setTextAppearance(getApplicationContext(),R.style.TextView_GuillotineItem);
                        gSS.setTextAppearance(getApplicationContext(), R.style.TextView_GuillotineItem_Selected);
                        gPocetna.setTypeface(type);
                        gSS.setTypeface(type);


                        final Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                new setFragment(new StudentskiServisiFragment(),"StudentskiServisiFragment").execute();
                            }
                        }, 300);

                        break;

                    case R.id.gMasinac:

                        break;


                    default:
                        break;
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();
                }
            }
        };

        gPocetna.setOnClickListener(listener);
        gSS.setOnClickListener(listener);
        //onStartService();
       // scheduleAlarm();


        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        if (getIntent().getBooleanExtra("notification",false))
        {


            getIntent().putExtra("notification", false);
            Bundle bundle = new Bundle();
            bundle.putBoolean("notification",true);
            bundle.putString("id", (getIntent().getStringExtra("forumID")));
            Fragment temeFragment = new TemeFragment();
            temeFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, temeFragment,"TemeFragment");
            fragmentTransaction.commit();
        }
        else
        {
            new setFragment(new VestiMasFragment(),"HomeFragment").execute();

        }





    }

    // Launching the service
    public void onStartService() {
        Intent i = new Intent(this, serviceRSS.class);
        HashMap<Integer,Boolean> mapa = new HashMap();
        mapa.put(73,true);
        Bundle extras =  new Bundle();
        extras.putSerializable("hashMap",mapa);
        i.putExtras(extras);

        startService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register for the particular broadcast based on ACTION string
        IntentFilter filter = new IntentFilter(serviceRSS.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(testReceiver, filter);
        // or `registerReceiver(testReceiver, filter)` for a normal broadcast
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener when the application is paused
        LocalBroadcastManager.getInstance(this).unregisterReceiver(testReceiver);
        // or `unregisterReceiver(testReceiver)` for a normal broadcast
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentByTag("StudentskiServisiFragment") != null) {
            if(StudentskiServisiFragment.mWebView.canGoBack())
                StudentskiServisiFragment.mWebView.goBack();
            else {
                super.onBackPressed();
            }
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onGuillotineOpened() {
       /** if(StudentskiServisiFragment.mWebView != null) {
            if (StudentskiServisiFragment.mWebView.getVisibility() == View.VISIBLE) {
                StudentskiServisiFragment.mWebView.setVisibility(View.GONE);
                Log.e("GONE","GONE");
            }
        }**/
        container.setVisibility(View.GONE);
        container.setClickable(false);

    }

    @Override
    public void onGuillotineClosed() {
     /**   if(StudentskiServisiFragment.mWebView != null && flag!=1) {
            if (!StudentskiServisiFragment.mWebView.isShown()) {


            }
        }**/

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                container.setVisibility(View.VISIBLE);
            }
        }, 300);
        //container.setVisibility(View.VISIBLE);
        container.setClickable(true);

    }

    private class setFragment extends AsyncTask<Void, Void, Void> {
        Activity a;
        Fragment f;
        String tag;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            ;

        }

        public setFragment(Fragment f, String tag)
        {
            this.f   = f;
            this.tag = tag;
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            runOnUiThread(new Runnable() {
                public void run() {

                    if (f != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, f,tag);
                        fragmentTransaction.commit();

                    }
                }
            });





            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

        }
    }





    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra("resultValue");
                Toast.makeText(MainActivity.this, resultValue, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), serviceReciever.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, serviceReciever.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
        int intervalMillis = 5000; //15 mins
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }

    public void initSharedPref(){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        Predmeti p = new Predmeti();
        editor.putBoolean("firstRun",false);
        int[] ids = p.getPredmeti_id();
        for(int i =0;i<ids.length;i++){
            editor.putBoolean(String.valueOf(ids[i]),false);
        }
        editor.apply();
    }

}


