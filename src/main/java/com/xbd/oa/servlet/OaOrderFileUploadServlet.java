package com.xbd.oa.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// String name = request.getParameter("name");
		// if (null != name && !"".equals(name)) {
		// name = name.concat("-");
		// } else {
		// name="new-";
		// }

		// 上传文件的存放路径
		final String uploadPath = PathUtil.getOaFileDir();
		final String uploadTmp = PathUtil.getTempUploadPath();

		final File upload = new File(uploadPath);
		if (!upload.exists()) {
			upload.mkdirs();
		}

		final File tmp = new File(uploadTmp);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}

		final DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(1024 * 8);// 设置缓冲区大小，这里是8kb
		diskFileItemFactory.setRepository(tmp);// 设置缓冲区目录

		final ServletFileUpload sfu = new ServletFileUpload(diskFileItemFactory);
		sfu.setHeaderEncoding("utf-8");
		sfu.setSizeMax(1024 * 1024 * 10);// 10M
		List<FileItem> fileItems = new ArrayList<FileItem>();
		try {
			fileItems = sfu.parseRequest(request);
			if (fileItems.size() == 0) {
				response.getOutputStream().write(EMPTY.getBytes());
				return;
			}
		} catch (FileUploadException e) {
			response.getOutputStream().write(ERROR.getBytes());
			e.printStackTrace();
			return;
		}

		String rtnUrl = "";
		final Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			final FileItem item = i.next();
			String fileName = item.getName();
			if (fileName != null && fileName.trim().length() > 0) {
				fileName = FilenameUtils.getName(fileName);
				Date now = new Date();
				// fileName = (name + now.getTime()).concat(".").concat(FilenameUtils.getExtension(fileName));
				// fileName = WebUtil.getUUID().concat(".").concat(FilenameUtils.getExtension(fileName));
				fileName = FilenameUtils.removeExtension(fileName).concat("-").concat("" + now.getTime()).concat(".").concat(FilenameUtils.getExtension(fileName));
				final File savedFile = new File(uploadPath, fileName);
				try {
					item.write(savedFile);
					final FileInputStream fis = new FileInputStream(savedFile);
					rtnUrl = "{\"fileType\":\"file\",\"url\":\"" + PathUtil.path2Url(uploadPath.concat(fileName)) + "\",\"path\":\"" + uploadPath.concat(fileName) + "\",\"attachment_name\":\""
							+ fileName + "\",\"fileName\":\"" + item.getName() + "\"}";
				} catch (Exception e) {
					response.getOutputStream().write(ERROR.getBytes());
					e.printStackTrace();
					return;
				}
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
