package com.example.kartikbhardwaj.bottom_navigation.notification;

public class NotificationModel {
    private String notifName;
    private String notifTime;


    public NotificationModel(String notifName, String notifTime) {
        this.notifName = notifName;
        this.notifTime = notifTime;
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



