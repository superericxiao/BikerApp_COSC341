package com.cosc341.heather.p04;

abstract class RecyclerViewItem {

    private String viewType;

    RecyclerViewItem(String viewType) {
        setViewType(viewType);
    }

    void setViewType(String viewType) {
        this.viewType = viewType;
    }
    String getViewType() {
        return viewType;
    }
}
