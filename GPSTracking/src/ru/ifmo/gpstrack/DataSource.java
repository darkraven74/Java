package ru.ifmo.gpstrack;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_NAME,
			SQLiteHelper.COLUMN_TEXT, SQLiteHelper.COLUMN_LA, SQLiteHelper.COLUMN_LO

	};

	public DataSource(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void updateData(Data data) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, data.name);
		values.put(SQLiteHelper.COLUMN_TEXT, data.text);
		values.put(SQLiteHelper.COLUMN_LA, data.latitude);
		values.put(SQLiteHelper.COLUMN_LO, data.longitude);

		int res = database.update(SQLiteHelper.TABLE_DATA, values, SQLiteHelper.COLUMN_ID + " = ?",
				new String[] { String.valueOf(data.id) });
	}

	public Data createData(Data data) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, data.name);
		values.put(SQLiteHelper.COLUMN_TEXT, data.text);
		values.put(SQLiteHelper.COLUMN_LA, data.latitude);
		values.put(SQLiteHelper.COLUMN_LO, data.longitude);

		long insertId = database.insert(SQLiteHelper.TABLE_DATA, null, values);
		Cursor cursor = database.query(SQLiteHelper.TABLE_DATA, allColumns, SQLiteHelper.COLUMN_ID + " = "
				+ insertId, null, null, null, null);
		cursor.moveToFirst();
		data = cursorToData(cursor);
		cursor.close();
		return data;
	}

	public void deleteData(Data data) {
		double id = data.id;
		database.delete(SQLiteHelper.TABLE_DATA, SQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public ArrayList<Data> getAllData() {
		ArrayList<Data> dataArray = new ArrayList<Data>();

		Cursor cursor = database.query(SQLiteHelper.TABLE_DATA, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Data data = cursorToData(cursor);
			dataArray.add(data);
			cursor.moveToNext();
		}
		cursor.close();
		return dataArray;
	}

	private Data cursorToData(Cursor cursor) {
		Data data = new Data();
		data.id = cursor.getLong(0);
		data.name = cursor.getString(1);
		data.text = cursor.getString(2);
		data.latitude = cursor.getDouble(3);
		data.longitude = cursor.getDouble(4);

		return data;
	}
}
