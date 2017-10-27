/**
 * @Title: DialogMenuSinger.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu.select.song
 * @Description: 菜单歌手dialog
 * @author: zhaoqy
 * @date: 2015-8-25 下午2:27:59
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu.select.song;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.requestdata.RequestDataManager;
import com.sz.ead.app.ktv.ui.adapter.MenuSingerAdapter;
import com.sz.ead.app.ktv.ui.dialog.menu.select.song.DialogMenuSong;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class DialogMenuSinger extends Dialog implements UICallBack, OnItemClickListener
{
	private MenuSingerAdapter mAdapter;
	private ArrayList<Column> mColumnList;
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
	
	public DialogMenuSinger(Context context, int theme, Column column)
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
        
        setContentView(R.layout.dialog_menu_singer);
        findViews();
        initData();
        setAdapter();
        setListeners();
        requestColumn();
	}

	private void findViews() 
	{
		mName = (TextView) findViewById(R.id.id_dialog_menu_singer_name);
		mCountText = (TextView) findViewById(R.id.id_dialog_menu_singer_count);
		mNoData = (TextView) findViewById(R.id.id_dialog_menu_singer_no);
		mListView = (ListView) findViewById(R.id.id_dialog_menu_singer_listview);
		mWaitting = (ImageView) findViewById(R.id.id_dialog_menu_singer_waitting);
	}
	
	private void initData() 
	{
		mName.setText(mColumn.getName());
		mColumnList = new ArrayList<Column>();
		mColumnList.clear();
	}
	
	private void setAdapter() 
	{
		mAdapter = new MenuSingerAdapter(getContext(), mColumnList, mListView);
		mListView.setAdapter(mAdapter);
	}
	
	private void setListeners()
	{
		mListView.setOnItemClickListener(this);
	}
	
	private void requestColumn() 
	{
		mWaitting.setVisibility(View.VISIBLE);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, MenuSingerAdapter.PAGE_NUM * 2, mTime, mColumn.getColumnCode(), "");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (parent.getId()) 
		{
		case R.id.id_dialog_menu_singer_listview:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			int curIndex = position + mAdapter.getPage() * MenuSingerAdapter.PAGE_NUM;
			dismiss();
			
			DialogMenuSong dialogMenuSong = new DialogMenuSong(mContext, R.style.menu_right_dialog, mColumnList.get(curIndex));
			dialogMenuSong.show();
			dialogMenuSong.setOnCancelListener(new OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					show();
				}
			});
			break;
		}
		default:
			break;
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
		try 
		{
			mWaitting.setVisibility(View.GONE);
			ArrayList<Column> temp = new ArrayList<Column>(); 
			temp = (ArrayList<Column>) RequestDataManager.getData(in);
			for (int i=0; i<temp.size(); i++)
			{
				mColumnList.add(temp.get(i));
			}
			
			if (temp.size() > 0)
			{
				if (mTime == 1)
				{
					mAdapter.notifyDataSetChanged();
					mListView.requestFocus();
					mListView.setSelection(0);
					
					int count = RequestDataManager.getTotal(in);
					mCountText.setText(String.format(getContext().getString(R.string.menu_singer_count), count));
					mTotPage = (count-1)/MenuSingerAdapter.PAGE_NUM + 1;
					mLoadPage = (mColumnList.size()-1)/MenuSingerAdapter.PAGE_NUM + 1;
				}
				else
				{
					mLoadPage = (mColumnList.size()-1)/MenuSingerAdapter.PAGE_NUM + 1;
					boolean nRet = mAdapter.setPage(mAdapter.getPage() + 1);
					if(nRet)
					{
						mListView.setSelection(mListView.getFirstVisiblePosition());
					}
				}
			}
			else
			{
				onNetError(-1, "error", null, token);
			}
		}
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " DialogMenuSelectCategory onSuccessful error + " + e.toString());
		}
	}

	@Override
	public void onNetError(int responseCode, String errorDesc, OutPacket out, int token) 
	{
		if (mTime == 1)
		{
			mWaitting.setVisibility(View.GONE);
			mNoData.setVisibility(View.VISIBLE);
			mCountText.setText(String.format(getContext().getString(R.string.menu_singer_count), 0));
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
				mListView.setSelection(MenuSingerAdapter.PAGE_NUM-1);
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
						requestColumn();
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
						requestColumn();
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
}
