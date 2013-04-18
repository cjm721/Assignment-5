package com.example.assignment5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper{
	
	static String database = "CARTOON";
	static String cartoon_Column = "Cartoon";
	static String character_Column = "Character";
	static String age_Column = "AGE";
	
	static DataBase dbHelper;
	
	public DataBase(Context ctx){
		super(ctx, "cartoon.db", null, 1);
		dbHelper = this;
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
		db.execSQL("DROP TABLE IF EXISTS cartoon");
		onCreate(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("Create TABLE " + database + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				cartoon_Column + " text, " + character_Column + " text, " + age_Column + " int);");
	}
	
}
