/**
 * @Title: SungInfoTable.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.db
 * @Description: 已唱Table
 * @author: zhaoqy
 * @date: 2015-8-6 下午8:59:15
 * @version: V1.0
 */

package com.sz.ead.app.ktv.db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.JsonParse;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;

public class SungInfoTable 
{
	public static final String AUTHORITY = "com.sz.ead.app.ktv_wakg.db.DatabaseManager";                      
	public static final String SUNG_TABLE = "sungtable";                                            
	public static final Uri CONTENT_SUNG_URI = Uri.parse("content://" + AUTHORITY + "/" + SUNG_TABLE); 
	public static final String ID = "_id";                        //主键
	public static final String VIDEO_ROWID = "rowid";             //数据库中的id
	public static final String VIDEO_CODE = "rescode";            //编码
	public static final String VIDEO_NAME = "name";               //名称
	public static final String VIDEO_TYPE = "type";               //资源类型0：直播，1：点播
	public static final String VIDEO_SOURCE = "source";           //频道来源
	public static final String VIDEO_URL = "tvurl";               //音乐播放地址
	public static final String VIDEO_IMAGE = "image";             //频道logo/资源小图
	public static final String VIDEO_BIGPIC = "bigpic";           //大图地址
	public static final String VIDEO_DIRECTOR = "director";       //导演
	public static final String VIDEO_ACTOR = "actor";             //演员
	public static final String VIDEO_TIME = "time";               //上映时间
	public static final String VIDEO_SUMMARY = "summary";         //简介
	public static final String VIDEO_SCORE = "score";             //评分
	public static final String VIDEO_HOT = "hot";                 //热度
	public static final String VIDEO_TOTALDRAMAS = "totaldramas"; //总集数
	public static final String VIDEO_RATINGS = "ratings";         //用户点评结果
	public static final String VIDEO_LABEL = "label";             //标签
	public static int gRowID = 0;

	/**
	 * @Title: getCreateSql
	 * @Description: 得到创建表spl
	 * @return 返回创建表spl
	 * @return: String
	 */
	public static String getCreateSql() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE  IF NOT EXISTS ");
		sb.append(SUNG_TABLE);
		sb.append("(");
		sb.append(ID);
		sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
		sb.append(VIDEO_ROWID);
		sb.append(" TEXT,");	
		sb.append(VIDEO_CODE);
		sb.append(" TEXT,");
		sb.append(VIDEO_NAME);
		sb.append(" TEXT,");
		sb.append(VIDEO_TYPE);
		sb.append(" TEXT,");
		sb.append(VIDEO_SOURCE);
		sb.append(" TEXT,");
		sb.append(VIDEO_URL);
		sb.append(" TEXT,");
		sb.append(VIDEO_IMAGE);
		sb.append(" TEXT,");
		sb.append(VIDEO_BIGPIC);
		sb.append(" TEXT,");
		sb.append(VIDEO_DIRECTOR);
		sb.append(" TEXT,");
		sb.append(VIDEO_ACTOR);
		sb.append(" TEXT,");
		sb.append(VIDEO_TIME);
		sb.append(" TEXT,");
		sb.append(VIDEO_SUMMARY);
		sb.append(" TEXT,");
		sb.append(VIDEO_SCORE);
		sb.append(" TEXT,");
		sb.append(VIDEO_HOT);
		sb.append(" TEXT,");
		sb.append(VIDEO_TOTALDRAMAS);
		sb.append(" TEXT,");
		sb.append(VIDEO_RATINGS);
		sb.append(" TEXT,");
		sb.append(VIDEO_LABEL);
		sb.append(" TEXT");
		sb.append(");");

