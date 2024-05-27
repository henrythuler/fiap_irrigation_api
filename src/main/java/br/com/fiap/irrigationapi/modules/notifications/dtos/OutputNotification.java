package br.com.fiap.irrigationapi.modules.notifications.dtos;

import br.com.fiap.irrigationapi.modules.notifications.models.Notification;
import java.time.LocalDateTime;

public record OutputNotification(
        Long id,
        String description,
        LocalDateTime timestamp,
        Long sensorId
) {

    public OutputNotification(Notification notification) {
        this(
                notification.getId(),
                notification.getDescription(),
                notification.getTimestamp(),
                notification.getSensor().getId()
        );
    }
}