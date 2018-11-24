package com.cosc341.heather.p04;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.cosc341.heather.p04.User.UserComparator;

public class FriendsListActivity extends AppCompatActivity {

    // int added onto chatUser id to create unique button id
    private static final int USER_PROFILE_ID_CODE = 10000;
    private static final int USER_CHAT_ID_CODE = 20000;

   // ArrayList<User> users;
    User thisUser;
    User[] userFriends;

    /**
     * Alphabetical list of user's friends
     * button to see their profile
     * button to chat with them
     * scrollable, but less updates
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);


        Intent intent = getIntent();
        thisUser = intent.getParcelableExtra("thisUser");

        //users = MainActivity.users;

        String[] friendUsernames = thisUser.getFriendUsernames();
        userFriends = new User[friendUsernames.length];

        for (int i = 0; i < friendUsernames.length; i++) {
            userFriends[i] = MainActivity.getUserFromUsername(friendUsernames[i]);
        }


        LinearLayout table = findViewById(R.id.tableRows);
        char letter = 'A' - 1; // starts at character before a

        // sort users alphabetically
        Arrays.sort(userFriends, UserComparator);

        LinearLayout row;
        TextView textView;

        /*
         * iterates through each chatUser in the friend list
         *  assumes arrayList has been sorted alphabetically
         *      if not usernames will be printed to unsorted username,
         *      will skip until username at appropriate letter
         *      then print all other username after next appropriate letter or 'Z'
         */
        for (User user: userFriends) {
            String username = user.getUsername();
            /*
            * Iterates through alphabet based on username
            *      if username starts with letter, print username
            *      otherwise print letters until username first letter reached or letter = Z
            *          Change to end on special char
            */

            while (username.toUpperCase().charAt(0) != letter && letter <= 'Z') { // if username starts with next letter
                // increment letter
                letter++;

                // create new row and textView to display letter
                row = new LinearLayout(this);

                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.setOrientation(LinearLayout.HORIZONTAL);

                textView = new TextView(this);
                textView.setText("Row: " + letter);

                // add textView to row and row to table
                row.addView(textView);
                table.addView(row);
            }
            // row information is added to
            row = new LinearLayout(this);

            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setOrientation(LinearLayout.HORIZONTAL);


            // Image button for going to chatUser's profile
            ImageButton ib_userProfile = new ImageButton(this);
            ib_userProfile.setImageResource(user.getProfilePicture());

            // imageButton id = userId [1%%%] + 10000 = 11%%%
            ib_userProfile.setId(USER_PROFILE_ID_CODE + user.getId());

            ib_userProfile.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            ib_userProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // calls goToUserProfile when button pressed
            ib_userProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToUserProfile(view);
                }
            });


            // text view with chatUser's name
            textView = new TextView(this);
            textView.setText("\t" + username);


            // image button for chatting with chatUser
            ImageButton ib_userChat = new ImageButton(this);
            ib_userChat.setImageResource(R.drawable.chat_icon);

            // imageButton id = userId [1%%%] + 20000 = 21%%%
            ib_userChat.setId(USER_CHAT_ID_CODE + user.getId());

            ib_userChat.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            ib_userChat.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // calls goToUserProfile when button pressed
            ib_userChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToUserChat(view);
                }
            });


            // adds textView to row, and row to table
            row.addView(ib_userProfile);
            row.addView(textView);
            row.addView(ib_userChat);
            table.addView(row);
        }
    }
    /**
     * goes to profile of chatUser when button pressed based on chatUser id
    */
    public void goToUserProfile(View view) {
        // gets id of chatUser
        int id = view.getId() - USER_PROFILE_ID_CODE;
        User user = new User("chatUser", "password", -1);

        for (User user1 : userFriends) {
            if (user1.getId() == id) {
                user = user1;
                break;
            }
        }

        if (user.getId() != -1) {
            Toast.makeText(this, user.getUsername() + " profile clicked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: invalid chatUser", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * goes to chat with chatUser based on chatUser id when button pressed
     */
    public void goToUserChat(View view) {
        // gets id of chatUser
        int id = view.getId() - USER_CHAT_ID_CODE;
        User user = new User("chatUser", "password", -1);

        for (User user1 : userFriends) {
            if (user1.getId() == id) {
                user = user1;
                break;
            }
        }

        if (user.getId() != -1) {
            Toast.makeText(this, user.getUsername() + " chat clicked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: invalid chatUser", Toast.LENGTH_SHORT).show();
        }
    }

}
