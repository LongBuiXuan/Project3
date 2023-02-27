package com.example.project3.ui;

import java.io.File;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Environment;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.project3.R;

/* Main game ROM library view */
public class LibraryActivity extends AppCompatActivity
{
    private AlertDialog romWarning;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_library);
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        tb.setTitle(getString(R.string.library_title));
        tb.setNavigationIcon(null);
        setSupportActionBar(tb);

        SectionsPagerAdapter spa = new LibraryActivity.SectionsPagerAdapter(getSupportFragmentManager());
        vp = (ViewPager)findViewById(R.id.container);
        vp.setAdapter(spa);

        ((TabLayout)findViewById(R.id.tabs)).setupWithViewPager(vp);

        // No ROMs were found. Instruct user on how to add them
        if (RomCache.getInstance(this).romList.size() == 0) {
            String path = new File(Environment.getExternalStorageDirectory(), getString(R.string.path_roms)).getAbsolutePath();
            romWarning = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_noroms_title))
                    .setMessage(String.format(getString(R.string.dialog_noroms_message), path))
                    .setPositiveButton(android.R.string.ok, null)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        /* Update ViewPager in case the ROM list has changed (e.g., favoriting a
           ROM from the search view) */
        super.onResume();
        vp.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (romWarning != null && romWarning.isShowing())
            romWarning.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;

        } else if (id == R.id.action_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
            return true;
        }  else if (id == R.id.action_help) {
            HelpFragment.newInstance().show(getSupportFragmentManager(), "fragment_help");
        }

        return super.onOptionsItemSelected(item);
    }

    /* Returns a fragment corresponding to one of the sections/tabs/pages */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Instantiate the fragment for the given page
            RomListFragment.SortingMode sm;
            switch (position) {
                case 0:
                    sm = RomListFragment.SortingMode.RECENT;
                    break;
                case 1:
                    sm = RomListFragment.SortingMode.FAVORITE;
                    break;
                default:
                    sm = RomListFragment.SortingMode.ALPHABETICAL;
            }
            return RomListFragment.newInstance(sm);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recent";
                case 1:
                    return "Favorite";
                case 2:
                    return "All";
            }
            return null;
        }
    }
}
