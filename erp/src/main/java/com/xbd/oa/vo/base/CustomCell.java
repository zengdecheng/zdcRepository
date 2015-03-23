package com.xbd.oa.vo.base;

import java.util.Arrays;

import com.xbd.oa.utils.POIUtilsEx;

/**
 * POI 自定义单元格
 * @author fangwei
 */
public class CustomCell {
	public static final String DEFAULT_TYPE ="String"; 	//默认类型
	private Short fontSize; 			// 字体大小
	private String fontName; 			// 字体名称
	private Boolean fontItalic; 		// 是否斜体
	private Short fontColor; 			// 字体颜色
	private Short fontBoldWeight;		// 字体粗细

	private Boolean hasBorder; 			// 是否有边框
	private Short cellColor; 			// 单元格颜色
	private Short[] borderSizes; 		// 边框大小 上下左右位置
	private Short[] borderColors;		// 边框颜色 上下左右位置
	private Boolean isWraptext; 		// 是否自动换行
	private Short alignment; 			// 水平对齐方式
	private Short verticalAlignment;	// 垂直对齐方式
	public Object value; 				// 填充内容
	public String fillType; 			// 填充类型
	public String pattern;				// 显示模板
	
	
	public CustomCell(Object value, String fillType) {
		this();
		this.value = value;
		this.fillType = fillType;
	}
	
	public CustomCell(Object value, String fillType,String pattern) {
		this();
		this.value = value;
		this.fillType = fillType;
		this.pattern = pattern;
	}

	public String getFontHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fontBoldWeight == null) ? 0 : fontBoldWeight.hashCode());
		result = prime * result + ((fontColor == null) ? 0 : fontColor.hashCode());
		result = prime * result + ((fontItalic == null) ? 0 : fontItalic.hashCode());
		result = prime * result + ((fontName == null) ? 0 : fontName.hashCode());
		result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
		return result+"";
	}

	public String getStyleHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + Arrays.hashCode(borderColors);
		result = prime * result + Arrays.hashCode(borderSizes);
		result = prime * result + ((cellColor == null) ? 0 : cellColor.hashCode());
		result = prime * result + ((fontBoldWeight == null) ? 0 : fontBoldWeight.hashCode());
		result = prime * result + ((fontColor == null) ? 0 : fontColor.hashCode());
		result = prime * result + ((fontItalic == null) ? 0 : fontItalic.hashCode());
		result = prime * result + ((fontName == null) ? 0 : fontName.hashCode());
		result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
		result = prime * result + ((hasBorder == null) ? 0 : hasBorder.hashCode());
		result = prime * result + ((isWraptext == null) ? 0 : isWraptext.hashCode());
		result = prime * result + ((verticalAlignment == null) ? 0 : verticalAlignment.hashCode());
		return result+pattern;
	}

	public CustomCell() {
		super();
		this.fontSize = POIUtilsEx.DEFAULT_FONT_SIZE;
		this.fontName = POIUtilsEx.DEFAULT_FONT_NAME;
		this.fontItalic = POIUtilsEx.DEFAULT_FONT_ITALIC;
		this.fontColor = POIUtilsEx.DEFAULT_FONT_COLOR;
		this.fontBoldWeight = POIUtilsEx.DEFAULT_FONT_BOLD_WEIGHT;
		this.hasBorder = POIUtilsEx.DEFAULT_HAS_BORDER;
		this.cellColor = POIUtilsEx.DEFAULT_CELL_COLOR;
		Short[] borderSize = { POIUtilsEx.DEFAULT_BORDER_SIZE };
		this.borderSizes = borderSize;
		Short[] borderColor = { POIUtilsEx.DEFAULT_BORDER_COLOR };
		this.borderColors = borderColor;
		this.isWraptext = POIUtilsEx.DEFAULT_WRAPTEXT;
		this.alignment = POIUtilsEx.DEFAULT_ALIGNMENT;
		this.verticalAlignment = POIUtilsEx.DEFAULT_VERTICALALIGNMENT;
		this.pattern = "";
		this.fillType = DEFAULT_TYPE;
		this.value = "";
	}

	public Short getFontSize() {
		return fontSize;
	}

	public CustomCell setFontSize(Short fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public String getFontName() {
		return fontName;
	}

	public CustomCell setFontName(String fontName) {
		this.fontName = fontName;
		return this;
	}

	public Boolean getFontItalic() {
		return fontItalic;
	}

	public CustomCell setFontItalic(Boolean fontItalic) {
		this.fontItalic = fontItalic;
		return this;
	}

	public Short getFontColor() {
		return fontColor;
	}

	public CustomCell setFontColor(Short fontColor) {
		this.fontColor = fontColor;
		return this;
	}

	public Short getFontBoldWeight() {
		return fontBoldWeight;
	}

	public CustomCell setFontBoldWeight(Short fontBoldWeight) {
		this.fontBoldWeight = fontBoldWeight;
		return this;
	}

	public Boolean getHasBorder() {
		return hasBorder;
	}

	public CustomCell setHasBorder(Boolean hasBorder) {
		this.hasBorder = hasBorder;
		return this;
	}

	public Short getCellColor() {
		return cellColor;
	}

	public CustomCell setCellColor(Short cellColor) {
		this.cellColor = cellColor;
		return this;
	}

	public Short[] getBorderSizes() {
		return borderSizes;
	}

	public CustomCell setBorderSizes(Short[] borderSizes) {
		this.borderSizes = borderSizes;
		return this;
	}
	
	public CustomCell setBorderSizes(Short borderSizes) {
		this.borderSizes = new Short[]{borderSizes};
		return this;
	}

	public Short[] getBorderColors() {
		return borderColors;
	}

	public CustomCell setBorderColors(Short[] borderColors) {
		this.borderColors = borderColors;
		return this;
	}
	public CustomCell setBorderColors(Short borderColors) {
		this.borderColors = new Short[]{borderColors};
		return this;
	}

	public Boolean getIsWraptext() {
		return isWraptext;
	}

	public CustomCell setIsWraptext(Boolean isWraptext) {
		this.isWraptext = isWraptext;
		return this;
	}

	public Short getAlignment() {
		return alignment;
	}

	public CustomCell setAlignment(Short alignment) {
		this.alignment = alignment;
		return this;
	}

	public Short getVerticalAlignment() {
		return verticalAlignment;
	}

	public CustomCell setVerticalAlignment(Short verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public CustomCell setValue(Object value) {
		this.value = value;
		return this;
	}

	public String getFillType() {
		return fillType;
	}

	public CustomCell setFillType(String fillType) {
		this.fillType = fillType;
		return this;
	}

	public String getPattern() {
		return pattern;
	}

	public CustomCell setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}
}
