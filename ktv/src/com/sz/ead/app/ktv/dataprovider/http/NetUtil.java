/**
 * @Title: NetUtil.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.http
 * @Description: 网络类型
 * @author: zhaoqy
 * @date: 2015-8-13 下午2:47:24
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class NetUtil 
{
	/**
	 * 
	 * @Title: isNetConnected
	 * @Description: 网络是否连接
	 * @param context
	 * @return
	 * @return: boolean
	 */
	public static boolean isNetConnected(Context context) 
	{
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		
		if (networkInfo != null)
		{
			return networkInfo.isAvailable();
		}

		return false;
	}

	/**
	 * 获取网络类型
	 * @author zhaoqy 
	 * @param context Android上下文环境
	 * @return unknown 0, gprs 1, 3g 2, wifi 3
	 */
	public static int getNetType(Context context) 
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		
		if (activeNetInfo != null) 
		{
			int type = activeNetInfo.getType();
			
			if (type == ConnectivityManager.TYPE_WIFI)
			{
				return 3;
			}
			else if (type == ConnectivityManager.TYPE_MOBILE) 
			{
				type = activeNetInfo.getSubtype();
				
				if (type == TelephonyManager.NETWORK_TYPE_UMTS || type == TelephonyManager.NETWORK_TYPE_CDMA)
				{
					return 2;
				}
				else
				{
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @Title: getNetInfo
	 * @Description: 获取NetworkInfo
	 * @param context
	 * @return
	 * @return: NetworkInfo
	 */
	public static NetworkInfo getNetInfo(Context context) 
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetInfo;
	}
	
	/**
	 * 
	 * @Title: getWifiLevel
	 * @Description: 获取wifi强度
	 * @param context
	 * @return
	 * @return: int
	 */
	public static int getWifiLevel(Context context) 
	{
		int strength = 0;
		//Wifi的连接速度及信号强度：
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		if (info.getBSSID() != null) 
		{
			//链接信号强度，前一个是信号接收器，后一个表示最大的信号强度
			strength = WifiManager.calculateSignalLevel(info.getRssi(), 4);
		}
		return strength;
	}
}
