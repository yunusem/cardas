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

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtEmail = findViewById(R.id.et_email_register);
        mEtPassword = findViewById(R.id.et_password_register);
        mEtRePassword = findViewById(R.id.et_repassword_register);
        mBtnRegister = findViewById(R.id.btn_register_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();
                String confirm = mEtRePassword.getText().toString();

                if (!TextUtils.isEmpty(email)
                        || !TextUtils.isEmpty(password)
                        || !TextUtils.isEmpty(confirm)) {

                    if (Objects.equals(password, confirm)) {
                        registerWithEmailAndPassword(email, password);
                    }
                }

            }
        });
    }

    private void registerWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendToMain();
                } else {
                    String task_result = task.getException().getMessage().toString();
                    Toast.makeText(RegisterActivity.this, "Error" + task_result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendToMain() {
        Intent main_intent = new Intent(RegisterActivity.this, MainActivity.class);
        main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main_intent);
        finish();
    }
}
