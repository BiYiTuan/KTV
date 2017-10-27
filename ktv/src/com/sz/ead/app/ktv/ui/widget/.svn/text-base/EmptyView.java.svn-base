/**
 * @Title: EmptyView.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.widget
 * @Description: 空View
 * @author: zhaoqy
 * @date: 2015-8-3 下午2:54:32
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;

public class EmptyView  extends RelativeLayout 
{
	private ImageView mImage;
	private ImageView mWaitting;
	private TextView  mText;
	
	public EmptyView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context)
	{
		inflate(context, R.layout.view_empty, this);
		mText = (TextView) findViewById(R.id.id_empty_text);
		mImage = (ImageView) findViewById(R.id.id_empty_icon);
		mWaitting = (ImageView) findViewById(R.id.id_empty_waitting);
	}
	
	public void showLoading()
	{
		mText.setVisibility(View.GONE);
		mImage.setVisibility(View.GONE);
		mWaitting.setVisibility(View.GONE);
	}
	
	public void showEmpty()
	{
		mText.setVisibility(View.VISIBLE);
		mImage.setVisibility(View.VISIBLE);
		mWaitting.setVisibility(View.GONE);
	}
	
	public void hide()
	{
		mText.setVisibility(View.GONE);
		mImage.setVisibility(View.GONE);
		mWaitting.setVisibility(View.GONE);
	}
}
