package com.xbd.oa.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.xbd.oa.utils.FilenameUtils;
import com.xbd.oa.utils.PathUtil;
import com.xbd.oa.utils.WebUtil;

/**
 * <B>功能简述</B><br>
 * 功能详细描述
 * 
 * @author hongliang
 * @see [相关类或方法]
 * @since [产品/模块版本]
 */
public class OaOrderImgUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -8867643021478572634L;

	private static final String EXTENSIONERROR = "extensionerror";
	private static final String ERROR = "error";
	private static final String EMPTY = "empty";
	private static String uploadPath;
	private static String uploadTmp;
	private static File upload;
	private static File tmp;

	public void init(ServletConfig config) throws ServletException{
		uploadPath = PathUtil.getOaPicDir();
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
	
	@Override
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
			String extensionStr = FilenameUtils.getExtension(fileName).toLowerCase();
			fileName = WebUtil.getUUID().concat(".").concat(FilenameUtils.getExtension(fileName));
			if (!"png".equals(extensionStr) && !"gif".equals(extensionStr) && !"jpg".equals(extensionStr) && !"bmp".equals(extensionStr) && !"jpeg".equals(extensionStr)) {
				response.getOutputStream().write(EXTENSIONERROR.getBytes());
				return;
			}
			try {
				FileUtils.copyFile(file,new File(uploadPath, fileName));
				rtnUrl = "{\"fileType\":\"pic\",\"url\":\"" + PathUtil.path2Url(uploadPath.concat(fileName)) + "\",\"path\":\"" + uploadPath.concat(fileName) + "\",\"attachment_name\":\""
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
}
