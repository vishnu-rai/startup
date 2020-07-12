package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    private fragfeed fragFeed;
    private fragmap fragMap;
    private fragnotification fragNotification;

    DrawerLayout drawerLayout;
    public NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);
        drawerLayout = findViewById(R.id.drawer_main);


        fragFeed=new fragfeed();
        fragMap=new fragmap();
        fragNotification=new fragnotification();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),0);

        viewPagerAdapter.addFragment(fragFeed, "Feed");
        viewPagerAdapter.addFragment(fragMap, "Map");
        viewPagerAdapter.addFragment(fragNotification, "Request");
        viewPager.setAdapter(viewPagerAdapter);


        tabLayout.getTabAt(0).setIcon(R.drawable.feed_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.map_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.active_notification_icon);

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.profile_btn:
//                startActivity(new Intent(Homepage.this, userprofile.class));
//                break;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

//            case R.id.nav_1:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new fragmentone()).addToBackStack(null).commit();
//                break;
//
//            case R.id.nav_2:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new fragmenttwo()).addToBackStack(null).commit();
//                break;
//
//            case R.id.nav_3:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new fragmentthree()).addToBackStack(null).commit();
//                break;
//
//            case R.id.nav_4:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new fragmentfour()).addToBackStack(null).commit();
//                break;
//
//            case R.id.logout:
//                logout();
//                break;

            case R.id.profile_btn:
                startActivity(new Intent(Homepage.this, Login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
