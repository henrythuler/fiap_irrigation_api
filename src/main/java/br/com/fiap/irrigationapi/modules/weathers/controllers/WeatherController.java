package br.com.fiap.irrigationapi.modules.weathers.controllers;

import br.com.fiap.irrigationapi.modules.weathers.dtos.CreateWeather;
import br.com.fiap.irrigationapi.modules.weathers.dtos.OutputWeather;
import br.com.fiap.irrigationapi.modules.weathers.dtos.UpdateWeather;
import br.com.fiap.irrigationapi.modules.weathers.models.Weather;
import br.com.fiap.irrigationapi.modules.weathers.services.WeatherService;
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
@RequestMapping("/api/weathers")
public class WeatherController {

    @Autowired
    private WeatherService service;
    @PostMapping
    public ResponseEntity<OutputWeather> create(@RequestBody @Valid CreateWeather weather) {
        OutputWeather savedWeather = service.create(weather);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedWeather.id()).toUri();
        return ResponseEntity.created(location).body(savedWeather);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Weather> getById(@PathVariable Long id) {
        Optional<Weather> weather = service.findById(id);
        return weather.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Weather>> getAll(Pageable pageable) {
        Page<Weather> weathers = service.findAll(pageable);
        return ResponseEntity.ok(weathers);
    }

    @PutMapping
    public OutputWeather update(@RequestBody @Valid UpdateWeather weather) {
        return service.update(weather);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
