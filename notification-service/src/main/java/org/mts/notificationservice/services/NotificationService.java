package org.mts.notificationservice.services;

import org.modelmapper.ModelMapper;
import org.mts.notificationservice.dtos.Message;
import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.mts.notificationservice.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public  class NotificationService implements INotificationService {


    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final SimpMessageSendingOperations sendingOperations;

    public NotificationService(NotificationRepository notificationRepository, ModelMapper modelMapper, SimpMessageSendingOperations sendingOperations) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
        this.sendingOperations = sendingOperations;
    }

    /**
     * @param notification to save/persist
     * @return {@link Notification}
     */
    @Override
    public Notification save(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    /**
     * @param id to use for research {@link Notification}
     * @return {@link Notification}
     */
    @Override
    public Notification findById(Long id) {
        return this.notificationRepository.findById(id).orElseThrow();
    }

    /**
     * @param userId to use for research {@link Notification}
     * @return {@link Notification}
     */
    @Override
    public Notification findByUserId(String userId) {
        return this.notificationRepository.findByUserId(userId);
    }

    /**
     * Research all {@link Notification} relative to user
     *
     * @param userId
     * @return {@link List} of {@link Notification}
     */
    @Override
    public List<Notification> findAllByUserId(String userId) {
        return this.notificationRepository.findAllByUserId(userId);
    }

    /**
     * Find All {@link Notification} not seen relative to user
     *
     * @param userId
     * @return {@link List} of {@link Notification}
     */
    @Override
    public List<Notification> findAllByUserIdAndNotSeen(String userId) {
        return this.notificationRepository.findAllByUserIdAndSeen(userId, false);
    }

    /**
     * Modify persisted entity {@link Notification}
     *
     * @param notification modified {@link Notification}
     * @return modified {@link Notification}
     */
    @Override
    public Notification modify(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Notification deleteById(Long id) {
        Notification notification = this.findById(id);
        this.notificationRepository.deleteById(id);
        return notification;
    }

    /**
     * Delete all {@link Notification} by their id in {@code ids}
     *
     * @param ids {@link List} of {@code id}
     * @return {@link List} of {@link Notification} deleted
     */
    @Override
    public List<Notification> deleteAllByIds(List<Long> ids) {
        List<Notification> notifications = this.notificationRepository.findAllById(ids);
       this.notificationRepository.deleteAllById(ids);
        return notifications;
    }

    /**
     * @param userId
     * @param notification
     * @return
     */
    @Override
    public boolean notifyUser(String userId,String destination ,Notification notification) {
        this.sendingOperations.convertAndSendToUser(userId,destination,this.modelMapper.map(this.save(notification), Message.class));
        return true;
    }

    /**
     * @param notification
     * @return
     */
    @Override
    public boolean notifyAll(String destination,Notification notification) {
        Notification saved = this.notificationRepository.save(notification);
        this.sendingOperations.convertAndSend(destination,this.modelMapper.map(saved,Message.class));
        return true;
    }

    /**
     * @param id
     * @param name
     * @return
     */
    @Override
    public Notification findByIdAndUserId(Long id, String name) {
        return this.notificationRepository.findByIdAndUserId(id,name).orElseThrow();
    }

    /**
     * @param name
     * @return
     */
    @Override
    public List<Notification> findByUserIdAndSeenFalse(String name) {
        return this.notificationRepository.findAllByUserIdAndSeenFalse(name);
    }

    /**
     * @param audience
     * @param targetType
     * @return
     */
    @Override
    public List<Notification> findByAudienceAndTargetType(NotificationAudience audience, NotificationTargetType targetType) {
        return this.notificationRepository.findByAudienceEqualsAndTargetTypeEquals(audience, targetType);
    }

}
