package com.example.shopbee;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private EditText email, pass;
    private Button BtnSign_up;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        InitUI();
        InitListener();
    }
    private void InitListener() {
        BtnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickSignup();
            }
        });
    }

    private void OnClickSignup() {
        String stremail = email.getText().toString().trim();
        // bổ xung check xem có bỏ trống hay ko
        String strpass = pass.getText().toString().trim();

        if (stremail.isEmpty() || strpass.isEmpty() ){
            Toast.makeText(Signup.this, "Bạn chưa nhập email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
        }else {

//        if (strpass.isEmpty()){
//            Toast.makeText(Signup.this, "Bạn chưa nhập email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
//        }
            mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(stremail, strpass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity(); //đóng tất cả các activi
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signup.this, "Đăng ký thất bại.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void InitUI() {
        email = findViewById(R.id.sign_up_account);
        pass = findViewById(R.id.sign_up_password);
        BtnSign_up = findViewById(R.id.BtnSign_up);
        progressDialog = new ProgressDialog(this);// tạo màn hình load
    }
}