package trialbycombat.com.bluemixtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MockIsnetLoginFragment extends Fragment {

    public MockIsnetLoginFragment() {
        // Required empty public constructor
    }

private FrameLayout lytIsnetBackGr;
    private MockIsnetLoginFragment activeFragment;

    public static MockIsnetLoginFragment newInstance( ) {
        MockIsnetLoginFragment fragment = new MockIsnetLoginFragment();
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
        View v = inflater.inflate(R.layout.fragment_mock_isnet_login, container, false);
        activeFragment = this;
        lytIsnetBackGr = (FrameLayout) v.findViewById(R.id.lytIsnetBackGr);
        lytIsnetBackGr.setBackgroundResource(R.drawable.iscep);
        lytIsnetBackGr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment paymentFragment = new PaymentFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentHolderforPayment, paymentFragment);
                transaction.commit();
            }
        });
        return v;
    }

}
