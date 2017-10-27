/**
 * @Title: UmSelectedFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: 已点歌曲
 * @author: zhaoqy
 * @date: 2015-7-31 下午5:23:01
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
import com.sz.ead.app.ktv.ui.activity.PlayerActivity;
import com.sz.ead.app.ktv.ui.adapter.UmSelectedAdapter;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class UmSelectedFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener, OnItemClickListener
{
	private UmSelectedAdapter mAdapter;
	private ArrayList<Video>  mVideoList;
	private TextView          mCount;
	private Button            mClear;
	private Button            mDelDuplicate;
	private ListView          mListView;
	private int               mNextFocus;  
	private boolean           mToping = false;

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mVideoList = SelectedInfoTable.querySelectedVideos();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_umselected, container, false);
		findViews(view);
		setAdapters();
		setSelectedCount();
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

	private void findViews(View view) 
	{
		mCount = (TextView) view.findViewById(R.id.id_umselected_count);
		mClear = (Button) view.findViewById(R.id.id_umselected_clear);
		mDelDuplicate = (Button) view.findViewById(R.id.id_umselected_delDuplicate);
		mListView = (ListView) view.findViewById(R.id.id_umselected_listview);
	}
	
	private void setAdapters()
	{
		mAdapter = new UmSelectedAdapter(getActivity(), mVideoList, mListView);
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
	
	private void setSelectedCount()
	{
		mCount.setText(String.format(getString(R.string.um_count), mVideoList.size()));
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.id_umselected_clear:  
		{
			if (mVideoList.size() > 0)
			{
				mVideoList.clear();
				mAdapter.notifyDataSetChanged();
				setSelectedCount();
				SelectedInfoTable.deleteSelected();
			}
			break;
		}
		case R.id.id_umselected_delDuplicate: 
		{
			SelectedInfoTable.removeDuplicateSelected();
			resetUmSelectedFragment();
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
			int curIndex = position + mAdapter.getPage() * UmSelectedAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			SelectedInfoTable.deleteSelectedVideoByRowID(video.getRowID());
			SelectedInfoTable.insertSelectedVideo(video);
			Intent intent = new Intent(getActivity(), PlayerActivity.class);
			startActivity(intent);
			break;
		}
		case 1: //置顶
		{
			if (mToping)
			{
				return;
			}
			int curIndex = position + mAdapter.getPage() * UmSelectedAdapter.PAGE_NUM;
			if (curIndex != 0)
			{
				mToping = true;
				Video video = mVideoList.get(curIndex);
				int tempPage = mAdapter.getPage();
				int tempIndex = position;
				ArrayList<Video> tempList = new ArrayList<Video>();
				tempList.clear();
				mVideoList.remove(curIndex);
				for (int i=0; i<mVideoList.size(); i++)
				{
					tempList.add(mVideoList.get(i));
				}
				mVideoList.add(0, video);
				mAdapter.notifyDataSetChanged();
				mAdapter.setPage(tempPage);
				mListView.setSelection(tempIndex);
				
				SelectedInfoTable.deleteSelected();
				SelectedInfoTable.insertSelectedVideoForTop(video);
				SelectedInfoTable.insertSelectedVideoListForTop(tempList);
				mToping = false;
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_top), Toast.LENGTH_SHORT);
			}
			break;
		}	
		case 2: //删除
		{
			int curIndex = position + mAdapter.getPage() * UmSelectedAdapter.PAGE_NUM;
			int count = mVideoList.size();
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			setSelectedCount();
			if (mAdapter.getPage() > 0 && curIndex == count-1 && position == mListView.getFirstVisiblePosition())
			{
				mAdapter.setPage(mAdapter.getPage()-1);
				mListView.setSelection(UmSelectedAdapter.PAGE_NUM-1);
			}
			else
			{
				mAdapter.notifyDataSetChanged();
			}
			SelectedInfoTable.deleteSelectedVideoByRowID(video.getRowID());
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
					mListView.setSelection(UmSelectedAdapter.PAGE_NUM - 1);
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
			if (mAdapter.getPage()*UmSelectedAdapter.PAGE_NUM + mListView.getSelectedItemPosition() >= mVideoList.size()-1)
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
			int totalPage = (mVideoList.size()-1)/UmSelectedAdapter.PAGE_NUM + 1;
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
	
	public void initUmSelectedFragment()
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
	
	public void resetUmSelectedFragment()
	{
		mVideoList.clear();
		mVideoList = SelectedInfoTable.querySelectedVideos();
		
		mAdapter = new UmSelectedAdapter(getActivity(), mVideoList, mListView);
	    mListView.setAdapter(mAdapter);
	    mListView.startLayoutAnimation();
		setSelectedCount();
	}
	
	private FreshSelected mFreshSelected = new FreshSelected();
	private class FreshSelected extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if (intent.getAction().equals(Constant.ACTION_FRESH_MENU_SELECTED)) 
			{
				Logcat.i(FlagConstant.TAG, " +++++ mFreshSelected ++++++ ");
				resetUmSelectedFragment();
			}
		}
	}
	
	private void registerIntentReceivers() 
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_FRESH_MENU_SELECTED);
		getActivity().registerReceiver(mFreshSelected, filter);
	}
	
	private void unregisterIntentReceivers() 
	{
		if (mFreshSelected != null) 
		{
			getActivity().unregisterReceiver(mFreshSelected);
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
