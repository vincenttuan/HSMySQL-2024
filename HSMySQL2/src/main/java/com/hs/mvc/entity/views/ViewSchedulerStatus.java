package com.hs.mvc.entity.views;

import java.util.Date;

public class ViewSchedulerStatus {
    private String empNo;
    private Date sDate;
    private Integer statusId;
    private String statusName;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "ViewSchedulerStatus{" + "empNo=" + empNo + ", sDate=" + sDate + ", statusId=" + statusId + ", statusName=" + statusName + '}';
    }

   
}
