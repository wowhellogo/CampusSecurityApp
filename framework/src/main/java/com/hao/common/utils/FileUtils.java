package com.hao.common.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * SD 卡文件操作
 * 
 * @author Albert Smith
 */
public class FileUtils {
    public final static int IS_OK = 10;
    private final String TAG = "FileUtils";
    private String root;
    
    public FileUtils() {
        // 得到当前外部存储设备的目录
        root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }
    
    /**
     * 在SD卡上创建文件
     * 
     * @param name
     * @return
     */
    public File createSDFile(String dir, String name) {
        File lodir = new File(root + dir);
        if (!lodir.exists()) {
            lodir.mkdir();
        }
        File file = new File(root + dir + "/" + name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
    
    /**
     * 在SD卡上创建目录
     * 
     * @param dir
     * @return
     */
    public File createSDDir(String dir) {
        File file = new File(root + dir);
        file.mkdir();
        return file;
    }
    
    /**
     * 判断路径下是否有该文件
     * 
     * @param dir
     * @param name
     * @return
     */
    public boolean isFileExist(String dir, String name) {
        File file = new File(root + dir + "/" + name);
        return file.exists();
    }
    
    /**
     * 将一个inputStream里面的数据写到SD卡中
     * 
     * @param dir
     * @param name
     * @param is
     * @return
     */
    public File writeToSDfromInput(String dir, String name, InputStream is) {
        if (isFileExist(dir, name)) {
            Log.d(TAG, "该文件已存在");
            // return getFile(dir, name);
        }
        File file = createSDFile(dir, name);
        if (file != null && is != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                while ((ch = is.read(buf)) != -1) {
                    fos.write(buf, 0, ch);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                file = null;
                e.printStackTrace();
                Log.d(TAG, "打开输出出错");
            } catch (IOException e) {
                file = null;
                e.printStackTrace();
                Log.d(TAG, "打开输出出错");
            }
        }
        
        return file;
    }
    
    /**
     * 返回对应路径的文件 不存在则返回null
     * 
     * @param dir
     * @param name
     * @return
     */
    public File getFile(String dir, String name) {
        File file = new File(root + dir + "/", name);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }
    
    /**
     * 创建新文件
     * 
     * @param dir
     * @param name
     * @return
     */
    public File creatFile(String dir, String name) {
        File file = new File(root + dir + "/", name);
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    // 递归删除目录
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}