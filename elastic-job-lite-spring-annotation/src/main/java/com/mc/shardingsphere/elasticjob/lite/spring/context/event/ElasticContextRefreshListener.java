package com.mc.shardingsphere.elasticjob.lite.spring.context.event;

import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * listen contextRefreshEvent
 *
 * @author mengcheng
 * @date 2021/12/26  10:02 下午
 * @see org.springframework.context.event.ContextRefreshedEvent
 **/
public class ElasticContextRefreshListener implements ApplicationListener<ContextStartedEvent> {


    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        startScheduled(applicationContext);
    }

    private void startScheduled(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(ScheduleJobBootstrap.class).values().forEach(ScheduleJobBootstrap::schedule);
    }

}
