package com.example.assignment5;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	DataBase dbHelper;
	SQLiteDatabase db;

	String tableName = "CARTOON";

	SimpleCursorAdapter records;

	int selectedRecord = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbHelper = new DataBase(getBaseContext());
		db = dbHelper.getWritableDatabase();



		displayRecords(getCursor());
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
				selectedRecord = arg2;
			}      	
		});
	}


	private void displayRecords(Cursor c) {		
		int [] to = {R.id.textView1, R.id.textView2, R.id.textView3};

		ListView lv = (ListView)findViewById(R.id.listView1);

		String[] collums = {DataBase.cartoon_Column, DataBase.character_Column, DataBase.age_Column};

		records = new SimpleCursorAdapter(this, R.layout.listview_main, c, collums, to);
		lv.setAdapter(records);

	}

	public Cursor getCursor(){
		String[] collums = {"_id", DataBase.cartoon_Column, DataBase.character_Column, DataBase.age_Column};
		Cursor c = db.query(DataBase.database, collums, null, null, null, null, DataBase.cartoon_Column + ", " + DataBase.character_Column);
		return c;
	}

	public void onResume(){
		super.onResume();
		displayRecords(getCursor());
		selectedRecord = -1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void add(View v){
		Intent i = new Intent(this, Add.class);
		startActivity(i);
	}

	public void delete(View v){
		if(selectedRecord == -1){
			displayDialog("Error","Must select record to delete first");
			return;
		}
		Intent i = new Intent(this, Delete.class);

		ListView lv = ((ListView)findViewById(R.id.listView1));
		LinearLayout ll = (LinearLayout)lv.getChildAt(selectedRecord);
		String cartoon = ((TextView)ll.getChildAt(0)).getText().toString();
		String character = ((TextView)ll.getChildAt(1)).getText().toString();
		String age = ((TextView)ll.getChildAt(2)).getText().toString();

		System.out.println(cartoon+character+age);

		i.putExtra("cartoon", cartoon);
		i.putExtra("character", character);
		i.putExtra("age", age);


		startActivity(i);


	}

	public void deleteAll(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete all entries. This cannot be undone.");
		builder.setTitle("Warning");
		builder.setNegativeButton("Cancel", new OnClickListener () 
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
			}
		}
				);
		builder.setPositiveButton("Confirm", new OnClickListener () 
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				db.execSQL("DELETE FROM " + DataBase.database);
				displayRecords(getCursor());

			}
		}
				);
		builder.create().show();
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
