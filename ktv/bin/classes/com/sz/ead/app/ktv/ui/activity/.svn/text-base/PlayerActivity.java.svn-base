/**
 * @Title: PlayerActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 音乐播放页面
 * @author: zhaoqy
 * @date: 2015-8-5 下午8:25:40
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.ead.app.ktv.bi.BI;
import com.sz.ead.app.ktv.bi.BiMsg;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.db.SungInfoTable;
import com.sz.ead.app.ktv.ui.dialog.DialogExit;
import com.sz.ead.app.ktv.ui.dialog.DialogExit.DialogExitListener;
import com.sz.ead.app.ktv.ui.dialog.DialogMenu;
import com.sz.ead.app.ktv.ui.widget.KtvVideoView;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ReflectUtils;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.framework.networktraffic.NetTraffic;
import com.sz.ead.framework.player.PPPServer;
import com.sz.ead.framework.player.PlayUriParser.uriCallBack;

public class PlayerActivity extends ActivityReceiver implements uriCallBack, OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener
{
	private ArrayList<Video> mVideoList;
	private FreshSelected    mFreshSelected;
    private Context      mContext;   //上下文
    private MediaPlayer  mPlayer;
    private KtvVideoView mVideoView; //播放控件
    private ImageView    mWelcome;
    private ImageView    mWaitting;
    private ImageView    mPreserve;   //流媒体维护
    private TextView     mCurVideo;
    private TextView     mNextVideo;
    private ServerAsync  mPPPServer;  //ppp同步服务
    private NetTraffic   mNetTraffic; //网络监测
    private Video        mVideo;
    private DialogExit   mExitPlayer; //退出播放的提示框
	private DialogMenu   mMenu;       //播放控制主菜单
	private String       mErrorType;  //错误类型
	private int          mErrorCode;  //错误码
	private int          mErrorExtra; //错误附加值
	private int          mRet;        //jni回调结果
	private int          mBufferId;   //点播缓冲时间启动id
	private int          mPlayId;     //播放时间启动id
	private boolean      mPause = false;
	private boolean      mCallback = true; //回调是否结束
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		mContext = this;
		
		findViews();
		init();
		getVodInfo();
		registerIntentReceivers();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) 
	{
		super.onPostCreate(savedInstanceState);
		
		mPlayId = BI.getStartTimeId();
		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnInfoListener(this);
		
		PPPServer.setCallBack(this);
		startTask(mVideoList.size()-1);
	}

	private void findViews() 
	{
		mVideoView = (KtvVideoView) findViewById(R.id.id_player_videoview);
		mWelcome = (ImageView) findViewById(R.id.id_player_welcome);
		mWaitting = (ImageView) findViewById(R.id.id_player_waiting);
		mPreserve = (ImageView) findViewById(R.id.id_player_preserve);
		mCurVideo = (TextView) findViewById(R.id.id_player_current);
		mNextVideo = (TextView) findViewById(R.id.id_player_next);
	}

	private void init() 
	{
		mNetTraffic = new NetTraffic();
		mNetTraffic.start();
		mFreshSelected = new FreshSelected();
	}
	
	private void getVodInfo()
	{
		mVideoList = SelectedInfoTable.querySelectedVideos();
		Logcat.i(FlagConstant.TAG, " count: " + mVideoList.size());
	}
	
	private void startTask(int curIndex) 
	{
		if (curIndex <= mVideoList.size())
		{
			//正在播放的歌曲
			mVideo = mVideoList.get(curIndex);
			SelectedInfoTable.deleteSelectedVideoByRowID(mVideo.getRowID());
			SungInfoTable.insertSungVideo(mVideo);
			sendFreshSungBroadCast();
			
			mVideoList.remove(curIndex);
			setCurPlay();
			mWaitting.setVisibility(View.VISIBLE);
			sendFreshMenuSelectedBroadCast();
			mHandler.sendEmptyMessage(FlagConstant.PLAY_CUT_SONG);
			//startPPPServerAsyncTask();
			//mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_TIMEOUT, 20*1000);
		}
	}
	
	private void setCurPlay()
	{
		if (mVideoList.size() > 0)
		{
			mCurVideo.setText(getString(R.string.player_curplay) + " " + mVideo.getName());
			mNextVideo.setText(getString(R.string.player_next_song) + " " + mVideoList.get(0).getName());
		}
		else 
		{
			mCurVideo.setText(getString(R.string.player_curplay) + " " + mVideo.getName());
			mNextVideo.setText(getString(R.string.player_selected_empty));
		}
	}
	
	private void sendFreshSungBroadCast()
	{
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_FRESH_SUNG);
		mContext.sendBroadcast(intent);
	}
	
	private void sendFreshMenuSelectedBroadCast()
	{
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_FRESH_MENU_SELECTED);
		mContext.sendBroadcast(intent);
	}
	
	private void startPPPServerAsyncTask()
	{
		if (mPPPServer != null)
		{
			mPPPServer.cancel(true);
		}
		mPPPServer = new ServerAsync();
		mPPPServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	/**
	 * 
	 * @Title: startPPPServer
	 * @Description: 开始PPP服务
	 * @param url
	 * @return: void
	 */
	private void startPPPServer(String url) 
	{
		//if (url.startsWith("gvppp"))  
		if (url.contains("ppp://"))  
		{
			//流媒体播放地址
			Logcat.i(FlagConstant.TAG, " 111111111 gPlayerRet: " + FlagConstant.gPlayerRet);
			if(FlagConstant.gPlayerRet != 0)
			{
				mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_RETASK, 500);
			}
			else
			{
				PPPServer.setPlayUri(url);
				PPPServer.startToHttpUri();
			}
		}
		else  
		{
			//原力播放地址
			PPPServer.setPlayUri(url);
			PPPServer.startToHttpUri();
		}
	}
	
	/**
	 * 
	 * @Title: stopVideoView
	 * @Description: 关闭播放器和ppp服务
	 * @return: void
	 */
	private void stopVideoView()
	{
		mWaitting.setVisibility(View.GONE);
		mVideoView.stopPlayback();
		mVideoView.destroyDrawingCache();
		PPPServer.stopPlay();
	}
	
	class ServerAsync extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected Void doInBackground(Void... params) 
		{
			String url = mVideo.getUrl();
			Logcat.i(FlagConstant.TAG, " player_ur: " + url);
			
			if(!url.isEmpty())
			{
				mBufferId = BI.getStartTimeId();
				startPPPServer(url);
			}
			else
			{
				//地址无效
				mHandler.sendEmptyMessage(FlagConstant.PLAY_ERROR);
			}
			return null;
		}
	}

	@Override
	public void jni_callback_found_http(int ret, String http_uri, String org_uri) 
	{
		Logcat.i(FlagConstant.TAG, " callback: " + ret + ", " + http_uri);
		if (ret != 0 || TextUtils.isEmpty(http_uri)) 
		{
			Logcat.i(FlagConstant.TAG, "PLAY_ERROR: jni_callback");
			//错误码: 流媒体库
			mErrorType = FlagConstant.ERROR_LIBRARY; 
			mErrorCode = ret;
			mHandler.sendEmptyMessage(FlagConstant.PLAY_ERROR);
			mCallback = true;
			return;
		} 
		else 
		{
			mCallback = true;
			Message msg = new Message();
			msg.what = FlagConstant.PLAY_SET_URL;
			msg.obj = http_uri;
			mHandler.sendMessage(msg); 
		}
	}

	@Override
	public void jni_callback_server_maintain(int ret) 
	{
		Logcat.d(FlagConstant.TAG, " ret: " + ret);
		mRet = ret;
		
		switch (ret) 
		{
		case 0:
		{
			mHandler.sendEmptyMessage(FlagConstant.PLAY_PRESERVE_END);
			break;
		}
		case 1:
		{
			mHandler.sendEmptyMessage(FlagConstant.PLAY_PRESERVE_START);
			break;
		}
		default:
			break;
		}
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) 
	{
		if(ReflectUtils.existSetParameterMethod())
		{
			mVideoView.setParameter(1);
		}	
		mHandler.sendEmptyMessage(FlagConstant.PLAY_START);
		mPlayer = mp;
		mHandler.removeMessages(FlagConstant.PLAY_TIMEOUT);
	}

	@Override
	public void onCompletion(MediaPlayer mp) 
	{
		mp.reset();
		mHandler.sendEmptyMessage(FlagConstant.PLAY_COMPLETE);
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) 
	{
		Logcat.i(FlagConstant.TAG, " PLAY_ERROR: onError");
		//错误码: 播放器
		mErrorType = FlagConstant.ERROR_PLAYER; 
		mErrorCode = what;
		mErrorExtra = extra;
		mHandler.sendEmptyMessage(FlagConstant.PLAY_ERROR);
		return true;
	}
	
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) 
	{
		switch(what)
		{
		case 0x12000:
		{
			Logcat.i(FlagConstant.TAG, " +++++0x12000+++++ ");
			mWaitting.setVisibility(View.GONE);
			mWelcome.setVisibility(View.GONE);
			
			if (getFirst())
			{
				saveFirst();
				//初始状态设置成原唱
				mPlayer.setParameter(MediaPlayer.KEY_PARAMETER_AML_PLAYER_SWITCH_SOUND_TRACK, "rmono");
				saveSoundTrack("rmono");
			}
		}
		default:
			break;
		}
		return true;
	}
	
	public Boolean getFirst()
	{
		@SuppressWarnings("static-access")
		SharedPreferences sp = getSharedPreferences("sp", mContext.MODE_PRIVATE);
		return sp.getBoolean("First", true);
	}
	
	public void saveFirst()
	{
		@SuppressWarnings("static-access")
		SharedPreferences sp = getSharedPreferences("sp", mContext.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("First", false);
		editor.commit();
	}
	
	
	/**
	 * 
	 * @Title: isPlaying
	 * @Description: 是否正在播放
	 * @return
	 * @return: boolean
	 */
	public boolean isPlaying()
	{
		return mVideoView.isPlaying();
	}
	
	public void pause()
	{
		mVideoView.pause();
	}
	
	public void play()
	{
		mVideoView.start();
	}
	
	public void cutSong()
	{
		mHandler.removeMessages(FlagConstant.PLAY_TIMEOUT);
		mHandler.removeMessages(FlagConstant.PLAY_CUT_SONG);
		
		if (mVideoList.size() > 0)
		{
			stopVideoView();
			mVideo = mVideoList.get(0);
			SelectedInfoTable.deleteSelectedVideoByRowID(mVideo.getRowID());
			SungInfoTable.insertSungVideo(mVideo);
			sendFreshSungBroadCast();
			
			mVideoList.remove(0);
			setCurPlay();
			mWaitting.setVisibility(View.VISIBLE);
			sendFreshMenuSelectedBroadCast();
			mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_CUT_SONG, 500);
		}
		else
		{
			onBack();
		}
	}
	
	public void replay()
	{
		mVideoView.seekTo(0);
		mVideoView.start();
		mWaitting.setVisibility(View.GONE);
	}
	
	public void switchSoundTrack()
	{
		String soundTrack = getSoundTrack();
		if (soundTrack.equals("lmono"))
		{
			//由伴唱切换到原唱
			mPlayer.setParameter(MediaPlayer.KEY_PARAMETER_AML_PLAYER_SWITCH_SOUND_TRACK, "rmono");
			saveSoundTrack("rmono");
		}
		else if (soundTrack.equals("rmono"))
		{
			//由原唱切换到伴唱
			mPlayer.setParameter(MediaPlayer.KEY_PARAMETER_AML_PLAYER_SWITCH_SOUND_TRACK, "lmono");
			saveSoundTrack("lmono");
		}
	}
	
	public String getSoundTrack()
	{
		@SuppressWarnings("static-access")
		SharedPreferences sp = getSharedPreferences("sp", mContext.MODE_PRIVATE);
		//默认原唱
		return sp.getString("SoundTrack", "rmono");
	}
	
	public void saveSoundTrack(String soundTrack)
	{
		@SuppressWarnings("static-access")
		SharedPreferences sp = getSharedPreferences("sp", mContext.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("SoundTrack", soundTrack);
		editor.commit();
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean nRet = false;

		if (mRet == -1)
		{
			mHandler.sendEmptyMessage(FlagConstant.PLAY_PRESERVE_HIDE);
		}
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			switch (event.getKeyCode()) 
			{
			case KeyEvent.KEYCODE_DPAD_CENTER:
			case KeyEvent.KEYCODE_MENU:
			{
				mMenu = new DialogMenu(mContext, R.style.menu_player_dialog);
				mMenu.show();
				mMenu.setVideoView(mVideoView);
				mMenu.resetTime();
				nRet = true;
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
	public void onBackPressed() 
	{
		mExitPlayer = new DialogExit(this, R.style.exit_player_dialog);
		mExitPlayer.setOnDialogExitListener(new DialogExitListener() 
		{
			@Override
			public void OnDialogExit() 
			{
				mExitPlayer.cancel();
				onBack();
			}
			
			@Override
			public void OnDialogCancel() 
			{
				mExitPlayer.cancel();
			}
		});
		mExitPlayer.show();
	}
	
	private void onBack()
	{
		stopVideoView();
		finish();
		sendPlayTimeBi();
	}
	
	private void next()
	{
		if (mVideoList.size() > 0)
		{
			stopVideoView();
			startTask(0);
		}
		else
		{
			onBack();
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() 
	{
		public void handleMessage(Message msg) 
		{
			switch (msg.what) 
			{
			case FlagConstant.PLAY_TIMEOUT:
			{
				mCallback = true;
				mErrorType = FlagConstant.ERROR_TIMEOUT; //错误码: 加载超时
				mErrorCode = -3;
				showToast();
				next();
				break;
			}
			case FlagConstant.PLAY_ERROR:
			{
				mCallback = true;
				mHandler.removeMessages(FlagConstant.PLAY_TIMEOUT);
				showToast();
				next();
				break;
			}
			case FlagConstant.PLAY_SET_URL:
			{
				mVideoView.setVideoURI(Uri.parse((String) msg.obj));
				break;
			}
			case FlagConstant.PLAY_COMPLETE:
			{
				next();
				break;
			}
			case FlagConstant.PLAY_START:
			{
				if(!ReflectUtils.existSetParameterMethod())
				{
					mWaitting.setVisibility(View.GONE);
					mWelcome.setVisibility(View.GONE);
				}
				mVideoView.start();
				if (mMenu != null)
				{
					mMenu.resetTime();
				}
				sendDemandBufferTimeBiMsg();
				break;
			}
			case FlagConstant.PLAY_RETASK:
			{
				Logcat.i(FlagConstant.TAG, " 222222 gPlayerRet: " + FlagConstant.gPlayerRet);
				if(FlagConstant.gPlayerRet != 0)
				{
					mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_RETASK, 500);
				}
				else
				{
					startPPPServerAsyncTask();
				}
				break;
			}
			case FlagConstant.PLAY_CUT_SONG:
			{
				if (mCallback)
				{
					mHandler.removeMessages(FlagConstant.PLAY_TIMEOUT);
					mCallback = false;
					startPPPServerAsyncTask();
					mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_TIMEOUT, 20*1000);
				}
				else
				{
					mHandler.sendEmptyMessageDelayed(FlagConstant.PLAY_CUT_SONG, 500);
				}
				break;
			}
			case FlagConstant.PLAY_PRESERVE_START:
			{
				mVideoView.pause();
				mPreserve.setBackgroundResource(R.drawable.ppp_preserve);
				mPreserve.invalidate();
				break;
			}
			case FlagConstant.PLAY_PRESERVE_END:
			{
				mPreserve.setBackground(null);
				mVideoView.start();
				break;
			}
			case FlagConstant.PLAY_PRESERVE_HIDE:
			{
				mPreserve.setBackground(null);
				mRet = -2;
				break;
			}
			default:
				break;
			}
		}
	};
	
	private void showToast()
	{
		String error = "";
		String text = "";
		if (mErrorType == FlagConstant.ERROR_LIBRARY || 
			mErrorType == FlagConstant.ERROR_TIMEOUT || 
			mErrorType == FlagConstant.ERROR_NETWORK)
		{
			error = mErrorCode + "(" + mErrorType + ")";
		}
		else if (mErrorType == FlagConstant.ERROR_PLAYER)
		{
			error = mErrorCode + ", " + mErrorExtra + "(" + mErrorType + ")";
		}
		text = String.format(getString(R.string.toast_switch_next), error);
		ToastUtils.getShowToast().showAnimationToast(mContext, text, Toast.LENGTH_LONG);
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		unregisterIntentReceivers();
		
		if (mPPPServer != null)
		{
			mPPPServer.cancel(true);
			mPPPServer = null;
		}
		
		if(mNetTraffic != null)
		{
			mNetTraffic.stop();
			mNetTraffic = null;
		}
		
		if(mHandler != null)
		{
			mHandler.removeCallbacksAndMessages(null);
		}
	}
	
	private class FreshSelected extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if (intent.getAction().equals(Constant.ACTION_FRESH_SELECTED)) 
			{
				mVideoList.clear();
				mVideoList = SelectedInfoTable.querySelectedVideos();
				setCurPlay();
			}
			else if (intent.getAction().equals(Constant.ACTION_CUT_SONG)) 
			{
				mVideoList.clear();
				mVideoList = SelectedInfoTable.querySelectedVideos();
				//如果要切的歌曲与当前播放的歌曲相同, 则重唱；否则切歌
				int curIndex = mVideoList.size()-1;
				if (mVideo.getResCode().equals(mVideoList.get(curIndex).getResCode()))
				{
					mVideo = mVideoList.get(curIndex);
					SelectedInfoTable.deleteSelectedVideoByRowID(mVideo.getRowID());
					SungInfoTable.insertSungVideo(mVideo);
					sendFreshSungBroadCast();
					
					mVideoList.remove(curIndex);
					setCurPlay();
					mWaitting.setVisibility(View.VISIBLE);
					replay();
					sendFreshMenuSelectedBroadCast();
				}
				else
				{
					stopVideoView();
					startTask(curIndex);
				}
			}
		}
	}
	
	private void registerIntentReceivers() 
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_FRESH_SELECTED);
		filter.addAction(Constant.ACTION_CUT_SONG);
		registerReceiver(mFreshSelected, filter);
	}
	
	private void unregisterIntentReceivers() 
	{
		if (mFreshSelected != null) 
		{
			unregisterReceiver(mFreshSelected);
			//mFreshSelected = null;
		}
	}
	
	@Override
	protected void onReceive(Intent intent) 
	{
		super.onReceive(intent);
		
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) 
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
			
			if ((netInfo == null || !netInfo.isAvailable()) || (netInfo != null && !netInfo.isAvailable())) 
			{
				if (mVideoView.getCurrentPosition() > 0)
				{
					if (mVideoView.isPlaying())
					{
						if (mMenu  != null)
						{
							mMenu.hide();
						}
						mPause = true;
						mVideoView.pause();
					}
					ToastUtils.getShowToast().showAnimationToast(mContext, getString(R.string.toast_network_normal), Toast.LENGTH_LONG);
				}
			}
			else if (netInfo != null && netInfo.isAvailable())
			{
				if (mVideoView.getCurrentPosition() > 0)
				{
					if (mPause)
					{
						mPause = false;
						mVideoView.start();
						if (mMenu  != null)
						{
							mMenu.show();
							mMenu.resetTime();
						}
					}
				}
			}
		}
		else  if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) 
        {
            String reason = intent.getStringExtra("reason");
            if (reason != null) 
            {
                if (reason.equals("homekey")) 
                {
                	onBack();
                } 
            }
        }
		else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) 
		{
			onBack();
		}
		else if (Constant.ACTION_TIMEAUTH_FAIL.equals(intent.getAction())) 
		{
			onBack();
		}
	}
	
	/**
	 * 
	 * @Title: sendDemandBufferTimeBiMsg
	 * @Description: 发送点播缓冲时长BI
	 * @return: void
	 */
	private void sendDemandBufferTimeBiMsg()
	{
		String code = mVideo.getResCode();
		String name = mVideo.getName();
		String classifyCode = "";
		String classifyName = "";
		String dramaCode = "";
		String dramaName = "";
		//点播缓冲时长BI
		BiMsg.sendDemandBufferTimeBiMsg(code, name, classifyCode, classifyName, dramaCode, dramaName, mBufferId);
	}
	
	/**
	 * 
	 * @Title: sendPlayTimeBi
	 * @Description: 播放时长
	 * @return: void
	 */
	private void sendPlayTimeBi()
	{
		String type = "1"; //1,点播
		String code = mVideo.getResCode();
		String name = mVideo.getName();
		String classifyCode = "";
		String classifyName = "";
		String dramaCode = "";
		String dramaName = "";
		//播放时长BI
		BiMsg.sendPlayTimeBiMsg(type, code, name, classifyCode, classifyName, dramaCode, dramaName, mPlayId);
	}
}
