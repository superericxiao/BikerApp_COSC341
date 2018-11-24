package com.cosc341.heather.p04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Newsfeed shows posts made by all users
 *  Posts shown in RecyclerView
 *  Newsfeed links to home, chatActivity, FriendsListActivity, and userProfile
 *  Todo posts should be sorted and added chronologically
 *  Todo users should be loaded from mainActivity
 *  Todo posts should be tied to a user
 *  Todo goToHome() and makeActivityPost() not fully implemented
 */
public class NewsfeedActivity extends AppCompatActivity { //implements NewsfeedActivity.RecyclerViewAdapter { //.ItemClickListener {

    // infinite scroll to show posts
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    // posts in newsfeed
    LinkedList<Post> newsfeedPosts; // Linked list used because items inserted at beginning of list

    // used for dummy posts
    static int numTextPosts;
    static int numActivityPosts;

    // All users
    public static ArrayList<User> users;
    private User thisUser;

    // int added onto chatUser id to create unique button id
    // Also used in friends list - currently separate instance
    private static final int USER_PROFILE_ID_CODE = 10000;

    // chatUser being chatted with, = 0 if no chat open
    //  currently refers to users spot in arrayList
    static int chatUserId = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        // used for creating dummy posts
        numTextPosts = 0;
        numActivityPosts = 0;

        /*
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        try {
            users = bundle.getParcelableArrayList("users");
        } catch (Exception ex) {
            System.err.println(ex);
        }
        */

        thisUser = MainActivity.getThisUser();
        users = MainActivity.users;

        // sets thisUser avatar and header image
        ImageButton ib_userAvatar = findViewById(R.id.ib_userAvatar);
        ib_userAvatar.setId(USER_PROFILE_ID_CODE + thisUser.getId());

        ImageView iv_header = findViewById(R.id.iv_userHeader);

        ib_userAvatar.setImageResource(thisUser.getProfilePicture());
        iv_header.setImageResource(thisUser.getHeaderPicture());

        // sets thisUser avatar to go to thisUser profile when clicked
        ib_userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUserProfile(view);
            }
        });

        // newsfeed containing posts
        newsfeedPosts = new LinkedList<>();

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rv_recyclerView01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, newsfeedPosts);
        recyclerView.setAdapter(adapter);

        // creates dummy posts !must be after recyclerView adapter creation
        makeDummyPosts();

        /*
         * Bottom menu bar
         *  ib_home links to mainActivity calls goToHome()
         *  ib_chat links to chatActivity calls goToChat()
         *  ib_friends links to friendListActivity calls goToFriendList()
         */
        ImageButton ib_home = findViewById(R.id.ib_home);
        ImageButton ib_chat = findViewById(R.id.ib_chat);
        ImageButton ib_friends = findViewById(R.id.ib_friends);

        ib_home.setImageResource(R.drawable.home_icon);
        ib_chat.setImageResource(R.drawable.chat_icon);
        ib_friends.setImageResource(R.drawable.friends_icon);
    }

    /**
     * Dummy posts made by users 1-6
     *  Used to populate newsfeed
     */
    public void makeDummyPosts() {
        makeTextPost(users.get(1), "TextPost");
        makeActivityPost(users.get(2), new Route("route" + (++numActivityPosts), 5));
        makeTextPost(users.get(3), "TextPost" + (++numTextPosts));
        makeActivityPost(users.get(4), new Route("route" + (++numActivityPosts), 5));
        makeTextPost(users.get(5), "TextPost" + (++numTextPosts));
        makeActivityPost(users.get(6), new Route("route" + (++numActivityPosts), 5));
    }

    //Todo clear editText field and minimize keyboard after post made
    /**
     * Makes a text post from text in edit text field
     *  Gets text from multiline edit text box
     *  Called when post button pressed
     *  Sends thisUser and messageContent to makeTextPost(user, messageContent)
     */
    public void makeTextPost(View view) {
         EditText et_post = findViewById(R.id.et_post);
         String content = et_post.getText().toString();
         System.out.println(content);

         makeTextPost(thisUser, content);
    }

    /**
     * Creates text post and posts it to newsfeed
     *  Called from makeTextPost(view), not linked to any UI elements
     *  Uses RecyclerView adapter to update newsfeed
     */
    public void makeTextPost(User user, String content) {
        newsfeedPosts.addFirst(new TextPost(user, content)); // text post posted to top of feed/first element in linkedList
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(0);

    }

    // TODO: 2018-11-21 implement makeActivityPost
    /**
     * Not Implemented
     */
    public void makeActivityPost(View view) {
        //newsfeedPosts.addFirst(new ActivityPost("user02", R.drawable.flowerswallpaper24, new Route("route" + (++numActivityPosts), 5)));
        //makeActivityPost();
        //adapter.notifyDataSetChanged();
    }
    /**
     * Adds Activity post
     */
    public void makeActivityPost(User user, Route route) {
        newsfeedPosts.addFirst(new ActivityPost(user, route));
        adapter.notifyDataSetChanged();
    }

    /**
     * Goes back to main activity
     *  Called when bottom menu center button pressed
     *  Not yet implemented
     */
    public void goToHome(View view) {
        this.finish();
    }

    /**
     * Goes to friend list activity
     *  Called when bottom menu left button pressed
     *  Sends arraylist of all users in from thisUser.getFriends()
     */
    public void goToFriendsList(View view) {
        Intent intent = new Intent(this, FriendsListActivity.class);
        intent.putExtra("thisUser", MainActivity.getThisUser());
        startActivity(intent);
    }

    /**
     * Goes to chat activity
     *  Called when bottom menu right button pressed
     *  Sends thisUser as "thisUser" and user being chatted with as "chatUser"
     */
    public void goToChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);

        Bundle bundle = new Bundle();

        bundle.putParcelable("thisUser", thisUser);
        //bundle.putParcelable("chatUser", MainActivity.getUserFromUsername(thisUser.chattingWith));
        bundle.putParcelable("chatUser", users.get(0));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Goes to user profile
     *  Called whenever a user's profile pic is clicked in the newsfeed
     *  Sends associated user
     */
    public void goToUserProfile(View view) {
        // gets id of user
        int id = view.getId() - USER_PROFILE_ID_CODE;
        User user = MainActivity.getUserFromId(id);

        if (user == null) {
            Toast.makeText(this, "Error: invalid user", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }



}



