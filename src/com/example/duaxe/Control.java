package com.example.duaxe;

import android.content.Context;
import android.media.AudioManager;

public class Control {
	// 2 biến xác định chiều cao và rộng của màn hình
	public static int HEIGHT;
	public static int WIDTH;
	// 2 biến xác định chiều cao và rộng của game
	public static int chieucao;
	public static int chieungang;

	public static int time_delay = 5;// Thời gian cập nhật lại màn hình (ms)

	public static int SPEED = 1;// Điều khiển tốc độ của background và car

	public static boolean huong = true;// Hướng

	// Ngược lại di chuyển từ dưới lên.

	public static boolean isPause = false;// Điều khiển tạm dừng và chơi tiếp
											// trong game
	// nếu isPause = false: Game đang chạy
	// nếu isPause = true: Game tạm dừng

	public static boolean overGame = false;// Biến xác định xem game kết thúc
											// hay chưa
	// Nếu overGame = false: Game chưa kết thúc
	// Nếu overGame = true: Game kết thúc

	public static boolean sound = true;// Biến xác định có cho bật âm thanh hay
										// không.
	// Nếu sound = false: Tắt âm thanh
	// Nếu sound = true: Bật âm thanh

	public static int volume = 15;// Biến xác định volume. Giá trị max=15.
	// Ban đầu thì volume là max.

	public static AudioManager audioManager = null;// Quản lý volume.

	public static boolean thoat = false;// Khi người dùng chọn thoát

	public static boolean isPlay = false;// Biến dùng để xác định xem game đã
											// bắt đầu hay chưa.
	// Nếu isPlay = false: hiện thị thời gian đếm ngược.
	// Nếu isPlay = true: Bắt đầu game

	public static int diem = 0;// Biến để lưu lại điểm của người chơi.

	public static Sound carstart = null, nhaccompetion = null,
			nen_menu = null, nhacrating = null, vacham = null,
			tocdothuong = null, tocdocao = null;

	// Khi người dùng chạm vào màn hình thì ta tăng tốc lên 3
	public static int touch_up_speed = 0;

	// ------------------------------------------------------------
	// Phương thức khởi tạo toàn bộ giá trị biến tĩnh
	public static void init(Context context) {
		HEIGHT = 1171;
		WIDTH = 720;
		chieucao = 781;
		chieungang = 480;
		time_delay = 5;
		SPEED = 1;
		huong = true;
		isPause = false;
		overGame = false;
		sound = true;
		volume = 15;
		thoat = false;
		isPlay = false;
		diem = 0;
		touch_up_speed = 0;

		Control.tocdothuong = new Sound(context, R.raw.tocdothuong);
		Control.tocdocao = new Sound(context, R.raw.tocdocao);
		Control.nhaccompetion = new Sound(context, R.raw.competition);
		Control.carstart = new Sound(context, R.raw.carstart);
		Control.nen_menu = new Sound(context, R.raw.nen_menu);
		Control.nhacrating = new Sound(context, R.raw.nhacrating);
		Control.vacham = new Sound(context, R.raw.vacham);

		Control.audioManager = (AudioManager) context
				.getSystemService(Menu.AUDIO_SERVICE);
	}

}
