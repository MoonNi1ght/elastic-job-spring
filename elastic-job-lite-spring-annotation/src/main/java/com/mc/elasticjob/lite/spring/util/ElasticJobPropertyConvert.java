package com.mc.elasticjob.lite.spring.util;

import com.mc.elasticjob.lite.spring.context.annotation.config.ElasticJobHandler;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * convert
 *
 * @author mengcheng
 * @date 2021/12/26  9:41 下午
 * @see ElasticJobHandler
 * @see JobConfiguration
 **/
public class ElasticJobPropertyConvert {
    public static JobConfiguration convert(ElasticJobHandler elasticJobHandler, TracingConfiguration<?> tracingConfiguration) {
        JobConfiguration.Builder jobConfigurationBuilder = JobConfiguration
                .newBuilder(elasticJobHandler.jobName(), elasticJobHandler.shardingTotalCount())
                .cron(elasticJobHandler.corn())
                .jobParameter(elasticJobHandler.jobParameter())
                .shardingItemParameters(elasticJobHandler.shardingItemParameters())
                .disabled(elasticJobHandler.disabled())
                .overwrite(elasticJobHandler.overWrite())
                .failover(elasticJobHandler.failOver())
                .jobParameter(elasticJobHandler.jobParameter())
                .misfire(elasticJobHandler.misfire());
        // set trace config
        if (Objects.nonNull(tracingConfiguration) && elasticJobHandler.trace()) {
            jobConfigurationBuilder.addExtraConfigurations(tracingConfiguration);
        }
        Stream.of(elasticJobHandler.jobProperties()).forEach(pros -> jobConfigurationBuilder.setProperty(pros.key(), pros.value()));
        return jobConfigurationBuilder.build();
    }
}
