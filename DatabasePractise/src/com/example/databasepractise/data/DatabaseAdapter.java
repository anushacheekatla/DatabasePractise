package com.example.databasepractise.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.databasepractise.Message;

public class DatabaseAdapter  {
	DatabaseOpenHelper dbOpenHelper;
	
	public DatabaseAdapter(Context context) {
		super();
		dbOpenHelper = new DatabaseOpenHelper(context);
	}
	public long insertData(String name,String pass){
		ContentValues contentValues = new ContentValues();
		contentValues.put(dbOpenHelper.NAME, name);
		contentValues.put(dbOpenHelper.PASSWORD, pass);
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		long id=db.insert(dbOpenHelper.TABLE_NAME, null, contentValues);
		return id;
	}
	
	public String getAllData(){
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		String[] columns = {dbOpenHelper.UID,dbOpenHelper.NAME,dbOpenHelper.PASSWORD};
		Cursor cur = db.query(dbOpenHelper.TABLE_NAME, columns, null, null, null, null, null);
		int uidIndex=cur.getColumnIndex(dbOpenHelper.UID);
		int nameIndex=cur.getColumnIndex(dbOpenHelper.NAME);
		int passwordIndex = cur.getColumnIndex(dbOpenHelper.PASSWORD);
		StringBuffer sb = new StringBuffer();
		while(cur.moveToNext()){
			
			sb.append(cur.getInt(uidIndex) + ": " +cur.getString(nameIndex)+" "+cur.getString(passwordIndex));
			
		}
		return sb.toString();
	}
	public int deleteUser(String user){
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		int noOfRowsDeleted=db.delete(dbOpenHelper.TABLE_NAME, dbOpenHelper.NAME + " = ?", new String[]{user});
		return noOfRowsDeleted;
	}
	public int updateUser(String oldUser,String newUser){
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(dbOpenHelper.NAME,newUser);
		
		int noOfRowsDeleted=db.update(dbOpenHelper.TABLE_NAME, contentValues, dbOpenHelper.NAME + " = ?", new String[]{oldUser});
		return noOfRowsDeleted;
	}

	static class DatabaseOpenHelper extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "anushaDatabase";
		private static final String TABLE_NAME = "ANUSHATABLE";
		private static final String UID="_id";
		private static final String NAME = "Name";
		private static final String PASSWORD = "Password";
		private static final int DATABASE_VERSION=1;
		private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
		private Context context;
		public DatabaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context=context;
			Message.message(context, "Constructor Called");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Message.message(context, "OnCreate Called");
			} catch (SQLException e) {
				Message.message(context, e.getMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				Message.message(context, "OnCreate Called");
			} catch (SQLException e) {
				Message.message(context,e.getMessage());
			}
			onCreate(db);

		}

	}

	
}
