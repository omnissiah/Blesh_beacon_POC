package trialbycombat.com.bluemixtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PaymentActivity extends AppCompatActivity {
    private GiftConnection selectedGift;

    public GiftConnection GetSelectedGift()
    {
        return this.selectedGift;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        this.selectedGift = (GiftConnection)intent.getExtras().getParcelable("chosenGiftConnection");

        // Create new fragment and transaction
        Fragment mockIsnetLoginFragment = new MockIsnetLoginFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentHolderforPayment, mockIsnetLoginFragment);
        transaction.commit();
    }
}
