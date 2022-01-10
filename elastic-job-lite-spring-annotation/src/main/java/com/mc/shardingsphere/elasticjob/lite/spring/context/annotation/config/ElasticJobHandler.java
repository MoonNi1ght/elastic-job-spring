package com.mc.shardingsphere.elasticjob.lite.spring.context.annotation.config;

import java.lang.annotation.*;

/**
 * @author mengcheng
 * @date 2021/02/28  下午4:57
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobHandler {

    /**
     * job名称
     *
     * @return the job name
     */
    String jobName();

    /**
     * 分片总数
     *
     * @return the job  shardingTotalCount
     */
    int shardingTotalCount() default 1;

    /**
     * job 执行周期表达式
     *
     * @return the job corn
     */
    String corn() default "";


    /**
     * 时间分区
     *
     * @return 时间分区
     */
    String timeZone() default "";

    /**
     * jobBootstrapBeanName
     */
    String jobBootstrapBeanName() default "";


    /**
     * 分片参数
     *
     * @return 分片参数
     */
    String shardingItemParameters() default "";

    /**
     * 分片策略
     * @return 分片策略
     */
    String jobShardingStrategyType() default "";

    /**
     * job 参数
     *
     * @return 返回job参数
     */
    String jobParameter() default "";


    /**
     * 监控作业运行时状态
     *
     * @return default true
     */
    boolean monitorExecution() default true;

    /**
     * 失效转移
     *
     * @return 是否开启失败转移
     */
    boolean failOver() default false;

    /**
     * 是否开启错过任务重新执行
     *
     * @return 是否开启失效转移
     */
    boolean misfire() default true;

    /**
     * 最大允许的本机与注册中心的时间误差秒数
     * @return default -1
     */
    int maxTimeDiffSeconds() default -1;

    /**
     * 修复作业服务器不一致状态服务调度间隔分钟
     * @return default 10
     */
    int reconcileIntervalMinutes() default 10;

    /**
     * 任务执行报错信息
     *
     * @return jobErrorHandlerType
     */
    String jobErrorHandlerType() default "";

    /**
     * 作业描述信息
     * @return 作业描述信息
     */
    String description() default "";

    /**
     * job 属性信息 配置 key=value,key1=value1
     * #{@link }
     *
     * @return job 属性
     */
    ElasticJobProperty[] jobProperties() default {};

    /**
     * 默认job 是否可用
     *
     * @return disabled
     */
    boolean disabled() default false;
    /**
     * 本地配置是否覆盖注册中心配置
     *
     * @return overwrite
     */
    boolean overWrite() default false;

    /**
     * default is true, the same as spring name space
     *
     * @return trace
     */
    boolean trace() default true;

}
