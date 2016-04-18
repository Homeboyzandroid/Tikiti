package com.tikiti.event;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    String mpesaAccId = "";
    String amountId = "";
    String mpesaid = "";


    private TextView txttitle, txtdesc, txtlocation, txtdate, txtLng, tvcatname,tvid;
    private Button btnbuy;
    ImageView imageView;
    public String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ticket);


        tvid = (TextView) findViewById(R.id.tvid);
        txttitle = (TextView) findViewById(R.id.tveventname);
        tvcatname = (TextView) findViewById(R.id.catname);

        txtdesc = (TextView) findViewById(R.id.tvdescription);

        txtlocation = (TextView) findViewById(R.id.tvloc);


        //call or rather pasing jsoon to the textviews
        String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketSubCategories/25";

        if (savedInstanceState != null) {
            Log.d("STATE", savedInstanceState.toString());
        }

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, requestUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray ja = response.getJSONArray("ticketSubCategories");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jsonObject = ja.getJSONObject(i);

                        // int id = Integer.parseInt(jsonObject.optString("categoryId").toString());
                        String title = jsonObject.getString("ticketMpesaAcc");
                        String mpesa = jsonObject.getString("ticketAmount");

                        mpesaAccId += "It's:" + title;
                        amountId += mpesa;
                        //  mpesaid += mpesa;


                    }

                    txttitle.setText(mpesaAccId);
                    tvcatname.setText(amountId);
                    //tvmpesa.setText(mpesaid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");

                    }
                }
        );
        requestQueue.add(jor);


    }
}