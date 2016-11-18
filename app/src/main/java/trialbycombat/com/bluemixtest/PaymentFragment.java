package trialbycombat.com.bluemixtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PaymentFragment extends Fragment {

private Button btnFinalizePayment;
    private LinearLayout lytFinalPaymentInfo;
    private FrameLayout lytFinishedPayment;
private TextView txtFinalMessage;
    public PaymentFragment() {
        // Required empty public constructor
    }

    private GiftConnection currentGift;

    public static PaymentFragment newInstance( ) {
        PaymentFragment fragment = new PaymentFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_payment, container, false);
        lytFinalPaymentInfo= (LinearLayout)v.findViewById(R.id.lytFinalPaymentInfo);
        lytFinishedPayment= (FrameLayout)v.findViewById(R.id.lytFinishedPayment);
        txtFinalMessage= (TextView)v.findViewById(R.id.txtFinalMessage);

        currentGift=((PaymentActivity)getActivity()).GetSelectedGift();

        btnFinalizePayment= (Button)v.findViewById(R.id.btnFinalizePayment);
        btnFinalizePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytFinalPaymentInfo.setVisibility(View.GONE);
                lytFinishedPayment.setVisibility(View.VISIBLE);
                txtFinalMessage.setText("Gönderiliyor...");
                new Thread(){
                    public void run(){
                        ServiceHandler.PostPayment(new PaymentData(currentGift.getBeaconid(), "yiğit", "irez", "234234234", "100"));
                    }
                }.start();

                txtFinalMessage.setText("TEŞEKKÜRLER");

            }
        });
        return v;
    }

}
