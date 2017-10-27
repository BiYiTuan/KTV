/**
 * @Title: BiMsg.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.bi
 * @Description: BI消息(消息编码: 指令类型(**) + 指令代码(***))
 * @author: zhaoqy
 * @date: 2015-8-17 下午5:51:33
 * @version: V1.0
 */

package com.sz.ead.app.ktv.bi;

import android.content.Context;

import com.sz.ead.app.ktv.utils.AppInfo;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.StbInfo;
import com.sz.ead.framework.newbi.NewBi;

public class BiMsg 
{
	/**
	 * 1 点击搜索按钮 format: {"01004",{"应用ID","搜索内容"}}
	 * 
	 * @Title: sendSearchBiMsg
	 * @Description: 点击搜索(输入关键字搜索)
	 * @param word
	 * @return: void
	 */
	public static void sendSearchBiMsg(String word) 
	{
		BI.sendBiMsg(NewBi.BICT_CLICK, 
				     NewBi.BICC_CLICK_SEARCH, 
				     Constant.APPID, 
				     word);
		Logcat.i(FlagConstant.TAG, " sendSearchBiMsg ");
	}

	/**
	 * 2 点击收藏列表中的一个item进入详情 format: {"01006",{"应用ID","影片id","影片名称","分类id","分类名称"}}
	 * 
	 * @Title: sendFavoriteBiMsg
	 * @Description: 点击收藏
	 * @param code
	 * @param name
	 * @param classifyCode
	 * @param classifyName
	 * @return: void
	 */
	public static void sendFavoriteBiMsg(String code, String name, String classifyCode, String classifyName) 
	{
		BI.sendBiMsg(NewBi.BICT_CLICK, 
				     NewBi.BICC_CLICK_COLLECT, 
				     Constant.APPID, 
				     code, 
				     name,
				     classifyCode, 
				     classifyName);
		Logcat.i(FlagConstant.TAG, " sendFavoriteBiMsg ");
	}

	/**
	 * 3 从开始缓冲点播剧集到开始播放的时间 format: {"02004",{"应用ID","影片id","影片名称","耗时","分类id","分类名称","剧集id","剧集名称"}}
	 * 
	 * @Title: sendDemandBufferTime
	 * @Description: 点播缓冲时长
	 * @param code
	 * @param name
	 * @param classifyCode
	 * @param classifyName
	 * @param dramaCode
	 * @param dramaName
	 * @param id
	 * @return: void
	 */
	public static void sendDemandBufferTimeBiMsg(String code, String name, String classifyCode, String classifyName, String dramaCode, String dramaName, int id) 
	{
		BI.sendBiMsg(NewBi.BICT_TIME, 
				     NewBi.BICC_TIME_VOD_BUFF, 
				     Constant.APPID, 
				     code, 
				     name,
				     BI.getConsumingTime(id), 
				     classifyCode, 
				     classifyName, 
				     dramaCode, 
				     dramaName);
		Logcat.i(FlagConstant.TAG, " sendDemandBufferTimeBiMsg ");
	}

	/**
	 * 4 取点播列表耗时 format: {"02009",{ "应用ID","父级ID","列表级数","耗时"}}
	 * 
	 * @Title: sendDemandListTime
	 * @Description: 点播列表耗时
	 * @param pid
	 * @param Series
	 * @param id
	 * @return: void
	 */
	public static void sendDemandListTimeBiMsg(String pid, String series, int id) 
	{
		BI.sendBiMsg(NewBi.BICT_TIME, 
				     NewBi.BICC_TIME_GET_VOD_LIST, 
				     Constant.APPID, 
				     pid, 
				     series,
				     BI.getConsumingTime(id));
		Logcat.i(FlagConstant.TAG, " sendDemandListTimeBiMsg ");
	}

	/**
	 * 5 搜索耗时 format: {"02010",{"应用ID","耗时"}}
	 * 
	 * @Title: sendSearchTime
	 * @Description: 搜索时长
	 * @param id
	 * @return: void
	 */
	public static void sendSearchTimeBiMsg(int id) 
	{
		BI.sendBiMsg(NewBi.BICT_TIME, 
				     NewBi.BICC_TIME_SEARCH, 
				     Constant.APPID,
				     BI.getConsumingTime(id));
		Logcat.i(FlagConstant.TAG, " sendSearchTimeBiMsg ");
	}

