/**
 * @Title: DialogMenuSong.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu.select.song
 * @Description: 分类下歌曲Dialog
 * @author: zhaoqy
 * @date: 2015-8-8 下午1:36:32
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu.select.song;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.sz.ead.app.ktv.ui.adapter.MenuSongAdapter;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class DialogMenuSong extends Dialog implements UICallBack, OnItemClickListener
{
	private MenuSongAdapter  mAdapter;
	private ArrayList<Video> mVideoList;
	private Context   mContext;
	private TextView  mName;
	private TextView  mCountText;
	private TextView  mNoData;
	private ListView  mListView;
	private ImageView mWaitting;
	private Column    mColumn;
	private int       mTime = 1;
	private int       mTotPage = 0;     
	private int       mLoadPage = 0; 
	private int       mListId = 0;   //点播列表耗时启动id
	
	public DialogMenuSong(Context context, int theme, Column column) 
	{
		super(context, theme);
		mContext = context;
		mColumn = column;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.END|Gravity.TOP);
        dialogWindow.setAttributes(lp);
        
        setContentView(R.layout.dialog_menu_song);
        findViews();
        initData();
        setAdapter();
        setListeners();
        requestColumnSub();
	}

	private void findViews() 
	{
		mName = (TextView) findViewById(R.id.id_dialog_menu_song_name);
		mCountText = (TextView) findViewById(R.id.id_dialog_menu_song_count);
		mNoData = (TextView) findViewById(R.id.id_dialog_menu_song_no);
		mListView = (ListView) findViewById(R.id.id_dialog_menu_song_listview);
		mWaitting = (ImageView) findViewById(R.id.id_dialog_menu_song_waitting);
	}

	private void initData() 
	{
		mName.setText(mColumn.getName());
		mVideoList = new ArrayList<Video>();
		mVideoList.clear();
	}

	private void setAdapter() 
	{
		mAdapter = new MenuSongAdapter(mContext, mVideoList, mListView);
		mListView.setAdapter(mAdapter);
	}

	private void setListeners() 
	{
		mListView.setOnItemClickListener(this);
	}
	
	private void requestColumnSub()
	{
		mListId = BI.getStartTimeId();
		mWaitting.setVisibility(View.VISIBLE);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMNSUB, MenuSongAdapter.PAGE_NUM * 2, mTime, mColumn.getColumnCode());
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
			int curIndex = position + mAdapter.getPage() * MenuSongAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			sendCutSongBroadCast();
			break;
		}
		case 1:
		{
			int curIndex = position + mAdapter.getPage() * MenuSongAdapter.PAGE_NUM;
			if (mVideoList.get(curIndex).getResCode().isEmpty() || 
				mVideoList.get(curIndex).getName().isEmpty() || 
				mVideoList.get(curIndex).getUrl().isEmpty())
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.toast_invalid), Toast.LENGTH_LONG);
				return;
			}
			SelectedInfoTable.insertSelectedVideo(mVideoList.get(curIndex));
			Spanned text = Html.fromHtml(String.format(mContext.getString(R.string.tip_select_song), mVideoList.get(curIndex).getName()));
			ToastUtils.getShowToast().showAnimationAddTipToast(mContext, text, Toast.LENGTH_LONG);
			sendFreshSelectedBroadCast();
			break;
		}
		case 2:
		{
			int curIndex = position + mAdapter.getPage() * MenuSongAdapter.PAGE_NUM;
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
	
	private void sendCutSongBroadCast()
	{
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_CUT_SONG);
		mContext.sendBroadcast(intent);
	}
	
	private void sendFreshSelectedBroadCast()
	{
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_FRESH_SELECTED);
		mContext.sendBroadcast(intent);
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
					mCountText.setText(String.format(getContext().getString(R.string.menu_song_count), count));
					mTotPage = (count-1)/MenuSongAdapter.PAGE_NUM + 1;
					mLoadPage = (mVideoList.size()-1)/MenuSongAdapter.PAGE_NUM + 1;
				}
				else
				{
					mLoadPage = (mVideoList.size()-1)/MenuSongAdapter.PAGE_NUM + 1;
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
			Logcat.e(FlagConstant.TAG, " DialogMenuSong onSuccessful error + " + e.toString());
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		if (mTime == 1)
		{
			mWaitting.setVisibility(View.GONE);
			mNoData.setVisibility(View.VISIBLE);
			mCountText.setText(String.format(getContext().getString(R.string.menu_song_count), 0));
		}
		//点播列表耗时BI
		BiMsg.sendDemandListTimeBiMsg("", "2", mListId);
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
				mListView.setSelection(MenuSongAdapter.PAGE_NUM-1);
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
		
		if(mListView.isFocused())
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
		
		if(mListView.isFocused())
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
