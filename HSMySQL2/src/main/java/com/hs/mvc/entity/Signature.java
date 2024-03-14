package com.hs.mvc.entity;

import java.io.Serializable;
import java.util.Date;

public class Signature implements Serializable  {

    private Integer id;
    private String empNo;
    private Integer signType;
    private Integer signYear;
    private Integer signMonth;
    private String signImage;
    private Date signCt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public Integer getSignYear() {
        return signYear;
    }

    public void setSignYear(Integer signYear) {
        this.signYear = signYear;
    }

    public Integer getSignMonth() {
        return signMonth;
    }

    public void setSignMonth(Integer signMonth) {
        this.signMonth = signMonth;
    }

    public String getSignImage() {
        return signImage;
    }

    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }

    public Date getSignCt() {
        return signCt;
    }

    public void setSignCt(Date signCt) {
        this.signCt = signCt;
    }

    
}
