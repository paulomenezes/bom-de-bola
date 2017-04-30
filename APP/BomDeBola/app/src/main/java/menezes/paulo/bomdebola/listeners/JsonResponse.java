package menezes.paulo.bomdebola.listeners;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Paulo Menezes on 22/07/2015.
 */
public interface JsonResponse {
    void response(List<JSONObject> objectList);
}
