package com.cardasproject.cardas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtEmail;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mBtnLogin = findViewById(R.id.btn_login_login);
        mBtnRegister = findViewById(R.id.btn_register_login);
        mEtEmail = findViewById(R.id.et_email_login);
        mEtPassword = findViewById(R.id.et_password_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    loginWithEmailAndPassword(email, password);
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToRegister();
            }
        });
    }

    private void loginWithEmailAndPassword(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    sendToMain();
                } else {
                    String task_result = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "Error" + task_result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendToMain() {
        Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
        main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main_intent);
        finish();
    }

    private void sendToRegister() {
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
    }
}
