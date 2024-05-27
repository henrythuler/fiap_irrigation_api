package br.com.fiap.irrigationapi.modules.schedules.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_schedule")
@Getter
@Setter
@EqualsAndHashCode
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCHEDULE")
    @SequenceGenerator(name = "SEQ_SCHEDULE", sequenceName = "SEQ_SCHEDULE", allocationSize = 1)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

}
