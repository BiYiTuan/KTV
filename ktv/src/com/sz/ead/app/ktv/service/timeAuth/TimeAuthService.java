/**
 * @Title: TimeAuthService.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.service.timeAuth
 * @Description: 定时鉴权(15分钟一次)
 * @author: zhaoqy
 * @date: 2015-6-8 下午8:31:07
 * @version: V1.0
 */

package com.sz.ead.app.ktv.service.timeAuth;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.sz.ead.app.ktv.dataprovider.entity.AuthResult;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.ui.dialog.DialogAuth;
import com.sz.ead.app.ktv.ui.dialog.DialogAuth.DialogExitListener;
import com.sz.ead.app.ktv.utils.Common;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.Token;

public class TimeAuthService extends Service implements UICallBack
{
	private AuthReceiver mReceiver = new AuthReceiver();
	private Context    mContext;  //上下文
	private AuthResult mAuthItem; //鉴权结果
	private DialogAuth mDialog;   //鉴权结果对话框
	private String     mContent;  //提示内容
	private int        mTime = 0; //定时鉴权次数
	
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		mContext = this;
		registerReceiver();
		//定时鉴权服务启动后, 15秒后, 开始第一次鉴权
		mHandler.sendEmptyMessageDelayed(FlagConstant.TIME_AUTH, 15*1000); 
	}
	
	private void registerReceiver()
	{
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
	    registerReceiver(mReceiver, intentFilter);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		return super.onStartCommand(intent, flags, startId);
	}
	
	/**
	 * 
	 * @Title: requestAuthResult
	 * @Description: 请求应用鉴权
	 * @return: void
	 */
	@SuppressLint("SimpleDateFormat")
	private void requestAuthResult()
	{
		mTime++;
		Logcat.i(FlagConstant.TAG, " TimeAuth requestAuth ");
		long time = System.currentTimeMillis();
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time));
		Logcat.i(FlagConstant.TAG, " date: " + date + ", " + mTime);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_AUTH, 0, 0);  
	}
	
	@Override
	public void onCancel(OutPacket out, int token) 
	{
	}

	@Override
	public void onSuccessful(Object in, int token) 
	{
		try 
		{
			switch (token) 
			{
			case Token.TOKEN_AUTH:
			{
				mAuthItem = (AuthResult) RequestDataManager.getData(in);
				checkAuthResult();
				break;
			}
			default:
				break;
			}
		} 
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " TimeAuth " + e.toString());
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		//15分钟后, 做下一次鉴权
		mHandler.sendEmptyMessageDelayed(FlagConstant.TIME_AUTH, 15*60*1000);  //15*60*1000
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}
	
	/**
	 * 
	 * @Title: sendMsgBroadCast
	 * @Description: 发送消息广播(通知Activity鉴权失败, 可以结束该应用)
	 * @return: void
	 */
	private void sendMsgBroadCast()
	{
		Logcat.i(FlagConstant.TAG, " TimeAuth sendMsgBroadCast");
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_TIMEAUTH_FAIL);
		mContext.sendBroadcast(intent);
	}
	
	/**
	 * 
	 * @Title: checkAuthResult
	 * @Description: 验证鉴权结果
	 * @return: void
	 */
	private void checkAuthResult()
	{
		int result = mAuthItem.getResult(); 
		Logcat.i(FlagConstant.TAG, " TimeAuth result: " + result);
		//0: 表示成功;
		if (result == 0)  
		{
			//15分钟后, 做下一次鉴权
			mHandler.sendEmptyMessageDelayed(FlagConstant.TIME_AUTH, 15*60*1000);  
		}
		else
		{
			if (!isBackground(mContext)) 
			{
				showAuthFailResult(result);
				mHandler.sendEmptyMessageDelayed(FlagConstant.TIME_AUTH, 15*60*1000); 
			}
			else
			{
				mHandler.sendEmptyMessageDelayed(FlagConstant.TIME_AUTH, 15*60*1000); 
			}
		}
	}
	
	/**                                                                                                                                           
	 * 
	 * @Title: showAuthFailResult
	 * @Description: 显示鉴权失败结果                                                                                   
	 * @param result
	 * @return: void
	 */
	private void showAuthFailResult(int result)
	{
		mContent = Common.getContent(mContext, result);
		createDialogAuth();
	}
	
	/**
	 * 
	 * @Title: isBackground
	 * @Description: 是否在后台运行
	 * @param context
	 * @return
	 * @return: boolean
	 */
	public static boolean isBackground(Context context) 
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) 
		{
			if (appProcess.processName.equals(context.getPackageName())) 
			{
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) 
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: createDialogAuthResult
	 * @Description: 创建鉴权失败对话框
	 * @param msg
	 * @return: void
	 */
	private void createDialogAuth()
	{
		if (mDialog != null && mDialog.isShowing())
		{
			return;
		}
		
		mDialog = new DialogAuth(mContext, mContent);
		mDialog.setOnDialogPromptListener(new DialogExitListener()
    	{
			@Override
			public void OnClickExit() 
			{
				mDialog.dismiss();
				//发送鉴权失败广播
				sendMsgBroadCast();
			}
    	});
		mDialog.show();
	}
	
	public class AuthReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent) 
		{
			if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) 
			{
	            String reason = intent.getStringExtra("reason");
	            if (reason != null && reason.equals("homekey")) 
	            {
	            	if(mDialog != null && mDialog.isShowing())
                	{
                		mDialog.dismiss();
                	}
	            }
	        }
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() 
	{
		public void handleMessage(Message msg) 
		{
			switch (msg.what) 
			{
			case FlagConstant.TIME_AUTH:
			{
				requestAuthResult();
				break;
			}
			default:
				break;
			}
		}
	};
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		
		if(mHandler != null)
		{
			Logcat.i(FlagConstant.TAG, " close TimeAuth mHandler.");
			mHandler.removeCallbacksAndMessages(null);
		}
		
		if(mReceiver != null)
		{
			unregisterReceiver(mReceiver);
		}
		
		if(mDialog != null && mDialog.isShowing())
    	{
    		mDialog.dismiss();
    	}
	}
}
