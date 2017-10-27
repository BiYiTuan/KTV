/**
 * @Title: DialogAuth.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog
 * @Description: 鉴权结果dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午7:49:57
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;

public class DialogAuth extends Dialog implements OnClickListener 
{
	private DialogExitListener mListener = null;
	private Button   mBack = null;
	private TextView mContent;
	private String   mMessage; //信息
	
	public interface DialogExitListener
	{
		public void OnClickExit();
	}
	
	public DialogAuth(Context context, int theme)
	{
		super(context, theme);
	}
	
	public DialogAuth(Context context, String msg) 
	{
		super(context, R.style.exit_player_dialog);
		mMessage = msg;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置成系统级别的Dialog，即全局性质的Dialog，在任何界面下都可以弹出来
		getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  
		getWindow().setContentView(R.layout.dialog_auth);
		
		init();
	}
	
	private void init()
	{
		mBack = (Button)findViewById(R.id.id_auth_button);
		mBack.setOnClickListener(this);
		mBack.requestFocus();
		mContent = (TextView) findViewById(R.id.id_auth_text);
		mContent.setText(mMessage);
	}
	
	public void setOnDialogPromptListener(DialogExitListener listener)
	{
		mListener = listener;
	}

	@Override
	public void onClick(View view) 
	{
		mListener.OnClickExit();
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK) 
		{
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
