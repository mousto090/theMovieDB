package ma.example.themoviedb1.MoviesAsyncTask;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.Utility;
import ma.example.themoviedb1.adapter.GridViewAdapter;
import ma.example.themoviedb1.logique.ConnectionHttp;
import ma.example.themoviedb1.model.Movies;

public class FetchActorRelatedMovies extends AsyncTask<String, Void, Movies[]> {

	public interface AsyncActorRelatedMovies{
		public void onLoadActorRelatedMoviesFinish(Movies[] movies);
	}
	
	private final String TAG = getClass().getSimpleName();
	
	public AsyncActorRelatedMovies delegateActorRelatedMovies = null;	
	
	

	private Movies[] getDataFromJson(String relatedMoviesJsonStr) throws JSONException {
		// colonnes a extraire
		final String MOVIE_ID = "id";
		final String RESULT = "results";
		final String TITLE = "title";
		final String POSTER_PATH = "poster_path";
		final String OVERVIEW = "overview";
		final String RATING = "vote_average";
		final String RELEASE_DATE = "release_date";
		Movies result[];

		JSONObject movies = new JSONObject(relatedMoviesJsonStr);
		JSONArray moviesResultArray = movies.getJSONArray(RESULT);

		result = new Movies[moviesResultArray.length()];

		for (int i = 0; i < result.length; i++) {

			JSONObject oneMovieObject = moviesResultArray.getJSONObject(i);
			// String overview = oneMovieObject.getString(OVERVIEW);
			// double rating = oneMovieObject.getDouble(RATING);
			// String releaseDate = oneMovieObject.getString(RELEASE_DATE);
			int id = oneMovieObject.getInt(MOVIE_ID);
			String title = oneMovieObject.getString(TITLE);
			String imageName = oneMovieObject.getString(POSTER_PATH);
			result[i] = new Movies(id, title, Utility.builtUrlImage(imageName, Constantes.IMAGE_SIZE_W342));
		}

		return result;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		ProgressDialog pg = new ProgressDialog(mcContext);
//		pg.show();
//		mProgressDialog.show();
		
	}

	@Override
	protected Movies[] doInBackground(String... params) {
		if (params.length == 0) {
			return null;
		}
		ConnectionHttp conn = new ConnectionHttp(params[0]);
		String jsonStr = conn.readStream(conn.getConnection());

		try {
			return getDataFromJson(jsonStr);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);

		} finally {
			conn.disconnect();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Movies[] result) {
		super.onPostExecute(result);
		
		
	}
}
