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
import com.radoman.masinac.adapter.TemeAdapter;
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


public class TemeFragment extends Fragment {
    RecyclerView recList;

    private static final String TAG_TEME = "teme";
    private static final String TAG_NASLOV = "naslov";
    private static final String TAG_DATUM = "datum";
    private static final String TAG_AUTOR = "autor";
    private static final String TAG_ATTACHMENT = "attachment";
    private static final String TAG_THREAD_ID = "thread_id";


    private ProgressDialog pDialog;
    private static String url = "http://ferometal.rs/parserTeme.php/";

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> temeList;

    private static int forum_id;


    public TemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET,nameValuePairs);



            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_TEME);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String naslov = c.getString(TAG_NASLOV);
                        String autor = c.getString(TAG_AUTOR);
                        String datum = c.getString(TAG_DATUM);
                        String attachment = c.getString(TAG_ATTACHMENT);
                        String forum_id = c.getString(TAG_THREAD_ID);


                        // tmp hashmap for single contact
                        HashMap<String, String> tema = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        tema.put(TAG_NASLOV, naslov);
                        tema.put(TAG_AUTOR, autor);
                        tema.put(TAG_DATUM, datum);
                        tema.put(TAG_ATTACHMENT, attachment);
                        tema.put(TAG_THREAD_ID, forum_id);

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


            TemeAdapter adapter = new TemeAdapter(temeList,getActivity());
            recList.setAdapter(adapter);

            recList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView tv = (TextView) view.findViewById(R.id.title);

                    Bundle bundle = new Bundle();
                    bundle.putString("thread_id", tv.getTag().toString());
                    bundle.putString("forum_id", String.valueOf(forum_id));
                    Fragment PostFragment = new PostFragment();
                    PostFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, PostFragment).addToBackStack("backStack");
                    fragmentTransaction.commit();

                }
            }));


        }
    }





}
