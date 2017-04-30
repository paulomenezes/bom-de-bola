package menezes.paulo.bomdebola.fragments.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentGeral extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout rv = (LinearLayout) inflater.inflate(R.layout.fragment_team_geral, container, false);

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> objectList) {
                try {
                    if (objectList.get(0).has("score01")) {
                        TextView nextGame = (TextView) rv.findViewById(R.id.nextGame);
                        nextGame.setText("Jogo anterior");

                        TextView scores = (TextView) rv.findViewById(R.id.scores);
                        scores.setText(objectList.get(0).getString("score01") + " - " + objectList.get(0).getString("score02"));
                    } else {
                        LinearLayout scoresPanel = (LinearLayout) rv.findViewById(R.id.scoresPanel);
                        scoresPanel.setVisibility(View.GONE);
                    }

                    TextView time = (TextView) rv.findViewById(R.id.time);
                    time.setText(objectList.get(0).getString("date"));

                    String idOpponent = "";

                    if (getArguments().getString("team").equals(objectList.get(0).getString("idTeam01"))) {
                        idOpponent = objectList.get(0).getString("idTeam02");
                    } else if (getArguments().getString("team").equals(objectList.get(0).getString("idTeam02"))) {
                        idOpponent = objectList.get(0).getString("idTeam01");
                    }

                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {
                            TextView rpa = (TextView) rv.findViewById(R.id.rpa);
                            try {
                                rpa.setText(objectList.get(0).getString("rpa"));
                            } catch (Exception e) {

                            }
                        }
                    }).execute(URLs.API_TEAM_READ + getArguments().getString("team"));

                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {
                            TextView name = (TextView) rv.findViewById(R.id.name);
                            try {
                                name.setText(objectList.get(0).getString("name"));
                            } catch (Exception e) {

                            }
                        }
                    }).execute(URLs.API_TEAM_READ + idOpponent);

                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {
                            TextView place = (TextView) rv.findViewById(R.id.place);
                            try {
                                place.setText(objectList.get(0).getString("name"));
                            } catch (Exception e) {

                            }
                        }
                    }).execute(URLs.API_FIELD_READ + objectList.get(0).getString("idField"));

                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {
                            TextView championships = (TextView) rv.findViewById(R.id.championships);
                            try {
                                championships.setText(objectList.get(0).getString("name"));
                            } catch (Exception e) {

                            }
                        }
                    }).execute(URLs.API_CHAMPIONSHIP_READ + objectList.get(0).getString("idChampionship"));

                    int points = 0;
                    int wins = 0;
                    int draws = 0;
                    int defeats = 0;
                    for (int i = 0; i < objectList.size(); i++) {
                        if (objectList.get(i).getString("idTeam01").equals(getArguments().getString("team"))) {
                            if (objectList.get(i).has("score01")) {
                                if (Integer.parseInt(objectList.get(i).getString("score01")) > Integer.parseInt(objectList.get(i).getString("score02"))) {
                                    wins++;
                                    points += 3;
                                } else if (Integer.parseInt(objectList.get(i).getString("score01")) < Integer.parseInt(objectList.get(i).getString("score02"))) {
                                    defeats++;
                                } else {
                                    points++;
                                    draws++;
                                }
                            }
                        } else {
                            if (objectList.get(i).has("score01")) {
                                if (Integer.parseInt(objectList.get(i).getString("score02")) > Integer.parseInt(objectList.get(i).getString("score01"))) {
                                    wins++;
                                    points += 3;
                                } else if (Integer.parseInt(objectList.get(i).getString("score02")) < Integer.parseInt(objectList.get(i).getString("score01"))) {
                                    defeats++;
                                } else {
                                    points++;
                                    draws++;
                                }
                            }
                        }
                    }

                    TextView pointsTV = (TextView)rv.findViewById(R.id.points);
                    pointsTV.setText(points + "");
                    TextView winsTV = (TextView)rv.findViewById(R.id.wins);
                    winsTV.setText(wins + "");
                    TextView drawTV = (TextView)rv.findViewById(R.id.draw);
                    drawTV.setText(draws + "");
                    TextView defeatTV = (TextView)rv.findViewById(R.id.defeat);
                    defeatTV.setText(defeats + "");

                } catch (Exception e) {

                }
            }
        }).execute(URLs.API_GAME_NEXT + getArguments().getString("team"));

        return rv;
    }
}
