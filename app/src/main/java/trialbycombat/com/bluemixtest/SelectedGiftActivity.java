package trialbycombat.com.bluemixtest;

import android.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SelectedGiftActivity extends AppCompatActivity {
    private GiftConnection selectedGift;

    public GiftConnection GetSelectedGift()
    {
        return this.selectedGift;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.selectedGift = (GiftConnection)intent.getExtras().getParcelable("chosenGiftConnection");
        setContentView(R.layout.activity_selected_gift);

        //ActionBar bar = getActionBar();
       // bar.setTitle(selectedGift.getName()+" Bebek");

        // Create new fragment and transaction
        Fragment selectedGiftActivityFragment = new SelectedGiftActivityFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.replace(R.id.fragment, selectedGiftActivityFragment);
        transaction.commit();
    }
}
