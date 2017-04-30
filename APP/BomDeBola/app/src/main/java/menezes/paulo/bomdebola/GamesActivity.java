package menezes.paulo.bomdebola;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.adapters.AdapterFragmentHomeGames;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.JsonParser;
import menezes.paulo.bomdebola.util.URLs;

public class GamesActivity extends AppCompatActivity {
    private static List<JSONObject> mObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerview);
        setupRecyclerView(rv);
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        if (mObjectList == null) {
            final ProgressDialog mDialog = ProgressDialog.show(this, "", getResources().getString(R.string.msg_loading));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    mObjectList = objectList;

                    mDialog.dismiss();

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new AdapterFragmentHomeGames(GamesActivity.this, objectList, recyclerView));
                }
            }).execute(URLs.API_GAME);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new AdapterFragmentHomeGames(GamesActivity.this, mObjectList, recyclerView));
        }
    }
}
