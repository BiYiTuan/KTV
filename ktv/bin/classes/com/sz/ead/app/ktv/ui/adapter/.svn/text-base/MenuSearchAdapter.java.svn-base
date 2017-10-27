/**
 * @Title: MenuSearchAdapter.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.adapter
 * @Description: 菜单搜索结果Adapter
 * @author: zhaoqy
 * @date: 2015-8-10 下午2:58:43
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.db.CollectInfoTable;

public class MenuSearchAdapter extends BaseAdapter 
{
	public static final int  PAGE_NUM = 8;
	private LayoutInflater   mInflater;
	private ArrayList<Video> mSearchList;
	private Context          mContext;
	private ListView         mListView;
	private int              mPage;
	private int              mFocusPosition = 0;
	
	public int getPage() 
	{
		return mPage;
	}

	public boolean setPage(int page) 
	{
		boolean isChanged = false;;
		int size = mSearchList.size();
		if(size > PAGE_NUM * page && page >= 0)
		{
			mPage = page;
			notifyDataSetChanged();
			isChanged = true;
		}
		return isChanged;
	}

	public int getFocusPosition() 
	{
		return mFocusPosition;
	}

	public void setFocusPosition(int focusPosition) 
	{
		mFocusPosition = focusPosition;
		super.notifyDataSetChanged();
	}
	public MenuSearchAdapter(Context context, ArrayList<Video> list, ListView lv) 
	{
		mSearchList = list;
		mContext = context;
		mListView = lv;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		int size = mSearchList.size();
		if(size == 0)
		{
			mPage = 0;
			return size;
		}
		else if(size < PAGE_NUM * mPage)
		{
			setPage(mPage - 1);
		}
		else if(size < PAGE_NUM * mPage + PAGE_NUM)
		{
			return size - (PAGE_NUM * mPage);
		}
		return PAGE_NUM;
	}

	@Override
	public Object getItem(int position) 
	{
		return mSearchList.get(position + PAGE_NUM * mPage);
	}


	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder mHolder = null;
		if (convertView == null) 
		{
			convertView = mInflater.inflate(R.layout.item_menu_search_result, parent, false);
			mHolder = new ViewHolder();
			mHolder.mName = (TextView) convertView.findViewById(R.id.tv_menu_search_result_name);
			mHolder.mPlay = (ImageView) convertView.findViewById(R.id.iv_menu_search_result_play);
			mHolder.mAdd = (ImageView) convertView.findViewById(R.id.iv_menu_search_result_add);
			mHolder.mCollect = (ImageView) convertView.findViewById(R.id.iv_menu_search_result_collect);
			convertView.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) convertView.getTag();
		}

		int curIndex = position + PAGE_NUM * mPage;
		mHolder.mName.setText(curIndex + 1 + ":" + mSearchList.get(curIndex).getName());
		
		switch (mFocusPosition)
		{
		case 0:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_selector);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			
			boolean collect = CollectInfoTable.isCollectsVideoExist(mSearchList.get(curIndex).getResCode());
			int resid = collect == true ?  R.drawable.btn_collected_unselected : R.drawable.btn_collect_unselected;
			mHolder.mCollect.setImageResource(resid);
			break;
		}
		case 1:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_selector);
			
			boolean collect = CollectInfoTable.isCollectsVideoExist(mSearchList.get(curIndex).getResCode());
			int resid = collect == true ?  R.drawable.btn_collected_unselected : R.drawable.btn_collect_unselected;
			mHolder.mCollect.setImageResource(resid);
			break;
		}
		case 2:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			
			boolean collect = CollectInfoTable.isCollectsVideoExist(mSearchList.get(curIndex).getResCode());
			int resid = collect == true ?  R.drawable.btn_collected_selector : R.drawable.btn_collect_selector;
			mHolder.mCollect.setImageResource(resid);
			break;
		}
		default:
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			
			boolean collect = CollectInfoTable.isCollectsVideoExist(mSearchList.get(position).getResCode());
			int resid = collect == true ?  R.drawable.btn_collected_unselected : R.drawable.btn_collect_unselected;
			mHolder.mCollect.setImageResource(resid);
			break;
		}
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView  mName;
		ImageView mPlay;
		ImageView mAdd;
		ImageView mCollect;
	}
	
	@Override
	public void notifyDataSetChanged() 
	{
		super.notifyDataSetChanged();
		mListView.startLayoutAnimation();
	}
}
