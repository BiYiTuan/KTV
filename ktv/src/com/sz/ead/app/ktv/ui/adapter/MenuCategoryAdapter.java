/**
 * @Title: MenuCategoryAdapter.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.adapter
 * @Description: 菜单分类Adapter
 * @author: zhaoqy
 * @date: 2015-8-8 下午4:41:43
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

public class MenuCategoryAdapter extends BaseAdapter 
{
	public static final int   PAGE_NUM = 8;
	private LayoutInflater    mInflater;
	private ArrayList<Column> mCategoryList;
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
		int size = mCategoryList.size();
		if(size > PAGE_NUM * page && page >= 0)
		{
			mPage = page;
			notifyDataSetChanged();
			isChanged = true;
		}
		return isChanged;
	}

	public MenuCategoryAdapter(Context context, ArrayList<Column> list, ListView lv) 
	{
		mCategoryList = list;
		mContext = context;
		mListView = lv;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		int size = mCategoryList.size();
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
		return mCategoryList.get(position + PAGE_NUM * mPage);
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
			convertView = mInflater.inflate(R.layout.item_menu_select_category, parent, false);
			mHolder = new ViewHolder();
			mHolder.mName = (TextView) convertView.findViewById(R.id.tv_menu_select_category_name);
			convertView.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		int curIndex = position + PAGE_NUM * mPage;
		mHolder.mName.setText(mCategoryList.get(curIndex).getName());
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
