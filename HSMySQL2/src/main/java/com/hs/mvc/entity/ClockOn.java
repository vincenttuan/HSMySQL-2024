package com.hs.mvc.entity;

import java.io.Serializable;
import java.util.Date;

public class ClockOn implements Serializable  {
    private Integer clockId;
    private String empNo;
    private Integer statusId;
    private Date clockOn;
    private String image;
    
    // 其他關聯欄位資料
    private String empName; // 關聯 Employee 查找 emp_name
    private String statusName; // 關聯 Status 查找 status_name
    
    public Integer getClockId() {
        return clockId;
    }

    public void setClockId(Integer clockId) {
        this.clockId = clockId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getClockOn() {
        return clockOn;
    }

    public void setClockOn(Date clockOn) {
        this.clockOn = clockOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
    
}
