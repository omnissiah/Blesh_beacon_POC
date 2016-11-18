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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectedGiftActivityFragment extends Fragment {
private GiftConnection selectedGift;

    private TextView txtSelectedGiftDescription,txtSelectedGiftRecepientName;
    private ImageView imgSelectedGiftRecepient;
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

        txtSelectedGiftDescription.setText(selectedGift.getName());
        txtSelectedGiftRecepientName.setText(selectedGift.getName());
        Bitmap bMap = BitmapFactory.decodeByteArray(selectedGift.getPhoto().getBytes(), 0, selectedGift.getPhoto().length());
        imgSelectedGiftRecepient.setImageBitmap(bMap);

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
