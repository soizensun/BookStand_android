package com.example.zozen.bookstore.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zozen.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private EditText emailEDT, passwordEDT;
    private Button loginBTN, registerBTN;
    private ProgressBar waitingForLoginPB;
    private FirebaseAuth firebaseAuth;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();

        emailEDT = findViewById(R.id.emailLoginEDT);
        passwordEDT = findViewById(R.id.passwordLoginEDT);
        loginBTN = findViewById(R.id.loginBTN);
        registerBTN = findViewById(R.id.registerLoginBTN);
        waitingForLoginPB = findViewById(R.id.waitingForLoginPB);
        waitingForLoginPB.setVisibility(View.INVISIBLE);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEDT.getText().toString().trim();
                String password  = passwordEDT.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()){
                    if(email.isEmpty()) {
                        emailEDT.setError("please enter your email");
                        return;
                    }
                    if(password.isEmpty()) {
                        passwordEDT.setError("please enter your password");
                        return;
                    }
                }
                else {
                    waitingForLoginPB.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    waitingForLoginPB.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(LoginPage.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });
    }
}
