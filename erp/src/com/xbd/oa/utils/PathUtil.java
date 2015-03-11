package com.xbd.oa.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.use.base.utils.base.FileUtils;

/**
 * <B>功能简述</B><br>
 * 功能详细描述
 * 
 * @author hongliang
 * @see [相关类或方法]
 * @since [产品/模块版本]
 */
public class PathUtil {
	private static int increment = 0;
	private static long lTime = 0;

	private static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(
			"yyyy-MM-dd");

	private PathUtil() {

	}

	/**
	 * 
	 * <B>获取上传的临时路径</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String getTempUploadPath() {
		final String path = ResourceUtil.getString("temp.upload.path");
		return path.replace("@date@", YYYY_MM_DD.format(new Date()));
	}

	/**
	 * 
	 * <B>获取下载的临时路径</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String getTempDownloadPath() {
		final String path = ResourceUtil.getString("tmp.down.path");
		File localPath = new File(PathUtil.getTempUploadPath().replace("@date@", YYYY_MM_DD.format(new Date())));
		if( !localPath.exists() ){
			localPath.mkdir();
		}
		return path.replace("@date@", YYYY_MM_DD.format(new Date()));
	}
	
	/**
	 * 获得工作流文件上传路径
	 * 
	 * @param fileName
	 *            上传时候文件名(包含后缀名)
	 * @return
	 */
	public static String getOaFilePath(String fileName) {
		String dir = getOaFileDir();
		return dir + getUniqueLongId() + "/" + FileUtils.getSuffix(fileName);

	}

	/**
	 * 
	 * <B>返回工作流节点文件上传目录</B><br>
	 * 功能详细描述
	 * 
	 * @author lucky eagle
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String getOaFileDir() {
		String path = ResourceUtil.getString("oa.workflow.path.file");
		return path.replace("@date@", YYYY_MM_DD.format(new Date())).replace("@type@", "file");

	}

	/**
	 * 获得工作流文件上传路径
	 * 
	 * @param fileName
	 *            上传时候文件名(包含后缀名)
	 * @return
	 */
	public static String getOaPicPath(String fileName) {
		String dir = getOaPicDir();
		return dir + getUniqueLongId() + "/" + FileUtils.getSuffix(fileName);

	}

	/**
	 * 
	 * <B>返回工作流节点图片上传目录</B><br>
	 * 功能详细描述
	 * 
	 * @author lucky eagle
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String getOaPicDir() {
		String path = ResourceUtil.getString("oa.workflow.path.pic");
		return path.replace("@date@", YYYY_MM_DD.format(new Date())).replace("@type@", "pic");

	}

	/**
	 * 
	 * <B>本地路径转换为网络路径</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @param path
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String path2Url(String path) {
		if (null != path) {
			final String urlPrefix = ResourceUtil.getString("file.url.prefix");
			final String pathPrefix = ResourceUtil
					.getString("file.path.prefix");
			return path.replace(pathPrefix, urlPrefix);
		}
		return null;
	}

	/**
	 * 
	 * <B>网络路径转换为本地路径</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @param url
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String url2Path(String url) {
		if (null != url) {
			final String urlPrefix = ResourceUtil.getString("file.url.prefix");
			final String pathPrefix = ResourceUtil.getString("file.path.prefix");
			final String path = pathPrefix.concat(url.replace(urlPrefix, ""));
			if (path.startsWith("c:/")) {
				return path;
			}
			return path.startsWith("/") ? path : "/".concat(path);
		}
		return null;

	}

	/**
	 * <B>生成临时的csv文件</B><br>
	 * @param args
	 */
	public static String getTmpDownCsvPath() {
		final String path = ResourceUtil.getString("tmp.down.path").replace("@date@", YYYY_MM_DD.format(new Date()));
		File localPath = new File(path);
		if( !localPath.exists() ){
			localPath.mkdirs();
		}
		return path + System.currentTimeMillis() + ".csv";
	}
	
	
	/**
	 * 
	 * <B>网络路径转换为文件名</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @param url
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String url2FileName(String url) {
		if (null != url) {
			int index = url.lastIndexOf("/") + 1;
			int length = url.length();
			if (index > 0) {
				return url.substring(index, length);
			}
			return url;
		}
		return null;
	}

	// 唯一数生成方法
	public synchronized static long getUniqueLongId() {
		// long iCheck = Math.round(Math.random() * 10000);

		long lTmpTime = System.currentTimeMillis();
		if (lTime != lTmpTime) {
			lTime = lTmpTime;
			increment = 0;
		} else
			increment++;

		return Long.valueOf("" + lTime + increment);
	}

	// 顺序数生成方法
	public synchronized static long getUniqueId(int lmt_length) {
		// long iCheck = Math.round(Math.random() * 10000);
		long lTmpTime = (long) Math.pow(10, lmt_length);
		if (lTime >= lTmpTime) {
			lTime = 0;
		}
		return ++lTime;
	}

}
