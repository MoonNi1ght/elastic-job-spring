package com.mc.elasticjob.lite.spring.context.annotation;

/**
 * @author mengcheng
 * @date 2021/02/28  下午11:03
 **/
public interface ElasticJobConstant {

    /**
     * 处理 elastic-job 注解 Bean Name
     */
    String ELASTIC_JOB_ANNOTATION_BEAN_PROCESS_NAME = "com.mc.elastic.job.spring.context.annotation.config.ElasticJobAnnotationBeanProcess";

    /**
     * 注册所有job的Bean Name
     */
    String ELASTIC_JOB_START_BEAN_NAME = "com.mc.elastic.job.spring.context.event.ElasticContextRefreshListener";

    /**
     * elastic-job spring boot start bean
     */
    String ELASTIC_JOB_SPRING_BOOT_START_BEAN_NAME = "org.apache.shardingsphere.elasticjob.lite.spring.boot.job.ScheduleJobBootstrapStartupRunner";


    /**
     * job bean suffix
     */
    String ELASTIC_JOB_BEAN_SUFFIX = "ScheduleJobBootstrap";
}
