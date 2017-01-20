package com.tcl.utils;

import com.sun.media.sound.InvalidFormatException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * AHTHOR : 杨清杰
 * DATE   : 2016/10/13
 * DESC   : Poi解析工具
 */
public class PoiUtil {

    public static final String UPON = "UPON";
    public static final String BELOW = "BELOW";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";

    /**
     * AHTHOR : 杨清杰
     * DATE   : 2016/9/28
     * DESC   : 读取Excel文件
     */
    public static List<List<String>> readExcel(InputStream inputStream, int startRow, boolean isfull) throws NoSuchFieldException, IOException, InvalidFormatException {

        ArrayList<List<String>> lists = new ArrayList<List<String>>();
        DataFormatter formatter = new DataFormatter();
        String cellValue = "";
        // 读取excel
        Workbook workbook = createWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        if (sheet != null) {
            for (Row row : sheet) {
                // 跳过第一行
                if (row.getRowNum() < startRow - 1) continue;

                boolean isCellNull = false; // 本行是否存在单元格为空的数据
                List<String> list = new ArrayList<String>(); // 本行数据集
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        //数值类型
                        case Cell.CELL_TYPE_NUMERIC:
                            //进一步判断 ，单元格格式是日期格式
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = formatter.formatCellValue(cell);
                            } else {
                                //数值
                                double value = cell.getNumericCellValue();
                                int intValue = (int) value;
                                cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                        case Cell.CELL_TYPE_FORMULA: {
                            try {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                cellValue = String.valueOf(cell.getRichStringCellValue());
                            }

                        }
                        break;
                        case Cell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            cellValue = "";
                            break;
                        default:
                            cellValue = cell.toString().trim();
                            break;
                    }
                    if (StringUtils.isNotBlank(cellValue)) {
                        list.add(cellValue);
                    } else {
                        list.add("");
                        // 单元格为空跳过这一行
                        isCellNull = true;
                        break;
                    }

                }
                if (isfull) {
                    if (!isCellNull) lists.add(list);
                } else {
                    lists.add(list);
                }

            }

        }
        return lists;
    }

    /**
     * AHTHOR : 杨清杰
     * DATE   : 2016/9/28
     * DESC   : 读取Excel文件
     */
    public static List<List<String>> readExcel(InputStream inputStream, int startRow) throws NoSuchFieldException, IOException, InvalidFormatException {

        ArrayList<List<String>> lists = new ArrayList<List<String>>();
        DataFormatter formatter = new DataFormatter();
        String cellValue = "";
        // 读取excel
        Workbook workbook = createWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        if (sheet != null) {
            for (Row row : sheet) {
                // 跳过第一行
                if (row.getRowNum() < startRow - 1) continue;

                boolean isCellNull = false; // 本行是否存在单元格为空的数据
                List<String> list = new ArrayList<String>(); // 本行数据集
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        //数值类型
                        case Cell.CELL_TYPE_NUMERIC:
                            //进一步判断 ，单元格格式是日期格式
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = formatter.formatCellValue(cell);
                            } else {
                                //数值
                                double value = cell.getNumericCellValue();
                                int intValue = (int) value;
                                cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                        case Cell.CELL_TYPE_FORMULA: {
                            try {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                cellValue = String.valueOf(cell.getRichStringCellValue());
                            }

                        }
                        break;
                        case Cell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            cellValue = "";
                            break;
                        default:
                            cellValue = cell.toString().trim();
                            break;
                    }
                    if (StringUtils.isNotBlank(cellValue)) {
                        list.add(cellValue);
                    } else {
                        // 单元格为空跳过这一行
                        isCellNull = true;
                        break;
                    }

                }
                if (!isCellNull) lists.add(list);
            }

        }
        return lists;
    }

    /**
     * AHTHOR : 杨清杰
     * DATE   : 2016/10/13
     * DESC   : 创建操作excel的对象
     */
    public static Workbook createWorkbook(InputStream inputStream) throws IOException {
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (workbook != null) {
            //设置错误提示列的样式、字体、颜色
            Font font = workbook.createFont();
            font.setColor(Font.COLOR_RED);
            workbook.createCellStyle();
        }

        return workbook;
    }

    /**
     * AHTHOR : 杨清杰
     * DATE   : 2016/11/17
     * DESC   : 根据Workbook获取指定sheetAt的页面，获取指定坐标(D4)的单元格
     */
    public static Cell getCell(Workbook workbook, int sheetAt, String coordinate) throws InvalidFormatException, IOException {

        // 解析坐标 - 0 开始
        int x = 0; // 列坐标
        int y = 0; // 行坐标
        if (StringUtils.isNotBlank(coordinate) && coordinate.length() > 1) {
            coordinate = coordinate.toLowerCase();
            x = (int) coordinate.toCharArray()[0] - 97;
            y = Integer.parseInt(coordinate.substring(1) + "") - 1;
        } else {
            return null;
        }

        // 获取单元格
        return getCellForXy(workbook, sheetAt, x, y);
    }

    /**
     * AHTHOR : 杨清杰
     * DATE   : 2016/11/17
     * DESC   : 根据Workbook获取指定sheetAt的页面，获取指定坐标(D4)的单元格
     */
    public static String getCellStr(Workbook workbook, int sheetAt, String coordinate) throws InvalidFormatException, IOException {

        // 解析坐标 - 0 开始
        int x = 0; // 列坐标
        int y = 0; // 行坐标
        if (StringUtils.isNotBlank(coordinate) && coordinate.length() > 1) {
            coordinate = coordinate.toLowerCase();
            x = (int) coordinate.toCharArray()[0] - 97;
            y = Integer.parseInt(coordinate.substring(1) + "") - 1;
        } else {
            return null;
        }

        // 获取单元格
        Cell cellForXy = getCellForXy(workbook, sheetAt, x, y);
        return analysisCell(cellForXy);
    }

    /**
      AHTHOR : 杨清杰
      DATE   : 2016/11/17
      DESC   : 获取单元格 - 根据XY
    */
    private static Cell getCellForXy(Workbook workbook, int sheetAt, int x, int y) {
        Cell cell = null;
        Sheet sheet = workbook.getSheetAt(sheetAt);
        if (sheet != null) {
            if (sheet.getLastRowNum() >= y) {
                Row row = sheet.getRow(y); // 获取第几行
                if (row.getLastCellNum() >= x) {
                    cell = row.getCell(x);
                }
            }
        }

        return cell;
    }

    /**
     AHTHOR : 杨清杰
     DATE   : 2016/11/17
     DESC   : 获取行数据
     */
    public static Row getRow(Workbook workbook, int sheetAt, int y) {
        Row row = null;
        Sheet sheet = workbook.getSheetAt(sheetAt);
        if (sheet != null) {
            if (sheet.getLastRowNum() >= y) {
                row = sheet.getRow(y - 1); // 获取第几行
            }
        }
        return row;
    }
