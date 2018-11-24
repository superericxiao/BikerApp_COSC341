package com.cosc341.heather.p04;

public class ActivityPost extends Post {

    Route route;

    public ActivityPost(User user, Route route) {
        super("2", user);
        setRoute(route);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
