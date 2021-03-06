package menezes.paulo.bomdebola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class FieldsActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        setUpMapIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(final List<JSONObject> objectList) {
                try {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            Double.parseDouble(objectList.get(0).getString("latitude").replace(',', '.')),
                            Double.parseDouble(objectList.get(0).getString("longitude").replace(',', '.'))), 14));

                    for (int i = 0; i < objectList.size(); i++) {
                        mMap.addMarker(new MarkerOptions().position(
                                       new LatLng(
                                               Double.parseDouble(objectList.get(i).getString("latitude").replace(',', '.')),
                                               Double.parseDouble(objectList.get(i).getString("longitude").replace(',', '.'))))
                                        .title(objectList.get(i).getString("name"))
                                        .snippet("RPA: " + objectList.get(i).getString("rpa")));
                    }

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            try {
                                int index = 0;
                                for (int i = 0; i < objectList.size(); i++) {
                                    if (objectList.get(i).getString("name").equals(marker.getTitle())) {
                                        index = i;
                                        break;
                                    }
                                }

                                Intent intent = new Intent(FieldsActivity.this, FieldActivity.class);
                                intent.putExtra("id", objectList.get(index).getString("_id"));
                                startActivity(intent);
                            } catch (Exception e) {
                                if (e != null) {

                                }
                            }
                        }
                    });
                } catch (Exception e) {

                }
            }
        }).execute(URLs.API_FIELD);
    }
}
