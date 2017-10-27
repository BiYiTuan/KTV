/**
 * @Title: UserCenterAboutFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: 关于
 * @author: zhaoqy
 * @date: 2015-7-31 下午2:33:28
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.fragment;

import java.io.InputStream;
import org.apache.http.util.EncodingUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;

public class UCAboutFragment extends BaseFragment
{
	private TextView mAbout; //关于信息
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_usercenter_about, container, false);
		findViews(view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	private void findViews(View view) 
	{
		mAbout = (TextView) view.findViewById(R.id.id_about_text);
		mAbout.setText(getFromAssets("about.txt"));
	}
	
	/**
	 * 
	 * @Title: getFromAssets
	 * @Description: 读取本地文件信息(该方法可以换段落)
	 * @param fileName: 本地文件名
	 * @return
	 * @return: String
	 */
	public String getFromAssets(String fileName)
	{
		String result = "";
		try 
		{
			InputStream in = getResources().getAssets().open(fileName);
			int lenght = in.available();       //获取文件的字节数
			byte[]  buffer = new byte[lenght]; //创建byte数组
		
			in.read(buffer);  //将文件中的数据读到byte数组中
			result = EncodingUtils.getString(buffer, "UTF-8");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
