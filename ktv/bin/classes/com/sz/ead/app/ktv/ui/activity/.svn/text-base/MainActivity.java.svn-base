/**
 * @Title: MainActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 主页面
 * @author: zhaoqy
 * @date: 2015-7-30 上午9:37:14
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.sz.ead.app.hostdownloader.HostUtil;
import com.sz.ead.app.ktv.bi.BI;
import com.sz.ead.app.ktv.bi.BiMsg;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.db.ColumnInfoTable;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.service.timeAuth.TimeAuthService;
import com.sz.ead.app.ktv.service.upgrade.UpgradeModelService;
import com.sz.ead.app.ktv.ui.widget.HomeItem;
import com.sz.ead.app.ktv.utils.BitmapUtil;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FileUtil;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.ImageLoaderUtils;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.framework.player.PPPServer;

public class MainActivity extends BaseActivity implements UICallBack, OnClickListener
{
	private Context           mContext;
	private ImageView         mSearch;
	private ImageView         mWelcome;
	private ImageView         mFocus;
	private View              mRecdView;  //推荐节目，下面包括五张图片的展示 mRecdView
	private ImageView         mRecommendBak;
	private ImageView[]       mRecommends = new ImageView[5]; //循环图片1
	private HomeItem[]        mHomeItem = new HomeItem[8];   //栏目
	private ArrayList<Column> mRecdList = new ArrayList<Column>();
	private ArrayList<Column> mColumnList = new ArrayList<Column>();
	private ArrayList<Video>  mVideoList = new ArrayList<Video>();
	private int               mCurIndex = 0;
	private int               mTime = 0;         //请求次数
	private int               mStartId = 0;      //BI启动id
	private boolean           mFinished = false; //开机启动页结束
	private boolean           mWantExit = false; //是否想退出
	private Animation         mRightAnim[] = new Animation[6];
	private Animation         mLeftAnim[] = new Animation[6];
	private Integer[]         menu_right_anim = { R.anim.index_right_0_to_1, R.anim.index_right_1_to_2, R.anim.index_right_2_to_3,
												  R.anim.index_right_3_to_4, R.anim.index_right_4_to_5, R.anim.index_right_5_to_0 };
	private Integer[]         menu_left_anim =  { R.anim.index_left_0_to_5,  R.anim.index_left_1_to_0,  R.anim.index_left_2_to_1,
												  R.anim.index_left_3_to_2,  R.anim.index_left_4_to_3,  R.anim.index_left_5_to_4 };

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		
		initData();
		findViews();
		setListeners();
		setVisibleState(false);
		initAnim();
		requestColumn();
	}

	private void findViews() 
	{
		mSearch = (ImageView) findViewById(R.id.id_main_search);
		mWelcome = (ImageView) findViewById(R.id.id_main_welcome);
		mFocus = (ImageView) findViewById(R.id.id_main_focus);
		mRecdView = (View) findViewById(R.id.id_main_recommend);
		mRecommendBak = (ImageView) findViewById(R.id.id_main_recommend_bak);
		int recdRes[] =  { R.id.id_main_recommend1, R.id.id_main_recommend2, R.id.id_main_recommend3, 
		                   R.id.id_main_recommend4, R.id.id_main_recommend5 };
		for (int i=0; i<5; i++)
		{
			mRecommends[i] = (ImageView) findViewById(recdRes[i]);
		}
		
		int scaleRes[] = { R.id.id_main_music, R.id.id_main_usercenter, R.id.id_main_column1, R.id.id_main_column2,
		                   R.id.id_main_column3, R.id.id_main_column4, R.id.id_main_column5, R.id.id_main_column6 };
		for (int i=0; i<8; i++)
		{
			mHomeItem[i] = (HomeItem) findViewById(scaleRes[i]);
		}
	}
	
	private void setListeners()
	{
		for (int i=0; i<8; i++)
		{
			mHomeItem[i].setOnClickListener(this);
		}
		mSearch.setOnClickListener(this);
		mRecdView.setOnClickListener(this);
	}
	
	private void setVisibleState(boolean visible)
	{
		mSearch.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		
		for (int i=0; i<5; i++)
		{
			mRecommends[i].setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		}
		
		for (int i=0; i<8; i++)
		{
			mHomeItem[i].setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		}
	}
	
	private void initData()
	{
		mRecdList.clear();
		mColumnList.clear();
		mVideoList.clear();
		mHandler.sendEmptyMessageDelayed(FlagConstant.HOME_FINISH, 2000);  
		Constant.initEPGHost(mContext);
		new Thread(Init).start();
	}

	private void initAnim() 
	{
		for(int i=0; i<6; i++)
		{
			mRightAnim[i] = AnimationUtils.loadAnimation(mContext, menu_right_anim[i]);
			mRightAnim[i].setFillAfter(true);
			mRightAnim[i].setFillEnabled(true);
			
			mLeftAnim[i] = AnimationUtils.loadAnimation(mContext, menu_left_anim[i]);
			mLeftAnim[i].setFillAfter(true);
			mLeftAnim[i].setFillEnabled(true);
		}
		mCurIndex = 0;
	}
	
	private Runnable Init = new Runnable() 
	{
		public void run() 
		{
			//初始化BI服务
			BI.startBi(mContext);      
			mStartId = BI.getStartTimeId();
			ImageLoaderUtils.initImageLoader(getApplicationContext());
			FlagConstant.init();
		}
	};
	
	private Runnable InitPPP = new Runnable() 
	{
		public void run() 
		{
			//初始化ppp服务
			PPPServer.initPPPServer(getApplicationContext()); 
		}
	};
	
	private void requestColumn()
	{
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, 11, 1, "", "");
	}
	
	private void requestVideo(String resCode)
	{
		mTime++;
		RequestDataManager.requestData(this, mContext, Token.TOKEN_VIDEO, 0, 0, resCode);
	}

	@Override
	public void onCancel(OutPacket out, int token) 
	{
		onNetError(-1, "error", null, token);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccessful(Object in, int token) 
	{
		try 
		{
			switch (token) 
			{
			case Token.TOKEN_COLUMN:
			{
				ArrayList<Column> temp = new ArrayList<Column>(); 
				temp = (ArrayList<Column>) RequestDataManager.getData(in);
				for (int i=0; i<temp.size(); i++)
				{
					Column item = temp.get(i);
					if (item.getIsResource() == 0)
					{
						if (mRecdList.size() < 5)
						{
							mRecdList.add(item);
						}
					}
					else 
					{
						if (mColumnList.size() < 6)
						{
							mColumnList.add(item);
						}
					}
				}
				onRecdList();
				onColumnList();
				//请求详情接口
				mHandler.sendEmptyMessageDelayed(FlagConstant.HOME_REQUEST_VIDEO, 1000);
				break;
			}
			case Token.TOKEN_VIDEO:
			{
				Video item = (Video) RequestDataManager.getData(in);
				if (item != null)
				{
					int size = item.getDramaList().size();
					if (size > 0)
					{
						item.setUrl(item.getDramaList().get(0).getUrl());
					}
					mVideoList.add(item);
				}
				mHandler.sendEmptyMessage(FlagConstant.HOME_REQUEST_VIDEO);
				break;
			}
			default:
				break;
			}
		} 
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " MainActivity onSuccessful error + " + e.toString());
		}
	}
	
	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		switch (token) 
		{
		case Token.TOKEN_COLUMN:
		{
			checkList(ColumnInfoTable.COLUMNTABLE_RECOMMEND);
			
			initScreenShot();
			checkList(ColumnInfoTable.COLUMNTABLE_COLUMN);
			freshUI();
			//开始请求详情接口
			mHandler.sendEmptyMessageDelayed(FlagConstant.HOME_REQUEST_VIDEO, 1000);
			break;
		}
		case Token.TOKEN_VIDEO:
		{
			//添加一个数据
			Column column = mRecdList.get(mTime-1);
			Video item = new Video();
			item.setResCode("");
			item.setName("");
			item.setType(column.getIsResource()+"");
			item.setUrl("");
			mVideoList.add(item);
			
			if (mTime < mRecdList.size())
			{
				mHandler.sendEmptyMessage(FlagConstant.HOME_REQUEST_VIDEO);
			}
			break;
		}
		default:
			break;
		}
	}
	
	private void onRecdList()
	{
		checkList(ColumnInfoTable.COLUMNTABLE_RECOMMEND);
		initScreenShot();
		updateDB(ColumnInfoTable.COLUMNTABLE_RECOMMEND);
	}

	private void onColumnList() 
	{
		freshUI();
		updateDB(ColumnInfoTable.COLUMNTABLE_COLUMN);
	}
	
	private void checkList(int type)
	{
		switch (type) 
		{
		case ColumnInfoTable.COLUMNTABLE_RECOMMEND:
		{
			if (mRecdList.size() < 5)
			{
				ArrayList<Column> columnList = new ArrayList<Column>();
				columnList = ColumnInfoTable.queryColumn(ColumnInfoTable.COLUMNTABLE_RECOMMEND);
				for (int i=0; i<columnList.size(); i++)
				{
					if (isCodeValid(columnList.get(i).getColumnCode(), ColumnInfoTable.COLUMNTABLE_RECOMMEND) && mRecdList.size() < 5)
					{
						Column temp = columnList.get(i);
						mRecdList.add(temp);
					}
				}
			}
			//使mRecdList的长度为5
			while (mRecdList.size() < 5)
			{
				Column item = new Column();
				item.setColumnCode("");
				item.setName("");
				item.setImage("");
				item.setResCode("");
				mRecdList.add(item);
			}
			break;
		}
		case ColumnInfoTable.COLUMNTABLE_COLUMN:
		{
			if (mColumnList.size() < 6)
			{
				ArrayList<Column> columnList = new ArrayList<Column>();
				columnList = ColumnInfoTable.queryColumn(ColumnInfoTable.COLUMNTABLE_COLUMN);
				for (int i=0; i<columnList.size(); i++)
				{
					if (isCodeValid(columnList.get(i).getColumnCode(), ColumnInfoTable.COLUMNTABLE_COLUMN) && mColumnList.size() < 6)
					{
						Column temp = columnList.get(i);
						mColumnList.add(temp);
					}
				}
			}
			break;
		}
		default:
			break;
		}
	}
	
	private void freshUI()
	{
		for (int i=0; i<5; i++)
		{
			if (i < mColumnList.size())
			{
				if (i < 2)
				{
					mHomeItem[i+2].setImage(mColumnList.get(i).getImage());
					mHomeItem[i+2].setText(mColumnList.get(i).getName());
				}
				else if (i >=2 && i<= 4)
				{
					mHomeItem[i+3].setImage(mColumnList.get(i).getImage());
					mHomeItem[i+3].setText(mColumnList.get(i).getName());
				}
			}
		}		
	}
	
	private void updateDB(int type)
	{
		switch (type) 
		{
		case ColumnInfoTable.COLUMNTABLE_RECOMMEND:
		{
			for (int i=0; i<mRecdList.size(); i++)
			{
				if (i < 5)
				{
					ColumnInfoTable.insertColumn(mRecdList.get(i), ColumnInfoTable.COLUMNTABLE_RECOMMEND);
				}
			}
			break;
		}
		case ColumnInfoTable.COLUMNTABLE_COLUMN:
		{
			for (int i=0; i<mColumnList.size(); i++)
			{
				if (i < 6)
				{
					ColumnInfoTable.insertColumn(mColumnList.get(i), ColumnInfoTable.COLUMNTABLE_COLUMN);
				}
			}
			break;
		}
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @Title: isCodeValid
	 * @Description: 判断code是否有效
	 * @param code
	 * @param type
	 * @return
	 * @return: boolean
	 */
	private boolean isCodeValid(String code, int type) 
	{
		boolean ret = false;
		switch (type) 
		{
		case ColumnInfoTable.COLUMNTABLE_RECOMMEND:
		{
			for (int i=0; i<mRecdList.size(); i++)
			{
				if (mRecdList.get(i).getColumnCode().equals(code))
				{
					ret = false;
				}
			}
			ret = true;
			break;
		}
		case ColumnInfoTable.COLUMNTABLE_COLUMN:
		{
			for (int i=0; i<mColumnList.size(); i++)
			{
				if (mColumnList.get(i).getColumnCode().equals(code))
				{
					ret = false;
				}
			}
			ret = true;
			break;
		}
		default:
			break;
		}
		return ret;
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.id_main_search:
		{
			Intent intent = new Intent(mContext, SearchActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.id_main_recommend:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mVideoList.size() >= 5)
			{
				int curIndex = (5-mCurIndex + 2)%5;
				if (mVideoList.get(curIndex).getResCode().isEmpty() || 
					mVideoList.get(curIndex).getName().isEmpty() || 
					mVideoList.get(curIndex).getUrl().isEmpty())
				{
					ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
					return;
				}
				//先把这条数据插入到待唱Table中
				SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
				Intent intent = new Intent(mContext, PlayerActivity.class);
				startActivity(intent);
			}
			break;	
		}
		case R.id.id_main_music:
		{
			//已点歌曲
			Intent intentUserMusicLis = new Intent(mContext, UserMusicListActivity.class);
			startActivity(intentUserMusicLis);
			break;
		}
		case R.id.id_main_usercenter:
		{
			//个人中心
			Intent intentUserCenter = new Intent(mContext, UserCenterActivity.class);
			startActivity(intentUserCenter);
			break;
		}
		case R.id.id_main_column1:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mColumnList.size() > 0)
			{
				//华语K歌榜
				Intent intent = new Intent(mContext, SongListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(FlagConstant.COLUMN, mColumnList.get(0));
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		case R.id.id_main_column2:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mColumnList.size() > 1)
			{
				//新歌抢先榜
				Intent intent = new Intent(mContext, SongListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(FlagConstant.COLUMN, mColumnList.get(1));
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		case R.id.id_main_column3:
		{
			//拼音点歌
			Intent intent = new Intent(mContext, SearchActivity.class);
			startActivity(intent);
			break;
		}	
		case R.id.id_main_column4:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mColumnList.size() > 2)
			{
				//分类点歌
				Intent intent = new Intent(mContext, SongCategoryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(FlagConstant.COLUMN, mColumnList.get(2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		case R.id.id_main_column5:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mColumnList.size() > 3)
			{
				//歌星点歌
				Intent intent = new Intent(mContext, SingerCategoryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(FlagConstant.COLUMN, mColumnList.get(3));
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		case R.id.id_main_column6:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			if (mColumnList.size() > 4)
			{
				//语种点歌
				Intent intent = new Intent(mContext, LangListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(FlagConstant.COLUMN, mColumnList.get(4));
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		default:
			break;
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
			case KeyEvent.KEYCODE_DPAD_LEFT:
			{
				if (mRecdView.isFocused() && mFinished && mRecdList.size() >= 5)
				{
					//反方向的动画，向右边滑动
					doKeyLeft(); 
					nRet = true;
				}
				else if (mSearch.isFocused())
				{
					nRet = true;
				}
				else if (mHomeItem[2].isFocused())
				{
					mHomeItem[0].requestFocus();
					nRet = true;
				}
				break;
			}
			case KeyEvent.KEYCODE_DPAD_RIGHT:	
			{
				if (mRecdView.isFocused() && mFinished && mRecdList.size() >= 5)
				{
					//反方向的动画，向左边滑动
					doKeyRight();
					nRet = true;
				}
				break;
			}
			case KeyEvent.KEYCODE_DPAD_UP:	
			{
				if (mRecdView.isFocused() && mFinished)
				{
					mFocus.setVisibility(View.INVISIBLE);
					mSearch.requestFocus();
					nRet = true;
				}
				else if (mHomeItem[0].isFocused() || mHomeItem[2].isFocused() || mHomeItem[3].isFocused() || mHomeItem[4].isFocused() || 
						 mHomeItem[5].isFocused() || mHomeItem[6].isFocused() || mHomeItem[7].isFocused())
				{
					mRecdView.requestFocus();
					mFocus.setVisibility(View.VISIBLE);
					mFocus.bringToFront();
					nRet = true;
				}
				break;
			}
			case KeyEvent.KEYCODE_DPAD_DOWN:	
			{
				if (mRecdView.isFocused() && mFinished)
				{
					mFocus.setVisibility(View.INVISIBLE);
					mHomeItem[0].requestFocus();
					nRet = true;
				}
				else if (mSearch.isFocused())
				{
					mRecdView.requestFocus();
					mFocus.setVisibility(View.VISIBLE);
					mFocus.bringToFront();
					nRet = true;
				}
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
	
	private void doKeyLeft() 
	{
		setImageViewPic(0);
		mRecommendBak.bringToFront();
		mRecommends[4].bringToFront();
		mRecommends[3].bringToFront();
		mRecommends[0].bringToFront();
		mRecommends[2].bringToFront();
		mRecommends[1].bringToFront();
		
		mRecommendBak.startAnimation(mRightAnim[0]);
		mRecommends[0].startAnimation(mRightAnim[1]);
		mRecommends[1].startAnimation(mRightAnim[2]);
		mRecommends[2].startAnimation(mRightAnim[3]);
		mRecommends[3].startAnimation(mRightAnim[4]);
		mRecommends[4].startAnimation(mRightAnim[5]);

		mRightAnim[1].setAnimationListener(new AnimationListener() 
		{
			@Override
			public void onAnimationStart(Animation arg0) 
			{
				mFocus.setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) 
			{
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) 
			{
				mFocus.setVisibility(View.VISIBLE);
				mFocus.bringToFront();
			}
		});
		mCurIndex ++ ;
		mCurIndex = mCurIndex % 5;
	}

	private void doKeyRight() 
	{
		setImageViewPic(1);
		mRecommendBak.bringToFront();
		mRecommends[0].bringToFront();
		mRecommends[1].bringToFront();
		mRecommends[4].bringToFront();
		mRecommends[2].bringToFront();
		mRecommends[3].bringToFront();
		
		mRecommendBak.startAnimation(mLeftAnim[0]);
		mRecommends[0].startAnimation(mLeftAnim[1]);
		mRecommends[1].startAnimation(mLeftAnim[2]);
		mRecommends[2].startAnimation(mLeftAnim[3]);
		mRecommends[3].startAnimation(mLeftAnim[4]);
		mRecommends[4].startAnimation(mLeftAnim[5]);

		mLeftAnim[1].setAnimationListener(new AnimationListener() 
		{
			@Override
			public void onAnimationStart(Animation arg0) 
			{
				mFocus.setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) 
			{
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) 
			{
				mFocus.setVisibility(View.VISIBLE);
				mFocus.bringToFront();
			}
		});
		mCurIndex -- ;
		if(mCurIndex < 0)
		{
			mCurIndex = mCurIndex + 5;
		}
		mCurIndex = mCurIndex %5;
	}
	
	private void setImageViewPic(int flag)
	{
		if(flag == 0)
		{
			setImage(mRecommends[0], mDrawableList[(5-mCurIndex)%5]);
			setImage(mRecommends[1], mDrawableList[(5-mCurIndex + 1)%5]);
			setImage(mRecommends[2], mDrawableList[(5-mCurIndex + 2)%5]);
			setImage(mRecommends[3], mDrawableList[(5-mCurIndex + 3)%5]);
			setImage(mRecommends[4], mDrawableList[(5-mCurIndex + 4)%5]);
			setImage(mRecommendBak, mDrawableList[(5-mCurIndex + 4)%5]);
			mRecommendBak.setVisibility(View.VISIBLE);
		}
		else
		{
			setImage(mRecommends[0], mDrawableList[(5-mCurIndex)%5]);
			setImage(mRecommends[1], mDrawableList[(5-mCurIndex + 1)%5]);
			setImage(mRecommends[2], mDrawableList[(5-mCurIndex + 2)%5]);
			setImage(mRecommends[3], mDrawableList[(5-mCurIndex + 3)%5]);
			setImage(mRecommends[4], mDrawableList[(5-mCurIndex + 4)%5]);
			setImage(mRecommendBak, mDrawableList[(5-mCurIndex)%5]);
			mRecommendBak.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		if (!mWantExit)
		{
			mWantExit = true;
			mHandler.sendEmptyMessageDelayed(FlagConstant.HOME_WANT_EXIT_APP, 2500);
			ToastUtils.getShowToast().showAnimationToast(mContext, getString(R.string.toast_want_to_exit_app), Toast.LENGTH_LONG);
		}
		else
		{
			mHandler.removeMessages(FlagConstant.HOME_WANT_EXIT_APP);
			finish();
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			switch (msg.what) 
			{
			case FlagConstant.HOME_FINISH:
			{
				BiMsg.sendAppStartBiMsg(mContext, mStartId);
				setVisibleState(true);
				
				new Thread(InitPPP).start();
				mFinished = true;
				mWelcome.setVisibility(View.GONE);
				
				
				mFocus.setVisibility(View.VISIBLE);
				mFocus.bringToFront();
				mRecdView.setNextFocusDownId(R.id.id_main_music);
				
				startTimeAuthService();
				startUpgradeService();
				break;
			}
			case FlagConstant.HOME_REQUEST_VIDEO:
			{
				if (mTime < mRecdList.size() && mTime < 5)
				{
					requestVideo(mRecdList.get(mTime).getResCode());
				}
				break;
			}
			case FlagConstant.HOME_WANT_EXIT_APP:
			{
				mWantExit = false;
				break;
			}
			case MSG_SCREENSHOT_SUCCESS:	// 截图加载完成
			{
				setImage(mRecommends[0], mDrawableList[0]);
				setImage(mRecommends[1], mDrawableList[1]);
				setImage(mRecommends[2], mDrawableList[2]);
				setImage(mRecommends[3], mDrawableList[3]);
				setImage(mRecommends[4], mDrawableList[4]);
				break;
			}
			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		HostUtil.uninit();
		ImageLoaderUtils.unInitImageLoaderService();
		removeDrawable();
		BI.stopBi();
		stopTimeAuthService();
		stopUpgradeService();
		PPPServer.stopService();
		BiMsg.sendAppExitBiMsg(mContext);
		
		if(mHandler != null)
		{
			mHandler.removeCallbacksAndMessages(null);
		}
	}
	
	/**
	 * 
	 * @Title: startUpgradeService
	 * @Description: 启动升级服务
	 * @return: void
	 */
	private void startUpgradeService()
	{ 
		//Logcat.i(FlagConstant.TAG, " start upgrade service.");
        Intent intent = new Intent();   
        intent.setClass(this, UpgradeModelService.class);
        startService(intent);
	}
	
	/**
	 * 
	 * @Title: stopUpgradeService
	 * @Description: 关闭升级服务
	 * @return: void
	 */
	private void stopUpgradeService()
	{ 
		//Logcat.i(FlagConstant.TAG, " stops upgrade service.");
        Intent intent = new Intent(); 
        intent.setClass(this, UpgradeModelService.class);
        stopService(intent);
	}
	
	/**
	 * 
	 * @Title: startTimeAuthService
	 * @Description: 启动定时鉴权服务
	 * @return: void
	 */
	private void startTimeAuthService()
	{ 
		//Logcat.i(FlagConstant.TAG, " start TimeAuth service.");
        Intent intent = new Intent();   
        intent.setClass(this, TimeAuthService.class);
        startService(intent);
	}
	
	/**
	 * 
	 * @Title: stopTimeAuthService
	 * @Description: 关闭定时鉴权服务
	 * @return: void
	 */
	private void stopTimeAuthService()
	{ 
		//Logcat.i(FlagConstant.TAG, " stops TimeAuth service.");
        Intent intent = new Intent(); 
        intent.setClass(this, TimeAuthService.class);
        stopService(intent);
	}
	
	private String mFilePath = "" ;	// 截图保存路径
	private Drawable[] mDrawableList;
	ArrayList<String> mImagePathList; //截图地址
	
	private void initScreenShot()
	{
		mFilePath = mContext.getFilesDir().getPath() + "/recommend/";	
		int size = mRecdList.size();
		Logcat.i(FlagConstant.TAG, " ++++++ size: " + size + ",  path: " + mFilePath);
		mImagePathList = new ArrayList<String>();
		mDrawableList =  new Drawable[size];
		//创建截图文件保存路径
		FileUtil.createDir(mFilePath);
		
		for (int i=0; i<size; i++)
		{
			mDrawableList[i] = null;
			String url = mRecdList.get(i).getImage();
			String splistr[] = url.split("/");
			String name = splistr[splistr.length-1];
			
			//保存截图路径，以包名为文件夹名称，url为文件名称
			String pkgNamePath = mFilePath  + "/"+ name;
			File f = new File(pkgNamePath);
			
			if (f.exists() && !url.equals(""))
			{
				try
				{
					//截图列表
					mImagePathList.add(i, pkgNamePath);	
					//解析本地图片
					CreateDrawableThread draw = new CreateDrawableThread(i, mFilePath, name);
					draw.start();
				}
				catch(java.lang.OutOfMemoryError e)
				{
				    //替代方案
					Logcat.i(FlagConstant.TAG, "java.lang.OutOfMemoryError e ");
				 }
			}
			else
			{
				try
				{
				    //load big memory data
					mImagePathList.add(i, "");			//不存在该图片文件，以空代替，然后去下载截图
					ImageLoaderUtils.mImageLoader.loadImage(url, ImageLoaderUtils.mMainRecdOption, new ImageListener());
				}
				catch(java.lang.OutOfMemoryError e)
				{
				    //替代方案
					Logcat.i(FlagConstant.TAG, "java.lang.OutOfMemoryError e ");
				}
			}
		}
	}

    //截图加载完成
 	public static final int MSG_SCREENSHOT_SUCCESS = 0x1011;
    
    //保存和解析图片
  	public class CreateDrawableThread extends Thread
  	{
  		private String  mScreenShotPath;
  	    private String  mPkgNamePath;
  	    private String  mName;
  	    private Bitmap  mLoadeImage;
  	    private int     mIndex;
  	    private boolean isSave = false;	//是否下载保存图片
  	    
  	    public CreateDrawableThread(int index,String packageNamePath, String name)
  	    {
  	    	mIndex = index;
  	    	mPkgNamePath = packageNamePath;
  	    	mName = name;
  	    	mScreenShotPath = packageNamePath + "/" + name;
  	    }
  	    
  	    public void saveBitmap(Bitmap loadeImage)
  	    {
  	    	isSave = true;
  	    	mLoadeImage = loadeImage;
  	    	//Logcat.i(FlagConstant.TAG," loadedImage3: " + mLoadeImage);
  	    }
  	    
  	    public void run()
  	    {
  	    	if (isSave)
  	    	{
  	    		//Logcat.i(FlagConstant.TAG," loadedImage4: " + mLoadeImage);
  	    		//保存截图
  	    		BitmapUtil.saveMyBitmap(mPkgNamePath,mName, mLoadeImage);
  	    	}
  	    	
  	    	//通过截图路径生成Drawable格式
  	    	mDrawableList[mIndex] = BitmapDrawable.createFromPath(mScreenShotPath);
  	    	mHandler.sendEmptyMessage(MSG_SCREENSHOT_SUCCESS);
  	    }
  	}
  	
  	class ImageListener extends SimpleImageLoadingListener
  	{  
 	    @Override  
 	    public void onLoadingStarted(String imageUri, View view)
 	    {  
 	        super.onLoadingStarted(imageUri, view);  
 	    }  
 	  
 	    @Override  
 	    public void onLoadingFailed(String imageUri, View view, FailReason failReason) 
 	    {  
 	        super.onLoadingFailed(imageUri, view, failReason);  
 	    }  
 	  
 	    @Override  
 	    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
 	    {  
 	    	//Logcat.i(FlagConstant.TAG, "imageUri: " + imageUri + " loadedImage: " + loadedImage);
 	    	if (loadedImage != null)
 	    	{
 	    		setSuccessImg(imageUri, loadedImage);
 	    	}
 	        super.onLoadingComplete(imageUri, view, loadedImage);  
 	    }  
 	  
 	    @Override  
 	    public void onLoadingCancelled(String imageUri, View view) 
 	    {  
 	        super.onLoadingCancelled(imageUri, view);  
 	    }
 	}
  	 
    //保存截图
    private void setSuccessImg(String imageUri,Bitmap loadeImage)
 	{
 		int count = mRecdList.size();
 		for(int i=0; i<count; i++)
 		{
 			if (mRecdList.get(i).getImage().equals(imageUri))
 			{
 				String splistr[] = imageUri.split("/");
 				String name = splistr[splistr.length-1];
 				mImagePathList.add(i, mFilePath + name);

 				//保存和解析本地图片
 				CreateDrawableThread draw = new CreateDrawableThread(i, mFilePath, name);
 				//Logcat.i(FlagConstant.TAG," loadedImage2: " + loadeImage);
 				draw.saveBitmap(loadeImage);
 				draw.start();
 				return;
 			}
 		}
 	}
     
     public void removeDrawable()
     {
 		for(int i=0; i<mRecdList.size(); i++)
 		{
 			if(null != mDrawableList[i])
 			{
 				BitmapDrawable bd = (BitmapDrawable) mDrawableList[i];
 				Bitmap bm = bd.getBitmap();
 				
 				if (bm != null && !bm.isRecycled()) 
 				{ 
 					bm.recycle();
 				    System.gc();
 					bm=null;
 				}     
 				mDrawableList[i] = null;
 			}
 		}		
 	}
     
    //显示截图，如果不存在，显示默认图片
 	public void setImage(ImageView imageView, Drawable drawable)
 	{
 		if (null == drawable)
 		{
 			imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.index_recommend_default));
 		}
 		else
 		{
 			//setImageDrawable(drawable);
 			imageView.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.drawableToBitmap(drawable), 15));
 		}
 	}
}
