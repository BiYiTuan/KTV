/**
 * @Title: MenuHotWordAdapter.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.adapter
 * @Description: 菜单热词adapter
 * @author: zhaoqy
 * @date: 2015-8-10 下午2:56:33
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.dataprovider.entity.Hotword;

public class MenuHotWordAdapter extends BaseAdapter 
{
	private LayoutInflater     mInflater;
	private ArrayList<Hotword> mHotList;
	private Context            mContext;

	public MenuHotWordAdapter(Context context, ArrayList<Hotword> list) 
	{
		mHotList = list;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		return mHotList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return mHotList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_menu_search_hot, parent, false);
			mHolder = new ViewHolder();
			mHolder.mName = (TextView) convertView.findViewById(R.id.tv_menu_search_hot_name);
			convertView.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.mName.setText(mHotList.get(position).getName());
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView mName;
	}
}
