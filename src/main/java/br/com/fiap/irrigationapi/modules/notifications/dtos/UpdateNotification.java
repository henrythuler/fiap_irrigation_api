package br.com.fiap.irrigationapi.modules.notifications.dtos;

import br.com.fiap.irrigationapi.modules.notifications.models.Notification;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateNotification(
        Long id,
        String description,
        LocalDateTime timestamp,

        @NotNull(message = "SensorId is required!")
        Long sensorId
) {
    public UpdateNotification(Notification notification) {
        this(
                notification.getId(),
                notification.getDescription(),
                notification.getTimestamp(),
                notification.getSensor().getId()
        );
    }
}
