package com.xbd.oa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import net.sf.jxls.util.Util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author fangwei
 * @since 2014-12-07
 */
public class POIUtils {
	public static final Logger logger = Logger.getLogger(POIUtils.class);
	public static DecimalFormat format2=new DecimalFormat("0.##");
	public static DecimalFormat format3=new DecimalFormat("0.###");
	/**
	 * 设置样式
	 * 
	 * @param wb
	 *            workbook
	 * @param hasBorder
	 *            是否有边框
	 * @param fontColor
	 *            字体颜色
	 * @param isWrapText
	 *            是否自动换行
	 * @return
	 */
	public static CellStyle getStyle(Workbook wb, boolean hasBorder, short fontColor, boolean isWrapText) {
		CellStyle style = wb.createCellStyle();

		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("微软雅黑");
		style.setFont(font);

		if (hasBorder) {
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setTopBorderColor(fontColor);
			style.setBottomBorderColor(fontColor);
			style.setLeftBorderColor(fontColor);
			style.setRightBorderColor(fontColor);
		}
		style.setWrapText(isWrapText);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		return style;
	}

	public static void insertRow(Sheet sheet, int startRow, int rows) {
		sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows, true, false);
		for (int i = 0; i < rows; i++) {
			Row sourceRow = null;
			Row targetRow = null;
			sourceRow = sheet.getRow(startRow);
			targetRow = sheet.createRow(++startRow);
			Util.copyRow(sheet, sourceRow, targetRow);
		}
		// sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows, true, false);
		/*
		 * startRow = startRow - 1; for (int i = 0; i < rows; i++) { Row sourceRow = null; Row targetRow = null; Cell sourceCell = null; Cell targetCell = null; short m;
		 * 
		 * startRow = startRow + 1; sourceRow = sheet.getRow(startRow); targetRow = sheet.createRow(startRow + 1); targetRow.setHeight(sourceRow.getHeight());
		 * 
		 * for (m = sourceRow.getFirstCellNum(); m < sourceRow.getPhysicalNumberOfCells(); m++) { sourceCell = sourceRow.getCell(m); targetCell = targetRow.createCell(m); targetCell.setCellStyle(sourceCell.getCellStyle()); targetCell.setCellType(sourceCell.getCellType()); } }
		 */
	}

	/**
	 * 初始化
	 */
	public static void processExcel(OutputStream os, String file, String sheetIndex, Map<String, Object> dynaRow, Map<String, Object> params) {
		boolean is2007 = false;
		Workbook wb = null;
		logger.debug("开始写入Excel...");
		if (!new File(file).exists()) {
			throw new RuntimeException("the attachment in server [" + file + "]is not exists!");
		}
		if (file.endsWith("xlsx")) {
			is2007 = true;
		}
		try {
			InputStream input = new FileInputStream(file);
			if (is2007) {
				logger.debug("开始创建XSSFWorkbook...");
				wb = new XSSFWorkbook(input);
				logger.debug("结束创建XSSFWorkbook...");
			} else {
				logger.debug("开始创建HSSFWorkbook...");
				wb = new HSSFWorkbook(input);
				logger.debug("结束创建HSSFWorkbook...");
			}
			logger.debug("开始得到目标Sheet...");
			Sheet sheet = wb.getSheetAt(Integer.parseInt(sheetIndex) - 2);
			sheet.setForceFormulaRecalculation(true);
			logger.debug("结束得到Sheet...");
			logger.debug("开始增加行...");
			dynaRow(sheet, dynaRow);
			logger.debug("结束增加行...");
			logger.debug("开始填充sheet...");
			excelFilling(wb, sheet, params);
			logger.debug("结束填充sheet...");
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	public static void processExcel(OutputStream os, String file, List<Map<String,Object>> list) {
		boolean is2007 = false;
		Workbook wb = null;
		logger.debug("开始写入Excel...");
		if (!new File(file).exists()) {
			throw new RuntimeException("the attachment in server [" + file + "]is not exists!");
		}
		if (file.endsWith("xlsx")) {
			is2007 = true;
		}
		try {
			InputStream input = new FileInputStream(file);
			if (is2007) {
				logger.debug("开始创建XSSFWorkbook...");
				wb = new XSSFWorkbook(input);
				logger.debug("结束创建XSSFWorkbook...");
			} else {
				logger.debug("开始创建HSSFWorkbook...");
				wb = new HSSFWorkbook(input);
				logger.debug("结束创建HSSFWorkbook...");
			}
			for(Map<String,Object> map:list){
				String sheetIndex = map.get("sheetIndex").toString();
				logger.debug("开始得到目标Sheet...");
				Sheet sheet = wb.getSheetAt(Integer.parseInt(sheetIndex) - 2);
				logger.debug("结束得到Sheet...");
				logger.debug("开始增加行...");
				Map<String,Object> dynaRow=  (Map<String, Object>) map.get("dynaRow");
				dynaRow(sheet, dynaRow);
				logger.debug("结束增加行...");
				logger.debug("开始填充sheet...");
				Map<String,Object> params=  (Map<String, Object>) map.get("params");
				excelFilling(wb, sheet, params);
				logger.debug("结束填充sheet...");
				sheet.setForceFormulaRecalculation(true);
			}
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 动态增减行
	 */
	public static void dynaRow(Sheet sheet, Map<String, Object> dynaRow) {
		if (!dynaRow.isEmpty()) {
			for (Map.Entry<String, Object> entry : dynaRow.entrySet()) {
				int addIndex = Integer.parseInt((String) entry.getKey());
				int addNum = Integer.parseInt((String) entry.getValue());
				insertRow(sheet, addIndex, addNum);
			}
		}
	}

	@SuppressWarnings("static-access")
	public static void excelFilling(Workbook wb, Sheet sheet, Map<String, Object> map) throws Exception {
		Row row = null;
		Cell cell = null;
		Drawing drawing = sheet.createDrawingPatriarch();
		CellStyle style = getStyle(wb, true, HSSFColor.BLACK.index, true);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String[] indexs = entry.getKey().split(",");
			String value = entry.getValue()==null?"":entry.getValue().toString();
			if (indexs.length == 2) {
				row = sheet.getRow(Integer.parseInt(indexs[0]));
				cell = row.createCell(Integer.parseInt(indexs[1]));
				if("0.00".equals(value) ||"0.00".equals(value) || "0.0".equals(value)){
				}else{
					if(entry.getValue() instanceof Number){
						cell.setCellValue(Float.parseFloat(format2.format(Double.parseDouble(value))));
					}else{
						cell.setCellValue(value);
					}
				}
				cell.setCellStyle(style);
			}else if (indexs.length == 3){
				row = sheet.getRow(Integer.parseInt(indexs[0]));
				cell = row.createCell(Integer.parseInt(indexs[1]));
				String type = indexs[2];
				if("0.000".equals(value) ||"0.00".equals(value) || "0.0".equals(value)){
				}else{
					try {
						if(type.equals("3Double")){
							cell.setCellValue(format3.format(Double.parseDouble(value)));
						}else{
							cell.setCellValue(Float.parseFloat(format2.format(Double.parseDouble(value))));
						}
					} catch (Exception e) {
						cell.setCellValue(value);
					}
				}
				cell.setCellStyle(style);
			}else if (indexs.length == 4) {
				if (StringUtils.isBlank(value)) {
					// 如果无图
					row = sheet.getRow(Integer.parseInt(indexs[0]));
					cell = row.createCell(Integer.parseInt(indexs[1]));
					cell.setCellValue("没有图片");
					cell.setCellStyle(style);
				} else {
					try {
						// String smallPic = value.substring(0, value.lastIndexOf("/") + 1) + "s_" + value.substring(value.lastIndexOf("/") + 1, value.length());
						String picType = value.substring(value.lastIndexOf(".")).toLowerCase();;
						logger.debug("在"+indexs[0]+"，"+indexs[1]+"，"+indexs[2]+"，"+indexs[3]+"写入图片："+value);
						File urlFile = File.createTempFile(System.currentTimeMillis() + "", picType);
						FileUtils.copyURLToFile(new URL(value), urlFile);
						byte[] bytes = FileUtils.readFileToByteArray(urlFile);
						boolean isPic = true;
						// 类型判断
						Integer pictureIdx = null;
						switch (picType) {
						case ".gif":
							pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
							break;
						case ".jpeg":
							pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_JPEG);
							break;
						case ".jpg":
							pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_JPEG);
							break;
						case ".bmp":
							pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_JPEG);
							break;
						case ".png":
							pictureIdx = wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
							break;
						default:// png
							isPic = false;
							break;
						}
						if(isPic){
							CreationHelper helper = wb.getCreationHelper();
							ClientAnchor anchor = helper.createClientAnchor();
							anchor.setRow1(Integer.parseInt(indexs[0]));
							anchor.setCol1(Integer.parseInt(indexs[1]));
							anchor.setRow2(Integer.parseInt(indexs[2]));
							anchor.setCol2(Integer.parseInt(indexs[3]));
							drawing.createPicture(anchor, pictureIdx);
						}else{
							row = sheet.getRow(Integer.parseInt(indexs[0]));
							cell = row.createCell(Integer.parseInt(indexs[1]));
							cell.setCellValue("附件不是图片！");
							cell.setCellStyle(style);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
