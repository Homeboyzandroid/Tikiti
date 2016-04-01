package fixxit.homeboyz.tikiti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import fixxit.homeboyz.tikiti.Fragments.EventsFragment;
import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

public class Main2Activity extends GoogleNavigationDrawer implements GAccountListener {

    @Override
    public void init(Bundle savedInstanceState) {

        // add first account
        GAccount account = new GAccount("NeoKree","neokree@gmail.com",this.getResources().getDrawable(R.drawable.photo),this.getResources().getDrawable(R.drawable.bamboo));
        this.addAccount(account);

        // set listener
        this.setAccountListener(this);

        // add your sections
        this.addSection(this.newSection("Section 1",new EventsFragment()));
        this.addSection(this.newSection("Section 2",new EventsFragment()));
        this.addDivisor();
        this.addSection(this.newSection("Recorder",this.getResources().getDrawable(R.drawable.ic_mic_white_24dp),new EventsFragment()).setNotifications(10));

        // add custom colored section with icon
        this.addSection(this.newSection("Night Section", this.getResources().getDrawable(R.drawable.ic_hotel_grey600_24dp), new EventsFragment())
                .setSectionColor(Color.parseColor("#2196f3")).setNotifications(150)); // material blue 500

        this.addDivisor();
        // add custom colored section with only text
        this.addSection(this.newSection("Last Section", new EventsFragment()).setSectionColor((Color.parseColor("#ff9800")))); // material orange 500

        Intent i = new Intent(this,MainActivity.class);
        this.addSection(this.newSection("Settings",this.getResources().getDrawable(R.drawable.ic_settings_black_24dp),i));

    }

    @Override
    public void onAccountOpening(GAccount account) {

    }
}