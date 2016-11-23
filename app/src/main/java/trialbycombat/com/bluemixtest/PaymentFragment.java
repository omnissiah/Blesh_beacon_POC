package trialbycombat.com.bluemixtest;


import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PaymentFragment extends Fragment {

private Button btnFinalizePayment;
    private RelativeLayout lytFinalPaymentInfo;
    private FrameLayout lytFinishedPayment,lytChooseAccount;
    private TextView txtPayemntEventDescription,txtPaymentAmount,txtPaymentRecepientName;
    private ImageView imgAccountSelection;

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
        lytFinalPaymentInfo= (RelativeLayout)v.findViewById(R.id.lytFinalPaymentInfo);
        lytFinishedPayment= (FrameLayout)v.findViewById(R.id.lytFinishedPayment);
        txtFinalMessage= (TextView)v.findViewById(R.id.txtFinalMessage);
        txtPayemntEventDescription= (TextView)v.findViewById(R.id.txtPayemntEventDescription);
        txtPaymentAmount= (TextView)v.findViewById(R.id.txtPaymentAmount);
        txtPaymentRecepientName= (TextView)v.findViewById(R.id.txtPaymentRecepientName);

        lytChooseAccount= (FrameLayout)v.findViewById(R.id.lytChooseAccount);
        lytChooseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                lytChooseAccount.setVisibility(View.GONE);
            }
        });

        imgAccountSelection=(ImageView)v.findViewById(R.id.imgAccountSelection);
        imgAccountSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytChooseAccount.setVisibility(View.VISIBLE);
            }
        });



        currentGift=((PaymentActivity)getActivity()).GetSelectedGift();

        txtPaymentRecepientName.setText(currentGift.getName()+" "+currentGift.getSurname());
        txtPayemntEventDescription.setText(currentGift.getDescription());

        btnFinalizePayment= (Button)v.findViewById(R.id.btnFinalizePayment);
        btnFinalizePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPaymentAmount.getText()!=null && Integer.parseInt(txtPaymentAmount.getText().toString())>0) {
                    lytFinalPaymentInfo.setVisibility(View.GONE);
                    lytFinishedPayment.setVisibility(View.VISIBLE);
                    txtFinalMessage.setText("Gönderiliyor...");
                    new Thread() {
                        public void run() {
                            ServiceHandler.PostPayment(new PaymentData(currentGift.getBeaconid(), "yiğit", "irez", "234234234", txtPaymentAmount.getText().toString()));
                        }
                    }.start();

                    txtFinalMessage.setText("TEŞEKKÜRLER");
                }
            }
        });
        return v;
    }

}
