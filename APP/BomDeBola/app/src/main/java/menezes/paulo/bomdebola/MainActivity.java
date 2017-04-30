package menezes.paulo.bomdebola;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import menezes.paulo.bomdebola.fragments.home.FragmentGames;
import menezes.paulo.bomdebola.fragments.home.FragmentNews;
import menezes.paulo.bomdebola.fragments.home.FragmentTeams;
import menezes.paulo.bomdebola.models.User;
import menezes.paulo.bomdebola.util.LoadProfileImage;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private SharedPreferences mPreferences;
    public static User sUser = null;

    private View mViewHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PostAddActivity.class));
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == "Novidades") {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPreferences = getSharedPreferences("CurrentUser", Activity.MODE_PRIVATE);
        String user = mPreferences.getString("user", null);

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            sUser = new User();
            sUser.load(user);

            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

            if (navigationView != null) {
                setupDrawerContent(navigationView);

                if (mViewHeader == null) {
                    mViewHeader = navigationView.inflateHeaderView(R.layout.drawer_header);
                    CircleImageView profileImage = (CircleImageView) mViewHeader.findViewById(R.id.profile_image);
                    ImageView backgroundImage = (ImageView) mViewHeader.findViewById(R.id.background_image);
                    TextView name = (TextView) mViewHeader.findViewById(R.id.name);

                    name.setText(sUser.getName());

                    new LoadProfileImage(profileImage).execute(sUser.getImageProfile());
                    new LoadProfileImage(backgroundImage).execute(sUser.getImageCover());
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentNews(), "Novidades");
        adapter.addFragment(new FragmentGames(), "Jogos");
        adapter.addFragment(new FragmentTeams(), "Times");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getTitle().toString()) {
                            case "Jogos":
                                startActivity(new Intent(MainActivity.this, GamesActivity.class));
                                break;
                            case "Campeonatos":
                                startActivity(new Intent(MainActivity.this, ChampionshipsActivity.class));
                                break;
                            case "Jogadores":
                                startActivity(new Intent(MainActivity.this, PlayersActivity.class));
                                break;
                            case "Gerenciamento":
                                startActivity(new Intent(MainActivity.this, ManagerActivity.class));
                                break;
                            case "Times":
                                startActivity(new Intent(MainActivity.this, TeamsActivity.class));
                                break;
                            case "Campos":
                                startActivity(new Intent(MainActivity.this, FieldsActivity.class));
                                break;
                            case "Perfil":
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
