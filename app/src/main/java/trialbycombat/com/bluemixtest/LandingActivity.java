package trialbycombat.com.bluemixtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LandingActivity extends AppCompatActivity {

    private Context activeContext;
    private ImageView imgExtendedApps,imgLandingOrg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        activeContext=this;

        imgExtendedApps = (ImageView) this.findViewById(R.id.imgExtendedApps);
        imgExtendedApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(activeContext, MainActivity.class);
                activeContext.startActivity(myIntent);
                finish();
            }
        });

        imgLandingOrg = (ImageView) this.findViewById(R.id.imgLandingOrg);
        imgLandingOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgLandingOrg.setVisibility(View.GONE);
                imgExtendedApps.setVisibility(View.VISIBLE);
            }
        });
    }
}
