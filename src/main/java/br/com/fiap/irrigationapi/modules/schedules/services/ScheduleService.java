package br.com.fiap.irrigationapi.modules.schedules.services;

import br.com.fiap.irrigationapi.exceptions.NotFoundException;
import br.com.fiap.irrigationapi.modules.schedules.dtos.CreateSchedule;
import br.com.fiap.irrigationapi.modules.schedules.dtos.UpdateSchedule;
import br.com.fiap.irrigationapi.modules.schedules.models.Schedule;
import br.com.fiap.irrigationapi.modules.schedules.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(CreateSchedule createSchedule) {
        Schedule newSchedule = new Schedule();
        BeanUtils.copyProperties(createSchedule, newSchedule);
        newSchedule = scheduleRepository.save(newSchedule);
        return newSchedule;
    }

    public Schedule update(UpdateSchedule updateSchedule) {
        try {
            Schedule foundSchedule = scheduleRepository.getReferenceById(updateSchedule.id());
            BeanUtils.copyProperties(updateSchedule, foundSchedule);
            return scheduleRepository.save(foundSchedule);
        }catch(EntityNotFoundException e){
            throw new NotFoundException("Schedule", updateSchedule.id());
        }
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Page<Schedule> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }
}
