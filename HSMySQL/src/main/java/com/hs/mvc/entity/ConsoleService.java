package com.hs.mvc.entity;

// 後台功能項

import java.io.Serializable;

public class ConsoleService implements Serializable {
    private Integer csId; // 主鍵
    private Integer csgId; // 群組 id
    private String csName; // 功能名稱
    private String csJspPath; // jsp 路徑
    private Integer sortId; // 排序
    
    public Integer getCsId() {
        return csId;
    }

    public void setCsId(Integer csId) {
        this.csId = csId;
    }

    public Integer getCsgId() {
        return csgId;
    }

    public void setCsgId(Integer csgId) {
        this.csgId = csgId;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getCsJspPath() {
        return csJspPath;
    }

    public void setCsJspPath(String csJspPath) {
        this.csJspPath = csJspPath;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "ConsoleService{" + "csId=" + csId + ", csgId=" + csgId + ", csName=" + csName + ", csJspPath=" + csJspPath + ", sortId=" + sortId + '}';
    }
    
    
}
