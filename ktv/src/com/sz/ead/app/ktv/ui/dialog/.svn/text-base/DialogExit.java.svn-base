/**
 * @Title: DialogExit.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog
 * @Description: 退出播放dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午7:57:56
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.sz.ead.app.ktv_wakg.R;

public class DialogExit extends Dialog implements OnClickListener 
{
	private DialogExitListener mListener = null;
	private Button mSure = null;
	private Button mCancel = null;
	
	public interface DialogExitListener
	{
		public void OnDialogExit();
		public void OnDialogCancel();
	}
	
	public DialogExit(Context context, int theme)
	{
		super(context, theme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_exit);
		init();
	}
	
	private void init()
	{
		mCancel = (Button)findViewById(R.id.id_exit_cancel);
		mCancel.setOnClickListener(this);
		mSure = (Button)findViewById(R.id.id_exit_sure);
		mSure.setOnClickListener(this);
		mSure.requestFocus();
	}
	
	public void setOnDialogExitListener(DialogExitListener listener)
	{
		mListener = listener;
	}

	@Override
	public void onClick(View view) 
	{
		switch (view.getId()) 
		{
		case R.id.id_exit_cancel:
		{
			mListener.OnDialogCancel();
			break;
		}
		case R.id.id_exit_sure:
		{
			mListener.OnDialogExit();	
			break;
		}
		default:
			break;
		}
	}
}
