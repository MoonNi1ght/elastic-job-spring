package com.mc.shardingsphere.elasticjob.error.handler.feishu;

/**
 * <p>
 * fly book constant
 * </p>
 *
 * @author mengcheng
 * @date 2021/12/30  5:21 下午
 **/
public interface FlyBookPropertyConstants {

    String HANDLER_ERROR_TYPE = "feishu";

    String PRE_FIX = HANDLER_ERROR_TYPE + ".";

    String WEB_HOOK = PRE_FIX + "webhook";

    String SECRET = PRE_FIX + "secret";

}
