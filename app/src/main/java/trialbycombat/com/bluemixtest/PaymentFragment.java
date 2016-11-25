package trialbycombat.com.bluemixtest;


import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PaymentFragment extends Fragment {

private Button btnFinalizePayment;
    private RelativeLayout lytFinalPaymentInfo;
    private FrameLayout lytFinishedPayment,lytChooseAccount,lytAdd5btn,lytAdd10btn,lytAdd20btn;
    private TextView txtPayemntEventDescription,txtPaymentRecepientName;
    private EditText txtPaymentAmount;
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
        txtPaymentAmount= (EditText)v.findViewById(R.id.txtPaymentAmount);

        lytAdd5btn= (FrameLayout)v.findViewById(R.id.lytAdd5btn);
        lytAdd5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log","5 added");
                txtPaymentAmount.setText("" + (Integer.parseInt((txtPaymentAmount.getText().toString().length() > 0) ? txtPaymentAmount.getText().toString() : "0") + 5), TextView.BufferType.EDITABLE);
            }
        });

        lytAdd10btn= (FrameLayout)v.findViewById(R.id.lytAdd10btn);
        lytAdd10btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log","10 added");
                txtPaymentAmount.setText("" + (Integer.parseInt((txtPaymentAmount.getText().toString().length() > 0) ? txtPaymentAmount.getText().toString() : "0") + 10), TextView.BufferType.EDITABLE);
            }
        });

        lytAdd20btn= (FrameLayout)v.findViewById(R.id.lytAdd20btn);
        lytAdd20btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log","20 added");
                txtPaymentAmount.setText("" + (Integer.parseInt((txtPaymentAmount.getText().toString().length() > 0) ? txtPaymentAmount.getText().toString() : "0") + 20), TextView.BufferType.EDITABLE);
            }
        });

        txtFinalMessage= (TextView)v.findViewById(R.id.txtFinalMessage);
        txtPayemntEventDescription= (TextView)v.findViewById(R.id.txtPayemntEventDescription);
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
