package fixxit.homeboyz.tikiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TicketPayInfo extends AppCompatActivity {

    TextView tvmpesaid, tvpayid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_pay_info);
        //all the intent pass activities are done here

        final Intent intent= getIntent();
        tvmpesaid = (TextView)findViewById(R.id.tvbsno);
        tvpayid = (TextView)findViewById(R.id.tvpayid);
       // tvpayid.setText(getIntent().getExtras().getInt("categoryId"));
    }
}
