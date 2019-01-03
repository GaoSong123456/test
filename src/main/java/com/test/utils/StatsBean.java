/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: TongJBean
 * Author:   Administrator
 * Date:     2018/7/25 1:25
 * Description: TongJBean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.utils;

import org.apache.commons.collections4.Get;

/**
 * 统计员工出勤信息实体类
 *
 * @author Administrator
 * @create 2018/7/25
 * @since 1.0.0
 */
public class StatsBean {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 部门
     */
    private String department;

    /**
     * 总次数
     */
    private int count = 0;
    /**
     * 迟到次数
     */
    private int after = 0;
    /**
     * 早退次数
     */
    private int before = 0;
    /**
     * 上班未打卡次数
     */
    private int missing = 0;

    /**
     * 下班未打卡次数
     */
    private int intgetOff;
    /**
     * 工作日
     */
    private int work = 0;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getMissing() {
        return missing;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public int getIntgetOff() {
        return intgetOff;
    }

    public void setIntgetOff(int intgetOff) {
        this.intgetOff = intgetOff;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }
}
