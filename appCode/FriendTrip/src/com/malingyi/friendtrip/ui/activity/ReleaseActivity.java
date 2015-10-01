package com.malingyi.friendtrip.ui.activity;

import com.malingyi.friendtrip.R;
import com.umeng.socialize.net.l;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

public class ReleaseActivity extends SuperActivity {
    
	/**控件**/
	EditText OriPlace; //始发地
	EditText DesPlace; //目的地
	RadioGroup TripType; //出行类型
	EditText GoTime;   //出行时间
	EditText PeopleNum;  //出行人数
	EditText Phone;    //联系电话
	EditText ExtraContent; //附加信息
	
	Button btn_release;  //发布按钮
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_releasetrip);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		OriPlace = (EditText) findViewById(R.id.edit_ori_Release);
		DesPlace = (EditText) findViewById(R.id.edit_dest_Release);
		TripType = (RadioGroup) findViewById(R.id.rdioGroup_Type_Release);
		GoTime   = (EditText) findViewById(R.id.edit_goTime_Release);
		PeopleNum = (EditText) findViewById(R.id.edit_peopleNum_Release);
		Phone    = (EditText) findViewById(R.id.edit_phone_Release);
		ExtraContent = (EditText) findViewById(R.id.edit_etra_Release);
		btn_release = (Button) findViewById(R.id.btn_release_Release);
		
				
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		TripType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
		GoTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						final int _year = year;
						final int _month = monthOfYear;
						final int _day  =dayOfMonth;
						TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								if(minute>=10)
								  GoTime.setText(_year+"-"+_month+"-"+_day+" "+hourOfDay+":"+minute);
								else {
							      GoTime.setText(_year+"-"+_month+"-"+_day+" "+hourOfDay+":0"+minute);
								}
							}
						}, 10, 00, true);
						timePickerDialog.show();
					}
				}, 2015, 1, 1);
				datePickerDialog.show();
			}
		});
	    
		btn_release.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//获得各个控件的状态，包装数据到ReleaseItem里面，传送到服务器
				Toast.makeText(mContext, "点击发布", Toast.LENGTH_SHORT).show();
			    
				
				
				finish();
			}
		});
	}

	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void obtainInfo() {
		// TODO Auto-generated method stub

	}

}
