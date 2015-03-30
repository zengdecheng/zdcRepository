package com.xbd.erp.base.pojo.sys;


public class PageControls {
	private long intRowCount = 0; // 记录数据总条数
	private long intPageCount = 0; // 记录数据总页数
	private long intCurrentPageNo = 1; // 记录数据当前页数
	private int intPageSize = 20; // 记录每页显示的数据数
	private boolean paginationFlag;

	public PageControls() {}

	public PageControls(FSPBean fsp, long count) {
		this(fsp, count, true);
	}

	public PageControls(FSPBean fsp, long count, boolean paginationFlag) {
		int strCurrentPageNo = fsp.getPageNo(); // 取得待显示页码
		int strPageSize = fsp.getPageSize(); // 取得每页显示的数据数
		// 设定数据总条数
		intRowCount = count;
		// 设定数据当前页数
		intCurrentPageNo = (new Integer(strCurrentPageNo)).intValue();
		// 设定每页显示的数据数
		intPageSize = (new Integer(strPageSize)).intValue();
		// 设定数据总页数
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		// 如果当前页码大于设定的数据总页数，则更新当前页码为最大的数据页码
		if (intCurrentPageNo > intPageCount)
			intCurrentPageNo = intPageCount;
		this.paginationFlag = paginationFlag;
	}

	public String writePageInfo() {
		return writePageInfo("");
	}

	/**
	 * 返回BaseTable,用于执行选择数据读取操作
	 * 
	 * @param request
	 *            HttpServletRequest 页面请求对象
	 * @param tableObject
	 *            选取的当前页的List数据
	 * @return BaseTable
	 */

