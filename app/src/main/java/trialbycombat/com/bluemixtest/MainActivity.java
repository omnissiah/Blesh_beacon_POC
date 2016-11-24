
// To detect proprietary beacons, you must add a line like below corresponding to your beacon
// type.  Do a web search for "setBeaconLayout" to get the proper expression.
// beaconManager.getBeaconParsers().add(new BeaconParser().
//        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        /*
        m - matching byte sequence for this beacon type to parse (exactly one required)
        s - ServiceUuid for this beacon type to parse (optional, only for Gatt-based beacons)
        i - identifier (at least one required, multiple allowed)
        p - power calibration field (exactly one required)
        d - data field (optional, multiple allowed)
        x - extra layout. Signifies that the layout is secondary to a primary layout with the same matching byte sequence (or ServiceUuid). Extra layouts
        do not require power or identifier fields and create Beacon objects without identifiers.
        Example of a parser string for AltBeacon:
        "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"

        This signifies that the beacon type will be decoded when an advertisement is found with 0xbeac in bytes 2-3, and a three-part identifier will be pulled out of bytes
        4-19, bytes 20-21 and bytes 22-23, respectively. A signed power calibration value will be pulled out of byte 24, and a data field will be pulled out of byte 25
        ALTBEACON      m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25
        EDDYSTONE TLM  x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15
        EDDYSTONE UID  s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19
        EDDYSTONE URL  s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v
        IBEACON        m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24
        m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24

        */

package trialbycombat.com.bluemixtest;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    private MainActivity mainAct;
    private Button btnStartBeaconSearch;
    private BeaconManager beaconManager;
    private FrameLayout frmBackground;
    private ListView beaconListView;
    List<GiftConnection> giftConnectionList;
    private boolean beaconPreviouslyAdded=false;
private Animation pulse;
private ArrayAdapter beaconAdapter;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = BeaconManager.getInstanceForApplication(this);


        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        initUIElements();
        initPermissions();

    }

    private void initPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog){
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    private void initUIElements() {
        //custom toolbar
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(getApplicationName(this));

        mActionBar.setCustomView(mCustomView);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        //custom toolbar end

        mainAct = this;
         beaconListView= (ListView) findViewById(R.id.beacon_list);
        btnStartBeaconSearch = (Button) findViewById(R.id.btnStartBeaconSearch);
        frmBackground = (FrameLayout) findViewById(R.id.frmBackground);
        giftConnectionList=new ArrayList<>();

        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        pulse.setRepeatCount(Animation.INFINITE);

        btnStartBeaconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(frmBackground);

                if (frmBackground.getVisibility() == View.GONE) {
                    btnStartBeaconSearch.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    giftConnectionList.clear();
                    beaconManager.bind(mainAct);
                    btnStartBeaconSearch.startAnimation(pulse);
                } else {
                    btnStartBeaconSearch.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    beaconManager.unbind(mainAct);
                    btnStartBeaconSearch.clearAnimation();
                }
            }
        });
    }

    private  String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    private String getBeaconHex(List<Identifier> identifiers) {
        return identifiers.get(0).toString().replace("-", "").toUpperCase()
                +String.format("%04X",Long.parseLong(Integer.toHexString(Integer.parseInt(identifiers.get(1).toString().toUpperCase())),16))
                +String.format("%04X",Long.parseLong(Integer.toHexString(Integer.parseInt(identifiers.get(2).toString().toUpperCase())), 16));
    }


    private void setListContent()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beaconAdapter = new GiftBeaconAdapter(mainAct, R.layout.activity_listview, giftConnectionList);
                beaconListView.setAdapter(beaconAdapter);
            }
        });
    }

    private void toggleVisibility(final ViewGroup layout)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(layout.getVisibility()== View.GONE)
                    layout.setVisibility(View.VISIBLE);
                else
                    layout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 1:firsttime
     * 2:seeing
     * 3:just lost contact
     * 4:Not seeing
     * @param seeingType

    private void changeSeeingStatus(final int seeingType)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (seeingType == 1)
                    btnIsSeeing.setBackgroundColor(Color.GREEN);
                else if (seeingType == 2)
                    btnIsSeeing.setBackgroundColor(Color.BLUE);
                else if (seeingType == 3)
                    btnIsSeeing.setBackgroundColor(Color.RED);
                else if (seeingType == 4)
                    btnIsSeeing.setBackgroundColor(Color.GRAY);
            }
        });
    } */

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //txtBeaconResult.append("\ncoarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    private Collection<org.altbeacon.beacon.Beacon> beaconInsider;

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> beacons, Region region) {
                beaconInsider = beacons;

                for (Beacon beacon:beaconInsider)
                {
                    beaconPreviouslyAdded=false;
                    if(beacon!=null)
                    {
                        GiftConnection giftConnection=null;
                        if(giftConnectionList.size()==0) {
                            giftConnection = ServiceHandler.GetBeacon(getBeaconHex(beacon.getIdentifiers()));
                            if (giftConnection != null) {
                                giftConnection.setBeaconDistance(beacon.getDistance());
                                giftConnection.setLastContactedTime(System.currentTimeMillis());
                                giftConnectionList.add(giftConnection);
                            }
                            else
                                giftConnectionList.add(new GiftConnection(getBeaconHex(beacon.getIdentifiers()), beacon.getDistance(),System.currentTimeMillis()));
                        }
                        else
                        {
                            for (int j=0;j<giftConnectionList.size();j++)
                            {
                                //remove 30sec+ beacons from list and assume contact lost
                                if((System.currentTimeMillis()-giftConnectionList.get(j).getLastContactedTime())/1000>30) {
                                    giftConnectionList.remove(j);
                                }else {

                                    if (giftConnectionList.get(j).getBeaconid().equals(getBeaconHex(beacon.getIdentifiers()))) {
                                        beaconPreviouslyAdded = true;

                                        if ((System.currentTimeMillis() - giftConnectionList.get(j).getLastContactedTime()) / 1000 < 30) {
                                            giftConnectionList.get(j).setBeaconDistance(beacon.getDistance());
                                            giftConnectionList.get(j).setLastContactedTime(System.currentTimeMillis());
                                            break;
                                        }
                                    }
                                }
                            }

                            if(!beaconPreviouslyAdded){
                                giftConnection = ServiceHandler.GetBeacon(getBeaconHex(beacon.getIdentifiers()));
                                if (giftConnection != null) {
                                    giftConnection.setBeaconDistance(beacon.getDistance());
                                    giftConnection.setLastContactedTime(System.currentTimeMillis());
                                    giftConnectionList.add(giftConnection);
                                }
                                else
                                    giftConnectionList.add(new GiftConnection(getBeaconHex(beacon.getIdentifiers()),beacon.getDistance(), System.currentTimeMillis()));
                            }
                        }
                    }
                }
                setListContent();
                //slow down beacon search- non UI thread
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ex) {

                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }

/*
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                changeSeeingStatus(1);
            }

            @Override
            public void didExitRegion(Region region) {
                changeSeeingStatus(3);
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                if(state==1)
                    changeSeeingStatus(2);
                else
                    changeSeeingStatus(4);
            }
        });*/

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, Identifier.fromInt(1), null));
        } catch (RemoteException e) {    }
    }
}


