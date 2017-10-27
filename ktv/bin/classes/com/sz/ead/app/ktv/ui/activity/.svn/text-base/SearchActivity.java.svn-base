/**
 * @Title: SearchActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 搜索页
 * @author: zhaoqy
 * @date: 2015-8-3 上午11:30:54
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
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.bi.BI;
import com.sz.ead.app.ktv.bi.BiMsg;
import com.sz.ead.app.ktv.dataprovider.datapacket.ElementListData;
import com.sz.ead.app.ktv.dataprovider.entity.Hotword;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.db.CollectInfoTable;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.ui.adapter.HotWordAdapter;
import com.sz.ead.app.ktv.ui.adapter.SearchAdapter;
import com.sz.ead.app.ktv.utils.Common;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class SearchActivity extends BaseActivity implements UICallBack, OnItemClickListener, OnFocusChangeListener
{
	private Context            mContext;
	private TextView           mKeys;
	private RelativeLayout     mHotwordView;
	private GridView           mHotword;
	private ImageView          mHotwordWaitting;
	private TextView           mHotwordNo;
	private ArrayList<Hotword> mHotwordList;
	private HotWordAdapter     mHotAdapter;
	private RelativeLayout     mResultView;
	private TextView           mResultName;
	private TextView           mResultCount;
	private ListView           mResultListView;
	private ImageView          mResultWaitting;
	private TextView           mResultNo;
	private ArrayList<Video>   mVideoList;
	private SearchAdapter      mResultAdapter;
	private StringBuffer       mBuf;            //用户输入的关键字
	private String             mKeyword;        //搜索节目的关键字，通过网络获得的.热词
	private int                mNextFocus;      //结果列表中播放、添加和收藏三个按钮需要选择的位置
	private int                mTime = 1;
	private int                mTotPage = 0;    //总页数
	private int                mLoadPage = 0;   //下载页数
	private int                mListId = 0;     //搜索列表耗时启动id

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mContext = this;
		
		findViews();
		initData();
		setAdapter();
		setListener();
		requestHotword();
	}

	private void findViews() 
	{
		mKeys = (TextView) findViewById(R.id.tv_search_keystr);
		
		ViewStub hotword = (ViewStub) findViewById(R.id.id_search_hotword);  
		mHotwordView = (RelativeLayout) hotword.inflate();
		mHotword = (GridView) mHotwordView.findViewById(R.id.id_search_hotword_gridview);
		mHotwordWaitting = (ImageView) mHotwordView.findViewById(R.id.id_search_hotword_waitting);
		mHotwordNo = (TextView) mHotwordView.findViewById(R.id.id_search_hotword_no);
		
		ViewStub result = (ViewStub) findViewById(R.id.id_search_result);  
		mResultView = (RelativeLayout) result.inflate();
		mResultName = (TextView) mResultView.findViewById(R.id.id_search_result_name);
		mResultCount = (TextView) mResultView.findViewById(R.id.id_search_result_count);
		mResultListView = (ListView) mResultView.findViewById(R.id.id_search_result_listview);
		mResultWaitting = (ImageView) mResultView.findViewById(R.id.id_search_result_waitting);
		mResultNo = (TextView) mResultView.findViewById(R.id.id_search_result_no);
	}
	
	private void initData()
	{
		mBuf = new StringBuffer();
		mHotwordList = new ArrayList<Hotword>();
		mVideoList = new ArrayList<Video>();
		mHotwordList.clear();
		mVideoList.clear();
	}
	
	private void setAdapter()
	{
		mHotAdapter = new HotWordAdapter(mContext, mHotwordList);
		mHotword.setAdapter(mHotAdapter);
		
		mResultAdapter = new SearchAdapter(mContext, mVideoList, mResultListView);
		mResultListView.setAdapter(mResultAdapter);
	}
	
	private void setListener()
	{
		mHotword.setOnItemClickListener(this);
		mResultListView.setOnItemClickListener(this);
		mResultListView.setOnFocusChangeListener(this);
	}
	
	private void requestHotword()
	{
		mHotwordView.setVisibility(View.VISIBLE);
		mHotwordWaitting.setVisibility(View.VISIBLE);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_HOTWORD, 0, 0, "");
	}
	
	private void requestSearchResult()
	{
		if (mTime == 1)
		{
			mResultNo.setVisibility(View.GONE);
			mHotwordView.setVisibility(View.GONE);
			mResultView.setVisibility(View.VISIBLE);
			mResultListView.setVisibility(View.VISIBLE);
			mResultName.setText(mKeyword);
			mResultCount.setVisibility(View.INVISIBLE);
			mResultWaitting.setVisibility(View.VISIBLE);
		}
		mListId = BI.getStartTimeId();
		RequestDataManager.requestData(this, mContext, Token.TOKEN_SEARCH, SearchAdapter.PAGE_NUM * 2, mTime, mKeyword);
	}
	
	private void reInit()
	{
		mVideoList.clear();
		mTotPage = 0;
		mLoadPage = 0;   
	}
	
	public void doKeyboardCharPress(View v)
	{
		if(mBuf.length() >= 10)
		{
			ToastUtils.getShowToast().showAnimationToast(mContext, getString(R.string.search_keyword_limit), Toast.LENGTH_LONG);
			return;
		}
		mBuf.append(((TextView)v).getText());
		mKeys.setText(mBuf.toString());
		mKeyword = mBuf.toString();
		
		mTime = 1;
		reInit();
		requestSearchResult();
		
		//点击搜索BI
		BiMsg.sendSearchBiMsg(mKeyword);
	}
	
	public void doKeyboardClear(View v)
	{
		int len = mBuf.length();
		if(len == 0)
		{
			return;
		}
		mBuf.delete(0, len);
		mKeys.setText(mBuf.toString());
		mKeyword = mBuf.toString();
		
		//显示热词
		reInit();
		mHotwordView.setVisibility(View.VISIBLE);
		mResultView.setVisibility(View.GONE);
	}
	
	public void doKeyboardDel(View v)
	{
		int len = mBuf.length();
		if(len == 0)
		{
			return;
		}
		mBuf.deleteCharAt(len-1);
		mKeys.setText(mBuf.toString());
		mKeyword = mBuf.toString();
		
		if (mBuf.length() > 0)
		{
			mTime = 1;
			reInit();
			requestSearchResult();
			
			//点击搜索BI
			BiMsg.sendSearchBiMsg(mKeyword);
		}
		else
		{
			//显示热词
			reInit();
			mHotwordView.setVisibility(View.VISIBLE);
			mResultView.setVisibility(View.GONE);
		}
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
		switch (token) 
		{
		case Token.TOKEN_HOTWORD:
		{
			mHotwordWaitting.setVisibility(View.GONE);
			ArrayList<Hotword> temp = new ArrayList<Hotword>();
			temp = (ArrayList<Hotword>) RequestDataManager.getData(in);
			for (int i=0; i<temp.size(); i++)
			{
				if (mHotwordList.size() < 18)
				{
					//最多可以展示18个热词
					mHotwordList.add(temp.get(i));
				}
			}
			
			if (mHotwordList.size() > 0)
			{
				mHotAdapter.notifyDataSetChanged();
				mHotword.requestFocus();
				mHotword.setSelection(0);
			}
			else
			{
				onNetError(-1, "error", null, token);
			}
			break;
		}
		case Token.TOKEN_SEARCH:
		{
			mResultWaitting.setVisibility(View.GONE);
			ElementListData data = (ElementListData) in;
			String lastKeyword =  data.getArgument().get(0);
			int page = data.getPage();
			Logcat.i(FlagConstant.TAG, " +++++++++=  lastKeyword: " + lastKeyword + ", mKeyWord: " + mKeyword);
			Logcat.i(FlagConstant.TAG, " +++++++++=  mTime: " + mTime + ", page: " + page);
			
			if (mTime == page && Common.contrast(lastKeyword, mKeyword))
			{
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
						mResultAdapter.notifyDataSetChanged();
						
						int count = RequestDataManager.getTotal(in);
						mResultCount.setVisibility(View.VISIBLE);
						mResultCount.setText("(" + count + ")");
						mTotPage = (count-1)/SearchAdapter.PAGE_NUM + 1;
						mLoadPage = (mVideoList.size()-1)/SearchAdapter.PAGE_NUM + 1;
					}
					else
					{
						mLoadPage = (mVideoList.size()-1)/SearchAdapter.PAGE_NUM + 1;
						boolean nRet = mResultAdapter.setPage(mResultAdapter.getPage() + 1);
						if(nRet)
						{
							mResultListView.setSelection(mResultListView.getFirstVisiblePosition());
						}
					}
					//搜索耗时BI
					BiMsg.sendSearchTimeBiMsg(mListId);
				}
				else
				{
					onNetError(-1, "error", null, token);
				}
			}
			break;
		}	
		default:
			break;
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		switch (token) 
		{
		case Token.TOKEN_HOTWORD:
		{
			mHotwordWaitting.setVisibility(View.GONE);
			mHotword.setVisibility(View.GONE);
			mHotwordNo.setVisibility(View.VISIBLE);
			((TextView)findViewById(R.id.tv_search_a)).requestFocus();
			break;
		}
		case Token.TOKEN_SEARCH:
		{
			if (mTime == 1)
			{
				mResultWaitting.setVisibility(View.GONE);
				mResultListView.setVisibility(View.GONE);
				mResultNo.setVisibility(View.VISIBLE);
				mResultCount.setVisibility(View.VISIBLE);
				mResultCount.setText("(" + 0 + ")");
			}
			//搜索耗时BI
			BiMsg.sendSearchTimeBiMsg(mListId);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (parent.getId()) 
		{
		case R.id.id_search_hotword_gridview:
		{
			mBuf.setLength(0);
			mBuf.append(mHotwordList.get(position).getName());
			mKeys.setText(mBuf.toString());
			mKeyword = mBuf.toString();
			
			mTime = 1;
			reInit();
			requestSearchResult();
			
			//点击热词BI
			BiMsg.sendHotSearchBiMsg(mHotwordList.get(position).getName());	
			break;
		}
		case R.id.id_search_result_listview:
		{
			if(mResultAdapter.getFocusPosition() == 0)
			{
				if(!NetUtil.isNetConnected(mContext))
				{
					ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
					return;
				}
				int curIndex = position + SearchAdapter.PAGE_NUM * mResultAdapter.getPage();
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
			else if(mResultAdapter.getFocusPosition() == 1)
			{
				int curIndex = position + SearchAdapter.PAGE_NUM * mResultAdapter.getPage();
				if (mVideoList.get(curIndex).getResCode().isEmpty() || 
					mVideoList.get(curIndex).getName().isEmpty() || 
					mVideoList.get(curIndex).getUrl().isEmpty())
				{
					ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
					return;
				}
				//插入待唱Table中
				SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
				Spanned text = Html.fromHtml(String.format(getString(R.string.tip_select_song), mVideoList.get(curIndex).getName()));
				ToastUtils.getShowToast().showAnimationAddTipToast(mContext, text, Toast.LENGTH_LONG);
			}
			else if(mResultAdapter.getFocusPosition() == 2)
			{
				int curIndex = position + SearchAdapter.PAGE_NUM * mResultAdapter.getPage();
				if (CollectInfoTable.isCollectsVideoExist(mVideoList.get(curIndex).getResCode()))
				{
					CollectInfoTable.deleteCollectsVideo(mVideoList.get(curIndex).getResCode());
				}
				else
				{
					CollectInfoTable.insertCollectsVideo(mVideoList.get(curIndex));
				}
				mResultAdapter.setFocusPosition(2);
			}
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
			mResultAdapter.setFocusPosition(mNextFocus);
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
		
		if(mResultListView.isFocused() && mResultListView.getSelectedItemPosition() == 0)
		{
			nRet = mResultAdapter.setPage(mResultAdapter.getPage()-1);
			if(nRet)
			{
				mResultListView.setSelection(SearchAdapter.PAGE_NUM-1);
			}
		}
		return nRet;
	}
	
	private boolean doKeyDown() 
	{
		boolean nRet = false;
		
		if(mResultListView.isFocused())
		{
			if (mResultListView.getLastVisiblePosition() == mResultListView.getSelectedItemPosition())
			{
				if (mResultAdapter.getPage()+1 < mTotPage)
				{
					if (mResultAdapter.getPage()+1 < mLoadPage)
					{
						nRet = mResultAdapter.setPage(mResultAdapter.getPage()+1);
						if(nRet)
						{
							mResultListView.setSelection(mResultListView.getFirstVisiblePosition());
						}
					}
					else
					{
						if(NetUtil.isNetConnected(mContext))
						{
							mTime++;
							requestSearchResult();
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
					if (mResultAdapter.getPage() * SearchAdapter.PAGE_NUM + mResultListView.getSelectedItemPosition() == mVideoList.size()-1)
					{
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
		
		if(mResultListView.isFocused())
		{
			if (mResultAdapter.getPage() > 0)
			{
				nRet = mResultAdapter.setPage(mResultAdapter.getPage()-1);
				if(nRet)
				{
					mResultListView.setSelection(mResultListView.getFirstVisiblePosition());
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
		
		if(mResultListView.isFocused())
		{
			if (mResultAdapter.getPage()+1 < mTotPage)
			{
				if (mResultAdapter.getPage()+1 < mLoadPage)
				{
					nRet = mResultAdapter.setPage(mResultAdapter.getPage()+1);
					if(nRet)
					{
						mResultListView.setSelection(mResultListView.getFirstVisiblePosition());
					}
				}
				else
				{
					if(NetUtil.isNetConnected(mContext))
					{
						mTime++;
						requestSearchResult();
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
		
		if(mResultListView.isFocused())
		{
			switch (mResultAdapter.getFocusPosition()) 
			{
			case 1:
			{
				mResultAdapter.setFocusPosition(0);
				nRet = true;
				break;
			}
			case 2:
			{
				mResultAdapter.setFocusPosition(1);
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
		
		if(mResultListView.isFocused())
		{
			switch (mResultAdapter.getFocusPosition()) 
			{
			case 0:
			{
				mResultAdapter.setFocusPosition(1);
				nRet = true;
				break;
			}
			case 1:
			{
				mResultAdapter.setFocusPosition(2);
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
