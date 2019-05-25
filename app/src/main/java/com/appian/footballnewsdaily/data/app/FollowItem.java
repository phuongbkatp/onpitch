package com.appian.footballnewsdaily.data.app;

public class FollowItem {
    private int id;
    private String name;
    private String app_key;
    private boolean isCheck;

    public FollowItem(int id, String app_key, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
        this.app_key =app_key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }
}
