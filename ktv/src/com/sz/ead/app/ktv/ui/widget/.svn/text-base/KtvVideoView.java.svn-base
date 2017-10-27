/**
 * @Title: KtvVideoView.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.widget
 * @Description: 播放器
 * @author: zhaoqy
 * @date: 2015-8-24 下午5:53:25
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class KtvVideoView extends VideoView 
{
	public KtvVideoView(Context context) 
	{
		super(context);
	}
	
	public KtvVideoView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public KtvVideoView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width,height);
	}
}
