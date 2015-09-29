package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class DemNguoc extends Thread {
	private Texture[] sp_demnguocTexture;
	private Sprite[] sp_demnguoc;
	private int i = 3;

	public DemNguoc() {
	}

	// -----------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		sp_demnguoc = new Sprite[4];

		for (int j = 0; j < sp_demnguoc.length; j++) {

			int cx = Control.chieungang / 2 - sp_demnguocTexture[j].getWidth() / 2;
			int cy = Control.chieucao / 2 - sp_demnguocTexture[j].getHeight() / 2;
			sp_demnguoc[j] = new Sprite(sp_demnguocTexture[j], cx, cy);
			sp_demnguoc[j].hide();
			scene.getTopLayer().add(sp_demnguoc[j]);
		}
	}

	// -----------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		sp_demnguocTexture = new Texture[4];

		sp_demnguocTexture[0] = new AssetTexture("go.png", context);
		sp_demnguocTexture[1] = new AssetTexture("mot.png", context);
		sp_demnguocTexture[2] = new AssetTexture("hai.png", context);
		sp_demnguocTexture[3] = new AssetTexture("ba.png", context);
	}

	// -----------------------------------------------------
	// Cài đặt phương thức run
	public void run() {
		//Chạy nhạc trước khi đua
		Control.nhaccompetion.play();
		
		while (true) {
			try {
				Thread.sleep(1200);// Giữa chậm 1s
				if (i==1){
					Control.nhaccompetion.release();
					Control.carstart.play();
				}
				if (i == 0) {
					sp_demnguoc[1].hide();
					sp_demnguoc[i].show();
					Thread.sleep(1300);					
		
					for (int j = 0; j < sp_demnguoc.length; j++)
						sp_demnguoc[j].hide();//.onRemove();
					Control.isPlay = true;
					
					Thread.sleep(200);
					Control.carstart.release();
					Control.tocdothuong.loop();
					return;
				}
				for (int j = 0; j < sp_demnguoc.length; j++) {
					if (i != j)
						sp_demnguoc[j].hide();
					else
						sp_demnguoc[i].show();
				}
				i--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// -----------------------------------------------------
	// -----------------------------------------------------
	// -----------------------------------------------------
	// -----------------------------------------------------
}
