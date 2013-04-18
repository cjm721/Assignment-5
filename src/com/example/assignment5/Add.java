package com.example.assignment5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Add extends Activity{
	
	DataBase dbHelper;
	SQLiteDatabase db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		dbHelper = DataBase.dbHelper;
		db = dbHelper.getWritableDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void back(View v){
		finish();
	}
	
	public void add(View v){
		String Cartoon = ((EditText)findViewById(R.id.editText1)).getText().toString().trim();
		String name = ((EditText)findViewById(R.id.etCharacter)).getText().toString().trim();
		int age = ((Spinner)findViewById(R.id.sAge)).getSelectedItemPosition() + 1;
		
		if(Cartoon == null || Cartoon.equals("")){
			displayDialog("Error", "Must enter a Cartoon");
			return;
		}else if (name == null || name.equals("")){
			displayDialog("Error", "Must enter a Character");
			return;
		}else{
			String statement = "INSERT INTO " + DataBase.database + 
					" (" + DataBase.cartoon_Column + ", " + DataBase.character_Column + ", " + DataBase.age_Column + ")" + 
					" VALUES (\"" + Cartoon + "\", \"" + name + "\", " + age + ")";
			System.out.println(statement);
			db.execSQL(statement);
			((Spinner)findViewById(R.id.sAge)).setSelection(0);
			((EditText)findViewById(R.id.editText1)).requestFocus();
			displayDialog("Success", "Character successfully added");
		}
		
	}
	
    public void displayDialog(String title, String message) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(false);
    	builder.setTitle(title);
    	builder.setMessage(message);

    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int i) {
    		  dialog.dismiss();
    	  }
    	});

    	AlertDialog alert = builder.create();
    	alert.show();
    }
}
