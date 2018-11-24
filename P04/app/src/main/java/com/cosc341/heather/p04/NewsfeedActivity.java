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


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        try {
            users = bundle.getParcelableArrayList("users");
        } catch (Exception ex) {
            System.err.println(ex);
        }

        // sets thisUser avatar and header image
        ImageButton ib_userAvatar = findViewById(R.id.ib_userAvatar);
        ib_userAvatar.setId(USER_PROFILE_ID_CODE + users.get(0).getId());

        ImageView iv_header = findViewById(R.id.iv_userHeader);

        ib_userAvatar.setImageResource(users.get(0).getProfilePicture());
        iv_header.setImageResource(users.get(0).getHeaderPicture());

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

    // TODO: 2018-11-21 Move to mainActivity, pass users from there
    /**
     * Loads users into arrayList
     *  currently contains dummy users, change to loading from file
     */
    public void loadUsers() {
        users = new ArrayList<>();

        // thisUser + 26 total dummy users. thisUser index = 0

        // Adds this thisUser to users array
        users.add(new User("thisUser", "password", 1000));
        users.get(0).setThisUser(true);
        users.get(0).setProfilePicture(R.drawable.profilepic_blue);

        // Adds dummy users to array
        users.add(new User("abigail", "password", 1007));
        users.add(new User("brandon", "password", 1008));
        users.add(new User("charlie", "password", 1009));
        users.add(new User("duncan", "password", 1010));
        users.add(new User("effie", "password", 1011));

        users.add(new User("fred", "password", 1012));
        users.add(new User("george", "password", 1013));
        users.add(new User("hannah", "password", 1014));
        users.add(new User("isaac", "password", 1015));
        users.add(new User("jack", "password", 1016));

        users.add(new User("kellie", "password", 1017));
        users.add(new User("lydia", "password", 1018));
        users.add(new User("monica", "password", 1019));
        users.add(new User("nathan", "password", 1020));
        users.add(new User("ophelia", "password", 1021));

        users.add(new User("patricia", "password", 1022));
        users.add(new User("quinn", "password", 1023));
        users.add(new User("rachel", "password", 1024));
        users.add(new User("sarah", "password", 1025));
        users.add(new User("tony", "password", 1026));

        users.add(new User("ulysses", "password", 1027));
        users.add(new User("vanessa", "password", 1028));
        users.add(new User("william", "password", 1029));
        users.add(new User("xavier", "password", 1030));
        users.add(new User("yvette", "password", 1031));

        users.add(new User("zachariah", "password", 1032));


        // adds every other chatUser to thisUser's friend list
        for (int i = 0; i < users.size(); i += 2) {
            if (users.get(i) != null && !users.get(i).isThisUser()) {
                users.get(0).addFriend(users.get(i));
            }
        }

        // adds five random friends to each chatUser [can be same chatUser] except thisUser (chatUser(0))
        // may add same chatUser multiple times or chatUser friends are being added to
        for (User user: users) {
            if (!user.isThisUser()) {
                for (int i = 0; i < 5; i++) {
                    user.addFriend(users.get((int) (Math.random() * 26) + 1));
                }
            }
        }

        // sets chatUser being chatted with to chatUser 1007 (abigail)
        chatUserId = 1;

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

         makeTextPost(users.get(0), content);
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
        intent.putParcelableArrayListExtra("users", users.get(0).getFriends());
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

        bundle.putParcelable("thisUser", users.get(0));
        bundle.putParcelable("chatUser", users.get(chatUserId));

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
        User user;

        if (id == 1000) { // current user
            user = users.get(0);
        } else {
            user = new User("user", "password", -1);

            for (User user1 : users) {
                if (user1.getId() == id) {
                    user = user1;
                    break;
                }
            }
        }

        if (user.getId() == -1) {
            Toast.makeText(this, "Error: invalid user", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }



}



