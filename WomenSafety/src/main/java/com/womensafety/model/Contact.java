package com.womensafety.model;

public class Contact {
    private int contactId, userId;
    private String contactName, mobile, relation;

    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
}
