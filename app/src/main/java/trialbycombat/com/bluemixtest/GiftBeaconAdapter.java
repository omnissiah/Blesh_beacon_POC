package trialbycombat.com.bluemixtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

                LinearLayout lytListItem = (LinearLayout) rowView.findViewById(R.id.lytListItem);
                lytListItem.setOnClickListener(new View.OnClickListener() {
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
                TextView txtLastContacted = (TextView) rowView.findViewById(R.id.txtLastContacted);

                if (con != null) {
                    if (con.getName() != null) {
                        if(con.getEventtype()!=null)
                        {
                            switch(con.getEventtype())
                            {
                                case "1":
                                lytListItem.setBackgroundResource(R.drawable.baby_girl_bg);
                                    txtRecepientName.setTextColor(Color.parseColor("#383535"));
                                    txtEventDescription.setTextColor(Color.parseColor("#f27acf"));
                                    txtDistance.setTextColor(Color.parseColor("#f27acf"));
                                    txtLastContacted.setTextColor(Color.parseColor("#f27acf"));
                                    break;
                                case "2":
                                    lytListItem.setBackgroundResource(R.drawable.baby_boy_bg);
                                    txtRecepientName.setTextColor(Color.parseColor("#383535"));
                                    txtEventDescription.setTextColor(Color.parseColor("#30d0f2"));
                                    txtDistance.setTextColor(Color.parseColor("#30d0f2"));
                                    txtLastContacted.setTextColor(Color.parseColor("#30d0f2"));
                                    break;
                                case "3":
                                    lytListItem.setBackgroundResource(R.drawable.tema_bg);
                                    txtRecepientName.setTextColor(Color.parseColor("#ffa500"));
                                    txtEventDescription.setTextColor(Color.parseColor("#f2f2f2"));
                                    txtDistance.setTextColor(Color.parseColor("#f2f2f2"));
                                    txtLastContacted.setTextColor(Color.parseColor("#f2f2f2"));
                                    break;
                                default:
                                    lytListItem.setBackgroundResource(R.drawable.baby_girl_bg);
                                    txtRecepientName.setTextColor(Color.parseColor("#383535"));
                                    txtEventDescription.setTextColor(Color.parseColor("#f27acf"));
                                    txtDistance.setTextColor(Color.parseColor("#f27acf"));
                                    txtLastContacted.setTextColor(Color.parseColor("#f27acf"));
                                    break;
                            }
                        }

                        txtRecepientName.setText(con.getName() + " "+con.getSurname());
                        txtEventDescription.setText(con.getDescription());

                    } else {
                        return new View(context);
                        //TODO decide if non existant beaconID's will show in list and if so how?
                        //txtRecepientName.setText(con.getBeaconid().substring(0, 5) + "..." + con.getBeaconid().substring(con.getBeaconid().length() - 6, con.getBeaconid().length()-1));
                        //txtRecepientName.setTextSize(8);
                        //txtEventDescription.setTextSize(12);
                        //txtEventDescription.setText("Tanımsız");
                        //lytListItem.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    }

                    txtDistance.setText(String.format("%.2f", con.getBeaconDistance()) + " m");
                    txtLastContacted.setText((System.currentTimeMillis()- con.getLastContactedTime())/1000+" sn");
                    rowView.setTag(con);
                }
            }
        }

        return rowView;
    }

}
