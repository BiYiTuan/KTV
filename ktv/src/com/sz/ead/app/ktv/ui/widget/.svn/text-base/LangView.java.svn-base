/**
 * @Title: CategoryItem.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.ui.widget
 * @Description:  栏目Item
 * @author: zhaoqy
 * @date: 2015-8-10 下午9:54:05
 * @version: V1.0
 */

package com.sz.ead.app.ktv.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sz.ead.app.ktv_wakg.R;
import com.sz.ead.app.ktv.utils.ImageLoaderUtils;

public class LangView extends RelativeLayout 
{
	private Context   mContext; //上下文
	private ImageView mIcon;    //icon
	private TextView  mName;    //名称
	
	public LangView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		mContext = context;
		
		initView();
	}
	
	private void initView()
	{
		inflate(mContext, R.layout.view_lang, this);
		mIcon = (ImageView) findViewById(R.id.id_category_icon);
		mName = (TextView) findViewById(R.id.id_category_name);
	}
	
	/**
	 * 
	 * @Title: getIconImage
	 * @Description: 获取mIcon
	 * @return
	 * @return: ImageView
	 */
	public void setImageView(String url)
	{
		ImageLoaderUtils.mImageLoader.displayImage(url, mIcon, ImageLoaderUtils.mLangOption);
	}
	
	/**
	 * 
	 * @Title: setName
	 * @Description: 设置名称(关键字高亮显示)
	 * @param name
	 * @return: void
	 */
	public void setName(String name)
	{
		mName.setText(name);
	}
}
