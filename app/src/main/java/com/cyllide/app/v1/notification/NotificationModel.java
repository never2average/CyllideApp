package com.cyllide.app.v1.notification;

public class NotificationModel {
    private String notifName;
    private String notifTime;
    private String notifID;


    public String getNotifID() {
        return notifID;
    }

    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    public NotificationModel(String notifName, String notifTime, String notifID) {
        this.notifName = notifName;
        this.notifTime = notifTime;
        this.notifID = notifID;
    }

    public String getNotifName() {
        return notifName;
    }

    public String getNotifTime() {
        return notifTime;
    }

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public void setNotifTime(String notifTime) {
        this.notifTime = notifTime;
    }
}



