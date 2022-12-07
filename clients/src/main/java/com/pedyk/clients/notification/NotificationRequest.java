package com.pedyk.clients.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public record NotificationRequest(String message, Integer toCustomerId, String toCustomerEmail) {

}
