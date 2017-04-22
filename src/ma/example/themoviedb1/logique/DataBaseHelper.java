package ma.example.themoviedb1.logique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private final String TAG = getClass().getSimpleName();
	public final static String DATABASE_NAME = "moviedb.db";
	public final static int DATABASE_VERSION = 1;	
	
	public DataBaseHelper(Context context){
		super(context,DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		final  String CREATE_TABLE_MOVIES = " CREATE TABLE " + DataBaseContract.LocalMovies.TABLE_NAME + " ( " +
				DataBaseContract.LocalMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				DataBaseContract.LocalMovies.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_TITLE + " TEXT NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_POSTER_PATH + " TEXT, " +
				DataBaseContract.LocalMovies.COLUMN_GENRE + " TEXT NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_RUNTIME + " INTEGER NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_RATING + " REAL NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_SYNOPSIE + " TEXT NOT NULL, " +
				DataBaseContract.LocalMovies.COLUMN_LIST_TYPE+ " TEXT NOT NULL " +
				" ) ;";
		
		db.execSQL(CREATE_TABLE_MOVIES);
		Log.v(TAG, "oncreate database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DELETE IF EXISITS " + DataBaseContract.LocalMovies.TABLE_NAME);
		
	}
}
