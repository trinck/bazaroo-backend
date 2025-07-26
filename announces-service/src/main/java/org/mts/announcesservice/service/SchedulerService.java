package org.mts.announcesservice.service;

import org.jetbrains.annotations.NotNull;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.schedulers.SearchJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;

@Service
public class SchedulerService implements ISchedulerService{


    private final Scheduler scheduler;
    @Value("${app.quartz.jobs.id.prefix}")
    private String JOB_PREFIX_ID;
    @Value("${app.quartz.triggers.id.prefix}")
    private String TRIGGER_PREFIX_ID;
    @Value("${app.quartz.groups.search}")
    private String GROUPS;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    public void scheduleSearch(@NotNull SavedSearch savedSearch,LocalTime time) throws SchedulerException {


        if(time == null){
            return;
        }

        String cron;
        switch (savedSearch.getType()){
            case DAILY -> cron = String.format("0 %d %d * * ?", time.getMinute(), time.getHour());
            case WEEKLY -> cron = String.format("0 %d %d */7 * ?", time.getMinute(), time.getHour());
            default -> {
                return;
            }
        }

        JobDetail jobDetail = JobBuilder.newJob(SearchJob.class)
                .withIdentity(this.JOB_PREFIX_ID + savedSearch.getId(), this.GROUPS)
                .usingJobData("userId", savedSearch.getUserId())
                .usingJobData("id", savedSearch.getId())
                .usingJobData("criteria",savedSearch.getCriteria())
                .usingJobData("type",savedSearch.getType().name())
                .storeDurably(true)
                .build();



        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(this.TRIGGER_PREFIX_ID + savedSearch.getId(), this.GROUPS)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();



        this.scheduler.scheduleJob(jobDetail, cronTrigger);
    }


    @Override
    public void deleteNotification(Long searchId) throws SchedulerException {
        JobKey jobKey = new JobKey(this.JOB_PREFIX_ID + searchId, this.GROUPS);
        this.scheduler.deleteJob(jobKey);
    }

    /**
     * @param searchIds
     * @throws SchedulerException
     */
    @Override
    public void deleteAllNotifications(List<Long> searchIds) throws SchedulerException {

            searchIds.forEach(id -> {
                try {
                    if (this.jobExists(id)){
                        this.deleteNotification(id);
                    }
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    @Override
    public void pauseNotification(Long searchId) throws SchedulerException {
        JobKey jobKey = new JobKey(this.JOB_PREFIX_ID + searchId, this.GROUPS);
        this.scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeNotification(Long searchId) throws SchedulerException {
        JobKey jobKey = new JobKey(this.JOB_PREFIX_ID + searchId, this.GROUPS);
        this.scheduler.resumeJob(jobKey);
    }

    /**
     * @param savedSearchId
     * @return
     * @throws SchedulerException
     */
    @Override
    public boolean jobExists(@NotNull Long savedSearchId) throws SchedulerException {
        return this.scheduler.checkExists(JobKey.jobKey(this.JOB_PREFIX_ID+ savedSearchId,this.GROUPS));
    }

    /**
     * @param savedSearch
     * @return
     * @throws SchedulerException
     */
    @Override
    public boolean isJobScheduling(@NotNull SavedSearch savedSearch) throws SchedulerException {
        return true;
    }

    @Override
    public JobDetail getJobDetail(long id) throws SchedulerException {
       return this.scheduler.getJobDetail(JobKey.jobKey(this.JOB_PREFIX_ID+id,this.GROUPS));
    }
}
