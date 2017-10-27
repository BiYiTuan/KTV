/**
 * @Title: OutElementListPacket.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.packet.outpacket
 * @Description: 本文件定义上行至服务器的元素列表数据包
 * @author: zhaoqy
 * @date: 2015-6-8 下午8:07:18
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.packet.outpacket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import com.sz.ead.app.ktv.dataprovider.datapacket.ElementListData;
import com.sz.ead.app.ktv.dataprovider.http.UICallBack;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.GeneralParam;
import com.sz.ead.app.ktv.utils.Logcat;
import com.sz.ead.app.ktv.utils.StbInfo;
import com.sz.ead.app.ktv.utils.Token;

public class OutElementListPacket implements OutPacket
{
	private ElementListData mElementLitData; 
	
	/**
	 * 外发元素列表数据的构造函数
	 * @author zhaoqy
	 * @param uiCallback
	 * @param token
	 * @param datas
	 */
	public OutElementListPacket(UICallBack uiCallback, int token, ElementListData data) 
	{
		mElementLitData = data;
	}

	/**
	 * 
	 * @author zhaoqy
	 * @param context  android上下文context
	 * @return 请求URL地址
	 * @throws ProtocolException
	 * @throws IOException
	 */
	@Override
	public URL serviceURL(Context context) throws ProtocolException, IOException 
	{
		StringBuffer sb = new StringBuffer();
		sb.append(Constant.REQUEST_URL_HOST);
	
		switch (mElementLitData.getToken()) 
		{
		case Token.TOKEN_AUTH:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/app_auth.xml");
			}
			else
			{
				sb.append("/");
				sb.append("auth.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("firmware=");
				sb.append(StbInfo.getHwVersion(context));
				sb.append("&");
				sb.append("pmid=");
				sb.append(StbInfo.getPmVersion(context));
				sb.append("&");
				sb.append("version=");
				sb.append(StbInfo.getVersion(context));
			}
			break;
		}
		case Token.TOKEN_UPGRADE:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/app_upgrade.xml");
			}
			else
			{
				sb.append("/");
				sb.append("upgrade.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("version=");
				sb.append(mElementLitData.getArgument().get(0));  //传versionName(String), 而不是versionCode(int)
			}
			break;
		}
		case Token.TOKEN_COLUMN: 
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/column_main.xml");
			}
			else
			{
				sb.append("/");
				sb.append("column.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("size=");
				sb.append(mElementLitData.getSize());
				sb.append("&");
				sb.append("page=");
				sb.append(mElementLitData.getPage());
				sb.append("&");
				sb.append("pcolumncode=");
				sb.append(mElementLitData.getArgument().get(0));
				sb.append("&");
				sb.append("keyword=");
				sb.append(mElementLitData.getArgument().get(1));
			}
			break;
		}
		case Token.TOKEN_COLUMNSUB: 
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/columnsub_lang.xml");
			}
			else
			{
				sb.append("/");
				sb.append("columnsubcoll.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("size=");
				sb.append(mElementLitData.getSize());
				sb.append("&");
				sb.append("page=");
				sb.append(mElementLitData.getPage());
				sb.append("&");
				sb.append("sorttype=");
				sb.append("1");
				sb.append("&");
				sb.append("columncode=");
				sb.append(mElementLitData.getArgument().get(0));
			}
			break;
		}
		case Token.TOKEN_VIDEO: 
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/video.xml");
			}
			else
			{
				sb.append("/");
				sb.append("video.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("videocode=");
				sb.append(mElementLitData.getArgument().get(0));
			}
			break;
		}
		case Token.TOKEN_HOTWORD: 
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/hotword.xml");  
			}
			else
			{
				sb.append("/");
				sb.append("hotword.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
			}
			break;
		}
		case Token.TOKEN_SEARCH: 
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/search.xml");
			}
			else
			{
				sb.append("/");
				sb.append("searchvideocoll.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("size=");
				sb.append(mElementLitData.getSize());
				sb.append("&");
				sb.append("page=");
				sb.append(mElementLitData.getPage());
				sb.append("&");
				sb.append("sorttype=");
				sb.append("1");
				sb.append("&");
				sb.append("type=");
				sb.append("1");
				sb.append("&");
				sb.append("keyword=");
				sb.append(URLEncoder.encode(mElementLitData.getArgument().get(0), "UTF-8"));
			}
			break;
		}
		case Token.TOKEN_APPLY:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/apply.xml");
			}
			else
			{
				sb.append("/");
				sb.append("applytrial.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
			}
			break;
		}
		case Token.TOKEN_FEEAUTH:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/feeauth.xml");
			}
			else
			{
				sb.append("/");
				sb.append("feeauth.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("rescode=");
				sb.append("");
			}
			break;
		}
		case Token.TOKEN_ACCOUNT:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/account.xml");
			}
			else
			{
				sb.append("/");
				sb.append("useraccount.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
			}
			break;
		}
		case Token.TOKEN_RECHARGE:
		{
			if(Constant.URL_LOCAL_SERVICE)
			{
				sb.append("/recharge.xml");
			}
			else
			{
				sb.append("/");
				sb.append("license.action?");
				sb.append(GeneralParam.generateGeneralParam(context));
				sb.append("&");
				sb.append("lc=");
				sb.append(mElementLitData.getArgument().get(0));
			}
			break;
		}
		default:
			break;
		}
		
		Logcat.i(FlagConstant.TAG, " URL: " + sb.toString());
		return new URL(sb.toString());
	}

	/**
	 * 设置外发包请求服务器时所用的方法
	 * @author zhaoqy
	 * @return 外发包向服务器的请求方法
	 */
	@Override
	public Method requestMethod() 
	{
		Method method = Method.GET;
		
		switch (mElementLitData.getToken()) 
		{
		case Token.TOKEN_AUTH: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_UPGRADE: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_COLUMN: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_COLUMNSUB: 
		{
			method = Method.GET;
			break;
		}
		case Token.TOKEN_VIDEO: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_HOTWORD: 
		{
			method = Method.GET;
			break;
		}
		case Token.TOKEN_SEARCH: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_APPLY: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_FEEAUTH: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_ACCOUNT: 
		{
			method = Method.POST;
			break;
		}
		case Token.TOKEN_RECHARGE: 
		{
			method = Method.POST;
			break;
		}
		default:
			break;
		}
		return method;
	}
	
	@Override
	public HTTPLINK requestLink() 
	{
		HTTPLINK method = HTTPLINK.HTTP;
		switch (mElementLitData.getToken()) 
		{
		case Token.TOKEN_RECHARGE:
		{
			method = HTTPLINK.HTTPS;
			break;
		}
		default:
			break;
		}
		return method;
	}

	/**
	 * 设置外发包请求服务器的连接超时，数据读取超时
	 * @author zhaoqy
	 * @return 请求超时的时间
	 */
	@Override
	public int getTimeout() 
	{
		return Constant.NETWORK_TIMEOUT;
	}

	/**
	 * 设置请求Http头信息
	 * @author zhaoqy
	 * @param httpConn Http请求连接
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	@Override
	public void setRequestProperty(HttpURLConnection httpConn) throws SocketTimeoutException, IOException 
	{
		return;
	}
	
	@Override
	public boolean fillData(OutputStream output, Context context) throws IOException 
	{
		return true;
	}

	@Override
	public String generateGeneralParam(Context context) 
	{
		return null;
	}
}
