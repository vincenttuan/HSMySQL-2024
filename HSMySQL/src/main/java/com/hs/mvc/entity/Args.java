
package com.hs.mvc.entity;
// id	name	intArg1	intArg2	floatArg1	floatArg2	strArg1	strArg2	memo
public class Args {
    private Integer id;
    private String name;
    private Integer intArg1;
    private Integer intArg2;
    private Float floatArg1;
    private Float floatArg2;
    private String strArg1;
    private String strArg2;
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIntArg1() {
        return intArg1;
    }

    public void setIntArg1(Integer intArg1) {
        this.intArg1 = intArg1;
    }

    public Integer getIntArg2() {
        return intArg2;
    }

    public void setIntArg2(Integer intArg2) {
        this.intArg2 = intArg2;
    }

    public Float getFloatArg1() {
        return floatArg1;
    }

    public void setFloatArg1(Float floatArg1) {
        this.floatArg1 = floatArg1;
    }

    public Float getFloatArg2() {
        return floatArg2;
    }

    public void setFloatArg2(Float floatArg2) {
        this.floatArg2 = floatArg2;
    }

    public String getStrArg1() {
        return strArg1;
    }

    public void setStrArg1(String strArg1) {
        this.strArg1 = strArg1;
    }

    public String getStrArg2() {
        return strArg2;
    }

    public void setStrArg2(String strArg2) {
        this.strArg2 = strArg2;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Args{" + "id=" + id + ", name=" + name + ", intArg1=" + intArg1 + ", intArg2=" + intArg2 + ", floatArg1=" + floatArg1 + ", floatArg2=" + floatArg2 + ", strArg1=" + strArg1 + ", strArg2=" + strArg2 + ", memo=" + memo + '}';
    }
    
    
}
