package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import menezes.paulo.bomdebola.ProfileActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.models.User;
import menezes.paulo.bomdebola.util.LoadProfileImage;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterPlayers extends RecyclerView.Adapter<AdapterPlayers.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<JSONObject> mValues;

    private RecyclerView mRecyclerView;

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public RelativeLayout overlay;
        public CircleImageView profile;
        public TextView title;
        public ImageView placeIcon;
        public TextView place;
        public ImageView timeIcon;
        public TextView time;
        public TextView text;
        public Button like;
        public Button comment;
        public Button share;

        public ViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            overlay = (RelativeLayout) view.findViewById(R.id.overlay);
            profile = (CircleImageView) view.findViewById(R.id.profile);
            title = (TextView) view.findViewById(R.id.title);
            placeIcon = (ImageView) view.findViewById(R.id.placeIcon);
            place = (TextView) view.findViewById(R.id.place);
            timeIcon = (ImageView) view.findViewById(R.id.timeIcon);
            time = (TextView) view.findViewById(R.id.time);
            text = (TextView) view.findViewById(R.id.text);
            like = (Button) view.findViewById(R.id.like);
            comment = (Button) view.findViewById(R.id.comment);
            share = (Button) view.findViewById(R.id.share);

            like.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
        }
    }

    public JSONObject getValueAt(int position) {
        return mValues.get(position);
    }

    public AdapterPlayers(Context context, List<JSONObject> items, RecyclerView recyclerView) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;

        mRecyclerView = recyclerView;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_news, parent, false);
        view.setBackgroundResource(mBackground);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int itemPosition = mRecyclerView.getChildPosition(v);
                    JSONObject object = mValues.get(itemPosition);

                    Intent i = new Intent(mContext, ProfileActivity.class);
                    User u = new User();
                    u.load(object);
                    i.putExtra("user", u);
                    mContext.startActivity(i);
                } catch (Exception e) {

                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.title.setText(new String(mValues.get(position).getString("name").getBytes(), "UTF-8"));
            holder.text.setVisibility(View.GONE);

            if (mValues.get(position).has("imageProfile")) {
                new LoadProfileImage(holder.profile).execute(mValues.get(0).getString("imageProfile"));
            } else {
                holder.profile.setVisibility(View.GONE);
            }

            if (mValues.get(position).has("imageCover")) {
                new LoadProfileImage(holder.image).execute(mValues.get(position).getString("imageCover"));
            } else {
                holder.image.setVisibility(View.INVISIBLE);
                holder.image.getLayoutParams().height = 200;
                holder.overlay.setVisibility(View.GONE);

                holder.title.setTextColor(Color.parseColor("#000000"));
            }

            holder.time.setText(mValues.get(position).getString("birthday"));

            if (mValues.get(position).getString("city").length() > 0) {
                holder.place.setText(mValues.get(position).getString("city"));
            } else {
                if (mValues.get(position).getString("district").length() > 0) {
                    holder.place.setText(mValues.get(position).getString("district").toLowerCase());
                } else {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, 0);
                    holder.timeIcon.setLayoutParams(lp);

                    holder.place.setVisibility(View.GONE);
                    holder.placeIcon.setVisibility(View.GONE);
                }
            }
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

