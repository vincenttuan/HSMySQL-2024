package com.hs.mvc.entity;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable  {
    private Integer empId;
    private String empNo;
    private String empName;
    private Boolean empActive;
    private Date empCt;
    private String empRfid;
    private String empPwd; // 密碼
    private Integer agent1Id; // 第一代理人 id
    private Integer empPriority;
    private Integer deptId1; // 部門一
    private Integer deptId2; // 部門二
    
    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Boolean getEmpActive() {
        return empActive;
    }

    public void setEmpActive(Boolean empActive) {
        this.empActive = empActive;
    }

    public Date getEmpCt() {
        return empCt;
    }

    public void setEmpCt(Date empCt) {
        this.empCt = empCt;
    }

    public String getEmpRfid() {
        return empRfid;
    }

    public void setEmpRfid(String empRfid) {
        this.empRfid = empRfid;
    }

    public String getEmpPwd() {
        return empPwd;
    }

    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd;
    }

    public Integer getAgent1Id() {
        return agent1Id;
    }

    public void setAgent1Id(Integer agent1Id) {
        this.agent1Id = agent1Id;
    }

    public Integer getEmpPriority() {
        return empPriority;
    }

    public void setEmpPriority(Integer empPriority) {
        this.empPriority = empPriority;
    }

    public Integer getDeptId1() {
        return deptId1;
    }

    public void setDeptId1(Integer deptId1) {
        this.deptId1 = deptId1;
    }

    public Integer getDeptId2() {
        return deptId2;
    }

    public void setDeptId2(Integer deptId2) {
        this.deptId2 = deptId2;
    }

    @Override
    public String toString() {
        return "Employee{" + "empId=" + empId + ", empNo=" + empNo + ", empName=" + empName + ", empActive=" + empActive + ", empCt=" + empCt + ", empRfid=" + empRfid + ", empPwd=" + empPwd + ", agent1Id=" + agent1Id + ", empPriority=" + empPriority + ", deptId1=" + deptId1 + ", deptId2=" + deptId2 + '}';
    }

    
    
    
}
