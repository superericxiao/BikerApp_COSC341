package com.cosc341.heather.p04;

public class TextPost extends Post {

    String content;

    public TextPost(User user, String content) {
        super("1", user);
        setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