/**
     * 获取某个单元格的相邻单元格
     * @param workbook
     * @param sheetAt
     * @param cell
     * @param position
     * @return
     */
    public static Cell getNeighbourCell(Workbook workbook,int sheetAt,Cell cell,String position) {
        Cell neighbourCell = null;
        if (cell != null) {
            int x = cell.getColumnIndex();
            int y = cell.getRowIndex();
            switch (position) {
                case UPON: y --;
                case BELOW: y ++;
                case LEFT: x --;
                case RIGHT:x ++;
            }
            Sheet sheet = workbook.getSheetAt(sheetAt);
            if (sheet != null) {
                if (sheet.getLastRowNum() >= y) {
                    Row row = sheet.getRow(y);
                    if (row.getLastCellNum() >= x) neighbourCell = row.getCell(x);
                }
            }
        }
        return neighbourCell;
    }

    /**
     AHTHOR : 杨清杰
     DATE   : 2016/11/21
     DESC   : 解析cell值
     */
    public static String analysisCell(Cell cell) {
       String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if(cell != null)
            switch (cell.getCellType()) {
                //数值类型
                case Cell.CELL_TYPE_NUMERIC:
                    //进一步判断 ，单元格格式是日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        //数值
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                case Cell.CELL_TYPE_FORMULA: {
                    try {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }

                }
                break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }

        return cellValue;
    }


    public static void main(String[] args) throws Exception {
        // 测试
        Cell a1 = getCell(createWorkbook(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\test\\address_list_temp.xlsx"))), 0, "a3");
        if(a1 != null) System.out.println(a1.getStringCellValue());
    }
}
