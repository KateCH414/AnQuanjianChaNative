package com.jd.yyc.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.jd.project.lib.andlib.utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 文件操作工具类
 */
public class FileUtil {

    public static final String TAG = FileUtil.class.getSimpleName();


    /**
     * 判断手机是否存在sd卡,并且有读写权限
     *
     * @return
     */
    public static boolean isExistSdcard(Context context) {
        boolean flag = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            flag = true;
        } else {

        }
        return flag;
    }

    /**
     * 创建一个文件，创建成功返回true
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除一个文件
     *
     * @param filePath 要删除的文件路径名
     * @return true if this file was deleted, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 删除一个文件目录以及目录下的文件或者子目录
     */
    public static void deleteFileDirectory(String filepath) {
        try {
            File f = new File(filepath);//定义文件路径
            if (f.exists() && f.isDirectory()) {//判断是文件还是目录
                if (f.listFiles().length == 0) {//若目录下没有文件则直接删除
                    f.delete();
                } else {//若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            deleteFileDirectory(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
                        }
                        delFile[j].delete();//删除文件
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个文件目录下的文件或者子目录
     */
    public static void deleteFileDirectoryFile(String filepath) {
        try {
            File f = new File(filepath);//定义文件路径
            if (f.exists() && f.isDirectory()) {//判断是文件还是目录
                if (f.listFiles().length == 0) {//目录不删除
//                    f.delete();
                } else {//若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            deleteFileDirectory(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
                        }
                        delFile[j].delete();//删除文件
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     */
    public static void write(Context context, String fileName, byte content[]) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean writeFile(File file, Object o, boolean append) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        if (!file.exists())
            try {
                if (!file.createNewFile())
                    return false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        if (o == null)
            return false;
        else {
            try {
                fos = new FileOutputStream(file, append);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(o);
                oos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (oos != null)
                        oos.close();
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    public static boolean writeFile(String pathName, Object o, boolean append) {
        File file = new File(pathName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile())
                    return false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return writeFile(file, o, append);
    }

    /**
     * ObjectInputStream return Set/Map/Vector
     */
    public static Object readFile(String pathName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        File file = new File(pathName);
        if (!file.exists())
            return null;
        else {
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                obj = ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ois != null)
                        ois.close();
                    if (fis != null)
                        fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return obj;
        }
    }

    //将文本文件中的内容读入到buffer中
    public static void readToBuffer(StringBuffer buffer, String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 拷贝文件
     */
    public static void copyFile(File fromFile, File toFile, Boolean rewrite) {
        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Stores an image on the storage
     *
     * @param image       the image to store.
     * @param pictureFile the file in which it must be stored
     */
    public static void storeImage(Bitmap image, File pictureFile) {
        storeImage(image, pictureFile, 90);
    }

    /**
     * Stores an image on the storage
     *
     * @param image       the image to store.
     * @param pictureFile the file in which it must be stored
     * @param quality
     */
    public static void storeImage(Bitmap image, File pictureFile, int quality) {
        if (pictureFile == null) {
            Logger.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Logger.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Logger.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * 保存图片<br>
     * 图片保存到默认路径，名字用时间戳。
     *
     * @param saveBitmap
     */

    @SuppressLint("NewApi")
    public static String savePhoto(Context context, String dirName, Bitmap saveBitmap, boolean sendBroad) {
        return savePhoto(context, dirName, null, saveBitmap, sendBroad);
    }

    public static String savePhoto(Context context, String dirName, String fileName, Bitmap saveBitmap, boolean sendBroad) {

        String root = null;
        String path = null;

        if (isExistSdcard(context)) {
            root = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            root = Environment.getDataDirectory() + File.separator + dirName;
        }

        if (fileName == null) {
            path = root + File.separator + System.currentTimeMillis() + ".jpg";
        } else {
            path = root + File.separator + fileName;
        }


        try {
            File directory = new File(root);
            // 若保存路径不存在，先创建保存目录文件夹
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(path);

            if (file.exists()) {
                file.delete();
            }

            storeImage(saveBitmap, file);

            if (sendBroad) {
                // 发送扫描广播
                Uri data = Uri.parse("file://" + path);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
                ToastUtil.show(context, "已保存至路径" + path);
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String saveGif(Context context, String gifpath, String dirName) {

        String root = null;
        String path = null;

        if (isExistSdcard(context)) {
            root = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            root = Environment.getDataDirectory() + File.separator + dirName;
        }
        path = root + File.separator + System.currentTimeMillis() + ".gif";

        try {
            File directory = new File(root);
            // 若保存路径不存在，先创建保存目录文件夹
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(path);

            if (file.exists()) {
                file.delete();
            }

            File gifFile = new File(gifpath);
            if (gifFile.exists()) {
                copyFile(gifFile, file, true);
            }

            // 发送扫描广播
            Uri data = Uri.parse("file://" + path);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
            ToastUtil.show(context, "已保存至路径" + path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 保存图片<br>
     * 图片保存到默认路径，名字用时间戳。
     *
     * @param saveBitmap
     */

    @SuppressLint("NewApi")
    public static String saveFileCache(Context context, String dirName, Bitmap saveBitmap) {
        String path = context.getCacheDir() + dirName;// 保存路径
        String fileName = path + File.separator + System.currentTimeMillis() + ".jpg";

        try {
            File directory = new File(path);
            // 若保存路径不存在，先创建保存目录文件夹
            if (!directory.exists())
                directory.mkdirs();
            File file = new File(fileName);
            storeImage(saveBitmap, file);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isFileExists(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    //保存已经存在图片，并覆盖原来的
    public static String saveExistPhote(Context context, String filePath, Bitmap bitmap, boolean sendBroad) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }

            storeImage(bitmap, file);

            if (sendBroad) {
                // 发送扫描广播
                Uri data = Uri.parse("file://" + filePath);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
                ToastUtil.show(context, "已保存至路径" + filePath);
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}