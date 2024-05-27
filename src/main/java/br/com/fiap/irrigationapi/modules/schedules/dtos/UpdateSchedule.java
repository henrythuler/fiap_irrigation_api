package br.com.fiap.irrigationapi.modules.schedules.dtos;

import br.com.fiap.irrigationapi.modules.schedules.models.Schedule;

import java.time.LocalDateTime;

public record UpdateSchedule(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public UpdateSchedule(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}
