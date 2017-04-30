package menezes.paulo.bomdebola;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class FieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b.getString("id").length() > 0) {
            final String id = b.getString("id");

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    TextView name = (TextView) findViewById(R.id.name);
                    TextView address = (TextView) findViewById(R.id.address);
                    TextView rpa = (TextView) findViewById(R.id.rpa);
                    TextView type = (TextView) findViewById(R.id.type);

                    try {
                        setTitle(objectList.get(0).getString("name"));

                        name.setText(objectList.get(0).getString("name"));
                        address.setText(objectList.get(0).getString("address") + ", " + objectList.get(0).getString("district"));
                        rpa.setText(objectList.get(0).getString("rpa"));
                        type.setText(objectList.get(0).getString("type") == "SIM" ? "Privado" : "Público");
                    } catch (Exception e) {

                    }

                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {

                        }
                    }).execute(URLs.API_GAME_GET + id);
                }
            }).execute(URLs.API_FIELD_READ + id);
        }
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
}
