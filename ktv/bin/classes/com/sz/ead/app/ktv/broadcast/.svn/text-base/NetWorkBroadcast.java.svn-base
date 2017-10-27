/**
 * @Title: NetWorkBroadcast.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.broadcast
 * @Description: 网络广播
 * @author: zhaoqy
 * @date: 2015-8-13 下午2:44:27
 * @version: V1.0
 */

package com.sz.ead.app.ktv.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class NetWorkBroadcast extends BroadcastReceiver 
{
	public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) 
		{
			if(!NetUtil.isNetConnected(context))
			{
				ToastUtils.getShowToast().showAnimationToast(context, context.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
			}
		}
	}
}
