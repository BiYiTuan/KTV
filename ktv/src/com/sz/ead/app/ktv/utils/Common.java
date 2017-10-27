/**
 * @Title: Common.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 公共
 * @author: zhaoqy
 * @date: 2015-8-3 下午1:56:47
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sz.ead.app.ktv.dataprovider.entity.DictionaryItem;
import com.sz.ead.app.ktv.dataprovider.entity.DictionaryValueItem;
import com.sz.ead.app.ktv.dataprovider.xmlpull.PullParse;

public class Common 
{
	/**
	 * 
	 * @Title: contrast
	 * @Description: str1是否包含str2
	 * @param str1
	 * @param str2
	 * @return
	 * @return: boolean
	 */
	public static boolean contrast(String str1, String str2)
	{
		boolean ret = false;
		if(str1 == null)
		{
			if(str2 == null)
			{
				ret = true;
			}
			else
			{
				ret = true;
			}
		}
		else if(str1.equals(str2))
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	
	/** 
     * 格式化时间单元(时、分、秒) 小于10的话在十位上补0，如传入2的话返回02，传入12的话返回12 
     * @param time 播放时间 
     * @return 格式化后的时间,如(02) 
     */  
    public static String formatTimeUnit(int time) 
    {  
        return time < 10 ? "0" + time : "" + time;  
    }  
	
	/** 
     * @param format_time 
     * @return (时:分:秒)格式的时间格式，如(00:03:00) 
     */  
    public static String formatTimeString(int format_time) 
    {  
    	int second = format_time / 1000;
        //String hours=formatTimeUnit(second / 3600);        //时  
        //String minutes=formatTimeUnit((second / 60) % 60); //分  
        String minutes=formatTimeUnit(second / 60); //分  
        String seconds=formatTimeUnit(second % 60); //秒  
        
        return /*hours + ":" + */minutes+ ":" + seconds;  
    }  
	
	/** 
     * @param current_time 当前播放时间 
     * @param total_time 总播放时间 
     * @return 当前播放时间/总播放时间，如(00:03:02/00:31:52) 
     */  
    public static String getFormatTime(int current_time, int total_time) 
    {  
        current_time = Math.abs(current_time); //得到当前播放时间的绝对值  
        total_time = Math.abs(total_time);     //得到总播放时间的绝对值  
        
        return formatTimeString(current_time) + "/" + formatTimeString(total_time);  
    }
    
    /**
	 * 
	 * @Title: setCoordinateOfView
	 * @Description: 设置坐标 (设置控件所在的位置YY，并且不改变宽高，XY为绝对位置)
	 * @param view
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return: void
	 */
	public static void setCoordinateOfView(View view,int x,int y, int width, int height)
	{
		RelativeLayout.LayoutParams rllp =new RelativeLayout.LayoutParams(view.getLayoutParams());
		rllp.leftMargin = x;
		rllp.topMargin = y;
		rllp.width = width;
		rllp.height = height;
		view.setLayoutParams(rllp);
	} 
	
	public static void setCoordinateOfView2(View view,int x,int y/*, int width, int height*/)
	{
		//使用MarginLayoutParams有bug
		MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
		int width = x+margin.width;
		int height = y+margin.height;
		margin.setMargins(x, y, width, height);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
		view.setLayoutParams(layoutParams);
	} 
	
	/**
	 * 
	 * @Title: amplifyItem
	 * @Description: 放大自定义View
	 * @param view   当前选中View
	 * @param focus  放大ImageView
	 * @param multi  放大倍数
	 * @return: void
	 */
	public static void amplifyItem(View view, ImageView focus, double multi)
	{
		int width = view.getWidth();
		int height = view.getHeight();
		int top = view.getTop();
		int left = view.getLeft();
		int addWidth = (int) ((float) multi * width);
		int addHeight = (int) ((float) multi * height);
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) focus.getLayoutParams();
		params.leftMargin = left - addWidth/2 - 28;
		params.topMargin = top - addHeight/2 -28 -2;
		params.width = width + addWidth + 28*2;
		params.height = height- 40 + addHeight + 28*2;
				                   
		focus.setLayoutParams(params);
		focus.setVisibility(View.VISIBLE);
		focus.bringToFront();
	}
	
	/**
	 * 
	 * @Title: getContent
	 * @Description: 获取字典内容
	 * @param context
	 * @param result
	 * @return
	 * @return: String
	 */
	public static String getContent(Context context, int result)
	{
		String content = "";
		ArrayList<DictionaryItem> dictonaryList = new ArrayList<DictionaryItem>();
		
		try 
		{
			dictonaryList = PullParse.parseDictionary(context);
		}
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " parseDictonary error.");
		}
		
		DictionaryItem item;
		DictionaryValueItem value;
		
		for (int i=0; i<dictonaryList.size(); i++)
		{
			item = dictonaryList.get(i);
			if (result == item.getStatus())
			{
				for (int j=0; j<item.getContentList().size(); j++)
				{
					value = item.getContentList().get(j);
					if (value.getLanguage().equals(StbInfo.getShowLaug()))
					{
						content = value.getContent();
					}
				}
			}
		}
		return content;
	}
	
	/**
	 * 
	 * @Title: convertViewToBitmap
	 * @Description: 将View截图转换成BitMap
	 * @param view 输入view
	 * @return
	 * @return: Bitmap
	 */
	public static Bitmap convertViewToBitmap(View view) 
	{
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
		MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		
		if (bitmap == null) 
		{
			return null;
		}
		return bitmap;
	}
	
	/**
	 * 
	 * @Title: createReflection
	 * @Description: 
	 * @param originalImage
	 * @param rate_reflection
	 * @param color0  主要改变透明度，如0x7fffffff，为半透明，定义倒影的起始透明度(上)   
	 * @param color1 主要改变透明度，如0x00ffffff，为完全透明，定义倒影的末端透明度(下)
	 * @return
	 * @return: Bitmap
	 */
	public static Bitmap createReflection(Bitmap originalImage, int height_reflection) 
	{
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflection = Bitmap.createBitmap(originalImage, 0, height - height_reflection, width, height_reflection, matrix, false);
		Bitmap reflectionImage = Bitmap.createBitmap(width, height_reflection, Config.ARGB_8888);
		Canvas canvas = new Canvas(reflectionImage);
		canvas.drawBitmap(reflection, 0, 0, null);
		Paint paint = new Paint();
		//透明度根据自己的情况设置, 该应用的倒影的起始透明度都设置成0x1fffffff
		//LinearGradient shader = new LinearGradient(0, 0, 0, height_reflection, 0x7fffffff, 0x00ffffff, TileMode.CLAMP);
		LinearGradient shader = new LinearGradient(0, 0, 0, height_reflection, 0x1fffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, 0, width, height_reflection, paint);
		return reflectionImage;
	}
	
	/**
	 * 
	 * @Title: createReflection
	 * @Description: 
	 * @param originalImage
	 * @param rate_reflection
	 * @param color0  主要改变透明度，如0x7fffffff，为半透明，定义倒影的起始透明度
	 * @param color1 主要改变透明度，如0x00ffffff，为完全透明，定义倒影的末端透明度
	 * @return
	 * @return: Bitmap
	 */
	public static Bitmap createReflection(Bitmap originalImage, int height_reflection, int color0, int color1) 
	{
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflection = Bitmap.createBitmap(originalImage, 0, height - height_reflection, width, height_reflection, matrix, false);
		Bitmap reflectionImage = Bitmap.createBitmap(width, height_reflection, Config.ARGB_8888);
		Canvas canvas = new Canvas(reflectionImage);
		canvas.drawBitmap(reflection, 0, 0, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, 0, 0, height_reflection, color0, color1, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, 0, width, height_reflection, paint);
		return reflectionImage;
	}
}
