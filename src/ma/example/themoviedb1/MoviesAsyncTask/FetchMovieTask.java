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

public class FetchMovieTask extends AsyncTask<String, Void, Movies[]> {

	public interface AsyncDrawerItemSelected{
		public void onItemDrawerSelected(Movies[] movies);
	}
	
	private final String TAG = getClass().getSimpleName();
	
	public AsyncDrawerItemSelected delegateItemDrawer = null;	
	
	private Movies[] getDataFromJson(String moviesJsonString) throws JSONException {
		// colonnes a extraire
		final String MOVIE_ID = "id";
		final String RESULT = "results";
		final String TITLE = "title";
		final String POSTER_PATH = "poster_path";
		final String RATING = "vote_average";
		final String RELEASE_DATE = "release_date";
		final String VOTE_COUNT = "vote_count";
		Movies result[];

		JSONObject movies = new JSONObject(moviesJsonString);
		JSONArray moviesResultArray = movies.getJSONArray(RESULT);

		result = new Movies[moviesResultArray.length()];

		for (int i = 0; i < result.length; i++) {

			JSONObject oneMovieObject = moviesResultArray.getJSONObject(i);
			int id = oneMovieObject.getInt(MOVIE_ID);
			String title = oneMovieObject.getString(TITLE);
			String posterName = oneMovieObject.getString(POSTER_PATH);
			double rating = oneMovieObject.getDouble(RATING);
			String releaseDate = oneMovieObject.getString(RELEASE_DATE);
			int voteCount = oneMovieObject.getInt(VOTE_COUNT);
			
			result[i] = new Movies(id, title, Utility.builtUrlImage(posterName, Constantes.IMAGE_SIZE_W342),
					releaseDate, rating, voteCount);
		}

		return result;
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
		if (null == result) {
			return;
		}
		super.onPostExecute(result);
		delegateItemDrawer.onItemDrawerSelected(result);

		
	}
}
