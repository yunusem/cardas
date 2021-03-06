package com.cardasproject.cardas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    private Button mBtnLogout;
    private Button mBtnEdit;
    private ProgressBar mPbUserInfo;

    private String uid;
    private String name;
    private String surname;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mBtnLogout = findViewById(R.id.btn_logout_main);
        mBtnEdit = findViewById(R.id.btn_editprofile_main);
        mPbUserInfo = findViewById(R.id.pb_userinfo_main);

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToEditProfile();
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void sendToEditProfile() {
        Intent userInfo_intent = new Intent(MainActivity.this, UserInfoActivity.class);
        userInfo_intent.putExtra("name", name);
        userInfo_intent.putExtra("surname", surname);
        userInfo_intent.putExtra("type", type);
        userInfo_intent.putExtra("purpose", "edit");
        startActivity(userInfo_intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) sendToStart();

        else {
            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            uid = current_user.getUid();
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    name = dataSnapshot.child("name").getValue().toString();
                    surname = dataSnapshot.child("surname").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();

                    if (name.equals("empty") || surname.equals("empty") || type.equals("0")) {
                        sendToUserInfo();
                    } else {
                        profileLoaded();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void profileLoaded() {
        mPbUserInfo.setVisibility(View.GONE);
        mBtnEdit.setVisibility(View.VISIBLE);
        mBtnLogout.setVisibility(View.VISIBLE);
    }

    private void sendToUserInfo() {
        Intent userInfo_intent = new Intent(MainActivity.this, UserInfoActivity.class);
        userInfo_intent.putExtra("purpose", "new");
        startActivity(userInfo_intent);
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
