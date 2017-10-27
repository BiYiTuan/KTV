/**
 * @Title: Column.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.entity.ktv
 * @Description: 栏目item
 * @author: zhaoqy
 * @date: 2015-7-29 上午9:58:52
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Column implements Parcelable 
{
	private String  mColumnID;       //栏目id
	private String  mColumnCode;     //栏目编码
	private String  mName;           //栏目名称
	private String  mImage;          //栏目图片地址
	private String  mResCode;        //播放地址
	private int     mHasChildColumn; //是否有子栏目 0：没有，1：有
	private int     mIsResource;     //是否是资源 0：是，1：不是
	private int     mSort;           //分类
	
	public Column() 
	{
	}

	public Column(Column item) 
	{
		mColumnID = item.mColumnID;
		mColumnCode = item.mColumnCode;
		mName = item.mName;
		mImage = item.mImage;
		mResCode = item.mResCode;
		mHasChildColumn = item.mHasChildColumn;
		mIsResource = item.mIsResource;
		mSort = item.mSort;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeString(mColumnID);
		out.writeString(mColumnCode);
		out.writeString(mName);
		out.writeString(mImage);
		out.writeString(mResCode);
		out.writeInt(mHasChildColumn);
		out.writeInt(mIsResource);
		out.writeInt(mSort);
	}
	
	public static final Parcelable.Creator<Column> CREATOR = new Parcelable.Creator<Column>() 
	{
		public Column createFromParcel(Parcel in) 
		{
			return new Column(in);
		}
		
		public Column[] newArray(int size) 
		{
			return new Column[size];
		}
	};
	
	private Column(Parcel in) 
	{
		mColumnID = in.readString();
		mColumnCode = in.readString();
		mName = in.readString();
		mImage = in.readString();
		mResCode = in.readString();
		mHasChildColumn = in.readInt();
		mIsResource = in.readInt();
		mSort = in.readInt();
	}
	
	public String getColumnID() 
	{
		return mColumnID;
	}
	
	public void setColumnID(String columnID) 
	{
		mColumnID = columnID;
	}
	
	public String getColumnCode() 
	{
		return mColumnCode;
	}

	public void setColumnCode(String columnCode) 
	{
		//Logcat.i(FlagConstant.TAG, " columnCode: " + columnCode);
		mColumnCode = columnCode;
	}
	
	public String getName() 
	{
		return mName;
	}

	public void setName(String name) 
	{
		//Logcat.i(FlagConstant.TAG, " name: " + name);
		mName = name;
	}
	
	public String getImage() 
	{
		return mImage;
	}

	public void setImage(String image) 
	{
		//Logcat.i(FlagConstant.TAG, " image: " + image);
		mImage = image;
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

	public int getHasChildColumn() 
	{
		return mHasChildColumn;
	}

	public void setHasChildColumn(int hasChildColumn) 
	{
		mHasChildColumn = hasChildColumn;
	}

	public int getIsResource() 
	{
		return mIsResource;
	}

	public void setIsResource(int isResource) 
	{
		mIsResource = isResource;
	}
	
	public void setSort(int sort)
	{
		mSort = sort;
	}
	
	public int getSort()
	{
		return mSort;
	}
}
