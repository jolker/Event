/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.model;

/**
 *
 * @author anhlnt
 */
public class Event {
    private String eventName;
    private String startDate;
    private String endDate;
    private String payMoney;
    private String giftsPerPerson;
    private String scoreGift;
    private String scoreMoney;
    private String status;
    private String id;
    private String giftsCardPercent;
    private String giftsNcoinPercent;
    private String giftsSpecialPercent;

    public Event() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getGiftsPerPerson() {
        return giftsPerPerson;
    }

    public void setGiftsPerPerson(String giftsPerPerson) {
        this.giftsPerPerson = giftsPerPerson;
    }

    public String getScoreGift() {
        return scoreGift;
    }

    public void setScoreGift(String scoreGift) {
        this.scoreGift = scoreGift;
    }

    public String getScoreMoney() {
        return scoreMoney;
    }

    public void setScoreMoney(String scoreMoney) {
        this.scoreMoney = scoreMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGiftsCardPercent() {
        return giftsCardPercent;
    }

    public void setGiftsCardPercent(String giftsCardPercent) {
        this.giftsCardPercent = giftsCardPercent;
    }

    public String getGiftsNcoinPercent() {
        return giftsNcoinPercent;
    }

    public void setGiftsNcoinPercent(String giftsNcoinPercent) {
        this.giftsNcoinPercent = giftsNcoinPercent;
    }

    public String getGiftsSpecialPercent() {
        return giftsSpecialPercent;
    }

    public void setGiftsSpecialPercent(String giftsSpecialPercent) {
        this.giftsSpecialPercent = giftsSpecialPercent;
    }
    
}
