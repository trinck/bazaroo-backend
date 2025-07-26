package org.mts.announcesservice.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.mts.announcesservice.schedulers.SearchJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    @MockBean
    private Scheduler scheduler;

    @Test
    void testScheduleSearch_Daily() throws SchedulerException {
        SavedSearch savedSearch = SavedSearch.builder()
                .id(1L)
                .type(SavedSearchScheduleType.DAILY)
                .userId("user123")
                .criteria("criteria")
                .active(true).build();

        LocalTime time = LocalTime.of(10, 0);

        schedulerService.scheduleSearch(savedSearch, time);

        ArgumentCaptor<JobDetail> jobCaptor = ArgumentCaptor.forClass(JobDetail.class);
        ArgumentCaptor<CronTrigger> triggerCaptor = ArgumentCaptor.forClass(CronTrigger.class);

        verify(scheduler).scheduleJob(jobCaptor.capture(), triggerCaptor.capture());

        JobDetail jobDetail = jobCaptor.getValue();
        CronTrigger cronTrigger = triggerCaptor.getValue();

        assertThat(jobDetail.getKey().getName()).isEqualTo("search_1");
        assertThat(jobDetail.getKey().getGroup()).isEqualTo("notifications");
        assertThat(jobDetail.getJobClass()).isEqualTo(SearchJob.class);
        assertThat(jobDetail.getJobDataMap().getString("userId")).isEqualTo("user123");

        assertThat(cronTrigger.getCronExpression()).isEqualTo("0 0 10 * * ?");
    }

    @Test
    void testScheduleSearch_Weekly() throws SchedulerException {
        SavedSearch savedSearch = SavedSearch.builder()
                .id(2L)
                .type(SavedSearchScheduleType.WEEKLY)
                .userId("user124")
                .criteria("criteria")
                .active(true).build();

        LocalTime time = LocalTime.of(15, 30);

        schedulerService.scheduleSearch(savedSearch, time);

        ArgumentCaptor<JobDetail> jobCaptor = ArgumentCaptor.forClass(JobDetail.class);
        ArgumentCaptor<CronTrigger> triggerCaptor = ArgumentCaptor.forClass(CronTrigger.class);

        verify(scheduler).scheduleJob(jobCaptor.capture(), triggerCaptor.capture());

        JobDetail jobDetail = jobCaptor.getValue();
        CronTrigger cronTrigger = triggerCaptor.getValue();

        assertThat(jobDetail.getKey().getName()).isEqualTo("search_2");
        assertThat(jobDetail.getKey().getGroup()).isEqualTo("notifications");
        assertThat(jobDetail.getJobClass()).isEqualTo(SearchJob.class);
        assertThat(jobDetail.getJobDataMap().getString("userId")).isEqualTo("user124");

        assertThat(cronTrigger.getCronExpression()).isEqualTo("0 30 15 */7 * ?");
    }

    @Test
    void testScheduleSearch_InvalidType_ShouldDoNothing() throws SchedulerException {
        SavedSearch savedSearch = SavedSearch.builder()
                .id(3L)
                .type(SavedSearchScheduleType.IMMEDIATE)
                .userId("user125")
                .criteria("criteria")
                .active(true).build();

        LocalTime time = LocalTime.of(12, 0);

        schedulerService.scheduleSearch(savedSearch, time);
        verify(scheduler, never()).scheduleJob(any(JobDetail.class), any(CronTrigger.class));
    }

    @Test
    void testScheduleSearch_SchedulerException_ShouldThrow() throws SchedulerException {
        SavedSearch savedSearch = SavedSearch.builder()
                .id(4L)
                .type(SavedSearchScheduleType.DAILY)
                .userId("user126")
                .criteria("criteria")
                .active(true).build();

        LocalTime time = LocalTime.of(9, 45);

        doThrow(new SchedulerException("Test Scheduler Exception")).when(scheduler)
                .scheduleJob(any(JobDetail.class), any(CronTrigger.class));

        assertThrows(SchedulerException.class, () -> schedulerService.scheduleSearch(savedSearch, time));

        verify(scheduler).scheduleJob(any(JobDetail.class), any(CronTrigger.class));
    }
}