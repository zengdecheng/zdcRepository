package com.xbd.oa.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.DATE;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
 
public class ExcelUtils {
    public void createExcel(OutputStream os) throws WriteException, IOException
    {
       //创建工作薄
       WritableWorkbook workbook = Workbook.createWorkbook(os);
       //创建新的一页
       WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        //创建要显示的具体内容
       sheet.getSettings().setDefaultColumnWidth(10);
       sheet.getSettings().setDefaultRowHeight(500);
       
       WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 11,
               WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
               Colour.BLACK);
       WritableCellFormat format= new WritableCellFormat();
       format.setFont(font);
       format.setAlignment(Alignment.CENTRE);
       format.setVerticalAlignment(VerticalAlignment.CENTRE);
       format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
       
       //不超时则为绿色
       format.setBackground(Colour.WHITE);
       //超时白色
       WritableCellFormat format_ot = new WritableCellFormat();
       format_ot.setFont(font);
       format_ot.setAlignment(Alignment.CENTRE);
       format_ot.setVerticalAlignment(VerticalAlignment.CENTRE);
       format_ot.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
       format_ot.setBackground(Colour.WHITE);
       
       
       
       
       
       Label xuexiao = new Label(0, 0, "学校",format);
       sheet.addCell(xuexiao);
       Label zhuanye = new Label(1, 0, "专业",format);
       sheet.addCell(zhuanye);
       Label jingzhengli = new Label(2, 0, "专业竞争力",format);
       sheet.addCell(jingzhengli);
      
       Label qinghua = new Label(0, 1, "清华大学",format);
       sheet.addCell(qinghua);
       Label jisuanji = new Label(1, 1, "计算机专业",format);
       sheet.addCell(jisuanji);
       Label gao = new Label(2, 1, "高" ,format_ot);
       sheet.addCell(gao);
      
       Label beida = new Label(0, 2, "北京大学",format);
       sheet.addCell(beida);
       Label falv = new Label(1, 2, "法律专业",format);
       sheet.addCell(falv);
       Label zhong = new Label(2, 2, "中",format_ot);
       sheet.addCell(zhong);
      
