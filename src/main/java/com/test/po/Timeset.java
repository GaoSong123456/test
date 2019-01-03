package com.test.po;

public class Timeset {
    private  Integer timeid;

    private  String cometime;

    private  String leavetime;

    private  Integer worktype;

    public Integer getWorktype() {
        return worktype;
    }

    public void setWorktype(Integer worktype) {
        this.worktype = worktype;
    }

    public Integer getTimeid() {
        return timeid;
    }

    public void setTimeid(Integer timeid) {
        this.timeid = timeid;
    }

    public String getCometime() {
        return cometime;
    }

    public void setCometime(String cometime) {
        this.cometime = cometime;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    @Override
    public String toString() {
        return "Timeset{" +
                "timeid=" + timeid +
                ", cometime='" + cometime + '\'' +
                ", leavetime='" + leavetime + '\'' +
                ", worktype=" + worktype +
                '}';
    }
}
