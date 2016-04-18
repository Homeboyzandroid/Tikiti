package com.tikiti.event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EventSubcategory extends AppCompatActivity {
    String URL = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketCategories/28";
    RequestQueue requestQueue;
    String data = "";

    //declaration of values
    ImageView imageView;
   TextView tvcatname,tvamount,tvshowamout,tvtotal,tvprice,tvtickersNo;
    EditText etTicketsNo;
    Button btnpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_subcategory);

        //initialization of values
        imageView = (ImageView) findViewById(R.id.imagepay);
       tvcatname = (TextView) findViewById(R.id.catName);
        tvshowamout = (TextView) findViewById(R.id.tvshowamount);
        etTicketsNo = (EditText) findViewById(R.id.editText);

        if(savedInstanceState!=null){
            Log.d("STATE",savedInstanceState.toString());
        }

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                        try{

                            JSONArray ja = response.getJSONArray("ticketCategories");

                            for(int i=0; i < ja.length(); i++){

                                JSONObject jsonObject = ja.getJSONObject(i);

                                // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                String title = jsonObject.getString("categoryName");
                               // String url = jsonObject.getString("mpesaAcc");

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



