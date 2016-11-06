package trialbycombat.com.bluemixtest;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    private MainActivity mainAct;
    private TextView txtResult;
    private FloatingActionButton btnStartBeaconSearch;
    private Gson gson=new Gson();
    private ServiceCallerAsyncTask serviceCallerAsyncTask;
    private BeaconManager beaconManager;
    private TextView txtID1,txtID2,txtID3,txtRSSI,txtTX,txtDistance,txtBeaconType,txtNumberOfBeacons;
    private ImageButton btnIsSeeing;
    private FrameLayout frmBackground;
    private RelativeLayout frmBeaconPage;
    private ListView beaconListView;
    List<org.altbeacon.beacon.Beacon> beaconList;

private ArrayAdapter beaconAdapter;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = BeaconManager.getInstanceForApplication(this);
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
        mainAct = this;
        serviceCallerAsyncTask = new ServiceCallerAsyncTask();
        txtID1 = (TextView) findViewById(R.id.txtID1);
        txtID2 = (TextView) findViewById(R.id.txtID2);
        txtID3 = (TextView) findViewById(R.id.txtID3);
         beaconListView= (ListView) findViewById(R.id.beacon_list);
        txtBeaconType = (TextView) findViewById(R.id.txtBeaconType);
        txtNumberOfBeacons = (TextView) findViewById(R.id.txtNumberOfBeacons);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        txtRSSI = (TextView) findViewById(R.id.txtRSSI);
        txtTX = (TextView) findViewById(R.id.txtTX);
        btnIsSeeing = (ImageButton) findViewById(R.id.btnIsSeeing);
        btnStartBeaconSearch = (FloatingActionButton) findViewById(R.id.btnStartBeaconSearch);
        frmBackground = (FrameLayout) findViewById(R.id.frmBackground);
        frmBeaconPage = (RelativeLayout) findViewById(R.id.frmBeaconPage);
        beaconList=new ArrayList<>();
        btnStartBeaconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(frmBackground);
                toggleVisibility(frmBeaconPage);

                if (frmBeaconPage.getVisibility()==View.VISIBLE) {
                    beaconManager.bind(mainAct);
                }
                else {
                    beaconManager.unbind(mainAct);
                }
            }
        });
    }

    public class ServiceCallerAsyncTask extends AsyncTask<Void, Void, Beacon[]> {
        Beacon[] data;
        @Override
        protected Beacon[] doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet("https://appnode-red-starter.mybluemix.net/api/v1/MaviCheck/95F428B14A3A4E39B08621BFF38DEB6D");
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);


                final String jsonString = EntityUtils.toString(response.getEntity());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtResult.setText(jsonString);
                    }
                });

                data = gson.fromJson(jsonString, Beacon[].class);
            } catch (Exception e) {
                return null;
            }


            return data;
        }

        protected void onPostExecute(Beacon[] results) {
            if (results!=null) {
            }

        }
    }

    private void changeTextFlow(final org.altbeacon.beacon.Beacon beacon, final int beaconListSize)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtBeaconType.setText("iBeacon");
                txtDistance.setText(String.format("%.2f", beacon.getDistance() )+" m");
                txtID1.setText(beacon.getId1().toString());
                txtID2.setText(beacon.getId2().toString());
                txtID3.setText(beacon.getId3().toString());
                txtRSSI.setText(beacon.getRssi()+" dBm");
                txtTX.setText(beacon.getTxPower()+" dBm");
                txtNumberOfBeacons.setText(String.valueOf(beaconListSize));

            }
        });
    }

    private void setListContent()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beaconAdapter = new ArrayAdapter<org.altbeacon.beacon.Beacon>(mainAct, R.layout.activity_listview, beaconList);

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
     */
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
    }

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

              /*  if (beacons.size() > 0) {
                    org.altbeacon.beacon.Beacon beacon=beaconInsider.iterator().next();

                    changeTextFlow(beacon,beacons.size());
                }*/
                beaconList.clear();

                for (int i=0;i<beacons.size();i++)
                {
                    beaconList.add(beaconInsider.iterator().next());
                }
                setListContent();
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }


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
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

}


