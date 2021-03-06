package com.tikiti.event.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tikiti.event.Adapters.AppController;
import com.tikiti.event.Adapters.CustomListAdapter;
import com.tikiti.event.EventDetails;
import com.tikiti.event.R;
import com.tikiti.event.Utils.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



/**
 * Created by homeboyz on 3/31/16.
 */
public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;


    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static EventsFragment newInstance(String text){
        EventsFragment mFragment = new EventsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    public static final String Event_ID = "event_id";
    public static final String event_name = "event_name";

    Bitmap b;

    private ListView lvevents;
    //    private String universitiesUrl = "http://10.0.2.2/php_tizzi/php_list_db_example/universities.php";
    private String eventsUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getAllActiveEvents";
    private String tag_json_obj = "request_unis_list";

    private CustomListAdapter adapter;
    private ArrayList<Event> unisList = new ArrayList<Event>();

    private ProgressDialog mProgress;
    public String jsonValue;

    String title;
    String location;
    String description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);


        lvevents = (ListView) view.findViewById(R.id.lvevents);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);


        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchdata();

                                    }
                                }
        );

        lvevents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(),""+unisList.get(position).getId(),Toast.LENGTH_SHORT).show();

                Intent intent_more_details = new Intent(getActivity(), EventDetails.class);
                //intent_more_details.putExtra(MoreDetailsActivity.UNIVERSITY_ID, unisList.get(position).getId());
                int itemid = unisList.get(position).getId();
                intent_more_details.putExtra("eventId", itemid);
                String item = unisList.get(position).getTitle();
                String image = unisList.get(position).getImage();
                String itemdec = unisList.get(position).getDescription();
                intent_more_details.putExtra("eventName", item);

                intent_more_details.putExtra("description", itemdec);

                //passing location
                String itemlocation = unisList.get(position).getLocation();
                intent_more_details.putExtra("eventLocation", itemlocation);

                //passing date to the other activity
                String itemdate = unisList.get(position).getDate().split("T")[0];
                Date createdOn = new Date();
                final SimpleDateFormat ft = new SimpleDateFormat("MM dd yyyy", Locale.US);
                String formattedDate = ft.format(createdOn);

                intent_more_details.putExtra("eventStart", itemdate);

                //passing image

                intent_more_details.putExtra("imageUrl", image);


                startActivity(intent_more_details);


            }
        });


        return view;

    }
    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchdata();
    }

    private void fetchdata() {

     //   showProgress();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, eventsUrl, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {//checking if response is null
                    try {
                        JSONArray unisArray = response.getJSONArray("eventsList");
                        System.err.println(unisArray);
                        for (int i = 0; i < unisArray.length(); i++) {
                            JSONObject unisItem = unisArray.getJSONObject(i);

                            int id = unisItem.getInt("eventId");
                            String event_name = unisItem.getString("eventName");
                            String description = unisItem.getString("description");
                            String image = unisItem.getString("imageUrl");
                            String location = unisItem.getString("eventLocation");

                            String date = unisItem.getString("eventStart").split("T")[0];

                            Event universitiesModel = new Event(id, event_name,description, image, location, date, null, null);
                            unisList.add(universitiesModel);

                            addToAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

///                stopProgress();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    try {
                        Toast.makeText(getActivity(),
                                "Network Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof ServerError) {
                    try {
                        Toast.makeText(
                                getActivity(),
                                "Problem Connecting to Server. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    try {
                        Toast.makeText(getActivity(),
                                "No Connection", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof TimeoutError) {
                    try {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Timeout Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                }

                //stopProgress();
                swipeRefreshLayout.setRefreshing(false);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

    }


    private void addToAdapter() {
        adapter = new CustomListAdapter(getActivity(), unisList);
        adapter.notifyDataSetChanged();
        lvevents.setAdapter(adapter);

    }
}
