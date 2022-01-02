package com.mc.shardingsphere.elasticjob.lite.spring.context.annotation.config;

import java.lang.annotation.*;

/**
 * <p>
 * elastic-job handler
 * </p>>
 *
 * @author mengcheng
 * @date 2021/12/26  3:54 下午
 **/
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobProperty {

    /**
     * 属性key
     *
     * @return key
     */
    String key();


    /**
     * 属性 value
     *
     * @return value
     */
    String value();
}
