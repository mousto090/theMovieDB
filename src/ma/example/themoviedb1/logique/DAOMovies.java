package ma.example.themoviedb1.logique;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.model.Movies;

public class DAOMovies extends ConnectionSqliteDb{

	//	These indices are tied to DATABASE_COLUMNS.  If DATABASE_COLUMNS changes, these
    // 	must change.
	public final static int _ID = 0;
	public final static int COLUMN_MOVIE_ID = 1;
	public final static int COLUMN_TITLE = 2;
	public final static int COLUMN_POSTER_PATH = 3;
	public final static int COLUMN_GENRE = 4;
	public final static int COLUMN_RELEASE_DATE = 5;
	public final static int COLUMN_RUNTIME = 6;
	public final static int COLUMN_RATING = 7;
	public final static int COLUMN_VOTE_COUNT = 8;
	public final static int COLUMN_SYNOPSIE = 9;
	public final static int COLUMN_LIST_TYPE = 10;
	
	public DAOMovies(Context context) {
		super(context);
		openConnexion();
	}

	public Long insertMovie(Movies movie, String typeList){
		
		//SQLiteDatabase db = openConnexion();
		ContentValues contentValues = createMovieValues(movie, typeList);
		long insertId = mDb.insert(DataBaseContract.LocalMovies.TABLE_NAME, null, contentValues);
		Log.v("Movie inserted", movie.getId()+"");
		return insertId;
	}
	
	private ContentValues createMovieValues(Movies movie, String typeList){
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_MOVIE_ID, movie.getId());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_TITLE, movie.getTitle());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_POSTER_PATH, movie.getPosterName());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_GENRE, movie.getGenres());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_RELEASE_DATE, movie.getReleaseDate());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_RUNTIME, movie.getDuree());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_RATING, movie.getRating());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_VOTE_COUNT, movie.getVoteCount());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_SYNOPSIE, movie.getOverview());
		contentValues.put(DataBaseContract.LocalMovies.COLUMN_LIST_TYPE, typeList);
		return contentValues;
		
	}
	public int delete(Movies movie, String typeList){
		
		int deleted = mDb.delete(DataBaseContract.LocalMovies.TABLE_NAME,
				DataBaseContract.LocalMovies.COLUMN_MOVIE_ID + " = ? AND " + 
				DataBaseContract.LocalMovies.COLUMN_LIST_TYPE + " = ? ", 
				new String[]{Integer.toString(movie.getId()), typeList});
		Log.v("Movie deleted", deleted+"");
		return deleted;
	}
	
	public int deleteAll(){
		Log.v("Movie deleted", "All Movie deleted");
		return mDb.delete(DataBaseContract.LocalMovies.TABLE_NAME,
				null, null);		
	}
	
//	public int update(Movies movie){
//		
//		ContentValues contentValues = new ContentValues();
//		contentValues = createMovieValues(movie);
//		int rowAffected =  mDb.update(
//				DataBaseContract.LocalMovies.TABLE_NAME,
//				contentValues,
//				DataBaseContract.LocalMovies._ID + " = ? ",
//				new String[]{Integer.toString(movie.getId())});
//		
//		return rowAffected;
//	}
//	
	
	public List<Movies> getListMoviesByType(String listType){
		List<Movies> listMovies = new ArrayList<Movies>();
		
		Cursor curseur = mDb.query(DataBaseContract.LocalMovies.TABLE_NAME, 
				null,
				DataBaseContract.LocalMovies.COLUMN_LIST_TYPE + " = ? ",
				new String[] {listType},
				null,
				null,
				null);
		curseur.moveToFirst();
		while(!curseur.isAfterLast()){
			Movies movie = new Movies(
					curseur.getInt(COLUMN_MOVIE_ID),
					curseur.getString(COLUMN_TITLE), 
					curseur.getString(COLUMN_POSTER_PATH),
					curseur.getString(COLUMN_RELEASE_DATE),
					curseur.getInt(COLUMN_RUNTIME),
					curseur.getDouble(COLUMN_RATING), 
					curseur.getInt(COLUMN_VOTE_COUNT),
					curseur.getString(COLUMN_SYNOPSIE),
					null, null, null, null, null, null);
			
			if(listType.equals(Constantes.TYPE_FAVORITE)){
				movie.setFavorite((curseur.getString(COLUMN_LIST_TYPE).equals(Constantes.TYPE_FAVORITE)));
			}
			
			if(listType.equals(Constantes.TYPE_WATCHLIST)){
				movie.setWatchlist((curseur.getString(COLUMN_LIST_TYPE).equals(Constantes.TYPE_WATCHLIST)));
			}
//			
			listMovies.add(movie);
			curseur.moveToNext();
		}
		curseur.close();
		return listMovies;
	}
	
	public List<Movies> getAllMovies(){
		List<Movies> listMovies = new ArrayList<Movies>();
		
		Cursor curseur = mDb.query(DataBaseContract.LocalMovies.TABLE_NAME, 
				null,
				null,
				null,
				null,
				null,
				null);
		curseur.moveToFirst();
		while(!curseur.isAfterLast()){
			Movies movie = new Movies(
					curseur.getInt(COLUMN_MOVIE_ID),
					curseur.getString(COLUMN_TITLE), 
					curseur.getString(COLUMN_POSTER_PATH),
					curseur.getString(COLUMN_RELEASE_DATE),
					curseur.getInt(COLUMN_RUNTIME),
					curseur.getDouble(COLUMN_RATING), 
					curseur.getInt(COLUMN_VOTE_COUNT),
					curseur.getString(COLUMN_SYNOPSIE),
					null, null, null, null, null, null);
			listMovies.add(movie);
			curseur.moveToNext();
		}
		
		curseur.close();
		return listMovies;
	}

}
