package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;

public class Diem implements SPRITE_{

	public int len = 5;
	public So[] p_so = new So[len];	
	
	public void init(E3Scene scene) {
		int c = 10;
		for(int i=0;i<len;i++){			
			p_so[i].init(scene);
			p_so[i].setSo(0);
			p_so[i].move(c, 10);
			c+=15;
		}
		
	}

	public void load(Context context) {
		
		for(int i=0;i<len;i++){
			p_so[i]= new So();
			p_so[i].load(context);
			}
		
	}
	
	int du = 0, diem = 0, index = 0;
	public void move() {
		diem = Control.diem;
		if(diem >= 99999 && diem == 0)
			return;
		du = 0;
		index = len - 1;
		while(true){
			du = diem % 10;
			diem = diem / 10;
			p_so[index].setSo(du);
			index--;
			if(diem < 10){
				p_so[index].setSo(diem);
				break;
			}
		}
		
	}

}
