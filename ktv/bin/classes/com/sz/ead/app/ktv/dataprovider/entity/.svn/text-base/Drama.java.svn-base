/**
 * @Title: Drama.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.entity
 * @Description: 剧集item
 * @author: zhaoqy
 * @date: 2015-7-30 下午5:55:31
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Drama implements Parcelable 
{
	private String mDramaName;   //集数名
	private String mDramaCode;   //集数编码
	private String mNumber;      //剧集序号
	private String mSize;        //剧集大小
	private String mTime;        //时长
	private String mRate;        //码率
	private String mFormat;      //格式
	private String mUrl;         //播放地址
	private String mScreenshots; //剧集截图
	
	public Drama() 
	{
	}

	public Drama(Drama item) 
	{
		mDramaName = item.mDramaName;
		mDramaCode = item.mDramaCode;
		mNumber = item.mNumber;
		mSize = item.mSize;
		mTime = item.mTime;
		mRate = item.mRate;
		mFormat = item.mFormat;
		mUrl = item.mUrl;
		mScreenshots = item.mScreenshots;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeString(mDramaName);
		out.writeString(mDramaCode);
		out.writeString(mNumber);
		out.writeString(mSize);
		out.writeString(mTime);
		out.writeString(mRate);
		out.writeString(mFormat);
		out.writeString(mUrl);
		out.writeString(mScreenshots);
	}
	public static final Parcelable.Creator<Drama> CREATOR = new Parcelable.Creator<Drama>() 
	{
		public Drama createFromParcel(Parcel in) 
		{
			return new Drama(in);
		}
		public Drama[] newArray(int size) 
		{
			return new Drama[size];
		}
	};
	
	private Drama(Parcel in) 
	{
		mDramaName = in.readString();
		mDramaCode = in.readString();
		mNumber = in.readString();
		mSize = in.readString();
		mTime = in.readString();
		mRate = in.readString();
		mFormat = in.readString();
		mUrl = in.readString();
		mScreenshots = in.readString();
	}
	
	public String getDramaName() 
	{
		return mDramaName;
	}

	public void setDramaName(String dramaName) 
	{
		//Logcat.i(FlagConstant.TAG, " ++++++ dramaName " + dramaName);
		mDramaName = dramaName;
	}

	public String getDramaCode() 
	{
		return mDramaCode;
	}

	public void setDramaCode(String dramaCode) 
	{
		//Logcat.i(FlagConstant.TAG, " ++++++ dramaCode " + dramaCode);
		mDramaCode = dramaCode;
	}

	public String getNumber() 
	{
		return mNumber;
	}

	public void setNumber(String number) 
	{
		mNumber = number;
	}

	public String getSize() 
	{
		return mSize;
	}
	
	public void setSize(String size) 
	{
		mSize = size;
	}

	public String getTime() 
	{
		return mTime;
	}

	public void setTime(String time) 
	{
		mTime = time;
	}

	public String getRate() 
	{
		return mRate;
	}

	public void setRate(String rate) 
	{
		mRate = rate;
	}

	public String getFormat() 
	{
		return mFormat;
	}

	public void setFormat(String format) 
	{
		mFormat = format;
	}

	public String getUrl() 
	{
		return mUrl;
	}

	public void setUrl(String url) 
	{
		//Logcat.i(FlagConstant.TAG, " ++++++ url " + url);
		mUrl = url;
	}

	public String getScreenshots() 
	{
		return mScreenshots;
	}

	public void setScreenshots(String screenshots) 
	{
		mScreenshots = screenshots;
	}
}
