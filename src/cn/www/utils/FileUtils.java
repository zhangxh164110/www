package cn.www.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtils {
	private static Log logger = LogFactory.getLog(FileUtils.class);
	private static String message;
	public static final int BUFFER_SIZE = 5 * 1024;

	public FileUtils() {
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(String filePathAndName, String encoding) throws IOException {
		StringBuilder str = new StringBuilder();
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + " ");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			br.close();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			message = "创建目录操作出错";
			logger.error("创建一个目录操作出错，目录名：" + folderPath);
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (@SuppressWarnings("unused")
			int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			message = "创建多级目录操作出错！";
			logger.error("创建多级目录操作出错！");
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 */
	public static void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			} else {
				myFilePath.delete();
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
			logger.error("创建文件操作出错，文件名：" + filePathAndName);
		}
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 */
	public static void createFile(String filePathAndName, String fileContent, String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			} else {
				myFilePath.delete();
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			message = "创建文件操作出错";
		}
	}

	public static void createFile(File file, String fileContent, String encoding) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(file, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static boolean deleteFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "<br>删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 */
	public static void deleteFolder(String folderPath) {
		try {
			deleteAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			message = ("删除文件夹操作出错");
			logger.error("删除文件夹操作出错，目录名：" + folderPath);
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 */
	public static boolean deleteAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
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
				deleteAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				deleteFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			@SuppressWarnings("unused")
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
			logger.error("复制单个文件操作出错，文件名：" + oldPathFile);
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldFile
	 *            准备复制的文件源
	 * @param newFile
	 *            拷贝的新文件
	 */
	public static void copyFile(File oldFile, File newFile) {
		try {
			org.apache.commons.io.FileUtils.copyFile(oldFile, newFile);
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
			logger.error("复制单个文件操作出错，文件名：" + oldFile.getName());
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			File a = new File(oldPath);
			if (a == null || !a.isDirectory()) {
				return;
			}
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
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
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
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
			message = "复制整个文件夹内容操作出错";
			logger.error("复制整个文件夹内容操作出错，文件夹名：" + oldPath);
		}
	}

	/**
	 * 移动文件
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		deleteFile(oldPath);
	}

	/**
	 * 移动目录
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		deleteFolder(oldPath);
	}

	public static String getMessage() {
		return message;
	}

	/**
	 * 解析压缩文件，获得图片名称
	 */
	public static String parseZip(String zipPath, String currentPath) {
		StringBuffer pictureNams = new StringBuffer();
		OutputStream os = null;
		InputStream is = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipPath);
			@SuppressWarnings("rawtypes")
			Enumeration zipList = zipFile.entries();
			ZipEntry zipEntry = null;
			byte[] buf = new byte[1024];
			int i = 0;
			while (zipList.hasMoreElements()) {
				// 从ZipFile中得到一个ZipEntry
				zipEntry = (ZipEntry) zipList.nextElement();
				if (zipEntry.isDirectory()) {
					continue;
				}
				if (zipEntry.getName().contains(".")) {
					String fileext = zipEntry.getName().substring(zipEntry.getName().lastIndexOf("."));
					if (fileext.equals(".jpg") || fileext.equals(".gif") || fileext.equals(".bmp")) {
						pictureNams.append(System.currentTimeMillis() + String.valueOf(i) + fileext + ";");
						os = new BufferedOutputStream(new FileOutputStream(currentPath + File.separator
								+ System.currentTimeMillis() + String.valueOf(i) + fileext));
						is = new BufferedInputStream(zipFile.getInputStream(zipEntry));
						int readLen = 0;
						// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
						while ((readLen = is.read(buf, 0, 1024)) != -1) {
							os.write(buf, 0, readLen);
						}
						if (is != null)
							is.close();
						if (os != null)
							os.close();
					}
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
				if (zipFile != null)
					zipFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileUtils.deleteFile(zipPath);
		}
		return pictureNams.toString();
	}

	/**
	 * 文件上传
	 */
	public static String[] uploadFile(String path, List<File> files, List<String> uploadFileName) {
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		FileOutputStream fos = null;
		FileInputStream fis = null;
		byte[] buffer = new byte[1024];
		int len = 0;
		StringBuffer filepath = new StringBuffer();
		String[] paths = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			try {
				Long currentTime = System.currentTimeMillis();
				String name = uploadFileName.get(i).substring(uploadFileName.get(i).lastIndexOf("."),
						uploadFileName.get(i).lastIndexOf(""));
				String fileNames = currentTime + i + name;
				filepath.append(fileNames);
				fos = new FileOutputStream(path + fileNames);
				// 以上传文件建立一个文件上传流
				fis = new FileInputStream(files.get(i));
				// 将上传文件的内容写入服务器
				len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				paths[i] = filepath.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return paths;
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean fileIsExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 创建文件夹
	 */
	public static void createFolders(String realPath) {
		File file = new File(realPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 保存文件/上传文件
	 * 
	 * @param src
	 *            上传的文件
	 * @param path
	 *            保存的路径，包括文件名
	 */
	public static boolean saveFile(File src, String path) {
		File dst = new File(path);
		return saveFile(src, dst);
	}

	/**
	 * 保存文件/上传文件
	 * 
	 * @param src
	 *            上传的文件
	 * @param dst
	 *            保存的文件
	 */
	public static boolean saveFile(File src, File dst) {
		try {
			if (!dst.getParentFile().exists()) {
				dst.getParentFile().mkdirs();
			}
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				return true;
			} finally {
				if (null != in)
					in.close();
				if (null != out)
					out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
