package br.com.fiap.irrigationapi.modules.schedules.dtos;

import br.com.fiap.irrigationapi.modules.schedules.models.Schedule;

import java.time.LocalDateTime;

public record CreateSchedule(
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public CreateSchedule(Schedule schedule) {
        this(
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}
