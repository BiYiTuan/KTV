/**
 * @Title: Indicator.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.widget
 * @Description:  页码（页码总数大于6时, 用1/6这种格式）
 * @author: zhaoqy
 * @date: 2015-8-10 下午9:59:01
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.widget;

import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.utils.Common;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Indicator extends RelativeLayout
{
	public static final int TOP = 670;   //高度
	public static final int SPACE = 20;  //间距
	public static final int WIDTH = 22;  //宽度
	public static final int HEIGHT = 22; //高度
	public static final int MAX = 5;     //最多显示个数
	private ImageView mIndicator[] = new ImageView[MAX]; 
	private Context   mContext;          //上下文
	private TextView  mPage;             //数字表示页码
	private int       mTotNum = 0;       //总页数
	private int       mInit_x = 0;       //第一张图片的x坐标
	private int       mIndicator_x = 0;  //每张图片的x坐标
	private int       mTop = TOP;        //高度
	
	public Indicator(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init()
	{
		inflate(mContext, R.layout.view_indicator, this);
		mIndicator[0] = (ImageView) findViewById(R.id.id_indicator1);
		mIndicator[1] = (ImageView) findViewById(R.id.id_indicator2);
		mIndicator[2] = (ImageView) findViewById(R.id.id_indicator3);
		mIndicator[3] = (ImageView) findViewById(R.id.id_indicator4);
		mIndicator[4] = (ImageView) findViewById(R.id.id_indicator5);
		mPage = (TextView) findViewById(R.id.id_indicator_page);
	}
	
	/**
	 * 
	 * @Title: setDotTop
	 * @Description: 设置高度(绝对高度)
	 * @param top
	 * @return: void
	 */
	public void setIndicatorTop(int top)
	{
		mTop = top;
	}
	
	/**
	 * 
	 * @Title: setDotTotalNumber
	 * @Description: 设置总页数(对外接口)
	 * @param number
	 * @return: void
	 */
	public void setIndicatorTotalNumber(int number)
	{
		mTotNum = number;
		setIndicatorPosition();
		
		if (mTotNum > MAX)
		{
			for(int i=0; i<MAX; i++)
			{
				mIndicator[i].setVisibility(View.GONE);
			}
			mPage.setVisibility(View.VISIBLE);
		}
		else
		{
			mPage.setVisibility(View.GONE);
			
			for(int i=0; i<MAX; i++)
			{
				if (number == 1)
				{
					mIndicator[i].setVisibility(View.GONE);
				}
				else
				{
					if (i < number)
					{
						mIndicator[i].setVisibility(View.VISIBLE);
					}
					else
					{
						mIndicator[i].setVisibility(View.GONE);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: setDotCurrentNumber
	 * @Description: 设置当前页码(对外接口)
	 * @param number
	 * @return: void
	 */
	public void setIndicatorCurrentNumber(int number)
	{
		if (mTotNum > MAX)
		{
			mPage.setText(number + "/" + mTotNum);
		}
		else
		{
			if (mTotNum > 1 && mTotNum <= MAX)
			{
				for (int i=0; i<mTotNum; i++)
				{
					if (i == number-1)
					{
						mIndicator[i].setImageResource(R.drawable.page_indicator_focused);
					}
					else
					{
						mIndicator[i].setImageResource(R.drawable.page_indicator_other);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: setDotPosition
	 * @Description: 重新设置控件坐标
	 * @return: void
	 */
	private void setIndicatorPosition()
	{
		if (mTotNum > 0)
		{
			if (mTotNum <= MAX)
			{
				if (mTotNum > 1)
				{
					mInit_x = (int)(1280 - WIDTH*mTotNum - SPACE*(mTotNum-1))/2;
					
					for (int i=0; i<mTotNum; i++)
					{
						mIndicator_x = mInit_x + i*(WIDTH + SPACE);
						Common.setCoordinateOfView(mIndicator[i], mIndicator_x, mTop, WIDTH, HEIGHT);
					}
				}
			}
			else
			{
				mInit_x = 0;
				Common.setCoordinateOfView2(mPage, mInit_x, mTop);
			}
		}
	}
}
