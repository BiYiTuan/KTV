/**
 * @Title: BaseActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 基Activity
 * @author: zhaoqy
 * @date: 2015-7-29 下午4:24:08
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.sz.ead.app.ktv.application.UILApplication;
import com.sz.ead.app.ktv.utils.Constant;

public abstract class BaseActivity extends FragmentActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		UILApplication.getInstance().addActivity(this);
		
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("android.intent.action.SCREEN_OFF");  //待机键广播
	    intentFilter.addAction(Constant.ACTION_TIMEAUTH_FAIL);       //定时鉴权失败广播
	    registerReceiver(mBroadcastReceiver, intentFilter);
	}

	@Override
	protected void onDestroy() 
	{
		UILApplication.getInstance().removeActivity(this);
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
	
	protected BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() 
	{
		@Override
	    public void onReceive(Context context, Intent intent) 
	    {
			BaseActivity.this.onReceive(intent);
		}
	};
	
	protected void onReceive(Intent intent) 
	{
		this.finish();
	}
}
