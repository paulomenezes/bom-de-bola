package menezes.paulo.bomdebola;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.listeners.Request;
import menezes.paulo.bomdebola.models.Game;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.SendJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class ControlGameActivity extends AppCompatActivity {

    public int mGol1 = 0;
    public int mGol2 = 0;

    public int mYellowCards = 0;
    public int mRedCards = 0;

    String time01 = "";
    String time02 = "";

    String time01ID = "";
    String time02ID = "";

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        final String id = b.getString("_id");
        String idTeam01 = b.getString("idTeam01");
        final String idTeam02 = b.getString("idTeam02");

        mDialog = ProgressDialog.show(this, "", getResources().getString(R.string.msg_loading));

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(final List<JSONObject> time1) {
                new DownloadJsonAsyncTask(new JsonResponse() {
                    @Override
                    public void response(List<JSONObject> time2) {
                        final TextView scores = (TextView) findViewById(R.id.scores);

                        Button gol1 = (Button) findViewById(R.id.gol1);
                        gol1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mGol1++;
                                scores.setText(mGol1 + " - " + mGol2);
                            }
                        });

                        Button gol2 = (Button) findViewById(R.id.gol2);
                        gol2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mGol2++;
                                scores.setText(mGol1 + " - " + mGol2);
                            }
                        });

                        try {
                            gol1.setText("Gol para " + time1.get(0).getString("name"));
                            gol2.setText("Gol para " + time2.get(0).getString("name"));

                            time01 = time1.get(0).getString("name");
                            time02 = time2.get(0).getString("name");

                            time01ID = time1.get(0).getString("_id");
                            time02ID = time2.get(0).getString("_id");
                        } catch (Exception e) {

                        }

                        final Button yellow = (Button) findViewById(R.id.yellow);
                        yellow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardButtons(yellow, 0);
                            }
                        });

                        final Button red = (Button) findViewById(R.id.red);
                        red.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardButtons(red, 1);
                            }
                        });

                        final Button finish = (Button) findViewById(R.id.finish);
                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ControlGameActivity.this);
                                builder2.setMessage("Deseja finalizar o jogo?");
                                builder2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDialog = ProgressDialog.show(ControlGameActivity.this, "", getResources().getString(R.string.msg_loading));

                                        Game g = new Game();
                                        g.setScore01(mGol1 + "");
                                        g.setScore02(mGol2 + "");
                                        new SendJsonAsyncTask(new Gson().toJson(g), new Request() {
                                            @Override
                                            public void success(Boolean status) {
                                                mDialog.dismiss();
                                                startActivity(new Intent(ControlGameActivity.this, ManagerActivity.class));
                                            }
                                        }).execute(URLs.API_GAME_UPDATE + id);
                                    }
                                });

                                builder2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder2.show();
                            }
                        });

                        mDialog.dismiss();
                    }
                }).execute(URLs.API_TEAM_READ + idTeam02);
            }
        }).execute(URLs.API_TEAM_READ + idTeam01);
    }

    private void cardButtons(final Button button, final int type) {
        String[] items = { time01, time02 };
        AlertDialog.Builder builder3 = new AlertDialog.Builder(ControlGameActivity.this);
        builder3.setTitle("Selecione seu time").setItems(items, new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int position) {
                mDialog = ProgressDialog.show(ControlGameActivity.this, "", getResources().getString(R.string.msg_loading));
                new DownloadJsonAsyncTask(new JsonResponse() {
                    @Override
                    public void response(List<JSONObject> objectList) {
                        mDialog.dismiss();

                        try {
                            String[] items = new String[objectList.size()];
                            for (int i = 0; i < objectList.size(); i++) {
                                items[i] = objectList.get(i).getString("name");
                            }

                            AlertDialog.Builder builder3 = new AlertDialog.Builder(ControlGameActivity.this);
                            builder3.setTitle("Escolha o jogador").setItems(items, new DialogInterface.OnClickListener() {
                                @Override

                                public void onClick(DialogInterface dialog, int position) {
                                    if (type == 0) {
                                        mYellowCards++;
                                        button.setText("Cartão amarelo (" + mYellowCards + ")");
                                    } else {
                                        mRedCards++;
                                        button.setText("Cartão vermelho (" + mRedCards + ")");
                                    }
                                }
                            });

                            builder3.show();
                        } catch (Exception e) {

                        }
                    }
                }).execute(URLs.API_USER_TEAM + (position == 0 ? time01ID : time02ID));
            }
        });

        builder3.show();
    }
}
