package com.example.mahmoudashraf.mymovies;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment F;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            F = new F_Movie();
            Bundle bundle = new Bundle();
            bundle.putString("Type", "upcoming");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer, F).commit();
            this.setTitle("UpComing Movies");

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Now_Playing) {
            Fragment F=new F_Movie();
            Bundle bundle=new Bundle();
            bundle.putString("Type","now_playing");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Now Playing Movies");
        } else if (id == R.id.UpComing_Movies) {
            Fragment F=new F_Movie();
            Bundle bundle=new Bundle();
            bundle.putString("Type","upcoming");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("UpComing Movies");
        } else if (id == R.id.Popular_Movies) {
            Fragment F=new F_Movie();
            Bundle bundle=new Bundle();
            bundle.putString("Type","popular");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Popular Movies");
        } else if (id == R.id.Top_Rated) {
            Fragment F=new F_Movie();
            Bundle bundle=new Bundle();
            bundle.putString("Type","top_rated");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Top Rated Movies");
        }else if (id==R.id.Popular_Series){
            Fragment F=new F_TV();
            Bundle bundle=new Bundle();
            bundle.putString("Type","popular");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Popular Series");
        }else if (id==R.id.Top_Rated_Series){
            Fragment F=new F_TV();
            Bundle bundle=new Bundle();
            bundle.putString("Type","top_rated");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Popular Series");
        }else if (id==R.id.Latest_Series){
            Fragment F=new F_TV();
            Bundle bundle=new Bundle();
            bundle.putString("Type","latest");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Popular Series");
        }else if (id==R.id.OnAir_Series){
            Fragment F=new F_TV();
            Bundle bundle=new Bundle();
            bundle.putString("Type","on_the_air");
            F.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeContainer,F).commit();
            this.setTitle("Popular Series");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