		return sb.toString();
	}

	public static String getUpgradeSql() 
	{
		String string = "DROP TABLE IF EXISTS " + SUNG_TABLE;
		return string;
	}

	/**
	 * @Title: querySungVideos
	 * @Description: 查询已唱video
	 * @return
	 * @return: ArrayList<Video>
	 */
	public static ArrayList<Video> querySungVideos() 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		ArrayList<Video> videoList = new ArrayList<Video>();
		Cursor cursor = null;
		String where = null;
		String orderBy = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		orderBy = ID + " DESC";  
		cursor = db.query(SUNG_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			cursor.setNotificationUri(DatabaseManager.mContext.getContentResolver(), CONTENT_SUNG_URI);
			int n = cursor.getCount();
			for (int i=0; i<n; i++) 
			{
				cursor.moveToPosition(i);
				Video video = new Video();
				video.setResCode(cursor.getString(cursor.getColumnIndex(VIDEO_CODE)));
				video.setRowID(cursor.getString(cursor.getColumnIndex(VIDEO_ROWID)));
				video.setName(cursor.getString(cursor.getColumnIndex(VIDEO_NAME)));
				video.setType(cursor.getString(cursor.getColumnIndex(VIDEO_TYPE)));
				video.setSource(cursor.getString(cursor.getColumnIndex(VIDEO_SOURCE)));
				video.setUrl(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
				video.setImage(cursor.getString(cursor.getColumnIndex(VIDEO_IMAGE)));
				video.setBigpic(cursor.getString(cursor.getColumnIndex(VIDEO_BIGPIC)));
				video.setDirector(cursor.getString(cursor.getColumnIndex(VIDEO_DIRECTOR)));
				video.setActor(cursor.getString(cursor.getColumnIndex(VIDEO_ACTOR)));
				video.setTime(cursor.getString(cursor.getColumnIndex(VIDEO_TIME)));
				video.setSummary(cursor.getString(cursor.getColumnIndex(VIDEO_SUMMARY)));
				video.setScore(cursor.getString(cursor.getColumnIndex(VIDEO_SCORE)));
				video.setHot(cursor.getString(cursor.getColumnIndex(VIDEO_HOT)));
				video.setTotaldramas(cursor.getString(cursor.getColumnIndex(VIDEO_TOTALDRAMAS)));
				video.setRatings(cursor.getString(cursor.getColumnIndex(VIDEO_RATINGS)));
				video.setLabelList(JsonParse.parseJson(cursor.getString(cursor.getColumnIndex(VIDEO_LABEL))));
				videoList.add(video);
			}
		} 

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return videoList;
	}

	/**
	 * 
	 * @Title: querySungVideoAmount
	 * @Description: 查询已唱video的数量
	 * @return
	 */
	public static int querySungVideoAmount() 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return 0;
		}
		int amount = 0;
		Cursor cursor = null;
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		cursor = db.query(SUNG_TABLE, new String[] {ID}, where, null, null, null, null);
		if (cursor != null) 
		{
			amount = cursor.getCount();
		} 
		else 
		{
			amount = 0;
		}

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return amount;
	}

	/**
	 * 
	 * @Title: queryTopRecord
	 * @Description: 查询最近的记录
	 * @return
	 */
	public static Video queryTopRecordSung() 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		Video video = null;
		Cursor cursor = null;
		String where = null;
		String orderBy = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		orderBy = ID + " DESC";
		cursor = db.query(SUNG_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			int n = cursor.getCount();
			if (n > 0) 
			{
				cursor.moveToPosition(0);
				video= new Video();
				video.setResCode(cursor.getString(cursor.getColumnIndex(VIDEO_CODE)));
				video.setRowID(cursor.getString(cursor.getColumnIndex(VIDEO_ROWID)));
				video.setName(cursor.getString(cursor.getColumnIndex(VIDEO_NAME)));
				video.setType(cursor.getString(cursor.getColumnIndex(VIDEO_TYPE)));
				video.setSource(cursor.getString(cursor.getColumnIndex(VIDEO_SOURCE)));
				video.setUrl(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
				video.setImage(cursor.getString(cursor.getColumnIndex(VIDEO_IMAGE)));
				video.setBigpic(cursor.getString(cursor.getColumnIndex(VIDEO_BIGPIC)));
				video.setDirector(cursor.getString(cursor.getColumnIndex(VIDEO_DIRECTOR)));
				video.setActor(cursor.getString(cursor.getColumnIndex(VIDEO_ACTOR)));
				video.setTime(cursor.getString(cursor.getColumnIndex(VIDEO_TIME)));
				video.setSummary(cursor.getString(cursor.getColumnIndex(VIDEO_SUMMARY)));
				video.setScore(cursor.getString(cursor.getColumnIndex(VIDEO_SCORE)));
				video.setHot(cursor.getString(cursor.getColumnIndex(VIDEO_HOT)));
				video.setTotaldramas(cursor.getString(cursor.getColumnIndex(VIDEO_TOTALDRAMAS)));
				video.setRatings(cursor.getString(cursor.getColumnIndex(VIDEO_RATINGS)));
				video.setLabelList(JsonParse.parseJson(cursor.getString(cursor.getColumnIndex(VIDEO_LABEL))));
			}
		} 

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return video;
	}
	
	/**
	 * 
	 * @Title: getOldRecord
	 * @Description: 查询最早的记录
	 * @return
	 * @return: Video
	 */
	public static Video getOldRecordSung() 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		Video video = null;
		Cursor cursor = null;
		String where = null;
		String orderBy = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		orderBy = ID + " ASC";
		cursor = db.query(SUNG_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			cursor.moveToPosition(0);
			video = new Video();
			video.setResCode(cursor.getString(cursor.getColumnIndex(VIDEO_CODE)));
			video.setRowID(cursor.getString(cursor.getColumnIndex(VIDEO_ROWID)));
			video.setName(cursor.getString(cursor.getColumnIndex(VIDEO_NAME)));
			video.setType(cursor.getString(cursor.getColumnIndex(VIDEO_TYPE)));
			video.setSource(cursor.getString(cursor.getColumnIndex(VIDEO_SOURCE)));
			video.setUrl(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
			video.setImage(cursor.getString(cursor.getColumnIndex(VIDEO_IMAGE)));
			video.setBigpic(cursor.getString(cursor.getColumnIndex(VIDEO_BIGPIC)));
			video.setDirector(cursor.getString(cursor.getColumnIndex(VIDEO_DIRECTOR)));
			video.setActor(cursor.getString(cursor.getColumnIndex(VIDEO_ACTOR)));
			video.setTime(cursor.getString(cursor.getColumnIndex(VIDEO_TIME)));
			video.setSummary(cursor.getString(cursor.getColumnIndex(VIDEO_SUMMARY)));
			video.setScore(cursor.getString(cursor.getColumnIndex(VIDEO_SCORE)));
			video.setHot(cursor.getString(cursor.getColumnIndex(VIDEO_HOT)));
			video.setTotaldramas(cursor.getString(cursor.getColumnIndex(VIDEO_TOTALDRAMAS)));
			video.setRatings(cursor.getString(cursor.getColumnIndex(VIDEO_RATINGS)));
			video.setLabelList(JsonParse.parseJson(cursor.getString(cursor.getColumnIndex(VIDEO_LABEL))));
		}

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return video;
	}

	/***
	 * 
	 * @Title: queryVideo
	 * @Description: 查询特定节目
	 * @param rescode
	 * @return
	 */
	public static Video querySungVideo(String rescode) 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		Video video = null;
		Cursor cursor = null;
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		where = VIDEO_CODE + "=\"" + rescode + "\" ";
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		cursor = db.query(SUNG_TABLE, null, where, null, null, null, null);
		if (cursor != null) 
		{
			int n = cursor.getCount();
			for (int i=0; i<n; i++) 
			{
				cursor.moveToPosition(i);
				video = new Video();
				video.setResCode(cursor.getString(cursor.getColumnIndex(VIDEO_CODE)));
				video.setRowID(cursor.getString(cursor.getColumnIndex(VIDEO_ROWID)));
				video.setName(cursor.getString(cursor.getColumnIndex(VIDEO_NAME)));
				video.setType(cursor.getString(cursor.getColumnIndex(VIDEO_TYPE)));
				video.setSource(cursor.getString(cursor.getColumnIndex(VIDEO_SOURCE)));
				video.setUrl(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
				video.setImage(cursor.getString(cursor.getColumnIndex(VIDEO_IMAGE)));
				video.setBigpic(cursor.getString(cursor.getColumnIndex(VIDEO_BIGPIC)));
				video.setDirector(cursor.getString(cursor.getColumnIndex(VIDEO_DIRECTOR)));
				video.setActor(cursor.getString(cursor.getColumnIndex(VIDEO_ACTOR)));
				video.setTime(cursor.getString(cursor.getColumnIndex(VIDEO_TIME)));
				video.setSummary(cursor.getString(cursor.getColumnIndex(VIDEO_SUMMARY)));
				video.setScore(cursor.getString(cursor.getColumnIndex(VIDEO_SCORE)));
				video.setHot(cursor.getString(cursor.getColumnIndex(VIDEO_HOT)));
				video.setTotaldramas(cursor.getString(cursor.getColumnIndex(VIDEO_TOTALDRAMAS)));
				video.setRatings(cursor.getString(cursor.getColumnIndex(VIDEO_RATINGS)));
				video.setLabelList(JsonParse.parseJson(cursor.getString(cursor.getColumnIndex(VIDEO_LABEL))));
			}
		} 

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return video;
	}

	public static void insertSungVideo(Video video) 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return;
		}
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		
		String rowid = "" + System.currentTimeMillis();
		ContentValues values = new ContentValues();
		values.put(VIDEO_ROWID, rowid);
		values.put(VIDEO_CODE, video.getResCode());
		values.put(VIDEO_NAME, video.getName());
		values.put(VIDEO_TYPE, video.getType());
		values.put(VIDEO_SOURCE, video.getSource());
		values.put(VIDEO_URL, video.getUrl());
		values.put(VIDEO_IMAGE, video.getImage());
		values.put(VIDEO_BIGPIC, video.getBigpic());
		values.put(VIDEO_DIRECTOR, video.getDirector());
		values.put(VIDEO_ACTOR, video.getActor());
		values.put(VIDEO_TIME, video.getTime());
		values.put(VIDEO_SUMMARY, video.getSummary());
		values.put(VIDEO_SCORE, video.getScore());
		values.put(VIDEO_HOT, video.getHot());
		values.put(VIDEO_TOTALDRAMAS, video.getTotaldramas());
		values.put(VIDEO_RATINGS, video.getRatings());
		values.put(VIDEO_LABEL, JsonParse.buildJson(video.getLabelList()));
		db.insert(SUNG_TABLE, null, values);
	}
	
	public static void deleteSungVideoByRowID(String rowid) 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return;
		}
		String where = null;
		where = VIDEO_ROWID + "=\"" + rowid + "\" ";
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		db.delete(SUNG_TABLE, where, null);
	}
	
	public static void deleteSungVideo(String rescode) 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return;
		}
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		where = VIDEO_CODE + "=\"" + rescode + "\" ";
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		db.delete(SUNG_TABLE, where, null);
	}
	
	public static void deleteSung() 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return;
		}
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		db.delete(SUNG_TABLE, where, null);
	}

	/**
	 * 
	 * @Title: isVideoExist
	 * @Description: 是否存在特定code的video
	 * @param videocode
	 * @return
	 */
	public static boolean isSungVideoExist(String rescode) 
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return false;
		}
		boolean nRet = false;
		Cursor cursor = null;
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		where = VIDEO_CODE + "=\"" + rescode + "\" ";
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_SUNG_URI.getPathSegments().get(0));
		cursor = db.query(SUNG_TABLE, new String[] { ID }, where, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) 
		{
			nRet = true;
		} 
		else 
		{
			nRet = false;
		}

		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return nRet;
	}
	
	public static void removeDuplicateOfSung() 
	{
		//delete from sungtable where rescode  in (select  rescode  from people  group  by  rescode   having  count(rescode) > 1)  
		//and _id not in (select min(_id) from  people  group by rescode  having count(rescode )>1)  
		String sql = "delete from " + SUNG_TABLE + " where " + VIDEO_CODE + " in " + "(select " + VIDEO_CODE + " from " + SUNG_TABLE + " group by " + 
		             VIDEO_CODE + " having count(" + VIDEO_CODE + ")" + "> 1)" + " and " + ID + " not in " + "(select min(" + ID + ")" + " from " + SUNG_TABLE + 
		             " group by " + VIDEO_CODE + " having count(" + VIDEO_CODE + ")" + "> 1)";
		Logcat.i(FlagConstant.TAG, "+++++++ removeDuplicateOfSung ++++++ sql: " + sql);
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		db.execSQL(sql);
	}
}
