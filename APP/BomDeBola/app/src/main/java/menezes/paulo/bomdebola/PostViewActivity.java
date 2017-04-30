package menezes.paulo.bomdebola;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.models.Post;
import menezes.paulo.bomdebola.util.DatesUtil;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.LoadProfileImage;
import menezes.paulo.bomdebola.util.URLs;

public class PostViewActivity extends AppCompatActivity {
    public ImageView image;
    public RelativeLayout overlay;
    public CircleImageView profile;
    public TextView title;
    public ImageView placeIcon;
    public TextView place;
    public ImageView timeIcon;
    public TextView time;
    public TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Bundle extras = getIntent().getExtras();
        if (extras.getSerializable("post") != null) {
            Post post = (Post)extras.getSerializable("post");

            image = (ImageView) findViewById(R.id.image);
            overlay = (RelativeLayout) findViewById(R.id.overlay);
            profile = (CircleImageView) findViewById(R.id.profile);
            title = (TextView) findViewById(R.id.title);
            placeIcon = (ImageView) findViewById(R.id.placeIcon);
            place = (TextView) findViewById(R.id.place);
            timeIcon = (ImageView) findViewById(R.id.timeIcon);
            time = (TextView) findViewById(R.id.time);
            text = (TextView) findViewById(R.id.text);

            try {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                final ActionBar ab = getSupportActionBar();
                ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
                ab.setDisplayHomeAsUpEnabled(true);

                setTitle(post.getTitle());

                title.setText(post.getTitle());
                text.setText(post.getText());

                new DownloadJsonAsyncTask(new JsonResponse() {
                    @Override
                    public void response(List<JSONObject> objectList) {
                        try {
                            new LoadProfileImage(profile).execute(objectList.get(0).getString("imageProfile"));
                        } catch (Exception e) {

                        }
                    }
                }).execute(URLs.API_USER_READ + post.getIdUser());

                if (post.getImage() != null) {
                    new LoadProfileImage(image).execute(URLs.API_UPLOADS + post.getImage());
                } else {
                    image.setVisibility(View.INVISIBLE);
                    image.getLayoutParams().height = 200;
                    overlay.setVisibility(View.GONE);

                    title.setTextColor(Color.parseColor("#000000"));
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = dateFormat.parse(post.getDate());

                Date today = new Date();

                time.setText(DatesUtil.getPeriod(date, today));

                if (post.getPlace().length() > 0) {
                    place.setText(post.getPlace());
                } else {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, 0);
                    timeIcon.setLayoutParams(lp);

                    place.setVisibility(View.GONE);
                    placeIcon.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
        }
    }

}
