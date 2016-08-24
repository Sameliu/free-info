package com.sijin.free.strategy;

/**
 * Created by sijinzhang on 16/7/14.
 */


import com.sijin.free.constant.UseFileType;

/**
 * 策略容器
 */
public class Strategy {

    private Double priceStart;  //价格区间开始

    private Double priceEnd;  //价格区间结束

    private UseFileType useFileType;

    private Double weight;  //最低价加权

    private int compartorType;// 排序规则 1.按照买入幅度

    private String logfile; //统计结果的文件名字

    private boolean isGenerhtml; //是否生成图片

    private boolean isSendMail; //是否发报警

    private String fName;   //生成文件名


    public Double getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Double priceStart) {
        this.priceStart = priceStart;
    }

    public Double getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Double priceEnd) {
        this.priceEnd = priceEnd;
    }

    public UseFileType getUseFileType() {
        return useFileType;
    }

    public void setUseFileType(UseFileType useFileType) {
        this.useFileType = useFileType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getCompartorType() {
        return compartorType;
    }

    public void setCompartorType(int compartorType) {
        this.compartorType = compartorType;
    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public boolean isGenerhtml() {
        return isGenerhtml;
    }

    public void setIsGenerhtml(boolean isGenerhtml) {
        this.isGenerhtml = isGenerhtml;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public boolean isSendMail() {
        return isSendMail;
    }

    public void setIsSendMail(boolean isSendMail) {
        this.isSendMail = isSendMail;
    }
}
