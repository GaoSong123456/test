package com.test.utils;

import java.util.*;

import com.test.po.Usertime;

/**
 * 导入excel bean数据工具类
 *
 * @author kevin
 * @create 2018/8/5
 * @since 1.0.0
 */
public class ExcelBeanUtil {

    /**
     * 处理列表 塞入list-map 等待塞入excel的workbook进行处理
     *
     * @param products
     * @return
     */
    public static List<Map<Integer, Object>> manageProductList(final List<StatsBean> products) {
        List<Map<Integer, Object>> dataList = new ArrayList<>();
        if (products != null && products.size() > 0) {
            int length = products.size();

            Map<Integer, Object> dataMap;
            StatsBean bean;

            for (int i = 0; i < length; i++) {
                bean = products.get(i);
                //"指纹数字", "姓名", "部门", "迟到次数", "早退次数","上班未打卡次数","下班未打卡次数","总计","工作日"
                dataMap = new HashMap<>();
                dataMap.put(0, bean.getUserId());
                dataMap.put(1, bean.getUserName());
                dataMap.put(2,bean.getDepartment());
                dataMap.put(3,bean.getAfter());
                dataMap.put(4,bean.getBefore());
                dataMap.put(5,bean.getMissing());
                dataMap.put(6,bean.getIntgetOff());
                dataMap.put(7,bean.getCount());
                dataMap.put(8,bean.getWork());
                dataList.add(dataMap);
            }
        }
        return dataList;
    }


}












