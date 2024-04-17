package com.hs.mvc.entity.views;

public class EmpRankLevel {
    private String empNo; //emp_no
    private String empName; //emp_name
    private String empRfid; //emp_rfid
    private String title; //title
    private Integer rankNo; //rank_no
    private Integer levelNo; //level_no
    private Integer salary; //salary
    private Integer cnameCount; //cname_count
    private String cnames; //cnames

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

    public String getEmpRfid() {
        return empRfid;
    }

    public void setEmpRfid(String empRfid) {
        this.empRfid = empRfid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRankNo() {
        return rankNo;
    }

    public void setRankNo(Integer rankNo) {
        this.rankNo = rankNo;
    }

    public Integer getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Integer levelNo) {
        this.levelNo = levelNo;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getCnameCount() {
        return cnameCount;
    }

    public void setCnameCount(Integer cnameCount) {
        this.cnameCount = cnameCount;
    }

    public String getCnames() {
        return cnames;
    }

    public void setCnames(String cnames) {
        this.cnames = cnames;
    }

    @Override
    public String toString() {
        return "EmpRankLevel{" + "empNo=" + empNo + ", empName=" + empName + ", empRfid=" + empRfid + ", title=" + title + ", rankNo=" + rankNo + ", levelNo=" + levelNo + ", salary=" + salary + ", cnameCount=" + cnameCount + ", cnames=" + cnames + '}';
    }

    

}
