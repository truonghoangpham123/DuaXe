package com.example.duaxe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Background;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.sprite.TextSprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;
import com.e3roid.drawable.texture.TiledTexture;
import com.e3roid.event.SceneUpdateListener;
import com.e3roid.event.ShapeEventListener;

public class Ratings extends E3Activity implements SceneUpdateListener,
		ShapeEventListener {

	private Sprite restart_p;
	private Sprite menu_p;
	private Sprite reset_p;

	private Texture restart_pTexture;
	private Texture menu_pTexture;
	private Texture reset_pTexture;

	private int sohang = 5;
	private TextSprite[] ra;
	private TextSprite[] rank;
	private TextSprite nguoichoi;

	public CustomDialogYesNo customdialogyesno;

	// -------------------------------------------------------------------
	@Override
	public E3Engine onLoadEngine() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		E3Engine engine = new E3Engine(this, Control.chieungang,
				Control.chieucao);
		engine.requestFullScreen();
		engine.requestPortrait();
		Control.nhacrating.loop();

		return engine;
	}

	// -------------------------------------------------------------------
	@Override
	public void onLoadResources() {
		restart_pTexture = new AssetTexture("restartbtn.png", this);
		menu_pTexture = new AssetTexture("menubtn.png", this);
		reset_pTexture = new AssetTexture("reset.png", this);

	}

	// -------------------------------------------------------------------
	@Override
	public E3Scene onLoadScene() {
		E3Scene scene = new E3Scene();

		customdialogyesno = new CustomDialogYesNo(this, Ratings.this);

		scene.registerUpdateListener(Control.time_delay, this);
		scene.addEventListener(this);

		// Đặt hình nền
		Background nen = new Background(new TiledTexture("scorescreen.png",
				Control.chieungang, Control.chieucao, this));
		scene.getTopLayer().setBackground(nen);
		// Đặt vị trí các nút
		restart_p = new Sprite(restart_pTexture, 250, 552);
		menu_p = new Sprite(menu_pTexture, 150, 690);
		reset_p = new Sprite(reset_pTexture, 52, 452);
		// Lắng nghe sự kiện
		restart_p.addListener(this);
		menu_p.addListener(this);
		reset_p.addListener(this);

		scene.addEventListener(restart_p);
		scene.addEventListener(menu_p);
		scene.addEventListener(reset_p);

		scene.getTopLayer().add(restart_p);
		scene.getTopLayer().add(menu_p);
		scene.getTopLayer().add(reset_p);

		Typeface font = Typeface.createFromAsset(getAssets(), "SHOWG.TTF");

		ra = new TextSprite[sohang];
		rank = new TextSprite[sohang];
		for (int i = 0; i < sohang; i++) {
			ra[i] = new TextSprite("0", 24, this);
			rank[i] = new TextSprite("0", 24, this);

			ra[i].setTypeface(font);
			rank[i].setTypeface(font);

			ra[i].setColor(Color.WHITE);
			rank[i].setColor(Color.WHITE);

			scene.getTopLayer().add(ra[i]);
			scene.getTopLayer().add(rank[i]);
		}

		// Khoi tao diem nguoi choi de hien ra
		nguoichoi = new TextSprite("0", 28, this);
		nguoichoi.setTypeface(font);
		nguoichoi.setColor(Color.WHITE);
		scene.getTopLayer().add(nguoichoi);
		nguoichoi.move(130, 350);

		ra[0].move(145, 85);
		ra[1].move(145, 130);
		ra[2].move(145, 175);
		ra[3].move(145, 220);
		ra[4].move(145, 265);

		rank[0].move(345, 85);
		rank[1].move(345, 130);
		rank[2].move(345, 175);
		rank[3].move(345, 220);
		rank[4].move(345, 265);

		// Dien cac hang vao bang rank
		for (int i = 0; i < sohang; i++) {
			rank[i].setText(i + 1 + "");
		}

		int ne = 0;
		String data = this.kt("Player", Control.diem);
		// Hien thi thong tin diem nguoi choi
		nguoichoi.setText("Your Score: " + Control.diem);

		if (data.length() != 0) {
			this.addData(data);
		}

		int kiemtra = 0;// Bien de kiem tra de cho chen` chu~ You 1 lan` thui

		// Chen so diem nguoi choi vao bang diem
		String[] diem = this.getData().split("\n");
		for (int i = 0; i < diem.length; i++) {
			if (i % 2 != 0) {
				Log.d("i", String.valueOf(i));
				ra[ne].reload(true);
				if (Control.diem == Integer.parseInt(diem[i]) && (kiemtra == 0)) {
					ra[ne].setText("You => " + diem[i]);
					kiemtra = 1;//Da~ chen` chu~ you rui`
				} else
					ra[ne].setText(diem[i]);
				ne++;
			}
		}

		return scene;
	}

	// -------------------------------------------------------------------
	public boolean onTouchEvent(E3Scene arg0, Shape shape,
			MotionEvent motionEvent, int arg3, int arg4) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (shape.equals(restart_p)) {
				Control.init(this);
				Control.nhacrating.stop();// Tắt nhạc
				Intent i = new Intent(this, MainGame.class);
				this.startActivity(i);
				this.finish();
			} else if (shape.equals(menu_p)) {
				Control.nhacrating.stop();// Tắt nhạc
				Intent i = new Intent(this, Menu.class);
				this.startActivity(i);
				this.finish();
			} else if (shape.equals(reset_p)) {
				this.deleteData();
				for (int i = 0; i < sohang; i++) {
					ra[i].reload(true);
					ra[i].setText("0");
				}
			}
		}
		return false;
	}

	// -------------------------------------------------------------------
	long time_start = SystemClock.elapsedRealtime();
	int index = 0;
	boolean end = false;

	public void onUpdateScene(E3Scene arg0, long arg1) {

	}

	// -------------------------------------------------------------------
	@Override
	public boolean onKeyDown(E3Scene scene, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			customdialogyesno.show();
		}
		return super.onKeyDown(scene, keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Control.nhacrating.stop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Control.nhacrating.stop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Control.nhacrating.loop();
	}

	// =================================================================================================================
	String filename = "data.txt";// Tên file CSDL

	// ------------------------------------------------------
	// Phương thức đọc file và trả về một chuỗi
	public String getData() {
		try {
			InputStream in = openFileInput(filename);
			if (in != null) {
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuilder buf = new StringBuilder();

				while ((str = reader.readLine()) != null) {
					buf.append(str + "\n");
				}
				in.close();
				return buf.toString();
			} else
				return "";
		} catch (java.io.FileNotFoundException e) {
			return "";
		} catch (Throwable t) {
			return "";
		}
	}

	// ------------------------------------------------------
	public String getTenDiem(String s, String ten_, int diem_) {
		String[] tmp = s.split("\n");

		Log.d("tmp.le", String.valueOf(tmp.length));

		String[] ten = new String[tmp.length / 2];
		int[] diem = new int[tmp.length / 2];

		Log.d("diem.le", String.valueOf(diem.length));

		int j = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (i % 2 == 0)
				ten[j] = tmp[i];
			else {
				diem[j] = Integer.parseInt((tmp[i]));
				j++;
			}
		}

		int index_min = -1;
		for (int i = 0; i < diem.length; i++) {
			if (diem_ > diem[i]) {
				int min = diem[0];
				for (int k = 0; k < diem.length; k++) {
					if (min >= diem[k]) {
						min = diem[k];
						index_min = k;
					}
				}
				diem[index_min] = diem_;
				ten[index_min] = ten_;
				break;
			}
		}

		// Không thêm được
		if (index_min == -1)
			return "";// Không thêm được vào số điểm

		// Sắp xếp
		for (int i = 0; i < diem.length; i++) {
			for (int k = i; k < diem.length; k++) {
				if (diem[i] > diem[k]) {
					int tm = diem[i];
					diem[i] = diem[k];
					diem[k] = tm;

					String stm = ten[i];
					ten[i] = ten[k];
					ten[k] = stm;
				}
			}
		}

		String return_ = "";
		for (int i = 0; i < ten.length; i++) {
			Log.d("ten", ten[i]);
			Log.d("diem", String.valueOf(diem[i]));

			if (return_.length() == 0)
				return_ = ten[i] + "\n" + String.valueOf(diem[i]);
			else
				return_ = ten[i] + "\n" + String.valueOf(diem[i]) + "\n"
						+ return_;
		}

		return return_;
	}

	// ------------------------------------------------------
	// Phương thức xóa toàn bộ dữ liệu
	public void deleteData() {
		deleteFile(filename);
	}

	// ------------------------------------------------------
	// Phương thức thêm dữ liệu
	public boolean addData(String data) {
		try {
			Log.d("data=", data);
			deleteData();// Xóa cái cũ rùi lưu cái mới
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput(
					filename, 0));
			out.write(data.toString());
			out.close();
			return true;// Thành công
		} catch (Throwable t) {
			return false;// Không thành công
		}
	}

	// ------------------------------------------------------
	public String kt(String ten, int sodiem) {
		boolean them = true;
		String data_daco = getData();
		if (data_daco.length() == 0)
			data_daco = ten + "\n" + String.valueOf(sodiem);
		else {
			if (data_daco.split("\n").length < 10)
				data_daco = ten + "\n" + String.valueOf(sodiem) + "\n"
						+ data_daco;
			else {
				String stt = getTenDiem(data_daco, ten, sodiem);
				if (stt.length() != 0)
					data_daco = stt;
				else
					them = false;
			}
		}
		if (them)
			return data_daco;
		else
			return "";
	}
}
