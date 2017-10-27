/**
 * @Title: BitmapUtil.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: Bitmap
 * @author: zhaoqy
 * @date: 2015-8-24 下午5:46:14
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;

public class BitmapUtil 
{
	//将Drawable转化为Bitmap  
	public static Bitmap drawableToBitmap(Drawable drawable)
	{  
        int width = drawable.getIntrinsicWidth();  
        int height = drawable.getIntrinsicHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  : Bitmap.Config.RGB_565);  
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0,0,width,height);  
        drawable.draw(canvas);  
        return bitmap;  
    }  
		
	//获得圆角图片的方法  
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)
	{  
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap  .getHeight(), Config.ARGB_8888);  
		Canvas canvas = new Canvas(output);  
		  
	    final int color = 0xff424242;  
	    final Paint paint = new Paint();  
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
	    final RectF rectF = new RectF(rect);  
	  
	    paint.setAntiAlias(true);  
	    canvas.drawARGB(0, 0, 0, 0);  
	    paint.setColor(color);  
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
	  
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
	    canvas.drawBitmap(bitmap, rect, rect, paint);  
	  
	    return output;  
	}  
	
	public static void saveMyBitmap(String dir,String imgUrl,Bitmap mBitmap)
	{
		if (null != mBitmap && !mBitmap.isRecycled())
		{
			String splistr[] = imgUrl.split("/");
			String name = splistr[splistr.length-1];
			File f = new File(dir+"/" + name);
			
			try 
			{
				f.createNewFile();
			} 
			catch (IOException e) 
			{
			}
			
			FileOutputStream fOut = null;
			try 
			{
				fOut = new FileOutputStream(f);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			//Logcat.i(FlagConstant.TAG," loadedImage5: " + mBitmap);
			//Logcat.i(FlagConstant.TAG," fOut:" + fOut);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			
			try 
			{
				fOut.flush();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			try 
			{
				fOut.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
