package com.xbd.oa.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xbd.oa.utils.ResourceUtil;

/**
 * 
 * @author zh
 * 
 */
public class OaOrderFileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -8867643021478572634L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//String fileUrl = new String(request.getParameter("fileUrl").getBytes("iso8859-1"), "utf8");
			String fileUrl = new String(request.getParameter("fileUrl"));
			final String urlPrefix = ResourceUtil.getString("file.url.prefix");
			final String pathPrefix = ResourceUtil.getString("file.path.prefix");

			// path是指欲下载的文件的路径
			String path = fileUrl.replaceAll(urlPrefix, pathPrefix);
			File file = new File(path);
			String filename = file.getName();// 取得文件名

			// 设置response的Header
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());

			// 以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			while (fis.read(buffer) != -1) {
				toClient.write(buffer);
			}
			fis.close();
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
