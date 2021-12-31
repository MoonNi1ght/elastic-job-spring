package org.mc.shardingsphere.elasticjob.error.handler.feishu.entry;

import java.io.Serializable;

/**
 * <p>
 * feishu robot sign mag entry
 * </p>
 *
 * @author mengcheng
 * @date 2021/12/31  11:16 上午
 **/
public class RobotSignMsgEntry extends AbstractMagEntry implements Serializable {

    private static final long serialVersionUID = -6826555249717486185L;
    /**
     * seconds
     */
    private long timestamp;

    /**
     * sign
     */
    private String sign;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
