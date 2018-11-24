package com.cosc341.heather.p04;

public abstract class Post extends RecyclerViewItem {

    static int numLikes;
    User user;


    public Post(String viewType, User user) {
        super(viewType);
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static int getNumLikes() {
        return numLikes;
    }

    public static void setNumLikes(int numLikes) {
        Post.numLikes = numLikes;
    }
}
