package com.hs.mvc.entity.qa;

import java.util.Date;

public class QAReply {
    private Integer replyId;
    private Integer itemId;
    private String replyContent;
    private String replyEmpno;
    private Date replyCt;

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public Date getReplyCt() {
        return replyCt;
    }

    public void setReplyCt(Date replyCt) {
        this.replyCt = replyCt;
    }

    @Override
    public String toString() {
        return "QAReply{" + "replyId=" + replyId + ", itemId=" + itemId + ", replyContent=" + replyContent + ", replyEmpno=" + replyEmpno + ", replyCt=" + replyCt + '}';
    }
    
    
}
