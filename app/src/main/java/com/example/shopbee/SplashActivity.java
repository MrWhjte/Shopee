package com.example.shopbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NextActivity();
            }
        },2000);
    }

    private void NextActivity() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        //nếu chưa login
        Intent intent;
        if (user==null){
            intent = new Intent(this, Signin.class);
            startActivity(intent);
        }else {
            // người dùng đã login
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

}