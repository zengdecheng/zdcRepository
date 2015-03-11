package com.xbd.oa;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;

import com.xbd.oa.utils.POIUtilsEx;
import com.xbd.oa.vo.CustomCell;

public class TestPOI {
	/**
	 * @author fangwei 2015-01-30
	 * POI 填充内容<br>
	 * fillInfo<br>
	 * |- (fileUrl:fileUrl) excel模板地址<br>
	 * |- (sheetNames:name1,name2...) sheet名称<br>
	 * |- (name1FileInfo:map) name1填充信息<br>
	 * |- (name1DynaRow:map) name1动态增行(40,5)<br>
	 * |- (name1MergeCell:map) name1合并单元格("1,2,3,4",true)<br>
	 * <br>
	 * 填充信息:<br>
	 * 1.1 常规文本默认格式填充 ("1,2","zhangsan")<br>
	 * 1.2 其他格式文本填充("1,2,Date/Calendar/Double/Boolean/String/expression(公式)/Picture","2015-01-01")<br>
	 * 					Date/Calendar：Date-DateFormat(yyyy-mm-dd等)<br>
	 * 					Double:Double-DecimalFormat(0.00,#,##0.0等)<br>		
	 * 2.常规图片默认格式填充("1,2,8,8",picUrl)<br>
	 * 3.自定义全格式文本填充("1,2,8,8),customCell)<br>
	 *	public static final Map<String, Object> fillInfo = Map<String, Object>(); // POI 填充内容
	 */
	@Test
	public void test1() throws Exception{
		String baseExcelFile = "D:/erp.xlsx";
		String outputFile = "D:/OUT.xlsx";
		Map<String,Object> fillInfo = new HashMap<String,Object>();
		fillInfo.put("fileUrl", baseExcelFile);
		fillInfo.put("sheetNames", "订单进度跟踪报表-大货");
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		sheet1FileInfo.put("0,0,Expression","RAND()");
		sheet1FileInfo.put("0,1,Expression","RAND()*5");
		sheet1FileInfo.put("0,2,Expression","RAND()");
		sheet1FileInfo.put("0,3,Expression","1+6");
		sheet1FileInfo.put("0,4,Expression","SUM(A1:D1)");
		sheet1FileInfo.put("1,1,5,5","http://erp.singbada.cn/images/login_pic_1.jpg");
		
		sheet1FileInfo.put("9,9",new CustomCell(1234.123, "Double-¥#,##0.0"));
		sheet1FileInfo.put("8,9",new CustomCell(11.6, "Double"));
		sheet1FileInfo.put("8,8",new CustomCell(new Date(), "Date"));
		sheet1FileInfo.put("8,7",new CustomCell("蓝背景", "String").setCellColor(IndexedColors.BLUE.index));
		sheet1FileInfo.put("8,6",new CustomCell("彩边框", "String").setBorderColors(IndexedColors.GREEN.index));
		sheet1FileInfo.put("8,5",new CustomCell("无边框", "String").setHasBorder(false));
		sheet1FileInfo.put("8,4",new CustomCell("红粗斜", "String").setFontBoldWeight(Font.BOLDWEIGHT_BOLD).setFontColor(IndexedColors.RED.index).setFontItalic(true));
		sheet1FileInfo.put("8,3",new CustomCell("楷体", "String").setFontName("楷体"));
		sheet1FileInfo.put("8,2",new CustomCell("粗体", "String").setFontBoldWeight(Font.BOLDWEIGHT_BOLD));
		sheet1FileInfo.put("8,1",new CustomCell("红色字体", "String").setFontColor(IndexedColors.RED.index));
		sheet1FileInfo.put("8,0",new CustomCell("蓝色字体", "String").setFontColor(IndexedColors.BLUE.index));
		sheet1FileInfo.put("7,9",new CustomCell("自定义字号", "String").setFontSize((short) 9));
		sheet1FileInfo.put("9,8,Double", 1111111.231D);
		sheet1FileInfo.put("7,8,Double", 11.231D);
		sheet1FileInfo.put("7,7,Boolean", false);//
		sheet1FileInfo.put("7,6,Calendar-yyyy/MM/dd hh", Calendar.getInstance());
		sheet1FileInfo.put("7,5,Calendar", Calendar.getInstance());//
		sheet1FileInfo.put("7,4,Date-yy-MM-dd", new Date());
		sheet1FileInfo.put("7,3,Date", new Date());
		sheet1FileInfo.put("7,2", new Date());//
		sheet1FileInfo.put("7,1", 111);//
		sheet1FileInfo.put("7,0", new CustomCell("自定义格式", "String"));
		Map<String,Object> sheet1DynaRow = new HashMap<String,Object>();
		//sheet1DynaRow.put("10", 1);
		//sheet1DynaRow.put("2", 1);
		Map<String,Object> sheet1MergeCell = new HashMap<String,Object>();
		sheet1MergeCell.put("4,4,0,4", true);

		fillInfo.put("订单进度跟踪报表-大货FileInfo", sheet1FileInfo);
		fillInfo.put("订单进度跟踪报表-大货DynaRow", sheet1DynaRow);
		fillInfo.put("订单进度跟踪报表-大货MergeCell", sheet1MergeCell);
		
		OutputStream os =new FileOutputStream(outputFile); 
		POIUtilsEx.processExcel(os, fillInfo);
		
		
	}
}
