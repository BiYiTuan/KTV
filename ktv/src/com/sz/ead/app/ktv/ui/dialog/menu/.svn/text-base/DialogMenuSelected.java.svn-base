/**
 * @Title: DialogMenuSelected.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu
 * @Description: 待唱歌曲Dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午9:20:48
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.db.SelectedInfoTable;
import com.sz.ead.app.ktv.ui.adapter.MenuSelectedAdapter;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class DialogMenuSelected extends Dialog implements OnFocusChangeListener, OnItemClickListener
{
	private Context             mContext;
	private MenuSelectedAdapter mAdapter;
	private ArrayList<Video>    mVideoList;
	private ListView            mListView;
	private TextView            mCount;
	private TextView            mNoSelected;
	private int                 mNextFocus; 
	private boolean             mToping = false;
	
	public DialogMenuSelected(Context context, int theme)
	{
		super(context, theme);
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.END|Gravity.TOP);
        dialogWindow.setAttributes(lp);
        
        setContentView(R.layout.dialog_menu_selected);
        registerIntentReceivers();
        findViews();
		initFakeData();
	}
	
	private void findViews() 
	{
		mListView = (ListView) findViewById(R.id.id_menu_selected_listview);
		mCount = (TextView) findViewById(R.id.id_menu_selected_count);
		mNoSelected = (TextView) findViewById(R.id.id_menu_selected_no);
	}
	
	private void initFakeData()
	{
		mVideoList = SelectedInfoTable.querySelectedVideos();
		
		if (mVideoList.size() > 0)
		{
			setAdapters();
			setListeners();
		}
		setSelectedCount();
	}
	
	private void setAdapters()
	{
		mAdapter = new MenuSelectedAdapter(getContext(), mVideoList, mListView);
		mListView.setAdapter(mAdapter);
		mListView.startLayoutAnimation();
	}

	private void setListeners() 
	{
		mListView.setOnFocusChangeListener(this);
		mListView.setOnItemClickListener(this);
	}
	
	private void setSelectedCount()
	{
		if (mVideoList.size() > 0)
		{
			mCount.setText(String.format(getContext().getString(R.string.menu_selected_title), mVideoList.size()));
			formatTextView(mCount, '(');
		}
		else
		{
			//无待唱歌曲
			mNoSelected.setVisibility(View.VISIBLE);
			mCount.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (mAdapter.getFocusPosition()) 
		{
		case 0: //置顶
		{
			if (mToping)
			{
				return;
			}
			int curIndex = position + mAdapter.getPage() * MenuSelectedAdapter.PAGE_NUM;
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
				sendFreshSelectedBroadCast();
			}
			else
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_top), Toast.LENGTH_SHORT);
			}
			break;
		}
		case 1: //删除
		{
			int curIndex = position + mAdapter.getPage() * MenuSelectedAdapter.PAGE_NUM;
			int count = mVideoList.size();
			Video video = mVideoList.get(curIndex);
			mVideoList.remove(curIndex);
			setSelectedCount();
			if (mAdapter.getPage() > 0 && curIndex == count-1 && position == mListView.getFirstVisiblePosition())
			{
				mAdapter.setPage(mAdapter.getPage()-1);
				mListView.setSelection(MenuSelectedAdapter.PAGE_NUM-1);
			}
			else
			{
				mAdapter.notifyDataSetChanged();
			}
			SelectedInfoTable.deleteSelectedVideoByRowID(video.getRowID());
			sendFreshSelectedBroadCast();
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
		else
		{
			mAdapter.setFocusPosition(0);
		}
	}
	
	private void sendFreshSelectedBroadCast()
	{
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_FRESH_SELECTED);
		mContext.sendBroadcast(intent);
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
				mListView.setSelection(MenuSelectedAdapter.PAGE_NUM-1);
			}
		}
		return nRet;
	}

	private boolean doKeyDown()
	{
		boolean nRet = false;
		
		if(mListView.isFocused() && mListView.getLastVisiblePosition() == mListView.getSelectedItemPosition())
		{
			nRet = mAdapter.setPage(mAdapter.getPage()+1);
			if(nRet)
			{
				mListView.setSelection(mListView.getFirstVisiblePosition());
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
			int totalPage = (mVideoList.size()-1)/MenuSelectedAdapter.PAGE_NUM + 1;
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
		
		if(mListView != null && mListView.isFocused())
		{
			if(mAdapter.getFocusPosition() == 1)
			{
				mAdapter.setFocusPosition(0);
				nRet = true;
			}
		}
		return nRet;
	}
	
	private boolean doKeyRight()
	{
		boolean nRet = false;
		
		if(mListView != null && mListView.isFocused())
		{
			if(mAdapter.getFocusPosition() == 0)
			{
				mAdapter.setFocusPosition(1);
				nRet = true;
			}
		}
		return nRet;
	}
	
	private FreshMenuSelected mFreshMenuSelected = new FreshMenuSelected();
	private class FreshMenuSelected extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if (intent.getAction().equals(Constant.ACTION_FRESH_MENU_SELECTED)) 
			{
				Logcat.i(FlagConstant.TAG, " +++++ FreshMenuSelected ++++++ ");
				initFakeData();
			}
		}
	}
	
	private void registerIntentReceivers() 
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_FRESH_MENU_SELECTED);
		mContext.registerReceiver(mFreshMenuSelected, filter);
	}
	
	private void unregisterIntentReceivers() 
	{
		if (mFreshMenuSelected != null) 
		{
			mContext.unregisterReceiver(mFreshMenuSelected);
			//mFreshMenuSelected = null;
		}
	}
	
	public void formatTextView(TextView tv, char c)
	{
		String str = tv.getText().toString().trim();
		int index = str.indexOf(c);
		int len = str.length();
		SpannableStringBuilder builder = new SpannableStringBuilder(str);	  
		//ForegroundColorSpan 分界前的文字颜色，BackgroundColorSpan分界后的文字颜色
		ForegroundColorSpan rightSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.umlist_count_text));  
		ForegroundColorSpan leftSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.umlist_normal_text));   
		builder.setSpan(leftSpan, 0, index, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);  
		builder.setSpan(rightSpan, index, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		tv.setText(builder);
	}
	
	@Override
	public void onDetachedFromWindow() 
	{
		super.onDetachedFromWindow();
		unregisterIntentReceivers();
	}
}
