/**
 * @Title: DialogSearchSinger.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog
 * @Description: 搜索歌手Dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午3:39:05
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog;

import java.lang.reflect.Method;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.sz.ead.app.ktv_wakg.R;

public class DialogSearchSinger extends Dialog implements android.view.View.OnClickListener
{
	private Context mContext;
	private View[]  mViews = new View[28];
	
	public DialogSearchSinger(Context context, int theme)
	{
		super(context, theme);
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.END);
        dialogWindow.setAttributes(lp);
        
        setContentView(R.layout.view_searchsinger_keyboard);
		findViews();
		setListeners();
	}
	
	private void findViews()
	{
		int resid[] = { R.id.tv_search_a, R.id.tv_search_b, R.id.tv_search_c, R.id.tv_search_d, R.id.tv_search_e, 
		                R.id.tv_search_f, R.id.tv_search_g, R.id.tv_search_h, R.id.tv_search_i, R.id.tv_search_j, 
		                R.id.tv_search_k, R.id.tv_search_l, R.id.tv_search_m, R.id.tv_search_n, R.id.tv_search_o,
		                R.id.tv_search_p, R.id.tv_search_q, R.id.tv_search_r, R.id.tv_search_s, R.id.tv_search_t,
		                R.id.tv_search_u, R.id.tv_search_v, R.id.tv_search_w, R.id.tv_search_x, R.id.tv_search_y,
		                R.id.tv_search_z, R.id.tv_search_del, R.id.tv_search_clear };
		for (int i=0; i<28; i++)
		{
			mViews[i] = findViewById(resid[i]);
		}
	}
	
	private void setListeners()
	{
		for (int i=0; i<28; i++)
		{
			mViews[i].setOnClickListener(this);
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean nRet = false;
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			switch (event.getKeyCode()) 
			{
			case KeyEvent.KEYCODE_MENU:
			case KeyEvent.KEYCODE_BACK:
			{
				cancel();
				break;
			}
			default:
				break;
			}
		}
		
		if (nRet)
		{
			return nRet;
		}
		else 
		{
			return super.dispatchKeyEvent(event);  
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.tv_search_del:
		{
			try 
			{
				Method mHandler = mContext.getClass().getMethod("doKeyboardDel", View.class);
				mHandler.invoke(mContext, v);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			break;
		}
		case R.id.tv_search_clear:
		{
			try 
			{
				Method mHandler = mContext.getClass().getMethod("doKeyboardClear", View.class);
				mHandler.invoke(mContext, v);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			break;
		}
		default:
			try 
			{
				Method mHandler = mContext.getClass().getMethod("doKeyboardCharPress", View.class);
				mHandler.invoke(mContext, v);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			break;
		}
	}
}
