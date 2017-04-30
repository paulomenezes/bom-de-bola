package menezes.paulo.bomdebola.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class JsonParser {
    public static List<JSONObject> getObjects(String jsonString) {
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
}
