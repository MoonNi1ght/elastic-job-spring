package org.mc.shardingsphere.elasticjob.error.handler.feishu;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.shardingsphere.elasticjob.error.handler.JobErrorHandler;
import org.apache.shardingsphere.elasticjob.infra.json.GsonFactory;
import org.mc.shardingsphere.elasticjob.error.handler.enums.MsgTypeEnums;
import org.mc.shardingsphere.elasticjob.error.handler.feishu.entry.AbstractMagEntry;
import org.mc.shardingsphere.elasticjob.error.handler.feishu.entry.RobotSignMsgEntry;
import org.mc.shardingsphere.elasticjob.error.handler.util.AbstractHttpCreate;
import org.mc.shardingsphere.elasticjob.error.handler.util.HttpProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Properties;

/**
 * <b>
 * elastic-job error handler  (only support  robot text sign msg)
 * </b>
 *
 * @author mengcheng
 * @date 2021/12/30  4:59 下午
 **/
public class FlyBookJobErrorHandler extends AbstractHttpCreate implements JobErrorHandler {

    private String webhook;

    private String secret;

    private final String CONTENT_TYPE = "application/json; charset=utf-8";

    private Logger logger = LoggerFactory.getLogger(FlyBookJobErrorHandler.class);

    @Override
    public void init(Properties props) {
        webhook = props.getProperty(FlyBookPropertyConstants.WEB_HOOK);
        secret = props.getProperty(FlyBookPropertyConstants.SECRET);
    }


    @Override
    public void handleException(String jobName, Throwable cause) {
        HttpProperty httpProperty = new HttpProperty(webhook);
        HttpPost postMethod = createPostMethod(httpProperty);
        setEntry(postMethod, jobName, cause);
        try (CloseableHttpResponse response = super.getHttpclient().execute(postMethod)) {
            String resContent = EntityUtils.toString(response.getEntity());
            int status = response.getStatusLine().getStatusCode();
            if (Objects.equals(HttpURLConnection.HTTP_OK, status)) {
                logger.info("feishu notice ok. jobName:{}, job error info:{}, msg:{}", jobName, cause, resContent);
                return;
            }
            logger.error("feishu return code error，jobName:{}, job error info:{}, msg:{}", jobName, cause, resContent);
        } catch (Exception e) {
            logger.error("scheduled job error，notice error：jobName:{}, job error info:{}, notice error info : ", jobName, cause, e);
        }
    }

    private void setEntry(HttpEntityEnclosingRequestBase postMethod, String jobName, Throwable cause) {
        RobotSignMsgEntry robotSignMsgEntry = buildEntry(jobName, cause);
        String reqContent = GsonFactory.getGson().toJson(robotSignMsgEntry);
        StringEntity entity = new StringEntity(reqContent, StandardCharsets.UTF_8);
        entity.setContentType(CONTENT_TYPE);
        postMethod.setEntity(entity);
    }

    private RobotSignMsgEntry buildEntry(String jobName, Throwable cause) {
        RobotSignMsgEntry robotSignMsgEntry = new RobotSignMsgEntry();
        robotSignMsgEntry.setMsg_type(MsgTypeEnums.TEXT.getType());
        long timeStamp = ZonedDateTime.now().toEpochSecond();
        robotSignMsgEntry.setTimestamp(timeStamp);
        // sign
        robotSignMsgEntry.setSign(getSign(timeStamp, secret));
        // content
        robotSignMsgEntry.setContent(getContent(jobName, cause));
        return robotSignMsgEntry;
    }

    private AbstractMagEntry.MsgContentEntry getContent(String jobName, Throwable cause) {
        AbstractMagEntry.MsgContentEntry msgContentEntry = new AbstractMagEntry.MsgContentEntry(buildContext(jobName, cause));
        return msgContentEntry;
    }

    private String buildContext(String jobName, Throwable cause) {
        StringBuilder stringBuilder = new StringBuilder(2000);
        stringBuilder.append("jobName: ").append(jobName).append("\n").append("error info: \r");
        try (StringWriter writer = new StringWriter()) {
            cause.printStackTrace(new PrintWriter(writer, true));
            stringBuilder.append(writer);
        } catch (Exception exception) {
            logger.error("cast exception error: ", exception);
        }
        return stringBuilder.toString();
    }

    private static String getSign(long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        try {
            //使用HmacSHA256算法计算签名
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(new byte[]{});
            return new String(Base64.encodeBase64(signData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getType() {
        return FlyBookPropertyConstants.HANDLER_ERROR_TYPE;
    }
}
