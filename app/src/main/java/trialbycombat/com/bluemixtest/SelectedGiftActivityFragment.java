package trialbycombat.com.bluemixtest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectedGiftActivityFragment extends Fragment {
private GiftConnection selectedGift;

    private TextView txtSelectedGiftDescription,txtSelectedGiftRecepientName;
    private ImageView imgSelectedGiftRecepient;
    private RelativeLayout lytSelectedGift;

    public SelectedGiftActivityFragment() {
    }

    SelectedGiftActivityFragment activeFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_selected_gift, container, false);
        initUIElements(v);
        activeFragment=this;
        return v;
    }

    private Button btnStartGiftSending;

    private void initUIElements(View view) {
        selectedGift=((SelectedGiftActivity)getActivity()).GetSelectedGift();
        txtSelectedGiftDescription= (TextView)view.findViewById(R.id.txtSelectedGiftDescription);
        txtSelectedGiftRecepientName= (TextView)view.findViewById(R.id.txtSelectedGiftRecepientName);
        imgSelectedGiftRecepient= (ImageView)view.findViewById(R.id.imgSelectedGiftRecepient);
        lytSelectedGift= (RelativeLayout)view.findViewById(R.id.lytSelectedGift);

        txtSelectedGiftDescription.setText("Hoşgeldin \n"+selectedGift.getName()+" Bebek");
        txtSelectedGiftRecepientName.setText(selectedGift.getName()+" "+selectedGift.getSurname());
        Bitmap bMap = BitmapFactory.decodeByteArray(selectedGift.getPhoto().getBytes(), 0, selectedGift.getPhoto().length());
        imgSelectedGiftRecepient.setImageBitmap(bMap);

        switch(selectedGift.getEventtype())
        {
            case "1":
                lytSelectedGift.setBackgroundResource(R.drawable.baby_girl_bg2);
                txtSelectedGiftRecepientName.setTextColor(Color.parseColor("#cccccc"));
                txtSelectedGiftDescription.setTextColor(Color.parseColor("#efb8df"));
                break;
            case "2":
                lytSelectedGift.setBackgroundResource(R.drawable.baby_boy_bg);
                txtSelectedGiftRecepientName.setTextColor(Color.parseColor("#cccccc"));
                txtSelectedGiftDescription.setTextColor(Color.parseColor("#30d0f2"));
                break;
            case "3":
                lytSelectedGift.setBackgroundResource(R.drawable.just_married_bg);
                txtSelectedGiftRecepientName.setTextColor(Color.parseColor("#000059"));
                txtSelectedGiftDescription.setTextColor(Color.parseColor("#ffa500"));
                break;
            default:
                lytSelectedGift.setBackgroundResource(R.drawable.baby_girl_bg2);
                txtSelectedGiftRecepientName.setTextColor(Color.parseColor("#cccccc"));
                txtSelectedGiftDescription.setTextColor(Color.parseColor("#efb8df"));
                break;
        }

        btnStartGiftSending= (Button) view.findViewById(R.id.btnStartGiftSending);
        btnStartGiftSending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("chosenGiftConnection", selectedGift); //Optional parameters
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
