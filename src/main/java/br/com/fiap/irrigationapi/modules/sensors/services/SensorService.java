package br.com.fiap.irrigationapi.modules.sensors.services;

import br.com.fiap.irrigationapi.exceptions.DatabaseException;
import br.com.fiap.irrigationapi.exceptions.NotFoundException;
import br.com.fiap.irrigationapi.modules.areas.repositories.AreaRepository;
import br.com.fiap.irrigationapi.modules.sensors.dtos.CreateSensor;
import br.com.fiap.irrigationapi.modules.sensors.dtos.OutputSensor;
import br.com.fiap.irrigationapi.modules.sensors.dtos.UpdateSensor;
import br.com.fiap.irrigationapi.modules.sensors.models.Sensor;
import br.com.fiap.irrigationapi.modules.sensors.repositories.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AreaRepository areaRepository;

    public OutputSensor create(CreateSensor createSensor){
        Sensor sensor = new Sensor();
        BeanUtils.copyProperties(createSensor, sensor);
        sensor.setArea(areaRepository.getReferenceById(createSensor.areaId()));
        sensor = sensorRepository.save(sensor);
        return new OutputSensor(sensor);
    }

    public OutputSensor findById(Long id){
        return new OutputSensor(sensorRepository.findById(id).orElseThrow(() -> new NotFoundException("Sensor", id)));
    }

    public Page<OutputSensor> findAll(Pageable pageable){
        return sensorRepository.findAll(pageable).map(OutputSensor::new);
    }

    public OutputSensor update(UpdateSensor updateSensor){
        try{
            Sensor foundSensor = sensorRepository.getReferenceById(updateSensor.id());
            BeanUtils.copyProperties(updateSensor, foundSensor);
            foundSensor.setArea(areaRepository.getReferenceById(updateSensor.areaId()));
            return new OutputSensor(sensorRepository.save(foundSensor));
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Sensor", updateSensor.id());
        }
    }

    public void delete(Long id){
        try{
            if(!sensorRepository.existsById(id)) throw new NotFoundException("Sensor", id);
            sensorRepository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

}
