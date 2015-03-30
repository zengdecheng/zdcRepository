package com.xbd.oa.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.xbd.oa.utils.FilenameUtils;
import com.xbd.oa.utils.PathUtil;

/**
 * <B>功能简述</B><br>
 * 功能详细描述
 * 
 * @author hongliang
 * @see [相关类或方法]
 * @since [产品/模块版本]
 */
public class OaOrderFileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -8867643021478572634L;

	private static final String ERROR = "error";
	private static final String EMPTY = "empty";
	private static String uploadPath;
	private static String uploadTmp;
	private static File upload;
	private static File tmp;
	
	
	public void init(ServletConfig config) throws ServletException{
		uploadPath = PathUtil.getOaFileDir();
		uploadTmp = PathUtil.getTempUploadPath();

		upload = new File(uploadPath);
		if (!upload.exists()) {
			upload.mkdirs();
		}
		tmp = new File(uploadTmp);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request;   
		File file = wrapper.getFiles("Filedata")[0];
		String fileName = wrapper.getFileNames("Filedata")[0];
		if (file == null) {
			response.getOutputStream().write(EMPTY.getBytes());
			return;
		}

		String rtnUrl = "";
		if (fileName != null && fileName.trim().length() > 0) {
			fileName = FilenameUtils.getName(fileName);
			Date now = new Date();
			fileName = FilenameUtils.removeExtension(fileName).concat("-").concat("" + now.getTime()).concat(".").concat(FilenameUtils.getExtension(fileName));
			try {
				FileUtils.copyFile(file,new File(uploadPath, fileName));
				rtnUrl = "{\"fileType\":\"file\",\"url\":\"" + PathUtil.path2Url(uploadPath.concat(fileName)) + "\",\"path\":\"" + uploadPath.concat(fileName) + "\",\"attachment_name\":\""
						+ fileName + "\",\"fileName\":\"" + fileName + "\"}";
			} catch (Exception e) {
				response.getOutputStream().write(ERROR.getBytes());
				e.printStackTrace();
				return;
			}
		}

		response.getOutputStream().write("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">".getBytes());
		if (rtnUrl.length() > 0) {
			response.getOutputStream().write(rtnUrl.getBytes("UTF-8"));
		} else {
			response.getOutputStream().write(EMPTY.getBytes());
		}
		return;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
