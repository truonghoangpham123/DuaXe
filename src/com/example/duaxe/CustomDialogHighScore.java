package com.example.duaxe;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
//Cai nay chua su dung, khong quay clip
public class CustomDialogHighScore extends Dialog implements
		android.view.View.OnClickListener {
	EditText text_name;
	TextView score;
	Ratings ratings;
	ImageButton save, cancel;
	String data;
	
	public CustomDialogHighScore(Context context, Ratings ratings, String data) {
		super(context);
		this.ratings = ratings;
		this.data = data;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialoghighscore);

		save = (ImageButton) findViewById(R.id.save);
		cancel = (ImageButton) findViewById(R.id.cancel);

		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		text_name = (EditText)findViewById(R.id.edit_name);
		score = (TextView)findViewById(R.id.text_diem);
		
		score.setText(String.valueOf(Control.diem));
	}

	public void onClick(View view) {
			switch (view.getId()) {
				case R.id.save: {
					if(text_name.getText().length() != 0){
						Data d = new Data();
						String data_ = d.kt(text_name.getText().toString(), Control.diem);
						d.addData(data_);
						this.dismiss();
						}
					else Tools.senMessenger(getContext(), "ABC");
					break;
				}
				case R.id.cancel: {
					this.dismiss();
					break;
				}
				default:
					break;
				}

	}

}
