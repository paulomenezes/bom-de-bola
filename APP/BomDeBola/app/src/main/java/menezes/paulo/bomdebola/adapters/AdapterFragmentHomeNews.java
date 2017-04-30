package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import menezes.paulo.bomdebola.PostViewActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.models.Post;
import menezes.paulo.bomdebola.util.DatesUtil;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.LoadProfileImage;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterFragmentHomeNews extends RecyclerView.Adapter<AdapterFragmentHomeNews.ViewHolder> {

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
        //public Button like;
        //public Button comment;
        //public Button share;

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
            //like = (Button) view.findViewById(R.id.like);
            //comment = (Button) view.findViewById(R.id.comment);
            //share = (Button) view.findViewById(R.id.share);
        }
    }

    public JSONObject getValueAt(int position) {
        return mValues.get(position);
    }

    public AdapterFragmentHomeNews(Context context, List<JSONObject> items, RecyclerView recyclerView) {
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
            holder.title.setText(mValues.get(position).getString("title"));
            holder.text.setText(mValues.get(position).getString("text").substring(0, mValues.get(position).getString("text").length() > 100 ? 100 : mValues.get(position).getString("text").length()) + "...");

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    try {
                        new LoadProfileImage(holder.profile).execute(objectList.get(0).getString("imageProfile"));
                    } catch (Exception e) {

                    }
                }
            }).execute(URLs.API_USER_READ + mValues.get(position).getString("idUser"));

            if (mValues.get(position).has("image")) {
                new LoadProfileImage(holder.image).execute(URLs.API_UPLOADS + mValues.get(position).getString("image"));
            } else {
                holder.image.setVisibility(View.INVISIBLE);
                holder.image.getLayoutParams().height = 200;
                holder.overlay.setVisibility(View.GONE);

                holder.title.setTextColor(Color.parseColor("#000000"));
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = dateFormat.parse(mValues.get(position).getString("date"));

            Date today = new Date();

            holder.time.setText(DatesUtil.getPeriod(date, today));

            if (mValues.get(position).getString("place").length() > 0) {
                holder.place.setText(mValues.get(position).getString("place"));
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 0);
                holder.timeIcon.setLayoutParams(lp);

                holder.place.setVisibility(View.GONE);
                holder.placeIcon.setVisibility(View.GONE);
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
