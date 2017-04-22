package ma.example.themoviedb1.logique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class ConnectionSqliteDb {

	protected SQLiteDatabase mDb = null;
	protected DataBaseHelper dbHelper = null;
	
	public ConnectionSqliteDb(Context context){
		dbHelper = new DataBaseHelper(context);
		
	}
	
	public SQLiteDatabase openConnexion(){
		mDb = dbHelper.getWritableDatabase();
		
		return mDb;
	}
	
	public void closeConnexion(){
		mDb.close();
	}
	
	public SQLiteDatabase getDb(){
		return mDb;
	}
}
