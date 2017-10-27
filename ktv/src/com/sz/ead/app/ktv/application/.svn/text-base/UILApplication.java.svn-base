/**
 * @Title: UILApplication.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.application
 * @Description: Application
 * @author: zhaoqy
 * @date: 2015-7-29 下午4:22:43
 * @version: V1.0
 */

package com.sz.ead.app.ktv.application;

import java.util.LinkedList;
import android.app.Activity;
import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UILApplication extends Application 
{
	private static UILApplication mApplication;
	private LinkedList<Activity> mActivityList = new LinkedList<Activity>();

	@Override
	public void onCreate() 
	{
		super.onCreate();
		mApplication = this;
	}

	@Override
	public void onTerminate() 
	{
		ImageLoader.getInstance().clearMemoryCache();
		super.onTerminate();
	}
	
	public synchronized static UILApplication getInstance() 
	{
		return mApplication;
	}

	public void addActivity(Activity activity) 
	{
		mActivityList.addLast(activity);
	}

	public void removeActivity(Activity activity) 
	{
		for (int i=0; i<mActivityList.size(); i++) 
		{
			if (mActivityList.get(i).getClass().getName().equals(activity.getClass().getName())) 
			{
				mActivityList.remove(i);
				i--;
				break;
			}
		}
	}

	//遍历所有Activity并finish
	public void exit() 
	{
		for (int i=0; i<mActivityList.size(); i++) 
		{
			mActivityList.getFirst().finish();
			mActivityList.removeFirst();
			i--;
		}
	}
}