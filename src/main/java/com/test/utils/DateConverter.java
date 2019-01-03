/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: DateConverter
 * Author:   Administrator
 * Date:     2018/7/10 13:38
 * Description: DateConverter
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈DateConverter〉
 *
 * @author Administrator
 * @create 2018/7/10
 * @since 1.0.0
 */
public class DateConverter implements Converter<String,Date> {

    
    @Override
    public Date convert(String arg0) {
        System.out.println(arg0);
        Date date=null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        simpleDateFormat.setLenient(false);// 宽松解析true，严格解析false
        if (arg0!=null) {

            try {
                date= simpleDateFormat.parse(arg0);
                System.out.println(date);
            } catch (ParseException e) {


            }

        }

        return date;
    }
}
