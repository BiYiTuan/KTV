/**
 * @Title: PPPServer.java
 * @Prject: itvvodtemp2
 * @Package: com.sz.ead.framework.player
 * @Description: ppp流媒体服务
 * @author: zhaoqy
 * @date: 2015-6-8 下午5:22:18
 * @version: V1.0
 */

package com.sz.ead.framework.player;

import android.content.Context;
import android.text.TextUtils;

import com.sz.ead.app.hostdownloader.HostUtil;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.StbInfo;
import com.sz.ead.framework.player.PlayUriParser.uriCallBack;

public class PPPServer 
{
	/**
	 * 
	 * @Title: initPPPServer
	 * @Description: 初始化PPP服务
	 * @return: void
	 */
	public static void initPPPServer(Context context)  
	{
		//String serv_ip = "npxyc.prsolive.com:7901|aspxyu.prsolive.com:7901|soreq.soppp.net:7901|pydjosm.soppp.net:7901";
		String serv_ip = getPPPip(context);
		if (TextUtils.isEmpty(serv_ip))
		{
			serv_ip = "poxy.likektv.top:7901|poxy.buddhisms.top:7901";
		}
		String urs_name = StbInfo.getCPUID(context);   
		String pwd = StbInfo.getMacAddress(context); 
		String type = Constant.APPID;
		String key = "aewrasdeiotasd";
		String version = PlayUriParser.getHttpUri().GetPlayerVersion();
		FlagConstant.gPlayerRet = PlayUriParser.getHttpUri().PPPServiceInit(serv_ip, urs_name, pwd, key, type);
		
		Logcat.i(FlagConstant.TAG, " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Logcat.i(FlagConstant.TAG, " cpuid: " + urs_name + ",  mac: " + pwd + ",  version: " + version);
		Logcat.i(FlagConstant.TAG, " gPlayerRet: " + FlagConstant.gPlayerRet);
		Logcat.i(FlagConstant.TAG, " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		initForceTV(context);
		FlagConstant.gInitPler = true;
	}
	
	/**
	 * 
	 * @Title: stopService
	 * @Description: 关闭服务
	 * @return: void
	 */
	public static void stopService()
	{
		Logcat.d(FlagConstant.TAG, " gInitPler: " + FlagConstant.gInitPler);
		if (FlagConstant.gInitPler)
		{
			PlayUriParser.getHttpUri().stopService();
		}
	}
	
	/**
	 * 
	 * @Title: stopPlay
	 * @Description: 关闭播放
	 * @return: void
	 */
	public static int stopPlay()
	{
		return PlayUriParser.getHttpUri().stopPlay();
	}
	
	/**
	 * 
	 * @Title: startToHttpUri
	 * @Description: 流媒体地址转http地址
	 * @return: void
	 */
	public static void startToHttpUri()
	{
		PlayUriParser.getHttpUri().startToHttpUri();
	}
	
	/**
	 * 
	 * @Title: setPlayUri
	 * @Description: 设置流媒体的播放地址
	 * @param url
	 * @return: void
	 */
	public static void setPlayUri(String url)
	{
		PlayUriParser.getHttpUri().setPlayUri(url);
	}
	
	/**
	 * 
	 * @Title: setCallBack
	 * @Description: 设置回调函数
	 * @param activity
	 * @return: void
	 */
	public static void setCallBack(uriCallBack activity)
	{
		PlayUriParser.getHttpUri().setCallBack(activity);
	}
	
	/**
	 * 
	 * @Title: initForceTV
	 * @Description: forcetv 点播播放初始化函数,限普通apk调用，系统apk不用调用
	 * @param context
	 * @return: void
	 */
	public static void initForceTV(Context context)
	{
		PlayUriParser.getHttpUri().ForceTvInit(context);
	}
	
	/**
	 * 
	 * @Title: getBufferStatus
	 * @Description: 获取缓存百分比，建议1s获取一次
	 * @return
	 * @return: int
	 */
	public static int getBufferStatus()
	{
		return PlayUriParser.getHttpUri().PPPGetBufferStatus();
	}
	
	public static String getPPPip(Context context)
	{
		return HostUtil.getService();
	}
}
