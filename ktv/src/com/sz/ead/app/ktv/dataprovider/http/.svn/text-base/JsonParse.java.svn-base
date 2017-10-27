/**
 * @Title: JsonParse.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.http
 * @Description: Json解析
 * @author: zhaoqy
 * @date: 2015-7-31 上午9:54:10
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.http;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import com.sz.ead.app.ktv.dataprovider.entity.Label;

public class JsonParse 
{
	public static ArrayList<Label> parseJson(String jsonStr)
	{
		ArrayList<Label> labelList = new ArrayList<Label>();
		try 
		{
			JSONObject obj = new JSONObject(jsonStr);
			Iterator<?> it = obj.keys();
			Label label = null;
			while(it.hasNext())
			{
				//遍历JSONObject
				label = new Label();
				label.setLabelName((String) it.next().toString());
				label.setValue(obj.getString(label.getLabelName()));
				labelList.add(label);
            }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return labelList;
	}
	
	public static String buildJson(ArrayList<Label> labels)
	{
		String json = null;
		try 
		{
			JSONObject obj = new JSONObject();
			int size = labels.size();
			for (int i = 0; i < size; i++) 
			{
				obj.put(labels.get(i).getLabelName(), labels.get(i).getValue());
			}
			json = obj.toString();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return json;
	}
}
