package com.hs.mvc.entity;

// 後台功能權限設定表

import java.io.Serializable;

public class ConsoleServiceSetup implements Serializable {
    private Integer cssId; // 主鍵
    private Integer csId; // 後台功能項主鍵
    private Integer cssEmpId; // 授權員工 id
    private Integer cssPriority; // 權限
    
    // 一對一 後台功能項
    private ConsoleService cs;
    
    public Integer getCssId() {
        return cssId;
    }

    public void setCssId(Integer cssId) {
        this.cssId = cssId;
    }

    public Integer getCsId() {
        return csId;
    }

    public void setCsId(Integer csId) {
        this.csId = csId;
    }

    public Integer getCssEmpId() {
        return cssEmpId;
    }

    public void setCssEmpId(Integer cssEmpId) {
        this.cssEmpId = cssEmpId;
    }

    public Integer getCssPriority() {
        return cssPriority;
    }

    public void setCssPriority(Integer cssPriority) {
        this.cssPriority = cssPriority;
    }

    public ConsoleService getCs() {
        return cs;
    }

    public void setCs(ConsoleService cs) {
        this.cs = cs;
    }

    @Override
    public String toString() {
        return "ConsoleServiceSetup{" + "cssId=" + cssId + ", csId=" + csId + ", cssEmpId=" + cssEmpId + ", cssPriority=" + cssPriority + ", cs=" + cs + '}';
    }

    
    
}
