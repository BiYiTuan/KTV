/**
 * @Title: DialogMenu.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog
 * @Description: 播放菜单
 * @author: zhaoqy
 * @date: 2015-8-3 下午8:14:53
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.ui.activity.PlayerActivity;
import com.sz.ead.app.ktv.ui.dialog.menu.DialogMenuSelect;
import com.sz.ead.app.ktv.ui.dialog.menu.DialogMenuSelected;
import com.sz.ead.app.ktv.ui.dialog.menu.DialogMenuTone;
import com.sz.ead.app.ktv.ui.widget.KtvVideoView;
import com.sz.ead.app.ktv.utils.Common;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class DialogMenu extends Dialog implements android.view.View.OnClickListener
{
	public static final int PERIOD_PROGRESS_FRESH = 300; //每隔300ms,刷新当前播放时间
	private PlayerActivity mPlayer;
	private KtvVideoView   mVideoView; //播放控件
	private Context  mContext;
	private SeekBar  mSeekbar;
	private TextView mCurTime;
	private TextView mTotTime;
	private TextView mPause;
	private TextView mNext;
	private TextView mAccompaniment;
	private TextView mReplay;
	private TextView mTone;
	private TextView mSelected;
	private TextView mSelect;
	private Handler  mHandler;   
	
	public DialogMenu(Context context, int theme)
	{
		super(context, theme);
		mContext = context;
		mPlayer = (PlayerActivity) context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
        setContentView(R.layout.dialog_menu_player);
        findViews();
        setListeners();
	}
	
	private void findViews() 
	{
		mSeekbar = (SeekBar) findViewById(R.id.id_player_menu_seekbar);
		mCurTime = (TextView) findViewById(R.id.id_player_menu_current);
		mTotTime = (TextView) findViewById(R.id.id_player_menu_total);
		mPause = (TextView) findViewById(R.id.id_player_menu_pause);
		mNext = (TextView) findViewById(R.id.id_player_menu_next);
		mAccompaniment = (TextView) findViewById(R.id.id_player_menu_accompaniment);
		mReplay = (TextView) findViewById(R.id.id_player_menu_replay);
		mTone = (TextView) findViewById(R.id.id_player_menu_tone);
		mSelected = (TextView) findViewById(R.id.id_player_menu_selected);
		mSelect = (TextView) findViewById(R.id.id_player_menu_select);
	}
	
	private void setListeners()
	{
		mPause.setOnClickListener(this);
		mNext.setOnClickListener(this);
		mAccompaniment.setOnClickListener(this);
		mReplay.setOnClickListener(this);
		mTone.setOnClickListener(this);
		mSelected.setOnClickListener(this);
		mSelect.setOnClickListener(this);
	}
	
	/**
	 * 
	 * @Title: setVideoView
	 * @Description: 设置VideoView
	 * @param videoView
	 * @return: void
	 */
	public void setVideoView(KtvVideoView videoView) 
	{
		mVideoView = videoView;
	}
	
	/**
	 * 
	 * @Title: setCurrentTime
	 * @Description: 设置当前时间
	 * @param current
	 * @return: void
	 */
	private void setCurrentTime(int current)
	{
		mCurTime.setText(Common.formatTimeString(current));
		mSeekbar.setMax((mVideoView.getDuration()));
		mSeekbar.setProgress((current));
	}
	
	/**
	 * 
	 * @Title: setTotalTime
	 * @Description: 设置总时间
	 * @param total
	 * @return: void
	 */
	private void setTotalTime(int total)
	{
		mTotTime.setText(Common.formatTimeString(total));
	}
	
	private void setPauseText()
	{
		if(mPlayer.isPlaying())
		{
			mPause.setText(getContext().getString(R.string.menu_pause));
			mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(true), null, null); 
		}
		else
		{
			mPause.setText(getContext().getString(R.string.menu_play));
			mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(false), null, null); 
		}
	}
	
	private void setAccompanimentText()
	{
		String soundTrack = mPlayer.getSoundTrack();
		if (soundTrack.equals("lmono"))
		{
			//左声道: 伴唱, 则显示原唱(表示可以切换到原唱)
			mAccompaniment.setText(getContext().getString(R.string.menu_acoustic));
			mAccompaniment.setCompoundDrawablesRelative(null, getCompoundDrawableAccompaniment(false), null, null); 
		}
		else if (soundTrack.equals("rmono"))
		{
			//右声道: 原唱, 则显示伴唱(表示可以切换到伴唱)
			mAccompaniment.setText(getContext().getString(R.string.menu_accompaniment));
			mAccompaniment.setCompoundDrawablesRelative(null, getCompoundDrawableAccompaniment(true), null, null); 
		}
	}
	
	/**
	 * 
	 * @Title: resetTime
	 * @Description: 重置定时器
	 * @return: void
	 */
	public void resetTime()
	{
		setTotalTime(mVideoView.getDuration());
		setCurrentTime(mVideoView.getCurrentPosition());
		setPauseText();
		setAccompanimentText();
		
		if(mPlayer.isPlaying())
		{
			if (mHandler != null) 
			{
				mHandler.removeCallbacksAndMessages(null);
				mHandler = null;
			}

			mHandler = new Handler();
			mHandler.postDelayed(progressRun, PERIOD_PROGRESS_FRESH);
		}
	}
	
	private Runnable progressRun = new Runnable() 
	{
		public void run() 
		{
			setCurrentTime(mVideoView.getCurrentPosition());
			if (mHandler != null) 
			{
				mHandler.postDelayed(progressRun, PERIOD_PROGRESS_FRESH);
			}
		}
	}; 

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.id_player_menu_pause:
		{
			if(NetUtil.isNetConnected(mContext))
			{
				if(mPlayer.isPlaying())
				{
					mPlayer.pause();
					mPause.setText(getContext().getString(R.string.menu_play));
					mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(false), null, null); 
				}
				else
				{
					mPlayer.play();
					mPause.setText(getContext().getString(R.string.menu_pause));
					mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(true), null, null); 
				}
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
			}
			break;
		}
		case R.id.id_player_menu_next:
		{
			if(NetUtil.isNetConnected(mContext))
			{
				//重新设置为播放状态，然后准备播放
				mPause.setText(getContext().getString(R.string.menu_pause));
				mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(true), null, null); 
				mPlayer.cutSong();
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
			}
			break;
		}
		case R.id.id_player_menu_accompaniment:
		{
			if(NetUtil.isNetConnected(mContext))
			{
				mPlayer.switchSoundTrack();
				setAccompanimentText();
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
			}
			break;
		}
		case R.id.id_player_menu_replay:
		{
			if(NetUtil.isNetConnected(mContext))
			{
				//重新设置为播放状态，然后准备播放
				mPause.setText(getContext().getString(R.string.menu_pause));
				mPause.setCompoundDrawablesRelative(null, getCompoundDrawablePlay(true), null, null); 
				mPlayer.replay();
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
			}
			break;
		}
		case R.id.id_player_menu_tone:
		{
			dismiss();
			DialogMenuTone dialog = new DialogMenuTone(mContext, R.style.menu_tone_dialog);
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() 
			{
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					show();
				}
			});
			break;
		}
		case R.id.id_player_menu_selected:
		{
			dismiss();
			DialogMenuSelected dialog = new DialogMenuSelected(mContext, R.style.menu_right_dialog);
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() 
			{
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					show();
				}
			});
			break;
		}
		case R.id.id_player_menu_select:
		{
			dismiss();
			DialogMenuSelect dialog = new DialogMenuSelect(mContext, R.style.menu_right_dialog);
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() 
			{
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					show();
				}
			});
			break;
		}
		default:
			break;
		}
	}
	
	Drawable getCompoundDrawablePlay(boolean isPlaying)
	{
		Resources res = getContext().getResources();
		Drawable img_star = null;
		if(isPlaying)
		{
			img_star = res.getDrawable(R.drawable.menu_pause_selector);
		}
		else
		{
			img_star = res.getDrawable(R.drawable.menu_play_selector);
		}
		//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_star.setBounds(0, 0, img_star.getMinimumWidth(), img_star.getMinimumHeight());
		return img_star;
	}
	
	Drawable getCompoundDrawableAccompaniment(boolean isAccompaniment)
	{
		Resources res = getContext().getResources();
		Drawable img_star = null;
		if(isAccompaniment)
		{
			img_star = res.getDrawable(R.drawable.menu_accompaniment_selector);
		}
		else
		{
			img_star = res.getDrawable(R.drawable.menu_acoustic_selector);
		}
		//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_star.setBounds(0, 0, img_star.getMinimumWidth(), img_star.getMinimumHeight());
		return img_star;
	}
	
	public void hide()
	{
		cancel();
		
		if (mHandler != null) 
		{
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean nRet = false;
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if(event.getKeyCode() == KeyEvent.KEYCODE_MENU)
			{
				cancel();
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
	public void onDetachedFromWindow() 
	{
		super.onDetachedFromWindow();
		
		/*if (mHandler != null) 
		{
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}*/
	}
}
