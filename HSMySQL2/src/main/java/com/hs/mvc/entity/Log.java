package com.hs.mvc.entity;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable  {
   private Integer id;
   private String empNo;
   private String logPath;
   private String fromAddress;
   private Date logCt;
   private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Date getLogCt() {
        return logCt;
    }

    public void setLogCt(Date logCt) {
        this.logCt = logCt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Log{" + "id=" + id + ", empNo=" + empNo + ", logPath=" + logPath + ", fromAddress=" + fromAddress + ", logCt=" + logCt + ", type=" + type + '}';
    }
   
}
