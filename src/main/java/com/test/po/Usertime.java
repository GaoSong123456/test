package com.test.po;

import java.sql.Timestamp;
import java.util.Date;

public class Usertime {
    private Integer usertimeid;

    private Integer userid;

    private Timestamp datetime;

    private Timestamp starttime;

    private Timestamp endtime;

    private User user;

    private String state;

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUsertimeid() {
        return usertimeid;
    }

    public void setUsertimeid(Integer usertimeid) {
        this.usertimeid = usertimeid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }


    @Override
    public String toString() {
        return "Usertime{" +
                "usertimeid=" + usertimeid +
                ", userid=" + userid +
                ", datetime=" + datetime +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", user=" + user +
                ", state='" + state + '\'' +
                '}';
    }
}