/**
 * @Title: SingerCategoryActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 歌手分类
 * @author: zhaoqy
 * @date: 2015-8-3 下午3:09:48
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.bi.BI;
import com.sz.ead.app.ktv.bi.BiMsg;
import com.sz.ead.app.ktv.dataprovider.datapacket.ElementListData;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.db.ColumnInfoTable;
import com.sz.ead.app.ktv.ui.dialog.DialogSearchSinger;
import com.sz.ead.app.ktv.ui.widget.EmptyView;
import com.sz.ead.app.ktv.ui.widget.ImageRotation;
import com.sz.ead.app.ktv.ui.widget.Indicator;
import com.sz.ead.app.ktv.ui.widget.SingerCategoryView;
import com.sz.ead.app.ktv.utils.Common;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class SingerCategoryActivity extends BaseActivity implements UICallBack, OnClickListener, OnFocusChangeListener, AnimationListener
{
	private static final int     PAGE_SIZE = 10; 
	private static final int     ACTION_DEFAULT = 0;
	private static final int     ACTION_ZERO = 1;
	private static final int     ACTION_FOUR = 2;
	private static final int     ACTION_FIVE = 3;
	private static final int     ACTION_NINE = 4;
	private static final int     UPDATE_DB = 6;
	private Context              mContext;
	private Column               mColumn;
	private StringBuffer         mBuf = new StringBuffer(); //用户输入的关键字
	private ArrayList<Column>    mCategoryList = new ArrayList<Column>();
	private ArrayList<Column>    mTempList = new ArrayList<Column>();
	private ArrayList<Column>    mDBList = new ArrayList<Column>();
	private SingerCategoryView[] mCategoryItem = new SingerCategoryView[PAGE_SIZE];
	private View                 mView;
	private TextView             mName;
	private ImageRotation        mWaitting;
	private ImageView            mFocus;
	private Indicator            mIndicator;
	private EmptyView            mEmptyView;
	private Animation            mScaleBig;                                  
	private Animation            mScaleSmall;    
	private TextView             mKeyStr;
	private DialogSearchSinger   mDiaSearch;
	private String               mColumnCode;
	private String               mKeyWord;
	private int                  mCount = 0;      
	private int                  mTotPage = 0;
	private int                  mCurPage = 0;    
	private int                  mLoadPage = 0;   
	private int                  mTime = 1;
	private int                  mAction = 0;
	private int                  mListId = 0;      //搜索列表耗时启动id
	private boolean              mSearching;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singercategory);
		mContext = this;
		
		findViews();
		setListener();
		getColumnCode();
		requestColumn();
	}

	private void findViews() 
	{
		int resid[] = { R.id.id_singercategory_item0, R.id.id_singercategory_item1, R.id.id_singercategory_item2, R.id.id_singercategory_item3, 
				        R.id.id_singercategory_item4, R.id.id_singercategory_item5, R.id.id_singercategory_item6, R.id.id_singercategory_item7,
				        R.id.id_singercategory_item8, R.id.id_singercategory_item9 }; 
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mCategoryItem[i] = (SingerCategoryView) findViewById(resid[i]);
		}
		mName = (TextView) findViewById(R.id.id_singercategory_name);
		mKeyStr = (TextView) findViewById(R.id.id_singercategory_keystr);
		mView = findViewById(R.id.id_singercategory_item);
		mWaitting = (ImageRotation) findViewById(R.id.id_singercategory_imagerotation);
		mFocus = (ImageView) findViewById(R.id.id_singercategory_focus);
		mIndicator = (Indicator) findViewById(R.id.id_singercategory_indicator);
		mEmptyView = (EmptyView) findViewById(R.id.id_singercategory_empty);
	}
	
	private void setVisibleState(boolean visible)
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mCategoryItem[i].setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		}
	}
	
	private void setListener() 
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mCategoryItem[i].setOnFocusChangeListener(this);
			mCategoryItem[i].setOnClickListener(this);
		}
	}
	
	private void getColumnCode() 
	{
		Bundle bundle = getIntent().getExtras();
		mColumn = bundle.getParcelable(FlagConstant.COLUMN);
		if (mColumn != null)
		{
			mColumnCode = mColumn.getColumnCode();
			mName.setText(mColumn.getName());
		}
		mCategoryList.clear();
		mTempList.clear();
		mDBList.clear();
	}
	
	private void requestColumn()
	{
		mWaitting.setVisibility(View.VISIBLE);
		setVisibleState(false);
		mSearching = false;
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, PAGE_SIZE*2, mTime, mColumnCode, "");
	}
	
	private void requestSingerSearch()
	{
		mListId = BI.getStartTimeId();
		mWaitting.setVisibility(View.VISIBLE);
		setVisibleState(false);
		mSearching = true;
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, PAGE_SIZE*2, mTime, "", mKeyWord);
	}
	
	private void reInit()
	{
		mCategoryList.clear();
		mCount = 0;      
		mTotPage = 0;
		mCurPage = 0;    
		mLoadPage = 0;   
		mIndicator.setIndicatorTotalNumber(0);
		mIndicator.setIndicatorCurrentNumber(0);
	}
	
	public void doKeyboardCharPress(View v)
	{
		if(mBuf.length() >= 10)
		{
			ToastUtils.getShowToast().showAnimationToast(mContext, getString(R.string.search_keyword_limit), Toast.LENGTH_LONG);
			return;
		}
		mBuf.append(((TextView)v).getText());
		mKeyStr.setText(mBuf.toString());
		mKeyWord = mBuf.toString();
		
		mTime = 1;
		reInit();
		requestSingerSearch();
		
		//点击搜索BI
		BiMsg.sendSearchBiMsg(mKeyWord);
	}
	
	public void doKeyboardClear(View v)
	{
		int len = mBuf.length();
		if(len == 0)
		{
			return;
		}
		mBuf.delete(0, len);
		mKeyStr.setText(mBuf.toString());
		mKeyWord = mBuf.toString();
		
		setVisibleState(false);
		reInit();
		setTempSingerCategoryList();
	}
	
	public void doKeyboardDel(View v)
	{
		int len = mBuf.length();
		if(len == 0)
		{
			return;
		}
		mBuf.deleteCharAt(len - 1);
		mKeyStr.setText(mBuf.toString());
		mKeyWord = mBuf.toString();
		
		if (mBuf.length() > 0)
		{
			mTime = 1;
			reInit();
			requestSingerSearch();
			
			//点击搜索BI
			BiMsg.sendSearchBiMsg(mKeyWord);
		}
		else
		{
			setVisibleState(false);
			reInit();
			setTempSingerCategoryList();
		}
	}
	
	@Override
	public void onAnimationStart(Animation animation) 
	{
	}

	@Override
	public void onAnimationEnd(Animation animation) 
	{
		if (animation == mScaleBig) 
		{
			View view = getCurrentFocus();
			Common.amplifyItem(view, mFocus, 0.10);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) 
	{
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if (!hasFocus) 
		{
			v.setSelected(false);
			mFocus.setVisibility(View.GONE);
			mScaleSmall = AnimationUtils.loadAnimation(mContext, FlagConstant.SCALE_VOD_SMALL_ANIMS);
			mScaleSmall.setFillAfter(false);
			mScaleSmall.setAnimationListener(this);
			v.startAnimation(mScaleSmall);
		}
		else
		{
			v.setSelected(true);
			mScaleBig = AnimationUtils.loadAnimation(mContext, FlagConstant.SCALE_VOD_BIG_ANIMS);
			mScaleBig.setFillAfter(true);
			mScaleBig.setAnimationListener(this);
			v.startAnimation(mScaleBig);
			v.bringToFront();
		}
	}

	@Override
	public void onClick(View v) 
	{
		int curIndex = getCurFocusIndex() + (mCurPage-1)*PAGE_SIZE;
		Intent intent = new Intent(mContext, SingerListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(FlagConstant.COLUMN, mCategoryList.get(curIndex));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private int getCurFocusIndex()
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			if (getCurrentFocus() == mCategoryItem[i])
			{
				return i;
			}
		}
		return 0;
	}
	
	@Override
	public void onCancel(OutPacket out, int token) 
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccessful(Object in, int token) 
	{
		try 
		{
			if (!mSearching)
			{
				mTempList.clear();
				ArrayList<Column> temp = new ArrayList<Column>(); 
				temp = (ArrayList<Column>) RequestDataManager.getData(in);
				for (int i=0; i<temp.size(); i++)
				{
					mCategoryList.add(temp.get(i));
					mTempList.add(temp.get(i));
					mDBList.add(temp.get(i));
				}
				
				if (temp.size() > 0)
				{
					if (mTime == 1)
					{
						mCount = RequestDataManager.getTotal(in);
						mTotPage = (mCount-1)/PAGE_SIZE + 1;
					}
					mLoadPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
					mCurPage++;
					mWaitting.setVisibility(View.GONE);
					freshSingerCategoryList();
					mHandler.sendEmptyMessageDelayed(UPDATE_DB, 1500);
				}
				else
				{
					onNetError(-1, "error", null, token);
				}
			}
			else
			{
				ElementListData data = (ElementListData) in;
				String lastKeyword =  data.getArgument().get(1);
				int page = data.getPage();
				Logcat.i(FlagConstant.TAG, " +++++++++=  lastKeyword: " + lastKeyword + ", mKeyWord: " + mKeyWord);
				Logcat.i(FlagConstant.TAG, " +++++++++=  mTime: " + mTime + ", page: " + page);
				
				if (mTime == page && Common.contrast(lastKeyword, mKeyWord))
				{
					ArrayList<Column> temp = new ArrayList<Column>(); 
					temp = (ArrayList<Column>) RequestDataManager.getData(in);
					for (int i=0; i<temp.size(); i++)
					{
						mCategoryList.add(temp.get(i));
					}
					
					if (temp.size() > 0)
					{
						if (mTime == 1)
						{
							mCount = RequestDataManager.getTotal(in);
							mTotPage = (mCount-1)/PAGE_SIZE + 1;
						}
						mLoadPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
						mCurPage++;
						mWaitting.setVisibility(View.GONE);
						freshSingerCategoryList();
						//搜索耗时BI
						BiMsg.sendSearchTimeBiMsg(mListId);
					}
					else
					{
						onNetError(-1, "error", null, token);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " SingerCategoryActivity onSuccessful error + " + e.toString());
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		if (!mSearching && mTime == 1)
		{
			mCategoryList.clear();
			ArrayList<Column> columnList = new ArrayList<Column>();
			columnList = ColumnInfoTable.queryColumn(ColumnInfoTable.COLUMNTABLE_SINGER_CATEGORY);
			Logcat.i(FlagConstant.TAG, " +++++++++=  columnList.size: " + columnList.size());
			for (int i=0; i<columnList.size(); i++)
			{
				mCategoryList.add(columnList.get(i));
			}
			
			if (mCategoryList.size() > 0)
			{
				mCount = mCategoryList.size();
				mTotPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
				mLoadPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
				mCurPage++;
				mWaitting.setVisibility(View.GONE);
				freshSingerCategoryList();
				
			}
			else
			{
				mWaitting.setVisibility(View.GONE);
				mEmptyView.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			mWaitting.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.VISIBLE);
		}
		if (mSearching)
		{
			//搜索耗时BI
			BiMsg.sendSearchTimeBiMsg(mListId);
		}
	}
	
	private void freshSingerCategoryList() 
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			if(i + (mCurPage-1)*PAGE_SIZE < mCount)
			{
				int curIndex = i + (mCurPage-1)*PAGE_SIZE;
				Column item = mCategoryList.get(curIndex);
				mCategoryItem[i].setName(item.getName());
				mCategoryItem[i].setImageView(item.getImage());
				mCategoryItem[i].setVisibility(View.VISIBLE);
			}
			else
			{
				mCategoryItem[i].setVisibility(View.GONE);
			}
		}
		setDefaultFocus();
		updateIndicator();
	}
	
	private void setDefaultFocus()
	{
		switch (mAction)
		{
		case ACTION_DEFAULT:
		{
			mCategoryItem[0].requestFocus();
			break;
		}
		case ACTION_ZERO:
		{
			mCategoryItem[4].requestFocus();
			break;
		}	
		case ACTION_FOUR:
		{
			mCategoryItem[0].requestFocus();
			break;
		}
		case ACTION_FIVE:
		{
			mCategoryItem[9].requestFocus();
			break;
		}
		case ACTION_NINE:
		{
			if (mCount-1-(mCurPage-1)*PAGE_SIZE < PAGE_SIZE/2)
			{
				mCategoryItem[0].requestFocus();
			}
			else
			{
				mCategoryItem[5].requestFocus();
			}
			break;
		}
		default:
			break;
		}
	}
	
	private void updateIndicator()
	{
		if (mCurPage > 0 && mCurPage <= mTotPage)
		{
			mIndicator.setIndicatorTotalNumber(mTotPage);
			mIndicator.setIndicatorCurrentNumber(mCurPage);
		}
	}
	
	private void setTempSingerCategoryList()
	{
		Logcat.i(FlagConstant.TAG, " +++++++++=  mTempList.size: " + mTempList.size());
		for (int i=0; i<mTempList.size(); i++)
		{
			mCategoryList.add(mTempList.get(i));
		}
		
		if (mCategoryList.size() > 0)
		{
			mCount = mCategoryList.size();
			mTotPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
			mLoadPage = (mCategoryList.size()-1)/PAGE_SIZE + 1;
			mCurPage++;
			freshSingerCategoryList();
			mEmptyView.setVisibility(View.GONE);
		}
		else
		{
			mWaitting.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.VISIBLE);
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
			/*case KeyEvent.KEYCODE_MENU:
			{
				showKeyboard();
				break;
			}*/
			case KeyEvent.KEYCODE_DPAD_DOWN:
			{
				nRet = doKeyDown();
				break;
			}
			case KeyEvent.KEYCODE_DPAD_UP:
			{
				nRet = doKeyUp();
				break;
			}	
			case KeyEvent.KEYCODE_PAGE_DOWN:
			{
				nRet = doKeyPageDown();
				break;
			}
			case KeyEvent.KEYCODE_PAGE_UP:
			{
				nRet = doKeyPageUp();
				break;
			}
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
	
	private void showKeyboard()
	{
		if(mDiaSearch == null)
		{
			mDiaSearch = new DialogSearchSinger(SingerCategoryActivity.this, R.style.search_singer_dialog);
			mDiaSearch.show();
		}
		else
		{
			mDiaSearch.show();
		}
	}

	private boolean doKeyDown() 
	{
		return false;
	}

	private boolean doKeyUp() 
	{
		return false;
	}

	private boolean doKeyPageDown() 
	{
		if (mCurPage < mTotPage)
		{
			if (mCurPage < mLoadPage)
			{
				mCurPage++;
				mAction = ACTION_DEFAULT;
				freshSingerCategoryList();
			}
			else
			{
				if(NetUtil.isNetConnected(mContext))
				{
					mAction = ACTION_DEFAULT;
					mTime++;
					if (!mSearching)
					{
						requestColumn();
					}
					else
					{
						requestSingerSearch();
					}
				}
				else
				{
					ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				}
			}
			return true;
		}
		return false;
	}

	private boolean doKeyPageUp() 
	{
		if (mCurPage > 1)
		{
			mCurPage--;
			mAction = ACTION_DEFAULT;
			freshSingerCategoryList();
			return true;
		}
		return false;
	}

	private boolean doKeyLeft() 
	{
		int curIndex = getCurFocusIndex();
		if (mTotPage == 1)
		{
			if (curIndex == 0)
			{
				mCategoryItem[mCount-1].requestFocus();
				return true;
			}
			else if (curIndex == 5)
			{
				mCategoryItem[4].requestFocus();
				return true;
			}
		}
		else if (mTotPage > 1)
		{
			if (mCurPage > 1)
			{
				if (curIndex == 0 || curIndex == 5)
				{
					TranslateAnimation animation = new TranslateAnimation(-12800, 0, 0, 0);
					animation.setDuration(400);
					animation.setFillAfter(true);
					mView.startAnimation(animation);
					
					if (curIndex == 0)
					{
						mCurPage--;
						mAction = ACTION_ZERO;
						freshSingerCategoryList();
					}
					else if (curIndex == 5)
					{
						mCurPage--;
						mAction = ACTION_FIVE;
						freshSingerCategoryList();
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean doKeyRight() 
	{
		int curIndex = getCurFocusIndex();
		if (mTotPage == 1)
		{
			if (curIndex == mCount-1)
			{
				mCategoryItem[0].requestFocus();
				return true;
			}
			else if (curIndex == 4)
			{
				mCategoryItem[5].requestFocus();
				return true;
			}
		}
		else if (mTotPage > 1)
		{
			if (mCurPage < mTotPage)
			{
				if (curIndex == 4 || curIndex == 9)
				{
					if (mCurPage < mLoadPage)
					{
						TranslateAnimation animation = new TranslateAnimation(1280, 0, 0, 0);
						animation.setDuration(400);
						animation.setFillAfter(true);
						mView.startAnimation(animation);
						
						if (curIndex == 4)
						{
							mCurPage++;
							mAction = ACTION_FOUR;
							freshSingerCategoryList();
						}
						else if (curIndex == 9)
						{
							mCurPage++;
							mAction = ACTION_NINE;
							freshSingerCategoryList();
						}
					}
					else
					{
						if(NetUtil.isNetConnected(mContext))
						{
							mAction = ACTION_DEFAULT;
							mTime++;
							if (!mSearching)
							{
								requestColumn();
							}
							else
							{
								requestSingerSearch();
							}
						}
						else
						{
							ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
						}
					}
					return true;
				}
			}
		}
		return false;
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
			case UPDATE_DB:
			{
				ColumnInfoTable.insertColumnList(mDBList, ColumnInfoTable.COLUMNTABLE_SINGER_CATEGORY);
				break;
			}
			default:
				break;
			}
		}
	};
}
