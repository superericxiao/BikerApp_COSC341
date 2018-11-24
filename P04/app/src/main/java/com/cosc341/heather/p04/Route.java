package com.cosc341.heather.p04;

public class Route {

    String routeName;
    Integer routeImage;

    public Route(String routeName, Integer routeImage) {
        setRouteName(routeName);
        setRouteImage(routeImage);
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getRouteImage() {
        return routeImage;
    }

    public void setRouteImage(Integer routeImage) {
        this.routeImage = routeImage;
    }
}
