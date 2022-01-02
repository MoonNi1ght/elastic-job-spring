package com.mc.shardingsphere.elasticjob.error.handler.feishu.entry;

import java.io.Serializable;

/**
 * <p>
 * abstract feishu mag
 * </p>
 *
 * @author mengcheng
 * @date 2021/12/31  11:18 上午
 **/
public class AbstractMagEntry implements Serializable {

    private static final long serialVersionUID = 6650904097509015981L;

    private String msg_type;

    private MsgContentEntry content;


    public static class MsgContentEntry {

        private String text;

        public MsgContentEntry(String text) {
            this.text = text;
        }
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public MsgContentEntry getContent() {
        return content;
    }

    public void setContent(MsgContentEntry content) {
        this.content = content;
    }
}
