package com.mc.elasticjob.lite.spring.context.annotation.config;

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
     * job 执行周期表达式
     *
     * @return the job corn
     */
    String corn() default "";

    /**
     * 分片总数
     *
     * @return the job  shardingTotalCount
     */
    int shardingTotalCount() default 1;


    /**
     * jobBootstrapBeanName
     */
    String jobBootstrapBeanName() default "";

    /**
     * job 参数
     *
     * @return 返回job参数
     */
    String jobParameter() default "";

    /**
     * 分片参数
     *
     * @return 分片参数
     */
    String shardingItemParameters() default "";

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
     * job 属性信息 配置 key=value,key1=value1
     * #{@link }
     *
     * @return job 属性
     */
    ElasticJobProperty[] jobProperties() default {};

    /**
     * 本地配置是否覆盖注册中心配置
     *
     * @return overwrite
     */
    boolean overWrite() default false;

    /**
     * 默认job 是否可用
     *
     * @return disabled
     */
    boolean disabled() default false;

    /**
     * default is true, the same as spring name space
     * @return trace
     */
    boolean trace() default true;

}
