package com.saku.lmlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.saku.lmlib.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static void loadImageWithGlide(Context context, String imageUrl, int placeHolderResId, ImageView iv) {
//        final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.my_logo);
        if (0 == placeHolderResId) {
            placeHolderResId = R.drawable.my_logo;
        }
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(placeHolderResId)
                .placeholder(placeHolderResId)
                .into(iv);
    }

    /**
     * 压缩图片的质量
     *
     * @param image    原图片
     * @param destPath 压缩后图片的保存路径
     * @param maxSize  目标最大的压缩尺寸 byte
     */
    public static void compressImageQuality(Bitmap image, String destPath, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;//100表示不压缩图片，这里是想将图片读到ByteArrayOutputStream流中
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        //maxSize是用户希望将图片压缩成 多少kb，因为options的范围在0到100所以不能这里需要           //对options的值做一个判断
        //循环对图片进行压缩
        while (options > 10 && os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destPath);
            fos.write(os.toByteArray());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (image != null && !image.isRecycled()) {
                    image.recycle();
                }
                fos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩图片的质量
     *
     * @param srcPath  原图片的路径
     * @param destPath 压缩后图片的保存路径
     * @param maxSize  目标最大的压缩尺寸 byte
     */
    public static void compressImageQuality(String srcPath, String destPath, int maxSize) {
        final Bitmap bitmap = BitmapFactory.decodeFile(srcPath);
        if (bitmap == null) {
//            Toast.makeText(LmlibApplication.getAppContext(), " 图片不存在！", Toast.LENGTH_SHORT).show();
            LLog.d("lm", "ImageUtils ------ compressImageQuality: bitmap = null");
            return;
        }
        compressImageQuality(bitmap, destPath, maxSize);
    }

    /**
     * 根据宽高压缩图片
     *
     * @param imgPath 图片路径
     * @param destW   目标图片宽度 in px
     * @param destH   目标图片高度 in px
     * @return 压缩后的图片
     */
    public static Bitmap compressImage(String imgPath, float destW, float destH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;  // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(imgPath, newOpts);  // 将file形式的图片转为bitmap
        newOpts.inJustDecodeBounds = false;   // 开始压缩图片，要读取整张图片内存，要将options.inJustDecodeBounds 设回false

        int w = newOpts.outWidth;     //原图的宽高
        int h = newOpts.outHeight;
        int ratio = 1;// ratio=1表示不缩放
        if (w > h && w > destW) {// 如果宽度大的话根据宽度固定大小缩放
            ratio = (int) (w / destW);
        } else if (w < h && h > destH) {// 如果高度高的话根据宽度固定大小缩放
            ratio = (int) (h / destH);
        }
        if (ratio <= 0)
            ratio = 1;
        newOpts.inSampleSize = ratio;// 设置缩放比例
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * 把bitmap保存到指定路径
     */
    public static void saveBitmapToFile(@NonNull Bitmap bitmap, String destPath) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destPath);
            fos.write(os.toByteArray());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(fos);
            FileUtils.closeIO(os);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

}