       Label ligong = new Label(0, 3, "北京理工大学",format);
       sheet.addCell(ligong);
       Label hangkong = new Label(1, 3, "航空专业",format_ot);
       sheet.addCell(hangkong);
       Label di = new Label(2, 3, "低",format);
       sheet.addCell(di);
        //把创建的内容写入到输出流中，并关闭输出流
       workbook.write();
       workbook.close();
       os.close();
    }
    
   /**
     * 输出excel文件
     * @param OutputStream os 输出流 文件地址
     * @param String tableName 表名
     * @param int col 列码
     * @param int row 行号
     * @param List date 表格数据
     **/
    public static void createExcel4OA(OutputStream os, String tableName, int col, int row, List<ArrayList<HashMap>> list ) throws WriteException, IOException{
    	//行号和列的默认值
    	col = 0;
    	row = 0;
    	
    	//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet(tableName, 0);
        //设置默认表格高宽
        sheet.getSettings().setDefaultColumnWidth(12);
        sheet.getSettings().setDefaultRowHeight(500);
        
        WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 11,
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.AUTOMATIC);//设置字体，是否加粗，是否加下划线，字体颜色
        WritableCellFormat format= new WritableCellFormat();
        format.setFont(font);
        format.setAlignment(Alignment.CENTRE);//设置垂直居中
        format.setVerticalAlignment(VerticalAlignment.CENTRE);//设置水平居中
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//设置边框
        
        //不超时则为绿色
        format.setBackground(Colour.SEA_GREEN);
        //超时白色
        WritableCellFormat format_ot = new WritableCellFormat();
        format_ot.setFont(font);
        format_ot.setAlignment(Alignment.CENTRE);
        format_ot.setVerticalAlignment(VerticalAlignment.CENTRE);
        format_ot.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
        format_ot.setBackground(Colour.WHITE);
        
        List a = new ArrayList();
        a.add("序号");
		a.add("款号");
		a.add("财务部");
		a.add("技术部");
		a.add("采购部");
		a.add("CQC");
		a.add("MQC");
		a.add("QA验货");
		a.add("财务确认尾款");
		a.add("QA发货");
		a.add("汇总");
		a.add("计划时长");
        for(int i = 0;i<a.size();i++ ){
        	Label l = new Label(i,0,a.get(i).toString(),format_ot);
        	sheet.addCell(l);
        }
        
        //创建要显示的具体内容
        int i = 1;
        for(ArrayList<HashMap> dataList : list ){
        	int j = 0;
        	for(HashMap data: dataList){
        		Label titleName = null;
        		if( "0".equals( data.get("flag").toString() ) ){
        			titleName = new Label(j,i,(String) data.get("time"),format);
        		}else{
        			titleName = new Label(j,i,(String) data.get("time"),format_ot);
        		}
        		
        		sheet.addCell(titleName);
        		j++;
        	}
        	i++;
        }
        workbook.write();
        workbook.close();
        os.close();
        
        System.out.println("excel complete !");
    }
    
    public static void createExcelOADaHuoRate(OutputStream os, String tableName, int col, int row, List<ArrayList<String>> list ) throws WriteException, IOException{
    	//行号和列的默认值
    	col = 0;
    	row = 0;
    	
    	//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet(tableName, 0);
        //设置默认表格高宽
        sheet.getSettings().setDefaultColumnWidth(16);
        sheet.getSettings().setDefaultRowHeight(500);
        
       WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 11,
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.AUTOMATIC);//设置字体，是否加粗，是否加下划线，字体颜色
       //设置最后一行为蓝色
       WritableCellFormat format= new WritableCellFormat();
        format.setFont(font);
        format.setAlignment(Alignment.CENTRE);//设置垂直居中
        format.setVerticalAlignment(VerticalAlignment.CENTRE);//设置水平居中
        format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//设置边框
        format.setBackground(Colour.PALE_BLUE);
        //设置中间部分的背景为白色
        WritableCellFormat format_center = new WritableCellFormat();
        format_center.setBackground(Colour.WHITE);
        format_center.setFont(font);
        format_center.setAlignment(Alignment.CENTRE);
        format_center.setVerticalAlignment(VerticalAlignment.CENTRE);
        format_center.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
        
        //设置标题背景为黄色
        WritableCellFormat format_ot = new WritableCellFormat();
        format_ot.setBackground(Colour.YELLOW2);
        format_ot.setFont(font);
        format_ot.setAlignment(Alignment.CENTRE);
        format_ot.setVerticalAlignment(VerticalAlignment.CENTRE);
        format_ot.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
        
        List a = new ArrayList();
        a.add("序号");
        a.add("部门");
        a.add("流进订单");
        a.add("流出订单");
        a.add("未完成订单");
        a.add("异动订单数");
        a.add("正常订单数");
        a.add("及时率");
        for(int i = 0;i<a.size();i++ ){
        	Label l = new Label(i,0,a.get(i).toString(),format_ot);
        	sheet.addCell(l);
        }
        
        //创建要显示的具体内容
        int i = 1;
        for(ArrayList<String> dataList : list ){
        	int j = 0;
        	for(String data: dataList){
        		Label titleName = null;
        		if(i==list.size()){
        			sheet.mergeCells(1, i, 2, i);
        			sheet.mergeCells(5, i, 7, i);
        			titleName=new Label(j, i, data,format);
        		}else{
        			titleName=new Label(j, i, data,format_center);
        		}
        		sheet.addCell(titleName);
        		j++;
        	}
        	i++;
        }
        workbook.write();
        workbook.close();
        os.close();
        
        System.out.println("excel complete !");
    }
    public static void main(String[] args ){
    	try {
	    	ExcelUtils eu = new ExcelUtils();
	    	File file = new File("c:/srv/upload/" + System.currentTimeMillis() + ".xls");
	    	OutputStream os;
			os = new FileOutputStream(file,true);
			eu.createExcel(os);
			System.out.println("complete!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}