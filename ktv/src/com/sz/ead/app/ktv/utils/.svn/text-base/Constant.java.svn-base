/**
 * @Title: Constant.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 常量
 * @author: zhaoqy
 * @date: 2015-8-6 下午3:38:39
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import android.content.Context;
import android.text.TextUtils;

import com.sz.ead.app.hostdownloader.HostUtil;

public class Constant 
{
	public static final boolean URL_LOCAL_SERVICE = false; //是不是本地服务器 自己电脑作为服务器
	public static final int TERMAL_TYPE = 4;               //终端类型:1:tvpad; 2:pc; 3:ios; 4:android
	public static final int NETWORK_TIMEOUT = 15000;       //网络请求的超时时间设置
	public static final int OTHER_ERROR = -999;            //客户端未知错误
	
	public static final String APP_VERSION = "ktv1.07";   //应用显示版本
	public static final String APPID = "com.sz.ead.app.ktv_wakg";  
	public static final String REQUEST_URL_MAIN_HOST = "http://appepg.wsxtvepg.com";  
	
	//public static final String REQUEST_URL_PORT = "8080";
	//public static final String REQUEST_URL_HOST = REQUEST_URL_MAIN_HOST + ":" + REQUEST_URL_PORT;
	
	public static final String HTTP_DOMAIN_NAME1 = "http://appepg.argae.nz:8080";
	public static final String HTTP_DOMAIN_NAME2 = "http://caepg.ibtso.top:8080";
	public static String http_down_host1;
	public static String http_down_host2;
	public static String REQUEST_URL_HOST = ""; 
	
	public static final String ACTION_FRESH_SELECTED = "com.sz.ead.app.ktv_wakg.action.fresh_selected";           //刷新播放页面的已点列表
	public static final String ACTION_FRESH_MENU_SELECTED = "com.sz.ead.app.ktv_wakg.action.fresh_menu_selected"; //刷新菜单页面的已点列表
	public static final String ACTION_FRESH_SUNG = "com.sz.ead.app.ktv_wakg.action.fresh_sung";                   //刷新已唱列表
	public static final String ACTION_CUT_SONG = "com.sz.ead.app.ktv_wakg.action.cut_song";                       //切歌
	public static final String ACTION_TIMEAUTH_FAIL = "com.sz.ead.app.ktv_wakg.action.timeauthfail";              //定时鉴权失败广播
	
	public static void initEPGHost(Context context)
	{
		HostUtil.init(context);
		http_down_host1 = HostUtil.getHttp();
		http_down_host2 = HostUtil.getHttpSub();
		if (TextUtils.isEmpty(http_down_host1))
		{
			http_down_host1 = HTTP_DOMAIN_NAME1;
			http_down_host2 = HTTP_DOMAIN_NAME2;
		}
		else
		{
			if (TextUtils.isEmpty(http_down_host2))
			{
				http_down_host2 = http_down_host1;
			}
		}
		REQUEST_URL_HOST = http_down_host1;
	}
	
	public static void changeEPGHost()
	{
		if (REQUEST_URL_HOST.equalsIgnoreCase(http_down_host1))
		{
			REQUEST_URL_HOST = http_down_host2;
		}
		else if (REQUEST_URL_HOST.equalsIgnoreCase(http_down_host2))
		{
			REQUEST_URL_HOST = http_down_host1;
		}
	}
}
