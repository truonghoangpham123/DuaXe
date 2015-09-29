package com.example.duaxe;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class CustomdialogInforOk extends Dialog implements
		android.view.View.OnClickListener {
	private ImageButton ok;

	// private static int[] ID = new int[5];

	public CustomdialogInforOk(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialoginfor);

		ok = (ImageButton) findViewById(R.id.ok_infor);
		ok.setOnClickListener(this);

		// ID[0] = R.id.ok_infor;
		// ID[1] = R.id.ok_infor;
		// ID[2] = R.id.ok_infor;
		// ID[3] = R.id.ok_infor;
		// ID[4] = R.id.ok_infor;

		// Cứ 300ms thì update lại imbutton Ok
		// Timer update_ok = new Timer();
		// update_ok.schedule(new Update_ImButton(ok), 300, 1000);

	}

	public void onClick(View view) {
		if (view.getId() == R.id.ok_infor) {
			this.dismiss();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	/*
	 * static int index = 0; private static class Update_ImButton extends
	 * TimerTask { private final ImageButton imbutton;
	 * 
	 * public Update_ImButton(ImageButton imbutton) { this.imbutton = imbutton;
	 * } public void run() { imbutton.post(new Runnable() { public void run() {
	 * while(true){ imbutton.setImageResource(ID[index]); index++; if(index >=
	 * ID.length) index = 0; } } }); } }
	 */
}