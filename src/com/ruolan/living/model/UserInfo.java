package com.ruolan.living.model;

public class UserInfo {

    private int userId;
    private int userLevel;
    private int sendNums;
    private int getNums;
    private int exp;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getSendNums() {
        return sendNums;
    }

    public void setSendNums(int sendNums) {
        this.sendNums = sendNums;
    }

    public int getGetNums() {
        return getNums;
    }

    public void setGetNums(int getNums) {
        this.getNums = getNums;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
