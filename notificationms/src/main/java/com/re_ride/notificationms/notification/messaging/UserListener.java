package com.re_ride.notificationms.notification.messaging;

import com.re_ride.notificationms.notification.Notification;
import com.re_ride.notificationms.notification.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserListener {
    private NotificationService notificationService;

    public UserListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserRequests(UserEvent event){
        System.out.println("Received event: " + event);

        Notification notification = new Notification();
        notification.setCreatedAt(LocalDateTime.now());
        notification.setNotificationType(Notification.NotificationType.WELCOME);

        switch (event.getUserType()){
            case RIDER:
                notification.setMessage("Welcome to Re-Ride! Thanks for riding with us.");
                break;
            case DRIVER:
                notification.setMessage("Welcome to Re-Ride! Thanks for driving with us.");
                break;
            default:
                throw new IllegalArgumentException("Unknown user type: " + event.getUserType());
        }

        notificationService.createNotification(event.getUserId(), notification);
    }
}
