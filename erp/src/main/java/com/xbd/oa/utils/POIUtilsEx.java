package com.xbd.oa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.util.Util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xbd.oa.vo.CustomCell;

/**
 * @author fangwei 2015-01-30
 * POI 填充内容<br>
 * fillInfo<br>
 * |- (fileUrl:fileUrl) excel模板地址<br>
 * |- (sheetNames:name1,name2...) sheet名称<br>
 * |- (name1FileInfo:map) name1填充信息<br>
 * |- (name1DynaRow:map) name1动态增行(40,5)<br>
 * |- (name1MergeCell:map) name1合并单元格("行1,行2,列1,列2",true)<br>
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
public class POIUtilsEx {
	private static final Logger logger = Logger.getLogger(POIUtilsEx.class);

	/**
	 * DEBUG模式
	 */
	private static final boolean isDebug = true;									// debug模式
	private static final boolean zeroBlank = false;									// 0是否显示	
	private static final String DEBUG_WARN = "warn"; 								// DEBUG 模式
	private static final String DEBUG_INFO = "info"; 								// DEBUG 模式
	private static final String DEBUG_ERROR = "error"; 								// DEBUG 模式
	private static final String debugMode = DEBUG_ERROR;
	private static CustomCell defaultCells;											//默认样式
	
	
	/**
	 * 初始化参数
	 */
	public static final Short DEFAULT_FONT_SIZE = 11; 								// 默认字体大小
	public static final String DEFAULT_FONT_NAME = "宋体"; 							// 默认字体
	public static final Boolean DEFAULT_FONT_ITALIC = false; 						// 默认非斜体
	public static final Short DEFAULT_FONT_BOLD_WEIGHT = Font.BOLDWEIGHT_NORMAL; 	// 默认字体粗细
	public static final Short DEFAULT_FONT_COLOR = IndexedColors.BLACK.index; 		// 默认字体颜色
	public static final Boolean DEFAULT_HAS_BORDER = true;							// 默认是否有边框

	public static final Short DEFAULT_CELL_COLOR = IndexedColors.WHITE.index; 		// 单元格颜色
	public static final Short DEFAULT_BORDER_SIZE = CellStyle.BORDER_THIN; 			// 默认边框粗细
	public static final Short DEFAULT_BORDER_COLOR = IndexedColors.BLACK.index; 	// 默认边框颜色
	public static final Boolean DEFAULT_WRAPTEXT = true; 							// 默认换行
	public static final Short DEFAULT_ALIGNMENT = CellStyle.ALIGN_CENTER; 			// 默认水平对齐方式
	public static final Short DEFAULT_VERTICALALIGNMENT = CellStyle.VERTICAL_CENTER;// 默认垂直对齐方式
	
	private static final Map<String, Font> fontCacheMap = new HashMap<String, Font>(); 				// POI 字体缓存
	private static final Map<String, CellStyle> styleCacheMap = new HashMap<String, CellStyle>(); 	// POI 样式缓存

	private static CustomCell getDefaultCells(){
		if(defaultCells == null){
			defaultCells = new CustomCell();
		}
		return defaultCells;
	}
	
	/**
	 * 清空缓存
	 */
	private static void clearCache() {
		fontCacheMap.clear();
		styleCacheMap.clear();
	}

	/**
	 * 日志写入情况
	 * @param message
	 * @param mode
	 */
	private static void debug(String message, String mode) {
		if (isDebug) {
			if (mode.equals(DEBUG_ERROR)) {
				logger.error(message);
			} else if (mode.equals(DEBUG_WARN)) {
				logger.warn(message);
			} else if (mode.equals(DEBUG_INFO)) {
				logger.info(message);
			}
		}
	}

	/**
	 * 设置上下左右边框颜色
	 * 
	 * @param style
	 * @param borderColor
	 * @return
	 */
	private static CellStyle setBorderColor(CellStyle style, short borderColor) {
		style.setTopBorderColor(borderColor);
		style.setBottomBorderColor(borderColor);
		style.setLeftBorderColor(borderColor);
		style.setRightBorderColor(borderColor);
		return style;
	}

	/**
	 * 分别设置上下左右边框颜色
	 * 
	 * @param style
	 * @param topBorderColor
	 * @param bottomBorderColor
	 * @param leftBorderColor
	 * @param rightBorderColor
	 * @return
	 */
	private static CellStyle setBorderColor(CellStyle style, short topBorderColor, short bottomBorderColor, short leftBorderColor, short rightBorderColor) {
		style.setTopBorderColor(topBorderColor);
		style.setBottomBorderColor(bottomBorderColor);
		style.setLeftBorderColor(leftBorderColor);
		style.setRightBorderColor(rightBorderColor);
		return style;
	}

	/**
	 * 设置上下左右边框的样式
	 * 
	 * @param style
	 * @param borderStyle
	 * @return
	 */
	private static CellStyle setBorderStyle(CellStyle style, short borderStyle) {
		style.setBorderTop(borderStyle);
		style.setBorderBottom(borderStyle);
		style.setBorderLeft(borderStyle);
		style.setBorderRight(borderStyle);
		return style;
	}

	/**
	 * 分别设置上下左右边框样式
	 * 
	 * @param style
	 * @param borderTopStyle
	 * @param borderBottomStyle
	 * @param borderLeftStyle
	 * @param borderRightStyle
	 * @return
	 */
	private static CellStyle setBorderStyle(CellStyle style, short borderTopStyle, short borderBottomStyle, short borderLeftStyle, short borderRightStyle) {
		style.setBorderTop(borderTopStyle);
		style.setBorderBottom(borderBottomStyle);
		style.setBorderLeft(borderLeftStyle);
		style.setBorderRight(borderRightStyle);
		return style;
	}

	/**
	 * 得到字体
	 * @param wb
	 * @return
	 */
	private static Font getFont(Workbook wb, CustomCell customCell) {
		String fontHashCode = customCell.getFontHashCode();
		Font font = fontCacheMap.get(fontHashCode);
		if(font == null){
			font = wb.createFont();
			font.setFontHeightInPoints(customCell.getFontSize());
			font.setFontName(customCell.getFontName());
			font.setItalic(customCell.getFontItalic());
			font.setColor(customCell.getFontColor());
			font.setBoldweight(customCell.getFontBoldWeight());
			fontCacheMap.put(fontHashCode,font);
		}
		return font;
	}
	
	/**
	 * 得到格式化对象
	 * @param wb
	 * @param cc
	 * @param pattern
	 * @return
	 */
	private static CellStyle getDataFormat(Workbook wb,CustomCell cc, String pattern) {
		CellStyle cs = getStyle(wb,cc,pattern);
		cs.setDataFormat(wb.createDataFormat().getFormat(pattern));
		return cs;
	}
	
	/**
	 * 得到单元格样式
	 * @param wb
	 * @param customCell
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static CellStyle getStyle(Workbook wb, CustomCell customCell,String pattern) {
		if(customCell != null){
			String styleHashCode = customCell.getStyleHashCode()+pattern;
			CellStyle style = styleCacheMap.get(styleHashCode);
			if(style == null){
				//1.初始化字体
				Font font = getFont(wb, customCell);
				style = wb.createCellStyle();
				if (customCell != null) {
					//2.初始化边框
					if (customCell.getHasBorder()) {
						Short[] borderSizes = customCell.getBorderSizes();
						if (borderSizes != null) {
							if (borderSizes.length == 1) {
								setBorderStyle(style, borderSizes[0]);
							} else if (borderSizes.length == 4) {
								setBorderStyle(style, borderSizes[0], borderSizes[1], borderSizes[2], borderSizes[3]);
							}
						}
						Short[] borderColors = customCell.getBorderColors();
						if (borderColors != null) {
							if (borderColors.length == 1) {
								setBorderColor(style, borderColors[0]);
							} else if (borderColors.length == 4) {
								setBorderColor(style, borderColors[0], borderColors[1], borderColors[2], borderColors[3]);
							}
						}
					}else{
						setBorderStyle(style,style.BORDER_NONE);
						setBorderColor(style,style.BORDER_NONE);
					}
					//3.初始化cell颜色
					style.setFillBackgroundColor(customCell.getCellColor());// 设置背景色
					style.setFillForegroundColor(customCell.getCellColor());// 设置前景色
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					
					//4.初始化单元格样式
					style.setWrapText(customCell.getIsWraptext());
					style.setAlignment(customCell.getAlignment());
					style.setVerticalAlignment(customCell.getVerticalAlignment());
				}
				style.setFont(font);
				if(StringUtils.isNotBlank(pattern)){
					DataFormat format = wb.createDataFormat();
					style.setDataFormat(format.getFormat(pattern));
				}
				styleCacheMap.put(styleHashCode,style);
			}
			return style;
		}else{
			return null;
		}
	}

	/**
	 * 添加行
	 * @param sheet
	 * @param startRow
	 * @param rows
	 */
	private static void insertRow(Sheet sheet, int startRow, int rows) {
		sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows, true, false);
		for (int i = 0; i < rows; i++) {
			Row sourceRow = null;
			Row targetRow = null;
			sourceRow = sheet.getRow(startRow);
			targetRow = sheet.createRow(++startRow);
			Util.copyRow(sheet, sourceRow, targetRow);
		}
	}

	/**
	 * 动态增减行
	 */
	private static void dynaRow(Sheet sheet, Map<String, Object> dynaRow) {
		if (!dynaRow.isEmpty()) {
			for (Map.Entry<String, Object> entry : dynaRow.entrySet()) {
				int addIndex = Integer.parseInt(entry.getKey().toString());
				int addNum = Integer.parseInt(entry.getValue().toString());
				insertRow(sheet, addIndex, addNum);
				debug("复制第"+addIndex+"行，插入到"+addIndex+"行后"+addNum+"次", debugMode);
			}
		}
	}
	
	/**
	 * 合并单元格
	 */
	private static void mergedRegion(Sheet sheet, Map<String, Object> mergeCell) {
		if (!mergeCell.isEmpty()) {
			for (Map.Entry<String, Object> entry : mergeCell.entrySet()) {
				String keys = (String) entry.getKey();
				Boolean value= (Boolean) entry.getValue();
				String key[] = keys.split(",");
				sheet.addMergedRegion(new CellRangeAddress(Integer.parseInt(key[0]), Integer.parseInt(key[1]), Integer.parseInt(key[2]), Integer.parseInt(key[3])));
				if(value){
					debug("合并单元格： "+keys, debugMode);
				}
			}
		}
	}	
	
	/**
	 * EXCEL生成
	 * @param os
	 * @param fillInfo
	 */
	@SuppressWarnings("unchecked")
	public static void processExcel(OutputStream os, Map<String, Object> fillInfo) {
		boolean is2007 = false;
		Workbook wb = null;
		String file = (String) fillInfo.get("fileUrl");
		debug("开始写入Excel...", debugMode);
		if (!new File(file).exists()) {
			throw new RuntimeException("the attachment in server [" + file + "]is not exists!");
		}
		if (file.endsWith("xlsx")) {
			is2007 = true;
		}
		try {
			InputStream input = new FileInputStream(file);
			if (is2007) {
				debug("创建XSSFWorkbook...", debugMode);
				wb = new XSSFWorkbook(input);
			} else {
				debug("创建HSSFWorkbook...", debugMode);
				wb = new HSSFWorkbook(input);
			}
			for(int i=0;i<wb.getNumberOfSheets();i++){
				wb.setSheetHidden(i, 2);
			}
			if(fillInfo!=null && fillInfo.size()>0){
				String sheetNames = (String) fillInfo.get("sheetNames");
				if(StringUtils.isNotBlank(sheetNames)){
					String[] sheets = sheetNames.split(",");
					for(String sheetName: sheets){
						debug("开始得到目标Sheet"+sheetName+"...",debugMode);
						Sheet sheet = wb.getSheet(sheetName);
						wb.setSheetHidden(wb.getSheetIndex(sheetName), 0);
						if(sheet !=null){
							Map<String,Object> fileInfo = (Map<String, Object>) fillInfo.get(sheetName+"FileInfo");
							Map<String,Object> dynaRow = (Map<String, Object>) fillInfo.get(sheetName+"DynaRow");
							Map<String,Object> mergeCell = (Map<String, Object>) fillInfo.get(sheetName+"MergeCell");
							//1.动态增行
							if(dynaRow !=null) dynaRow(sheet,dynaRow);
							//2.合并单元格
							if(mergeCell !=null) mergedRegion(sheet,mergeCell);
							//3.内容填充
							if(fileInfo !=null) excelFilling(wb, sheet, fileInfo);
							//4.强制计算
							sheet.setForceFormulaRecalculation(true);
						}else{
							debug("得不到"+file+"中的sheet:"+sheetName, debugMode);
						}
					}
				}
			}
			wb.write(os);
			debug("EXCEL成功生成...", debugMode);
			clearCache();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clearCache();
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
	 * @param cell  单元格
	 * @param style	样式
	 * @param value	值
	 * @param type	填充类型
	 */
	private static void fillCell(Workbook wb,Cell cell,CustomCell cc,CellStyle style,Object value,String type){
		if(type.startsWith("Date")){
			try {
				Date date = (Date) value;
				if(type.indexOf("-") == -1){
					cell.setCellValue(date);
					cell.setCellStyle(getDataFormat(wb,cc,DateUtil.ALL_24H));
				}else{
					String dateFormat =type.substring(type.indexOf("-")+1);
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					cell.setCellValue(sdf.format(value));
					cell.setCellStyle(style);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.startsWith("Calendar")){
			try {
				Calendar c  = (Calendar) value;
				if(type.indexOf("-") == -1){
					cell.setCellValue(c);
					cell.setCellStyle(getDataFormat(wb,cc,DateUtil.ALL_24H));
				}else{
					String dateFormat =type.substring(type.indexOf("-")+1);
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					cell.setCellValue(sdf.format(((Calendar)value).getTime()));
					cell.setCellStyle(style);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.startsWith("Double")){
			try {
				Double d;
				try {
					d = (Double) value;
				} catch (Exception e) {
					if(value.equals("")){
						d = 0D;
					}else{
						d = Double.parseDouble(value.toString());
					}
				}
				if(zeroBlank){
					if(type.indexOf("-") == -1){
						cell.setCellValue(d);
						cell.setCellStyle(getDataFormat(wb,cc,"0.##"));
					}else{
						String decimalFormat =type.substring(type.indexOf("-")+1);
						DecimalFormat df=new DecimalFormat(decimalFormat);
						cell.setCellValue(df.format(d));
						cell.setCellStyle(style);
					}
				}else{
					if ("0".equals(d.toString()) || "0.00".equals(d.toString()) || "0.0".equals(d.toString())) {
						cell.setCellStyle(style);
					} else {
						if(type.indexOf("-") == -1){
							cell.setCellValue(d);
							cell.setCellStyle(getDataFormat(wb,cc,"0.##"));
						}else{
							String decimalFormat =type.substring(type.indexOf("-")+1);
							DecimalFormat df=new DecimalFormat(decimalFormat);
							cell.setCellValue(df.format(d));
							cell.setCellStyle(style);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.startsWith("Integer")){
			try {
				Integer d;
				try {
					d = (Integer) value;
				} catch (Exception e) {
					if(value.equals("")){
						d = 0;
					}else{
						d = Integer.parseInt(value.toString());
					}
				}
				if(zeroBlank){
					cell.setCellValue(d);
					cell.setCellStyle(style);
				}else{
					if ("0".equals(d.toString())) {
						cell.setCellStyle(style);
					} else {
						cell.setCellValue(d);
						cell.setCellStyle(style);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.startsWith("Boolean")){
			try {
				Boolean b = (Boolean) value;
				cell.setCellValue(b);
				cell.setCellStyle(style);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.startsWith("Expression")){
			try {
				cell.setCellFormula((String)value);
				if(StringUtils.isNotBlank(cc.getPattern())){
					cell.setCellStyle(getDataFormat(wb,cc,cc.getPattern()));
				}else{
					cell.setCellStyle(style);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{//String 字符串
			try {
				cell.setCellValue(value.toString());
				cell.setCellStyle(style);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 填充EXCEL内容
	 * @param wb
	 * @param sheet
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private static void excelFilling(Workbook wb, Sheet sheet, Map<String, Object> map) throws Exception {
		debug("开始填充单元格内容...", debugMode);
		Row row = null;
		Cell cell = null;
		Drawing drawing = sheet.createDrawingPatriarch();
		CellStyle style = getStyle(wb, getDefaultCells(),"");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String[] indexs = entry.getKey().split(",");
			Object value = entry.getValue();
			row = sheet.getRow(Integer.parseInt(indexs[0]));
			if(row == null) {
				row = sheet.createRow(Integer.parseInt(indexs[0]));
			}
			cell = row.createCell(Integer.parseInt(indexs[1]));
			if(!value.getClass().equals(CustomCell.class)){
				if(indexs.length == 2){												//String类型
					fillCell(wb, cell ,getDefaultCells(), style, value, "String");
				}if(indexs.length == 3){											//自定义类型
					fillCell(wb, cell, getDefaultCells(),style, value, indexs[2]);
				}if(indexs.length == 4){
					String picUrl = (String)value;
					if (StringUtils.isBlank(picUrl)) {								// 如果无图
						cell.setCellValue("无图");
						cell.setCellStyle(style);
					}else{
						try {
							String picType = (picUrl.substring(picUrl.lastIndexOf("."))).toLowerCase();
							debug("在" + indexs[0] + "，" + indexs[1] + "，" + indexs[2] + "，" + indexs[3] + "写入图片：" + value,debugMode);
							File urlFile = File.createTempFile(System.currentTimeMillis() + "", picType);
							FileUtils.copyURLToFile(new URL(picUrl), urlFile);
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
							if (isPic) {
								CreationHelper helper = wb.getCreationHelper();
								ClientAnchor anchor = helper.createClientAnchor();
								anchor.setRow1(Integer.parseInt(indexs[0]));
								anchor.setCol1(Integer.parseInt(indexs[1]));
								anchor.setRow2(Integer.parseInt(indexs[2]));
								anchor.setCol2(Integer.parseInt(indexs[3]));
								drawing.createPicture(anchor, pictureIdx);
							} else {
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
			}else{
				CustomCell customCell = (CustomCell) value;
				fillCell(wb, cell,customCell, getStyle(wb, customCell,""), customCell.getValue(), customCell.getFillType());
			}
		}
	}
}
