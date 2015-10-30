package com.bluezhang.mediarecoder;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class SurfaceViewLookVideo extends AppCompatActivity implements Camera.PreviewCallback, SurfaceHolder.Callback {
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceView surfaceView;

    private boolean isRecording;

    private Button btnBegin;
    private Button btnStop;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view_look_video);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        mediaRecorder = new MediaRecorder();
        btnBegin = (Button) findViewById(R.id.btn_begin);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setEnabled(false);
    }

    public void btnLookBegin(View view) {
        btnBegin.setEnabled(false);
        btnStop.setEnabled(true);
        if (!isRecording) {

            CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
            mediaRecorder.setPreviewDisplay(holder.getSurface());
            //设置声音的输入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置视频的输入源
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            //设置视频的输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //设置视频的编码格式
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            //设置声音的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //设置声音的位速率
            mediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            //设置视频视频帧率
            mediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
            //设置视频的尺寸
            mediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);

            //设置视频的存放位置
            File folder = FileUtil.getMediaRecorderFolder(this);
            File targetFile = new File(folder, "video-" + System.currentTimeMillis() + ".mp4");

            mediaRecorder.setOutputFile(targetFile.getAbsolutePath());

            try {
                try {
                    if (camera != null) {

                        camera.stopPreview();
                        camera.release();
                    }
                }catch (Exception e){

                }

                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnLookStop(View view) {
        btnBegin.setEnabled(true);
        btnStop.setEnabled(false);
        if (isRecording) {
            isRecording = false;
            mediaRecorder.stop();
            mediaRecorder.reset();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        camera.setPreviewCallback(this);

        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.release();
                camera = null;
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    @Override
    protected void onDestroy() {
        if (mediaRecorder != null) {
            if(isRecording) {
                mediaRecorder.stop();
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
        super.onDestroy();
    }
}
