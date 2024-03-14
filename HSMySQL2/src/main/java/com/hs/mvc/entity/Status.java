package com.hs.mvc.entity;

import java.io.Serializable;

public class Status implements Serializable  {
    private Integer statusId;
    private Integer statusNo;
    private String statusName;
    private Integer statusBegin;
    private Integer statusEnd;
    private Integer endoflist;
    private String color;
    private Integer firstEndStatusNo;
    private Integer secondEndStatusNo;
    private Integer groupId;
    
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusNo() {
        return statusNo;
    }

    public void setStatusNo(Integer statusNo) {
        this.statusNo = statusNo;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusBegin() {
        return statusBegin;
    }

    public void setStatusBegin(Integer statusBegin) {
        this.statusBegin = statusBegin;
    }

    public Integer getStatusEnd() {
        return statusEnd;
    }

    public void setStatusEnd(Integer statusEnd) {
        this.statusEnd = statusEnd;
    }

    public Integer getEndoflist() {
        return endoflist;
    }

    public void setEndoflist(Integer endoflist) {
        this.endoflist = endoflist;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getFirstEndStatusNo() {
        return firstEndStatusNo;
    }

    public void setFirstEndStatusNo(Integer firstEndStatusNo) {
        this.firstEndStatusNo = firstEndStatusNo;
    }

    public Integer getSecondEndStatusNo() {
        return secondEndStatusNo;
    }

    public void setSecondEndStatusNo(Integer secondEndStatusNo) {
        this.secondEndStatusNo = secondEndStatusNo;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
    
    
}
