package com.tikiti.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tikiti.event.Utils.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;





public class EventDetails extends AppCompatActivity {

    private static final String TAG = "PostFragment";
    public static String Event_ID = "event_id";
    public static final String event_name = "event_name";
    private String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getAllActiveEvents";

    private ArrayList<Event> unisList = new ArrayList<Event>();

    private WebView webView;
    private Bundle extras;
    private ProgressDialog mProgress;
    private String tag_json_obj = "request_single_uni_details";// Tag used to cancel the request
    private int eventid;
    private String eventname;
    public String image;
    public String jsonValue;

    RequestQueue requestQueue;
    String data = "";
    String dataId = "";
    String mpesaid = "";



    private TextView txttitle, txtdesc, txtlocation, txtdate, txtLng, tvticket,tvid,tvmpesa;
    private Button btnbuy;
    ImageView imageView;
    private ArrayList<Event> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //the textviews to pass  parameters
        tvid = (TextView)findViewById(R.id.tvid);
        tvticket = (TextView) findViewById(R.id.ticket);
        tvmpesa = (TextView)findViewById(R.id.tvmpesaid);

        //all the intent pass activities are done here
        final Intent intent= getIntent();
        txttitle = (TextView) findViewById(R.id.evntDetailtitle);
        txttitle.setText(getIntent().getExtras().getString("eventName"));

        txtdesc = (TextView) findViewById(R.id.desc);
        txtdesc.setText(getIntent().getExtras().getString("description"));

        txtlocation = (TextView) findViewById(R.id.location);
        txtlocation.setText(getIntent().getExtras().getString("eventLocation"));

        txtdate = (TextView) findViewById(R.id.tvdate);
        txtdate.setText(getIntent().getExtras().getString("eventStart"));


        //String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
        String formattedDate = sdf.format(createdOn);


        imageView = (ImageView)findViewById(R.id.image);
        image= String.valueOf(intent.getStringExtra("imageUrl"));
        Log.d("LOG_TAG", image);
        Picasso.with(this)
                .load(image)
                .into(imageView);



        //this is where am passing my id and getting the url/id
        extras = getIntent().getExtras();
        if (extras != null) {
            eventid = extras.getInt(String.valueOf(eventid));
        }

        String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketCategories/" +Integer.toString(extras.getInt("eventId")) ;

        if(savedInstanceState!=null){
            Log.d("STATE", savedInstanceState.toString());
        }

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,requestUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                    JSONArray ja = response.getJSONArray("ticketCategories");

                    for(int i=0; i < ja.length(); i++){

                        JSONObject jsonObject = ja.getJSONObject(i);

                         int id = Integer.parseInt(jsonObject.optString("categoryId").toString());
                        String title = jsonObject.getString("categoryName");
                        String mpesa = jsonObject.getString("mpesaAcc");

                        data += "It's:"+title ;
                        dataId +=  id ;
                        mpesaid += mpesa;


                    }

                    tvticket.setText(data);
                    tvid.setText(dataId);
                    tvmpesa.setText(mpesaid);
                }catch(JSONException e){e.printStackTrace();}
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");

                    }
                }
        );
        requestQueue.add(jor);


        btnbuy = (Button) findViewById(R.id.buttontct);
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starting the intent for payments
                Intent intent = new Intent(getApplicationContext(), TicketPayInfo.class);
                // 2. put key/value data
                intent.putExtra("mpesaAcc", mpesaid);
                intent.putExtra("categoryId", dataId);


                // 3. or you can add data to a bundle
                Bundle extras = new Bundle();
                extras.putString("mpesaAcc", mpesaid);
                extras.putString("categoryId", dataId);

                // 4. add bundle to intent
                intent.putExtras(extras);


                //Toast.makeText(getApplicationContext(),"fuck you"+ mpesaid,Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivityDrawer.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
