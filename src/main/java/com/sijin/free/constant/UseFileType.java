package com.sijin.free.constant;

/**
 * Created by sijinzhang on 16/8/18.
 */
public enum UseFileType {
    /**
     * 使用配置
     */
    CONFIGFILE(1),
    /**
     * 使用白名单
     */
    WHITELIST(2),

    /**
     * 使用概念
     */
    GAINIAN(3),

    /**
     * 使用监控
     */
    MONITOR(4),

    /**
     * 默认使用全部
     */
    DEFAULT(0);

    private int type;

    UseFileType(int type) {
        this.type =type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static int getTypeValue(int type) {
        for (UseFileType c : UseFileType.values()) {
            if (c.getType() == type) {
                return c.getType();
            }
        }
        return UseFileType.getTypeValue(0);
    }
}
