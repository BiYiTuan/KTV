/**
 * @Title: Logcat.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 打印日志
 * @author: zhaoqy
 * @date: 2015-8-13 下午2:48:08
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import android.util.Log;

public class Logcat 
{
	private static boolean sVerbose = true; //冗长
	private static boolean sDebug = false;   //调试
	private static boolean sInfo = true;    //信息
	private static boolean sWarn = true;    //警告
	private static boolean sError = true;   //错误
	
	public static void v(String tag, String msg)
	{
		if (sVerbose)
		{
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag, String msg)
	{
		if (sDebug)
		{
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag, String msg)
	{
		if (sInfo)
		{
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag, String msg)
	{
		if (sWarn)
		{
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag, String msg)
	{
		if (sError)
		{
			Log.e(tag, msg);
		}
	}
}
