package com.pedyk.notification;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(
            name = "notification_id_sequence",
            sequenceName = "notification_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_sequence")
    private Integer id;
    private String message;
    private Integer toCustomerId;
    private String toCustomerEmail;
    private String sender;
    private LocalDateTime sentAt;

}
