package trialbycombat.com.bluemixtest;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.internal.http.StatusLine;

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

    public static void PostPayment(PaymentData paymentInfo) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://appnode-red-starter.mybluemix.net/api/v1/Payment");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("beaconid", paymentInfo.getBeaconid()));
            nameValuePairs.add(new BasicNameValuePair("name", paymentInfo.getName()));
            nameValuePairs.add(new BasicNameValuePair("surname", paymentInfo.getSurname()));
            nameValuePairs.add(new BasicNameValuePair("customernumber", paymentInfo.getCustomerNumber()));
            nameValuePairs.add(new BasicNameValuePair("amount", paymentInfo.getAmount()));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            System.out.print("e");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        //non depr version below. Doesnt work properly- empty body.
      /*  try {
            URL url = new URL("https://appnode-red-starter.mybluemix.net/api/v1/Payment"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("beaconid", paymentInfo.getBeaconid());
            jsonObject.put("name", paymentInfo.getName());
            jsonObject.put("surname", paymentInfo.getSurname());
            jsonObject.put("customernumber", paymentInfo.getCustomerNumber());
            jsonObject.put("amount", paymentInfo.getAmount());

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            int responseCode=httpURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((line=br.readLine()) != null) {

                }
            }

            } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
