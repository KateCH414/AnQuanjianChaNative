package com.miko.zd.anquanjianchanative.Utils;
/*
 * Created by zd on 2016/11/4.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.miko.zd.anquanjianchanative.Bean.JsonItem;
import com.miko.zd.anquanjianchanative.Bean.JsonRecord;
import com.miko.zd.anquanjianchanative.Bean.RecordItemBean;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.miko.zd.anquanjianchanative.MainActivity.itemTree;

public class Utils {

    public static void renameFile(String file, String toFile) {

        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

            System.out.println("File does not exist: " + file);
            return;
        }

        File newFile = new File(toFile);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }

    }
    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static void checkSave(Context context) {
        SharedPreferences sp = context.getSharedPreferences("tempUser", Context.MODE_PRIVATE);
        String user = sp.getString("user", null);
        String date = sp.getString("date", null);
        sp = context.getSharedPreferences("tempCheckInfo", Context.MODE_PRIVATE);
        int order = sp.getInt("order", -3);
        String dept = sp.getString("dept", null);
        String principal = sp.getString("principal", null);
        String room = sp.getString("room", null);

        new Thread(() -> {
            ArrayList<RecordItemBean> mData = new ArrayList<>();
            JsonRecord jsonRecord = new JsonRecord();
            jsonRecord.setPrincipal(principal);
            jsonRecord.setRoom(room);
            jsonRecord.setDept(dept);
            jsonRecord.setInspector(user);
            jsonRecord.setDate(date);
            for (int i = 0; i < itemTree.size(); i++) {
                for (int j = 0; j < itemTree.get(i).getItemList().size(); j++) {
                    for (int k = 0; k < itemTree.get(i).getItemList().get(j).getItemList().size(); k++) {
                        if (itemTree.get(i).getItemList().get(j).getItemList().get(k).getCb() != -1
                                || itemTree.get(i).getItemList().get(j).getItemList().get(k).getPaths().size() != 1
                                || !itemTree.get(i).getItemList().get(j).getItemList().get(k).getNote().equals("")) {
                            RecordItemBean recordItemBean = new RecordItemBean(i, j, k);
                            mData.add(recordItemBean);
                        }
                    }
                }
            }
            ArrayList<JsonItem> itemList = new ArrayList<>();
            for (RecordItemBean item : mData) {
                JsonItem jsonItem = new JsonItem();
                jsonItem.setG(item.getGrad());
                jsonItem.setP(item.getParent());
                jsonItem.setI(item.getIndex());
                jsonItem.setCb(itemTree.get(item.getGrad()).getItemList().get(item.getParent()).
                        getItemList().get(item.getIndex()).getCb());
                jsonItem.setNote(itemTree.get(item.getGrad()).getItemList().get(item.getParent()).
                        getItemList().get(item.getIndex()).getNote());
                jsonItem.setTitle(itemTree.get(item.getGrad()).getItemList().get(item.getParent()).
                        getItemList().get(item.getIndex()).getItemName());
                ArrayList<String> base64List = new ArrayList<>();
                for (String path : itemTree.get(item.getGrad()).getItemList().get(item.getParent()).
                        getItemList().get(item.getIndex()).getPaths()) {
                    base64List.add(path);
                }
                jsonItem.setBase64Pic(base64List);
                base64List = itemTree.get(item.getGrad()).getItemList().get(item.getParent()).
                        getItemList().get(item.getIndex()).getPaths();
                jsonItem.setBase64Pic(base64List);
                itemList.add(jsonItem);
            }
            jsonRecord.setItemList(itemList);
            Log.i("xyz", String.valueOf(jsonRecord.getItemList().size()));
            Utils.createTxt(Environment.getExternalStorageDirectory() + "/SDAnQuanJianChaUnS/" + user + "_"
                    + date + "_" + dept+"_"+principal + ".txt",
                    new Gson().toJson(jsonRecord,JsonRecord.class));
        }).start();

    }

    public static int charSize(String s, char c) {
        int num = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                num++;
            }
        }
        return num;
    }

    public static String FiletoBase64(Bitmap bitmap) {
    //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
    public static void StringToFile(String base64,String picName,String dir) throws IOException {
        File dirF = new File(dir);
        if(!dirF.exists()){
            dirF.mkdirs();
        }
        File picF = new File(dir+"/"+picName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(picF));
        stringtoBitmap(base64).compress(Bitmap.CompressFormat.JPEG,90,bos);
        bos.flush();
        bos.close();
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String readTxt(File file) throws IOException {
        StringBuffer content = new StringBuffer();
        InputStream instream = new FileInputStream(file);
        InputStreamReader inputreader = new InputStreamReader(instream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
//分行读取
        while ((line = buffreader.readLine()) != null) {
            content.append(line);
        }
        instream.close();
        return String.valueOf(content);
    }

    public static synchronized void createTxt(String filePath, String content) {
        //如果filePath是传递过来的参数，可以做一个后缀名称判断； 没有指定的文件名没有后缀，则自动保存为.txt格式
        if (!filePath.endsWith(".txt") && !filePath.endsWith(".log"))
            filePath += ".txt";
        //保存文件
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        try {
            OutputStream outstream = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(outstream);
            out.write(content);
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTxt(String path, String content) throws IOException {
        FileWriter fw = new FileWriter(new File(path), true);
        fw.write(content);
        fw.flush();
        fw.close();
    }

}

