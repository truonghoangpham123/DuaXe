package com.example.duaxe;

import android.content.Context;
import android.util.Log;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Car {
	// Car
	public Sprite[] car;
	public Texture[] carTexture;
	public int so_car = 10;// So xe tham gia cuoc dua 14
	public int soxe=15; //Số hình xe
	public int[] check; // Kiem tra xem xe tao ra co trung khong
	public int[] landuong;
	public int so_landuong = 6;
	public int[] RE;
	public int[] landuong_car;
	private int speed = 0;

	private final int speed_re = 1;// Tốc độ rẽ của xe là 3

	// Time
	public Time time;

	// Den
	public Den den = new Den();

	// ---------------------------------------------------------------
	public Car() {
	}

	// ---------------------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		car = new Sprite[so_car];
		RE = new int[so_car];

		landuong_car = new int[so_car];
		for (int i = 0; i < so_car; i++) {
			// Ban đầu thì ta đặt toàn bộ số car này ở ngoài màn hình
			car[i] = new Sprite(carTexture[i], -100, -100);
			scene.getTopLayer().add(car[i]);

			// Ban đầu thì không xe nào được quyền rẻ cả
			RE[i] = -1;
			// Nếu RE=0=> rẻ trái, RE=1 rẻ phải

			// Khởi tạo lại làn đường ban đầu là -1
			landuong_car[i] = -1;
		}

		// Khởi tạo các giá trị của làn đường. Có 8 làn đường.
		landuong = new int[so_landuong];
		landuong[0] = 102;
		landuong[1] = 150;
		landuong[2] = 199;
		landuong[3] = 252;
		landuong[4] = 302;
		landuong[5] = 352;
		// landuong[6] = 301;
		// landuong[7] = 352;

		// Set vị trí ban đầu cho xe
		setVitriBandau();

		den.init(scene);

		time = new Time();
		time.setTime(15);// Sau 15s có 1 xe chuyển hướng
		time.start();

	}

	// ---------------------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		check = new int[soxe+1];//Khoi tao mang kiem tra
		carTexture = new Texture[so_car];
		int t;//Biến t để sinh số ngẫu nhiên
		for (int i = 1; i <= 15; i++) {
			check[i] = 0;
		}
		for (int i = 0; i < so_car; i++) {
			//Tao random 1 so khong trung nhau tu 2 den 15
			do {
				t = Tools.getRandomIndex(2, soxe);
			} while (check[t] == 1);
			check[t] = 1;
			carTexture[i] = new AssetTexture(
					"car" + String.valueOf(t) + ".png", context);
		}
		den.load(context);
	}

	// ---------------------------------------------------------------
	// Phương thức kiểm tra xem car có đi ra khỏi màn hình chưa?
	public boolean endHeight(Sprite sprite) {
		if (sprite.getRealY() >= Control.chieucao+400)
			return true;// Ra khỏi màn hình
		return false;// Vẫn còn trong màn hình
	}

	// ---------------------------------------------------------------
	// Phương thức khởi tạo vị trí ban đầu của các xe
	public void setVitriBandau() {
		int vitri_y = ((Control.chieucao / 3) * 2) - 80;
		int l = 0;
		int y = 0;

		// Ta cho 4 xe lên màn hình
		for (int i = 0; i < 4; i++) {
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);
				if (l == 3)
					continue;
				y = Tools.getRandomIndex(10, vitri_y);
				car[i].move(landuong[l], y);

				boolean tmp = true;
				for (int j = 0; j < so_car; j++) {
					if (i != j) {
						if (car[i].collidesWith(car[j]))
							tmp = false;// Xóa lên xe khác
					}
				}

				if (!tmp)
					continue;
				else {
					car[i].show();
					landuong_car[i] = l;
					break;
				}
			}
		}

		// Số xe còn lại sẽ nằm ở vị trí ngoài màn hình
		for (int i = 4; i < so_car; i++) {
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);
				if (l == 3)
					continue;
				y = Tools.getRandomIndex(-100, -Control.chieucao);

				car[i].move(landuong[l], y);

				boolean tmp = true;
				for (int j = 0; j < so_car; j++) {
					if (i != j) {
						if (car[i].collidesWith(car[j]))
							tmp = false;// Xóa lên xe khác
					}
				}

				if (!tmp)
					continue;
				else {
					car[i].hide();
					landuong_car[i] = l;
					break;
				}
			}
		}
	}

	// ---------------------------------------------------------------
	// Phương thức set vị trí
	public void setVitri(Sprite sprite, int index) {
		// Khi car mà di chuyển ngoài mình thì ta set lại vị trí của nó
		if (endHeight(sprite)) {
			int l = 0, y = 0;
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);
				y = Tools.getRandomIndex(-100, -Control.chieucao);
				sprite.move(landuong[l], y);

				boolean tmp = true;
				for (int i = 0; i < so_car; i++) {
					if (!sprite.equals(car[i])) {
						if (sprite.collidesWith(car[i]))
							tmp = false;// Xóa lên xe khác
					}
				}

				if (!tmp)
					continue;
				else {
					landuong_car[index] = l;
					sprite.hide();
					break;
				}
			}
		}
	}

	// ---------------------------------------------------------------
	// Phương thức di chuyển Car
	public void move() {
		boolean kt = false;
		if (!Control.huong) {
			if (speed > -5)
				speed--;
		} else
			speed = Control.SPEED + Control.touch_up_speed;

		for (int i = 0; i < so_car; i++) {
			car[i].moveRelativeY(speed);
			// Nếu car nào mà di chuyển vào màn hình thì ta cho hiện thấy
			if (car[i].getRealY() + car[i].getHeight() >= 0)
				car[i].show();

			// Nếu xe nào được phép rẽ thì ta cho rẽ
			if (RE[i] != -1) {
				kt = true;
				if (RE[i] == 0) {// Rẽ trái
					car[i].moveRelativeX(-speed_re);
					den.ht(car[i], 0);
					if (landuong_car[i] != 0) {
						if (car[i].getRealX() <= (landuong[landuong_car[i] - 1])) {
							landuong_car[i] = landuong_car[i] - 1;
							car[i].moveX(landuong[landuong_car[i]]);
							RE[i] = -1;
						}
					}
				} else {// Rẽ phải
					car[i].moveRelativeX(speed_re);
					den.ht(car[i], 1);
					if (landuong_car[i] != so_landuong - 1) {
						if (car[i].getRealX() >= (landuong[landuong_car[i] + 1])) {
							landuong_car[i] = landuong_car[i] + 1;
							car[i].moveX(landuong[landuong_car[i]]);
							RE[i] = -1;
						}
					}
				}
			}

			if (endHeight(car[i])) {
				setVitri(car[i], i);
				// Để đạt được điểm là khi có 1 xe duoc khởi tạo
				// lại thành công
				// thêm 20 điểm
				// vào số điểm
				Control.diem += 20;
				Log.d("DIEM", String.valueOf(Control.diem));
			}

			if (!Control.huong) {
				if (car[i].getRealY() < -100)
					car[i].hide();
			}

		}
		if (!kt)
			den.vang_.hide();

		// Nếu time=0 thì ta bắt đầu cho car rẽ
		if (time.getTime() == 0) {
			// Cho phép 1 xe rẻ
			// Xác định xem xe nào được phép rẽ
			int chon_xe = chonxe();

			if (chon_xe == -1) {

			} else if (chon_xe == -2) {
				time.setTime(2);
			} else {
				time.setTime(2);
			}
		}
	}

	// ---------------------------------------------------------------
	// Phương thức chọn 1 xe để cho xe để cho xe đó rẽ trái hoặc phải
	public int chonxe() {
		int[] tmp = new int[so_car];
		int k = 0;
		for (int i = 0; i < so_car; i++) {
			if (car[i].isVisible()
					&& car[i].getRealY() < (Control.chieucao - 100)
					&& RE[i] == -1) {
				tmp[k] = i;
				k++;
			}
		}
		// Trường hợp không có xe nào đang hiện thấy phù hợp với điều kiện
		if (k == 0)
			return -2;
		// Chọn ngẫu nhiên 1 xe trong các xe hiện thấy
		int index = Tools.getRandomIndex(0, k - 1);
		// Kiểm tra Car này xem nó đang ở làn đường nào?

		boolean duocre = true;

		// Nếu là làn đường đầu tiên
		if (landuong_car[tmp[index]] == 0) {
			// Chỉ có thể rẻ phải
			// Kiểm tra xem xe này có thể rẽ phải được không?
			duocre = sosanh(k, tmp[index], 1);
			if (duocre) {
				RE[tmp[index]] = 1;
			}
		}
		// Nếu là làn đường cuối cùng
		else if (landuong_car[tmp[index]] == so_landuong - 1) {
			// Chỉ có thể rẽ trái
			// Kiểm tra xem xe này có thể rẽ trái được không?
			duocre = sosanh(k, tmp[index], so_landuong - 2);
			if (duocre) {
				RE[tmp[index]] = 0;
			}
		}
		// Ở trong giữa làn đường
		else {
			int landuong_hientai = landuong_car[tmp[index]];

			// Chọn ngẫu nhiên hướng rẽ
			int huong = Tools.getRandomIndex(0, 1);

			if (huong == 0) {
				duocre = sosanh(k, tmp[index], landuong_hientai - 1);
				if (duocre) {
					RE[tmp[index]] = 0;
				}
			} else {
				duocre = sosanh(k, tmp[index], landuong_hientai + 1);

				if (duocre) {
					RE[tmp[index]] = 1;
				}
			}
		}

		// Nếu xe này được rẽ thì trả về chỗ số xe được rẽ
		if (duocre) {
			return tmp[index];
		}
		return -1;// Xe không rẽ được
	}

	// ---------------------------------------------------------------
	// Phương thức kiểm tra có thể di chuyển được không?
	public boolean sosanh(int k, int index, int ld) {
		for (int i = 0; i < so_car; i++) {
			if (index != i && landuong_car[i] == ld) {
				if (car[index].getRealY() >= car[i].getRealY()
						&& car[index].getRealY() <= car[i].getRealY()
								+ car[i].getHeight()) {
					return false;
				}

				if (car[index].getRealY() + car[index].getHeight() >= car[i]
						.getRealY()
						&& car[index].getRealY() + car[index].getHeight() <= car[i]
								.getRealY() + car[i].getHeight()) {
					return false;
				}
			}
		}
		return true;
	}

	// ---------------------------------------------------------------
	// Phương thức kiểm tra nếu có 2 xe va chạm vào nhau thì nổ tung và biến đi
	// Hoặc xe nào ra khỏi đường đua thì cũng vậy
	public void kt1() {
	}
}