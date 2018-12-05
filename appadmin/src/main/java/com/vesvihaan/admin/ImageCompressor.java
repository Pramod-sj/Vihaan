package pramod.com.yourcook.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressor {
    public static File compressImage(File imageFile, int width, int height, Bitmap.CompressFormat compressFormat,int quality,String destinationPath){
        FileOutputStream fileOutputStream=null;
        File file=new File(destinationPath).getParentFile();
        if(!file.exists()){
            file.mkdir();
        }
        try{
            fileOutputStream=new FileOutputStream(destinationPath);
            decodeBitmapFromFile(imageFile,width,height).compress(compressFormat,quality,fileOutputStream);

        }
        catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new File(destinationPath);
    }
    public static Bitmap decodeBitmapFromFile(File imageFile,int width,int height) throws Exception{
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        //calculate insamplesize
        options.inSampleSize=calculateSampleSize(options,width,height);
        //Decode bitmap with insamplesize set
        options.inJustDecodeBounds=false;
        Bitmap scaledBitmap=BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        //check rotaion of image and display it properly
        ExifInterface exifInterface=new ExifInterface(imageFile.getAbsolutePath());
        int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,0);
        Log.i("orientation", String.valueOf(orientation));
        Matrix matrix=new Matrix();
        if (orientation == 0) {
            matrix.postRotate(0);
        }
        else if(orientation==3){
            matrix.postRotate(90);
        }
        else if(orientation==8){
            matrix.postRotate(180);
        }
        scaledBitmap=Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);
        return scaledBitmap;
    }

    static int calculateSampleSize(BitmapFactory.Options options,int width,int height){
       //raw height and width of image
       int height1=options.outHeight;
       int width1=options.outWidth;
        int inSampleSize=1;
        if(height1>height || width1>width){
            int halfHeight=height1/2;
            int halfWidth=width1/2;
            while((halfHeight/inSampleSize)>=height && (halfWidth/inSampleSize)>=width){
                inSampleSize*=2;
            }
        }
        return inSampleSize;
    }
}
