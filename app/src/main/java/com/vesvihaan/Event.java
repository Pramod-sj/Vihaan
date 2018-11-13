package com.vesvihaan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Event implements Serializable{
    String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Map<String,Registration> getEventRegisteredUsers() {
        return eventRegisteredUsers;
    }

    public void setEventRegisteredUsers(Map<String, Registration> eventRegisteredUsers) {
        this.eventRegisteredUsers = eventRegisteredUsers;
    }

    String eventName;
    String eventQuote;
    String eventDesc;
    float eventEntryPrice;
    int eventParticipantCount;
    String eventVenue;
    String eventImageUrl;
    String eventDay;
    String eventTime;
    String eventType;
    ArrayList<String> eventRules;

    Map<String,Registration> eventRegisteredUsers;

    public ArrayList<String> getEventRules() {
        return eventRules;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setEventRules(ArrayList<String> eventRules) {
        this.eventRules = eventRules;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventQuote() {
        return eventQuote;
    }

    public void setEventQuote(String eventQuote) {
        this.eventQuote = eventQuote;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public float getEventEntryPrice() {
        return eventEntryPrice;
    }

    public void setEventEntryPrice(float eventEntryPrice) {
        this.eventEntryPrice = eventEntryPrice;
    }

    public int getEventParticipantCount() {
        return eventParticipantCount;
    }

    public void setEventParticipantCount(int eventParticipantCount) {
        this.eventParticipantCount = eventParticipantCount;
    }
}
