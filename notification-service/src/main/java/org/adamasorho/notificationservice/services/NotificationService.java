package org.adamasorho.notificationservice.services;

import org.adamasorho.notificationservice.event.OrderPlacedEvent;

public interface NotificationService {
    void notificationHandler(OrderPlacedEvent orderPlacedEvent);
}
