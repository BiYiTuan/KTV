/**
 * @Title: Token.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: 标识ID (token)
 * @author: zhaoqy
 * @date: 2015-7-29 下午1:48:47
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

public class Token 
{
	public static final int TOKEN_AUTH = 1;      //应用鉴权        
	public static final int TOKEN_UPGRADE = 2;   //应用升级
	public static final int TOKEN_COLUMN = 3;    //栏目      
	public static final int TOKEN_COLUMNSUB = 4; //栏目下资源     
	public static final int TOKEN_HOTWORD = 5;   //热词          
	public static final int TOKEN_SEARCH = 6;    //搜索结果       
	public static final int TOKEN_VIDEO = 7;     //资源详情  
	public static final int TOKEN_APPLY = 8;     //申请试用
	public static final int TOKEN_FEEAUTH = 9;   //资源播放鉴权
	public static final int TOKEN_ACCOUNT = 10;  //用户账户信息
	public static final int TOKEN_RECHARGE = 11; //授权码充值
}
