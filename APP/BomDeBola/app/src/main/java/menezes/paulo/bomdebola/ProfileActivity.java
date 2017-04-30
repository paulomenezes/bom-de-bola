package menezes.paulo.bomdebola;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import menezes.paulo.bomdebola.models.User;
import menezes.paulo.bomdebola.util.LoadProfileImage;

public class ProfileActivity extends AppCompatActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        CircleImageView profileImage = (CircleImageView) findViewById(R.id.profile);
        ImageView backgroundImage = (ImageView) findViewById(R.id.image);

        TextView name = (TextView) findViewById(R.id.title);
        TextView age = (TextView) findViewById(R.id.age);
        TextView ageLabel = (TextView) findViewById(R.id.ageLabel);
        TextView birthday = (TextView) findViewById(R.id.birthday);
        TextView birthdayabel = (TextView) findViewById(R.id.birthdayLabel);
        TextView city = (TextView) findViewById(R.id.city);
        TextView cityLabel = (TextView) findViewById(R.id.cityLabel);
        TextView district = (TextView) findViewById(R.id.district);
        TextView districtLabel = (TextView) findViewById(R.id.districtLabel);

        if (getIntent().getExtras() == null) {
            mUser = MainActivity.sUser;
        } else {
            mUser = (User) getIntent().getExtras().getSerializable("user");
        }

        name.setText(mUser.getName());
        if (mUser.getBirthday() != null && mUser.getBirthday().contains("/")) {
            age.setText((Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(mUser.getBirthday().split("/")[2])) + " anos");
            birthday.setText(mUser.getBirthday());
        } else {
            age.setVisibility(View.GONE);
            ageLabel.setVisibility(View.GONE);
            birthday.setVisibility(View.GONE);
            birthdayabel.setVisibility(View.GONE);
        }

        if (mUser.getCity() != null && mUser.getCity().length() > 0) {
            city.setText(mUser.getCity());
        } else {
            city.setVisibility(View.GONE);
            cityLabel.setVisibility(View.GONE);
        }

        if (mUser.getDistrict() != null && mUser.getDistrict().length() > 0) {
            district.setText(mUser.getDistrict());
        } else {
            district.setVisibility(View.GONE);
            districtLabel.setVisibility(View.GONE);
        }

        new LoadProfileImage(profileImage).execute(mUser.getImageProfile());
        new LoadProfileImage(backgroundImage).execute(mUser.getImageCover());
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

