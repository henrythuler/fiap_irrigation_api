package br.com.fiap.irrigationapi.modules.notifications.services;

import br.com.fiap.irrigationapi.exceptions.NotFoundException;
import br.com.fiap.irrigationapi.modules.notifications.dtos.CreateNotification;
import br.com.fiap.irrigationapi.modules.notifications.dtos.OutputNotification;
import br.com.fiap.irrigationapi.modules.notifications.dtos.UpdateNotification;
import br.com.fiap.irrigationapi.modules.notifications.models.Notification;
import br.com.fiap.irrigationapi.modules.notifications.repositories.NotificationRepository;
import br.com.fiap.irrigationapi.modules.sensors.repositories.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public OutputNotification create(CreateNotification createNotification) {
        var notification = new Notification();
        BeanUtils.copyProperties(createNotification, notification);
        notification.setSensor(sensorRepository.getReferenceById(createNotification.sensorId()));
        notification = notificationRepository.save(notification);
        return new OutputNotification(notification);
    }

    public OutputNotification update(UpdateNotification updateNotification) {
        try {
            var foundNotification = notificationRepository.getReferenceById(updateNotification.id());
            BeanUtils.copyProperties(updateNotification, foundNotification);
            foundNotification.setSensor(sensorRepository.getReferenceById(updateNotification.sensorId()));
            return new OutputNotification(notificationRepository.save(foundNotification));
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Notification", updateNotification.id());
        }
    }

    public Notification findById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        } else {
            throw new NotFoundException("Notification", id);
        }
    }

    public Page<OutputNotification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(OutputNotification::new);
    }


    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }
}