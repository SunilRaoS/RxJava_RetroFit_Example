package com.telstra.sunil.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.telstra.sunil.R;
import com.telstra.sunil.databinding.ActivityMainBinding;
import com.telstra.sunil.view.adapter.HeaderDataAdapter;
import com.telstra.sunil.viewmodel.HeaderViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding activityMainBinding;
    private HeaderViewModel headerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activityMainBinding.drawerLayout, activityMainBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityMainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        activityMainBinding.navView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        headerViewModel = new HeaderViewModel(this);
        activityMainBinding.setHeaderViewModel(headerViewModel);

        //Creating Adapter
        HeaderDataAdapter headerDataAdapter = new HeaderDataAdapter();
        activityMainBinding.listRow.setAdapter(headerDataAdapter);
        activityMainBinding.listRow.setLayoutManager(new LinearLayoutManager(this));

        //Add Observable
        headerViewModel.addObserver(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof HeaderViewModel) {
            HeaderDataAdapter headerDataAdapter = (HeaderDataAdapter) activityMainBinding.listRow.getAdapter();
            HeaderViewModel headerViewModel = (HeaderViewModel) o;
            if (headerDataAdapter != null) {
                for (int i = 0; i < headerViewModel.getRowItemList().size(); i++) {
                    if (headerViewModel.getRowItemList().get(i).getTitle() == null &&
                            headerViewModel.getRowItemList().get(i).getImageHref() == null &&
                            headerViewModel.getRowItemList().get(i).getDescription() == null)
                        headerViewModel.getRowItemList().remove(i);

                    if (headerViewModel.getRowItemList().get(i).getTitle() == null &&
                            headerViewModel.getRowItemList().get(i).getDescription() == null)
                        headerViewModel.getRowItemList().remove(i);
                }
                headerDataAdapter.setRowItems(headerViewModel.getRowItemList());
            }
            activityMainBinding.toolbar.setTitle(headerViewModel.getTitle());
        }
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        headerViewModel.reset();
    }
}
