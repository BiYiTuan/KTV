/**
 * @Title: DialogMenuSelectRecommend.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu.select
 * @Description: 分类dialog
 * @author: zhaoqy
 * @date: 2015-8-8 上午11:30:11
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu.select;

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
import com.sz.ead.app.ktv.ui.adapter.MenuCategoryAdapter;
import com.sz.ead.app.ktv.ui.dialog.menu.select.song.DialogMenuSinger;
import com.sz.ead.app.ktv.ui.dialog.menu.select.song.DialogMenuSong;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;
import com.sz.ead.app.ktv.utils.Token;

public class DialogMenuSelectCategory extends Dialog implements UICallBack, OnItemClickListener
{
	private MenuCategoryAdapter mAdapter;
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
	
	public DialogMenuSelectCategory(Context context, int theme, Column column)
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
        
        setContentView(R.layout.dialog_menu_select_category);
        findViews();
        initData();
        setAdapter();
        setListeners();
        requestColumn();
	}

	private void findViews() 
	{
		mName = (TextView) findViewById(R.id.id_dialog_menu_select_category_name);
		mCountText = (TextView) findViewById(R.id.id_dialog_menu_select_category_count);
		mNoData = (TextView) findViewById(R.id.id_dialog_menu_select_category_no);
		mListView = (ListView) findViewById(R.id.id_dialog_menu_select_category_listview);
		mWaitting = (ImageView) findViewById(R.id.id_dialog_menu_select_category_waitting);
	}
	
	private void initData() 
	{
		mName.setText(mColumn.getName());
		mColumnList = new ArrayList<Column>();
		mColumnList.clear();
	}
	
	private void setAdapter() 
	{
		mAdapter = new MenuCategoryAdapter(getContext(), mColumnList, mListView);
		mListView.setAdapter(mAdapter);
	}
	
	private void setListeners()
	{
		mListView.setOnItemClickListener(this);
	}
	
	private void requestColumn() 
	{
		mWaitting.setVisibility(View.VISIBLE);
		RequestDataManager.requestData(this, mContext, Token.TOKEN_COLUMN, MenuCategoryAdapter.PAGE_NUM * 2, mTime, mColumn.getColumnCode(), "");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch (parent.getId()) 
		{
		case R.id.id_dialog_menu_select_category_listview:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			int curIndex = position + mAdapter.getPage() * MenuCategoryAdapter.PAGE_NUM;
			dismiss();
			
			if (mColumn.getName().equals(/*"歌星点歌"*/mContext.getResources().getString(R.string.menu_select_4)))
			{
				DialogMenuSinger dialog = new DialogMenuSinger(mContext, R.style.menu_right_dialog, mColumnList.get(curIndex));
				dialog.show();
				dialog.setOnCancelListener(new OnCancelListener()
				{
					@Override
					public void onCancel(DialogInterface dialog) 
					{
						show();
					}
				});
			}
			else
			{
				DialogMenuSong dialog = new DialogMenuSong(mContext, R.style.menu_right_dialog, mColumnList.get(curIndex));
				dialog.show();
				dialog.setOnCancelListener(new OnCancelListener()
				{
					@Override
					public void onCancel(DialogInterface dialog) 
					{
						show();
					}
				});
			}
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
					mCountText.setText(String.format(getContext().getString(R.string.menu_select_category_count), count));
					mTotPage = (count-1)/MenuCategoryAdapter.PAGE_NUM + 1;
					mLoadPage = (mColumnList.size()-1)/MenuCategoryAdapter.PAGE_NUM + 1;
				}
				else
				{
					mLoadPage = (mColumnList.size()-1)/MenuCategoryAdapter.PAGE_NUM + 1;
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
			mCountText.setText(String.format(getContext().getString(R.string.menu_select_category_count), 0));
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
				mListView.setSelection(MenuCategoryAdapter.PAGE_NUM-1);
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
