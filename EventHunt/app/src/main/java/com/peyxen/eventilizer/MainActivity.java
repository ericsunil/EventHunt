package com.peyxen.eventilizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private static final String TAG = "MainActivity";

    public static boolean isPublic, isPaid, isSpecial;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionMenu menu;
    private FloatingActionButton _public, _paid, _special;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = getIntent().getStringExtra("id");
        menu = (FloatingActionMenu) findViewById(R.id.menu);
        _public = (FloatingActionButton) findViewById(R.id._public);
        _paid = (FloatingActionButton) findViewById(R.id._paid);
        _special = (FloatingActionButton)findViewById(R.id._special);

        isPublic = true;
        isPaid = false;
        isSpecial = false;

        _public.setImageResource(R.drawable.ic_supervisor_account_white_24dp);
        _paid.setImageResource(R.drawable.ic_attach_money_white_24dp);
        _special.setImageResource(R.drawable.ic_cake_white_24dp);
        _public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPubEventActivity.class));
            }
        });
        _paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPaidEventActivity.class));
            }
        });

        _special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddSpecialEventActivity.class));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer));
        // populate the navigation drawer
        setProfilePic();
    }
    public void setProfilePic() {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    mNavigationDrawerFragment.setUserData("Dipesh Manandhar", "dpesmdr20@gmail.com", getFacebookProfilePicture(profilePicUrl));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }
    public static Bitmap getFacebookProfilePicture(String url){
        URL facebookProfileURL= null;
        try {
            facebookProfileURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(facebookProfileURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position)
        {
            case 0:
                //home
                break;

            case 1:
                // pub events selected from navigation drawer
                isPublic = true;
                isPaid = false;
                isSpecial=false;
                break;
            case 2:
                // paid events selected from navigation drawer
                isPaid = true;
                isPublic = false;
                isSpecial = false;
                break;
            case 3:
                //special
                isPaid = false;
                isPublic = false;
                isSpecial = true;
                break;
           // case 4:
                //MyEvents
          //      break;
            case 4:
                //MyShop
                startActivity(new Intent(MainActivity.this,AddShop.class));
                break;

            case 5:
                //help & feedback
                startActivity(new Intent(MainActivity.this,Help.class));
                break;
        }
     }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new All(), "All");
        adapter.addFragment(new Nearby(), "Nearby");
        adapter.addFragment(new Upcoming(), "Upcoming");
        adapter.addFragment(new Popular(), "Popular");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
