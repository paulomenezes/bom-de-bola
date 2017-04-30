package menezes.paulo.bomdebola.util;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

import menezes.paulo.bomdebola.listeners.Request;

/**
 * Created by Paulo Menezes on 22/07/2015.
 */
public class SendJsonAsyncTask extends AsyncTask<String, Void, Boolean> {
    private String mObject;
    private Request mRequest;

    public SendJsonAsyncTask(String object, Request request) {
        this.mObject = object;
        this.mRequest = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String urlString = params[0];

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(urlString);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("data", mObject));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        mRequest.success(result);
    }
}

