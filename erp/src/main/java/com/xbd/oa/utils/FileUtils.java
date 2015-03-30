package com.xbd.oa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class FileUtils {
	public static void createAbsoluteDir(String dinPath) {
		String fname = "";
		File f = null;
		char[] c = dinPath.toCharArray();
		for (char d : c) {
			if (d == '\\' || d == '/') {
				f = new File(fname);
				if (!f.exists()) {
					f.mkdir();
				}

			}
			fname = fname + d;
		}
		// String[] dirs = dinPath.split("\\\\");
		// for (int i = 0; i < dirs.length - 1; i++) {
		// fname = fname + "\\" + dirs[i];
		// f = new File(fname);
		// if (!f.exists()) {
		// f.mkdir();
		// }
		//
		// }
		// System.out.println(System.getProperty("user.dir"));
	}

	public static byte[] readFile(String filePath) {
		byte bf[] = null;
		try {
			FileInputStream fs = new FileInputStream(filePath);
			int len = fs.available();
			bf = new byte[len];
			fs.read(bf);
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return bf;
	}
	/**
	 * 把一个已知的文件读成一个字符串
	 * @param filePath	文件路径
	 * @param splitSign 分割标识(每一段的分割标识)
	 * @return
	 */
	public static String readFileByStr(String filePath,String splitSign){
		try {
			StringBuffer buf = new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String strLine = br.readLine();
			while (strLine != null) {
				buf.append(strLine);
				if(splitSign != null)
				buf.append(splitSign);
				strLine = br.readLine();
			}
			br.close();
			return buf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *@param filePath
	 *@param splitSign
	 *@param encoding
	 *@return
	 *@author fuying
	 *@date 2009-3-30 下午06:15:40
	 *@comment 输入文件绝对地址和文件分割符，以及编码方式 得到读出的字符流
	 */
	public static String readFileByStr(String filePath,String splitSign,String encoding){
		BufferedReader br = null;
		try {
			StringBuffer buf = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),encoding));
			String strLine = br.readLine();
			while (strLine != null) {
				buf.append(strLine);
				if(splitSign != null)
				buf.append(splitSign);
				strLine = br.readLine();
			}
			return buf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static byte[] readFile(File file) {
		byte bf[] = null;
		FileInputStream fs = null;
		try {
			 fs = new FileInputStream(file);
			int len = fs.available();
			bf = new byte[len];
			fs.read(bf);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			if(fs != null){
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bf;
	}

	public static void writeFile(byte[] b, String dinPath) {
		FileOutputStream out = null;
		try {
			createAbsoluteDir(dinPath);
			 out = new FileOutputStream(dinPath);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 拷贝资源文件到指定目录下
	 * 
	 * @param srcFile
	 *            资源文件源
	 * @param dinPath
	 *            目标文件绝对地址
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, String dinPath) {
		FileInputStream input = null;
		FileOutputStream output = null;

		try {
			input = new FileInputStream(srcFile);
			// 建立目标路径的文件夹
			createAbsoluteDir(dinPath);
			output = new FileOutputStream(dinPath);
			// FileWriter writer= new FileWriter(dinPath);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
		} catch (IOException ioe) {
			ioe.getMessage();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.getMessage();
			}
		}

	}

	/**
	 * 拷贝资源文件到指定目录下
	 * 
	 * @param srcFile
	 *            资源文件源
	 * @param dinPath
	 *            目标文件绝对地址
	 * @throws IOException
	 */
	public static void copyFileOnce(File srcFile, String dinPath) {
		FileInputStream input = null;
		FileOutputStream output = null;

		try {
			input = new FileInputStream(srcFile);
			// 建立目标路径的文件夹
			createAbsoluteDir(dinPath);
			output = new FileOutputStream(dinPath);
			// FileWriter writer= new FileWriter(dinPath);
			byte[] b = new byte[input.available()];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
		} catch (IOException ioe) {
			ioe.getMessage();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.getMessage();
			}
		}

	}

	/**
	 * 拷贝资源文件到指定目录下
	 * 
	 * @param srcFile
	 *            资源文件源
	 * @param dinPath
	 *            目标文件绝对地址
	 * @throws IOException
	 */
	public static void copyFileOnce(File srcFile, File dinFile) {
		FileInputStream input = null;
		FileOutputStream output = null;

		try {
			input = new FileInputStream(srcFile);
			// 建立目标路径的文件夹
			createAbsoluteDir(dinFile.getAbsolutePath());
			output = new FileOutputStream(dinFile);
			// FileWriter writer= new FileWriter(dinPath);
			byte[] b = new byte[input.available()];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
		} catch (IOException ioe) {
			ioe.getMessage();
			throw new RuntimeException(ioe);
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.getMessage();
				throw new RuntimeException(ioe);
			}
		}

	}

	/**
	 * 拷贝资源文件到指定目录下
	 * 
	 * @param srcFile
	 *            资源文件绝对地址
	 * @param dinPath
	 *            目标文件绝对地址
	 * @throws IOException
	 */
	public static void copyFile(String srcPath, String dinPath) {
		File srcFile = new File(srcPath);
		if (srcFile.isFile()) {
			copyFile(srcFile, dinPath);
		}
	}

	public static void generateFile(InputStream srcStream, String dinPath)
			throws IOException {
		// 建立目标路径的文件夹
		createAbsoluteDir(dinPath);
		FileOutputStream output = new FileOutputStream(dinPath);
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = srcStream.read(b)) != -1) {
			output.write(b, 0, len);
		}
		output.flush();
		output.close();
	}

	/**
	 * 生成指定文件
	 * 
	 * @param srcStr
	 *            字符串型的文件源
	 * @param dinPath
	 *            目标文件绝对地址
	 * @param isAppend
	 *            是否追加模式
	 */
	public static void generateFile(String srcStr, String dinPath,
			boolean isAppend) {
		// 建立目标路径的文件夹
		createAbsoluteDir(dinPath);
		FileWriter writer = null;
		try {
			writer = new FileWriter(dinPath, isAppend);
			writer.write(srcStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();

		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			if(myDelFile.exists()){
				myDelFile.delete();
			}

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}

	}

	public static void delFolder(URI uri) throws FileNotFoundException {
		File file = new File(uri);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		if (file.isDirectory()) {
			File[] fe = file.listFiles();
			for (int i = 0; i < fe.length; i++) {
				try {
					delFolder(new URI(fe[i].toString()));
				} catch (URISyntaxException e) {
					System.out.println("删除文件夹操作出错");
					e.printStackTrace();
				}
				fe[i].delete(); // 删除已经是空的子目录
			}
		}
		file.delete(); // 删除总目录
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}
	
	/**
	 * 遍历目录
	 * @author:     zhangpeng 
	 * Create at:   2009-5-27 上午11:25:14 
	 * @param path
	 * @return 类型为key，文件路径为value的HashMap,key：pic图片，txt文字，smail，mic声音
	 */
	public static HashMap<String, String> ergodAllFile(String path) {
		HashMap<String, String> fileTypes = new HashMap<String, String>();
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return null;
		}
		String tmpPath = "";
		String suffix = "";
		String[] tempList = file.list();
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				tmpPath = path + tempList[i];
				suffix = FileUtils.getSuffix(tmpPath);
			} else {
				tmpPath = path + tempList[i];
				suffix = FileUtils.getSuffix(tmpPath);
			}
			
			if("txt".equals(suffix)){
				fileTypes.put("txt", tmpPath);
			}else if("png".equals(suffix) || "jpg".equals(suffix)){
				if(!fileTypes.containsKey("pic")){
					fileTypes.put("pic", tmpPath);
				}else{
					String hString = fileTypes.get("pic") + "," + tmpPath;
					fileTypes.put("pic", hString);
				}
			}else if("smail".equals(suffix)){
				fileTypes.put("smail", tmpPath);
			}
		}
		return fileTypes;
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}

	public static String getSuffix(String name) {
		if (name != null && name.lastIndexOf(".") > 0) {
			return name.substring(name.lastIndexOf(".") + 1);
		} else {
			return null;
		}
	}

	public static String getFolder(String name) {
		if (name != null) {
			if ((name.lastIndexOf("/") > 0) && (name.lastIndexOf("\\") > 0)) {
				if (name.lastIndexOf("\\") > name.lastIndexOf("/")) {
					return name.substring(0,name.lastIndexOf("\\") +1);
				} else {
					return name.substring(0,name.lastIndexOf("/") +1);
				}

			} else if (name.lastIndexOf("/") > 0) {
				return name.substring(0,name.lastIndexOf("/") +1);
			} else if (name.lastIndexOf("\\") > 0) {
				return name.substring(0,name.lastIndexOf("\\")+1 );
			}

		}
		return "";

	}
	
	/**
	 * 
	 *@param name
	 *@return
	 *@author fuying
	 *@date 2009-2-27 上午11:41:27
	 *@comment 得到文件的没有后缀名的短文件名
	 */
	public static String getShortNameNoSuffix(String name) {
		return name.substring(getFolder(name).length(),name.length()-getSuffix(name).length()-1);

	}
	
	/**
	 * 
	 *@param name
	 *@return
	 *@author fuying
	 *@date 2009-2-27 上午11:43:34
	 *@comment 得到文件的的短文件名
	 */
	public static String getShortName(String name){
		return name.substring(getFolder(name).length());
	}
	
	/**
	 * get the file size.
	 * @param sFilePath
	 * @return
	 * @author zhuyawei
	 * @date 2008-5-21 下午05:31:43
	 * @comment 得到文件的大小
	 */
	public static int getFileSize(String sFilePath) {
		FileInputStream fis = null;
		try {
			if (StringUtils.isEmpty(sFilePath)) throw new RuntimeException("file path is empth.");
			
			 fis = new FileInputStream(sFilePath);
			int iSize = fis.available();
			fis.close();

			return iSize;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(null!=fis)
				{
				fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		/*String[] ss = new String[]{"11"};
		System.out.println(ss);*/
		System.out.println("11");
		
		FileUtils.generateFile("hello baby\n", "D:/11/fuying.txt",true);
	}

}
