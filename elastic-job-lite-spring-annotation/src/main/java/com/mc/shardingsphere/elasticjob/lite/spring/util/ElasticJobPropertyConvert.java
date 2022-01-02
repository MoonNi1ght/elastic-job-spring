package com.mc.shardingsphere.elasticjob.lite.spring.util;

import com.mc.shardingsphere.elasticjob.lite.spring.context.annotation.config.ElasticJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.core.env.ConfigurableEnvironment;

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
    public static JobConfiguration convert(ElasticJobHandler elasticJobHandler, TracingConfiguration<?> tracingConfiguration, ConfigurableEnvironment environment) {
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
        //  handler error
        if (StringUtils.isNoneBlank(elasticJobHandler.jobErrorHandlerType())) {
            jobConfigurationBuilder.jobErrorHandlerType(elasticJobHandler.jobErrorHandlerType());
        }
        Stream.of(elasticJobHandler.jobProperties()).forEach(pros -> jobConfigurationBuilder.setProperty(pros.key(), resolve(pros.value(), environment)));
        return jobConfigurationBuilder.build();
    }

    /**
     * difference environment has more web hook address
     * <p>
     * value such like ${webhook.url}
     * </p>
     *
     * @param value       配置build
     * @param environment spring container environment
     * @return resolve
     */
    private static String resolve(String value, ConfigurableEnvironment environment) {
        if (StringUtils.isNotEmpty(value) && containPlaceHolder(value)) {
            return environment.resolvePlaceholders(value);
        }
        return value;
    }

    private static boolean containPlaceHolder(String value) {
        return value.startsWith(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX) && value.endsWith(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_SUFFIX);
    }
}
