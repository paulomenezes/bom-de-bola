package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.TeamActivity;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterFragmentHomeTeams  extends RecyclerView.Adapter<AdapterFragmentHomeTeams.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<JSONObject> mValues;

    private RecyclerView mRecyclerView;

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView championships;
        public TextView rpa;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            championships = (TextView) view.findViewById(R.id.championships);
            rpa= (TextView) view.findViewById(R.id.rpa);
        }
    }

    public JSONObject getValueAt(int position) {
        return mValues.get(position);
    }

    public AdapterFragmentHomeTeams(Context context, List<JSONObject> items, RecyclerView recyclerView) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;

        mRecyclerView = recyclerView;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_teams, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int itemPosition = mRecyclerView.getChildPosition(v);
                    JSONObject object = mValues.get(itemPosition);

                    Intent next = new Intent(mContext, TeamActivity.class);
                    next.putExtra("team", object.has("_id") ? object.getString("_id") : object.getString("id"));
                    next.putExtra("championship", object.getString("idChampionship"));

                    mContext.startActivity(next);
                } catch (Exception e) {

                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.name.setText(mValues.get(position).getString("name"));
            holder.rpa.setText(mValues.get(position).getString("rpa"));

            final JSONArray jsonArray = mValues.get(position).getJSONArray("idChampionship");
            for (int i = 0; i < jsonArray.length(); i++) {
                final int k = i;
                if (jsonArray.get(0) != null) {
                    new DownloadJsonAsyncTask(new JsonResponse() {
                        @Override
                        public void response(List<JSONObject> objectList) {
                            try {
                                if (k < jsonArray.length() - 1) {
                                    holder.championships.setText(holder.championships.getText() + objectList.get(0).getString("name") + ", ");
                                } else {
                                    holder.championships.setText(holder.championships.getText() + objectList.get(0).getString("name"));
                                }
                            } catch (Exception e) {

                            }
                        }
                    }).execute(URLs.API_CHAMPIONSHIP_READ + jsonArray.get(0));
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
