package fixxit.homeboyz.tikiti;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetTicket extends AppCompatActivity {
    public static final String EVENT_ID = "event_Id";


    private int eventid;
    private String eventName;
    private Bundle extras;

    RequestQueue requestQueue;
    String data = "";


    private TextView txttitle, txtdesc, txtlocation, txtdate, txtLng, tvcatname,tvid;
    private Button btnbuy;
    ImageView imageView;
    public String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ticket);


        Intent intent= getIntent();
        extras = getIntent().getExtras();
        tvid = (TextView)findViewById(R.id.tvid);
       // tvid.setText(Integer.toString(extras.getInt("eventId")));

        txttitle = (TextView) findViewById(R.id.tveventname);
        txttitle.setText(getIntent().getExtras().getString("eventName"));
        tvcatname = (TextView) findViewById(R.id.catname);

        txtdesc = (TextView) findViewById(R.id.tvdescription);
        txtdesc.setText(getIntent().getExtras().getString("description"));

        txtlocation = (TextView) findViewById(R.id.tvloc);
        txtlocation.setText(getIntent().getExtras().getString("eventLocation"));

        txtdate = (TextView) findViewById(R.id.tvtime);
        txtdate.setText(getIntent().getExtras().getString("eventStart"));

        imageView = (ImageView)findViewById(R.id.image);
        image= String.valueOf(intent.getStringExtra("imageUrl"));
        Log.d("LOG_TAG",image);
        Picasso.with(this)
                .load(image)
                .into(imageView);

        btnbuy = (Button) findViewById(R.id.buttontct);

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

                        // int id = Integer.parseInt(jsonObject.optString("id").toString());
                        String title = jsonObject.getString("categoryName");

                        data += "  Event is:: "+title ;
                    }

                    tvcatname.setText(data);
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
