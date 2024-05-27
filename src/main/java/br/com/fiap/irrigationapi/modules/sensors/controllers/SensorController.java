package br.com.fiap.irrigationapi.modules.sensors.controllers;

import br.com.fiap.irrigationapi.modules.sensors.dtos.CreateSensor;
import br.com.fiap.irrigationapi.modules.sensors.dtos.OutputSensor;
import br.com.fiap.irrigationapi.modules.sensors.dtos.UpdateSensor;
import br.com.fiap.irrigationapi.modules.sensors.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService service;

    @PostMapping
    public ResponseEntity<OutputSensor> create(@RequestBody CreateSensor createSensor){
        OutputSensor outputSensor = service.create(createSensor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(outputSensor.id()).toUri();
        return ResponseEntity.created(location).body(outputSensor);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OutputSensor> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public Page<OutputSensor> getAll(Pageable pageable){
        return service.findAll(pageable);
    }

    @PutMapping
    public OutputSensor update(@RequestBody UpdateSensor updateSensor){
        return service.update(updateSensor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
