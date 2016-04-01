package fixxit.homeboyz.tikiti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import fixxit.homeboyz.tikiti.Adapters.AppController;




public class EventDetails extends AppCompatActivity {


    public static String Event_ID = "event_name";

    public static int holder = Integer.parseInt(Event_ID);
    public static final String event_name = "event_name";
    private String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketCategories/" + holder;


    private Bundle extras;
    private ProgressDialog mProgress;
    private String tag_json_obj = "request_single_uni_details";// Tag used to cancel the request

    private int eventid;
    private String eventname;

    private TextView txttitle, txtdesc, txtlocation, txtdate, txtLng;
    private Button btnbuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();
        if (extras != null) {
            eventid = extras.getInt(String.valueOf(holder));
            eventname = extras.getString(event_name);
        }

        txttitle = (TextView) findViewById(R.id.title);
        txtdesc = (TextView) findViewById(R.id.desc);
        txtlocation = (TextView) findViewById(R.id.location);

        fetchDetails();
        Test();
    }

    private void fetchDetails() {

        showProgress();


        Uri.Builder builder = Uri.parse(requestUrl).buildUpon();
        //        builder.appendQueryParameter("event_id", Integer.toString(eventid));

        // http://testing.mlab-training.devs.mobi/php_list_db_example/universityinfo.php?university_id=2
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, builder.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    txttitle.setText(response.getString("eventName"));
                    txtdesc.setText(response.getString("description"));
                    txtlocation.setText(response.getString("eventLocation"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stopProgress();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    try {
                        Toast.makeText(getApplicationContext(),
                                "Network Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof ServerError) {
                    try {
                        Toast.makeText(
                                getApplicationContext(),
                                "Problem Connecting to Server. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    try {
                        Toast.makeText(getApplicationContext(),
                                "No Connection", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof TimeoutError) {
                    try {
                        Toast.makeText(
                                getApplicationContext().getApplicationContext(),
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
    }

    private void showProgress() {
        mProgress = ProgressDialog.show(EventDetails.this, "Please Wait",
                "Accessing server...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }

    public void Test() {
        // String s = "fred";  // use this if you want to test the exception below
        String s = "100";

        try {
            // the String to int conversion happens here
            int i = Integer.parseInt(s.trim());

            // print out the value after the conversion
            System.out.println("int i = " + i);

        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            Log.d("NumberFormatException: ", "+ nfe.getMessage()");
        }
    }
}