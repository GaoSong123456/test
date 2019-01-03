/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: PoiController
 * Author:   kevin
 * Date:     2018/8/5 21:13
 * Description: 导入导出
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.test.dao.UsertimeMapper;
import com.test.po.Usertime;
import com.test.service.UserTimeService;
import com.test.serviceimp.PoiService;
import com.test.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈导入导出〉
 * excel 下载
 *
 * @author kevin
 * @create 2018/8/5
 * @since 1.0.0
 */
@Controller
public class PoiController {

    private static final Logger log = LoggerFactory.getLogger(PoiController.class);

    private static final String prefix = "poi";

    @Autowired
    private UserTimeService userTimeService;

    @Autowired
    private PoiService poiService;

    // @Value("${poi.excel.sheet.name}")
    private String sheetProductName = "员工信息表";

    // @Value("${poi.excel.file.name}")
    private String excelProductName = "员工考勤信息.xls";


    /**
     * 下载excel
     *
     * @param response
     * @return
     */
    @RequestMapping(value = prefix + "/excel/export", method = RequestMethod.GET)
    public @ResponseBody
    String exportExcel(HttpServletResponse response, @RequestParam(value = "begindate1", required = false, defaultValue = "2018-07-01") Date start, @RequestParam(value = "enddate1", required = false, defaultValue = "2018-07-31") Date end, String name) {
        try {
            List<StatsBean> statsBeans = poiService.selectByDateAndName(start, end, name);

            String[] headers = new String[]{"指纹数字", "姓名", "部门", "迟到次数", "早退次数", "上班未打卡次数", "下班未打卡次数", "打卡异常总计", "出勤天数"};
            List<Map<Integer, Object>> dataList = ExcelBeanUtil.manageProductList(statsBeans);
            log.info("excel下载填充数据： {} ", dataList);

            Workbook wb = new HSSFWorkbook();
            ExcelUtil.fillExcelSheetData(dataList, wb, headers, sheetProductName);
            //解决 下载的中文乱码
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            WebUtil.downloadExcel(response, wb, excelProductName);
            return excelProductName;
        } catch (Exception e) {
            log.error("下载excel 发生异常：", e.fillInStackTrace());
        }
        return null;
    }

    /**
     * 上传excel导入数据
     *
     * @return 1、不要忘了支持springmvc上传文件的配置
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = prefix + "/excel/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadExcel(@RequestParam(value = "articleFile") MultipartFile excelFile, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入文件上传controller");
        String originalFilename = excelFile.getOriginalFilename();
        try {
            Workbook workbook = POIUtils.getWorkBook(excelFile);
            List<Usertime> usertimeList = poiService.readExcelData(workbook);

            for (Usertime  usertime  : usertimeList){
                System.out.println(usertime.getUserid()+"\t"+usertime.getDatetime());
            }

            userTimeService.insertBatch(usertimeList);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "false";





    }


}
