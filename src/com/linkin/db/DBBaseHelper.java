package com.linkin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBBaseHelper {

	protected  String DATABASE_NAME = "base.db";
	protected  String DATABASE_TABLE = "table_base";
	protected  int DATABASE_VERSION = 1;
	protected  String DATABASE_CREATE = "";
	protected static String _ID = "_id";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBBaseHelper(Context ctx,String databaseName,String databaseTable,int databaseVersion,String databaseCreate){
		context = ctx;
		DATABASE_NAME = databaseName;
		DATABASE_TABLE = databaseTable;
		DATABASE_VERSION = databaseVersion;
		DATABASE_CREATE = databaseCreate;
		DBHelper = new DatabaseHelper(context);
	}
	
	public SQLiteDatabase open() {
		db = DBHelper.getWritableDatabase();
		return db;
	}
	
	public void close() {
		DBHelper.close();
		db.close();
	}
	
	public long insert(ContentValues values){
		return insert(null,values);
	}
	
	public long insert(String nullColumnHack, ContentValues values){
		return db.insert(DATABASE_TABLE, null, values);
	}
	
	public int delete(int id){
		return delete(_ID+"="+id);
	}
	
	public int delete(String whereClause){
		return delete(whereClause,null);
	}
	
	public int delete(String whereClause,String[] whereArgs){
		return db.delete(DATABASE_TABLE, whereClause, whereArgs);
	}
	
	public int update(ContentValues values,String whereClause){
		return update(values,whereClause,null);
	}
	
	public int update(ContentValues values,String whereClause,String[] whereArgs){
		return db.update(DATABASE_TABLE, values, whereClause, whereArgs);
	}
	
	public Cursor query(){
		return query(null,null);
	}
	
	public Cursor query(String selection, String[] selectionArgs){
		return query(null,selection,selectionArgs,null,null,null);
	}
	
	public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		return db.query(DATABASE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy,String limit){
		return db.query(DATABASE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	private  class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			db.execSQL(DATABASE_CREATE);
		}

	};
}
