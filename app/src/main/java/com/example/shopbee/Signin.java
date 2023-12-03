package com.example.shopbee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    private LinearLayout layoutSiginup;
    private Button btn_login;
    private EditText get_email, get_pass;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);

        InitUi();
        InitListener();
    }

    private void InitListener() {
        layoutSiginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, Signup.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {
        mAuth = FirebaseAuth.getInstance();
        String email = get_email.getText().toString().trim();
        String pass = get_pass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(Signin.this, "Bạn chưa nhập email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
//        if (pass.isEmpty()){
//            Toast.makeText(Signin.this, "Bạn chưa nhập email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
//        }
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(Signin.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Signin.this, "Tài Khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void InitUi() {
        progressDialog = new ProgressDialog(this);
        layoutSiginup = findViewById(R.id.siginup);
        get_email = findViewById(R.id.input_account);
        get_pass = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.BtnSign_in);

    }

}