package com.hk.apache.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class ExcelMain {


    //读取Excel
    //读取xlxs
    private static void readExcel() {

        String pathStr = "";
        File file = new File(pathStr);
        try(FileInputStream fis = new FileInputStream(file)) {

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //构建Excel
    private static void buildExcel() {

        File file = new File(System.getProperty("user.dir") + File.separator + "cache" + File.separator + "test.xls");

        try(FileOutputStream fos = new FileOutputStream(file);) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("test");

            row = sheet.createRow(1);
            row.createCell(0).setCellValue("1");
            row.createCell(1).setCellValue("NO00001");
            row.createCell(2).setCellValue("设置值");


            workbook.setActiveSheet(0);
            workbook.write(fos);

        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        buildExcel();
    }
}
