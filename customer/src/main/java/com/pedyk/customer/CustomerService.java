package com.pedyk.customer;

import com.pedyk.amqp.RabbitMQMessageProducer;
import com.pedyk.clients.fraud.FraudCheckResponse;
import com.pedyk.clients.fraud.FraudClient;
import com.pedyk.clients.notification.NotificationClient;
import com.pedyk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        // todo: check if email valid or not taken,
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        NotificationRequest notificationRequest = new NotificationRequest(
                String.format("Hi %s, welcome to microservices application...", customer.getFirstName()),
                customer.getId(),
                customer.getEmail()
        );
        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");
    }
}
