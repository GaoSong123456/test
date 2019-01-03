/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: POIUtils
 * Author:   kevin
 * Date:     2018/8/29 9:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author kevin
 * @create 2018/8/29
 * @since 1.0.0
 */
public class POIUtils {
    private static Logger logger = Logger.getLogger(POIUtils.class);
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    public static List<String[]> readExcel(MultipartFile formFile) throws IOException {
//检查文件
        checkFile(formFile);
//获得工作簿对象
        Workbook workbook = getWorkBook(formFile);
//创建返回对象，把每行中的值作为一个数组，所有的行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (null != workbook) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
//获取当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (null == sheet) {
                    continue;
                }
//获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
//获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
//循环除了第一行之外的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
//获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
//后的当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
//获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
//循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }

    /**
     * 获取当前行数据
     *
     * @param cell
     * @return
     */
    @SuppressWarnings("deprecation")
    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            return cellValue;
        }
//把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
//判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC://数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING://字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN://Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA://公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK://空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR://故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


    /**
     * 获得工作簿对象
     *
     * @param formFile
     * @return
     */
    public static Workbook getWorkBook(MultipartFile formFile) {
//获得文件名
//	String fileName = formFile.getName();
        String fileName = formFile.getOriginalFilename();
//创建Workbook工作簿对象，表示整个excel
        Workbook workbook = null;
        try {
//获得excel文件的io流
            InputStream is = formFile.getInputStream();
//根据文件后缀名不同（xls和xlsx）获得不同的workbook实现类对象
            if (fileName.endsWith(XLS)) {
//2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
//2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 检查文件
     *
     * @param formFile
     * @throws IOException
     */
    public static void checkFile(MultipartFile formFile) throws IOException {
//判断文件是否存在
        if (null == formFile) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
//获得文件名
//	String fileName = formFile.getName();
        String fileName = formFile.getOriginalFilename();
//判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "不是excel文件！");
            throw new IOException(fileName + "不是excel文件！");
        }
    }


}