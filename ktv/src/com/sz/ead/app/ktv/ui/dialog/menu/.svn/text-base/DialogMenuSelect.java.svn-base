/**
 * @Title: DialogMenuSelect.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.dialog.menu
 * @Description: 点歌Dialog
 * @author: zhaoqy
 * @date: 2015-8-3 下午8:55:44
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.dialog.menu;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.http.NetUtil;
import com.sz.ead.app.ktv.db.ColumnInfoTable;
import com.sz.ead.app.ktv.ui.dialog.menu.select.DialogMenuSearch;
import com.sz.ead.app.ktv.ui.dialog.menu.select.DialogMenuSelectCategory;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.ToastUtils;

public class DialogMenuSelect extends Dialog implements android.view.View.OnClickListener
{
	private ArrayList<Column> mColumns;
	private Context mContext; 
	private Button  mPinyin;
	private Button  mRecommend;
	private Button  mCategory;
	private Button  mSinger;
	private Button  mLang;
	
	
	public DialogMenuSelect(Context context, int theme)
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
        
        setContentView(R.layout.dialog_menu_select);
        findViews();
        setListeners();
        getColumns();
	}

	private void findViews() 
	{
		mPinyin = (Button) findViewById(R.id.id_menu_select_pinyin);
		mRecommend = (Button) findViewById(R.id.id_menu_select_recommend);
		mCategory = (Button) findViewById(R.id.id_menu_select_category);
		mSinger = (Button) findViewById(R.id.id_menu_select_singer);
		mLang = (Button) findViewById(R.id.id_menu_select_lang);
	}
	
	private void setListeners()
	{
		mPinyin.setOnClickListener(this);
		mRecommend.setOnClickListener(this);
		mCategory.setOnClickListener(this);
		mSinger.setOnClickListener(this);
		mLang.setOnClickListener(this);
	}
	
	private void getColumns() 
	{
		mColumns = ColumnInfoTable.queryColumn(ColumnInfoTable.COLUMNTABLE_COLUMN);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.id_menu_select_pinyin:
		{
			//拼音点歌
			dismiss();
			DialogMenuSearch diaMenuSelectSearch = new DialogMenuSearch(mContext, R.style.menu_right_dialog);
			diaMenuSelectSearch.show();
			diaMenuSelectSearch.setOnCancelListener(new OnCancelListener() 
			{
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					show();
				}
			});
			break;
		}
		case R.id.id_menu_select_recommend:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			//热门推荐
			Logcat.i(FlagConstant.TAG, " ++++++ mColumns.size: " + mColumns.size());
			if (mColumns.size() > 5 /*&& mColumns.get(5).getName().equals("热门推荐")*/)
			{
				dismiss();
				DialogMenuSelectCategory dialog = new DialogMenuSelectCategory(mContext, R.style.menu_right_dialog, mColumns.get(5));
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
		case R.id.id_menu_select_category:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			//分类点歌
			Logcat.i(FlagConstant.TAG, " ++++++ mColumns.size: " + mColumns.size());
			if (mColumns.size() > 2 /*&& mColumns.get(2).getName().equals("分类点歌")*/)
			{
				dismiss();
				DialogMenuSelectCategory dialog = new DialogMenuSelectCategory(mContext, R.style.menu_right_dialog, mColumns.get(2));
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
		case R.id.id_menu_select_singer:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			//歌星点歌
			Logcat.i(FlagConstant.TAG, " ++++++ mColumns.size: " + mColumns.size());
			if (mColumns.size() > 3 /*&& mColumns.get(3).getName().equals("歌星点歌")*/)
			{
				dismiss();
				DialogMenuSelectCategory dialog = new DialogMenuSelectCategory(mContext, R.style.menu_right_dialog, mColumns.get(3));
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
		case R.id.id_menu_select_lang:
		{
			if(!NetUtil.isNetConnected(mContext))
			{
				ToastUtils.getShowToast().showAnimationToast(mContext, mContext.getString(R.string.network_anomaly), Toast.LENGTH_LONG);
				return;
			}
			
			//语种点歌
			Logcat.i(FlagConstant.TAG, " ++++++ mColumns.size: " + mColumns.size());
			if (mColumns.size() > 4 /*&& mColumns.get(4).getName().equals("语种点歌")*/)
			{
				dismiss();
				DialogMenuSelectCategory dialog = new DialogMenuSelectCategory(mContext, R.style.menu_right_dialog, mColumns.get(4));
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
}
