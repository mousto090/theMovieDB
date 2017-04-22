package ma.example.themoviedb1.logique;

import android.provider.BaseColumns;

public class DataBaseContract  {

	public final static class LocalMovies implements BaseColumns {

		public final static String TABLE_NAME = "movies";
		/**
		 * the local id
		 */
		public final static String _ID = "id";
		/**
		 * the id of the movie from themoviedb
		 */
		public final static String COLUMN_MOVIE_ID = "movie_id";
		public final static String COLUMN_TITLE = "title";
		public final static String COLUMN_POSTER_PATH = "poster_path";
		public final static String COLUMN_GENRE = "genre";
		public final static String COLUMN_RELEASE_DATE = "release_date";
		public final static String COLUMN_RUNTIME = "runtime";
		public final static String COLUMN_RATING = "rating";
		public final static String COLUMN_VOTE_COUNT = "vote_count";
		public final static String COLUMN_SYNOPSIE = "synopsie";
		/**
		 *  List type is either watch list or favorite list
		 */
		public final static String COLUMN_LIST_TYPE = "list_type"; 
	}
}
