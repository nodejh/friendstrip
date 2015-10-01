package com.special.ResideMenu;

import com.malingyi.friendtrip.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * User: special
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout{

    /** menu item  icon  */
    private ImageView iv_icon;
    /** menu item  title */
    private TextView tv_title;
    /** menu item Image */
    private ImageView single_image;
    /** 是否 是用户头像选项*/
    private boolean isSingleImage=false;
    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }
    /**
     * 显示单张图片的菜单项
     * @param context
     * @param icon
     * @param isSingleImage if true ，则显示的是大图，显示在行中间，否则，显示的是小图标且靠左
     */
    public ResideMenuItem(Context context , int icon ,boolean isSingleImage){
    	super(context);
    	if(isSingleImage == true){
    	   initSingleImageView(context);
    	   this.isSingleImage=isSingleImage;
           single_image.setImageResource(icon);
    	}else {
			initViews(context);
			iv_icon.setImageResource(icon);
		}
    }
    /**
     * 显示单张图片的菜单项
     * @param context
     * @param icon
     * @param isSingleImage if true ，则显示的是大图，显示在行中间，否则，显示的是小图标且靠左
     */
    public ResideMenuItem(Context context , String url ,boolean isSingleImage){
    	super(context);
    	if(isSingleImage == true){
    	   initSingleImageView(context);
    	   this.isSingleImage=isSingleImage;
    	   if(!url.trim().equals(""))
    	      Picasso.with(context).load(url).error(R.drawable.touxing).into(single_image);
    	   else 
			  single_image.setImageResource(R.drawable.touxing);
		
    	}else {
			initViews(context);
			Picasso.with(context).load(url).error(R.drawable.touxing).into(iv_icon);
		}
    }
    private void initSingleImageView(Context context){
    	LayoutInflater inflater = ( LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	inflater.inflate(R.layout.residemenu_image_item, this);
    	single_image = (ImageView) findViewById(R.id.img_User);
    }
    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon){
    	if(iv_icon == null)
    		initViews(getContext());
        iv_icon.setImageResource(icon);
    }
    public void setIcon(String url){
    	if(!url.equals("")){
    		Picasso.with(getContext()).load(url).error(R.drawable.touxiang).into(single_image);
    	}
    	else {
			single_image.setImageResource(R.drawable.touxiang);
		}
    }
    /**
     * set the title with resource
     * ;
     * @param title
     */
    public void setTitle(int title){
        tv_title.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }
}
