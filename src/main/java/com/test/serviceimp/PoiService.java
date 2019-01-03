package com.test.serviceimp;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.test.dao.TimesetMapper;
import com.test.dao.UsertimeMapper;
import com.test.po.Timeset;
import com.test.po.Usertime;
import com.test.utils.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.font.TrueTypeFont;


/**
 * excel 业务逻辑层
 *
 * @author kevin
 * @create 2018/8/5
 * @since 1.0.0
 */
@Service
public class PoiService {

    private static final Logger log = LoggerFactory.getLogger(PoiService.class);
    @Autowired
    private UsertimeMapper usertimeMapper;

    @Autowired
    private TimesetMapper timesetMapper;

    /**
     * 读取excel数据
     *
     * @param wb
     * @return
     * @throws Exception
     */
    public List<Usertime> readExcelData(Workbook wb) throws Exception {
        HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
        Usertime usertime = null;
        List<Usertime> userTimes = new ArrayList<>();
        for (int j = 0; j < sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(j);
            usertime = new Usertime();
            HSSFCell cellOne = row.getCell(0);
//判断各个单元格是否为空
            if(cellOne==null||cellOne.equals("")||cellOne.getCellType() ==HSSFCell.CELL_TYPE_BLANK){
                 return  userTimes;
            }


                Double id = cellOne.getNumericCellValue();
            HSSFCell cellTwo = row.getCell(1);
            Date dateCellValue = cellTwo.getDateCellValue();
            Integer userid = id.intValue();
            usertime.setUserid(userid);
            Timestamp ts = new Timestamp(dateCellValue.getTime());
            usertime.setDatetime(ts);

            userTimes.add(usertime);
        }
        log.info("获取数据列表: {} ", userTimes);
        return userTimes;
    }

    public List<StatsBean> selectByDateAndName(Date start, Date end, String name) {
        PageHelper.startPage(1, 10000);
        List<Usertime> usertimeList = usertimeMapper.selectByDateAndName(start, end, name);

        List<StatsBean> statsBeans = new ArrayList<>();

        for (Usertime usertime : usertimeList) {
            Integer uid = usertime.getUser().getUserid();


            boolean cc = true;//代表无数据
            for (int i = 0; i < statsBeans.size(); i++) {
                //存在员工记录
                StatsBean statsBean = statsBeans.get(i);
                if (uid == statsBean.getUserId()) {
                    addStats(usertime, statsBean);
                    //移除旧的
                    statsBeans.remove(i);
                    statsBeans.add(i, statsBean);
                    cc = false;
                }
            }
            //有员工记录 但是要新增的记录的员工不存在
            if (cc) {
                StatsBean statsBean1 = new StatsBean();
                addStats(usertime, statsBean1);
                statsBeans.add(statsBean1);
            }


        }

        return statsBeans;
    }

    /**
     * 将员工打卡相关信息 封装到 导出需要的实体类StatsBean
     *
     * @param usertime
     * @param statsBean
     */
    public void addStats(Usertime usertime, StatsBean statsBean) {
        //上班打卡时间
        String starttime = usertime.getStarttime().toString();
        //下班打卡时间
        String endtime = usertime.getEndtime().toString();

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
            //  true 表示 上下班 均有打卡
            boolean message = true;

                //上班统计
                if (cWork>= 12*60*60*1000) { //12点前无打卡记录
                    int count = statsBean.getCount() + 1;//有异常出勤次数+1
                    statsBean.setCount(count);
                    int missing = statsBean.getMissing() + 1;//上班未打卡次数+1
                    statsBean.setMissing(missing);
                    message = false;
                    //1.1.2   打卡时间在数据库规定上班时间之前 否则 迟到
                } else if (cWork>cTime) {
                    //迟到次数+1
                    int after = statsBean.getAfter() + 1;
                    statsBean.setAfter(after);

                    int count = statsBean.getCount() + 1;//有异常出勤次数+1
                    statsBean.setCount(count);
                }
            //2.统计早退的人数
            //2.1  2点后有打卡  无 下班未打卡

            if (aWork<14*60*60*1000) {
                int count = statsBean.getCount() + 1;//有异常出勤次数+1
                statsBean.setCount(count);
                int intgetOff = statsBean.getIntgetOff() + 1;
                statsBean.setIntgetOff(intgetOff);
                message = false;
            }
            //2.2 早于数据库规定下班时间  早退
            if (aWork<lTime){
                int count = statsBean.getCount() + 1;//有异常出勤次数+1
                statsBean.setCount(count);
                int before = statsBean.getBefore() + 1;
                statsBean.setBefore(before);
            }

            int work = statsBean.getWork() + 1;
            statsBean.setWork(work);

            //其他属性赋值
            statsBean.setUserId(usertime.getUserid());
            statsBean.setUserName(usertime.getUser().getUsername());
            statsBean.setDepartment(usertime.getUser().getDepartment());


        }else if (timeset.getWorktype()==2){//弹性上班时间方案

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

        //  true 表示 上下班 均有打卡
        boolean message = true;


        //上班统计
        if (hour >= 12) { //12点前无打卡记录
            int count = statsBean.getCount() + 1;//有异常出勤次数+1
            statsBean.setCount(count);
            int missing = statsBean.getMissing() + 1;//上班未打卡次数+1
            statsBean.setMissing(missing);
            message = false;

            //1.1.2   打卡时间 9点半之前 否则 迟到
        } else if (hour > 9 && minute > 30) {
            //迟到次数+1
            int after = statsBean.getAfter() + 1;
            statsBean.setAfter(after);

            int count = statsBean.getCount() + 1;//有异常出勤次数+1
            statsBean.setCount(count);
        }
        //2.统计早退的人数
        //2.1  2点后有打卡  无 下班未打卡

        if (afternoonHour < 14) {
            int count = statsBean.getCount() + 1;//有异常出勤次数+1
            statsBean.setCount(count);
            int intgetOff = statsBean.getIntgetOff() + 1;
            statsBean.setIntgetOff(intgetOff);
            message = false;
        }
        //   上下班打卡无异常才统计 在岗时间
        // 2.1.2  在岗时间于 <9.5  早退
        if (message) {
            if (number < total) {
                int count = statsBean.getCount() + 1;//有异常出勤次数+1
                statsBean.setCount(count);
                int before = statsBean.getBefore() + 1;
                statsBean.setBefore(before);
            }

        }
        int work = statsBean.getWork() + 1;
        statsBean.setWork(work);

        //其他属性赋值
        statsBean.setUserId(usertime.getUserid());
        statsBean.setUserName(usertime.getUser().getUsername());
        statsBean.setDepartment(usertime.getUser().getDepartment());

        }
    }
}