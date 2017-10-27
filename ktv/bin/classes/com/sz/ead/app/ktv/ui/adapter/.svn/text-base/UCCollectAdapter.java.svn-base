/**
 * @Title: UserCenterCollectAdapter.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.adapter
 * @Description: 收藏Adapt
 * @author: zhaoqy
 * @date: 2015-7-31 下午4:36:24
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

public class UCCollectAdapter extends BaseAdapter 
{
	public static final int  PAGE_NUM = 8;
	private LayoutInflater   mInflater;
	private ListView         mListView;
	private ArrayList<Video> mCollectList;
	private Context          mContext;
	private int              mPage;
	private int              mFocusPosition = 0;
	
	public int getPage() 
	{
		return mPage;
	}

	public boolean setPage(int page) 
	{
		boolean isChanged = false;;
		int size = mCollectList.size();
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

	public UCCollectAdapter(Context context, ArrayList<Video> list, ListView lv) 
	{
		mCollectList = list;
		mContext = context;
		mListView = lv;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		int size = mCollectList.size();
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
		return mCollectList.get(position + PAGE_NUM * mPage);
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
			convertView = mInflater.inflate(R.layout.item_uc_collect, parent, false);
			mHolder = new ViewHolder();
			mHolder.mName = (TextView) convertView.findViewById(R.id.id_collect_item_name);
			mHolder.mSinger = (TextView) convertView.findViewById(R.id.id_collect_item_singer);
			mHolder.mPlay = (ImageView) convertView.findViewById(R.id.id_collect_item_play);
			mHolder.mAdd = (ImageView) convertView.findViewById(R.id.id_collect_item_add);
			mHolder.mDel = (ImageView) convertView.findViewById(R.id.id_collect_item_del);
			convertView.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		int curIndex = position + PAGE_NUM * mPage;
		mHolder.mName.setText(curIndex + 1 + ":" + mCollectList.get(curIndex).getName());
		mHolder.mSinger.setText(mCollectList.get(curIndex).getActor());
		
		switch (mFocusPosition) 
		{
		case 0:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_selector);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			mHolder.mDel.setImageResource(R.drawable.btn_del_unselected);
			break;
		}
		case 1:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_selector);
			mHolder.mDel.setImageResource(R.drawable.btn_del_unselected);
			break;
		}
		case 2:
		{
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			mHolder.mDel.setImageResource(R.drawable.btn_del_selector);
			break;
		}
		default:
			mHolder.mPlay.setImageResource(R.drawable.btn_play_unselected);
			mHolder.mAdd.setImageResource(R.drawable.btn_add_unselected);
			mHolder.mDel.setImageResource(R.drawable.btn_del_unselected);
			break;
		}
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView  mName;
		TextView  mSinger;
		ImageView mPlay;
		ImageView mAdd;
		ImageView mDel;
	}
	
	@Override
	public void notifyDataSetChanged() 
	{
		super.notifyDataSetChanged();
		mListView.startLayoutAnimation();
	}
}
