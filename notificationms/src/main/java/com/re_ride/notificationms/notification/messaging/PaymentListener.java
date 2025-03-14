package com.re_ride.notificationms.notification.messaging;

import com.re_ride.notificationms.notification.Notification;
import com.re_ride.notificationms.notification.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentListener {
    private NotificationService notificationService;

    public PaymentListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handlePaymentRequests(PaymentEvent event){
        System.out.println("Received event: " + event);

        Notification notification = new Notification();
        notification.setCreatedAt(LocalDateTime.now());
        notification.setNotificationType(Notification.NotificationType.PAYMENT_RECEIPT);

        switch (event.getPaymentStatus()){
            case COMPLETED:
                notification.setMessage("Your payment was successful. Thank you for subscribing!");
                break;
            case FAILED:
                notification.setMessage("Your payment could not be processed. Please try again or contact support.");
                break;
            case PENDING:
                break;
            default:
                throw new IllegalArgumentException("Unknown payment status: " + event.getPaymentStatus());
        }

        notificationService.createNotification(event.getUserId(), notification);
    }
}
