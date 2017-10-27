/**
 * @Title: UserCenterSettingFragment.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.fragment
 * @Description: 设置
 * @author: zhaoqy
 * @date: 2015-7-31 下午2:49:07
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.utils.AppInfo;
import com.sz.ead.app.ktv.utils.Constant;

public class UCSettingFragment extends BaseFragment 
{
	private TextView mVersion;
	private TextView mLastest;
	
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
		View view = inflater.inflate(R.layout.fragment_usercenter_setting, container, false);
		findViews(view);
		setVersion();
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
		mVersion = (TextView) view.findViewById(R.id.id_setting_version);
		mLastest = (TextView) view.findViewById(R.id.id_setting_lastest);
	}
	
	private void setVersion()
	{
		//mVersion.setText(String.format(getString(R.string.usercenter_setting_version), AppInfo.getVersionName(getActivity())));
		mVersion.setText(Constant.APP_VERSION);
		mLastest.setVisibility(View.VISIBLE);
	}
}
