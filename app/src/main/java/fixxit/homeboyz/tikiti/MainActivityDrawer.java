package fixxit.homeboyz.tikiti;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import fixxit.homeboyz.tikiti.Fragments.EventsFragment;

public class MainActivityDrawer extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    @Override
    public void onInt(Bundle savedInstanceState) {

            // User Information
            this.userName.setText("Tikiti");
            this.userBackground.setImageResource(R.drawable.swybackimg);

            // Creating items navigation
            mHelpLiveo = new HelpLiveo();
            mHelpLiveo.add(getString(R.string.event), R.mipmap.ic_launcher, 7);
            //with(this, Navigation.THEME_DARK). add theme dark
            //with(this, Navigation.THEME_LIGHT). add theme light

            with(this) // default theme is dark
                    .startingPosition(0) //Starting position in the list
                    .addAllHelpItem(mHelpLiveo.getHelp())
                    .footerItem(R.string.setting, R.mipmap.ic_launcher)
                    .setOnClickUser(onClickPhoto)
                    .setOnPrepareOptionsMenu(onPrepare)
                    .setOnClickFooter(onClickFooter)
                    .build();
        }

    @Override //The "R.id.container" should be used in "beginTransaction (). Replace"
    public void onItemClick(int position) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new EventsFragment().newInstance(mHelpLiveo.get(position).getName());

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }
    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };
    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };
    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

}



