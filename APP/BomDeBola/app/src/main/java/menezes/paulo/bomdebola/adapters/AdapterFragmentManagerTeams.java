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

import menezes.paulo.bomdebola.PostViewActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.models.Post;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterFragmentManagerTeams extends RecyclerView.Adapter<AdapterFragmentManagerTeams.ViewHolder> {

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

    public AdapterFragmentManagerTeams(Context context, List<JSONObject> items, RecyclerView recyclerView) {
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

                    Intent next = new Intent(mContext, PostViewActivity.class);
                    Post post = new Post();
                    post.load(object);
                    next.putExtra("post", post);

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
