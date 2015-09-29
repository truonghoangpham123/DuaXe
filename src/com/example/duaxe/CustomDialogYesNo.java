package com.example.duaxe;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class CustomDialogYesNo extends Dialog implements
		android.view.View.OnClickListener {
	MainGame maingame;
	Menu menu;
	Ratings ratings;
	ImageButton yes, no;
	int m = -1;
	public CustomDialogYesNo(Context context, MainGame maingame) {
		super(context);
		this.maingame = maingame;
		this.m = 0;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialogyesno);

		yes = (ImageButton) findViewById(R.id.yes);
		no = (ImageButton) findViewById(R.id.no);

		yes.setOnClickListener(this);
		no.setOnClickListener(this);

		Control.tocdothuong.stop();
	}

	public CustomDialogYesNo(Context context, Menu menu) {
		super(context);
		this.menu = menu;
		this.m = 1;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialogyesno);

		yes = (ImageButton) findViewById(R.id.yes);
		no = (ImageButton) findViewById(R.id.no);

		yes.setOnClickListener(this);
		no.setOnClickListener(this);
	}
	
	public CustomDialogYesNo(Context context, Ratings ratings) {
		super(context);
		this.ratings = ratings;
		this.m = 2;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialogyesno);

		yes = (ImageButton) findViewById(R.id.yes);
		no = (ImageButton) findViewById(R.id.no);

		yes.setOnClickListener(this);
		no.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		if(this.m == 0){
			switch (view.getId()) {
				case R.id.yes: {
					// Thoát khỏi game
					Control.thoat = true;
					Control.tocdothuong.release();
					maingame.customdialogsetting.dismiss();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.dismiss();
					this.maingame.finish();
					break;
				}
				case R.id.no: {
					// Tiếp tục chơi
					Control.isPause=false;
					Control.tocdothuong.play();
					this.hide();
					break;
				}
				default:
					break;
				}
		}
		if(this.m == 1){
			switch (view.getId()) {
				case R.id.yes: {
					// Thoát khỏi game
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Control.nen_menu.release();				
					this.menu.customdialogyesno.dismiss();
					this.menu.setting_.dismiss();
					this.dismiss();
					this.menu.finish();
					break;
				}
				case R.id.no: {
					// Tiếp tục chơi
					this.hide();
					break;
				}
				default:
					break;
				}
		}
		if(this.m == 2){
			switch (view.getId()) {
				case R.id.yes: {
					// Thoát khỏi game
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Control.nhacrating.release();	
					this.ratings.customdialogyesno.dismiss();
					this.dismiss();
					this.ratings.finish();
					break;
				}
				case R.id.no: {
					// Tiếp tục chơi
					this.hide();
					break;
				}
				default:
					break;
				}
		}

	}

}
