package Task3.infrastructure;

import Task3.application.NotificationSender;

public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String phone, String text) {
        System.out.println("[SMS] " + text + " надіслано на номер " + phone);
    }
}
