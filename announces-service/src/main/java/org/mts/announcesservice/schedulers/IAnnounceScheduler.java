package org.mts.announcesservice.schedulers;


/**
 * Specify all methods scheduled to check announces (exp)
 */
public interface IAnnounceScheduler {

    public boolean checkAnnouncesExpired();
    public boolean scheduleSearchWeekly();
}
