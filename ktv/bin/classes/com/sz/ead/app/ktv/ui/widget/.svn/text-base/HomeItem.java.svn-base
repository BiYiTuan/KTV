/**
 * @Title: ScaleRelativeLayout.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.widget
 * @Description: 自定义RelativeLayout
 * @author: zhaoqy
 * @date: 2015-8-13 下午2:33:18
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.utils.ImageLoaderUtils;

public class HomeItem extends RelativeLayout implements ViewAnimation
{
	private ScaleAnimation item_zoom_in;
	private ScaleAnimation item_zoom_out;
	private Drawable mFocus;  
	private Rect mBound;  
    private Rect mRect;
    private int shadowPaddingLeft;
    private int shadowPaddingRight;
    private int shadowPaddingTop;
    private int shadowPaddingBottom;
    private Context   mContext;   //上下文
    private ImageView mImage;      //icon
	private TextView  mName;      //名称
	private Drawable  mDefaultImage;  
	private String    mDefaultText;
	private int       mType;
	
	public HomeItem(Context context) 
	{
		super(context);
		
		mContext = context;
		initView();
		initAnimation();
	}
	
	public HomeItem(Context context, AttributeSet attrs) 
	{
		super(context,attrs);
		mContext = context;
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Ktv);
		
		mFocus = ta.getDrawable(R.styleable.Ktv_shadowImage);
		mDefaultImage = ta.getDrawable(R.styleable.Ktv_defaultImage);
		mDefaultText = ta.getString(R.styleable.Ktv_defaultText);
		mType = ta.getInteger(R.styleable.Ktv_type, 0);
       
        shadowPaddingLeft = ta.getDimensionPixelSize(R.styleable.Ktv_shadowPaddingLeft, 28);
        shadowPaddingRight = ta.getDimensionPixelSize(R.styleable.Ktv_shadowPaddingRight, 28);
        shadowPaddingTop = ta.getDimensionPixelSize(R.styleable.Ktv_shadowPaddingTop, 28);
        shadowPaddingBottom = ta.getDimensionPixelSize(R.styleable.Ktv_shadowPaddingBottom, 28);
        ta.recycle();
        
        initView();
        initAnimation();
	}
	
	public HomeItem(Context context, AttributeSet attrs, int defaultstyle) 
	{
		super(context,attrs,defaultstyle);
		
		mContext = context;
		initView();
		initAnimation();
	}
	
	private void initView()
	{
		switch (mType) 
		{
		case 0:
		{
			inflate(mContext, R.layout.view_home_v, this);
			mImage = (ImageView) findViewById(R.id.id_home_v_imageview);
			mName = (TextView) findViewById(R.id.id_home_v_textview);
			mName.setText(mDefaultText);
			break;
		}
		case 1:
		{
			inflate(mContext, R.layout.view_home_h, this);
			mImage = (ImageView) findViewById(R.id.id_home_h_imageview);
			mName = (TextView) findViewById(R.id.id_home_h_textview);
			mImage.setImageDrawable(mDefaultImage);
			mName.setText(mDefaultText);
			break;
		}
		default:
			break;
		}
	}
	
	private void initAnimation()
	{
		setWillNotDraw(false);
		mRect = new Rect();  
        mBound = new Rect();
		setChildrenDrawingOrderEnabled(true);  
		item_zoom_in = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		item_zoom_in.setDuration(150);
		item_zoom_in.setInterpolator(new LinearInterpolator());
		item_zoom_in.setFillAfter(true);
		item_zoom_in.setFillBefore(true);
		
		item_zoom_out = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		item_zoom_out.setDuration(150);
		item_zoom_out.setInterpolator(new LinearInterpolator());
		item_zoom_out.setFillAfter(true);
		item_zoom_out.setFillBefore(true);
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		if (hasFocus() && mFocus != null) 
		{
            super.getDrawingRect(mRect);  
            mBound.set(mRect.left - shadowPaddingLeft, mRect.top - shadowPaddingTop, mRect.right + shadowPaddingRight, mRect.bottom + shadowPaddingBottom);  
            mFocus.setBounds(mBound);
            canvas.save();
            mFocus.draw(canvas);  
            canvas.restore();  
        } 
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) 
	{
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		doAnimation(gainFocus);
	}
	
	@Override
	public void doAnimation(boolean gainFocus) 
	{
		int count = getChildCount();
		for (int i = 0; i < count; i++) 
		{
			if(getChildAt(i) instanceof ViewAnimation)
			{
				((ViewAnimation)getChildAt(i)).doAnimation(gainFocus);
			}
		}
		if(gainFocus)
		{
			clearAnimation();
			bringToFront();
			getRootView().requestLayout();  
			getRootView().invalidate();
			startAnimation(item_zoom_in);
		}
		else
		{
			clearAnimation();
			startAnimation(item_zoom_out);
		}
		
		for (int i = 0; i < count; i++) 
		{
			if(getChildAt(i) instanceof TextView)
			{
				((TextView)getChildAt(i)).setSelected(gainFocus);
			}
		}
	};
	
	public void setImage(String url)
	{
		ImageLoaderUtils.mImageLoader.displayImage(url, mImage, ImageLoaderUtils.mMainScaleOption);
	}
	
	public void setText(String text)
	{
		mName.setText(mDefaultText);
	}
}