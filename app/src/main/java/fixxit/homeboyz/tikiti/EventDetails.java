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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fixxit.homeboyz.tikiti.Adapters.AppController;
import fixxit.homeboyz.tikiti.Utils.Event;


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

        imageView = (ImageView)findViewById(R.id.image);
        image= String.valueOf(intent.getStringExtra("imageUrl"));
        Log.d("LOG_TAG",image);
        Picasso.with(this)
                .load(image)
                .into(imageView);

        btnbuy = (Button) findViewById(R.id.buttontct);
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starting the intent for payments
                Intent intent_more_details = new Intent(getApplicationContext(), TicketPayInfo.class);
                //int id;
                int x= eventid;
                intent_more_details.putExtra("categoryId",x);

                startActivity(new Intent(getApplicationContext(),TicketPayInfo.class));
            }
        });

        //this is where am passing my id and getting the url/id
        extras = getIntent().getExtras();
        if (extras != null) {
            eventid = extras.getInt(String.valueOf(eventid));
        }

        //tveventname = (TextView) findViewById(R.id.tvname);
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
                        mpesaid = mpesa;


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


    }
}
