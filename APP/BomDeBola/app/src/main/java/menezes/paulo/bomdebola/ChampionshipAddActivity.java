package menezes.paulo.bomdebola;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import menezes.paulo.bomdebola.adapters.AdapterListCheckbox;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.listeners.Request;
import menezes.paulo.bomdebola.models.Championship;
import menezes.paulo.bomdebola.models.Team;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.SendJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class ChampionshipAddActivity extends AppCompatActivity {
    private ArrayList<Team> data;
    public static ArrayList<String> selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        final ListView listTeams = (ListView) findViewById(R.id.listTeams);

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> objectList) {
                data = new ArrayList<Team>();
                try {
                    for (int i = 0; i < objectList.size(); i++) {
                        Team t = new Team();
                        t.setId(objectList.get(i).has("_id") ? objectList.get(i).getString("_id") : objectList.get(i).getString("id"));
                        t.setName(objectList.get(i).getString("name"));
                        t.setRpa(objectList.get(i).getString("rpa"));
                        t.setIdManager(MainActivity.sUser.getId());

                        data.add(t);
                    }

                    AdapterListCheckbox dataAdapter = new AdapterListCheckbox(ChampionshipAddActivity.this, R.layout.list_checkbox, data);
                    listTeams.setAdapter(dataAdapter);
                    listTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                } catch (Exception e) {

                }
            }
        }).execute(URLs.API_TEAM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send) {
            register();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void register() {
        EditText editText = (EditText) findViewById(R.id.name);
        if (editText.getText().length() == 0) {
            Snackbar.make(findViewById(R.id.layout), "Campo obrigatório", Snackbar.LENGTH_LONG).show();
        } else {
            if (selected.size() < 2) {
                Snackbar.make(findViewById(R.id.layout), "Selecione pelo menos dois times", Snackbar.LENGTH_LONG).show();
            } else {
                final Championship championship = new Championship();
                championship.setName(editText.getText().toString());
                championship.setIdUser(MainActivity.sUser.getId());

                new SendJsonAsyncTask(new Gson().toJson(championship), new Request() {
                    @Override
                    public void success(Boolean status) {
                        if (status) {
                            new DownloadJsonAsyncTask(new JsonResponse() {
                                @Override
                                public void response(List<JSONObject> objectList) {
                                    try {
                                        for (int i = 0; i < selected.size(); i++) {
                                            Team team = new Team();
                                            team.setId(selected.get(i));
                                            team.setIdChampionship(objectList.get(0).has("_id") ? objectList.get(0).getString("_id") : objectList.get(0).getString("id"));
                                            String a = new Gson().toJson(team);
                                            new SendJsonAsyncTask(new Gson().toJson(team), new Request() {
                                                @Override
                                                public void success(Boolean status) {

                                                }
                                            }).execute(URLs.API_TEAM_UPDATE);
                                        }

                                        startActivity(new Intent(ChampionshipAddActivity.this, ManagerActivity.class));
                                        finish();
                                    } catch (Exception e) {

                                    }
                                }
                            }).execute(URLs.API_CHAMPIONSHIP_GET + championship.getName());
                        } else {
                            Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).execute(URLs.API_CHAMPIONSHIP_CREATE);
            }
        }
    }
}
