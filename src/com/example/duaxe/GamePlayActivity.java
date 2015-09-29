package com.example.duaxe;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GamePlayActivity extends Activity implements OnCompletionListener
// ,OnTouchListener
{

	MediaPlayer mp3;
	private Intent i;

	// ----------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Control.init(this);// Khi play thì khởi tạo lại các biến tĩnh
		// Với tham số FLAG_KEEP_SCREEN_ON giúp chúng ta khi chơi game màn hình
		// không bị tắt
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// Set full màn hình
		requestWindowFeature(Window.FEATURE_NO_TITLE);// Không hiển thị tiêu đề
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// Fullscreen
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gioithieu);

		// Nhạc nền màn hình loading
		mp3 = MediaPlayer.create(getApplicationContext(), R.raw.nhacloading);
		mp3.setOnCompletionListener(this);// Khi video đã chạy xong
		mp3.start();

		// Hiện thị dòng chữ để thông báo cho người dùng biết khi chạm vào màn
		// hình thì bỏ qua phần giới thiệu này.
		// Tools.senMessenger(this, "Touch Skip");

		// Khởi tạo Intent mà ta muốn chuyển sang sau khi video chơi xong
		i = new Intent(this, Menu.class);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp3.pause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp3.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mp3.start();
	}

	// ----------------------------------------------------------------------------
	public void onCompletion(MediaPlayer arg0) {
		// Khi chạy xong thì ta dừng lại và chuyển sang phần game
		this.startActivity(i);
		finish();// Kết thúc Intent
	}

	// ----------------------------------------------------------------------------
	// public boolean onTouch(View arg0, MotionEvent arg1) {
	// // Khi người chơi chạm vào màn hình
	// this.startActivity(i);
	// finish();// Kết thúc Intent
	// return false;
	// }
}