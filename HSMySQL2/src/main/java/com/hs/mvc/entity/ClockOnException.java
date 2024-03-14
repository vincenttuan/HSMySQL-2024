package com.hs.mvc.entity;

import java.io.Serializable;
import java.util.Date;

public class ClockOnException implements Serializable  {
    private int id;
    private String empNo;
    private String authorEmpno;
    private Date reportDate;
    private String exceptionMemo;
    private boolean exceptionCheck;
    private Date exceptionCt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getAuthorEmpno() {
        return authorEmpno;
    }

    public void setAuthorEmpno(String authorEmpno) {
        this.authorEmpno = authorEmpno;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getExceptionMemo() {
        return exceptionMemo;
    }

    public void setExceptionMemo(String exceptionMemo) {
        this.exceptionMemo = exceptionMemo;
    }

    public boolean isExceptionCheck() {
        return exceptionCheck;
    }

    public void setExceptionCheck(boolean exceptionCheck) {
        this.exceptionCheck = exceptionCheck;
    }

    public Date getExceptionCt() {
        return exceptionCt;
    }

    public void setExceptionCt(Date exceptionCt) {
        this.exceptionCt = exceptionCt;
    }

    
}
