package com.example.duaxe;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.event.SceneUpdateListener;
import com.e3roid.event.ShapeEventListener;

public class MainGame extends E3Activity implements SceneUpdateListener,
		ShapeEventListener, SensorEventListener {

	// Khai báo các biến để xử lý cảm biến gia tốc
	private SensorManager sensorManager;
	private int getX; // Vì Player chỉ di chuyển sang trái hoặc phải nên ta chỉ
						// cần nhận giá trị theo trục X

	// Khai báo biến MainGame với mặc định là cho phép gọi các hàm khi đang
	// trong vòng lặp
	static MainGame mg;

	// Dialog hiển thị thông báo hỏi có muốn thoát game hay không?
	private CustomDialogYesNo customdialogyesno;

	// Dialog hiển thị phần setting
	public CustomdialogSetting customdialogsetting;

	// Background. Lớp này để tạo ra đường đua
	private Background_ background = new Background_();

	// Car. Đại diện cho tất cả các oto cùng tham gia dua cùng Player
	private Car car = new Car();

	// Player. Nhân vật chính của chúng ta
	private Player player = new Player();

	// Pause
	private Sprite pause;
	private AssetTexture pauseTexture;

	// Continue. Ảnh hiển thị Button continue khi bạn Pause game
	private Sprite continue_;
	private AssetTexture continueTexture;

	// Setting. Hiển thị button setting
	private Sprite setting;
	private AssetTexture settingTexture;

	// DemNguoc .Khi bắt đầu game thì ta đếm ngược kiểu 3,2,1,go. Khi vẽ xong GO
	// thì ta cho Player di chuyển
	private DemNguoc demnguoc = new DemNguoc();

	// OverGame. Xác định game đã kết thúc
	private Sprite overgame;
	private AssetTexture overgameTexture;

	// Điểm. Lớp này dùng để hiển thị ảnh
	private Diem dd = new Diem();

	// Mảng này check để tăng tốc độ của trò chơi, Khi người chơi đạt được điểm
	// để tăng tốc thì tăng tốc
	boolean[] check = new boolean[9];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Với tham số FLAG_KEEP_SCREEN_ON giúp chúng ta khi chơi game màn hình
		// không bị tắt
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mg = this;
	}

	// ---------------------------------------------------------

	@Override
	public E3Engine onLoadEngine() {
		E3Engine engine = new E3Engine(this, Control.chieungang,
				Control.chieucao);
		engine.requestFullScreen();
		engine.requestPortrait();

		return engine;
	}

	// ---------------------------------------------------------

	// Thực hiện load toàn bộ dữ liệu cho các biến và đối tượng
	@Override
	public void onLoadResources() {
		// Load Background
		background.load(this);

		// Load Car
		car.load(this);

		// Load Player
		player.load(this);

		// Load Pause
		pauseTexture = new AssetTexture("pause.png", this);

		// Load Continue
		continueTexture = new AssetTexture("continue.png", this);

		// Load Setting
		settingTexture = new AssetTexture("setting.png", this);

		// Load DemNguoc
		demnguoc.load(this);

		// Load OverGame
		overgameTexture = new AssetTexture("overgame.png", this);

		// Load Diem
		dd.load(this);

	}

	// ---------------------------------------------------------
	// Khởi tạo và add vào Scene
	@Override
	public E3Scene onLoadScene() {
		E3Scene scene = new E3Scene();
		scene.registerUpdateListener(Control.time_delay, this);
		scene.addEventListener(this);

		customdialogyesno = new CustomDialogYesNo(this, MainGame.this);

		customdialogsetting = new CustomdialogSetting(this, true);

		customdialogsetting.hide();

		// Đăng ký để lắng nghe sự kiện thay đổi sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager
				.registerListener(this, sensorManager
						.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 1000);
		// Delay là 1000 mili giây

		// Khởi tạo Background
		background.init(scene);// Khởi tạo và add background vào scene

		// Khởi tạo Car
		car.init(scene);

		// Khởi tạo Player
		player.init(scene);

		// Khởi tạo Điểm
		dd.init(scene);

		// Add phần Pause tại vị trí bên dưới góc trái màn hình
		pause = new Sprite(pauseTexture, 15, Control.chieucao
				- pauseTexture.getHeight() - 5);
		pause.addListener(this);
		scene.addEventListener(pause);
		scene.getTopLayer().add(pause);

		// Add phần Setting tại vị trí góc dưới bên phải màn hình
		setting = new Sprite(settingTexture, Control.chieungang
				- settingTexture.getWidth() - 5, Control.chieucao
				- pauseTexture.getHeight() - 5);
		setting.addListener(this);
		scene.addEventListener(setting);
		scene.getTopLayer().add(setting);

		// Add phần Continue tại vị trí giữa màn hình
		int cx = Control.chieungang / 2 - continueTexture.getWidth() / 2;
		int cy = Control.chieucao / 2 - continueTexture.getHeight() / 2;

		continue_ = new Sprite(continueTexture, cx, cy);
		continue_.hide();
		continue_.addListener(this);
		scene.addEventListener(continue_);
		scene.getTopLayer().add(continue_);

		// Khởi tạo đếm ngược
		demnguoc.init(scene);
		demnguoc.start();// Bắt đầu đếm ngược

		cx = Control.chieungang / 2 - overgameTexture.getWidth() / 2;
		cy = Control.chieucao / 2 - overgameTexture.getHeight() / 2;
		overgame = new Sprite(overgameTexture, cx, cy);
		overgame.hide();
		scene.getTopLayer().add(overgame);

		// Toàn bộ check=false
		for (int i = 0; i < check.length; i++)
			check[i] = false;
		return scene;
	}

	// ---------------------------------------------------------
	// Khi chạm vào màn hình thì ta tăng tốc độ game lên tạm thời. Khi bạn rời
	// tay khỏi màn hình thì trở lại trạng thái ban đầu
	@Override
	public boolean onSceneTouchEvent(E3Scene scene, MotionEvent motionEvent) {
		if (!Control.isPause && Control.isPlay) {
			// Khi de tay vao man hinh
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				Control.touch_up_speed = 3;// Tăng tốc độ thêm 5
				Control.tocdocao.loop();
			}
			// Khi roi tay khoi man hinh
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				Control.touch_up_speed = 0;// Giảm tốc độ về 0
				Control.tocdocao.stop();
			}

		}
		return false;
	}

	// ---------------------------------------------------------
	// Bật các sự kiện khi chạm vào các button
	public boolean onTouchEvent(E3Scene arg0, Shape shape,
			MotionEvent motionEvent, int arg3, int arg4) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (!Control.isPause && Control.isPlay) {// Khi người chơi chạm vào
														// nut Pause
				if (shape.equals(pause) && pause.isVisible()) {
					Control.tocdothuong.stop();// tạm dừng nhạc nền
					Control.tocdocao.stop();
					Control.isPause = true;
					continue_.show();// Hiển thị 1 button Continue ở giữa màn
										// hình
					pause.hide();// ẩn button pause và setting
					// setting.hide();
				} else if (shape.equals(setting) && setting.isVisible()) {
					// Khi chạm vào button setting
					Control.tocdothuong.stop();// tạm dừng nhạc nền
					Control.tocdocao.stop();
					Control.isPause = true;
					customdialogsetting.show();// hiển thị dialog setting
				}
			} else {
				if (shape.equals(continue_) && continue_.isVisible()) {// Khi
																		// chạm
																		// button
																		// Play
					Control.isPause = false;
					Control.tocdothuong.loop();// cho chạy nhạc nền
					Control.tocdocao.stop();
					continue_.hide();// ẩn button continue
					pause.show();
					// setting.show();// hiển thị button pause và setting
				}
			}
		}
		return false;
	}

	// ---------------------------------------------------------
	// Khi đang chơi mà bạn nhấn back trên thiết bị thì ta cho hiển thị thông
	// báo hỏi xem có muốn thoát game hay không?
	@Override
	public boolean onKeyDown(E3Scene scene, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Control.isPause = true;
			Control.tocdothuong.stop();
			Control.tocdocao.stop();
			customdialogyesno.show();
		}
		// if (keyCode == KeyEvent.KEYCODE_MENU){
		// Control.isPause=true;
		// Control.nhacnen.stop();
		// customdialogyesno.show();
		// }
		return false;

	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Control.tocdothuong.stop();
		Control.tocdocao.stop();
		Control.isPause = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Control.tocdothuong.stop();
		Control.tocdocao.stop();
		Control.isPause = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Control.isPlay)
			Control.tocdothuong.loop();
		Control.isPause = false;
	}

	// ---------------------------------------------------------
	long timeStart = 0;
	boolean chuyen = false;
	long timeEnd = 0;
	boolean next = true;

	public void onUpdateScene(E3Scene scene, long elapsedMsec) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (!Control.overGame) {
					if (!Control.isPause)
						player.move(0);// Hiển thị khói xe
					if (!Control.isPause && Control.isPlay) {
						// Di chuyển background
						background.move();

						// Di chuyển xe
						car.move();

						player.vacham(car);

						dd.move();

						speedUp();

						if (Control.huong == false) {
							if (chuyen == false) {
								timeStart = SystemClock.elapsedRealtime();

								// Tắt nhạc
								Control.carstart.release();
								Control.tocdothuong.release();
								Control.tocdocao.release();
								chuyen = true;
							}

							timeEnd = SystemClock.elapsedRealtime() - timeStart;
							if (timeEnd > 100) {// Cứ sau 0.2s ta giảm tốc độ
												// của
												// xe 1 lần
								background.up--;
								if (background.speedBackGround <= 0) {
									Control.isPause = true;
									Control.isPlay = false;
									// Tắt nhạc
									Control.carstart.release();
									Control.tocdothuong.release();
									Control.tocdocao.release();
									Control.vacham.release();
									Control.overGame = true;
									overgame.show();
									timeStart = SystemClock.elapsedRealtime();
								}
								chuyen = false;
							}
						}
					}
				} else {
					// Hiển thị dòng chữ Game Over
					timeEnd = SystemClock.elapsedRealtime() - timeStart;
					if (timeEnd > 2500) {
						// Sau 2.5s thì ta chuyển sang intent khác
						if (next) {
							next = false;
							MainGame.mg.ac();
						}
						return;
					}

				}
				if (Control.thoat)
					return;
			}

		});

	}

	// Phương thức này sẽ chuyển sang giao diện hiển thị danh sách điểm
	public void ac() {
		try {
			customdialogsetting.dismiss();
			Intent i = new Intent(this, Ratings.class);
			this.startActivity(i);
			this.finish();// Khi chuyển sang thì ta tắt Maingame này đi
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ---------------------------------------------------------
	// Phần xử lý cảm biến gia tốc
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	// Khi cảm biến gia tốc thay đổi
	public void onSensorChanged(SensorEvent event) {
		if (!Control.isPause && Control.isPlay) {
			float[] values = event.values;
			// Lấy giá trị tọa độ X thông qua giá trị thay đổi Sensor
			if (Control.huong == true)
				getX = Math.round(values[0]);
			else
				getX = 0;

			getX = -getX;
			// Di chuyển player
			player.move(getX);
		}

	}

	// ---------------------------------------------------------
	// Phương thức tăng điểm
	public String getDiem(int di) {
		String diem_ = "0000000";
		int dodai = String.valueOf(di).length();
		if (dodai == 2)
			diem_ = "00000" + String.valueOf(di);
		else if (dodai == 3)
			diem_ = "0000" + String.valueOf(di);
		else if (dodai == 4)
			diem_ = "000" + String.valueOf(di);
		else if (dodai == 5)
			diem_ = "00" + String.valueOf(di);
		else if (dodai == 6)
			diem_ = "0" + String.valueOf(di);
		else if (dodai == 7)
			diem_ = String.valueOf(di);

		return diem_;
	}

	// ------------------------------------------------------------

	// Phương thức tăng tốc độ
	public void speedUp() {
		switch (Control.diem) {
		case 500:
			if (check[0] == false) {
				Control.SPEED++;
				check[0] = true;
			}
			break;
		case 1000:
			if (check[1] == false) {
				Control.SPEED++;
				check[1] = true;
			}
			break;
		case 1500:
			if (check[2] == false) {
				Control.SPEED++;
				check[2] = true;
			}
			break;
		case 3000:
			if (check[3] == false) {
				Control.SPEED++;
				check[3] = true;
			}
			break;
		case 4000:
			if (check[4] == false) {
				Control.SPEED++;
				check[4] = true;
			}
			break;
		case 5000:
			if (check[5] == false) {
				Control.SPEED++;
				check[5] = true;
			}
			break;
		case 6000:
			if (check[6] == false) {
				Control.SPEED++;
				check[6] = true;
			}
			break;
		case 7000:
			if (check[7] == false) {
				Control.SPEED++;
				check[7] = true;
			}
			break;
		case 8000:
			if (check[8] == false) {
				Control.SPEED++;
				check[8] = true;
			}
			break;
		}
	}
}
