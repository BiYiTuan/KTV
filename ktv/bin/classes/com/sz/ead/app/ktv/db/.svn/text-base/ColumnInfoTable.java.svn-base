/**
 * @Title: ColumnTable.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.db
 * @Description: 栏目表
 * @author: zhaoqy
 * @date: 2015-7-30 下午1:51:29
 * @version: V1.0
 */

package com.sz.ead.app.ktv.db;

import java.util.ArrayList;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ColumnInfoTable 
{
	public static final String AUTHORITY = "com.sz.ead.app.ktv_wakg.db.DatabaseManager";                        
	public static final String COLUMN_TABLE = "columntable";                                               
	public static final Uri CONTENT_COLUMN_URI = Uri.parse("content://" + AUTHORITY + "/" + COLUMN_TABLE); 
	public static final String ID = "_id";                              //主键
	public static final String COLUMN_ID = "column_id";                 //栏目id
	public static final String COLUMN_CODE = "column_code";             //栏目编码
	public static final String COLUMN_NAME = "column_name";             //栏目名称
	public static final String COLUMN_IMAGE = "column_image";           //图片地址
	public static final String COLUMN_RESCODE = "column_rescode";       //资源编码
	public static final String COLUMN_HASCHILD = "column_haschild";     //是否有子栏目
	public static final String COLUMN_ISRESOURCE = "column_isresource"; //是否是资源
	public static final String COLUMN_SORT = "column_sort";             //类型
	public static final int COLUMNTABLE_RECOMMEND = 0;                  //推荐
	public static final int COLUMNTABLE_COLUMN = 1;                     //分类
	public static final int COLUMNTABLE_LANG = 2;                       //语种
	public static final int COLUMNTABLE_SINGER_CATEGORY = 3;            //歌手分类
	public static final int COLUMNTABLE_SONG_CATEGORY = 4;              //分类点歌
	
	/**
	 * @Title: getCreateSql
	 * @Description: 得到创建表spl
	 * @return 返回创建表spl
	 * @return: String
	 */
	public static String getCreateSql() 
	{
		/**
		 * SQL语句：
		 * db.execSQL("CREATE TABLE  IF NOT EXISTS " + COLUMN_TABLE + "( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "column_id TEXT," +
		 * column_code + " TEXT,"  + "column_name TEXT,"  + "column_image TEXT," + "column_haschild Integer," + "column_isresource Integer" + ");");
		 */		
		StringBuffer sb = new StringBuffer();	
		sb.append("CREATE TABLE  IF NOT EXISTS ");
		sb.append(COLUMN_TABLE);
		sb.append("(");
		sb.append(ID);
		sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
		sb.append(COLUMN_ID);
		sb.append(" TEXT,");
		sb.append(COLUMN_CODE);
		sb.append(" TEXT,");
		sb.append(COLUMN_NAME);
		sb.append(" TEXT,");
		sb.append(COLUMN_IMAGE);
		sb.append(" TEXT,");
		sb.append(COLUMN_RESCODE);
		sb.append(" TEXT,");
		sb.append(COLUMN_HASCHILD);
		sb.append(" Integer,");	
		sb.append(COLUMN_ISRESOURCE);
		sb.append(" Integer,");	
		sb.append(COLUMN_SORT);
		sb.append(" Integer");	
		sb.append(");");
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: getUpgradeSql
	 * @Description: 得到升级表spl
	 * @return
	 * @return: String
	 */
	public static String getUpgradeSql() 
	{
		/**
		 * SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		 * qb.setTables(CONTENT_SORT_URI.getPathSegments().get(0)); Cursor
		 * abcCursor=qb.query(db,null,null,null,null,null,null);
		 */
		String string = "DROP TABLE IF EXISTS " + COLUMN_TABLE;
		return string;
	}
	
	/**
	 * 
	 * @Title: queryAllColumn
	 * @Description: 查询所有栏目
	 * @return
	 * @return: ArrayList<Column>
	 */
	public static ArrayList<Column> queryColumns()
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		ArrayList<Column> columnList = new ArrayList<Column>();
		Cursor cursor = null;
		String where = null;
		String orderBy = ID + " DESC";  //倒序
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		cursor = db.query(COLUMN_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			cursor.setNotificationUri(DatabaseManager.mContext.getContentResolver(), CONTENT_COLUMN_URI);
			int n = cursor.getCount();
			for (int i=0; i<n; i++) 
			{
				cursor.moveToPosition(i);
				Column columnInfo = new Column();
				columnInfo.setColumnID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
				columnInfo.setColumnCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
				columnInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
				columnInfo.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
				columnInfo.setResCode(cursor.getString(cursor.getColumnIndex(COLUMN_RESCODE)));
				columnInfo.setHasChildColumn(cursor.getInt(cursor.getColumnIndex(COLUMN_HASCHILD)));
				columnInfo.setIsResource(cursor.getInt(cursor.getColumnIndex(COLUMN_ISRESOURCE)));
				columnInfo.setSort(cursor.getInt(cursor.getColumnIndex(COLUMN_SORT)));
				columnList.add(columnInfo);
			}
		} 
		
		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return columnList;
	}
	
	public static ArrayList<Column> queryColumn(int sort)
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		ArrayList<Column> columnList = new ArrayList<Column>();
		Cursor cursor = null;
		String where = COLUMN_SORT + "=" + sort;
		String orderBy = ID + " ASC";  
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		cursor = db.query(COLUMN_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			cursor.setNotificationUri(DatabaseManager.mContext.getContentResolver(), CONTENT_COLUMN_URI);
			int n = cursor.getCount();
			for (int i=0; i<n; i++) 
			{
				cursor.moveToPosition(i);
				Column columnInfo = new Column();
				columnInfo.setColumnID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
				columnInfo.setColumnCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
				columnInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
				columnInfo.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
				columnInfo.setResCode(cursor.getString(cursor.getColumnIndex(COLUMN_RESCODE)));
				columnInfo.setHasChildColumn(cursor.getInt(cursor.getColumnIndex(COLUMN_HASCHILD)));
				columnInfo.setIsResource(cursor.getInt(cursor.getColumnIndex(COLUMN_ISRESOURCE)));
				columnInfo.setSort(cursor.getInt(cursor.getColumnIndex(COLUMN_SORT)));
				columnList.add(columnInfo);
			}
		} 
		
		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return columnList;
	}
	
	/**
	 * 
	 * @Title: getOldRecord
	 * @Description: 获取记录
	 * @return
	 * @return: Column
	 */
	public static Column getOldRecord()
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		Column columnInfo = null;
		Cursor cursor = null;
		String where = null;
		String orderBy = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		orderBy = ID + " ASC";
		cursor = db.query(COLUMN_TABLE, null, where, null, null, null, orderBy);
		if (cursor != null) 
		{
			cursor.setNotificationUri(DatabaseManager.mContext.getContentResolver(), CONTENT_COLUMN_URI);
			cursor.moveToPosition(0);
			columnInfo = new Column();
			columnInfo.setColumnID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
			columnInfo.setColumnCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
			columnInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			columnInfo.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
			columnInfo.setResCode(cursor.getString(cursor.getColumnIndex(COLUMN_RESCODE)));
			columnInfo.setHasChildColumn(cursor.getInt(cursor.getColumnIndex(COLUMN_HASCHILD)));
			columnInfo.setIsResource(cursor.getInt(cursor.getColumnIndex(COLUMN_ISRESOURCE)));
			columnInfo.setSort(cursor.getInt(cursor.getColumnIndex(COLUMN_SORT)));
		} 
		
		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return columnInfo;
	}
	
	/**
	 * 
	 * @Title: queryColumn
	 * @Description: 根据栏目编码查询栏目
	 * @param code
	 * @return
	 * @return: Column
	 */
	public static Column queryColumn(String code)
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return null;
		}
		Column columnInfo = null;
		Cursor cursor = null;
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		where = COLUMN_CODE + "=\"" + code + "\" ";
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		
		cursor = db.query(COLUMN_TABLE, null, where, null, null, null, null);
		if (cursor != null) 
		{
			cursor.setNotificationUri(DatabaseManager.mContext.getContentResolver(),CONTENT_COLUMN_URI);
			int n = cursor.getCount();
			if (n > 0)
			{
				cursor.moveToPosition(0);
				columnInfo = new Column();
				columnInfo.setColumnID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
				columnInfo.setColumnCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
				columnInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
				columnInfo.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
				columnInfo.setResCode(cursor.getString(cursor.getColumnIndex(COLUMN_RESCODE)));
				columnInfo.setHasChildColumn(cursor.getInt(cursor.getColumnIndex(COLUMN_HASCHILD)));
				columnInfo.setIsResource(cursor.getInt(cursor.getColumnIndex(COLUMN_ISRESOURCE)));
				columnInfo.setSort(cursor.getInt(cursor.getColumnIndex(COLUMN_SORT)));
			}
		} 
		
		if (cursor != null) 
		{
			cursor.close();
			cursor = null;
		}
		return columnInfo;
	}
	
	/**
	 * 
	 * @Title: insertColumn
	 * @Description: 插入栏目
	 * @param appRecd
	 * @return
	 * @return: boolean
	 */
	public static boolean insertColumn(Column column, int sort)
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return false;
		}
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		
		Column columnInfo = column;
		if(queryColumn(columnInfo.getColumnCode()) != null)
		{
			deleteColumn(columnInfo.getColumnCode());
		
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, columnInfo.getColumnID());
			values.put(COLUMN_CODE, columnInfo.getColumnCode());
			values.put(COLUMN_NAME, columnInfo.getName());
			values.put(COLUMN_IMAGE, columnInfo.getImage());
			values.put(COLUMN_RESCODE, columnInfo.getResCode());
			values.put(COLUMN_HASCHILD, columnInfo.getHasChildColumn());
			values.put(COLUMN_ISRESOURCE, columnInfo.getIsResource());
			values.put(COLUMN_SORT, sort);
			db.insert(COLUMN_TABLE, null, values);
		}
		else
		{
			int count = queryColumns().size();
			//最大11个
			int maxCount = 11;
			if(count >= maxCount)
			{
				Column old = getOldRecord();
				if(old != null)
				{
					deleteColumn(old.getColumnCode());
				}
			}
			
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, columnInfo.getColumnID());
			values.put(COLUMN_CODE, columnInfo.getColumnCode());
			values.put(COLUMN_NAME, columnInfo.getName());
			values.put(COLUMN_IMAGE, columnInfo.getImage());
			values.put(COLUMN_RESCODE, columnInfo.getResCode());
			values.put(COLUMN_HASCHILD, columnInfo.getHasChildColumn());
			values.put(COLUMN_ISRESOURCE, columnInfo.getIsResource());
			values.put(COLUMN_SORT, sort);
			db.insert(COLUMN_TABLE, null, values);
		}
		return true;
	}
	
	/**
	 * 
	 * @Title: insertColumnList
	 * @Description: 插入栏目列表
	 * @param columnList
	 * @param sort
	 * @return: void
	 */
	public static void insertColumnList(ArrayList<Column> columnList, int sort)
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return;
		}
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		
		db.beginTransaction();
		for (int i = 0; i < columnList.size(); i++) 
		{
			Column columnInfo = columnList.get(i);
			if(queryColumn(columnInfo.getColumnCode()) != null)
			{
				deleteColumn(columnInfo.getColumnCode());
			}
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, columnInfo.getColumnID());
			values.put(COLUMN_CODE, columnInfo.getColumnCode());
			values.put(COLUMN_NAME, columnInfo.getName());
			values.put(COLUMN_IMAGE, columnInfo.getImage());
			values.put(COLUMN_RESCODE, columnInfo.getResCode());
			values.put(COLUMN_HASCHILD, columnInfo.getHasChildColumn());
			values.put(COLUMN_ISRESOURCE, columnInfo.getIsResource());
			values.put(COLUMN_SORT, sort);
			db.insert(COLUMN_TABLE, null, values);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	/**
	 * 
	 * @Title: deleteColumn
	 * @Description: 删除栏目
	 * @param code
	 * @return
	 * @return: boolean
	 */
	public static boolean deleteColumn(String code)
	{
		if (DatabaseManager.mDbHelper == null) 
		{
			return false;
		}
		String where = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		where = COLUMN_CODE + "=\"" + code + "\" ";
		SQLiteDatabase db = DatabaseManager.mDbHelper.getReadableDatabase();
		qb.setTables(CONTENT_COLUMN_URI.getPathSegments().get(0));
		db.delete(COLUMN_TABLE, where, null);
		return true;
	}	
}
