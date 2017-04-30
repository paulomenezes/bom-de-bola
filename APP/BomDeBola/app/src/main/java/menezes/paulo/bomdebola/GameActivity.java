package menezes.paulo.bomdebola;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.models.Game;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        final ProgressDialog mDialog = ProgressDialog.show(this, "", getResources().getString(R.string.msg_loading));

        Bundle b = getIntent().getExtras();
        final Game game = (Game)b.getSerializable("game");

        TextView time = (TextView) findViewById(R.id.scores);
        time.setText(game.getScore01() + " - " + game.getScore02());

        TextView info = (TextView) findViewById(R.id.info);
        info.setText(game.getDate());

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> time01) {
                if (time01 != null && time01.size() > 0) {
                    try {
                        TextView time = (TextView) findViewById(R.id.time01);
                        time.setText(time01.get(0).getString("name"));
                    } catch (Exception e) {

                    }
                }
            }
        }).execute(URLs.API_TEAM_READ + game.getIdTeam01());

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> time02) {
                if (time02 != null && time02.size() > 0) {
                    try {
                        TextView time = (TextView) findViewById(R.id.time02);
                        time.setText(time02.get(0).getString("name"));
                    } catch (Exception e) {

                    }
                }
            }
        }).execute(URLs.API_TEAM_READ + game.getIdTeam02());

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> championship) {
                if (championship != null && championship.size() > 0) {
                    try {
                        TextView time = (TextView) findViewById(R.id.championship);
                        time.setText("Campeonato: " + championship.get(0).getString("name"));
                    } catch (Exception e) {

                    }
                }
            }
        }).execute(URLs.API_CHAMPIONSHIP_READ + game.getIdChampionship());

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> user) {
                if (user != null && user.size() > 0) {
                    try {
                        TextView time = (TextView) findViewById(R.id.manager);
                        time.setText("Organizador: " + user.get(0).getString("name"));
                    } catch (Exception e) {

                    }
                }
            }
        }).execute(URLs.API_USER_READ + game.getIdManager());

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> field) {
                if (field != null && field.size() > 0) {
                    try {
                        TextView time = (TextView) findViewById(R.id.field);
                        time.setText("Campo: " + field.get(0).getString("name"));
                    } catch (Exception e) {

                    }
                }
            }
        }).execute(URLs.API_FIELD_READ + game.getIdField());

        mDialog.dismiss();
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
