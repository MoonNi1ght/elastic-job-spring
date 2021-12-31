package org.mc.shardingsphere.elasticjob.error.handler.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * http 创建类
 *
 * @author mengcheng
 * @date 2021/12/30  5:44 下午
 **/
public class AbstractHttpCreate {


    private final CloseableHttpClient httpclient = HttpClients.createDefault();


    public HttpPost createPostMethod(HttpProperty httpProperty) {
        HttpPost request = new HttpPost(httpProperty.getUrl());
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(httpProperty.getConnectTimeoutMilliseconds())
                .setSocketTimeout(httpProperty.getReadTimeoutMilliseconds())
                .build();
        request.setConfig(requestConfig);
        return request;
    }

    public CloseableHttpClient getHttpclient() {
        return httpclient;
    }

}
