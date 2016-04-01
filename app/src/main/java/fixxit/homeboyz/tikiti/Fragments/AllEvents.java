package fixxit.homeboyz.tikiti.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fixxit.homeboyz.tikiti.Adapters.AppController;
import fixxit.homeboyz.tikiti.Adapters.CustomListAdapter;
import fixxit.homeboyz.tikiti.R;
import fixxit.homeboyz.tikiti.Utils.Event;


public class AllEvents extends Fragment {

    // Log tag
    private static final String TAG = AllEvents.class.getSimpleName();
    private static final String url = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getAllActiveEvents";
    private ProgressDialog pDialog;
    private List<Event> eventlist = new ArrayList<Event>();
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        listView  = (ListView)v.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(),eventlist);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());git
        //showing progress dialog before making volley request
        pDialog.setMessage ("loading all events..");
        pDialog.show();

        //creating volley request
        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {
                            Log.d("JsonArray",response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject obj = response.getJSONObject(i);
                            System.err.println(">>>> " + obj);
                                Event event = new Event();
                                event.setTitle(obj.getString("eventName"));
                                event.setThumbnailUrl(obj.getString("imageUrl"));
                                event.setDate(obj.getInt("eventStart"));

                                // adding movie to movies array
                                eventlist.add(event);

                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }




}