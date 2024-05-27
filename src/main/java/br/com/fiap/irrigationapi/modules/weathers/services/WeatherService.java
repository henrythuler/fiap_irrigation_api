package br.com.fiap.irrigationapi.modules.weathers.services;

import br.com.fiap.irrigationapi.exceptions.NotFoundException;
import br.com.fiap.irrigationapi.modules.sensors.repositories.SensorRepository;
import br.com.fiap.irrigationapi.modules.weathers.dtos.CreateWeather;
import br.com.fiap.irrigationapi.modules.weathers.dtos.OutputWeather;
import br.com.fiap.irrigationapi.modules.weathers.dtos.UpdateWeather;
import br.com.fiap.irrigationapi.modules.weathers.models.Weather;
import br.com.fiap.irrigationapi.modules.weathers.repositories.WeatherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public OutputWeather create(CreateWeather createWeather) {
        Weather weather = new Weather();
        BeanUtils.copyProperties(createWeather, weather);
        weather.setSensor(sensorRepository.getReferenceById(createWeather.sensorId()));
        weather = weatherRepository.save(weather);
        return new OutputWeather(weather);
    }

    public OutputWeather update(UpdateWeather updateWeather) {
        try {
            Weather foundWeather = weatherRepository.getReferenceById(updateWeather.id());
            BeanUtils.copyProperties(updateWeather, foundWeather);
            foundWeather.setSensor(sensorRepository.getReferenceById(updateWeather.sensorId()));
            return new OutputWeather(weatherRepository.save(foundWeather));
        }catch(EntityNotFoundException e){
            throw new NotFoundException("Weather", updateWeather.id());
        }
    }

    public Optional<Weather> findById(Long id) {
        return weatherRepository.findById(id);
    }

    public Page<Weather> findAll(Pageable pageable) {
        return weatherRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        weatherRepository.deleteById(id);
    }
}
