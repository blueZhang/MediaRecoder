package com.bluezhang.mediarecoder;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Author: blueZhang
 * DATE:2015/10/28
 * Time: 14:16
 * email:bluezhang521@163.com
 */
public final class FileUtil {
    private  FileUtil(){}
    public static File getMediaRecorderFolder(Context context) {
        File ret = null;
        String externalStorageState = Environment.getExternalStorageState();
        File dir = null;
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            //获取公共的照片拍照存储文件的位置
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                //因为公共的拍照位置不存在
                //获取存储卡中的特定的文件目录
                dir = Environment.getExternalStorageDirectory();
                dir = new File(dir, "MediaRecorder");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
        } else {
            //内部存储区
            dir = context.getFilesDir();
        }
        ret = dir;
        return ret;
    }

    public static File getImageFile(Context context){
        String state = Environment.getExternalStorageState();
        File dir=null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //获取公共的
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                dir = Environment.getExternalStorageDirectory();
                dir=new File(dir,"MEDIARECONDRE/IMAGES");
                if (!dir.exists()){
                    dir.mkdirs();

                }
            }

        }else {
            dir=context.getFilesDir();
        }

        File targetFile = new File(dir,"image"+System.currentTimeMillis()+".png");
        return  targetFile;
    }

}
