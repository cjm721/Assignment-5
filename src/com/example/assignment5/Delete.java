package com.example.assignment5;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Delete extends Activity{
	
	String cartoon;
	String character;
	String age;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		
		db = DataBase.dbHelper.getWritableDatabase();
		
		cartoon = getIntent().getStringExtra("cartoon"); //getExtra
		character = getIntent().getStringExtra("character"); //getExtra
		age = getIntent().getStringExtra("age"); //getExtra
		
		((TextView)findViewById(R.id.CartoonName)).setText(cartoon);
		((TextView)findViewById(R.id.CharacterName)).setText(character);
		((TextView)findViewById(R.id.AgeNum)).setText(""+age);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void delete(View v){
		String statement = DataBase.cartoon_Column + "=? AND " + DataBase.character_Column + "=? AND " + DataBase.age_Column + "=?";
		String[] args = {cartoon,character,age};
		db.delete(DataBase.database, statement, args);
		finish();
	}
	
	public void cancel(View v){
		finish();
	}
	
}