	public String writePageInfo(String pageLink) {
		String pageInfo = "";
		if (!paginationFlag) {
			return pageInfo;
		}

		// 如果链接串中不含"?"符号，如:pageControl.writePageInfo("rdbloglist.jsp")，则需手工在后边添加，以免下面拼凑出的字符串出现HTML语法错误
		if (pageLink.indexOf("?") == -1)
			pageLink = pageLink + "?1=1";

		if (intRowCount > 0) {
			pageInfo = "数据总数：" + intRowCount + "&nbsp;&nbsp;&nbsp;&nbsp;第" + intCurrentPageNo + "页/共" + intPageCount + "页\n";

			if (intCurrentPageNo != 1) {
				pageInfo = pageInfo + "&nbsp;&nbsp;<a href=\"#\" title='点击显示首页数据' onclick=\"document.getElementById('fsp.pageSize').value=" + intPageSize + ";document.getElementById('fsp.pageNo').value=1;document.forms[0].submit();\">" + "<img src='images/icon_page_frist.gif' border='0' /></a>\n";
			}

			if (intCurrentPageNo > 1) {
				pageInfo = pageInfo + "&nbsp;&nbsp;<a href=\"#\" title='点击显示前一页数据' onclick=\"document.getElementById('fsp.pageSize').value=" + intPageSize + ";document.getElementById('fsp.pageNo').value=" + String.valueOf(intCurrentPageNo - 1) + ";document.forms[0].submit();\"><img src='images/icon_page_prev.gif' border='0' /></a>\n";
			}

			if (intCurrentPageNo < intPageCount) {
				pageInfo = pageInfo + "&nbsp;&nbsp;<a href=\"#\" title='点击显示下一页数据' onclick=\"document.getElementById('fsp.pageSize').value=" + intPageSize + ";document.getElementById('fsp.pageNo').value=" + String.valueOf(intCurrentPageNo + 1) + ";document.forms[0].submit();\"><img src='images/icon_page_next.gif' border='0' /></a>\n";
			}

			if (intCurrentPageNo < intPageCount) {
				pageInfo = pageInfo + "&nbsp;&nbsp;<a href=\"#\" title='点击显示尾页数据' onclick=\"document.getElementById('fsp.pageSize').value=" + intPageSize + ";document.getElementById('fsp.pageNo').value=" + intPageCount + ";document.forms[0].submit();\"><img src='images/icon_page_last.gif' border='0' /></a>\n";
			}

			pageInfo = pageInfo + "<script language='javascript'>\n";
			pageInfo = pageInfo + "function gotoPage(objGoButton)\n";
			pageInfo = pageInfo + "{\n";
			pageInfo = pageInfo + "    objPageSize = document.getElementById('fsp.pageSize')\n";
			pageInfo = pageInfo + "    objPageNo = document.getElementById('fsp.pageNo')\n";
			pageInfo = pageInfo + "    if(objPageSize.value.match(/\\d+/) != objPageSize.value)\n";
			pageInfo = pageInfo + "    {\n";
			pageInfo = pageInfo + "        alert('请输入正整数数字！');\n";
			pageInfo = pageInfo + "        objPageSize.select();\n";
			pageInfo = pageInfo + "        objPageSize.focus();\n";
			pageInfo = pageInfo + "        return;\n";
			pageInfo = pageInfo + "    }\n";
			pageInfo = pageInfo + "    if(objPageNo.value.match(/\\d+/) != objPageNo.value)\n";
			pageInfo = pageInfo + "    {\n";
			pageInfo = pageInfo + "        alert('请输入正整数数字页码！');\n";
			pageInfo = pageInfo + "        objPageNo.select();\n";
			pageInfo = pageInfo + "        objPageNo.focus();\n";
			pageInfo = pageInfo + "        return;\n";
			pageInfo = pageInfo + "    }\n";
			pageInfo = pageInfo + "    if(objPageNo != null &&  document.getElementById('intPageCount') != null)\n";
			pageInfo = pageInfo + "    {\n";
			pageInfo = pageInfo + "    var pageMax = (new Number(objPageSize.value)).valueOf();\n";
			pageInfo = pageInfo + "    var recordCount = (new Number(document.getElementById('fsp.recordCount').value)).valueOf();\n";
			pageInfo = pageInfo + "      if ((new Number(objPageNo.value)).valueOf() > (recordCount/pageMax+1)) {\n";
			pageInfo = pageInfo + "        alert('您输入的页码大于最大页码！\\n请重新输入页码！');\n";
			pageInfo = pageInfo + "        objPageNo.select();\n";
			pageInfo = pageInfo + "        objPageNo.focus();\n";
			pageInfo = pageInfo + "        return;\n";
			pageInfo = pageInfo + "      }\n";
			pageInfo = pageInfo + "    }\n";
			pageInfo = pageInfo + "    objGoButton.disabled=true;\n";
			pageInfo = pageInfo + "    \n";
			pageInfo = pageInfo + "    document.forms[0].submit();\n";
			// pageInfo = pageInfo + "    window.location.href=\"" + pageLink +
			// "&pageNo=\" + objPageNo.value + \"&pageSize=\" + objPageSize.value;\n";
			pageInfo = pageInfo + "}\n";
			pageInfo = pageInfo + "</script>\n";
			pageInfo = pageInfo + "&nbsp;&nbsp;每页显示<input type='text' size=3 name='fsp.pageSize' id='fsp.pageSize' value='" + intPageSize + "' class='Input'>条\n";
			if (intCurrentPageNo < intPageCount) {
				pageInfo = pageInfo + "&nbsp;&nbsp;转到第<input type='text' size=4 name='fsp.pageNo' id='fsp.pageNo' value='" + String.valueOf(intCurrentPageNo) + "' class='Input'>页\n";
			} else {
				pageInfo = pageInfo + "&nbsp;&nbsp;转到第<input type='text' size=4 name='fsp.pageNo' id='fsp.pageNo' value='" + intCurrentPageNo + "' class='Input'>页\n";
			}
			pageInfo = pageInfo + "<input type=\"button\" class=\"Buttonshortest\" id=\"buttonGotoPage\" name=\"buttonGotoPage\" " + "onclick='gotoPage(this)' title=\"点击跳转到指定的页面\" value=\"GO\">\n";

			pageInfo = pageInfo + "<input type=\"hidden\" name=\"intPageCount\" id=\"intPageCount\" value=\"" + intPageCount + "\">\n";
			pageInfo = pageInfo + "<input type=\"hidden\" name=\"pageFlag\"  id=\"pageFlag\" value=\"1\">\n";
			pageInfo = pageInfo + "<input type=\"hidden\" name=\"fsp.recordCount\"  id=\"fsp.recordCount\"  value=\"" + intRowCount + "\">\n";

		} else
			pageInfo = "数据总数：0";

		return pageInfo;
	}

	/**
	 * 返回数据当前页数
	 * 
	 * @return String
	 */
	public String getCurrentPageNo() {
		return String.valueOf(this.intCurrentPageNo);
	}

	/**
	 * 返回数据总页数
	 * 
	 * @return String
	 */
	public String getPageCount() {
		return String.valueOf(this.intPageCount);
	}

	/**
	 * 返回当前每页显示的数据数
	 * 
	 * @return String
	 */
	public String getPageSize() {
		return String.valueOf(this.intPageSize);
	}

	/**
	 * 返回数据总条数
	 * 
	 * @return String
	 */
	public String getRowCount() {
		return String.valueOf(this.intRowCount);
	}

}
