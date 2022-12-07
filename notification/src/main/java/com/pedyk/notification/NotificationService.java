package com.pedyk.notification;

import com.pedyk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {

        notificationRepository.save(
                Notification.builder()
                        .message(notificationRequest.message())
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sentAt(LocalDateTime.now())
                        .sender("pedyk")
                        .build());
    }
}
