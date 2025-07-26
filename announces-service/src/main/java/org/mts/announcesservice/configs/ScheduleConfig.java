package org.mts.announcesservice.configs;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@EnableAutoConfiguration
@Configuration
public class ScheduleConfig {



    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);
        return factory;
    }
}
