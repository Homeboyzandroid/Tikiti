package fixxit.homeboyz.tikiti.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fixxit.homeboyz.tikiti.Adapters.AppController;
import fixxit.homeboyz.tikiti.Adapters.CustomListAdapter;
import fixxit.homeboyz.tikiti.EventDetails;
import fixxit.homeboyz.tikiti.R;
import fixxit.homeboyz.tikiti.Utils.Event;

/**
 * Created by homeboyz on 3/31/16.
 */
public class EventsFragment extends Fragment {

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
    /*public static final String Event_ID = "event_id";
    public static final String Event_ID = "event_id";
    public static final String Event_ID = "event_id";*/

    private ListView lvevents;
    //    private String universitiesUrl = "http://10.0.2.2/php_tizzi/php_list_db_example/universities.php";
    private String eventsUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getAllActiveEvents";
    private String tag_json_obj = "request_unis_list";

    private CustomListAdapter adapter;
    private ArrayList<Event> unisList = new ArrayList<Event>();

    private ProgressDialog mProgress;

    String title;
    String location;
    String description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2,container, false);

        lvevents = (ListView)view.findViewById(R.id.lvevents);

       lvevents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(getActivity(),""+unisList.get(position).getId(),Toast.LENGTH_SHORT).show();
              Intent intent_more_details = new Intent(getActivity(), EventDetails.class);
              //intent_more_details.putExtra(MoreDetailsActivity.UNIVERSITY_ID, unisList.get(position).getId());
              String item = unisList.get(position).getTitle();
              String image = unisList.get(position).getImage();
              String itemdate = unisList.get(position).getDate();
              String itemdec = unisList.get(position).getDescription();
              intent_more_details.putExtra("eventName", item);

              intent_more_details.putExtra("eventStart", itemdate);
              intent_more_details.putExtra("description",itemdec);
              startActivity(intent_more_details);

            /*  //passing image
              Bitmap bitmap = BitmapFactory.decodeResource(getResources(),0);
              ByteArrayOutputStream bytes = new ByteArrayOutputStream();
              bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
              intent_more_details.putExtra("imageUrl",bytes.toByteArray());*/
          }
       });


        showProgress();

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
                            //String desc = unisItem.getString("description");

                            Event universitiesModel = new Event(id, event_name,description, image, null, null, null, null);
                            unisList.add(universitiesModel);

                            addToAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                stopProgress();
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

                stopProgress();

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
        return view;
    }

    private void addToAdapter() {
        adapter = new CustomListAdapter(getActivity(), unisList);
        adapter.notifyDataSetChanged();
        lvevents.setAdapter(adapter);

    }


    private void showProgress() {
        mProgress = ProgressDialog.show(getActivity(), "Please Wait",
                "Accessing server...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }



}
