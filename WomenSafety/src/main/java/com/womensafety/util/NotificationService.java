package com.womensafety.util;

import com.womensafety.model.Contact;
import com.womensafety.model.User;
import java.util.List;

/**
 * Placeholder for the "configured notification service" mentioned in the project report.
 * Delivery is NOT guaranteed: until a real SMS/email provider is integrated here,
 * send() returns false and the alert status stays "Pending".
 */
public class NotificationService {

    public static boolean send(User user, List<Contact> contacts, Double lat, Double lng) {
        String loc = (lat != null && lng != null)
                ? "https://maps.google.com/?q=" + lat + "," + lng : "location not available";
        String msg = "EMERGENCY! " + user.getName() + " needs help. Location: " + loc;

        for (Contact c : contacts) {
            // TODO: call SMS / email / push provider API here (e.g. Twilio, MSG91, SMTP)
            System.out.println("[SOS - not actually sent] To " + c.getContactName() + " (" + c.getMobile() + "): " + msg);
        }
        return false;   // change to true only when a real provider confirms delivery
    }
}
