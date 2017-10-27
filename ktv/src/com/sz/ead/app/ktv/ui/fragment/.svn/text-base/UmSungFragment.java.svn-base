/**
 * @Title: UmSungFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: 已唱歌曲
 * @author: zhaoqy
 * @date: 2015-8-3 上午10:07:21
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.fragment;

import java.util.ArrayList;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.db.SungInfoTable;
import com.sz.ead.app.ktv.ui.activity.PlayerActivity;
import com.sz.ead.app.ktv.ui.adapter.UmSungAdapter;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class UmSungFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener, OnItemClickListener
{
	private UmSungAdapter    mAdapter;
	private ArrayList<Video> mVideoList;
	private ListView         mListView;
	private TextView         mCount;
	private Button           mClear;
	private Button           mDelDuplicate;
	private int              mNextFocus; 
	private int              mTempIndex = 0;
	private int              mTempPage = 0;
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mVideoList = SungInfoTable.querySungVideos();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_umsung, container, false);
		findViews(view);
		setAdapters();
		setSungCount();
		setListeners();
		registerIntentReceivers();
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() 
	{
		super.onResume();
	}
	
	private void findViews(View view) 
	{
		mListView = (ListView) view.findViewById(R.id.id_umsung_listview);
		mCount = (TextView) view.findViewById(R.id.id_umsung_count);
		mClear = (Button) view.findViewById(R.id.id_umsung_clear);
		mDelDuplicate = (Button) view.findViewById(R.id.id_umsung_delDuplicate);
	}

	private void setAdapters()
	{
		mAdapter = new UmSungAdapter(getActivity(), mVideoList, mListView);
		mListView.setAdapter(mAdapter);
		mListView.startLayoutAnimation();
	}
	
	private void setListeners() 
	{
		mClear.setOnClickListener(this);
		mDelDuplicate.setOnClickListener(this);
		mListView.setOnFocusChangeListener(this);
		mListView.setOnItemClickListener(this);
	}
	
	private void setSungCount()
	{
		mCount.setText(String.format(getString(R.string.um_count), mVideoList.size()));
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.id_umsung_clear:
		{
			if (mVideoList.size() > 0)
			{
				mVideoList.clear();
				mAdapter.notifyDataSetChanged();
				setSungCount();
				SungInfoTable.deleteSung();
			}
			break;
		}
		case R.id.id_umsung_delDuplicate:
		{
			SungInfoTable.removeDuplicateOfSung();
			resetUmSungFragment();
			break;
		}
		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (mAdapter.getFocusPosition()) 
		{
		case 0: //播放
		{
			if(!NetUtil.isNetConnected(getActivity()))
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			int curIndex = position + mAdapter.getPage() * UmSungAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			mTempIndex = position;
			mTempPage = mAdapter.getPage();
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			SungInfoTable.deleteSungVideoByRowID(video.getRowID());
			SelectedInfoTable.insertSelectedVideo(video);
			Intent intent = new Intent(getActivity(), PlayerActivity.class);
			startActivity(intent);
			break;
		}
		case 1: //添加
		{
			int curIndex = position + mAdapter.getPage() * UmSungAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Spanned text = Html.fromHtml(String.format(getString(R.string.tip_select_song), mVideoList.get(curIndex).getName()));
			ToastUtils.getShowToast().showAnimationAddTipToast(getActivity(), text, Toast.LENGTH_LONG);
			break;
		}	
		case 2: //删除
		{
			int curIndex = position + mAdapter.getPage() * UmSungAdapter.PAGE_NUM;
			int count = mVideoList.size();
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			setSungCount();
			if (mAdapter.getPage() > 0 && curIndex == count-1 && position == mListView.getFirstVisiblePosition())
			{
				mAdapter.setPage(mAdapter.getPage()-1);
				mListView.setSelection(UmSungAdapter.PAGE_NUM-1);
			}
			else
			{
				mAdapter.notifyDataSetChanged();
			}
			SungInfoTable.deleteSungVideoByRowID(video.getRowID());
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
		
		if(mListView.isFocused())
		{
			if (mListView.getSelectedItemPosition() == 0)
			{
				if (mAdapter.getPage() == 0)
				{
					if (mAdapter.getFocusPosition() == 2)
					{
						mDelDuplicate.requestFocus();
						return true;
					}
				}
				
				nRet = mAdapter.setPage(mAdapter.getPage() - 1);
				if(nRet)
				{
					mListView.setSelection(UmSungAdapter.PAGE_NUM - 1);
				}
			}
		}
		return nRet;
	}
	
	private boolean doKeyDown() 
	{
		boolean nRet = false;
		
		if(mClear.isFocused())
		{
			if (mVideoList.size() > 0)
			{
				mNextFocus = 0;
			}
			else
			{
				return true;
			}
		}
		else if(mDelDuplicate.isFocused())
		{
			if (mVideoList.size() > 0)
			{
				mNextFocus = 2;
			}
			else
			{
				return true;
			}
		}
		else if(mListView.isFocused())
		{
			if (mAdapter.getPage()*UmSungAdapter.PAGE_NUM + mListView.getSelectedItemPosition() >= mVideoList.size()-1)
			{
				return true;
			}
			
			if (mListView.getLastVisiblePosition() == mListView.getSelectedItemPosition())
			{
				nRet = mAdapter.setPage(mAdapter.getPage() + 1);
				if(nRet)
				{
					mListView.setSelection(mListView.getFirstVisiblePosition());
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
				nRet = mAdapter.setPage(mAdapter.getPage() - 1);
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
			int totalPage = (mVideoList.size()-1)/UmSungAdapter.PAGE_NUM + 1;
			if (mAdapter.getPage()+1 < totalPage)
			{
				nRet = mAdapter.setPage(mAdapter.getPage() + 1);
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
	
	private boolean doKeyLeft() 
	{
		boolean nRet = false;
		
		if (mListView.isFocused())
		{
			if(mAdapter.getFocusPosition() == 1)
			{
				mAdapter.setFocusPosition(0);
				nRet = true;
			}
			else if(mAdapter.getFocusPosition() == 2)
			{
				mAdapter.setFocusPosition(1);
				nRet = true;
			}
		}
		else if (mDelDuplicate.isFocused())
		{
			mClear.requestFocus();
			nRet = true;
		}
		return nRet;
	}
	
	private boolean doKeyRight() 
	{
		boolean nRet = false;
		
		if (mListView.isFocused())
		{
			if(mAdapter.getFocusPosition() == 0)
			{
				mAdapter.setFocusPosition(1);
				nRet = true;
			}
			else if(mAdapter.getFocusPosition() == 1)
			{
				mAdapter.setFocusPosition(2);
				nRet = true;
			}
		}
		return nRet;
	}
	
	public void initUmSungFragment()
	{
		if (mVideoList.size() > 0)
		{
			mListView.requestFocus();
			mListView.setSelection(0);
			mAdapter.setFocusPosition(0);
		}
		else
		{
			mClear.requestFocus();
		}
	}
	
	public void resetUmSungFragment()
	{
		Logcat.i(FlagConstant.TAG, " ++++++ 11111111 resetUmSungFragment  +++++++ ");
		mVideoList.clear();
		mVideoList = SungInfoTable.querySungVideos();
		
		mAdapter = new UmSungAdapter(getActivity(), mVideoList, mListView);
		mListView.setAdapter(mAdapter);
		mListView.startLayoutAnimation();
		setSungCount();
	}
	
	private FreshSung mFreshSung = new FreshSung();
	private class FreshSung extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if (intent.getAction().equals(Constant.ACTION_FRESH_SUNG)) 
			{
				Logcat.i(FlagConstant.TAG, " +++++ mFreshSung ++++++ ");
				resetUmSungFragment();
				mAdapter.setPage(mTempPage);
				mListView.setSelection(mTempIndex);
			}
		}
	}
	
	private void registerIntentReceivers() 
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_FRESH_SUNG);
		getActivity().registerReceiver(mFreshSung, filter);
	}
	
	private void unregisterIntentReceivers() 
	{
		if (mFreshSung != null) 
		{
			getActivity().unregisterReceiver(mFreshSung);
			//mFreshMenuSelected = null;
		}
	}
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		unregisterIntentReceivers();
	}
}
