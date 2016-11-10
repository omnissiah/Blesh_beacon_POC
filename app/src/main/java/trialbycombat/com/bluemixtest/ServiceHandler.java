package trialbycombat.com.bluemixtest;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * Created by IS96266 on 7.11.2016 - 14:35.
 */
public class ServiceHandler {

    private static Gson gson=new Gson();
    private static HttpClient httpClient = new DefaultHttpClient();
    private static HttpContext localContext = new BasicHttpContext();

    public static GiftConnection[] GetAllBeacons() {
        GiftConnection[] data = null;

        HttpGet httpGet = new HttpGet("https://appnode-red-starter.mybluemix.net/api/v1/MaviCheck");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);


            final String jsonString = EntityUtils.toString(response.getEntity());


            data = gson.fromJson(jsonString, GiftConnection[].class);
        } catch (Exception e) {
            return null;
        }
        return data;
    }

    public static GiftConnection GetBeacon(String beaconID) {
        GiftConnection[] data = null;

        HttpGet httpGet = new HttpGet("https://appnode-red-starter.mybluemix.net/api/v1/Mavicheck/" + beaconID.replace("-",""));
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);


            final String jsonString = EntityUtils.toString(response.getEntity());


            data = gson.fromJson(jsonString, GiftConnection[].class);
            if (data != null)
                return data[0];


        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
