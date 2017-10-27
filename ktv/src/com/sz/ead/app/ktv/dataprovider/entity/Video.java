/**
 * @Title: Video.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.entity
 * @Description: 资源item
 * @author: zhaoqy
 * @date: 2015-7-30 下午5:47:27
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable 
{
	private String mRowID;    //在数据库中的id
	private String mResCode;  //点播资源编码
	private String mName;     //点播资源名称
	private String mType;     //资源类型0：直播，1：点播
	private String mSource;   //频道来源
	private String mUrl;      //播放地址
	private String mImage;    //频道logo/资源小图
	private String mBigpic;   //大图地址
	private String mDirector; //导演
	private String mActor;    //演员
	private String mTime;     //上映时间
	private String mSummary;  //简介
	private String mScore;    //评分
	private String mHot;      //热度
	private String mTotal;    //总集数
	private String mRatings;  //用户点评结果
	private ArrayList<Label> mLabelList; //标签列表
	private ArrayList<Drama> mDramaList; //剧集列表

	public Video() 
	{
	}

	public Video(Video item) 
	{
		mRowID = item.mRowID;
		mResCode = item.mResCode;
		mName = item.mName;
		mType = item.mType;
		mSource = item.mSource;
		mUrl = item.mUrl;
		mImage = item.mImage;
		mBigpic = item.mBigpic;
		mDirector = item.mDirector;
		mActor = item.mActor;
		mTime = item.mTime;
		mSummary = item.mSummary;
		mScore = item.mScore;
		mHot = item.mHot;
		mTotal = item.mTotal;
		mRatings = item.mRatings;
		mLabelList = item.mLabelList;
		mDramaList = item.mDramaList;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeString(mRowID);
		out.writeString(mResCode);
		out.writeString(mName);
		out.writeString(mType);
		out.writeString(mSource);
		out.writeString(mUrl);
		out.writeString(mImage);
		out.writeString(mBigpic);
		out.writeString(mDirector);
		out.writeString(mActor);
		out.writeString(mTime);
		out.writeString(mSummary);
		out.writeString(mScore);
		out.writeString(mHot);
		out.writeString(mTotal);
		out.writeString(mRatings);
		out.writeList(mLabelList);
		out.writeList(mDramaList);
	}

	public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() 
	{
		public Video createFromParcel(Parcel in) 
		{
			return new Video(in);
		}

		public Video[] newArray(int size) 
		{
			return new Video[size];
		}
	};

	@SuppressWarnings("unchecked")
	private Video(Parcel in) 
	{
		mRowID = in.readString();
		mResCode = in.readString();
		mName = in.readString();
		mType = in.readString();
		mSource = in.readString();
		mUrl = in.readString();
		mImage = in.readString();
		mBigpic = in.readString();
		mDirector = in.readString();
		mActor = in.readString();
		mTime = in.readString();
		mSummary = in.readString();
		mScore = in.readString();
		mHot = in.readString();
		mTotal = in.readString();
		mRatings = in.readString();
		mLabelList = in.readArrayList(Label.class.getClassLoader());
		mDramaList = in.readArrayList(Drama.class.getClassLoader());
	}
	
	public String getRowID() 
	{
		return mRowID;
	}

	public void setRowID(String rowid) 
	{
		mRowID = rowid;
	}

	public String getResCode() 
	{
		return mResCode;
	}

	public void setResCode(String rescode) 
	{
		//Logcat.i(FlagConstant.TAG, " rescode: " + rescode);
		mResCode = rescode;
	}

	public String getName() 
	{
		return mName;
	}

	public void setName(String name) 
	{
		//Logcat.i(FlagConstant.TAG, " name: " + name);
		mName = name.trim();
	}
	
	public String getType() 
	{
		return mType;
	}

	public void setType(String type) 
	{
		mType = type;
	}
	
	public String getSource() 
	{
		return mSource;
	}

	public void setSource(String source) 
	{
		mSource = source;
	}
	
	public String getUrl() 
	{
		return mUrl;
	}

	public void setUrl(String url) 
	{
		mUrl = url;
	}
	
	public String getImage() 
	{
		return mImage;
	}

	public void setImage(String image) 
	{
		mImage = image;
	}

	public String getBigpic() 
	{
		return mBigpic;
	}

	public void setBigpic(String bigpic) 
	{
		mBigpic = bigpic;
	}

	public String getDirector() 
	{
		return mDirector;
	}

	public void setDirector(String director) 
	{
		mDirector = director;
	}

	public String getActor() 
	{
		return mActor;
	}

	public void setActor(String actor) 
	{
		mActor = actor;
	}

	public String getTime() 
	{
		return mTime;
	}

	public void setTime(String time) 
	{
		mTime = time;
	}

	public String getSummary() 
	{
		return mSummary;
	}

	public void setSummary(String summary) 
	{
		mSummary = summary;
	}

	public String getScore() 
	{
		return mScore;
	}

	public void setScore(String score) 
	{
		mScore = score;
	}

	public String getHot() 
	{
		return mHot;
	}

	public void setHot(String hot) 
	{
		mHot = hot;
	}

	public String getTotaldramas() 
	{
		return mTotal;
	}

	public void setTotaldramas(String total) 
	{
		mTotal = total;
	}

	public String getRatings() 
	{
		return mRatings;
	}

	public void setRatings(String ratings) 
	{
		mRatings = ratings;
	}

	public ArrayList<Label> getLabelList() 
	{
		if(mLabelList == null)
		{
			mLabelList = new ArrayList<Label>();
		}
		return mLabelList;
	}

	public void setLabelList(ArrayList<Label> labelList) 
	{
		mLabelList = labelList;
	}
	
	public ArrayList<Drama> getDramaList() 
	{
		if(mDramaList == null)
		{
			mDramaList = new ArrayList<Drama>();
		}
		return mDramaList;
	}

	public void setDramaList(ArrayList<Drama> dramaList) 
	{
		mDramaList = dramaList;
	}
}
