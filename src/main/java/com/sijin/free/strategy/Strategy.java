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

    private UseFileType useFileType;    //使用配置文件

    private Double weight;  //最低价加权

    private int compartorType; //比较排序

    private boolean isGenerhtml; //是否生成html

    private boolean isSendMail; //是否发报警

    private String htmlFileName;   //生成文件名

    private Report report;


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

    public boolean isGenerhtml() {
        return isGenerhtml;
    }

    public void setIsGenerhtml(boolean isGenerhtml) {
        this.isGenerhtml = isGenerhtml;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public void setHtmlFileName(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }

    public boolean isSendMail() {
        return isSendMail;
    }

    public void setIsSendMail(boolean isSendMail) {
        this.isSendMail = isSendMail;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public int getCompartorType() {
        return compartorType;
    }

    public void setCompartorType(int compartorType) {
        this.compartorType = compartorType;
    }
}
