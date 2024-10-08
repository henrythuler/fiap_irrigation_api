package br.com.fiap.irrigationapi.modules.notifications.dtos;

import br.com.fiap.irrigationapi.modules.notifications.Notification;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateNotification(
        String description,
        LocalDateTime timestamp,

        @NotNull(message = "SensorId is required!")
        Long sensorId
) {
    public CreateNotification(Notification notification) {
        this(
                notification.getDescription(),
                notification.getTimestamp(),
                notification.getSensor().getId()
        );
    }
}
