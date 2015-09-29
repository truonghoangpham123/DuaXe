package com.example.duaxe;


public class Time extends Thread {
	private boolean run_;
	private int time;
	private int time_delay;

	public Time() {
		time = 0;
		run_ = true;
		time_delay = 1000;
	}

	public void run() {
		while (run_) {
			try {
				Thread.sleep(time_delay);// ms
				if (time != 0) {
					time--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (Control.thoat)
				return;
		}
	}

	// -------------------------------
	// Phương thức getTimeDelay
	public int getTimeDelay() {
		return time_delay;
	}

	// -------------------------------
	// Phương thức setTimeDelay
	public void setTimeDelay(int time_delay) {
		this.time_delay = time_delay;
	}

	// -------------------------------
	// Phương thức getTime
	public int getTime() {
		return time;
	}

	// -------------------------------
	// Phương thức setTime
	public void setTime(int time) {
		this.time = time;
	}

	// -------------------------------
	// Phương thức cho biết time đang chạy hay là dừng
	public boolean isRun_() {
		return run_;// Nếu là true thì đang chạy, false là không chạy
	}

	// -------------------------------
	// Phương thức giải phóng thread này
	@SuppressWarnings("deprecation")
	public void setDestroy() {
		this.destroy();
	}
	// -------------------------------
}
