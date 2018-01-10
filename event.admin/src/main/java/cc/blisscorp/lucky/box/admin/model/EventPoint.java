/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.model;

import java.util.Map;

/**
 *
 * @author tiennv
 */
public class EventPoint {
     private String eventName;
    private String startDate;
    private String endDate;
    private String status;
    private String id;
    private Map<String, String> reward;
    private boolean giftsLimit;
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

    public Map<String, String> getReward() {
        return reward;
    }

    public void setReward(Map<String, String> reward) {
        this.reward = reward;
    }

    public boolean isGiftsLimit() {
        return giftsLimit;
    }

    public void setGiftsLimit(boolean giftsLimit) {
        this.giftsLimit = giftsLimit;
    }

 
  

  
    
}
