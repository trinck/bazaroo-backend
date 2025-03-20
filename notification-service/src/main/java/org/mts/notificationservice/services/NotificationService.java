package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
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
}
