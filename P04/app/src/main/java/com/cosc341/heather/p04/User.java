package com.cosc341.heather.p04;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class User implements Parcelable, Comparable {

    private String username;
    private String password;

    private int id;

    private Integer profilePicture;
    private Integer headerPicture;


    // FriendOfFriend class used to prevent inifinite loop
    //      User has friends who have friends etc.
    private ArrayList<User> friends;
    private ArrayList<String> friendUsernames;
    private int numFriends;

    private ArrayList<Integer> postIds;

    // indicates whether chatUser is current app chatUser
    private boolean thisUser;


    // user information
    private String email;
    private String phoneNumber;


    private String country;
    private String province;
    private String city;


    // Minimum information required for an account
    public User(String username, String password, int id) {
        this(username, password, id, "", "", "", "", "", null);
    }
    public User(String username, String password, int id, String email, String phoneNumber, String country, String province, String city, ArrayList<String> friendUsernames) {
        setUsername(username);
        setPassword(password);
        setId(id);

        setThisUser(false);

        setProfilePicture(R.drawable.profilepic_grey);
        setHeaderPicture(R.drawable.flowerswallpaper24);

        setEmail(email);
        setPhoneNumber(phoneNumber);
        setCountry(country);
        setProvince(province);
        setCity(city);

        friends = new ArrayList<>();

        this.friendUsernames = new ArrayList<>();

        if (friendUsernames != null) {
           this.friendUsernames = friendUsernames;
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(Integer profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getHeaderPicture() {
        return headerPicture;
    }
    public void setHeaderPicture(Integer headerPicture) {
        this.headerPicture = headerPicture;
    }


    public ArrayList<User> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<User> friends) {
        // adds all friends from chatUser arraylist to FriendOfFriend arraylist
        // uses addFriend method to distinguish between current chatUser and dummy users
        for (User friend : friends) {
            addFriend(friend);
        }
    }
    public void addFriend(User friend) {
        if (isThisUser()) { // if current chatUser, user friends are full users
            //this.friends.add(friend);
            this.friends.add(new User(friend.getUsername(), friend.getPassword(), friend.getId()));
        } else {
            // creates dummy friend using user values
            this.friends.add(new User(friend.getUsername(), friend.getPassword(), friend.getId()));
        }
    }
    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public void addFriend(String username) {
        friendUsernames.add(username);
    }
    public String[] getFriendUsernames() {
        return friendUsernames.toArray(new String[friendUsernames.size()]);
    }

    public int getNumFriends() {
        return numFriends;
    }

    public boolean isThisUser() {
        return thisUser;
    }
    public void setThisUser(boolean thisUser) {
        this.thisUser = thisUser;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }



    @Override
    public String toString() {
        return "id=" + getId() + "; username=" + getUsername() + "; password=" + getPassword() + "; phonenumber=" + getPhoneNumber();
    }


    @Override
    public boolean equals(Object  obj) {
        if (obj == null) {
            return false;
        } else if (((User)obj).getId() != this.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object user) {
        int compareId = ((User)user).getId();
        return this.getId() - compareId;
    }

    public static Comparator<User> UserComparator = new Comparator<User>() {
        @Override
        public int compare(User user1, User user2) {
            String username1 = user1.getUsername();
            String username2 = user2.getUsername();

            return username1.compareTo(username2);
        }
    };





    /**
     * Methods used to extend parcelable
     *  Required to pass User object to other activities
     *
     * Code generated by Parcelable code generator plugin by Michal Charmas
     **/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeInt(this.id);
        dest.writeValue(this.profilePicture);
        dest.writeValue(this.headerPicture);
        dest.writeTypedList(this.friends);
        dest.writeStringList(this.friendUsernames);
        dest.writeInt(this.numFriends);
        dest.writeList(this.postIds);
        dest.writeByte(this.thisUser ? (byte) 1 : (byte) 0);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.country);
        dest.writeString(this.province);
        dest.writeString(this.city);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.id = in.readInt();
        this.profilePicture = (Integer) in.readValue(Integer.class.getClassLoader());
        this.headerPicture = (Integer) in.readValue(Integer.class.getClassLoader());
        this.friends = in.createTypedArrayList(User.CREATOR);
        this.friendUsernames = in.createStringArrayList();
        this.numFriends = in.readInt();
        this.postIds = new ArrayList<Integer>();
        in.readList(this.postIds, Integer.class.getClassLoader());
        this.thisUser = in.readByte() != 0;
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.country = in.readString();
        this.province = in.readString();
        this.city = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
