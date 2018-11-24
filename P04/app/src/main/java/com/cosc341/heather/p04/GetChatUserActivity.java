package com.cosc341.heather.p04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GetChatUserActivity extends AppCompatActivity {

    private User thisUser;
    private User chatUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_chat_user);

        Intent intent = getIntent();
        thisUser = intent.getParcelableExtra("thisUser");
    }



    private void gotUser() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("chatUser", chatUser);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
