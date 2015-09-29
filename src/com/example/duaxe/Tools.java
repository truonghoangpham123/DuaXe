package com.example.duaxe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.Toast;

public class Tools {
	// ------------------------------------------------------------------------
	// Resize bipmap
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);

		return resizedBitmap;
	}

	// ---------------------------------------------------------------------------------------
	// Phương thức cho bạn 1 số ngẫu nhiên từ min->max.
	public static int getRandomIndex(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// Hiện thị 1 tin nhắn đơn giản
	public static void senMessenger(Context context, String messenger) {
		Toast.makeText(context, messenger, Toast.LENGTH_LONG).show();
	}
}
