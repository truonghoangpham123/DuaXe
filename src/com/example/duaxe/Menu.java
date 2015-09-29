package com.example.duaxe;

import android.content.Intent;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Background;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;
import com.e3roid.drawable.texture.TiledTexture;
import com.e3roid.event.SceneUpdateListener;
import com.e3roid.event.ShapeEventListener;

public class Menu extends E3Activity implements SceneUpdateListener,
		ShapeEventListener {
	private Sprite[] info_p;
	private Sprite[] sett_p;
	private Sprite[] play_p;

	private Texture[] info_pTexture;
	private Texture[] sett_pTexture;
	private Texture[] play_pTexture;

	private int index = 5;// Mỗi button có một ảnh

	public CustomdialogSetting setting_;// Hiển thị dialog setting

	public CustomDialogYesNo customdialogyesno;// Hiển thị dialog hỏi bạn có
												// muốn thoát game hay không?

	// -------------------------------------------------------------------
	@Override
	public E3Engine onLoadEngine() {
		// Với tham số FLAG_KEEP_SCREEN_ON giúp chúng ta khi chơi game màn hình
		// không bị tắt
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		E3Engine engine = new E3Engine(this, Control.WIDTH, Control.HEIGHT);
		engine.requestFullScreen();
		engine.requestPortrait();
		Control.nen_menu.loop();

		return engine;
	}

	// -------------------------------------------------------------------
	@Override
	public void onLoadResources() {
		info_pTexture = new Texture[index];
		sett_pTexture = new Texture[index];
		play_pTexture = new Texture[index];

		// Các ảnh button được lưu như sau:
		// but_infor0.png
		// but_infor1.png
		// ...
		// but_infor4.png

		// Vì lưu như vậy nên ta có thể dùng vòng for để load ảnh với tên được
		// xác định theo giá trị i
		
		for (int i = 0; i < index; i++) {
			info_pTexture[i] = new AssetTexture("but_infor" + String.valueOf(i)
					+ ".png", this);
			sett_pTexture[i] = new AssetTexture("but_setting"
					+ String.valueOf(i) + ".png", this);
			play_pTexture[i] = new AssetTexture("but_play" + String.valueOf(i)
					+ ".png", this);
		}
	}

	// -------------------------------------------------------------------
	@Override
	public E3Scene onLoadScene() {
		E3Scene scene = new E3Scene();

		setting_ = new CustomdialogSetting(this, false);
		customdialogyesno = new CustomDialogYesNo(this, Menu.this);

		scene.registerUpdateListener(Control.time_delay, this);
		scene.addEventListener(this);

		// ĐẶt hình nền. Ở đây ta dùng hình nen.png để đặt làm nền
		Background nen = new Background(new TiledTexture("nen.png",
				Control.WIDTH, Control.HEIGHT, this));
		
		
		scene.getTopLayer().setBackground(nen);

		info_p = new Sprite[index];
		sett_p = new Sprite[index];
		play_p = new Sprite[index];

		// Nếu đặt 3 button trên nền thì ta tính toán tạo tọa độ trước rùi đặt
		// nó lên màn hình
		for (int i = 0; i < index; i++) {
			info_p[i] = new Sprite(info_pTexture[i], 50, 1060);
			sett_p[i] = new Sprite(sett_pTexture[i], 400, 1010);
			play_p[i] = new Sprite(play_pTexture[i], 170, 880);

			info_p[i].addListener(this);
			sett_p[i].addListener(this);
			play_p[i].addListener(this);

			scene.getTopLayer().add(info_p[i]);
			scene.getTopLayer().add(sett_p[i]);
			scene.getTopLayer().add(play_p[i]);

			scene.addEventListener(info_p[i]);
			scene.addEventListener(sett_p[i]);
			scene.addEventListener(play_p[i]);
		}

		return scene;
	}

	// -------------------------------------------------------------------
	int v = 0;
	long stime = SystemClock.elapsedRealtime();

	// Mục đích có phần update này là làm cho các button được thay đổi ảnh, như
	// vậy khi chạy bạn sẽ thấy
	// mấy cái button này nó không đứng im mà có sự thay đổi nhỏ, cái này giúp
	// người dùng phân biệt được đây là button
	public void onUpdateScene(E3Scene arg0, long arg1) {
		runOnUiThread(new Runnable() {
			public void run() {
				for (int i = 0; i < index; i++) {
					if (v == i) {
						info_p[i].show();
						sett_p[i].show();
						play_p[i].show();
					} else {
						info_p[i].hide();
						sett_p[i].hide();
						play_p[i].hide();
					}
				}
				if (SystemClock.elapsedRealtime() - stime > 300) {
					v++;
					stime = SystemClock.elapsedRealtime();
					if (v >= index)
						v = 0;
				}
			}
		});
	}

	// -------------------------------------------------------------------
	// Bắt sự kiện khi người dùng chạm vào các button
	public boolean onTouchEvent(E3Scene arg0, Shape shape,
			MotionEvent motionEvent, int arg3, int arg4) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			for (int i = 0; i < index; i++) {
				if (shape.equals(info_p[i])) {// Khi người dùng chạm vào phần
												// infor thì ta bật dialog này
												// lên
					// Hiển thị infor
					inFor();
				} else if (shape.equals(sett_p[i])) {// Khi người dùng chạm vào
														// phần setting thì ta
														// bật dialog này lên
					// Hiển thị phần setting
					setTing();
				} else if (shape.equals(play_p[i])) {// Khi người chơi chạm vào
														// phân Play thì ta
														// chuyển sang phần
														// MainGame
					// Bắt đầu game
					play();
				}
			}
		}
		return false;
	}

	// -------------------------------------------------------------------
	public void inFor() {
		CustomdialogInforOk ok = new CustomdialogInforOk(this);
		ok.show();// Hiện ra dialog infor
	}

	// -------------------------------------------------------------------
	public void setTing() {
		setting_.show();// Hiện ra dialog setting
	}

	// -------------------------------------------------------------------
	// Khi ta nhấn Play thì ta sẽ giải phóng dialog setting
	public void play() {
		Control.init(this);

		setting_.dismiss();
		
		Control.nen_menu.stop();
		
		// Khai báo intent se chuyển sang MainGame
		Intent i = new Intent(this, MainGame.class);
		this.startActivity(i);
		// Khi chuyển sang MainGame thì ta tắt cái menu này đi
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Khi người dùng nhấn nút back trên thiết bị thì người ta hỏi
		// xem người dùng có muốn thoát không?
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			customdialogyesno.show();
		}
		
		return super.onKeyDown(keyCode, event);
	}
		
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Control.nen_menu.stop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Control.nen_menu.stop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Control.nen_menu.loop();
	}
}
