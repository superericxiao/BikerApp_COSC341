package com.cosc341.heather.p04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity to log in or to create an account
 *  Entered automatically when app opened
 */
public class LoginActivity extends AppCompatActivity {

    private ArrayList<User> users;

    // criteria for username and password when creating an account
    private static String usernameCriteria;
    private static String passwordCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        users = intent.getParcelableArrayListExtra("users");

        // changed in validUsername and validPassword methods
        usernameCriteria = "";
        passwordCriteria = "";
    }

    /**
     * Tries to login user based on text in tv_loginUsername and tv_loginPassword
     *  Called when btn_login pressed
     */
    public void login(View view) {
        TextView tv_username = findViewById(R.id.tv_loginUsername);
        TextView tv_password = findViewById(R.id.tv_loginPassword);

        String username = tv_username.getText().toString();
        String password = tv_password.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            User user = isUser(username); // return null if no user exists with username
            if (user == null) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            } else { // invalid password
                if (!user.getPassword().equals(password)) {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                    tv_password.setText("");
                } else {
                    user.setThisUser(true);
                    finish();
                }
            }
        }
    }

    /**
     * Checks if a user exists with the username
     *  Returns null if no user found
     *  Called when login or createAccount button pressed
     */
    private User isUser(String username) {

        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Creates user if no other user exists with username
     */
    public void createAccount (View view) {
        TextView tv_username = findViewById(R.id.tv_loginUsername);
        TextView tv_password = findViewById(R.id.tv_loginPassword);

        String username = tv_username.getText().toString();
        String password = tv_password.getText().toString();

        if (username.equals("")) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            User user = isUser(username); // return null if no user exists with username
            if (user != null) {
                Toast.makeText(this, "User already exists with username", Toast.LENGTH_SHORT).show();
            } else if(!validUsername(username)) {
                Toast.makeText(this, "Invalid username\nUsername must " + usernameCriteria, Toast.LENGTH_LONG).show();
            } else if (!validPassword(password)) {
                Toast.makeText(this, "Invalid password\nPassword must " + passwordCriteria, Toast.LENGTH_LONG).show();
            } else {
                user = new User(username, password, users.size());
                user.setThisUser(true);
                finish();
            }
        }

    }

    private boolean validUsername(String username) {
        usernameCriteria = ""; // no current username criteria
        return true;
    }

    private boolean validPassword(String password) {
        passwordCriteria += "contain one number";

        // checks if password contains a number
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
