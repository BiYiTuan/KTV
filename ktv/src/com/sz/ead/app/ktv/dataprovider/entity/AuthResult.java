/**
 * @Title: AuthResult.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.entity.ktv
 * @Description: 鉴权结果
 * @author: zhaoqy
 * @date: 2015-7-29 上午10:20:26
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

public class AuthResult 
{
	private int    mResult;    //鉴权结果：-1-系统错误，0-成功，1-区域限制, 2-终端限制, 3-黑名单等等
	private String mTrialTime; //成功时返回试用时间,0：表示没有试用期（单位：天）
	
	public int getResult() 
	{
		return mResult;
	}
	
	public void setResult(int result) 
	{
		mResult = result;
	}
	
	public String getTrialTime() 
	{
		return mTrialTime;
	}
	
	public void setTrialTime(String trialtime) 
	{
		mTrialTime = trialtime;
	}
}
