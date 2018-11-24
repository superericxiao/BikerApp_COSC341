package com.cosc341.heather.p04;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

public class UserProfileActivity extends AppCompatActivity {

    User user;

    LinkedList<Post> userPosts;
    String[] friendUsernames;

    RecyclerView rv_userPosts;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // gets chatUser from intent
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        // sets profile and header pictures
        ImageView iv_userAvatar = findViewById(R.id.iv_userProfile);
        ImageView iv_userHeader = findViewById(R.id.iv_userHeader);

        iv_userAvatar.setImageResource(user.getProfilePicture());
        iv_userHeader.setImageResource(user.getHeaderPicture());

        // sets username
        TextView tv_userName = findViewById(R.id.tv_userName);
        tv_userName.setText(user.getUsername());

        //TextView textView = findViewById(R.id.textView2);
        //textView.setText("About User");

        //users = new LinkedList<>();
        //users.add(chatUser);
        //users.add(chatUser);

        userPosts = new LinkedList<>();
        //userFriends = user.getFriends();
        friendUsernames = user.getFriendUsernames();

        // set up the RecyclerView
        rv_userPosts = findViewById(R.id.rv_userPosts);
        rv_userPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, userPosts);
        rv_userPosts.setAdapter(adapter);

        createUserPosts();
        createFriendsList();

        // cosmetic: sets button colours to white and layout visibilities to invisible
        findViewById(R.id.btn_aboutUser).setBackgroundColor(Color.WHITE);
        findViewById(R.id.btn_userPosts).setBackgroundColor(Color.WHITE);
        findViewById(R.id.btn_userFriends).setBackgroundColor(Color.WHITE);

        findViewById(R.id.ll_aboutUser).setVisibility(View.INVISIBLE);
        rv_userPosts.setVisibility(View.INVISIBLE);
        findViewById(R.id.sv_userFriends).setVisibility(View.INVISIBLE);
    }

    private void createUserPosts() {
        userPosts.addFirst(new TextPost(user, "TextPost1"));
        userPosts.addFirst(new ActivityPost(user, new Route("route1", 5)));
        userPosts.addFirst(new TextPost(user, "TextPost2"));
        userPosts.addFirst(new ActivityPost(user, new Route("route2", 5)));
        userPosts.addFirst(new TextPost(user, "TextPost3"));
        userPosts.addFirst(new ActivityPost(user, new Route("route3", 5)));

        adapter.notifyDataSetChanged();
    }

    /**
     * Similar code as in FriendsListActivity
     *  User icons are imageViews rather than imageButtons
     *  User chat not available
     *  Empty letters not displayed
     *
     */
    private void createFriendsList() {

        LinearLayout table = findViewById(R.id.tableRows);

        // sort users alphabetically
        //Collections.sort(userFriends, User.UserComparator);
        Arrays.sort(friendUsernames);

        LinearLayout row;
        TextView textView;

        User user;

        for (String username: friendUsernames) {

            user = MainActivity.getUserFromUsername(username);

            // row information is added to
            row = new LinearLayout(this);

            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setOrientation(LinearLayout.HORIZONTAL);

            // Image button for going to chatUser's profile
            ImageView iv_userProfile = new ImageView(this);
            iv_userProfile.setImageResource(user.getProfilePicture());

            iv_userProfile.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            iv_userProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);


            // text view with chatUser's name
            textView = new TextView(this);
            textView.setText("\t" + username);


            // adds textView to row, and row to table
            row.addView(iv_userProfile);
            row.addView(textView);
            table.addView(row);
        }

    }

    public void buttonClicked(View view) {

        LinearLayout ll_aboutUser = findViewById(R.id.ll_aboutUser);
        ScrollView sv_userFriends = findViewById(R.id.sv_userFriends);

        Button btn_aboutUser = findViewById(R.id.btn_aboutUser);
        Button btn_userPosts = findViewById(R.id.btn_userPosts);
        Button btn_userFriends = findViewById(R.id.btn_userFriends);

        switch (view.getId()) {
            case R.id.btn_aboutUser:
                btn_aboutUser.setBackgroundColor(Color.WHITE);
                btn_userPosts.setBackgroundColor(Color.BLUE);
                btn_userFriends.setBackgroundColor(Color.BLUE);

                ll_aboutUser.setVisibility(View.VISIBLE);
                rv_userPosts.setVisibility(View.INVISIBLE);
                sv_userFriends.setVisibility(View.INVISIBLE);

                loadAboutUser(user);

                break;

            case R.id.btn_userPosts:
                btn_aboutUser.setBackgroundColor(Color.BLUE);
                btn_userPosts.setBackgroundColor(Color.WHITE);
                btn_userFriends.setBackgroundColor(Color.BLUE);

                ll_aboutUser.setVisibility(View.INVISIBLE);
                rv_userPosts.setVisibility(View.VISIBLE);
                sv_userFriends.setVisibility(View.INVISIBLE);

                break;

            case R.id.btn_userFriends:
                btn_aboutUser.setBackgroundColor(Color.BLUE);
                btn_userPosts.setBackgroundColor(Color.BLUE);
                btn_userFriends.setBackgroundColor(Color.WHITE);

                ll_aboutUser.setVisibility(View.INVISIBLE);
                rv_userPosts.setVisibility(View.INVISIBLE);
                sv_userFriends.setVisibility(View.VISIBLE);

                break;
        }

    }

    private void loadAboutUser(User user) {
        LinearLayout ll_aboutUser = findViewById(R.id.ll_aboutUser);
        ll_aboutUser.removeAllViews();

        TextView textView = new TextView(this);
        StringBuilder textString = new StringBuilder();
        if (textString.length() > 0) {
            textString.setLength(0);
        }

        if (user.getEmail() != null && !user.getEmail().equals("")) { textString.append("\nEmail: "); textString.append(user.getEmail()); }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().equals("")) { textString.append("\nPhone number: "); textString.append(user.getPhoneNumber()); }
        if (user.getCountry() != null && !user.getCountry().equals("")) { textString.append("\nCountry: "); textString.append(user.getCountry()); }
        if (user.getProvince() != null && !user.getProvince().equals("")) { textString.append("\nProvince: "); textString.append(user.getProvince()); }
        if (user.getCity() != null && !user.getCity().equals("")) { textString.append("\nCity: "); textString.append(user.getCity()); }

        if (textString.length() == 0) { textString.append("No information to display"); }

        textView.setText(textString);
        textView.setTextSize(24);

        ll_aboutUser.addView(textView);
    }

}
