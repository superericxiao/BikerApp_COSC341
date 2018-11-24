package com.cosc341.heather.p04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class sends chatUser dummy sms messages
 *  used rather than implementing actual sms functionality
 */
public class ChatActivity extends AppCompatActivity {

    User thisUser;
    User chatUser;

    private final int CHAT_USER_REQUEST_CODE = 0;

    ArrayList<Message> messages;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_avtivity);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        thisUser = bundle.getParcelable("thisUser");
        chatUser = bundle.getParcelable("chatUser");

        if (chatUser != null) {

            findViewById(R.id.rv_chatRecyclerView).setVisibility(View.VISIBLE);
            findViewById(R.id.et_postMessage).setVisibility(View.VISIBLE);
            findViewById(R.id.et_postMessage).setVisibility(View.VISIBLE);
            findViewById(R.id.ib_postMessage).setVisibility(View.VISIBLE);

            findViewById(R.id.tv_noChatAvailable).setVisibility(View.GONE);
            findViewById(R.id.btn_findChat).setVisibility(View.GONE);

            messages = new ArrayList<>();

            recyclerView = findViewById(R.id.rv_chatRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RecyclerViewAdapter(this, messages);
            recyclerView.setAdapter(adapter);

            //postMessage(chatUser, new Date(), "message01");
            loadMessages();

            postMessage(thisUser, "message from this user", new Date());

        } else {
            findViewById(R.id.rv_chatRecyclerView).setVisibility(View.GONE);
            findViewById(R.id.et_postMessage).setVisibility(View.GONE);
            findViewById(R.id.et_postMessage).setVisibility(View.GONE);
            findViewById(R.id.ib_postMessage).setVisibility(View.GONE);

            findViewById(R.id.tv_noChatAvailable).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_findChat).setVisibility(View.VISIBLE);


        }
    }

    /**
     * Loads dummy messages into chat
     */
    private void loadMessages() {
        for (int i = 0; i < 10; i++) {
            postMessage(chatUser, "message " + i, new Date());
            postMessage(thisUser, "message " + ++i, new Date());
        }
    }

    /**
     * Posts a new message to the chat
     *  Creates a new date, gets content from edit text field
     *  Calls postMessage(thisUser, content, date)
     */
    public void postMessage(View view) {
        EditText editText = findViewById(R.id.et_postMessage);
        String content = editText.getText().toString();

        postMessage(thisUser, content, new Date());
    }

    /**
     * Posts a message to the chat
     *  Message is added to messages ArrayList
     *  RecyclerView automatically scrolls down to new message
     *  Called by postMessage(view)
     */
    private void postMessage(User user, String content, Date date) {
        messages.add(new Message(user, content, date)); // creates new message
        adapter.notifyDataSetChanged(); // updates recycler view with new message
        recyclerView.smoothScrollToPosition(messages.size() - 1); // scrolls down to new message
    }

    public void findUserToChat(View view) {
        Intent intent = new Intent(this, GetChatUserActivity.class);
        intent.putExtra("thisUser", thisUser);
        startActivityForResult(intent, CHAT_USER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHAT_USER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                chatUser = data.getParcelableExtra("chatUser");
            }
        }
    }
}
