package com.hs.mvc.entity.qa;

import java.util.Date;

public class QAItem {
    private Integer itemId;
    private Integer replyId;
    private Integer groupId;
    private String itemTopic;
    private String itemContent;
    private String itemEmpno;
    private Date itemCt;
    private Boolean itemIspublic;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getItemTopic() {
        return itemTopic;
    }

    public void setItemTopic(String itemTopic) {
        this.itemTopic = itemTopic;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemEmpno() {
        return itemEmpno;
    }

    public void setItemEmpno(String itemEmpno) {
        this.itemEmpno = itemEmpno;
    }

    public Date getItemCt() {
        return itemCt;
    }

    public void setItemCt(Date itemCt) {
        this.itemCt = itemCt;
    }

    public Boolean getItemIspublic() {
        return itemIspublic;
    }

    public void setItemIspublic(Boolean itemIspublic) {
        this.itemIspublic = itemIspublic;
    }

    @Override
    public String toString() {
        return "QAItem{" + "itemId=" + itemId + ", replyId=" + replyId + ", groupId=" + groupId + ", itemTopic=" + itemTopic + ", itemContent=" + itemContent + ", itemEmpno=" + itemEmpno + ", itemCt=" + itemCt + ", itemIspublic=" + itemIspublic + '}';
    }
    
    
}
