/**
 * @Title: ReflectUtils.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 映射Util
 * @author: zhaoqy
 * @date: 2015-8-5 下午9:12:31
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import java.lang.reflect.Method;
import android.widget.VideoView;

public class ReflectUtils 
{
	/**
	 * 
	 * @Title: getMethod
	 * @Description: 获取类的特定的public方法
	 * @param clazz
	 * @param name
	 * @param parameterTypes
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
	{
		Method method = null;
		try 
		{
			method = clazz.getMethod(name, parameterTypes);
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		return method;
	}
	
	/**
	 * 
	 * @Title: existSetParameterMethod
	 * @Description: 是否新的sdkVideoView带setParameter函数
	 * @return
	 * @return: boolean
	 */
	public static boolean existSetParameterMethod()
	{
		boolean nRet = false;
		if(ReflectUtils.getMethod(VideoView.class, "setParameter",int.class)!=null)
		{
			nRet = true;
		}
		return nRet;
	}
}
