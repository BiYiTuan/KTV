/**
 * @Title: PullParse.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.xmlpull
 * @Description: pull解析器  注意utf-8有两种格式带bom和不带bom 现在解析是不带bom  注意属性引号必须是英文引号
 * @author: zhaoqy
 * @date: 2014-8-15 下午4:38:11
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.xmlpull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import com.sz.ead.app.ktv.dataprovider.datapacket.ElementListData;
import com.sz.ead.app.ktv.dataprovider.entity.AuthResult;
import com.sz.ead.app.ktv.dataprovider.entity.Column;
import com.sz.ead.app.ktv.dataprovider.entity.DictionaryItem;
import com.sz.ead.app.ktv.dataprovider.entity.DictionaryValueItem;
import com.sz.ead.app.ktv.dataprovider.entity.Drama;
import com.sz.ead.app.ktv.dataprovider.entity.Hotword;
import com.sz.ead.app.ktv.dataprovider.entity.Label;
import com.sz.ead.app.ktv.dataprovider.entity.UpgradeItem;
import com.sz.ead.app.ktv.dataprovider.entity.Video;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;

public class PullParse 
{
	/**
	 * 
	 * @Title: parseAppAuth
	 * @Description: 解析应用鉴权结果
	 * @param responseBytes: 服务器数据
	 * @param token: 列表id
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseAppAuth(ByteBuffer responseBytes, int token)
	{
		ElementListData emData = new ElementListData(token, 0, 0, "");
		AuthResult authItem = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
        InputStreamReader in = new InputStreamReader(bin);
        
		try
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();     
            XmlPullParser parser = pullFactory.newPullParser();
            parser.setInput(in);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) 
            {
            	switch (eventType) 
            	{
            	case XmlPullParser.START_DOCUMENT:
            	{
            		break;
            	}
            	case XmlPullParser.START_TAG:
            	{
            		if ("auth".equalsIgnoreCase(parser.getName()))
            		{
            			authItem = new AuthResult();
            		}
            		else if (authItem != null)
            		{
            			if ("result".equalsIgnoreCase(parser.getName())) 
						{
							authItem.setResult(Integer.parseInt(parser.nextText()));
						} 
						else if ("trialtime".equalsIgnoreCase(parser.getName())) 
						{
							authItem.setTrialTime(parser.nextText());
						}
            		}
            		break;
            	}
            	case XmlPullParser.END_TAG:
            	{
            		if ("auth".equalsIgnoreCase(parser.getName()) && authItem != null) 
            		{
						emData.getList().add(authItem);
						authItem = null;
					}
					break;
            	}
            	default:
            		break;
            	}
            	eventType = parser.next();  
            }
		}
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " parseAppAuth " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseAppUpgrade
	 * @Description: 解析应用升级
	 * @param responseBytes: 服务器数据
	 * @param token: 列表id
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseAppUpgrade(ByteBuffer responseBytes, int token)
	{
		ElementListData emData = new ElementListData(token, 0, 0, "");
		UpgradeItem upgradeItem = null;
		UpgradeItem tempItem = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
        InputStreamReader in = new InputStreamReader(bin);
        
		try
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();     
            XmlPullParser parser = pullFactory.newPullParser();
            parser.setInput(in);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) 
            {
            	switch (eventType) 
            	{
            	case XmlPullParser.START_DOCUMENT:
            	{
            		break;
            	}
            	case XmlPullParser.START_TAG:
            	{
            		if ("result".equalsIgnoreCase(parser.getName()))
            		{
            			tempItem = new UpgradeItem();
            			tempItem.setIsupgrade(new Integer(parser.getAttributeValue(null, "isupgrade")));
            		}
            		else if ("package".equalsIgnoreCase(parser.getName()))
            		{
            			upgradeItem = new UpgradeItem();
            			upgradeItem.setIsupgrade(tempItem.getIsupgrade());
            		}
            		else if (upgradeItem != null)
            		{
            			if ("version".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setVersion(parser.nextText());
                		}
            			else if ("md5".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setMD5(parser.nextText());
                		}
            			else if ("url".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setUrl(parser.nextText());
                		}
            			else if ("message".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setMessage(parser.nextText());
                		}
            			else if ("isforce".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setIsforce(new Integer(parser.nextText()));
                		}
            			else if ("upgradeway".equalsIgnoreCase(parser.getName()))
                		{
            				upgradeItem.setUpgradeway(new Integer(parser.nextText()));
                		}
            		}
            		break;
            	}
            	case XmlPullParser.END_TAG:
            	{
            		if ("package".equalsIgnoreCase(parser.getName()) && upgradeItem != null) 
            		{
						emData.getList().add(upgradeItem);
						upgradeItem = null;
					}
					break;
            	}
            	default:
            		break;
            	}
            	eventType = parser.next();  
            }
		}
		catch (Exception e) 
		{
			Logcat.e(FlagConstant.TAG, " parseAppUpgrade " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseColumn
	 * @Description: 解析栏目列表
	 * @param responseBytes
	 * @param token
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseColumn(ByteBuffer responseBytes, /*int token*/ ElementListData elementListData) 
	{
		//ElementListData emData = new ElementListData(token, 0, 0, "");
		ElementListData emData = new ElementListData(elementListData.getToken(), elementListData.getSize(), elementListData.getPage(),
				                                     elementListData.getArgument().get(0), elementListData.getArgument().get(1));
		Column column = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
		InputStreamReader in = new InputStreamReader(bin);
		
		try 
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullFactory.newPullParser();
			parser.setInput(in);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				switch (eventType) 
				{
				case XmlPullParser.START_DOCUMENT:
				{
					break;
				}
				case XmlPullParser.START_TAG:
				{
					if ("columns".equalsIgnoreCase(parser.getName())) 
					{
						emData.setPage(new Integer(parser.getAttributeValue(null, "currentpage")));
						emData.setSize(new Integer(parser.getAttributeValue(null, "num")));
						emData.setTotal(new Integer(parser.getAttributeValue(null, "total")));
					}
					else if ("column".equalsIgnoreCase(parser.getName())) 
					{
						column = new Column();
					}
					else if (column != null) 
					{
						if("columnid".equalsIgnoreCase(parser.getName()))
						{
							column.setColumnID(parser.nextText());
						}
						else if("columncode".equalsIgnoreCase(parser.getName()))
						{
							column.setColumnCode(parser.nextText());
						}
						else if("name".equalsIgnoreCase(parser.getName()))
						{
							column.setName(parser.nextText());
						}
						else if("haschildcolumn".equalsIgnoreCase(parser.getName()))
						{
							column.setHasChildColumn(Integer.parseInt(parser.nextText()));
						}
						else if("isresource".equalsIgnoreCase(parser.getName()))
						{
							column.setIsResource(Integer.parseInt(parser.nextText()));
						}
						else if("image".equalsIgnoreCase(parser.getName()))
						{
							column.setImage(parser.nextText());
						}
						else if("rescode".equalsIgnoreCase(parser.getName()))
						{
							column.setResCode(parser.nextText());
						}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if ("column".equalsIgnoreCase(parser.getName()) && column != null) 
					{
						emData.getList().add(column);
						column = null;
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Logcat.e(FlagConstant.TAG, " parseColumn " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseColumnSub
	 * @Description: 解析栏目下资源
	 * @param responseBytes
	 * @param token
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseColumnSub(ByteBuffer responseBytes, int token) 
	{
		ElementListData emData = new ElementListData(token, 0, 0, "");
		Video video = null;
		Label label = null;
		Drama drama = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
		InputStreamReader in = new InputStreamReader(bin);
		
		try 
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullFactory.newPullParser();
			parser.setInput(in);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				switch (eventType) 
				{
				case XmlPullParser.START_DOCUMENT:
				{
					break;
				}
				case XmlPullParser.START_TAG:
				{
					if ("resources".equalsIgnoreCase(parser.getName())) 
					{
						emData.setPage(new Integer(parser.getAttributeValue(null, "currentpage")));
						emData.setSize(new Integer(parser.getAttributeValue(null, "num")));
						emData.setTotal(new Integer(parser.getAttributeValue(null, "total")));
					}
					else if ("res".equalsIgnoreCase(parser.getName())) 
					{
						video = new Video();
					}
					else if ("drama".equalsIgnoreCase(parser.getName())) 
					{
						drama = new Drama();
					}
					else if (video != null) 
					{
						if(label != null)
						{
							if ("labelname".equalsIgnoreCase(parser.getName())) 
							{
								label.setLabelName(parser.nextText());
							} 
							else if ("value".equalsIgnoreCase(parser.getName())) 
							{
								label.setValue(parser.nextText());
							}
						}
						else if (drama != null) 
						{
							if ("dramaname".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaName(parser.nextText());
							} 
							else if ("dramacode".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaCode(parser.nextText());
							}
							else if ("number".equalsIgnoreCase(parser.getName())) 
							{
								drama.setNumber(parser.nextText());
							} 
							else if ("size".equalsIgnoreCase(parser.getName())) 
							{
								drama.setSize(parser.nextText());
							}
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								drama.setTime(parser.nextText());
							}
							else if ("rate".equalsIgnoreCase(parser.getName())) 
							{
								drama.setRate(parser.nextText());
							}
							else if ("format".equalsIgnoreCase(parser.getName())) 
							{
								drama.setFormat(parser.nextText());
							} 
							else if ("url".equalsIgnoreCase(parser.getName())) 
							{
								drama.setUrl(parser.nextText());
							}
							else if ("screenshots".equalsIgnoreCase(parser.getName())) 
							{
								drama.setScreenshots(parser.nextText());
							}
						}
						else
						{
							if ("rescode".equalsIgnoreCase(parser.getName())) 
							{
								video.setResCode(parser.nextText());
							} 
							else if ("name".equalsIgnoreCase(parser.getName())) 
							{
								video.setName(parser.nextText());
							} 
							else if ("type".equalsIgnoreCase(parser.getName())) 
							{
								video.setType(parser.nextText());
							}
							else if ("source".equalsIgnoreCase(parser.getName())) 
							{
								video.setSource(parser.nextText());
							}
							else if ("tvurl".equalsIgnoreCase(parser.getName())) 
							{
								video.setUrl(parser.nextText());
							}
							else if ("image".equalsIgnoreCase(parser.getName())) 
							{
								video.setImage(parser.getName());
							} 
							else if ("bigpic".equalsIgnoreCase(parser.getName())) 
							{
								video.setBigpic(parser.nextText());
							}
							else if ("director".equalsIgnoreCase(parser.getName())) 
							{
								video.setDirector(parser.nextText());
							} 
							else if ("actor".equalsIgnoreCase(parser.getName())) 
							{
								video.setActor(parser.nextText());
							} 
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								video.setTime(parser.nextText());
							}
							else if ("summary".equalsIgnoreCase(parser.getName())) 
							{
								video.setSummary(parser.nextText());
							}
							else if ("score".equalsIgnoreCase(parser.getName())) 
							{
								video.setScore(parser.nextText());
							}
							else if ("hot".equalsIgnoreCase(parser.getName())) 
							{
								video.setHot(parser.nextText());
							} 
							else if ("totaldramas".equalsIgnoreCase(parser.getName())) 
							{
								video.setTotaldramas(parser.nextText());
							} 
							else if ("ratings".equalsIgnoreCase(parser.getName())) 
							{
								video.setRatings(parser.nextText());
							} 
							else if ("label".equalsIgnoreCase(parser.getName())) 
							{
								label = new Label();
							}
						}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if ("res".equalsIgnoreCase(parser.getName()) && video != null) 
					{
						emData.getList().add(video);
						video = null;
					} 
					else if ("label".equalsIgnoreCase(parser.getName()) && video != null && label != null) 
					{
						video.getLabelList().add(label);
						label = null;
					}
					else if("drama".equalsIgnoreCase(parser.getName()) && video != null && drama != null)
            		{
            			video.getDramaList().add(drama);
						drama = null;
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Logcat.e(FlagConstant.TAG, " parseColumnSub " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseVideo
	 * @Description: 解析资源详情
	 * @param responseBytes
	 * @param token
	 * @return
	 * @return: ElementListData
	 */
	public static ElementListData parseVideo(ByteBuffer responseBytes, int token) 
	{
		ElementListData emData = new ElementListData(token, 0, 0, "");
		Video video = null;
		Label label = null;
		Drama drama = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
		InputStreamReader in = new InputStreamReader(bin);
		
		try 
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullFactory.newPullParser();
			parser.setInput(in);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				switch (eventType) 
				{
				case XmlPullParser.START_DOCUMENT:
				{
					break;
				}
				case XmlPullParser.START_TAG:
				{
					if ("resources".equalsIgnoreCase(parser.getName()))
            		{
						video = new Video();
					}
					else if("drama".equalsIgnoreCase(parser.getName()))
    				{
	                	drama = new Drama();
	                }
					else if (video != null)
					{
						if (label != null)
						{
							if ("labelname".equalsIgnoreCase(parser.getName())) 
							{
								label.setLabelName(parser.nextText());
							} 
							else if ("value".equalsIgnoreCase(parser.getName())) 
							{
								label.setValue(parser.nextText());
							}
						}
						else if (drama != null) 
						{
							if ("dramaname".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaName(parser.nextText());
							} 
							else if ("dramacode".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaCode(parser.nextText());
							}
							else if ("number".equalsIgnoreCase(parser.getName())) 
							{
								drama.setNumber(parser.nextText());
							} 
							else if ("size".equalsIgnoreCase(parser.getName())) 
							{
								drama.setSize(parser.nextText());
							}
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								drama.setTime(parser.nextText());
							}
							else if ("rate".equalsIgnoreCase(parser.getName())) 
							{
								drama.setRate(parser.nextText());
							}
							else if ("format".equalsIgnoreCase(parser.getName())) 
							{
								drama.setFormat(parser.nextText());
							} 
							else if ("url".equalsIgnoreCase(parser.getName())) 
							{
								drama.setUrl(parser.nextText());
							}
							else if ("screenshots".equalsIgnoreCase(parser.getName())) 
							{
								drama.setScreenshots(parser.nextText());
							}
						}
						else
            			{
							if ("rescode".equalsIgnoreCase(parser.getName())) 
							{
								video.setResCode(parser.nextText());
							} 
							else if ("name".equalsIgnoreCase(parser.getName())) 
							{
								video.setName(parser.nextText());
							} 
							else if ("type".equalsIgnoreCase(parser.getName())) 
							{
								video.setType(parser.nextText());
							}
							else if ("source".equalsIgnoreCase(parser.getName())) 
							{
								video.setSource(parser.nextText());
							}
							else if ("tvurl".equalsIgnoreCase(parser.getName())) 
							{
								video.setUrl(parser.nextText());
							}
							else if ("image".equalsIgnoreCase(parser.getName())) 
							{
								video.setImage(parser.getName());
							} 
							else if ("bigpic".equalsIgnoreCase(parser.getName())) 
							{
								video.setBigpic(parser.nextText());
							}
							else if ("director".equalsIgnoreCase(parser.getName())) 
							{
								video.setDirector(parser.nextText());
							} 
							else if ("actor".equalsIgnoreCase(parser.getName())) 
							{
								video.setActor(parser.nextText());
							} 
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								video.setTime(parser.nextText());
							}
							else if ("summary".equalsIgnoreCase(parser.getName())) 
							{
								video.setSummary(parser.nextText());
							}
							else if ("score".equalsIgnoreCase(parser.getName())) 
							{
								video.setScore(parser.nextText());
							}
							else if ("hot".equalsIgnoreCase(parser.getName())) 
							{
								video.setHot(parser.nextText());
							} 
							else if ("totaldramas".equalsIgnoreCase(parser.getName())) 
							{
								video.setTotaldramas(parser.nextText());
							} 
							else if ("ratings".equalsIgnoreCase(parser.getName())) 
							{
								video.setRatings(parser.nextText());
							} 
            				else if("label".equalsIgnoreCase(parser.getName()))
            				{
            					label = new Label(); 
			                }
            			}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if ("resources".equalsIgnoreCase(parser.getName()) && video != null) 
            		{
						emData.getList().add(video);
						video = null;
					}
					else if("label".equalsIgnoreCase(parser.getName()) && video != null && label != null)
            		{
						video.getLabelList().add(label);
						label = null;
					}
            		else if("drama".equalsIgnoreCase(parser.getName()) && video != null && drama != null)
            		{
            			video.getDramaList().add(drama);
						drama = null;
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			Logcat.e(FlagConstant.TAG, " parseVideo " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseHotword
	 * @Description: 解析热词
	 * @param responseBytes
	 * @param token
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseHotword(ByteBuffer responseBytes, int token) 
	{
		ElementListData emData = new ElementListData(token, 0, 0, "");
		Hotword hotword = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
		InputStreamReader in = new InputStreamReader(bin);
		
		try 
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullFactory.newPullParser();
			parser.setInput(in);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				switch (eventType) 
				{
				case XmlPullParser.START_DOCUMENT:
				{
					break;
				}
				case XmlPullParser.START_TAG:
				{
					if ("result".equalsIgnoreCase(parser.getName())) 
					{
						emData.setTotal(new Integer(parser.getAttributeValue(null, "total")));
					}
					else if ("hotword".equalsIgnoreCase(parser.getName())) 
					{
						hotword = new Hotword();
					} 
					else if (hotword != null) 
					{
						if ("name".equalsIgnoreCase(parser.getName())) 
						{
							hotword.setName(parser.nextText());
						} 
						else if ("frequency".equalsIgnoreCase(parser.getName())) 
						{
							hotword.setFrequency(Integer.parseInt(parser.nextText()));
						}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if ("hotword".equalsIgnoreCase(parser.getName()) && hotword != null) 
					{
						emData.getList().add(hotword);
						hotword = null;
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Logcat.e(FlagConstant.TAG, " parseHotword " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseSearchVideo
	 * @Description: 解析搜索结果
	 * @param responseBytes
	 * @param token
	 * @return
	 * @return: ElementListData
	 */
	@SuppressLint("UseValueOf")
	public static ElementListData parseSearchVideo(ByteBuffer responseBytes, /*int token*/ElementListData elementListData) 
	{
		ElementListData emData = new ElementListData(elementListData.getToken(), elementListData.getSize(), elementListData.getPage(), elementListData.getArgument().get(0));
		Video video = null;
		Label label = null;
		Drama drama = null;
		ByteArrayInputStream bin = new ByteArrayInputStream(responseBytes.array());
		InputStreamReader in = new InputStreamReader(bin);
		
		try 
		{
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullFactory.newPullParser();
			parser.setInput(in);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				switch (eventType) 
				{
				case XmlPullParser.START_DOCUMENT:
				{
					break;
				}
				case XmlPullParser.START_TAG:
				{
					if ("resources".equalsIgnoreCase(parser.getName())) 
					{
						emData.setPage(new Integer(parser.getAttributeValue(null, "currentpage")));
						emData.setSize(new Integer(parser.getAttributeValue(null, "num")));
						emData.setTotal(new Integer(parser.getAttributeValue(null, "total")));
					} 
					else if ("res".equalsIgnoreCase(parser.getName())) 
					{
						video = new Video();
					}
					else if ("drama".equalsIgnoreCase(parser.getName())) 
					{
						drama = new Drama();
					}
					else if (video != null) 
					{
						if(label != null)
						{
							if ("labelname".equalsIgnoreCase(parser.getName())) 
							{
								label.setLabelName(parser.nextText());
							} 
							else if ("value".equalsIgnoreCase(parser.getName())) 
							{
								label.setValue(parser.nextText());
							}
						}
						else if (drama != null) 
						{
							if ("dramaname".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaName(parser.nextText());
							} 
							else if ("dramacode".equalsIgnoreCase(parser.getName())) 
							{
								drama.setDramaCode(parser.nextText());
							}
							else if ("number".equalsIgnoreCase(parser.getName())) 
							{
								drama.setNumber(parser.nextText());
							} 
							else if ("size".equalsIgnoreCase(parser.getName())) 
							{
								drama.setSize(parser.nextText());
							}
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								drama.setTime(parser.nextText());
							}
							else if ("rate".equalsIgnoreCase(parser.getName())) 
							{
								drama.setRate(parser.nextText());
							}
							else if ("format".equalsIgnoreCase(parser.getName())) 
							{
								drama.setFormat(parser.nextText());
							} 
							else if ("url".equalsIgnoreCase(parser.getName())) 
							{
								drama.setUrl(parser.nextText());
							}
							else if ("screenshots".equalsIgnoreCase(parser.getName())) 
							{
								drama.setScreenshots(parser.nextText());
							}
						}
						else
						{
							if ("rescode".equalsIgnoreCase(parser.getName())) 
							{
								video.setResCode(parser.nextText());
							} 
							else if ("name".equalsIgnoreCase(parser.getName())) 
							{
								video.setName(parser.nextText());
							} 
							else if ("type".equalsIgnoreCase(parser.getName())) 
							{
								video.setType(parser.nextText());
							}
							else if ("source".equalsIgnoreCase(parser.getName())) 
							{
								video.setSource(parser.nextText());
							}
							else if ("tvurl".equalsIgnoreCase(parser.getName())) 
							{
								video.setUrl(parser.nextText());
							}
							else if ("image".equalsIgnoreCase(parser.getName())) 
							{
								video.setImage(parser.getName());
							} 
							else if ("bigpic".equalsIgnoreCase(parser.getName())) 
							{
								video.setBigpic(parser.nextText());
							}
							else if ("director".equalsIgnoreCase(parser.getName())) 
							{
								video.setDirector(parser.nextText());
							} 
							else if ("actor".equalsIgnoreCase(parser.getName())) 
							{
								video.setActor(parser.nextText());
							} 
							else if ("time".equalsIgnoreCase(parser.getName())) 
							{
								video.setTime(parser.nextText());
							}
							else if ("summary".equalsIgnoreCase(parser.getName())) 
							{
								video.setSummary(parser.nextText());
							}
							else if ("score".equalsIgnoreCase(parser.getName())) 
							{
								video.setScore(parser.nextText());
							}
							else if ("hot".equalsIgnoreCase(parser.getName())) 
							{
								video.setHot(parser.nextText());
							} 
							else if ("totaldramas".equalsIgnoreCase(parser.getName())) 
							{
								video.setTotaldramas(parser.nextText());
							} 
							else if ("ratings".equalsIgnoreCase(parser.getName())) 
							{
								video.setRatings(parser.nextText());
							} 
							else if ("label".equalsIgnoreCase(parser.getName())) 
							{
								label = new Label();
							}
						}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if ("res".equalsIgnoreCase(parser.getName()) && video != null) 
					{
						emData.getList().add(video);
						video = null;
					} 
					else if ("label".equalsIgnoreCase(parser.getName()) && video != null && label != null) 
					{
						video.getLabelList().add(label);
						label = null;
					}
					else if("drama".equalsIgnoreCase(parser.getName()) && video != null && drama != null)
            		{
            			video.getDramaList().add(drama);
						drama = null;
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Logcat.e(FlagConstant.TAG, " parseSearchVideo " + e.toString());
		}
		return emData;
	}
	
	/**
	 * 
	 * @Title: parseDictonary
	 * @Description: 解析应用词典
	 * @param context
	 * @return
	 * @throws IOException
	 * @return: ArrayList<DictionaryItem>
	 */
	@SuppressLint("UseValueOf")
	public static ArrayList<DictionaryItem> parseDictionary(Context context) throws IOException
	{
		AssetManager am = null;
		InputStream is = null;
		ArrayList<DictionaryItem> dictorylist = new ArrayList<DictionaryItem>();
		DictionaryItem dictionaryItem = null;
		DictionaryValueItem dictionaryValueItem = null;
		
		try 
		{		
			am = context.getAssets();
			is = am.open("dictionary.xml");
			
			XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlParser = pullFactory.newPullParser();
			xmlParser.setInput(is, "UTF-8");

			int evtType = xmlParser.getEventType();
			while (evtType != XmlPullParser.END_DOCUMENT) 
			{
				String tag = xmlParser.getName();
				switch (evtType) 
				{
				case XmlPullParser.START_TAG: 
				{
					if (tag.equalsIgnoreCase("item")) 
					{
						dictionaryItem = new DictionaryItem();
					} 
					else if (dictionaryItem != null) 
					{
						if (tag.equalsIgnoreCase("status")) 
						{
							dictionaryItem.setStatus(new Integer(xmlParser.nextText()));
						}
						
						if (tag.equalsIgnoreCase("value")) 
						{
							dictionaryValueItem = new DictionaryValueItem();
							dictionaryValueItem.setLanguage(xmlParser.getAttributeValue(null, "lang"));
							dictionaryValueItem.setContent(xmlParser.nextText());
							dictionaryItem.getContentList().add(dictionaryValueItem);
							dictionaryValueItem = null;
						}
					}
					break;
				}
				case XmlPullParser.END_TAG: 
				{
					if (tag.equalsIgnoreCase("item") && dictionaryItem != null) 
					{
						dictorylist.add(dictionaryItem);
						dictionaryItem = null;
					}
					break;
				}
				default:
            		break;	
				}
				evtType = xmlParser.next();
			}
		} 
		catch (XmlPullParserException e) 
		{
			Logcat.e(FlagConstant.TAG, " parseDictionary " + e.toString());
			e.printStackTrace();
		} 
		finally 
		{
			if (is != null)
			{
				is.close();
			}
		}

		return dictorylist;
	}
}
