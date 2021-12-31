package org.mc.shardingsphere.elasticjob.error.handler.util;

/**
 * <p>
 *     http property
 * </p>
 *
 * @author mengcheng
 * @date 2021/12/30  5:47 下午
 **/
public class HttpProperty {

  private String url;

    private int connectTimeoutMilliseconds = 2000;

    private int readTimeoutMilliseconds = 2000;


    public HttpProperty() {
    }

    public HttpProperty(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    public void setConnectTimeoutMilliseconds(int connectTimeoutMilliseconds) {
        this.connectTimeoutMilliseconds = connectTimeoutMilliseconds;
    }

    public int getReadTimeoutMilliseconds() {
        return readTimeoutMilliseconds;
    }

    public void setReadTimeoutMilliseconds(int readTimeoutMilliseconds) {
        this.readTimeoutMilliseconds = readTimeoutMilliseconds;
    }
}
