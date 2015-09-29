package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class So implements SPRITE_ {

	private Sprite[] so;
	private Texture[] soTexture;
	private int so_so = 10;

	// -----------------------------------------------------------------------
	public void init(E3Scene scene) {
		so = new Sprite[so_so];
		for (int i = 0; i < so_so; i++) {
			so[i] = new Sprite(soTexture[i], -200, -200);// Tạm thời đặt tại vị
															// trí này
			if (i != 0)
				so[i].hide();// Mặc định ban đầu là hiện thấy số 0
			scene.getTopLayer().add(so[i]);
		}

	}

	// -----------------------------------------------------------------------
	public void load(Context context) {
		soTexture = new Texture[so_so];

		for (int i = 0; i < so_so; i++) {
			soTexture[i] = new AssetTexture(String.valueOf(i) + ".png", context);
		}

	}


	// -----------------------------------------------------------------------
	public void move() {
		// TODO Auto-generated method stub

	}

	// -----------------------------------------------------------------------
	// Phương thức move tới vị trí cài đặt
	public void move(int x, int y) {
		for (int i = 0; i < so_so; i++)
			so[i].move(x, y);
	}

	// -----------------------------------------------------------------------
	// Phương thức đặt số cần hiện thấy
	public void setSo(int so) {
		for (int i = 0; i < so_so; i++) {
			if (i == so)
				this.so[i].show();
			else
				this.so[i].hide();
		}
	}
}
