package ru.ifmo.gpstrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_DATA = "data";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_LA = "la";
	public static final String COLUMN_LO = "lo";

	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " + TABLE_DATA + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_TEXT
			+ " text not null, " + COLUMN_LA + " real, " + COLUMN_LO + " real);";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		onCreate(db);
	}

}
