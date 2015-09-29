package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Khoi implements SPRITE_ {

	private Sprite[] kh;
	private int so_kh;
	private Texture khTexture;

	private int dauy = 0, cuoiy = 0;

	// -------------------------------------------------
	public Khoi() {

	}

	// -------------------------------------------------
	public void init(E3Scene scene) {
		so_kh = 20;
		kh = new Sprite[so_kh];

		for (int i = 0; i < so_kh; i++) {
			kh[i] = new Sprite(khTexture, -10, -10);
			scene.getTopLayer().add(kh[i]);
			
		}
	}

	// -------------------------------------------------
	public void load(Context context) {
		khTexture = new AssetTexture("khoi.png", context);

	}

	// -------------------------------------------------
	public void move() {
		// TODO Auto-generated method stub

	}

	// -------------------------------------------------
	// Xác định vị trí ban đầu của player
	public void xd(Sprite sprite) {
		dauy = (sprite.getRealY() + sprite.getHeight()) - 10;
		cuoiy = dauy + 30;
		for (int i = 0; i < so_kh; i++) {
			kh[i].move(Tools.getRandomIndex(sprite.getRealX(),
					(sprite.getRealX() + sprite.getWidth())), dauy);
		}
	}

	// -------------------------------------------------
	public void move(Sprite sprite) {
		for (int i = 0; i < so_kh; i++) {
			//Khói sẻ hiện về bên trái và bên phải của car
			kh[i].moveRelative(Tools.getRandomIndex(-4, 4),
					Tools.getRandomIndex(1, 4));
			// Nếu vị trí mà nằm ngoài cuối y thì ta khởi tạo lại vị trí
			if (kh[i].getRealY() > cuoiy) {
				kh[i].move(
						Tools.getRandomIndex(sprite.getRealX() + 15,
								(sprite.getRealX() + sprite.getWidth()) - 15),
						dauy);
				if (Control.touch_up_speed == 0)
					kh[i].hide();
				else
					kh[i].show();

			}
		}
	}
	// --------------------------------------------------
}
