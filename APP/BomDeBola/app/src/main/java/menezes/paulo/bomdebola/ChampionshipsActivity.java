package menezes.paulo.bomdebola;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class ChampionshipsActivity extends AppCompatActivity {
    public static List<JSONObject> sObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championships);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        final ListView list = (ListView) findViewById(R.id.listView);

        if (sObjectList == null) {
            final ProgressDialog mDialog = ProgressDialog.show(this, null, getResources().getString(R.string.msg_loading));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(final List<JSONObject> objectList) {
                    String[] data = new String[objectList.size()];
                    try {
                        sObjectList = objectList;

                        for (int i = 0; i < objectList.size(); i++) {
                            data[i] = objectList.get(i).getString("name");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChampionshipsActivity.this, R.layout.text_fragment_manager, data);

                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    Intent next = new Intent(ChampionshipsActivity.this, ChampionshipActivity.class);
                                    next.putExtra("championship", objectList.get(position).has("_id") ? objectList.get(position).getString("_id") : objectList.get(position).getString("id"));

                                    startActivity(next);
                                } catch (Exception e) {

                                }
                            }
                        });

                        mDialog.dismiss();
                    } catch (Exception e) {

                    }
                }
            }).execute(URLs.API_CHAMPIONSHIP);
        } else {
            try {
                String[] data = new String[sObjectList.size()];

                for (int i = 0; i < sObjectList.size(); i++) {
                    data[i] = sObjectList.get(i).getString("name");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChampionshipsActivity.this, R.layout.text_fragment_manager, data);

                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            } catch (Exception e) {

            }
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
