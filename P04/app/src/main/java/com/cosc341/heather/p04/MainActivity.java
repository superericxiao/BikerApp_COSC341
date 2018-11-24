package com.cosc341.heather.p04;

import android.content.Intent;
import android.content.res.AssetManager;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> userArrayList;
    public static ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUsers();
        //setButtonVisibility();
    }

    /**
     * Sets buttons to visible or gone based on criteria
     *  If no user set as thisUser login=Visible, other options=Gone
     */
    private void setButtonVisibility() {
        Button btn_goToLogin = findViewById(R.id.btn_goToLogin);
        Button btn_goToNewsfeed = findViewById(R.id.btn_goToNewsfeed);

        if (currentUser()) {
            btn_goToLogin.setVisibility(View.GONE); // invisible, not in layout
            btn_goToNewsfeed.setVisibility(View.VISIBLE);
        } else {
            btn_goToLogin.setVisibility(View.VISIBLE);
            btn_goToNewsfeed.setVisibility(View.GONE); // invisible, not in layout
        }
    }

    /**
     * Called by btn_login
     *  Goes to login activity
     */
    public void logIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        intent.putParcelableArrayListExtra("users", userArrayList);

        startActivity(intent);
    }


    /**
     * loads users, returns true is successful
     *  Currently loads users into both linkedHashMap and ArrayList in case hashMap doesn't work as intended
     */
    private boolean loadUsers() {

        userArrayList = new ArrayList<>();


        // initializes thisUser
        //userArrayList.add(new User("thisUser", "password", 1000));

        //userArrayList.get(0).setThisUser(true);
        //userArrayList.get(0).setProfilePicture(R.drawable.profilepic_blue);


        AssetManager assetManager = this.getAssets();

        String fileName = "user02.txt";
        String line;


        try (InputStream inputStream = assetManager.open(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 4 && !parts[0].equals("##")) {
                    String[] location = parts[5].split(",");
                    String[] friends = parts[8].split(",");
                    userArrayList.add(new User(parts[1], parts[2], Integer.parseInt(parts[0]) + 1000, parts[3], parts[4],
                            (location.length >= 1) ? location[0] : "" , (location.length >= 2) ? location[1] : "" , (location.length >= 3) ? location[2] : "",
                            new ArrayList<>(Arrays.asList(friends))
                    ));
                }
            }

            users = userArrayList;

            /*
            for (User user: userArrayList) {
                if (!user.isThisUser()) {
                    for (int i = 0; i < 5; i++) {
                        user.addFriend(userArrayList.get((int) (Math.random() * 26) + 1));
                    }
                }
            }
            */
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public void goToNewsfeed(View view) {
        if (!currentUser())
            users.get(users.size() - 1).setThisUser(true);

        Intent intent = new Intent(this, NewsfeedActivity.class);

        Bundle bundle = new Bundle();
        intent.putParcelableArrayListExtra("users", userArrayList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void makePosts() {
        String [] textPost  = {
                "textPost01",
                "textPost02",
                "textPost03",
                "textPost04"
        };





    }

    private boolean currentUser() {
        for (User user: users) {
            if (user.isThisUser()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method, returns user based on sent username
     *  If no user is found, returns null
     */
    public static User getUserFromUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserFromId(int id) {
        for (User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    public static User getThisUser() {
        for (User user: users) {
            if (user.isThisUser()) {
                return user;
            }
        }
        return null;
    }

}
