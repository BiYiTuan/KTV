/**
 * @Title: SingerListActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 歌手列表
 * @author: zhaoqy
 * @date: 2015-8-3 下午3:44:19
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.sz.ead.app.ktv.ui.dialog.DialogSearchSinger;
import com.sz.ead.app.ktv.ui.widget.EmptyView;
import com.sz.ead.app.ktv.ui.widget.ImageRotation;
import com.sz.ead.app.ktv.ui.widget.Indicator;
import com.sz.ead.app.ktv.ui.widget.SingerView;
import com.sz.ead.app.ktv.utils.Common;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class SingerListActivity extends BaseActivity implements UICallBack, OnClickListener, OnFocusChangeListener, AnimationListener
{
	private static final int   PAGE_SIZE = 12; 
	private static final int   ACTION_DEFAULT = 0;
	private static final int   ACTION_ZERO = 1;
	private static final int   ACTION_FIVE = 2;
	private static final int   ACTION_SIX = 3;
	private static final int   ACTION_ELEVEN = 4;
	private Context            mContext;
	private Column             mColumn;
	private StringBuffer       mBuf = new StringBuffer(); //用户输入的关键字
	private ArrayList<Column>  mTempList = new ArrayList<Column>();
	private ArrayList<Column>  mSingerList = new ArrayList<Column>();
	private SingerView[]       mSingerView = new SingerView[PAGE_SIZE];
	private View               mView;
	private TextView           mName;
	private ImageRotation      mWaitting;
	private ImageView          mFocus;
	private Indicator          mIndicator;
	private EmptyView          mEmptyView;
	private Animation          mScaleBig;                                  
	private Animation          mScaleSmall;    
	private TextView           mCountText;
	private TextView           mKeyStr;
	private DialogSearchSinger mDiaSearch;
	private String             mColumnCode;
	private String             mKeyWord;
	private int                mCount = 0;      
	private int                mTotPage = 0;
	private int                mCurPage = 0;    
	private int                mLoadPage = 0;   
	private int                mTime = 1;
	private int                mAction = 0;
	private int                mListId = 0;      //搜索列表耗时启动id
	private boolean            mSearching;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singerlist);
		mContext = this;
		
		findViews();
		setListener();
		getColumn() ;
		requestColumn();
	}

	private void findViews() 
	{
		int resid[] = { R.id.id_singerlist_item0, R.id.id_singerlist_item1, R.id.id_singerlist_item2, R.id.id_singerlist_item3, 
				R.id.id_singerlist_item4,  R.id.id_singerlist_item5, R.id.id_singerlist_item6, R.id.id_singerlist_item7, 
				R.id.id_singerlist_item8, R.id.id_singerlist_item9, R.id.id_singerlist_item10, R.id.id_singerlist_item11 }; 
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mSingerView[i] = (SingerView) findViewById(resid[i]);
		}
		mName = (TextView) findViewById(R.id.id_singerlist_title);
		mCountText = (TextView) findViewById(R.id.id_singerlist_count);
		mKeyStr = (TextView) findViewById(R.id.id_singerlist_keystr);
		mView = findViewById(R.id.id_singerlist_item);
		mWaitting = (ImageRotation) findViewById(R.id.id_singerlist_imagerotation);
		mFocus = (ImageView) findViewById(R.id.id_singerlist_focus);
		mIndicator = (Indicator) findViewById(R.id.id_singerlist_indicator);
		mEmptyView = (EmptyView) findViewById(R.id.id_singerlist_empty);
	}
	
	private void setVisibleState(boolean visible)
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mSingerView[i].setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		}
	}
	
	private void setListener() 
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			mSingerView[i].setOnFocusChangeListener(this);
			mSingerView[i].setOnClickListener(this);
		}
	}
	
	private void getColumn() 
	{
		Bundle bundle = getIntent().getExtras();
		mColumn = bundle.getParcelable(FlagConstant.COLUMN);
		if (mColumn != null)
		{
			mColumnCode = mColumn.getColumnCode();
			mName.setText(mColumn.getName());
			mCountText.setText(String.format(getString(R.string.category_count), 0));
		}
		mSingerList.clear();
		mTempList.clear();
	}
	
	private void requestColumn()
	{
		mWaitting.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
		setVisibleState(false);
		mSearching = false;
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, PAGE_SIZE*2, mTime, mColumnCode, "");
	}
	
	private void requestSingerSearch()
	{
		mListId = BI.getStartTimeId();
		mWaitting.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
		setVisibleState(false);
		mSearching = true;
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, PAGE_SIZE*2, mTime, mColumnCode, mKeyWord);
	}
	
	private void reInit()
	{
		mSingerList.clear();
		mCount = 0;      
		mTotPage = 0;
		mCurPage = 0;    
		mLoadPage = 0;   
		mIndicator.setIndicatorTotalNumber(0);
		mIndicator.setIndicatorCurrentNumber(0);
		mCountText.setText(String.format(getString(R.string.category_count), mCount));
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
		setTepmSingerList();
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
			setTepmSingerList();
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
		Intent intent = new Intent(mContext, SongListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(FlagConstant.COLUMN, mSingerList.get(curIndex));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private int getCurFocusIndex()
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			if (getCurrentFocus() == mSingerView[i])
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
				ArrayList<Column> temp = new ArrayList<Column>(); 
				temp = (ArrayList<Column>) RequestDataManager.getData(in);
				for (int i=0; i<temp.size(); i++)
				{
					mSingerList.add(temp.get(i));
					mTempList.add(temp.get(i));
				}
				
				if (temp.size() > 0)
				{
					if (mTime == 1)
					{
						mCount = RequestDataManager.getTotal(in);
						mTotPage = (mCount-1)/PAGE_SIZE + 1;
						mCountText.setText(String.format(getString(R.string.category_count), mCount));
					}
					mLoadPage = (mSingerList.size()-1)/PAGE_SIZE + 1;
					mCurPage++;
					mWaitting.setVisibility(View.GONE);
					freshSingerList();
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
						mSingerList.add(temp.get(i));
					}
					
					if (temp.size() > 0)
					{
						if (mTime == 1)
						{
							mCount = RequestDataManager.getTotal(in);
							mTotPage = (mCount-1)/PAGE_SIZE + 1;
							mCountText.setText(String.format(getString(R.string.category_count), mCount));
						}
						mLoadPage = (mSingerList.size()-1)/PAGE_SIZE + 1;
						mCurPage++;
						mWaitting.setVisibility(View.GONE);
						freshSingerList();
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
		mWaitting.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
		if (mSearching)
		{
			//搜索耗时BI
			BiMsg.sendSearchTimeBiMsg(mListId);
		}
	}
	
	private void freshSingerList() 
	{
		for (int i=0; i<PAGE_SIZE; i++)
		{
			if(i + (mCurPage-1)*PAGE_SIZE < mCount)
			{
				int curIndex = i + (mCurPage-1)*PAGE_SIZE;
				Column item = mSingerList.get(curIndex);
				mSingerView[i].setName(item.getName());
				mSingerView[i].setImageView(item.getImage());
				mSingerView[i].setVisibility(View.VISIBLE);
			}
			else
			{
				mSingerView[i].setVisibility(View.GONE);
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
			mSingerView[0].requestFocus();
			break;
		}
		case ACTION_ZERO:
		{
			mSingerView[5].requestFocus();
			break;
		}	
		case ACTION_FIVE:
		{
			mSingerView[0].requestFocus();
			break;
		}
		case ACTION_SIX:
		{
			mSingerView[11].requestFocus();
			break;
		}
		case ACTION_ELEVEN:
		{
			if (mCount-1-(mCurPage-1)*PAGE_SIZE < PAGE_SIZE/2)
			{
				mSingerView[0].requestFocus();
			}
			else
			{
				mSingerView[6].requestFocus();
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
	
	private void setTepmSingerList()
	{
		Logcat.i(FlagConstant.TAG, " +++++++++=  mTempList.sixe: " + mTempList.size());
		for (int i=0; i<mTempList.size(); i++)
		{
			mSingerList.add(mTempList.get(i));
		}
		
		if (mSingerList.size() > 0)
		{
			mCount = mSingerList.size();
			mCountText.setText(String.format(getString(R.string.category_count), mCount));
			mTotPage = (mSingerList.size()-1)/PAGE_SIZE + 1;
			mLoadPage = (mSingerList.size()-1)/PAGE_SIZE + 1;
			mCurPage++;
			freshSingerList();
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
			case KeyEvent.KEYCODE_MENU:
			{
				showKeyboard();
				break;
			}
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
			mDiaSearch = new DialogSearchSinger(SingerListActivity.this, R.style.search_singer_dialog);
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
				freshSingerList();
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
			freshSingerList();
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
				mSingerView[mCount-1].requestFocus();
				return true;
			}
			else if (curIndex == 6)
			{
				mSingerView[5].requestFocus();
				return true;
			}
		}
		else if (mTotPage > 1)
		{
			if (mCurPage > 1)
			{
				if (curIndex == 0 || curIndex == 6)
				{
					TranslateAnimation animation = new TranslateAnimation(-12800, 0, 0, 0);
					animation.setDuration(400);
					animation.setFillAfter(true);
					mView.startAnimation(animation);
					
					if (curIndex == 0)
					{
						mCurPage--;
						mAction = ACTION_ZERO;
						freshSingerList();
					}
					else if (curIndex == 6)
					{
						mCurPage--;
						mAction = ACTION_SIX;
						freshSingerList();
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
				mSingerView[0].requestFocus();
				return true;
			}
			else if (curIndex == 5)
			{
				mSingerView[6].requestFocus();
				return true;
			}
		}
		else if (mTotPage > 1)
		{
			if (mCurPage < mTotPage)
			{
				if (curIndex == 5 || curIndex == 11)
				{
					if (mCurPage < mLoadPage)
					{
						TranslateAnimation animation = new TranslateAnimation(1280, 0, 0, 0);
						animation.setDuration(400);
						animation.setFillAfter(true);
						mView.startAnimation(animation);
						
						if (curIndex == 5)
						{
							mCurPage++;
							mAction = ACTION_FIVE;
							freshSingerList();
						}
						else if (curIndex == 11)
						{
							mCurPage++;
							mAction = ACTION_ELEVEN;
							freshSingerList();
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
}
