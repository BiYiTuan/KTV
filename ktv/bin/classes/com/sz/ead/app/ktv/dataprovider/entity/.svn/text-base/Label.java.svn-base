/**
 * @Title: Label.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.entity
 * @Description: 标签
 * @author: zhaoqy
 * @date: 2015-7-31 上午9:51:09
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Label implements Parcelable
{
	private String mLabelName; //标签名称
	private String mValue;     //标签值
	
	public String getLabelName() 
	{
		return mLabelName;
	}
	
	public void setLabelName(String labelname) 
	{
		mLabelName = labelname;
	}
	
	public String getValue() 
	{
		return mValue;
	}
	
	public void setValue(String value) 
	{
		mValue = value;
	}
	
	public Label() 
	{
	}

	public Label(Label item) 
	{
		mLabelName = item.mLabelName;
		mValue = item.mValue;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeString(mLabelName);
		out.writeString(mValue);
	}

	public static final Parcelable.Creator<Label> CREATOR = new Parcelable.Creator<Label>() 
	{
		public Label createFromParcel(Parcel in) 
		{
			return new Label(in);
		}

		public Label[] newArray(int size) 
		{
			return new Label[size];
		}
	};

	private Label(Parcel in) 
	{
		mLabelName = in.readString();
		mValue = in.readString();
	}
}