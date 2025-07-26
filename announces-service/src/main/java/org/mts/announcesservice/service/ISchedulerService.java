package org.mts.announcesservice.service;

import org.jetbrains.annotations.NotNull;
import org.mts.announcesservice.entities.SavedSearch;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import java.time.LocalTime;
import java.util.List;

public interface ISchedulerService {
    public void scheduleSearch(@NotNull SavedSearch savedSearch, LocalTime time)throws SchedulerException;
    public void deleteNotification(Long searchId)throws SchedulerException;
    public void deleteAllNotifications(List<Long> searchIds)throws SchedulerException;

    public void pauseNotification(Long searchId) throws SchedulerException;

    public void resumeNotification(Long searchId)throws SchedulerException;
    public boolean jobExists(@NotNull Long savedSearchId)throws SchedulerException;
    public boolean isJobScheduling(@NotNull SavedSearch savedSearch)throws SchedulerException;

   public JobDetail getJobDetail(long id) throws SchedulerException;
}
