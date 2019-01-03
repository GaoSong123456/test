/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: UserTimeServiceImp
 * Author:   Administrator
 * Date:     2018/7/24 14:36
 * Description: UserTimeServiceImp
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.serviceimp;

import com.github.pagehelper.PageHelper;
import com.test.controller.PoiController;
import com.test.dao.TimesetMapper;
import com.test.dao.UsertimeMapper;
import com.test.po.Timeset;
import com.test.po.Usertime;
import com.test.service.TimesetService;
import com.test.service.UserTimeService;
import com.test.utils.StatsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 〈UserTimeServiceImp〉
 *
 * @author Administrator
 * @create 2018/7/24
 * @since 1.0.0
 */
@Transactional
@Service
public class UserTimeServiceImp implements UserTimeService {
    private static final Logger log = LoggerFactory.getLogger(PoiController.class);
    @Autowired
    private UsertimeMapper usertimeMapper;

    @Autowired
    private TimesetMapper timesetMapper;

    @Override
    public List<Usertime> selectByDateAndName(int now, int pageSize, Date start, Date end, String name) {


        PageHelper.startPage(now, pageSize);
        List<Usertime> usertimeList = usertimeMapper.selectByDateAndName(start, end, name);
       String week=null;
        for (int i = 0; i < usertimeList.size(); i++) {
            //上班打卡时间
            String starttime = usertimeList.get(i).getStarttime().toString();
            try {
                week = dayForWeek(starttime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //下班打卡时间
            String endtime = usertimeList.get(i).getEndtime().toString();

            Timeset timeset = timesetMapper.selectByPrimarykey();
           System.out.println(timeset.toString()+"嘿嘿");
            if (timeset.getWorktype() == 1) { //早9晚6硬性上班时间方案

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                /*Date startWork = null;
                Date endWork = null;
                try {
                    startWork = formatter.parse(starttime);
                    endWork = formatter.parse(endtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                //1.统计迟到的次数
                //1.1  12点前有打卡,无 上班未打卡
                //截止时间 >12未 打卡
                int hour = Integer.parseInt(starttime.substring(11, 13));
                int minute = Integer.parseInt(starttime.substring(14, 16));
                int second=Integer.parseInt(starttime.substring(17, 19));
                int afternoonHour = Integer.parseInt(endtime.substring(11, 13));
                int afternoonMinute = Integer.parseInt(endtime.substring(14, 16));
                int afternoonSecond=Integer.parseInt(starttime.substring(17, 19));
                //上班打卡时间  毫秒单位
                Long cWork= Long.valueOf(hour*60*60*1000+minute*60*1000+second*1000);
                //下班打卡时间 毫秒单位
                Long aWork= Long.valueOf(afternoonHour*60*60*1000+afternoonMinute*60*1000+afternoonSecond*1000);

                int cHour=Integer.parseInt(timeset.getCometime().substring(0, 2));
                int cminute=Integer.parseInt(timeset.getCometime().substring(3, 5));
                int csecond=Integer.parseInt(timeset.getCometime().substring(6, 8));
                int cafternoonHour=Integer.parseInt(timeset.getLeavetime().substring(0, 2));
                int cafternoonMinute=Integer.parseInt(timeset.getLeavetime().substring(3, 5));
                int cafternoonSecond=Integer.parseInt(timeset.getLeavetime().substring(6, 8));
                Long cTime = Long.valueOf(cHour*60*60*1000+cminute*60*1000+csecond*1000);
                Long lTime =Long.valueOf(cafternoonHour*60*60*1000+cafternoonMinute*60*1000+cafternoonSecond*1000);

                System.out.println(cHour+ ","+cminute+ ","+csecond+"哈哈"+ ","+cafternoonHour);
                System.out.println(cTime + "," + lTime+"嘿嘿嘿");
                //  true 表示 上下班 均有打卡
                boolean result = true;
                //上班统计
                if (cWork>= 12*60*60*1000) { //12点前无打卡记录
                    result = false;
                    usertimeList.get(i).setState("上班未打卡(" + week + ")");
                } else if (cWork>cTime) {
                    result = false;
                    //迟到次数+1
                    usertimeList.get(i).setState("迟到(" + week + ")");
                }
                //2.统计早退的人数
                //2.1  2点后有打卡  无 下班未打卡
                if (aWork< 12*60*60*1000) {
                    result = false;
                    usertimeList.get(i).setState("下班未打卡(" + week + ")");
                }
                // 2.1.2  下班时间早于数据库规定下班时间 早退
                if (aWork <lTime) {
                    result = false;
                    String message = usertimeList.get(i).getState();
                    if ("".equals(message) || message == null) {
                        usertimeList.get(i).setState("早退(" + week + ")");
                    }
                }
                if (result) {
                    usertimeList.get(i).setState("正常打卡");
                }

            } else if (timeset.getWorktype() == 2) {//弹性上班时间方案
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date startWork = null;
                Date endWork = null;
                try {
                    startWork = formatter.parse(starttime);
                    endWork = formatter.parse(endtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long number = (endWork.getTime() - startWork.getTime()) / 1000;
                double total = 9 * 60 * 60;
                //1.统计迟到的次数
                //1.1  12点前有打卡,无 上班未打卡
                //截止时间 >12未 打卡
                int hour = Integer.parseInt(starttime.substring(11, 13));
                int minute = Integer.parseInt(starttime.substring(14, 16));
                int afternoonHour = Integer.parseInt(endtime.substring(11, 13));
                int afternoonMinute = Integer.parseInt(endtime.substring(14, 16));
                boolean result = true;//当日考勤无异常

                //上班统计
                if (hour >= 12) { //12点前无打卡记录
                    result = false;
                    usertimeList.get(i).setState("上班未打卡(" + week + ")");
                } else if (hour > 9 && minute > 30) {
                    result = false;
                    //迟到次数+1
                    usertimeList.get(i).setState("迟到(" + week + ")");
                }
                //2.统计早退的人数
                //2.1  2点后有打卡  无 下班未打卡
                if (afternoonHour < 12) {
                    result = false;
                    usertimeList.get(i).setState("下班未打卡(" + week + ")");
                }
                // 2.1.2  在岗时间于 <9.5  早退
                if (number < total) {
                    result = false;
                    String message = usertimeList.get(i).getState();
                    if ("".equals(message) || message == null) {
                        usertimeList.get(i).setState("早退(" + week + ")");
                    }
                }
                if (result) {
                    usertimeList.get(i).setState("正常打卡");
                }
            }
        }
        return usertimeList;
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        String week=null;

        switch (dayForWeek){
            case 1:
                week="星期一";
                break;
            case 2:
                week="星期二";
                break;
            case 3:
                week="星期三";
                break;
            case 4:
                week="星期四";
                break;
            case 5:
                week="星期五";
                break;
            case 6:
                week="星期六";
                break;
            case 7:
                week="星期日";
                break;
        }




        return week;
    }

    /**
     * 提取的工具类 更改员工考勤统计记录
     *
     * @param start
     * @param end
     * @param name
     * @return
     */
    @Override
    public StatsBean counts(Date start, Date end, String name) {
        //统计迟到，早退，旷工
        //总次数
        int count = 0;
        //迟到次数
        int after = 0;
        // 早退次数
        int before = 0;
        //上班未打卡次数
        int missing = 0;
        // 下班未打卡次数
        int intgetOff = 0;
        //工作日
        int work = 0;
        List<Usertime> usertimeList2 = usertimeMapper.selectByDateAndName(start, end, name);
        for (int i = 0; i < usertimeList2.size(); i++) {
            //3.统计出勤天数
            //3. 数据库 是按员工 和打卡日期 分组统计  每一行记录代表一天出勤
            work++;//有对应记录 代表有出勤

            //上班打卡时间
            String starttime = usertimeList2.get(i).getStarttime().toString();
            //下班打卡时间
            String endtime = usertimeList2.get(i).getEndtime().toString();
            Timeset timeset = timesetMapper.selectByPrimarykey();
            System.out.println(timeset.toString()+"嘿嘿");
            if (timeset.getWorktype() == 1) { //早9晚6硬性上班时间方案

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                /*Date startWork = null;
                Date endWork = null;
                try {
                    startWork = formatter.parse(starttime);
                    endWork = formatter.parse(endtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                //1.统计迟到的次数
                //1.1  12点前有打卡,无 上班未打卡
                //截止时间 >12未 打卡
                int hour = Integer.parseInt(starttime.substring(11, 13));
                int minute = Integer.parseInt(starttime.substring(14, 16));
                int second=Integer.parseInt(starttime.substring(17, 19));
                int afternoonHour = Integer.parseInt(endtime.substring(11, 13));
                int afternoonMinute = Integer.parseInt(endtime.substring(14, 16));
                int afternoonSecond=Integer.parseInt(starttime.substring(17, 19));
                //上班打卡时间  毫秒单位
                Long cWork= Long.valueOf(hour*60*60*1000+minute*60*1000+second*1000);
                //下班打卡时间 毫秒单位
                Long aWork= Long.valueOf(afternoonHour*60*60*1000+afternoonMinute*60*1000+afternoonSecond*1000);

                int cHour=Integer.parseInt(timeset.getCometime().substring(0, 2));
                int cminute=Integer.parseInt(timeset.getCometime().substring(3, 5));
                int csecond=Integer.parseInt(timeset.getCometime().substring(6, 8));
                int cafternoonHour=Integer.parseInt(timeset.getLeavetime().substring(0, 2));
                int cafternoonMinute=Integer.parseInt(timeset.getLeavetime().substring(3, 5));
                int cafternoonSecond=Integer.parseInt(timeset.getLeavetime().substring(6, 8));
                Long cTime = Long.valueOf(cHour*60*60*1000+cminute*60*1000+csecond*1000);
                Long lTime =Long.valueOf(cafternoonHour*60*60*1000+cafternoonMinute*60*1000+cafternoonSecond*1000);

                System.out.println(cHour+ ","+cminute+ ","+csecond+"哈哈2"+ ","+cafternoonHour);
                System.out.println(cTime + "," + lTime+"嘿嘿嘿2");
                //上班统计
                if (cWork>= 12*60*60*1000) { //12点前无打卡记录
                    count++;//有异常出勤次数+1
                    missing++;//上班未打卡次数+1
                    //1.1.2   打卡时间数据库规定上班时间之前 否则 迟到
                } else if (cWork >cTime) {
                    //迟到次数+1
                    after++;
                    count++;//有异常出勤次数+1
                }
                //2.统计早退的人数
                //2.1  2点后有打卡  无 下班未打卡

                if (aWork<12*60*60*1000) {
                    count++;//有异常出勤次数+1
                    intgetOff++;
                }
                // 2.1.2  早于数据库规定下班时间 早退
                if (aWork<lTime) {
                    count++;//有异常出勤次数+1
                    before++;
                }



            } else if (timeset.getWorktype() == 2) {//弹性上班时间方案
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date startWork = null;
            Date endWork = null;
            try {
                startWork = formatter.parse(starttime);
                endWork = formatter.parse(endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long number = (endWork.getTime() - startWork.getTime()) / 1000;
            double total = 9.5 * 60 * 60;
            //1.统计迟到的次数
            //1.1  12点前有打卡,无 上班未打卡
            //截止时间 >12未 打卡
            int hour = Integer.parseInt(starttime.substring(11, 13));
            int minute = Integer.parseInt(starttime.substring(14, 16));

            int afternoonHour = Integer.parseInt(endtime.substring(11, 13));
            int afternoonMinute = Integer.parseInt(endtime.substring(14, 16));
            //上班统计
            if (hour >= 12) { //12点前无打卡记录
                count++;//有异常出勤次数+1
                missing++;//上班未打卡次数+1
                //1.1.2   打卡时间 9点半之前 否则 迟到
            } else if (hour > 9 && minute > 30) {
                //迟到次数+1
                after++;
                count++;//有异常出勤次数+1
            }
            //2.统计早退的人数
            //2.1  2点后有打卡  无 下班未打卡

            if (afternoonHour < 12) {
                count++;//有异常出勤次数+1
                intgetOff++;
            }
            // 2.1.2  在岗时间于 <9.5  早退
            if (number < total) {
                count++;//有异常出勤次数+1
                before++;
            }
        }
        }
        StatsBean jBean = new StatsBean();
        jBean.setCount(count);
        jBean.setAfter(after);
        jBean.setBefore(before);
        jBean.setMissing(missing);
        jBean.setWork(work);
        jBean.setIntgetOff(intgetOff);
        return jBean;
    }

    @Override
    public void insertBatch(List<Usertime> dataList) {

        usertimeMapper.insertBatch(dataList);
    }

    public static void main(String args[]) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat("HH:mm:ss").parse("18:00:00"));
        System.out.println(c);
        long sec=c.getTimeInMillis();
        System.out.println(sec);
    }
}
