package org.mts.announcesservice.configs;

import org.jetbrains.annotations.NotNull;
import org.mts.announcesservice.repositories.SavedSearchRepository;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IPreferenceService;
import org.mts.announcesservice.service.ISchedulerService;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {


    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) {
        super.setApplicationContext(context);
        this.beanFactory = context.getAutowireCapableBeanFactory();
    }

    @NotNull
    @Override
    protected Object createJobInstance(@NotNull TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        this.beanFactory.autowireBean(job);
        return job;
    }

}
