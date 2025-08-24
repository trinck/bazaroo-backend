package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Describe all methods those allow to save, delete, modify et get
 * Notification entity
 */
public interface INotificationService {

    /**
     * @param notification to save/persist
     * @return {@link Notification}
     */
    public Notification save (Notification notification);


    /**
     * @param id to use for research {@link Notification}
     * @return {@link Notification}
     */
    public Notification findById(Long id);

    /**
     * @param userId to use for research {@link Notification}
     * @return {@link Notification}
     */
    public Notification findByUserId(String userId);

    /**
     * Research all {@link Notification} relative to user
     * @param userId
     * @return {@link List} of {@link Notification}
     */
    public List<Notification> findAllByUserId(String userId);

    /**
     * Find All {@link Notification} not seen relative to user
     * @param userId
     * @return {@link List} of {@link Notification}
     */
    public List<Notification> findAllByUserIdAndNotSeen(String userId);

    /**
     * Modify persisted entity {@link Notification}
     * @param notification modified {@link Notification}
     * @return modified {@link Notification}
     */
    public Notification modify(Notification notification);
    public Notification deleteById(Long id);

    /**
     * Delete all {@link Notification} by their id in {@code ids}
     * @param ids {@link List} of {@code id}
     * @return {@link List} of {@link Notification} deleted
     */
    public List<Notification> deleteAllByIds(List<Long> ids);
    public boolean notifyUser(String userId,String destination ,Notification notification);
    public boolean notifyAll(String destination,Notification notification);

    public Notification findByIdAndUserId(Long id, String name);

    public List<Notification> findByUserIdAndSeenFalse(String name);
    public List<Notification> findByAudienceAndTargetType(NotificationAudience audience, NotificationTargetType targetType);
}
