package com.hs.mvc.entity;

import java.util.Date;

public class ValidIP {
    private Integer id;
    private String ip;
    private String device;
    private String content;
    private Integer status;
    private Date ct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    @Override
    public String toString() {
        return "ValidIP{" + "id=" + id + ", ip=" + ip + ", device=" + device + ", content=" + content + ", status=" + status + ", ct=" + ct + '}';
    }
    
    
}
