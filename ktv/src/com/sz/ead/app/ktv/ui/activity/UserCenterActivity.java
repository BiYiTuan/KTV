/**
 * @Title: UserCenterActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 个人中心
 * @author: zhaoqy
 * @date: 2015-7-31 下午2:05:47
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.ui.fragment.UCAboutFragment;
import com.sz.ead.app.ktv.ui.fragment.UCCollectFragment;
import com.sz.ead.app.ktv.ui.fragment.UCSettingFragment;
import com.sz.ead.app.ktv.ui.widget.NoScrollViewPager;

public class UserCenterActivity extends BaseActivity implements OnCheckedChangeListener, OnFocusChangeListener
{
	private UCAboutFragment   mUCAboutFragment;
	private UCCollectFragment mUCCollectFragment;
	private UCSettingFragment mUCSettingFragment;
	private NoScrollViewPager mPager;
	private RadioGroup        mRadioGroup;
	private RadioButton       mCollect;
	private RadioButton       mSetting;
	private RadioButton       mAbout;
	private int               mFocusIndex = 0; 
	private int               mType = 1; //1-已点歌曲; 2-已唱歌曲

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		
		findViews();
		initFragment();
		setListeners();
	}

	private void findViews() 
	{
		mPager = (NoScrollViewPager) findViewById(R.id.id_usercenter_viewpager);
		mRadioGroup = (RadioGroup) findViewById(R.id.id_usercenter_radiogroup);
		mCollect = (RadioButton) findViewById(R.id.id_usercenter_collect);
		mSetting = (RadioButton) findViewById(R.id.id_usercenter_setting);
		mAbout = (RadioButton) findViewById(R.id.id_usercenter_about);
	}
	
	private void initFragment()
	{
		mPager.setAdapter(new UserCenterPagerAdapter(getSupportFragmentManager()));
		mPager.setOffscreenPageLimit(2);
	}
	
	private void setListeners() 
	{
		mCollect.setOnFocusChangeListener(this);
		mSetting.setOnFocusChangeListener(this);
		mAbout.setOnFocusChangeListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if(hasFocus)
		{
			switch (v.getId()) 
			{
			case R.id.id_usercenter_collect:
			{
				mType = 1;
				mCollect.setChecked(true);
				break;
			}
			case R.id.id_usercenter_setting:
			{
				mType = 2;
				mSetting.setChecked(true);
				break;
			}	
			case R.id.id_usercenter_about:
			{
				mType = 3;
				mAbout.setChecked(true);
				break;
			}
			default:
				break;
			}
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.id_usercenter_collect:
		{
			mPager.setCurrentItem(0, false);
			break;
		}
		case R.id.id_usercenter_setting:
		{
			mPager.setCurrentItem(1, false);
			break;
		}	
		case R.id.id_usercenter_about:
		{
			mPager.setCurrentItem(2, false);
			break;
		}
		default:
			break;
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		boolean nRet = false;
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			switch (event.getKeyCode()) 
			{
			case KeyEvent.KEYCODE_DPAD_UP:
			{
				nRet = doKeyUp(event);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_DOWN:
			{
				nRet = doKeyDown(event);
				break;
			}
			case KeyEvent.KEYCODE_PAGE_UP:
			{
				nRet = doKeyPageUp(event);
				break;
			}
			case KeyEvent.KEYCODE_PAGE_DOWN:
			{
				nRet = doKeyPageDown(event);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_LEFT:
			{
				nRet = doKeyLeft(event);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_RIGHT:
			{
				nRet = doKeyRight(event);
				break;
			}
			default:
				break;
			}
		}
		
		if (nRet)
		{
			return nRet;
		}
		else 
		{
			return super.dispatchKeyEvent(event);  
		}
	}

	private boolean doKeyUp(KeyEvent event) 
	{
		boolean nRet = false; 
		
		switch (mFocusIndex) 
		{
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			break;
		}	
		default:
			break;
		}
		return nRet;
	}

	private boolean doKeyDown(KeyEvent event) 
	{
		boolean nRet = false; 
		
		switch (mFocusIndex) 
		{
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			break;
		}	
		default:
			break;
		}
		return nRet;
	}
	
	private boolean doKeyPageUp(KeyEvent event) 
	{
		boolean nRet = false; 
		
		switch (mFocusIndex) 
		{
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			break;
		}	
		default:
			break;
		}
		return nRet;
	}
	
	private boolean doKeyPageDown(KeyEvent event) 
	{
		boolean nRet = false; 
		
		switch (mFocusIndex) 
		{
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			break;
		}	
		default:
			break;
		}
		return nRet;
	}

	private boolean doKeyLeft(KeyEvent event) 
	{
		boolean nRet = false;
		
		switch (mFocusIndex) 
		{
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			if (!nRet)
			{
				nRet = true;
				mCollect.setSelected(false);
				mCollect.requestFocus();
				mFocusIndex = 0;
			}
			break;
		}	
		default:
			break;
		}
		return nRet;
	}

	private boolean doKeyRight(KeyEvent event) 
	{
		boolean nRet = false;
		
		switch (mFocusIndex) 
		{
		case 0:
		{
			if (mType == 1)
			{
				mCollect.setSelected(true);
				mFocusIndex = 1;
				mUCCollectFragment.initUCCollectFragment();
			}
			nRet = true;
			break;
		}
		case 1:
		{
			nRet = mUCCollectFragment.dispatchKeyEvent(event);
			break;
		}	
		default:
			break;
		}
		return nRet;
	}
	
	public class UserCenterPagerAdapter extends FragmentPagerAdapter
	{
	    public UserCenterPagerAdapter(FragmentManager fm) 
	    {  
	        super(fm);  
	    }  

		@Override
		public Fragment getItem(int position) 
		{
			switch (position)
			{
			case 0:
			{
				mUCCollectFragment = new UCCollectFragment();
				return mUCCollectFragment;
			}
			case 1:
			{
				mUCSettingFragment = new UCSettingFragment();
				return mUCSettingFragment;
			}
			case 2:
			{
				mUCAboutFragment = new UCAboutFragment();
				return mUCAboutFragment;
			}
			default:
				return null;
			}
		}  
	  
	    @Override  
	    public int getCount() 
	    {
	    	return 3; 
	    }
	}
}
