package com.example.duaxe;

import android.content.Context;

import com.e3roid.E3Scene;

public interface SPRITE_ {
	public E3Scene scene = null;
	public Context context = null;

	public void init(E3Scene scene);

	public void load(Context context);

	public void move();
}
