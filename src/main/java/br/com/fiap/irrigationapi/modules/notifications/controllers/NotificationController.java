package br.com.fiap.irrigationapi.modules.notifications.controllers;

import br.com.fiap.irrigationapi.modules.notifications.dtos.CreateNotification;
import br.com.fiap.irrigationapi.modules.notifications.dtos.OutputNotification;
import br.com.fiap.irrigationapi.modules.notifications.dtos.UpdateNotification;
import br.com.fiap.irrigationapi.modules.notifications.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping
    public ResponseEntity<OutputNotification> create(@RequestBody @Valid CreateNotification createNotification) {
        OutputNotification outputNotification = service.create(createNotification);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(outputNotification.id()).toUri();
        return ResponseEntity.created(location).body(outputNotification);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OutputNotification> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new OutputNotification(service.findById(id)));
    }

    @GetMapping("/all")
    public Page<OutputNotification> getAll(Pageable pageable){
        return service.findAll(pageable);
    }


    @PutMapping
    public ResponseEntity<OutputNotification> update(@RequestBody @Valid UpdateNotification updateNotification) {
        OutputNotification outputNotification = service.update(updateNotification);
        return ResponseEntity.ok(outputNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}