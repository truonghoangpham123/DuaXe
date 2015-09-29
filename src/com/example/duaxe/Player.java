package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Player {
	private Sprite player;
	private Texture playerTexture;

	private int start_width = 102;
	private int end_width = 387;

	private Khoi kh = new Khoi();

	// -----------------------------------
	public Player() {
	}

	// -----------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		player = new Sprite(playerTexture, 199,
				((Control.chieucao / 3) * 2) + 40);
		kh.init(scene);
		kh.xd(player);

		scene.getTopLayer().add(player);
	
	}

	// -----------------------------------
	// Phương thức load
	public void load(Context context) {
		int t = Tools.getRandomIndex(1, 4);
		playerTexture = new AssetTexture("car1_" + t + ".png", context);

		kh.load(context); //Add khoi xe vao doi tuong
		

	}

	// -----------------------------------
	// Phương thức move
	public void move(int x) {
		// Giới hạn player không cho phép di chuyển khỏi đường đi
		// Nễu còn trong đường đi thì mới cho Player di chuyển

		if (player.getRealX() + x >= start_width
				&& player.getRealX() + x <= end_width - player.getWidth()) {
			// Nếu có thể di chuyển theo chiều x thì di chuyển
			player.moveRelativeX(x);
		}

		kh.move(player);

	}

	// -----------------------------------
	// Phương thức xét va chạm giữa Player và Car
	public boolean vacham(Car car) {
		for (int i = 0; i < car.so_car; i++) {
			if (car.car[i].collidesWith(player)) {
				Control.huong = false;
				Control.vacham.play();
				return true;// Có va chạm
			}
		}
		return false;// Không có va chạm

	}
	// -----------------------------------
}
