package fixxit.homeboyz.tikiti.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fixxit.homeboyz.tikiti.R;
import fixxit.homeboyz.tikiti.Utils.Event;

/**
 * Created by homeboyz on 3/31/16.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Event> listItems;
    private Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Context context, ArrayList<Event> items) {
        this.mContext = context;
        this.listItems = items;

    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {

        return listItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.event_items, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgimage = (ImageView) view.findViewById(R.id.eventimage);

            viewHolder.txtUniName = (TextView) view.findViewById(R.id.eventtitle);
            viewHolder.txtUniDesc = (TextView) view.findViewById(R.id.eventdate);
            viewHolder.txtlocation = (TextView) view.findViewById(R.id.loca);
            viewHolder.txtdate = (TextView) view.findViewById(R.id.tvtime);
            viewHolder.tvid = (TextView)view.findViewById(R.id.textView3);

            //viewHolder.txtUniId = (TextView) view.findViewById(R.id.uniId);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        Event model = listItems.get(position);
//        System.out.println(model.getTitle());
        viewHolder.tvid.setText(Integer.toString(model.getId()));
        viewHolder.txtUniName.setText(model.getTitle());
        viewHolder.txtUniDesc.setText(model.getDescription());

        viewHolder.txtlocation.setText(model.getLocation());
        viewHolder.txtdate.setText(model.getDate());
        /// Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext)
                .load(model.getImage())
                .into(viewHolder.imgimage);

        //viewHolder.txtUniId.setText(Integer.toString(model.getId()));


        return view;
    }

    private static class ViewHolder {
        TextView txtUniName;
        TextView txtUniDesc;
        TextView txtUniId;
        ImageView imgimage;
        TextView txtlocation;
        TextView txtdate;
        TextView tvid;
    }
}