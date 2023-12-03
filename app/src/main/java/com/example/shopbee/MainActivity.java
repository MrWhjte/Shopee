package com.example.shopbee;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopbee.Fragment.Favorite;
import com.example.shopbee.Fragment.History;
import com.example.shopbee.Fragment.Home;
import com.example.shopbee.Fragment.home.Add;
import com.example.shopbee.Fragment.home.MainPage;
import com.example.shopbee.Fragment.home.Profile;
import com.example.shopbee.Fragment.home.Search;
import com.example.shopbee.Fragment.home.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int Request_code = 10;
    private DrawerLayout drawerLayout;
    private static final int home = 0;
    private static final int fav = 1;
    private static final int his = 3;
    private static final int pro = 4;


    private int currentFragment = home;

    private ImageView ImgAvatar;
    private TextView textView_name, textView_email;
    private NavigationView navigationView;
    final private Profile mprofile = new Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout();
        UnitUi();

        //bắt sự kiện khi click vào item view
        navigationView.setNavigationItemSelectedListener(this);
        ReplaceFragment(new Home()); // nhảy vào home khi vào app
        navigationView.getMenu().findItem(R.id.navi_home).setChecked(true); // set lựa chọn khi mở menu
        ShowInformation();
    }

    private void DrawerLayout() {

        Toolbar toolbar = findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.DrawLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navi_home) {
            if (currentFragment != home) {
                ReplaceFragment(new Home());
                currentFragment = home;
            }
        } else if (id == R.id.navi_favorite) {
            if (currentFragment != fav) {
                ReplaceFragment(new Favorite());
                currentFragment = fav;
            }
        } else if (id == R.id.navi_history) {
            if (currentFragment != his) {
                ReplaceFragment(new History());
                currentFragment = his;
            }
        } else if (id == R.id.navi_profile) {
            if (currentFragment != pro) {
                ReplaceFragment(mprofile);
                currentFragment = pro;
            }
        } else if (id == R.id.navi_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, Signin.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // xử lý khi nhấn nút back
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ContenFrame, fragment);
        fragmentTransaction.commit();
    }

    private void UnitUi() {
        navigationView = findViewById(R.id.navi_view);
        ImgAvatar = navigationView.getHeaderView(0).findViewById(R.id.img_avt1);
        textView_email = navigationView.getHeaderView(0).findViewById(R.id.textview_email1);
        textView_name = navigationView.getHeaderView(0).findViewById(R.id.textview_name1);
    }

    private void ShowInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri PhotoUrl = user.getPhotoUrl();
        textView_name.setText(name);
        textView_email.setText(email);
        Glide.with(this).load(PhotoUrl).error(R.drawable.avt_default).into(ImgAvatar);

    }

}