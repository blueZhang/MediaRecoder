package com.bluezhang.mediarecoder;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    //首先需要Mediarecorder
    private MediaRecorder mediaRecorder;
    private boolean isPlaying ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        mediaRecorder = new MediaRecorder();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    /**
     * 开始录音
     * @param view
     */
    public void btnStartClick(View view) {
        if(!isPlaying) {

            //录音的步骤
            //设置输入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);

            //设置输出文件的格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //设置之声音编码器
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);

            //设置录音的声道，代表立体声
            mediaRecorder.setAudioChannels(2);
            mediaRecorder.setAudioEncodingBitRate(128*1024);

//            mediaRecorder.setVideoEncodingBitRate();
            mediaRecorder.setAudioSamplingRate(96000);




            //设置保存
            File folder = FileUtil.getMediaRecorderFolder(this);
            File targetFile = new File(folder, "audio" + System.currentTimeMillis() + ".3gp");
            mediaRecorder.setOutputFile(targetFile.getAbsolutePath());
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
            isPlaying = true;
        }

    }

    public void btnPouseClick(View view) {
    }

    public void btnStopClick(View view) {
        if(isPlaying){
            isPlaying = false;

            mediaRecorder.stop();
            mediaRecorder.reset();
        }
    }

    public void btnGoTo(View view) {
        Intent intent = new Intent(this,ListViewActivity.class);
        startActivity(intent);
    }

    public void btnGoNext(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
