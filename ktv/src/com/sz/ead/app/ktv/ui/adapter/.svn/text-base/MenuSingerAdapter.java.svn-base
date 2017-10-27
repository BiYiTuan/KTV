/**
 * @Title: MenuSingerAdapter.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.adapter
 * @Description: 菜单歌手Adapter
 * @author: zhaoqy
 * @date: 2015-8-25 下午2:21:12
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Column;

public class MenuSingerAdapter extends BaseAdapter 
{
	public static final int   PAGE_NUM = 8;
	private LayoutInflater    mInflater;
	private ArrayList<Column> mSingerList;
	private ListView          mListView;
	private Context           mContext;
	private int               mPage;
	
	public int getPage() 
	{
		return mPage;
	}

	public boolean setPage(int page) 
	{
		boolean isChanged = false;;
		int size = mSingerList.size();
		if(size > PAGE_NUM * page && page >= 0)
		{
			mPage = page;
			notifyDataSetChanged();
			isChanged = true;
		}
		return isChanged;
	}

	public MenuSingerAdapter(Context context, ArrayList<Column> list, ListView lv) 
	{
		mSingerList = list;
		mContext = context;
		mListView = lv;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		int size = mSingerList.size();
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
		return mSingerList.get(position + PAGE_NUM * mPage);
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
			convertView = mInflater.inflate(R.layout.item_menu_singer, parent, false);
			mHolder = new ViewHolder();
			mHolder.mName = (TextView) convertView.findViewById(R.id.tv_menu_singer_name);
			convertView.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		int curIndex = position + PAGE_NUM * mPage;
		mHolder.mName.setText(mSingerList.get(curIndex).getName());
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView mName;
	}
	
	@Override
	public void notifyDataSetChanged() 
	{
		super.notifyDataSetChanged();
		mListView.startLayoutAnimation();
	}
}
