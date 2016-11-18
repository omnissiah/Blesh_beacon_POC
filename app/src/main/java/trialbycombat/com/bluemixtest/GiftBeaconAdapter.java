package trialbycombat.com.bluemixtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            if(data!= null && data.size()>(position)) {
                final GiftConnection con = data.get(position);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(layoutResourceId, parent, false);

                LinearLayout layoutListItemMain = (LinearLayout) rowView.findViewById(R.id.layoutListItemMain);
                layoutListItemMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(context, SelectedGiftActivity.class);
                        myIntent.putExtra("chosenGiftConnection", con); //Optional parameters
                        context.startActivity(myIntent);
                    }
                });
                TextView txtRecepientName = (TextView) rowView.findViewById(R.id.txtRecepientName);
                TextView txtEventDescription = (TextView) rowView.findViewById(R.id.txtEventDescription);
                TextView txtDistance = (TextView) rowView.findViewById(R.id.txtDistance);
                ImageView imgRecepient = (ImageView) rowView.findViewById(R.id.imgRecepient);

                if (con != null) {
                    if (con.getName() != null) {
                        txtRecepientName.setText(con.getName() + con.getSurname());
                        txtEventDescription.setText(con.getName());
                        txtDistance.setText(String.format("%.2f", con.getBeaconDistance()) + " m");
                        Bitmap bMap = BitmapFactory.decodeByteArray(con.getPhoto().getBytes(), 0, con.getPhoto().length());
                        imgRecepient.setImageBitmap(bMap);
                    } else {
                        txtRecepientName.setText(con.getBeaconid());
                        txtEventDescription.setText("!?!");
                        txtDistance.setText(String.format("%.2f", con.getBeaconDistance()) + " m");
                    }

                    rowView.setTag(con);
                }
            }
        }

        return rowView;
    }

}
