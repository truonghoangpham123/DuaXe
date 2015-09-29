package com.example.duaxe;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CustomdialogSetting extends Dialog implements
		android.view.View.OnClickListener {

	private ImageButton but_v_giam, but_v_tang, but_ok;
	private ToggleButton bt_volume;
	private TextView text_volume;
	private boolean check;
	
	public CustomdialogSetting(Context context, boolean check) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialogsetting);

		this.check = check;//Biến để kiểm tra layout dang hiển thị
		
		but_v_giam = (ImageButton) findViewById(R.id.v_giam);
		but_v_tang = (ImageButton) findViewById(R.id.v_tang);
		but_ok = (ImageButton) findViewById(R.id.ok_setting);
		bt_volume=(ToggleButton)findViewById(R.id.bt_volume);
		
		text_volume = (TextView)findViewById(R.id.text_volume);
		
		but_v_tang.setOnClickListener(this);
		but_v_giam.setOnClickListener(this);
		but_ok.setOnClickListener(this);
		bt_volume.setOnClickListener(this);

	
		text_volume.setText(String.valueOf(Control.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)));
	}

	public void onClick(View view) {
		//V_tăng
		if(view.getId() == R.id.v_tang){
			int t = Integer.parseInt(text_volume.getText().toString());
			if(t+1 < 16){
				text_volume.setText(String.valueOf(t+1));
				Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	(t+1), 1);
			}
			Control.sound = true;
			
		}
		//V_giảm
		else if(view.getId() == R.id.v_giam){
			int t = Integer.parseInt(text_volume.getText().toString());
			if(t-1 > -1){
				text_volume.setText(String.valueOf(t-1));
				Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	(t-1), 1);
			}
			else if(t == 0){
				Control.sound = false;
			}
		}
		//Button Volume
		else if(view.getId() == R.id.bt_volume){
			int t = Integer.parseInt(text_volume.getText().toString());
			if(t!=0){
				text_volume.setText(String.valueOf(0));
				Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	0, 1);
			}
			else {
				text_volume.setText(String.valueOf(9));
				Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	9, 1);
			}
		}
		//Click OK
		else if (view.getId() == R.id.ok_setting) {
			if (check) {//Kiểm tra xem cái này có phải đang ở Maingame không?
				Control.isPause = false;// Khi ta nhấn vào OK thì tiếp tục MainGame
				Control.tocdothuong.loop();
				Log.d("check","true");
				//this.cancel();
			}
			this.hide();
		}
	}

}
