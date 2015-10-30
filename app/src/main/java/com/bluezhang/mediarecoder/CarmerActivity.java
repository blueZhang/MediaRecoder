package com.bluezhang.mediarecoder;


import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 自定义拍照功能  使用 android.hardware.camera 类
 */
public class CarmerActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.PreviewCallback {
    //照相机的API
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmer);
        SurfaceView preview = (SurfaceView) findViewById(R.id.custom_take_service);
        //要想使用surfaceView 就要使用SurfaceHolder ；
        //通过holder 就能进行绘制

        SurfaceHolder holder = preview.getHolder();
        //SurfaceView 会有一些变化 当做好准备 的时候就能回掉callBack
        holder.addCallback(this);


//        AlertDialog.Builder bu = new AlertDialog.Builder(this);
//        AlertDialog dfdsfdsfd = bu.setIcon(R.mipmap.ic_luncher).setTitle("dfdsfdsfd").create();
//        dfdsfdsfd.show();

    }

    //-------------------------------------------------------

    /**
     * 当SurfaceView准备好的时候从屏幕上还分出来一块区域
     * 作为SurfaceView的时候就会毁掉这个方法，代表了，可以
     * 在这个方法中进行绘制
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //1.准备Camera
        camera = Camera.open();
        //开始预览
        if (camera != null) {
            try {
                //设置预览的界面 ，通过SurfaceHolder 来绘制
                camera.setPreviewDisplay(holder);
                //设置预览的参数
                camera.setDisplayOrientation(90);
                Camera.Parameters parameters = camera.getParameters();
                parameters.setColorEffect(Camera.Parameters.EFFECT_BLACKBOARD);
                parameters.setJpegQuality(100);//设置图片的质量  1-100
                parameters.setPictureSize(500, 500);
                //设置图像是旋转的
                parameters.setRotation(90);
                //获取支持的颜色效果
                List<String> supportedColorEffects = parameters.getSupportedColorEffects();
                if (supportedColorEffects != null) {
                    for (String supportedColorEffect : supportedColorEffects) {
                        if (BuildConfig.DEBUG) Log.d("CameraActivity", supportedColorEffect);
                    }
                }
                parameters.setColorEffect(Camera.Parameters.EFFECT_BLACKBOARD);
//                parameters.setPictureFormat(ImageFormat.RAW12);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                camera.startPreview();


                camera.setPreviewCallback(this);
                //开始预览
                camera.startPreview();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 当SurfaceView不再使用的时候那么内
     * 部的屏幕区域就要销毁和释放，不能进行任何绘制了
     *
     * @param holder
     */

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {

            }
            //释放摄像头这样可以避免摄像头的占用避免其他程序的使用
            camera.release();

        }

    }
    ///--------------------------------------------------


    /**
     * 开始进行拍照
     *
     * @param v
     */
    @OnClick(R.id.custom_take)
    public void btnTakePicture(View v) {
        if (camera != null) {
            camera.takePicture(
                    null,//快门回掉，当拍照之后，回掉这个接口
                    null,//raw 模式 数据回掉
                    null,
                    this//当照片拍下来之后回掉的接口 jpeg模式的数据

            );

        }

    }

    /**
     * 用于接收数据
     *
     * @param data   对于jpeg模式数据就是一个模式
     * @param camera
     */

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File imageFile = FileUtil.getImageFile(this);
        try {
            FileOutputStream fout = new FileOutputStream(imageFile);
            fout.write(data);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        camera.startPreview();//调用预览之后就会停止那么久停止了所以要重新执行


    }

    /**
     * 在预览中刷新一帧回掉一次 吧预览的内容存储data数据
     * @param data
     * @param camera
     */
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
