/**
 * @Title: ToastUtils.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 自定义Toast
 * @author: zhaoqy
 * @date: 2015-8-10 上午11:40:14
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import java.lang.reflect.Field;
import android.content.Context;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;

public class ToastUtils 
{
	private static ToastUtils mShowToast = new ToastUtils();
	private static Toast toast = null;
	private static Toast animationToast = null;

	public static ToastUtils getShowToast() 
	{
		if (mShowToast == null) 
		{
			mShowToast = new ToastUtils();
		}
		return mShowToast;
	}

	public void showToast(Context context, String text) 
	{
		if (toast == null) 
		{
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} 
		else 
		{
			toast.setText(text);
		}
		toast.show();
	}

	public void showAnimationToast(Context context, String text, int duration) 
	{
		if (animationToast == null) 
		{
			animationToast = Toast.makeText(context, text, duration);
			animationToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
			ViewGroup view = (ViewGroup) animationToast.getView();
			view.setBackgroundResource(R.drawable.notice);
			setToastAnim(animationToast);
			TextView tv = (TextView) view.getChildAt(0);
			tv.setTextColor(context.getResources().getColor(R.color.notice_red));
			
			//TypedValue.COMPLEX_UNIT_PX : Pixels
			//TypedValue.COMPLEX_UNIT_SP : Scaled Pixels
			//TypedValue.COMPLEX_UNIT_DIP : Device Independent Pixels
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
			tv.setPadding(0, 36, 0, 0);
		} 
		else 
		{
			animationToast.setText(text);
		}
		animationToast.show();
	}
	
	/**
	 * 
	 * @Title: showAnimationAddTipToast
	 * @Description: 在顶部显示自定义的toast
	 * @param context
	 * @param text
	 * @param duration
	 */
	public void showAnimationAddTipToast(Context context, Spanned text, int duration) 
	{
		if (animationToast == null) 
		{
			animationToast = Toast.makeText(context, text, duration);
			animationToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
			ViewGroup view = (ViewGroup) animationToast.getView();
			view.setBackgroundResource(R.drawable.notice);
			setToastAnim(animationToast);
			TextView tv = (TextView) view.getChildAt(0);
			tv.setTextColor(context.getResources().getColor(R.color.notice_red));
			
			//TypedValue.COMPLEX_UNIT_PX : Pixels
			//TypedValue.COMPLEX_UNIT_SP : Scaled Pixels
			//TypedValue.COMPLEX_UNIT_DIP : Device Independent Pixels
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
			tv.setPadding(0, 36, 0, 0);
		} 
		else 
		{
			animationToast.setText(text);
		}
		animationToast.show();
	}
	
	/**
	 * 
	 * @Title: setToastAnim
	 * @Description: 设置新的动画
	 * @param mToast
	 */
	private static void setToastAnim(Toast mToast) 
	{
		try 
		{
			Object mTN = null;
			mTN = getField(mToast, "mTN");
			if (mTN != null) 
			{
				Object mParams = getField(mTN, "mParams");
				if (mParams != null && mParams instanceof WindowManager.LayoutParams) 
				{
					WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
					params.windowAnimations = R.style.AnimationTopToast;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private static Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException 
	{
		Field field = object.getClass().getDeclaredField(fieldName);
		if (field != null) 
		{
			field.setAccessible(true);
			return field.get(object);
		}
		return null;
	}
}