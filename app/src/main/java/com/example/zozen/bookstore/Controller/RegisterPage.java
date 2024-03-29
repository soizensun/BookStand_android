package com.example.zozen.bookstore.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterPage extends AppCompatActivity {
    private EditText emailEDT, passwordEDT, comfirmPasswordEDT;
    private Button registerBTN;
    private ProgressBar waitForRegisterPB;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEDT = findViewById(R.id.emailEDT);
        passwordEDT = findViewById(R.id.passwordEDT);
        registerBTN = findViewById(R.id.registerBTN);
        comfirmPasswordEDT = findViewById(R.id.confirmPasswordEDT);
        waitForRegisterPB = findViewById(R.id.waitForRegisterPB);
        waitForRegisterPB.setVisibility(View.INVISIBLE);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login", "onClick: login!!!!");

                String email = emailEDT.getText().toString().trim();
                String password = passwordEDT.getText().toString().trim();
                String confirmPassword = comfirmPasswordEDT.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    if(email.isEmpty()) emailEDT.setError("please enter your email for registering");
                    if(password.isEmpty()) passwordEDT.setError("please enter your password for registering");
                    if(confirmPassword.isEmpty()) comfirmPasswordEDT.setError("please enter your password again");
                }
                else{

                    if(password.equals(confirmPassword)){

                        if(password.length() >= 6){
                            waitForRegisterPB.setVisibility(View.VISIBLE);
                            Log.d("============", email + " " + password);
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            waitForRegisterPB.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                                            } else {
                                                Toast.makeText(RegisterPage.this, "duplicate email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            passwordEDT.setError("your password have to more than 6 character");
                        }

                    }
                    else {
                        Toast.makeText(RegisterPage.this,"password is not match", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }
}
