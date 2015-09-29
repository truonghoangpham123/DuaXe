package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Background_ {
	private Sprite bg1;
	private Sprite bg2;
	private Texture bg1Texture;
	public int speedBackGround;
	public int up = 10;
		
	

	// ---------------------------------------------------------------
	public Background_() {
	}

	// ---------------------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		bg1 = new Sprite(bg1Texture, 0, 15);
		bg2 = new Sprite(bg1Texture, 0, -Control.chieucao+25);
		scene.getTopLayer().add(bg1);
		scene.getTopLayer().add(bg2);
		speedBackGround = 0;
	}

	// ---------------------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		int t=Tools.getRandomIndex(1, 4);
		bg1Texture = new AssetTexture("bg"+t+".png", context);
	}

	// ---------------------------------------------------------------
	// Phương thức vẽ
	public void move() {
		speedBackGround = (Control.SPEED + up  + Control.touch_up_speed);
		if (speedBackGround <= 0)
			return;// Ngừng lại

		bg1.moveRelativeY(speedBackGround);
		bg2.moveRelativeY(speedBackGround);

		if (bg1.getRealY() >= Control.chieucao-30) {
			bg1.moveY(-Control.chieucao);
		}

		if (bg2.getRealY() >= Control.chieucao-30) {
			bg2.moveY(-Control.chieucao);
		}
	}

	// ---------------------------------------------------------------
	public void setUp(int up) {
		this.up = up;
	}

	// ---------------------------------------------------------------
	public int getUp() {
		return up;
	}
}
