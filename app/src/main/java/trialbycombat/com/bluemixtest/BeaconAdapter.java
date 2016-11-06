package trialbycombat.com.bluemixtest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.altbeacon.beacon.*;
import org.altbeacon.beacon.Beacon;

/**
 * Created by IS96266 on 4.11.2016 - 17:15.
 */
public class BeaconAdapter extends ArrayAdapter<Beacon> {

    Context context;
    int layoutResourceId;
    Beacon data[] = null;

    public BeaconAdapter(Context context, int layoutResourceId, Beacon[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Beacon holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

           // holder = new WeatherHolder();
           // holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
           // holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
         //   holder = (WeatherHolder)row.getTag();
        }

        Beacon beacon = data[position];
       // holder.txtTitle.setText(weather.title);
       // holder.imgIcon.setImageResource(weather.icon);

        return row;
    }
}
