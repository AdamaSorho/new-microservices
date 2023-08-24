package org.adamasorho.notificationservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.adamasorho.notificationservice.event.OrderPlacedEvent;
import org.adamasorho.notificationservice.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    @KafkaListener(topics = "notificationTopic")
    public void notificationHandler(OrderPlacedEvent orderPlacedEvent) {
        // todo: implement the logic later if get time
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
