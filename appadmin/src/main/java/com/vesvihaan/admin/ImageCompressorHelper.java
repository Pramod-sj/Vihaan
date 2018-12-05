package com.vesvihaan.admin;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;


public class ImageCompressorHelper {
    int height=700;
    int width=500;
    Bitmap.CompressFormat imageFormat=Bitmap.CompressFormat.JPEG;
    ImageCompressorHelper imageCompressorHelper;
    int quality=80;
    String imageName;
    Context context;
    int imageSource;
    public ImageCompressorHelper(Context context){
        this.context=context;
    }
    public ImageCompressorHelper setHeight(int height){
        this.height=height;
        return this;
    }
    public ImageCompressorHelper setSource(int imageSource){
        this.imageSource=imageSource;
        return this;
    }

    public ImageCompressorHelper setWidth(int height){
        this.height=height;
        return this;
    }
    public ImageCompressorHelper setImageFormat(Bitmap.CompressFormat imageFormat){
        this.imageFormat=imageFormat;
        return this;
    }
    public ImageCompressorHelper setImageName(String imageName){
        this.imageName=imageName;
        return this;
    }
    public Uri compressImage(Uri originalImageUri){
        File compressedImageFileLocation = new File(Environment.getExternalStorageDirectory() + "/Vihaan/", imageName + ".jpg");
        File file=ImageCompressor.compressImage(new File(originalImageUri.getPath()), width, height, imageFormat, quality, compressedImageFileLocation.getPath());
        File compressedFile=null;
        if (imageSource== Constant.UPLOAD_IMAGE_USING_CAMERA) {
            compressedFile = ImageCompressor.compressImage(new File(originalImageUri.getPath()), 700, 500, Bitmap.CompressFormat.JPEG, 80, compressedImageFileLocation.getPath());
            File originalFile = new File(originalImageUri.getPath());
            originalFile.delete();
        }
        else if(imageSource==Constant.UPLOAD_IMAGE_USING_GALLERY){
            //get image original path
            String[] projection={MediaStore.Images.Media.DATA};
            Cursor cursor=((Activity)context).managedQuery(originalImageUri,projection,null,null,null);
            int col_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToNext();
            //
            compressedFile = ImageCompressor.compressImage(new File(cursor.getString(col_index)), 700, 500, Bitmap.CompressFormat.JPEG, 80, compressedImageFileLocation.getPath());
        }
        return Uri.fromFile(compressedFile);
    }
}
