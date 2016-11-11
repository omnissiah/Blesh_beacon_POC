package trialbycombat.com.bluemixtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.altbeacon.beacon.*;
import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * Created by IS96266 on 4.11.2016 - 17:15.
 */
public class GiftBeaconAdapter extends ArrayAdapter<GiftConnection> {

    Context context;
    int layoutResourceId;
    List<GiftConnection> data = null;

    public GiftBeaconAdapter(Context context, int layoutResourceId, List<GiftConnection> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, parent, false);

            TextView txtRecepientName= (TextView)rowView.findViewById(R.id.txtRecepientName);
            TextView txtEventDescription= (TextView)rowView.findViewById(R.id.txtEventDescription);
            TextView txtDistance= (TextView)rowView.findViewById(R.id.txtDistance);
            ImageView imgRecepient= (ImageView)rowView.findViewById(R.id.imgRecepient);

            if(data.size()>0) {
                if(data.get(position).getName()!=null)
                {
                    txtRecepientName.setText(data.get(position).getName() + data.get(position).getSurname());
                    txtEventDescription.setText(data.get(position).getName());
                    txtDistance.setText(String.format("%.2f", data.get(position).getBeaconDistance()) + " m");
                    Bitmap bMap = BitmapFactory.decodeByteArray(data.get(position).getPhoto().getBytes(), 0, data.get(position).getPhoto().length());
                    imgRecepient.setImageBitmap(bMap);
                }
                else
                {
                    txtRecepientName.setText(data.get(position).getBeaconid());
                    txtEventDescription.setText("!?!");
                    txtDistance.setText(String.format("%.2f", data.get(position).getBeaconDistance()) + " m");
                }

                rowView.setTag(data.get(position));
            }

        }

        return rowView;
    }
}
