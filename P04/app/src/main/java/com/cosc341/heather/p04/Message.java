package com.cosc341.heather.p04;


import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class Message extends RecyclerViewItem {

    private User user;
    private String messageContent;
    private Date date;

    public Message(User user, String messageContent, Date date) {
        super((user.isThisUser() ? "3" : "4"));
        setUser(user);
        setMessageContent(messageContent);
        setDate(date);
    }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

}
