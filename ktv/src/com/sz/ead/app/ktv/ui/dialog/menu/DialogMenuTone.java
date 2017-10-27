/**
 * @Title: DialogMenuTone.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu
 * @Description: 调音dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午9:31:39
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;

public class DialogMenuTone extends Dialog implements OnSeekBarChangeListener
{
	private Context        mContext;    //上下文
	private RelativeLayout mAccompany;  //伴奏
	private RelativeLayout mMicrophone; //话筒
	private TextView       mProgress1;
	private TextView       mProgress2;
	private SeekBar        mSeekbar1;
	private SeekBar        mSeekbar2;
	private AudioManager   mAudioManager;  //音频管理器
	private int            mMaxVolume = 0; //最大音量值
	private int            mCurVolume = 0; //当前音量值
	
	public DialogMenuTone(Context context, int theme)
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
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        
        setContentView(R.layout.dialog_menu_tone);
        findViews();
        setListeners();
        setAudioManager();
	}

	private void findViews() 
	{
		mAccompany = (RelativeLayout) findViewById(R.id.id_menu_tone1);
		mMicrophone = (RelativeLayout) findViewById(R.id.id_menu_tone2);
		mProgress1 = (TextView) findViewById(R.id.id_menu_tone1_progress);
		mProgress2 = (TextView) findViewById(R.id.id_menu_tone2_progress);
		mSeekbar1 = (SeekBar) findViewById(R.id.id_menu_tone1_seekbar);
		mSeekbar2 = (SeekBar) findViewById(R.id.id_menu_tone2_seekbar);
	}
	
	private void setListeners()
	{
		//mSeekbar1.setOnSeekBarChangeListener(this);
		mSeekbar2.setOnSeekBarChangeListener(this);
	}
	
	/**
	 * 
	 * @Title: setAudioManager
	 * @Description: 设置音频管理器
	 * @return: void
	 */
	private void setAudioManager()
	{
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mCurVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSeekbar1.setMax(mMaxVolume);
		mSeekbar1.setProgress(mCurVolume);
		mProgress1.setText("" + mCurVolume);
		
		int progress = mSeekbar2.getProgress();
		mSeekbar2.setProgress(progress);
		mProgress2.setText("" + progress);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
	{
		switch (seekBar.getId()) 
		{
		case R.id.id_menu_tone1_seekbar:
		{
			mProgress1.setText("" + progress);
			break;
		}
		case R.id.id_menu_tone2_seekbar:
		{
			mProgress2.setText("" + progress);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) 
	{
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) 
	{
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean nRet = false;
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			switch (event.getKeyCode()) 
			{
			case KeyEvent.KEYCODE_DPAD_LEFT:
			{
				nRet = doKeyLeft();
				break;
			}
			case KeyEvent.KEYCODE_DPAD_RIGHT:
			{
				nRet = doKeyRight();
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
	
	private boolean doKeyLeft()
	{
		boolean nRet = false;
		
		if(mAccompany.isFocused())
		{
			onVolumeSub(true);
			freshCurVolume();
			nRet = true;
		}
		else if(mMicrophone.isFocused())
		{
			int progress = mSeekbar2.getProgress();
			progress --;
			if(progress < 0)
			{
				progress = 0;
			}
			mSeekbar2.setProgress(progress);
		}
		return nRet;
	}
	
	private boolean doKeyRight()
	{
		boolean nRet = false;
		
		if(mAccompany.isFocused())
		{
			onVolumeSub(false);
			freshCurVolume();
			nRet = true;
		}
		else if(mMicrophone.isFocused())
		{
			int progress = mSeekbar2.getProgress();
			progress ++;
			if(progress > 100)
			{
				progress = 100;
			}
			mSeekbar2.setProgress(progress);
		}
		return nRet;
	}
	
	/**
	 * 
	 * @Title: freshCurVolume
	 * @Description: 设置当前音量
	 * @return: void
	 */
	private void freshCurVolume()
	{
		mCurVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSeekbar1.setProgress(mCurVolume);
		mProgress1.setText("" + mCurVolume);
	}
	
	/**
	 * 
	 * @Title: onVolumeSub
	 * @Description: 响应音量加减
	 * @param flag
	 * @return: void
	 */
	private void onVolumeSub(boolean flag)
	{
		if(flag)
		{
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
		}
		else
		{
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
		}
	}
}
