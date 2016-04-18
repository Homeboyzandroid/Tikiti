package fixxit.homeboyz.tikiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TicketPayInfo extends AppCompatActivity {

    //declaration of values
    RequestQueue requestQueue;
    String mpesaAccId = "";
    String amountId = "";
    String mpesaid = "";

    private Bundle extras;
    private int eventid;

    TextView tvmpesaid, tvpayid,tvamount,tvmpesaacc;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_pay_info);


        //all the intent pass activities are done here

       // final Intent intent= getIntent();
        tvmpesaid = (TextView)findViewById(R.id.tvbsno);
        tvpayid = (TextView)findViewById(R.id.tvpayid);
        tvamount = (TextView)findViewById(R.id.tvamount);
        tvmpesaacc = (TextView)findViewById(R.id.tvacccno);

        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get message value from intent
        String message = intent.getStringExtra("mpesaAcc");
        String message1 = intent.getStringExtra("categoryId");

        // 3. show message on textView
        tvmpesaid.setText(message);
        tvpayid.setText(message1);

        ok = (Button)findViewById(R.id.buttonok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Thanks",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivityDrawer.class));
            }
        });

        String requestUrl = "http://tikiti-tech.co.ke/TikitiAPI/api/v1/list/getEventTicketSubCategories/" + message1;

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

                        mpesaAccId +=title;
                        amountId += mpesa;
                        //  mpesaid += mpesa;


                    }

                    tvmpesaacc.setText(mpesaAccId);
                    tvamount.setText(amountId);
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

    public boolean onOptionsItemSelected(MenuItem item){

        return true;

    }
}