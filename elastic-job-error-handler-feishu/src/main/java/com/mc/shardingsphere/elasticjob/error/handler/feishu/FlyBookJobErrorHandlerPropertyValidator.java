package com.mc.shardingsphere.elasticjob.error.handler.feishu;


import org.apache.shardingsphere.elasticjob.error.handler.JobErrorHandlerPropertiesValidator;
import org.apache.shardingsphere.elasticjob.infra.validator.JobPropertiesValidateRule;

import java.util.Properties;

/**
 * feishu property  check
 *
 * @author mengcheng
 * @date 2021/12/30  5:25 下午
 **/
public class FlyBookJobErrorHandlerPropertyValidator implements JobErrorHandlerPropertiesValidator {
    @Override
    public void validate(Properties props) {
        JobPropertiesValidateRule.validateIsRequired(props, FlyBookPropertyConstants.WEB_HOOK);
        JobPropertiesValidateRule.validateIsRequired(props, FlyBookPropertyConstants.SECRET);
    }

    @Override
    public String getType() {
        return FlyBookPropertyConstants.HANDLER_ERROR_TYPE;
    }
}
