package br.com.fiap.irrigationapi.modules.schedules.controllers;

import br.com.fiap.irrigationapi.modules.schedules.dtos.CreateSchedule;
import br.com.fiap.irrigationapi.modules.schedules.dtos.UpdateSchedule;
import br.com.fiap.irrigationapi.modules.schedules.models.Schedule;
import br.com.fiap.irrigationapi.modules.schedules.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody @Valid CreateSchedule schedule) {
        Schedule savedSchedule = service.save(schedule);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedSchedule.getId()).toUri();
        return ResponseEntity.created(location).body(savedSchedule);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable Long id) {
        Optional<Schedule> schedule = service.findById(id);
        return schedule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Schedule>> getAll(Pageable pageable) {
        Page<Schedule> schedules = service.findAll(pageable);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping
    public ResponseEntity<Schedule> update(@RequestBody @Valid UpdateSchedule schedule) {
            return ResponseEntity.ok(service.update(schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
