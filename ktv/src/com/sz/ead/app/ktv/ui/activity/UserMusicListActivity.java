/**
 * @Title: UserMusicListActivity.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.activity
 * @Description: 已点歌曲
 * @author: zhaoqy
 * @date: 2015-7-31 下午5:02:44
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
import com.sz.ead.app.ktv.ui.fragment.UmSelectedFragment;
import com.sz.ead.app.ktv.ui.fragment.UmSungFragment;
import com.sz.ead.app.ktv.ui.widget.NoScrollViewPager;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;

public class UserMusicListActivity extends BaseActivity implements OnCheckedChangeListener, OnFocusChangeListener
{
	private UmSelectedFragment mUmSelectedFragment;
	private UmSungFragment     mUmSungFragment;
	private NoScrollViewPager  mPager;
	private RadioGroup         mRadioGroup;
	private RadioButton        mSelected;
	private RadioButton        mSung;
	private int                mFocusIndex = 0; 
	private int                mType = 1; //1-已点歌曲; 2-已唱歌曲

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usermusiclist);
		
		findViews();
		initFragment();
		setListeners();
	}

	private void findViews() 
	{
		mPager = (NoScrollViewPager) findViewById(R.id.id_umlist_viewpager);
		mRadioGroup = (RadioGroup) findViewById(R.id.id_umlist_radiogroup);
		mSelected = (RadioButton) findViewById(R.id.id_umlist_selected);
		mSung = (RadioButton) findViewById(R.id.id_umlist_sung);
	}
	
	private void initFragment()
	{
		mPager.setAdapter(new UserMusicPagerAdapter(getSupportFragmentManager()));
		mPager.setOffscreenPageLimit(1);
	}
	
	private void setListeners() 
	{
		mSelected.setOnFocusChangeListener(this);
		mSung.setOnFocusChangeListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		switch (v.getId()) 
		{
		case R.id.id_umlist_selected:
		{
			if(hasFocus)
			{
				Logcat.d(FlagConstant.TAG, " onFocusChange: id_umlist_selected ");
				mType = 1;
				mSelected.setChecked(true);
			}
			break;
		}
		case R.id.id_umlist_sung:
		{
			if(hasFocus)
			{
				Logcat.d(FlagConstant.TAG, " onFocusChange: id_umlist_sung ");
				mType = 2;
				mSung.setChecked(true);
			}
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.id_umlist_selected:
		{
			Logcat.d(FlagConstant.TAG, " onCheckedChanged: id_umlist_selected ");
			mPager.setCurrentItem(0, false);
			mUmSelectedFragment.resetUmSelectedFragment();
			break;
		}
		case R.id.id_umlist_sung:
		{
			Logcat.d(FlagConstant.TAG, " onCheckedChanged: id_umlist_sung ");
			mPager.setCurrentItem(1, false);
			mUmSungFragment.resetUmSungFragment();
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
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
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
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
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
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
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
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
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
		case 0:
		{
			break;
		}
		case 1:
		{
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			if (!nRet)
			{
				nRet = true;
				mSelected.setSelected(false);
				mSelected.requestFocus();
				mFocusIndex = 0;
			}
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
			if (!nRet)
			{
				nRet = true;
				mSung.setSelected(false);
				mSung.requestFocus();
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
				mSelected.setSelected(true);
				mFocusIndex = 1;
				mUmSelectedFragment.initUmSelectedFragment();
			}
			else if (mType == 2)
			{
				mSung.setSelected(true);
				mFocusIndex = 2;
				mUmSungFragment.initUmSungFragment();
			}
			nRet = true;
			break;
		}
		case 1:
		{
			nRet = mUmSelectedFragment.dispatchKeyEvent(event);
			break;
		}	
		case 2:
		{
			nRet = mUmSungFragment.dispatchKeyEvent(event);
			break;
		}
		default:
			break;
		}
		return nRet;
	}
	
	public class UserMusicPagerAdapter extends FragmentPagerAdapter
	{
	    public UserMusicPagerAdapter(FragmentManager fm) 
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
				mUmSelectedFragment = new UmSelectedFragment();
				return mUmSelectedFragment;
			}
			case 1:
			{
				mUmSungFragment = new UmSungFragment();
				return mUmSungFragment;
			}
			default:
				return null;
			}
		}  
	  
	    @Override  
	    public int getCount() 
	    {
	    	return 2; 
	    }
	}
}
