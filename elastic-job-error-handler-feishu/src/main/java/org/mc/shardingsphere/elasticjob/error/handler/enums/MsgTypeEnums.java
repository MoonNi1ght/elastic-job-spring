package org.mc.shardingsphere.elasticjob.error.handler.enums;

/**
 * <p>
 *     feishu msg type
 * </p>
 *
 * @author mengcheng
 * @date 2021/12/31  11:21 上午
 **/
public enum MsgTypeEnums {


    /**
     * text msg
     */
    TEXT("text"),

    ;

    MsgTypeEnums(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
