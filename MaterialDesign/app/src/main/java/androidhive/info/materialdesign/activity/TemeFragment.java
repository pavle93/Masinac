package androidhive.info.masinac.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidhive.info.masinac.R;
import androidhive.info.masinac.adapter.PredmetiOASAdapter;
import androidhive.info.masinac.network.ServiceHandler;


public class TemeFragment extends Fragment {
    RecyclerView recList;

    private static final String TAG_TEME = "teme";
    private static final String TAG_NASLOV = "naslov";
    private static final String TAG_HREF = "href";
    private static final String TAG_AUTOR = "autor";
    private static final String TAG_UPDATE = "update";
    private static final String TAG_POSLEDNJI_POST = "poslednji_post";
    private static final String TAG_POSLEDNJI_POST_LINK = "poslednji_post_link";

    private ProgressDialog pDialog;
    private static String url = "http://ferometal.rs/parserTeme.php/";

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> temeList;


    public TemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_predmeti_oas, container, false);
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
            nameValuePairs.add(new BasicNameValuePair("id", "63"));
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

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


            PredmetiOASAdapter adapter = new PredmetiOASAdapter(temeList);
            recList.setAdapter(adapter);


        }
    }
}
