package com.example.shopbee.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopbee.Fragment.home.Add;
import com.example.shopbee.Fragment.home.MainPage;
import com.example.shopbee.Fragment.home.Profile;
import com.example.shopbee.Fragment.home.Search;
import com.example.shopbee.Fragment.home.Setting;
import com.example.shopbee.R;
import com.example.shopbee.Signup;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;


public class Home extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private BottomNavigationView bottomNavigationView;
    private View mView;
    public static final int Request_code = 10;
    private static final int home = 0;
    private static final int sea = 1;
    private static final int add = 2;
    private static final int pro = 3;
    private static final int set = 4;
    private int currentFragment = home;
    final private Profile Mprofile = new Profile();

    public Home() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        iniUit();
        ItemIsSelect();
        return mView;
    }

    private void iniUit() {
        bottomNavigationView = mView.findViewById(R.id.bottom_navigation);
        ReplaceFragment(new MainPage());
    }

    private void ItemIsSelect() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_search) {
                    processNavigationClick(new Search(), sea);
                } else if (id == R.id.bottom_home) {
                    processNavigationClick(new MainPage(), home);
                } else if (id == R.id.bottom_Placeholder_add) {
                    processNavigationClick(new Add(), add);
                } else if (id == R.id.bottom_profile) {
                    processNavigationClick(Mprofile, pro);
                } else if (id == R.id.bottom_Setting) {
                    processNavigationClick(new Setting(), set);
                }
                return true;
            }
        });
    }

    private void processNavigationClick(Fragment fragment, int mcurrentFragment) {
        if (currentFragment != mcurrentFragment) {
            ReplaceFragment(fragment);
            currentFragment = mcurrentFragment;
        }
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}