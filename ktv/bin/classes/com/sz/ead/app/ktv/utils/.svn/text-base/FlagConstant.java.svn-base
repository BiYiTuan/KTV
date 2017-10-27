/**
 * @Title: FlagConstant.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 标记常量
 * @author: zhaoqy
 * @date: 2015-8-13 下午3:06:59
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import com.sz.ead.app.ktv_wakg.R;

public class FlagConstant 
{
	public static final String TAG = "ktv_wakg";   //打印标签
	
	//视频加载失败错误码
	public static final String ERROR_PLAYER = "P";  //播放器
	public static final String ERROR_LIBRARY = "L"; //流媒体库
	public static final String ERROR_TIMEOUT = "T"; //加载超时
	public static final String ERROR_NETWORK = "N"; //网络异常
	
	public static final int HOME_FINISH = 0;        //应用启动页完成
	public static final int HOME_REQUEST_VIDEO = 1; //请求资源详情
	public static final int HOME_BITMAP = 2;        //Bitmap生成完成
	public static final int HOME_WANT_EXIT_APP = 3; //想退出应用
	
	//播放状态
	public static final int PLAY_TIMEOUT = 1;        //加载超时
	public static final int PLAY_ERROR = 2;          //加载失败
	public static final int PLAY_SET_URL = 3;        //设置URL
	public static final int PLAY_COMPLETE = 4;       //播放完成
	public static final int PLAY_START = 5;          //开始播放
	public static final int PLAY_RETASK = 6;         //重新开始任务
	public static final int PLAY_CUT_SONG = 7;       //切歌
	public static final int PLAY_PRESERVE_START = 8; //开始维护
	public static final int PLAY_PRESERVE_END = 9;   //完成维护
	public static final int PLAY_PRESERVE_HIDE = 10; //隐藏维护界面
	public static final int PLAY_WANT_EXIT = 11;     //想退出播放
	
	public static final int TIME_AUTH = 20;          //鉴权倒计时
	
	public static final int SCALE_VOD_BIG_ANIMS = R.anim.scale_vod_big_action;
	public static final int SCALE_VOD_SMALL_ANIMS = R.anim.scale_vod_small_action;
	
	public static final String COLUMN = "column";
	public static final String VIDEO = "video";
	
	public static boolean gInitPler = false; //是否已经初始化ppp
	public static int     gPlayerRet = -1;   //初始化PPP服务结果
	
	public static void init()
	{
		gInitPler = false;
		gPlayerRet = -1;
	}
}
