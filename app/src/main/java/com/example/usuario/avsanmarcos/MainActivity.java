package com.example.usuario.avsanmarcos;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.fragment.Administrador;
import com.example.usuario.fragment.Facultad;
import com.example.usuario.fragment.MainFragment;
import com.example.usuario.fragment.Noticias;
import com.example.usuario.fragment.Ubicacion;
import com.example.usuario.fragment.Universidad;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportMapFragment = SupportMapFragment.newInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new MainFragment()).commit();

        supportMapFragment.getMapAsync(this);
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
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentManager fragmentManager1 = getSupportFragmentManager();
        int id = item.getItemId();

        if(supportMapFragment.isAdded()){
            fragmentManager1.beginTransaction().hide(supportMapFragment).commit();
        }

        switch (id){
            case R.id.nav_universidad:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Universidad()).commit();
                break;
            case R.id.nav_facultad:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new Administrador()).commit();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Facultad()).commit();
                break;
            case R.id.nav_institucion:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Ubicacion()).commit();
                break;
            case R.id.nav_noticias:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Noticias()).commit();
                break;
            case R.id.nav_ubicate:
                break;
            case R.id.nav_send:
                LayoutInflater layoutInflater =(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(null, googleMap.toString());
        /*LatLng UNMSM = new LatLng(-12.0560204,-77.0866113);
        supportMapFragment.getMap().addMarker(new MarkerOptions().position(UNMSM).title("Universidad Nacional Mayor de San Marcos"));
        supportMapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLng(UNMSM));*/
    }
}
