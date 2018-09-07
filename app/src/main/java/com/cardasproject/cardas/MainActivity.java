package com.cardasproject.cardas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button mBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mBtnLogout = findViewById(R.id.btn_logout_main);

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) sendToStart();
    }

    private void sendToStart() {
        Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login_intent);
        finish();
    }

    private void logout() {
        mAuth.signOut();
        sendToStart();
    }
}
