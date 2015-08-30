/**
 * 
 */
package com.malingyi.friendtrip.ui.adapter;

import java.util.ArrayList;

import com.malingyi.friendtrip.model.TripItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author David
 *
 */
public class tirpRecordListAdapter extends BaseAdapter {
    
	private Context mContext;
	private ArrayList<TripItem> mList;
	/**
	 * 
	 */
	public tirpRecordListAdapter(Context context, ArrayList<TripItem> mList) {
		// TODO Auto-generated constructor stub
		super();
		mContext=context;
		this.mList=mList;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
