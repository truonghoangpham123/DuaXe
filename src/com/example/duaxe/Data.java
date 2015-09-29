package com.example.duaxe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Data extends Activity {
	String filename = "data.txt";// Tên file CSDL

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this_data = this;
	}

	// ------------------------------------------------------
	// Phương thức đọc file và trả về một chuỗi
	public String getData() {
		try {
			InputStream in = openFileInput(filename);
			if (in != null) {
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuilder buf = new StringBuilder();

				while ((str = reader.readLine()) != null) {
					buf.append(str + "\n");
				}
				in.close();
				return buf.toString();
			} else
				return "";
		} catch (java.io.FileNotFoundException e) {
			return "";
		} catch (Throwable t) {
			return "";
		}
	}

	// ------------------------------------------------------
	public String getTenDiem(String s, String ten_, int diem_) {
		String[] tmp = s.split("\n");

		Log.d("tmp.le", String.valueOf(tmp.length));

		String[] ten = new String[tmp.length / 2];
		int[] diem = new int[tmp.length / 2];

		Log.d("diem.le", String.valueOf(diem.length));

		int j = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (i % 2 == 0)
				ten[j] = tmp[i];
			else {
				diem[j] = Integer.parseInt((tmp[i]));
				j++;
			}
		}

		int index_min = -1;
		for (int i = 0; i < diem.length; i++) {
			if (diem_ > diem[i]) {
				int min = diem[0];
				for (int k = 0; k < diem.length; k++) {
					if (min >= diem[k]) {
						min = diem[k];
						index_min = k;
					}
				}
				diem[index_min] = diem_;
				ten[index_min] = ten_;
				break;
			}
		}

		// Không thêm được
		if (index_min == -1)
			return "";// Không được thêm vào số điểm.

		// Sắp xếp
		for (int i = 0; i < diem.length; i++) {
			for (int k = i; k < diem.length; k++) {
				if (diem[i] > diem[k]) {
					int tm = diem[i];
					diem[i] = diem[k];
					diem[k] = tm;

					String stm = ten[i];
					ten[i] = ten[k];
					ten[k] = stm;
				}
			}
		}

		String return_ = "";
		for (int i = 0; i < ten.length; i++) {
			Log.d("ten", ten[i]);
			Log.d("diem", String.valueOf(diem[i]));

			if (return_.length() == 0)
				return_ = ten[i] + "\n" + String.valueOf(diem[i]);
			else
				return_ = ten[i] + "\n" + String.valueOf(diem[i]) + "\n"
						+ return_;
		}

		return return_;
	}

	// ------------------------------------------------------
	// Phương thức xóa toàn bộ dữ liệu
	public void deleteData() {
		deleteFile(filename);
	}

	// ------------------------------------------------------
	// Phương thức thêm dữ liệu
	public boolean addData(String data) {
		try {
			deleteData();// Xóa cái cũ rùi lưu cái mới
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput(
					filename, 0));
			out.write(data.toString());
			out.close();
			return true;// Thành công
		} catch (Throwable t) {
			return false;// Không thành công
		}
	}

	// ------------------------------------------------------
	public String kt(String ten, int sodiem) {
		boolean them = true;
		String data_daco = getData();
		if (data_daco.length() == 0)
			data_daco = ten + "\n" + String.valueOf(sodiem);
		else {
			if (data_daco.split("\n").length < 10)
				data_daco = ten + "\n" + String.valueOf(sodiem) + "\n"
						+ data_daco;
			else {
				String stt = getTenDiem(data_daco, ten, sodiem);
				if (stt.length() != 0)
					data_daco = stt;
				else
					them = false;
			}
		}
		if (them)
			return data_daco;
		else
			return "";
	}
}
