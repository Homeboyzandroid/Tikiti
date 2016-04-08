package fixxit.homeboyz.tikiti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import fixxit.homeboyz.tikiti.Adapters.AppController;
import fixxit.homeboyz.tikiti.Utils.Event;


public class EventDetails extends AppCompatActivity {

    private static final String TAG = "PostFragment";
    public static String Event_ID = "event_id";
    public static final String event_name = "event_name";
    private String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getAllActiveEvents";

    private WebView webView;
    private Bundle extras;
    private ProgressDialog mProgress;
    private String tag_json_obj = "request_single_uni_details";// Tag used to cancel the request

    private int eventid;
    private String eventname;

    private TextView txttitle, txtdesc, txtlocation, txtdate, txtLng;
    private Button btnbuy;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txttitle = (TextView) findViewById(R.id.evntDetailtitle);
        txttitle.setText(getIntent().getExtras().getString("eventName"));


        txtdesc = (TextView) findViewById(R.id.desc);
        txtdesc.setText(getIntent().getExtras().getString("description"));

        txtlocation = (TextView) findViewById(R.id.location);
        txtlocation.setText(getIntent().getExtras().getString("eventLocation"));

        txtdate = (TextView) findViewById(R.id.tvdate);
        txtdate.setText(getIntent().getExtras().getString("eventStart"));

        //setting or parsing the image
       imageView = (ImageView)findViewById(R.id.image);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        imageView.setImageBitmap(bitmap);

        btnbuy = (Button) findViewById(R.id.buttontct);
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EventSubcategory.class));
            }
        });
        fetchDetails();

    }


    private void fetchDetails() {
        showProgress();


        Uri.Builder builder = Uri.parse(requestUrl).buildUpon();
        builder.appendQueryParameter("event_id", Integer.toString(eventid));

        // http://testing.mlab-training.devs.mobi/php_list_db_example/universityinfo.php?university_id=2
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, builder.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {//checking if response is null
                    try {
                        JSONArray unisArray = response.getJSONArray("eventsList");
                        System.err.println(unisArray);
                        for (int i = 0; i < unisArray.length(); i++) {
                            JSONObject unisItem = unisArray.getJSONObject(i);

                            txtdesc.setText(response.getString("description"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    stopProgress();
                }
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
}