	/**
	 * 6 从进入播放界面到退出播放界面的时间 播放类型：0，直播；1,点播 format: {"02011",{"播放类型","应用id","影片id", "影片名称","播放时长","分类id","分类名称","剧集id","剧集名称"}}
	 * 
	 * @Title: sendPlayTime
	 * @Description: 播放时长
	 * @param type
	 * @param code
	 * @param name
	 * @param classifyCode
	 * @param classifyName
	 * @param dramaCode
	 * @param dramaName
	 * @param id
	 * @return: void
	 */
	public static void sendPlayTimeBiMsg(String type, String code, String name, String classifyCode, String classifyName, String dramaCode, String dramaName, int id) 
	{
		BI.sendBiMsg(NewBi.BICT_TIME, 
				     NewBi.BICC_TIME_PLAY, 
				     type, 
				     Constant.APPID, 
				     code, 
				     name,
				     BI.getConsumingTime(id), 
				     classifyCode, 
				     classifyName, 
				     dramaCode, 
				     dramaName);
		Logcat.i(FlagConstant.TAG, " sendPlayTimeBiMsg ");
	}

	/**
	 * 7 点击热词搜索 format: {"01002",{"应用ID","搜索热词"}}
	 * 
	 * @Title: sendHotSearchBiMsg
	 * @Description: 点击热词搜索
	 * @param word
	 * @return: void
	 */
	public static void sendHotSearchBiMsg(String word) 
	{
		BI.sendBiMsg(NewBi.BICT_CLICK, 
				     NewBi.BICC_HOT_SEARCH, 
				     Constant.APPID,
				     word);
		Logcat.i(FlagConstant.TAG, " sendHotSearchBiMsg ");
	}

	/**
	 * 8 format: {"01008",{"应用ID","来源类型"}}
	 * 
	 * @Title: sendVodDetail
	 * @Description: 影视详情
	 * @param type (来源类型:1专题,2推荐,3收藏,4历史,5搜索,6点播)
	 * @return: void
	 */
	public static void sendVodDetailBiMsg(String type) 
	{
		BI.sendBiMsg(NewBi.BICT_CLICK, 
				     NewBi.BICC_MOVIE_DETAILS, 
				     Constant.APPID, 
				     type);
		Logcat.i(FlagConstant.TAG, " sendVodDetailBiMsg ");
	}

	/**
	 * 9 format: {"00006",{"应用id","版本","语种"，"耗时"，”结果值”}} NewBi.BICT_FIRMWARE 和
	 * NewBi.BICC_APP_START 拼成 00006
	 * 
	 * @Title: sendAppStartBiMsg
	 * @Description: 启动应用
	 * @param context
	 * @return: void
	 */
	public static void sendAppStartBiMsg(Context context, int id) 
	{
		BI.sendBiMsg(NewBi.BICT_FIRMWARE, 
				     NewBi.BICC_APP_START, 
				     Constant.APPID,
				     AppInfo.getVersionName(context), 
				     StbInfo.getShowLaug(), 
				     BI.getConsumingTime(id),
					 "0");
		Logcat.i(FlagConstant.TAG, " sendAppStartBiMsg ");
	}

	/**
	 * 10 format: {"00007",{"应用id","版本","语种"}}
	 * 
	 * @Title: sendAppExitBiMsg
	 * @Description: 退出应用
	 * @param context
	 * @return: void
	 */
	public static void sendAppExitBiMsg(Context context) 
	{
		BI.sendBiMsg(NewBi.BICT_FIRMWARE, 
				     NewBi.BICC_APP_EXIT, 
				     Constant.APPID,
				     AppInfo.getVersionName(context), 
				     StbInfo.getShowLaug());
		Logcat.i(FlagConstant.TAG, " sendAppExitBiMsg ");
	}
	
	/**
	 * 11 format: {"02006",{"应用ID","耗时"}}
	 * @Title: sendFeeTimeBiMsg
	 * @Description: 付费时间
	 * @param id
	 */
	public static void sendFeeTimeBiMsg(int id) 
	{
		BI.sendBiMsg(NewBi.BICT_TIME, 
				     NewBi.BICC_TIME_RECHARGE, 
				     Constant.APPID,
				     BI.getConsumingTime(id));
		Logcat.i(FlagConstant.TAG, " sendFeeTimeBiMsg ");
	}
}
