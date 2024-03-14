package com.hs.mvc.entity.views;

import java.util.Date;

public class ViewClockOnReportMark {
    private Date reportDate;
    private Integer week;
    private String groupName;
    private String empNo;
    private String empName;
    
    private String statusName_1;
    private String statusShortname_1;
    private String clockOn_1;
    private String standard_1;
    
    private String statusName_2;
    private String statusShortname_2;
    private String clockOn_2;
    private String standard_2;
    
    private String statusName_3;
    private String statusShortname_3;
    private String clockOn_3;
    private String standard_3;
    
    private String statusName_4;
    private String statusShortname_4;
    private String clockOn_4;
    private String standard_4;
    
    private Double diffHh;
    private Double restHh;
    
    private String mark1;
    private String mark2;
    private String mark3;
    private String mark4;
    private String memo;
    
    private Boolean result;
    
    // 主管回覆欄位
    private int replyScore;
    private String replyContent;
    private String replyEmpno;
    private Date replyDate;
    
    // 主管考核欄位
    private int assessmentScore;
    private String assessmentContent;
    private String assessmentEmpno;
    private Date assessmentDate;

    public int getReplyScore() {
        return replyScore;
    }

    public void setReplyScore(int replyScore) {
        this.replyScore = replyScore;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyEmpno() {
        return replyEmpno;
    }

    public void setReplyEmpno(String replyEmpno) {
        this.replyEmpno = replyEmpno;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public int getAssessmentScore() {
        return assessmentScore;
    }

    public void setAssessmentScore(int assessmentScore) {
        this.assessmentScore = assessmentScore;
    }

    public String getAssessmentContent() {
        return assessmentContent;
    }

    public void setAssessmentContent(String assessmentContent) {
        this.assessmentContent = assessmentContent;
    }

    public String getAssessmentEmpno() {
        return assessmentEmpno;
    }

    public void setAssessmentEmpno(String assessmentEmpno) {
        this.assessmentEmpno = assessmentEmpno;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
    
    
    
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getClockOn_1() {
        return clockOn_1;
    }

    public void setClockOn_1(String clockOn_1) {
        this.clockOn_1 = clockOn_1;
    }

    public String getStandard_1() {
        return standard_1;
    }

    public void setStandard_1(String standard_1) {
        this.standard_1 = standard_1;
    }

    public String getClockOn_2() {
        return clockOn_2;
    }

    public void setClockOn_2(String clockOn_2) {
        this.clockOn_2 = clockOn_2;
    }

    public String getStandard_2() {
        return standard_2;
    }

    public void setStandard_2(String standard_2) {
        this.standard_2 = standard_2;
    }

    public String getClockOn_3() {
        return clockOn_3;
    }

    public void setClockOn_3(String clockOn_3) {
        this.clockOn_3 = clockOn_3;
    }

    public String getStandard_3() {
        return standard_3;
    }

    public void setStandard_3(String standard_3) {
        this.standard_3 = standard_3;
    }

    public String getClockOn_4() {
        return clockOn_4;
    }

    public void setClockOn_4(String clockOn_4) {
        this.clockOn_4 = clockOn_4;
    }

    public String getStandard_4() {
        return standard_4;
    }

    public void setStandard_4(String standard_4) {
        this.standard_4 = standard_4;
    }

    public Double getDiffHh() {
        return diffHh;
    }

    public void setDiffHh(Double diffHh) {
        this.diffHh = diffHh;
    }

    public Double getRestHh() {
        return restHh;
    }

    public void setRestHh(Double restHh) {
        this.restHh = restHh;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        this.mark4 = mark4;
    }

    public String getMemo() {
        if (memo != null && memo.length() > 1) {
            memo = memo.replaceAll("\\<.*?\\>", "");
        }
        return memo;
    }

    public void setMemo(String memo) {
        if (memo != null && memo.length() > 1) {
            memo = memo.replaceAll("\\<.*?\\>", "");
        }
        this.memo = memo;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getStatusName_1() {
        return statusName_1;
    }

    public void setStatusName_1(String statusName_1) {
        this.statusName_1 = statusName_1;
    }

    public String getStatusName_2() {
        return statusName_2;
    }

    public void setStatusName_2(String statusName_2) {
        this.statusName_2 = statusName_2;
    }

    public String getStatusName_3() {
        return statusName_3;
    }

    public void setStatusName_3(String statusName_3) {
        this.statusName_3 = statusName_3;
    }

    public String getStatusName_4() {
        return statusName_4;
    }

    public void setStatusName_4(String statusName_4) {
        this.statusName_4 = statusName_4;
    }

    public String getStatusShortname_1() {
        return statusShortname_1;
    }

    public void setStatusShortname_1(String statusShortname_1) {
        this.statusShortname_1 = statusShortname_1;
    }

    public String getStatusShortname_2() {
        return statusShortname_2;
    }

    public void setStatusShortname_2(String statusShortname_2) {
        this.statusShortname_2 = statusShortname_2;
    }

    public String getStatusShortname_3() {
        return statusShortname_3;
    }

    public void setStatusShortname_3(String statusShortname_3) {
        this.statusShortname_3 = statusShortname_3;
    }

    public String getStatusShortname_4() {
        return statusShortname_4;
    }

    public void setStatusShortname_4(String statusShortname_4) {
        this.statusShortname_4 = statusShortname_4;
    }

    @Override
    public String toString() {
        return "ViewClockOnReportMark{" + "reportDate=" + reportDate + ", week=" + week + ", groupName=" + groupName + ", empNo=" + empNo + ", empName=" + empName + ", statusName_1=" + statusName_1 + ", statusShortname_1=" + statusShortname_1 + ", clockOn_1=" + clockOn_1 + ", standard_1=" + standard_1 + ", statusName_2=" + statusName_2 + ", statusShortname_2=" + statusShortname_2 + ", clockOn_2=" + clockOn_2 + ", standard_2=" + standard_2 + ", statusName_3=" + statusName_3 + ", statusShortname_3=" + statusShortname_3 + ", clockOn_3=" + clockOn_3 + ", standard_3=" + standard_3 + ", statusName_4=" + statusName_4 + ", statusShortname_4=" + statusShortname_4 + ", clockOn_4=" + clockOn_4 + ", standard_4=" + standard_4 + ", diffHh=" + diffHh + ", restHh=" + restHh + ", mark1=" + mark1 + ", mark2=" + mark2 + ", mark3=" + mark3 + ", mark4=" + mark4 + ", memo=" + memo + ", result=" + result + ", replyScore=" + replyScore + ", replyContent=" + replyContent + ", replyEmpno=" + replyEmpno + ", replyDate=" + replyDate + ", assessmentScore=" + assessmentScore + ", assessmentContent=" + assessmentContent + ", assessmentEmpno=" + assessmentEmpno + ", assessmentDate=" + assessmentDate + '}';
    }

    

    
}
