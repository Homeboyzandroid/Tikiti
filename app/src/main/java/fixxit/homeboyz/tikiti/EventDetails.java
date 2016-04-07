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


    public static String Event_ID = "event_id";

    public static final String event_name = "event_name";
    private String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketCategories/27";


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
            eventid = extras.getInt(Event_ID);
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
        //sometimes android suck when it comes to developing with json and url that do not work
        //to make the matter worse is when you are working with guys who dont care or the person
        //who developed that api does not change it in a way that android or java can consume it
        //imagine the little saraly that am paid at homeboyz and the guys dont what to change their url
        //this is rubish and wastage of time sometimes//
        //but there is one prayer that i keep on asking my God and it is all about when i will start my own business
        /// this is my prayer everyday to start my own business in nairobi or somewhere else that will make me help
        //the people around me and my familu.
        //Another prayer God please give me a good loving and supportive husband to be the father of my kids.
        //I would like my daugher easther to one day go to an international shchool and to be doing amaizing thing
        //I would also like it when she becomes a musician a  gosple muscian and  have a beutiful voice like that of her grandmother
        //Remember my brother help githuku to leave his own live and know that no one is going to help him in life.
        //help Kiige to understand his talent cos education does not work for him
        //help Chege I love his with all his disabilities because i know God You created him with a reason and he will serve you.
        //I dnt want to be a prostiture or a loose girl and I ask D
    }

}