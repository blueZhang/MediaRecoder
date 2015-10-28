package com.bluezhang.mediarecoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

/**
 * 拍照并且获取bitmap
 * ACTION_VIDEO_CAPTURE视频
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewInject(R.id.imageView_from_bitmap)
    private ImageView imageView;
    private File targetFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

    }

    /**
     * 打开相机拍照 在返回之后可以得到bitmap 或者是将这个图片进行存储
     * @param v
     */
    @OnClick(R.id.paizhao)
    public void takePhoto(View v) {
        Toast.makeText(this, "正在打开相机", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 998);

    }

    /**
     * 拍照并且直接保存成图片文件 不会返回Bitmap结果
     *
     * @param v
     */

    @OnClick(R.id.cunchuwenjian)
    public void takePhotoAndSave(View v) {
        Toast.makeText(this, "正在打开相机", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //设置EXTRA_OUTPUT  设置保存文件的路径
        //数据类型是URI
        File dir = FileUtil.getImageFile(this);
        Toast.makeText(this, "targetFile"+dir.exists()+" "+dir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        Uri uri = Uri.fromFile(dir);//图片存储的文件
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent, 2);

    }

    @OnClick(R.id.biaozhunpaizhao)
    public void biaozhunpaizhao(View v) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(intent, 888);

    }
    @OnClick(R.id.tiaozhuan)
    public void newCarmer(View v) {
        Intent intent = new Intent(this,CarmerActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.listView)
    public void gontoListView(View v){
        Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 998) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);

                    }
                }
            }
        }else if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                if (targetFile != null && targetFile.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile(targetFile.getAbsolutePath(), options);

                    imageView.setImageBitmap(bitmap);
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
