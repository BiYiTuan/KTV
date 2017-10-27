/**
 * @Title: DictionaryValueItem.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.entity
 * @Description: 应用词典状态值item
 * @author: zhaoqy
 * @date: 2015-6-8 下午5:33:58
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.entity;

public class DictionaryValueItem 
{
	private String mLanguage; //语言
	private String mContent;  //内容
	
	public void setLanguage(String language)
	{
		mLanguage = language;
	}
	
	public String getLanguage()
	{
		return mLanguage;
	}
	
	public void setContent(String content)
	{
		mContent = content;
	}
	
	public String getContent()
	{
		return mContent;
	}
}
