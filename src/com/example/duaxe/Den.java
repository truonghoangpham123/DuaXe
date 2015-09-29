package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Den {
	public Sprite do_;
	public Sprite vang_;
	private Texture doTexture;
	private Texture vangTexture;

	private boolean an = false;

	// -----------------------------------------------

	public Den() {
	}

	// ------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		do_ = new Sprite(doTexture, -10, -10);
		vang_ = new Sprite(vangTexture, -10, -10);

		scene.getTopLayer().add(do_);
		scene.getTopLayer().add(vang_);
	}

	// ------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		doTexture = new AssetTexture("do.png", context);
		vangTexture = new AssetTexture("vang.png", context);
	}

	// ------------------------------------------------
	//
	public void ht(Sprite sprite, int huong) {
		int x1 = sprite.getRealX() + 6;
		int x2 = sprite.getRealX() + sprite.getWidth() - 6;

		int y = sprite.getRealY() + sprite.getHeight() - 7;

		// Cho đèn nhấp nháy
		if (an) {
			vang_.hide();
			an = false;
		} else {
			vang_.show();
			an = true;
		}

		if (huong == 0) {// Rẽ trái
			vang_.move(x1, y);
		} else {// Rẽ phải
			vang_.move(x2, y);
		}
	}
	// ------------------------------------------------
	// ------------------------------------------------
	// ------------------------------------------------
	// ------------------------------------------------
	// ------------------------------------------------
}
