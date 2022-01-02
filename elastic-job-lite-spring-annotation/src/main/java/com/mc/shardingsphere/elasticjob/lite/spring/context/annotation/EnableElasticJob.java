package com.mc.shardingsphere.elasticjob.lite.spring.context.annotation;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * <p>
 *   自动触发 elastic-job 默认配置
 * </p>
 *
 * @author mengcheng
 * @date 2021/02/28  下午4:59
 * */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ElasticJobAnnotationImportSelector.class)
public @interface EnableElasticJob {


}
