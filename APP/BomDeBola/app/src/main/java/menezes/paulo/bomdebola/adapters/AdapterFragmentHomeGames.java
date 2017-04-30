package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.ControlGameActivity;
import menezes.paulo.bomdebola.GameActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.models.Game;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterFragmentHomeGames extends RecyclerView.Adapter<AdapterFragmentHomeGames.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<JSONObject> mValues;

    private RecyclerView mRecyclerView;

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time01;
        public TextView info;
        public TextView time02;
        public TextView date;
        public Button seeChampionships;

        public ViewHolder(View view) {
            super(view);

            time01 = (TextView) view.findViewById(R.id.time01);
            info = (TextView) view.findViewById(R.id.info);
            time02 = (TextView) view.findViewById(R.id.time02);
            date = (TextView) view.findViewById(R.id.date);
            seeChampionships = (Button) view.findViewById(R.id.seeChampionships);
        }
    }

    public JSONObject getValueAt(int position) {
        return mValues.get(position);
    }

    public AdapterFragmentHomeGames(Context context, List<JSONObject> items, RecyclerView recyclerView) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;

        mRecyclerView = recyclerView;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_games, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int itemPosition = mRecyclerView.getChildPosition(v);
                    JSONObject object = mValues.get(itemPosition);

                    Intent next = new Intent(mContext, GameActivity.class);
                    Game g = new Game();
                    g.setId(object.getString("_id"));
                    g.setIdTeam01(object.getString("idTeam01"));
                    g.setIdTeam02(object.getString("idTeam02"));
                    g.setIdChampionship(object.getString("idChampionship"));
                    g.setIdField(object.getString("idField"));
                    g.setIdManager(object.getString("idManager"));
                    g.setScore01(object.getString("score01"));
                    g.setScore02(object.getString("score02"));
                    g.setDate(object.getString("date"));
                    next.putExtra("game", g);

                    mContext.startActivity(next);
                } catch (Exception e) {

                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            if (mValues.get(position).has("score01")) {
                holder.info.setText(mValues.get(position).getString("score01") + " - " + mValues.get(position).getString("score02"));
            } else {
                holder.info.setText("0 - 0");
                holder.seeChampionships.setText("Selecionar");
                holder.seeChampionships.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent i = new Intent(mContext, ControlGameActivity.class);
                            i.putExtra("_id", mValues.get(position).getString("_id"));
                            i.putExtra("idTeam01", mValues.get(position).getString("idTeam01"));
                            i.putExtra("idTeam02", mValues.get(position).getString("idTeam02"));
                            mContext.startActivity(i);
                        } catch (Exception e) {

                        }
                    }
                });
            }
            holder.date.setText(mValues.get(position).getString("date"));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> time01) {
                    if (time01 != null && time01.size() > 0) {
                        try {
                            holder.time01.setText(time01.get(0).getString("name"));
                        } catch (Exception e) {

                        }
                    }
                }
            }).execute(URLs.API_TEAM_READ + mValues.get(position).getString("idTeam01"));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> time02) {
                    if (time02 != null && time02.size() > 0) {
                        try {
                            holder.time02.setText(time02.get(0).getString("name"));
                        } catch (Exception e) {

                        }
                    }
                }
            }).execute(URLs.API_TEAM_READ + mValues.get(position).getString("idTeam02"));
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        } else {
            return  0;
        }
    }
}
