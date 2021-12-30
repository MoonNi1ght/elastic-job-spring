package com.mc.elasticjob.lite.spring.context.annotation.config;


import com.mc.elasticjob.lite.spring.context.annotation.ElasticJobConstant;
import com.mc.elasticjob.lite.spring.util.ElasticJobPropertyConvert;
import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author mengcheng
 * @date 2021/02/28  下午4:57
 **/
public class ElasticJobAnnotationBeanProcess implements BeanPostProcessor, BeanFactoryAware, SmartLifecycle {

    private ConfigurableListableBeanFactory configurableBeanFactory;

    private List<AnnotationMeta> earlyAnnotationBeanList;

    private final Object mutex = new Object();

    private boolean isRunning;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final AnnotationMeta annotationMeta = getAnnotationByBean(bean);
        if (Objects.nonNull(annotationMeta)) {
            // 缓存job配置信息
            registerJob(annotationMeta);
        }
        return bean;
    }

    /**
     * 注册配置信息
     *
     * @param annotationMeta 注解的元数据
     */
    private void registerJob(AnnotationMeta annotationMeta) {
        if (Objects.isNull(annotationMeta)) {
            return;
        }
        synchronized (mutex) {
            earlyAnnotationBeanList.add(annotationMeta);
        }

    }

    private AnnotationMeta getAnnotationByBean(Object bean) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        if (checkClassInfo(targetClass, bean)) {
            ElasticJobHandler elasticJobHandler = AnnotationUtils.findAnnotation(targetClass, ElasticJobHandler.class);
            // only register scheduled job
            if (Objects.nonNull(elasticJobHandler) && StringUtils.hasText(elasticJobHandler.corn())) {
                return new AnnotationMeta(elasticJobHandler, (ElasticJob) bean);
            }
        }
        return null;
    }


    /**
     * 判断该类是否有 #{@link ElasticJobHandler} 注解
     *
     * @param targetClass 扫描到的bean 所属的 class
     * @param bean        扫描到的bean
     * @return 该类是否 ElasticJobHandler 注解,并且是否为 `ElasticJob` 的子类
     */
    private boolean checkClassInfo(Class<?> targetClass, Object bean) {
        return Objects.nonNull(targetClass)
                && bean instanceof ElasticJob
                && Objects.nonNull(AnnotationUtils.findAnnotation(targetClass, ElasticJobHandler.class));

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, beanFactory, "bea");
        this.configurableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        earlyAnnotationBeanList = new ArrayList<>(16);
    }


    private boolean checkBeanDefinition(AnnotationMeta annotationMeta) {
        String jobBeanName = getJobBeanName(annotationMeta);
        return !configurableBeanFactory.containsBeanDefinition(jobBeanName);
    }

    private String getJobBeanName(AnnotationMeta annotationMeta) {
        String jobBootstrapBeanName = annotationMeta.elasticJobHandler.jobBootstrapBeanName();
        return StringUtils.hasText(jobBootstrapBeanName) ? jobBootstrapBeanName
                : annotationMeta.elasticJobHandler.jobName() + ElasticJobConstant.ELASTIC_JOB_BEAN_SUFFIX;
    }

    @Override
    public void start() {
        isRunning = true;
        if (CollectionUtils.isEmpty(earlyAnnotationBeanList)) {
            return;
        }
        synchronized (mutex) {
            CoordinatorRegistryCenter registryCenter = configurableBeanFactory.getBean(CoordinatorRegistryCenter.class);
            // trace config
            TracingConfiguration<?> tracingConfiguration = getOneTraceConfig();
            earlyAnnotationBeanList.stream().filter(this::checkBeanDefinition).forEach(annotationMeta -> {
                String jobBeanName = getJobBeanName(annotationMeta);
                configurableBeanFactory.registerSingleton(jobBeanName,
                        new ScheduleJobBootstrap(registryCenter, annotationMeta.elasticJob, ElasticJobPropertyConvert.convert(annotationMeta.elasticJobHandler, tracingConfiguration)));
            });
        }
    }

    private TracingConfiguration<?> getOneTraceConfig() {
        Map<String, TracingConfiguration> tracingConfigurationMap = configurableBeanFactory.getBeansOfType(TracingConfiguration.class);
        if (tracingConfigurationMap.isEmpty()) {
            return null;
        }
        return tracingConfigurationMap.values().iterator().next();
    }

    @Override
    public void stop() {
        isRunning = false;
        if (CollectionUtils.isEmpty(earlyAnnotationBeanList)) {
            return;
        }
        synchronized (mutex) {
            configurableBeanFactory.getBeansOfType(ScheduleJobBootstrap.class).values().forEach(ScheduleJobBootstrap::shutdown);
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 保存类上注解信息,
     */
    private static class AnnotationMeta {

        final ElasticJobHandler elasticJobHandler;

        final ElasticJob elasticJob;

        String beanName;

        public AnnotationMeta(ElasticJobHandler elasticJobHandler, ElasticJob elasticJob) {
            check(elasticJobHandler, elasticJob);
            this.elasticJobHandler = elasticJobHandler;
            this.elasticJob = elasticJob;
        }

        private void check(ElasticJobHandler elasticJobBinding, ElasticJob elasticJob) {
            Assert.notNull(elasticJobBinding, "ElasticJobHandler can not null");
            Assert.notNull(elasticJob, "ElasticJob object can not null");
        }

        public AnnotationMeta(ElasticJobHandler elasticJobHandler, ElasticJob elasticJob, String beanName) {
            check(elasticJobHandler, elasticJob);
            this.elasticJobHandler = elasticJobHandler;
            this.elasticJob = elasticJob;
            this.beanName = beanName;
        }
    }
}
