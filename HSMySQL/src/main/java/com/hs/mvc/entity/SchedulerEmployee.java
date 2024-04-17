
package com.hs.mvc.entity;

import java.util.Date;

public class SchedulerEmployee {
    private Integer id;
    private Integer iid;
    private Integer gid;
    private Date sdate;
    private String empNo;
    
    //ManyToOne
    private Employee employee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "SchedulerEmployee{" + "id=" + id + ", iid=" + iid + ", gid=" + gid + ", sdate=" + sdate + ", empNo=" + empNo + ", employee=" + employee + '}';
    }

    
    
}
