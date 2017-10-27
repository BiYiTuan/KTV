/**
 * @Title: UserCenterCollectFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: 收藏
 * @author: zhaoqy
 * @date: 2015-7-31 下午3:10:57
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.fragment;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
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
import com.sz.ead.app.ktv.db.CollectInfoTable;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.ui.activity.PlayerActivity;
import com.sz.ead.app.ktv.ui.adapter.UCCollectAdapter;
import com.sz.ead.app.ktv.ui.adapter.UmSelectedAdapter;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class UCCollectFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener, OnItemClickListener
{
	private UCCollectAdapter mAdapter;
	private ArrayList<Video> mVideoList;
	private TextView         mCount;
	private Button           mAdd;
	private Button           mClear;
	private ListView         mListView;
	private int              mNextFocus; //根据列表的上一个焦点位置来决定的列表item中的图标选中位置

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mVideoList = CollectInfoTable.queryCollectVideos();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_usercenter_collect, container, false);
		findViews(view);
		setAdapters();
		setCollectCount();
		setListeners();
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
		mCount = (TextView) view.findViewById(R.id.id_collect_count);
		mAdd = (Button) view.findViewById(R.id.id_collect_add);
		mClear = (Button) view.findViewById(R.id.id_collect_clear);
		mListView = (ListView) view.findViewById(R.id.id_collect_listview);
	}
	
	private void setAdapters()
	{
		mAdapter = new UCCollectAdapter(getActivity(), mVideoList, mListView);
		mListView.setAdapter(mAdapter);
	}

	private void setListeners() 
	{
		mAdd.setOnClickListener(this);
		mClear.setOnClickListener(this);
		mListView.setOnFocusChangeListener(this);
		mListView.setOnItemClickListener(this);
	}
	
	private void setCollectCount()
	{
		mCount.setText(String.format(getString(R.string.usercenter_collect_count), mVideoList.size()));
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.id_collect_add:
		{
			if (mVideoList.size() > 0)
			{
				if (isVideoListValid())
				{
					SelectedInfoTable.insertSelectedVideoList(mVideoList);
					ToastUtils.getShowToast().showAnimationToast(getActivity(), getString(R.string.toast_add_all), Toast.LENGTH_LONG);
				}
				else
				{
					ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_add_errro), Toast.LENGTH_LONG);
				}
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getString(R.string.toast_add_zero), Toast.LENGTH_LONG);
			}
			break;
		}
		case R.id.id_collect_clear:
		{
			if (mVideoList.size() > 0)
			{
				mVideoList.clear();
				mAdapter.notifyDataSetChanged();
				setCollectCount();
				CollectInfoTable.deleteCollects();
			}
			break;
		}
		default:
			break;
		}
	}
	
	private boolean isVideoListValid()
	{
		for (int i=0; i<mVideoList.size(); i++)
		{
			if (mVideoList.get(i).getResCode().isEmpty() || 
				mVideoList.get(i).getName().isEmpty() || 
				mVideoList.get(i).getUrl().isEmpty())
			{
				Logcat.i(FlagConstant.TAG, " ++++++ index: " + i);
				return false;
			}
		}
		return true;
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
			int curIndex = position + mAdapter.getPage() * UCCollectAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Intent intent = new Intent(getActivity(), PlayerActivity.class);
			startActivity(intent);
			break;
		}
		case 1: //添加
		{
			int curIndex = position + mAdapter.getPage() * UCCollectAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(getActivity(), getActivity().getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Spanned text = Html.fromHtml(String.format(getString(R.string.tip_select_song),  mVideoList.get(curIndex).getName()));
			ToastUtils.getShowToast().showAnimationAddTipToast(getActivity(), text, Toast.LENGTH_LONG);
			break;
		}	
		case 2: //删除
		{
			int curIndex = position + mAdapter.getPage() * UCCollectAdapter.PAGE_NUM;
			int count = mVideoList.size();
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			setCollectCount();
			if (mAdapter.getPage() > 0 && curIndex == count-1 && position == mListView.getFirstVisiblePosition())
			{
				mAdapter.setPage(mAdapter.getPage()-1);
				mListView.setSelection(UCCollectAdapter.PAGE_NUM-1);
			}
			else
			{
				mAdapter.notifyDataSetChanged();
			}
			CollectInfoTable.deleteCollectsVideo(video.getResCode());
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
						mClear.requestFocus();
						return true;
					}
				}
				
				nRet = mAdapter.setPage(mAdapter.getPage()-1);
				if(nRet)
				{
					mListView.setSelection(UCCollectAdapter.PAGE_NUM-1);
				}
			}
		}
		return nRet;
	}
	
	private boolean doKeyDown() 
	{
		boolean nRet = false;
		
		if(mAdd.isFocused())
		{
			if (mVideoList.size() > 0)
			{
				//清空按钮向下对应的是第一个图标
				mNextFocus = 0;
			}
			else
			{
				return true;
			}
		}
		else if(mClear.isFocused())
		{
			if (mVideoList.size() > 0)
			{
				//清空按钮向下对应的是第三个图标
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
				nRet = mAdapter.setPage(mAdapter.getPage()+1);
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
				nRet = mAdapter.setPage(mAdapter.getPage()+1);
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
		else if (mClear.isFocused())
		{
			mAdd.requestFocus();
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
	
	public void initUCCollectFragment()
	{
		if (mVideoList.size() > 0)
		{
			mListView.requestFocus();
			mListView.setSelection(0);
			mAdapter.setFocusPosition(0);
		}
		else
		{
			mAdd.requestFocus();
		}
	}
}
