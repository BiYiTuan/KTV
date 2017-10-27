/**
 * @Title: RequestDataManager.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.requestdata
 * @Description: 请求数据管理
 * @author: zhaoqy
 * @date: 2015-7-29 下午3:04:29
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.requestdata;

import java.util.ArrayList;
import android.content.Context;
import com.sz.ead.app.ktv.dataprovider.datapacket.ElementListData;
import com.sz.ead.app.ktv.dataprovider.entity.AuthResult;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.entity.Hotword;
import com.sz.ead.app.ktv.dataprovider.entity.UpgradeItem;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.dataprovider.http.HttpEngineManager;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.dataprovider.packet.inpacket.InElementListPacket;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutElementListPacket;
import com.sz.ead.app.ktv.utils.Token;

public class RequestDataManager 
{
	/**
	 * 
	 * @Title: requestData
	 * @Description:      请求数据
	 * @param uiCallback: 回调对象
	 * @param context:    上下文切换
	 * @param token:      列表标识ID
	 * @param size:       每页个数
	 * @param page:       当前页
	 * @param argument:   不定参数
	 * @return: void
	 */
	public static void requestData(UICallBack uiCallback, Context context, int token, int size, int page, Object... argument)
	{
		ElementListData data = null;
		data = new ElementListData(token, size, page, argument);
		
		OutElementListPacket mOutPacket = new OutElementListPacket(uiCallback, token, data);
		InElementListPacket mInPacket = new InElementListPacket(uiCallback, token, data);
		HttpEngineManager.createHttpEngine(mOutPacket, mInPacket, context);
	}
	
	/**
	 * 
	 * @Title: getData
	 * @Description: 获取数据
	 * @param in:    将服务器返回的数据解析之后的数据
	 * @return
	 * @return: Object
	 */
	@SuppressWarnings("unchecked")
	public static Object getData(Object in)
	{
		Object out = null;
		ElementListData data = (ElementListData) in;
		
		switch (data.getToken()) 
		{
		case Token.TOKEN_AUTH:             //应用鉴权
		{
			for (Object obj: data.getList()) 
			{
				out = (AuthResult)obj;
			}
			break;
		}
		case Token.TOKEN_UPGRADE:          //应用升级
		{
			out = new ArrayList<UpgradeItem>();
			
			if(data != null)
			{
				for (Object item : data.getList()) 
				{
					UpgradeItem upgradeItem = (UpgradeItem)item;
					((ArrayList<UpgradeItem>)out).add(upgradeItem);
				}
			}	
			break;
		}
		case Token.TOKEN_COLUMN:            //栏目列表
		{
			out = new ArrayList<Column>();
			
			if(data != null)
			{
				for (Object item : data.getList()) 
				{
					Column columnItem = (Column)item;
					((ArrayList<Column>)out).add(columnItem);
				}
			}	
			break;
		}
		case Token.TOKEN_COLUMNSUB:          //栏目下资源
		{
			out = new ArrayList<Video>();
			
			if(data != null)
			{
				for (Object item : data.getList()) 
				{
					Video videoItem = (Video)item;
					((ArrayList<Video>)out).add(videoItem);
				}
			}	
			break;
		}
		case Token.TOKEN_VIDEO:             //资源详情
		{
			for (Object obj: data.getList()) 
			{
				out = (Video)obj;
			}
			break;
		}
		case Token.TOKEN_HOTWORD:          //点播热词
		{
			out = new ArrayList<Hotword>();
			
			if(data != null)
			{
				for (Object item : data.getList()) 
				{
					Hotword hotwordItem = (Hotword)item;
					((ArrayList<Hotword>)out).add(hotwordItem);
				}
			}	
			break;
		}
		case Token.TOKEN_SEARCH:         //点播资源搜索  
		{
			out = new ArrayList<Video>();
			
			if(data != null)
			{
				for (Object item : data.getList()) 
				{
					Video videoItem = (Video)item;
					((ArrayList<Video>)out).add(videoItem);
				}
			}	
			break;
		}
		default:
			break;
		}
		
		return out;
	}
	
	/**
	 * 
	 * @Title: getTotal
	 * @Description: 获取数据个数
	 * @param in
	 * @return
	 * @return: int
	 */
	public static int getTotal(Object in)
	{
		int count = 0;
		ElementListData data = (ElementListData) in;
		
		switch (data.getToken()) 
		{
		case Token.TOKEN_AUTH:      //应用鉴权
		case Token.TOKEN_UPGRADE:   //应用升级
		case Token.TOKEN_VIDEO:     //点播资源详情
		{
			break;
		}
		case Token.TOKEN_COLUMN:    //栏目列表
		case Token.TOKEN_COLUMNSUB: //栏目下资源
		case Token.TOKEN_HOTWORD:   //点播热词
		case Token.TOKEN_SEARCH:    //点播资源搜索  
		{
			count = data.getTotal();
			break;
		}
		default:
			break;
		}
		
		return count;
	}
	
	public static void cancelRequest(int id)
	{
		HttpEngineManager.cancelById(id, true);
	}
}
