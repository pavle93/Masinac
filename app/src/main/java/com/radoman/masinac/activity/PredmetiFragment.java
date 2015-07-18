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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.radoman.masinac.R;
import com.radoman.masinac.adapter.PredmetiAdapter;
import com.radoman.masinac.model.RecyclerItemClickListener;
import com.radoman.masinac.network.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.grantland.widget.AutofitTextView;


public class PredmetiFragment extends Fragment implements SearchView.OnQueryTextListener {
    public static RecyclerView recList;

    private static final String TAG_TEME = "teme";
    private static final String TAG_NASLOV = "naslov";
    private static final String TAG_HREF = "href";
    private static final String TAG_AUTOR = "autor";
    private static final String TAG_FORUM_ID = "forum_id";
    private static final String TAG_POSLEDNJI_POST = "poslednji_post";
    private static final String TAG_POSLEDNJI_POST_LINK = "poslednji_post_link";


    private ProgressDialog pDialog;
    private static String url = "http://ferometal.rs/parserOAS.php/";

    private static int forum_id;

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> temeList;


    public PredmetiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_predmeti_oas, container, false);
        Bundle bundle = this.getArguments();



        if (bundle != null) {
            forum_id = Integer.parseInt(bundle.getString("id"));
        }


        switch (forum_id)
        {
            case 4: MainActivity.gTitle.setText(getActivity().getResources().getString(R.string.oas));break;
            case 5: MainActivity.gTitle.setText(getActivity().getResources().getString(R.string.mas));break;
            case 6: MainActivity.gTitle.setText(getActivity().getResources().getString(R.string.dok));break;
            case 744: MainActivity.gTitle.setText(getActivity().getResources().getString(R.string.kat));break;
            case 745: MainActivity.gTitle.setText(getActivity().getResources().getString(R.string.mod));break;
        }

        temeList = new ArrayList<HashMap<String, String>>();
        recList = (RecyclerView) rootView.findViewById(R.id.forumPredmetiOAS);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        new GetTeme().execute();

        return rootView;
    }

    private class GetTeme extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(forum_id)));
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, nameValuePairs);



            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_TEME);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String naslov = c.getString(TAG_NASLOV);
                        String href = c.getString(TAG_HREF);
                        String autor = c.getString(TAG_AUTOR);
                        String poslednji_post;
                        String forum_id = c.getString(TAG_FORUM_ID);

                        poslednji_post = c.getString(TAG_POSLEDNJI_POST);

                        String poslednji_post_link = c.getString(TAG_POSLEDNJI_POST_LINK);


                        // tmp hashmap for single contact
                        HashMap<String, String> tema = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        tema.put(TAG_NASLOV, naslov);
                        tema.put(TAG_HREF, href);
                        tema.put(TAG_AUTOR, autor);

                        tema.put(TAG_POSLEDNJI_POST, poslednji_post);
                        tema.put(TAG_POSLEDNJI_POST_LINK, poslednji_post_link);
                        tema.put(TAG_FORUM_ID,forum_id);
                        // adding contact to contact list
                        temeList.add(tema);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            PredmetiAdapter adapter = new PredmetiAdapter(temeList, forum_id,getActivity());
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            if(sharedPref.getBoolean("boxEmpty", false))
            {
                adapter.setEmptyFilter();
                recList.setAdapter(adapter);
                Log.e("EMPTY FILTER","TRUE");
            }
            else {
                recList.setAdapter(adapter);
            }






            recList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    AutofitTextView tv = (AutofitTextView) view.findViewById(R.id.tvTitle);
                    RelativeLayout title = (RelativeLayout) view.findViewById(R.id.click_area);

                    Bundle bundle = new Bundle();
                    bundle.putString("id", tv.getTag().toString());
                    final Fragment temeFragment = new TemeFragment();
                    temeFragment.setArguments(bundle);
                    title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, temeFragment).addToBackStack("backStack");
                            fragmentTransaction.commit();
                        }
                    });



                }
            }));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_teme, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query){

        ((PredmetiAdapter) recList.getAdapter()).setFilter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (s.isEmpty()) {

            if(sharedPref.getBoolean("boxEmpty",false)) {

                ((PredmetiAdapter) recList.getAdapter()).setEmptyFilter();
            }
            ((PredmetiAdapter) recList.getAdapter()).flushFilter();
        }

         ((PredmetiAdapter) recList.getAdapter()).setFilter(s);
        if(sharedPref.getBoolean("boxEmpty",false))
        {
            ((PredmetiAdapter) recList.getAdapter()).setEmptyFilter();
        }
        return false;
    }
}