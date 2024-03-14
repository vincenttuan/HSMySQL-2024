package com.hs.mvc.entity.qa;

import java.util.Date;

public class QAGroup {
    private Integer groupId;
    private String groupTopic;
    private String groupEmpno;
    private Date groupCt;
    private Boolean groupActive;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupTopic() {
        return groupTopic;
    }

    public void setGroupTopic(String groupTopic) {
        this.groupTopic = groupTopic;
    }

    public String getGroupEmpno() {
        return groupEmpno;
    }

    public void setGroupEmpno(String groupEmpno) {
        this.groupEmpno = groupEmpno;
    }

    public Date getGroupCt() {
        return groupCt;
    }

    public void setGroupCt(Date groupCt) {
        this.groupCt = groupCt;
    }

    public Boolean getGroupActive() {
        return groupActive;
    }

    public void setGroupActive(Boolean groupActive) {
        this.groupActive = groupActive;
    }

    @Override
    public String toString() {
        return "QAGroup{" + "groupId=" + groupId + ", groupTopic=" + groupTopic + ", groupEmpno=" + groupEmpno + ", groupCt=" + groupCt + ", groupActive=" + groupActive + '}';
    }
    
}
