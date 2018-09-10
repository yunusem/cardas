package com.cardasproject.cardas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText mEtName;
    private EditText mEtSurname;
    private Switch mSwType;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mEtName = findViewById(R.id.et_name_userinfo);
        mEtSurname = findViewById(R.id.et_surname_userinfo);
        mSwType = findViewById(R.id.sw_type_userinfo);
        mBtnSave = findViewById(R.id.btn_save_userinfo);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String surname = mEtSurname.getText().toString();
                String type = "1";
                if (mSwType.isChecked()) {
                    type = "2";
                }


                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(surname)) {
                    saveProfileInfo(name,surname,type);
                }
            }
        });
    }

    private void saveProfileInfo(String name, String surname, String type) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("surname", surname);
        userMap.put("type", type);
        userMap.put("km", "0");
        userMap.put("wallet", "0");

        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    sendToMain();
                }
            }
        });
    }

    private void sendToMain() {
        Intent main_intent = new Intent(UserInfoActivity.this, MainActivity.class);
        main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main_intent);
        finish();
    }
}
