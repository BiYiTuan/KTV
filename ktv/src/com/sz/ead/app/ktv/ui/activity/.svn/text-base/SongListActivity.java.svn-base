/**
 * @Title: SongListActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 华语K歌榜
 * @author: zhaoqy
 * @date: 2015-8-3 上午10:46:41
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.bi.BI;
import com.sz.ead.app.ktv.bi.BiMsg;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.db.CollectInfoTable;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.ui.adapter.SongAdapter;
import com.sz.ead.app.ktv.ui.widget.EmptyView;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.ImageLoaderUtils;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class SongListActivity extends BaseActivity implements UICallBack, OnFocusChangeListener, OnItemClickListener
{
	private Context          mContext;
	private ImageView        mPoster;
	private TextView         mName;
	private TextView         mCountText;
	private ListView         mListView;
	private ImageView        mWaitting;
	private EmptyView        mEmptyView;
	private ArrayList<Video> mVideoList;
	private SongAdapter      mAdapter;
	private Column           mColumn;
	private String           mColumnCode;
	private int              mNextFocus;      //根据列表的上一个焦点位置来决定的列表item中的图标选中位置
	private int              mTime = 1;
	private int              mTotPage = 0;    //总页数
	private int              mLoadPage = 0;   //下载页数
	private int              mListId = 0;     //点播列表耗时启动id

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song);
		mContext = this;
		
		findViews();
		setAdapters();
		setListeners();
		getColumn();
		requestColumnSub();
	}

	private void findViews() 
	{
		mPoster = (ImageView) findViewById(R.id.id_song_poster);
		mName = (TextView) findViewById(R.id.id_song_name);
		mCountText = (TextView) findViewById(R.id.id_song_count);
		mListView = (ListView) findViewById(R.id.id_song_listview);
		mWaitting = (ImageView) findViewById(R.id.id_song_waitting);
		mEmptyView = (EmptyView) findViewById(R.id.id_song_empty);
	}

	private void setAdapters() 
	{
		mVideoList = new ArrayList<Video>();
		mVideoList.clear();
		mAdapter = new SongAdapter(mContext, mVideoList, mListView);
		mListView.setAdapter(mAdapter);
	}
	
	private void setListeners() 
	{
		mListView.setOnFocusChangeListener(this);
		mListView.setOnItemClickListener(this);
	}
	
	private void getColumn() 
	{
		Bundle bundle = getIntent().getExtras();
		mColumn = bundle.getParcelable(FlagConstant.COLUMN);
		if (mColumn != null)
		{
			mColumnCode = mColumn.getColumnCode();
			ImageLoaderUtils.mImageLoader.displayImage(mColumn.getImage(), mPoster, ImageLoaderUtils.mSongOption);
			mName.setText(mColumn.getName());
			mCountText.setText(String.format(getString(R.string.albumlist_count), "" + 0));
		}
	}

	private void requestColumnSub()
	{
		mListId = BI.getStartTimeId();
		mWaitting.setVisibility(View.VISIBLE);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMNSUB, SongAdapter.PAGE_NUM * 2, mTime, mColumnCode);
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
			mWaitting.setVisibility(View.GONE);
			ArrayList<Video> temp = new ArrayList<Video>(); 
			temp = (ArrayList<Video>) RequestDataManager.getData(in);
			for (int i=0; i<temp.size(); i++)
			{
				Video item = temp.get(i);
				int size = item.getDramaList().size();
				if (size > 0)
				{
					item.setUrl(item.getDramaList().get(0).getUrl());
				}
				mVideoList.add(item);
			}
			
			if (temp.size() > 0)
			{
				if (mTime == 1)
				{
					mAdapter.notifyDataSetChanged();
					mListView.requestFocus();
					mListView.setSelection(0);
					mAdapter.setFocusPosition(0);
					
					int count = RequestDataManager.getTotal(in);
					mCountText.setText(String.format(getString(R.string.albumlist_count), "" + count));
					mTotPage = (count-1)/SongAdapter.PAGE_NUM + 1;
					mLoadPage = (mVideoList.size()-1)/SongAdapter.PAGE_NUM + 1;
				}
				else
				{
					mLoadPage = (mVideoList.size()-1)/SongAdapter.PAGE_NUM + 1;
					boolean nRet = mAdapter.setPage(mAdapter.getPage() + 1);
					if(nRet)
					{
						mListView.setSelection(mListView.getFirstVisiblePosition());
					}
				}
				//点播列表耗时BI
				BiMsg.sendDemandListTimeBiMsg("", "2", mListId);
			}
			else
			{
				onNetError(-1, "error", null, token);
			}
		}
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " SongListActivity onSuccessful error + " + e.toString());
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		mWaitting.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
		mCountText.setText(String.format(getString(R.string.albumlist_count), "" + 0));
		//点播列表耗时BI
		BiMsg.sendDemandListTimeBiMsg("", "2", mListId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (mAdapter.getFocusPosition()) 
		{
		case 0:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			int curIndex = position + mAdapter.getPage() * SongAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Intent intent = new Intent(mContext, PlayerActivity.class);
			startActivity(intent);
			break;
		}
		case 1:
		{
			int curIndex = position + mAdapter.getPage() * SongAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Spanned text = Html.fromHtml(String.format(getString(R.string.tip_select_song), mVideoList.get(curIndex).getName()));
			ToastUtils.getShowToast().showAnimationAddTipToast(mContext, text, Toast.LENGTH_LONG);
			break;
		}
		case 2:
		{
			int curIndex = position + mAdapter.getPage() * SongAdapter.PAGE_NUM;
			if (CollectInfoTable.isCollectsVideoExist(mVideoList.get(curIndex).getResCode()))
			{
				CollectInfoTable.deleteCollectsVideo(mVideoList.get(curIndex).getResCode());
			}
			else
			{
				CollectInfoTable.insertCollectsVideo(mVideoList.get(curIndex));
			}
			mAdapter.setFocusPosition(2);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if(hasFocus)
		{
			mAdapter.setFocusPosition(mNextFocus);
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
			case KeyEvent.KEYCODE_DPAD_UP:
			{
				nRet = doKeyUp();
				break;
			}
			case KeyEvent.KEYCODE_DPAD_DOWN:
			{
				nRet = doKeyDown();
				break;
			}
			case KeyEvent.KEYCODE_PAGE_UP:
			{
				nRet = doKeyPageUp();
				break;
			}
			case KeyEvent.KEYCODE_PAGE_DOWN:
			{
				nRet = doKeyPageDown();
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

	private boolean doKeyUp() 
	{
		boolean nRet = false;
		
		if(mListView.isFocused() && mListView.getSelectedItemPosition() == 0)
		{
			nRet = mAdapter.setPage(mAdapter.getPage()-1);
			if(nRet)
			{
				mListView.setSelection(SongAdapter.PAGE_NUM-1);
			}
		}
		return nRet;
	}
	
	private boolean doKeyDown() 
	{
		boolean nRet = false;
		
		if(mListView.isFocused() && mListView.getLastVisiblePosition() == mListView.getSelectedItemPosition())
		{
			if (mAdapter.getPage()+1 < mTotPage)
			{
				if (mAdapter.getPage()+1 < mLoadPage)
				{
					nRet = mAdapter.setPage(mAdapter.getPage()+1);
					if(nRet)
					{
						mListView.setSelection(mListView.getFirstVisiblePosition());
					}
				}
				else
				{
					if(NetUtil.isNetConnected(mContext))
					{
						mTime++;
						requestColumnSub();
						nRet = true;
					}
					else
					{
						ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
						nRet = true;
					}
				}
			}
		}
		return nRet;
	}
	
	private boolean doKeyPageUp() 
	{
		boolean nRet = false;
		
		if(mListView.isFocused())
		{
			if (mAdapter.getPage() > 0)
			{
				nRet = mAdapter.setPage(mAdapter.getPage()-1);
				if(nRet)
				{
					mListView.setSelection(mListView.getFirstVisiblePosition());
				}
			}
			else
			{
				nRet = true;
			}
		}
		return nRet;
	}

	private boolean doKeyPageDown() 
	{
		boolean nRet = false;
		
		if(mListView.isFocused())
		{
			if (mAdapter.getPage()+1 < mTotPage)
			{
				if (mAdapter.getPage()+1 < mLoadPage)
				{
					nRet = mAdapter.setPage(mAdapter.getPage()+1);
					if(nRet)
					{
						mListView.setSelection(mListView.getFirstVisiblePosition());
					}
				}
				else
				{
					if(NetUtil.isNetConnected(mContext))
					{
						mTime++;
						requestColumnSub();
						nRet = true;
					}
					else
					{
						ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
						nRet = true;
					}
				}
			}
			else
			{
				nRet = true;
			}
		}
		return nRet;
	}
	
	private boolean doKeyLeft() 
	{
		boolean nRet = false;
		
		if (mListView.isFocused())
		{
			switch (mAdapter.getFocusPosition()) 
			{
			case 1:
			{
				mAdapter.setFocusPosition(0);
				nRet = true;
				break;
			}
			case 2:
			{
				mAdapter.setFocusPosition(1);
				nRet = true;
				break;
			}	
			default:
				break;
			}
		}
		return nRet;
	}
	
	private boolean doKeyRight() 
	{
		boolean nRet = false;
		
		if (mListView.isFocused())
		{
			switch (mAdapter.getFocusPosition()) 
			{
			case 0:
			{
				mAdapter.setFocusPosition(1);
				nRet = true;
				break;
			}
			case 1:
			{
				mAdapter.setFocusPosition(2);
				nRet = true;
				break;
			}	
			default:
				break;
			}
		}
		return nRet;
	}
}
