/**
 * @Title: DictionaryItem.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.entity
 * @Description: 应用词典item
 * @author: zhaoqy
 * @date: 2015-6-8 下午5:33:37
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

import java.util.ArrayList;

public class DictionaryItem 
{
	private int mStatus;  
	private ArrayList<DictionaryValueItem> mContentList = new ArrayList<DictionaryValueItem>();
	
	public void setStatus(int status)
	{
		mStatus = status;
	}
	
	public int getStatus()
	{
		return mStatus;
	}
	
	public ArrayList<DictionaryValueItem> getContentList() 
	{
		return mContentList;
	}

	public void setContentList(ArrayList<DictionaryValueItem> contentList) 
	{
		mContentList = contentList;
	}
}
