package com.ruolan.living.model;

public class RoomInfo {

    private int roomId;
    private String userId;
    private String userAvatar;
    private String userName;
    private String liveCover;
    private String liveTitle;
    private int watcherNum;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public void setWatcherNum(int watcherNum) {
        this.watcherNum = watcherNum;
    }

    public int getWatcherNum() {
        return watcherNum;
    }
}
