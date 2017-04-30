package menezes.paulo.bomdebola.util;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;

/**
 * Created by Paulo Menezes on 22/07/2015.
 */
public class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<JSONObject>> {
    private JsonResponse mJsonResponse;

    public DownloadJsonAsyncTask(JsonResponse jsonResponse) {
        this.mJsonResponse = jsonResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<JSONObject> doInBackground(String... params) {
        String urlString = params[0];

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlString);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String json = toString(inputStream);
                inputStream.close();

                List<JSONObject> objects = getObjects(json);

                return objects;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private List<JSONObject> getObjects(String jsonString) {
        List<JSONObject> objects = new ArrayList<JSONObject>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject object;

            for (int i = 0; i < jsonArray.length(); i++) {
                object = new JSONObject(jsonArray.getString(i));
                objects.add(object);
            }
        } catch (JSONException e) {
            try {
                JSONObject object = new JSONObject(jsonString);
                objects.add(object);
            } catch (Exception ex) {
                return null;
            }
        }

        return objects;
    }

    @Override
    protected void onPostExecute(List<JSONObject> result) {
        super.onPostExecute(result);

        mJsonResponse.response(result);
    }

    private String toString(InputStream is) throws IOException {
        byte[] bytes = new byte[1024];

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read;

        while ((read = is.read(bytes)) > 0) {
            byteArrayOutputStream.write(bytes, 0, read);
        }

        return new String(byteArrayOutputStream.toByteArray());
    }
}
