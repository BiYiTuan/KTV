/**
 * @Title: GeneralParam.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 共有参数
 * @author: zhaoqy
 * @date: 2015-7-29 下午2:05:26
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import android.content.Context;

public class GeneralParam 
{
	/**
	 * 
	 * @Title: generateGeneralParam
	 * @Description: 生成共有参数
	 * @param context
	 * @return
	 * @return: String
	 */
	public static String generateGeneralParam(Context context) 
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("appid=");
		sb.append(Constant.APPID);
		sb.append("&");
		sb.append("terminal=");
		sb.append(Constant.TERMAL_TYPE);
		sb.append("&");
		sb.append("machineno=");
		sb.append(StbInfo.getMacAddress(context));
		sb.append("&");
		sb.append("lang=");
		sb.append(StbInfo.getPortLaug());  
		
		return sb.toString();
	}
}
