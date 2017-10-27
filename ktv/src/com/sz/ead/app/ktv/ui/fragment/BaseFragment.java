/**
 * @Title: BaseFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: BaseFragment
 * @author: zhaoqy
 * @date: 2015-8-13 下午1:51:23
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.fragment;

import java.lang.reflect.Field;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public abstract class BaseFragment extends Fragment 
{
	@Override
	public void onDetach() 
	{
		super.onDetach();
		//用来初始化ChildFragmentManager,避免出现null应用异常
		try 
		{
		    Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
		    childFragmentManager.setAccessible(true);
		    childFragmentManager.set(this, null);
		}
		catch (Exception e) 
		{
		    throw new RuntimeException(e);
		}
	}
	
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean handled = false;
		return handled;
	}
}
