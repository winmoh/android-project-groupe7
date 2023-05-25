package com.example.projetandroid;

public class EventItem {

    private String id;
    private String club;
    private String title;
    private String location;
    private String date;
    private String time;
    private String is_private;
    private String eventCode;
    private String eventImage;

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String evenImage) {
        this.eventImage = evenImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_private() {
        return is_private;
    }

    public void setIs_private(String is_private) {
        this.is_private = is_private;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public EventItem() {
    }

    public EventItem(String id, String club, String title,
                     String location, String date, String time,
                     String is_private, String eventCode,String eventImage) {
        this.eventImage=eventImage;
        this.id = id;
        this.club = club;
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.is_private = is_private;
        this.eventCode = eventCode;
    }
}
