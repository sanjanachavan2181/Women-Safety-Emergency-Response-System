package com.womensafety.model;

import java.sql.Timestamp;

public class Alert {
    private int alertId, userId;
    private Timestamp alertTime;
    private Double latitude, longitude;
    private String status;

    public int getAlertId() { return alertId; }
    public void setAlertId(int alertId) { this.alertId = alertId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Timestamp getAlertTime() { return alertTime; }
    public void setAlertTime(Timestamp alertTime) { this.alertTime = alertTime; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
