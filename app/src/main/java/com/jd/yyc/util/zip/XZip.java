package com.jd.yyc.util.zip;


import com.jd.yyc.util.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Android Zip压缩解压缩
 * <p/>
 */
public class XZip {

	/**
	 * 取得压缩包中的 文件列表(文件夹,文件自选)
	 *
	 * @param zipFileString  压缩包名字
	 * @param bContainFolder 是否包括 文件夹
	 * @param bContainFile   是否包括 文件
	 * @return
	 * @throws Exception
	 */
	public static List<File> GetFileList(String zipFileString,
	                                     boolean bContainFolder, boolean bContainFile) throws Exception {

		List<File> fileList = new ArrayList<File>();
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(
				zipFileString));
		ZipEntry zipEntry;
		String szName = "";

		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();

			if (zipEntry.isDirectory()) {

				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(szName);
				if (bContainFolder) {
					fileList.add(folder);
				}

			} else {
				File file = new File(szName);
				if (bContainFile) {
					fileList.add(file);
				}
			}
		}// end of while

		inZip.close();

		return fileList;
	}

	/**
	 * 返回压缩包中的文件InputStream
	 *
	 * @param zipFileString 压缩文件的名字
	 * @param fileString    解压文件的名字
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream UpZip(String zipFileString,
	                                String fileString) throws Exception {
		ZipFile zipFile = new ZipFile(zipFileString);
		ZipEntry zipEntry = zipFile.getEntry(fileString);

		return zipFile.getInputStream(zipEntry);

	}

	/**
	 * 解压一个压缩文件 到 指定位置
	 *
	 * @param in            压缩文件流
	 * @param outPathString 指定路径
	 * @throws Exception
	 */
	public static void UnZipFoder(InputStream in, String outPathString,
	                              UnZipCallBackListener unZipCallBackListener) {
		UnZipFolder(in, null, outPathString, unZipCallBackListener);
	}

	/**
	 * 解压一个压缩文件 到 指定位置
	 *
	 * @param zipFileString 压缩包的名字
	 * @param outPathString 指定路径
	 * @throws Exception
	 */
	public static void UnZipFoder(String zipFileString, String outPathString,
	                              UnZipCallBackListener unZipCallBackListener) {
		UnZipFolder(null, zipFileString, outPathString, unZipCallBackListener);
	}

	/**
	 * 解压一个压缩文档 到指定位置
	 *
	 * @param in            文件流
	 * @param zipFileString 压缩包的名字
	 * @param outPathString 指定的路径
	 * @throws Exception
	 */
	public static void UnZipFolder(InputStream in, String zipFileString,
	                               String outPathString, UnZipCallBackListener unZipCallBackListener) {
		InputStream inputStream = null;

		if (in != null) {
			inputStream = in;
		} else {
			try {
				inputStream = new FileInputStream(zipFileString);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				unZipCallBackListener.UnZipErro(-4, "解压资源不存在");
			}
		}
		ZipInputStream inZip = null;// = new ZipInputStream(inputStream);
		ZipEntry zipEntry;
		String szName = "";
		try {
			try {
				inZip = new ZipInputStream(inputStream);
			} catch (Exception e) {
				// unZipCallBackListener.UnZipErro(-5,"解压资源不存在");
				return;
			}
			// 解压文件数
			// int num=0;
			// 解压大小测试
			long oldNum = 0;
			int num = 0;
			while ((zipEntry = inZip.getNextEntry()) != null) {
				// num++;
				// 解压后的大小
				// oldNum=oldNum+zipEntry.getSize();
				// 解压前的大小 但是比原来的要小
				oldNum = oldNum + zipEntry.getCompressedSize();
				szName = zipEntry.getName();
				// Constants.Logdada("name:"+zipEntry.getNickName());
				// unZipCallBackListener.UnZipStart(num+"");
				unZipCallBackListener.UnZipStart(oldNum);
				if (zipEntry.isDirectory()) {

					// get the folder name of the widget
					szName = szName.substring(0, szName.length() - 1);
					File folder = new File(outPathString + File.separator
							+ szName);
					L.d("name:" + outPathString + File.separator + szName);
					folder.mkdirs();

				} else {
					String p = outPathString + File.separator
							+ szName;
					File file = new File(p);
					L.d("==?>" + p);
					if (p.contains("MACOSX") || p.contains("DS_Store")) {
						continue;
					}
					unZipCallBackListener.UnZipFile(num, p);
					num++;
					file.createNewFile();
					// get the output stream of the file
					FileOutputStream out = new FileOutputStream(file);
					int len;
					byte[] buffer = new byte[1024];
					// read (len) bytes into buffer
					while ((len = inZip.read(buffer)) != -1) {
						// write (len) byte from buffer at the position 0
						out.write(buffer, 0, len);
						out.flush();
					}
					out.close();
				}
			}
			inZip.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			unZipCallBackListener.UnZipErro(-4, "解压资源不存在");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			unZipCallBackListener.UnZipErro(-5, e.getMessage().toString());
			return;
		}// end of while
		unZipCallBackListener.UnZipFinish();
		// 删除zip包
		try {
			new File(zipFileString).delete();
		} catch (Exception e) {
			e.printStackTrace();
			L.d("删除资源不存在");
		}
	}// end of func

	/**
	 * 压缩文件,文件夹
	 *
	 * @param srcFileString 要压缩的文件/文件夹名字
	 * @param zipFileString 指定压缩的目的和名字
	 * @throws Exception
	 */
	public static void ZipFolder(String srcFileString, String zipFileString)
			throws Exception {
		// 创建Zip包
		ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(
				zipFileString));

		// 打开要输出的文件
		File file = new File(srcFileString);

		// 压缩
		ZipFiles(file.getParent() + File.separator, file.getName(), outZip);

		// 完成,关闭
		outZip.finish();
		outZip.close();

	}// end of func

	/**
	 * 压缩文件
	 *
	 * @param folderString
	 * @param fileString
	 * @param zipOutputSteam
	 * @throws Exception
	 */
	private static void ZipFiles(String folderString, String fileString,
	                             ZipOutputStream zipOutputSteam) throws Exception {
		if (zipOutputSteam == null)
			return;

		File file = new File(folderString + fileString);

		// 判断是不是文件
		if (file.isFile()) {

			ZipEntry zipEntry = new ZipEntry(fileString);
			FileInputStream inputStream = new FileInputStream(file);
			zipOutputSteam.putNextEntry(zipEntry);

			int len;
			byte[] buffer = new byte[4096];

			while ((len = inputStream.read(buffer)) != -1) {
				zipOutputSteam.write(buffer, 0, len);
			}

			zipOutputSteam.closeEntry();
		} else {

			// 文件夹的方式,获取文件夹下的子文件
			String fileList[] = file.list();

			// 如果没有子文件, 则添加进去即可
			if (fileList.length <= 0) {
				ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
				zipOutputSteam.putNextEntry(zipEntry);
				zipOutputSteam.closeEntry();
			}

			// 如果有子文件, 遍历子文件
			for (int i = 0; i < fileList.length; i++) {
				ZipFiles(folderString, fileString + File.separator
						+ fileList[i], zipOutputSteam);
			}// end of for

		}// end of if

	}// end of func

	public void finalize() throws Throwable {

	}

	/**
	 * 解压缩监听
	 *
	 * @author Administrator dada add
	 */
	public interface UnZipCallBackListener {
		/**
		 * 解压异常
		 *
		 * @param str 异常描述 -2为解压缩空间不足 -3资源不存在 -5未知其他错误
		 */
		public void UnZipErro(int type, String str);

		/**
		 * 解压结束
		 */
		public void UnZipFinish();

		/**
		 * 开始解压
		 *
		 * @param num 解压进度
		 */
		public void UnZipStart(long num);

		public void UnZipFile(int num, String name);
	}
}