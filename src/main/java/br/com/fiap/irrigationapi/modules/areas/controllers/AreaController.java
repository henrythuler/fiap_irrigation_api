package br.com.fiap.irrigationapi.modules.areas.controllers;

import br.com.fiap.irrigationapi.modules.areas.dtos.CreateArea;
import br.com.fiap.irrigationapi.modules.areas.dtos.OutputArea;
import br.com.fiap.irrigationapi.modules.areas.dtos.UpdateArea;
import br.com.fiap.irrigationapi.modules.areas.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    private AreaService service;

    @PostMapping
    public ResponseEntity<OutputArea> create(@RequestBody CreateArea area) {
        OutputArea outputArea = service.create(area);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(outputArea.id()).toUri();
        return ResponseEntity.created(location).body(outputArea);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OutputArea> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<OutputArea>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<OutputArea> update(@RequestBody UpdateArea area) {
        return ResponseEntity.ok(service.update(area));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}