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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radoman.masinac.R;
import com.radoman.masinac.adapter.NaslovnaAdapter;
import com.radoman.masinac.model.RecyclerItemClickListener;
import com.radoman.masinac.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://ferometal.rs/parser.php/";
    private static final String TAG_TEME = "teme";
    private static final String TAG_NASLOV = "naslov";
    private static final String TAG_HREF = "href";
    private static final String TAG_AUTOR = "autor";
    private static final String TAG_UPDATE = "update";
    private static final String TAG_POSLEDNJI_POST = "poslednji_post";
    private static final String TAG_POSLEDNJI_POST_LINK = "poslednji_post_link";

    JSONArray contacts = null;
    RecyclerView recList;

    ArrayList<HashMap<String, String>> temeList;

    public HomeFragment() {
        // Required empty public constructor



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        MainActivity.isWebViewVisible = false;
        temeList = new ArrayList<HashMap<String, String>>();
        MainActivity.gTitle.setText(R.string.pocetna);
        recList = (RecyclerView) rootView.findViewById(R.id.forumNaslovna);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        try {
            new GetTeme().execute().get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST);




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

                        String poslednji_post = c.getString(TAG_POSLEDNJI_POST);
                        String poslednji_post_link = c.getString(TAG_POSLEDNJI_POST_LINK);


                        // tmp hashmap for single contact
                        HashMap<String, String> tema = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        tema.put(TAG_NASLOV, naslov);
                        tema.put(TAG_HREF, href);
                        tema.put(TAG_AUTOR, autor);

                        tema.put(TAG_POSLEDNJI_POST, poslednji_post);
                        tema.put(TAG_POSLEDNJI_POST_LINK, poslednji_post_link);
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


            NaslovnaAdapter adapter = new NaslovnaAdapter(temeList);
            recList.setAdapter(adapter);

            recList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView tv = (TextView)view.findViewById(R.id.txtTema);

                    Bundle bundle = new Bundle();
                    bundle.putString("id", tv.getTag().toString());
                    Fragment predmetiFragment = new PredmetiFragment();
                    predmetiFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, predmetiFragment).addToBackStack("backStack");
                    fragmentTransaction.commit();

                }
            }));

        }

    }

}
